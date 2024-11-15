package bookstore.javabrightbrains.exception;

public class MessagesException {
    public static String BOOK_NOT_FOUND = "Book not found";
    public static String CATEGORY_NOT_FOUND = "Category not found";
    public static String USER_NOT_FOUND = "User not found";
    public static String FAVORITE_NOT_FOUND = "Favorite not found";
    public static String CART_ITEM_NOT_FOUND = "Cart item not found";
    public static String ORDER_NOT_FOUND = "Order not found";
    public static String CART_ITEM_NOT_BELONG_TO_USER = "Cart item does not belong to the user";
    public static String CATEGORY_DUPLICATED = "Such category already exists";
    public static String FAVORITE_DUPLICATED = "This book is already in favorite";
    public static String NOT_ENOUGH_BOOKS_IN_STOCK = "Not enough books available in stock";
    public static  String QUANTITY_CANNOT_BE_ZERO_OR_NEGATIVE = "Quantity cannot be zero or negative";
    public static  String ORDER_CANNOT_BE_CANCELED_INVALID_STATUS = "Order cannot be canceled unless it is in 'Pending' or 'Created' status";


}
