package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.cart.CartItemDto;
import bookstore.javabrightbrains.entity.Book;
import bookstore.javabrightbrains.entity.Cart;
import bookstore.javabrightbrains.entity.CartItem;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.repository.BookRepository;
import bookstore.javabrightbrains.repository.CartItemRepository;
import bookstore.javabrightbrains.repository.CartRepository;
import bookstore.javabrightbrains.utils.MappingUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private MappingUtils mappingUtils;

    @Transactional
    public void addToCart(CartItemDto cartItemDto) {
        Cart cart = cartRepository.findById(1L)
                .orElseThrow(() -> new IdNotFoundException("Cart not found for user ID: " + 1L));

        Book book = bookRepository.findById(cartItemDto.getBookId())
                .orElseThrow(() -> new IdNotFoundException("Book not found with ID: " + 1L));

        CartItem cartItem = mappingUtils.toCartItem(cartItemDto, cart, book);

        cartItemRepository.save(cartItem);
    }
}
