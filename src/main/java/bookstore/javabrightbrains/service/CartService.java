package bookstore.javabrightbrains.service;

import bookstore.javabrightbrains.dto.cart.CartItemUpdateRequestDto;
import bookstore.javabrightbrains.dto.cart.CartItemRequestDto;
import bookstore.javabrightbrains.dto.cart.CartResponseDto;
import bookstore.javabrightbrains.entity.Book;
import bookstore.javabrightbrains.entity.Cart;
import bookstore.javabrightbrains.entity.CartItem;
import bookstore.javabrightbrains.entity.User;
import bookstore.javabrightbrains.exception.IdNotFoundException;
import bookstore.javabrightbrains.exception.InvalidQuantityException;
import bookstore.javabrightbrains.exception.MessagesException;
import bookstore.javabrightbrains.exception.NotEnoughBooksInStockException;
import bookstore.javabrightbrains.repository.BookRepository;
import bookstore.javabrightbrains.repository.CartItemRepository;
import bookstore.javabrightbrains.repository.CartRepository;
import bookstore.javabrightbrains.repository.UserRepository;
import bookstore.javabrightbrains.utils.MappingUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private MappingUtils mappingUtils;

    @Transactional
    public void addToCart(Long userId, CartItemRequestDto cartItemRequestDto) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    User user = userRepository.findById(userId)
                            .orElseThrow(() -> new IdNotFoundException(MessagesException.USER_NOT_FOUND));
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
        Book book = bookRepository.findById(cartItemRequestDto.getBookId())
                .orElseThrow(() -> new IdNotFoundException(MessagesException.BOOK_NOT_FOUND));

        CartItem cartItem = mappingUtils.toCartItem(cartItemRequestDto, cart, book);

        cartItemRepository.save(cartItem);
    }
    @Transactional
    public CartResponseDto getCart(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.USER_NOT_FOUND));
        Cart cart = cartRepository.findByUserId(userId)
                .orElse(new Cart());

        List<CartItem> cartItems = cartItemRepository.findAllByCart(cart);
        return mappingUtils.toCartResponseDto(cart, cartItems);
    }

    @Transactional
    public void updateCartItem(Long userId, Long cartItemId, CartItemUpdateRequestDto cartItemUpdateRequestDto) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.CART_ITEM_NOT_FOUND));

        if (!cartItem.getCart().getUser().getId().equals(userId)) {
            throw new IdNotFoundException(MessagesException.CART_ITEM_NOT_BELONG_TO_USER);
        }

        Book book = cartItem.getBook();
        int availableQuantity = book.getTotalStock();

        int newQuantity = cartItemUpdateRequestDto.getQuantity();
        if (newQuantity <= 0) {
            throw new InvalidQuantityException(MessagesException.QUANTITY_CANNOT_BE_ZERO_OR_NEGATIVE);
        }

        if (cartItemUpdateRequestDto.getQuantity() > availableQuantity) {
            throw new NotEnoughBooksInStockException(MessagesException.NOT_ENOUGH_BOOKS_IN_STOCK);
        }

        cartItem.setQuantity(cartItemUpdateRequestDto.getQuantity());
        cartItemRepository.save(cartItem);
    }

    @Transactional
    public void deleteCartItem(Long userId, Long cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IdNotFoundException(MessagesException.CART_ITEM_NOT_FOUND));

        if (!cartItem.getCart().getUser().getId().equals(userId)) {
            throw new IdNotFoundException(MessagesException.CART_ITEM_NOT_BELONG_TO_USER);
        }

        cartItemRepository.delete(cartItem);
    }

}