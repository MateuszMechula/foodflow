package pl.foodflow.utils;

public class ErrorMessages {

    public static final String RESTAURANT_BY_ID_NOT_FOUND = "Restaurant with ID: [%s] not found";
    public static final String RESTAURANT_BY_NIP_NOT_FOUND = "Restaurant with ID: [%s] not found";
    public static final String CATEGORY_ITEM_NOT_FOUND = "CategoryItem with ID: [%s] not found";
    public static final String CATEGORY_ITEM_IS_NULL = "CategoryItem is null";
    public static final String CUSTOMER_NOT_FOUND = "Customer with ID: [%s] not found";
    public static final String CUSTOMER_WITH_USER_ID_NOT_FOUND = "Customer with ID: [%s] not found";
    public static final String IMAGE_FILE_IS_MISSING = "Image file is empty or missing";
    public static final String MENU_NOT_CREATED = "To add a category you have to create a menu first";
    public static final String MENU_NOT_FOUND = "Menu with ID: [%s] not found";
    public static final String MENU_CATEGORY_NEED_TO_CHOOSE = "You need to choose a category";
    public static final String MENU_CATEGORY_NOT_FOUND = "MenuCategory with ID: [%s] not found";
    public static final String RESTAURANT_AND_MENU_NOT_CREATED =
            "To add a category, you must create a restaurant and a menu first";
    public static final String RESTAURANT_NOT_CREATED = "To add a menu, you must create a restaurant first";
    public static final String RESTAURANT_ALREADY_HAS_MENU =
            "Restaurant with nip: [%s] already has a menu. You can't create more than one.";
    public static final String ORDER_RECORD_NOT_FOUND = "Order Record with ID: [%s] not found";
    public static final String ORDER_RECORD_ID_IS_NULL = "OrderRecord ID cannot be NULL";
    public static final String ORDER_ITEMS_NOT_FOUND = "OrderItems not found";
    public static final String OWNER_WITH_ID_NOT_FOUND = "Owner with ID: [%s] not found";
    public static final String OWNER_WITH_USER_ID_NOT_FOUND = "Owner with userId: [%s] not found";
    public static final String RESTAURANT_ADDRESS_WITH_ADDRESS_ID_NOT_FOUND =
            "RestaurantAddress with address ID: [%s] not found";
    public static final String USER_WITH_USERNAME_NOT_FOUND = "User with username: [%s] not found";
    public static final String ORDER_CANNOT_BE_DELETED_AFTER_FIVE_MINUTES =
            "Order cannot be deleted as it was placed more than 5 minutes ago";

}
