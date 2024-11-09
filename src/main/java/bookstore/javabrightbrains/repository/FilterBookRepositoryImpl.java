package bookstore.javabrightbrains.repository;

import bookstore.javabrightbrains.dto.book.BookFilterDto;
import bookstore.javabrightbrains.entity.Book;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Book> criteria = cb.createQuery(Book.class);
//
//        Metamodel m = entityManager.getMetamodel();
//        EntityType<Book> Book_ = m.entity(Book.class);

        Root<Book> book = criteria.from(Book.class);

        List<Predicate> predicates = new ArrayList<>();

        if(filter.isDiscount() ) {
            predicates.add(cb.ge(book.get("discount"), 0));
        }

        if(filter.getCategory() != null) {
            predicates.add(cb.equal(book.get("category"), filter.getCategory()));
        }

        if (filter.getMinPrice() != null && filter.getMaxPrice() != null) {
            predicates.add(cb.between(book.get("price"),
                    BigDecimal.valueOf(filter.getMinPrice()),
                    BigDecimal.valueOf(filter.getMaxPrice())));
        }

        criteria.orderBy(cb.desc(book.get("name")));

        // This query fetches the Books as per the Page Limit
        List<Book> result = entityManager.createQuery(criteria).setFirstResult((int) pageable.getOffset()).setMaxResults(pageable.getPageSize()).getResultList();

        // Create Count Query
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<Book> booksRootCount = countQuery.from(Book.class);
        countQuery.select(cb.count(booksRootCount)).where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

        // Fetches the count of all Books as per given criteria
        Long count = entityManager.createQuery(countQuery).getSingleResult();

        Page<Book> resultPage = new PageImpl<>(result, pageable, count);
        return resultPage;
//
//        criteria.where(predicates.toArray(Predicate[]::new));
//        return entityManager.createQuery(criteria).getResultList();
    }
}
