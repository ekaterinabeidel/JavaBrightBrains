package bookstore.javabrightbrains.repository;

import bookstore.javabrightbrains.dto.book.BookFilterDto;
import bookstore.javabrightbrains.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;


import org.hibernate.Session;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaCriteriaQuery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.query.QueryUtils;
import org.springframework.stereotype.Repository;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class FilterBookRepositoryImpl implements FilterBookRepository {
    private final EntityManager entityManager;

    @Override
    public Page<Book> findByFilter(BookFilterDto filter, Pageable pageable) {
        HibernateCriteriaBuilder cb = entityManager.unwrap(Session.class).getCriteriaBuilder();
        JpaCriteriaQuery<Book> criteria = cb.createQuery(Book.class);

        Root<Book> book = criteria.from(Book.class);

        List<Predicate> predicates = new ArrayList<>();

        if(filter.isDiscount()) {
            predicates.add(cb.greaterThan(book.get("discount"), 0));
        }

        if(filter.getCategory() != null) {
            predicates.add(cb.equal(book.get("category"), filter.getCategory()));
        }

        if (filter.getMinPrice() != null && filter.getMaxPrice() != null) {
            predicates.add(cb.between(book.get("priceDiscount"),
                    BigDecimal.valueOf(filter.getMinPrice()),
                    BigDecimal.valueOf(filter.getMaxPrice())));
        }

        criteria.orderBy(QueryUtils.toOrders(pageable.getSort(), book, cb));

        // This query fetches the Books as per the Page Limit
        List<Book> result = entityManager.createQuery(criteria.where(predicates.toArray(new Predicate[0])))
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();


        // Create Count Query
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Book> booksRootCount = countQuery.from(Book.class);
        countQuery.select(cb.count(booksRootCount))
                .where(cb.and(predicates
                        .toArray(new Predicate[predicates.size()])));

        // Fetches the count of all Books as per given criteria
        Long count = entityManager.createQuery(criteria.createCountQuery()).getSingleResult();

        Page<Book> resultPage = new PageImpl<>(result, pageable, count);
        return resultPage;
    }
}
