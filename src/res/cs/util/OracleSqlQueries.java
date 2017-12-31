package res.cs.util;

public class OracleSqlQueries {
	public static final String GET_USER = "SELECT * FROM users WHERE user_name = ?";
	public static final String CREATE_USER = "INSERT INTO users (first_name, last_name, user_name, password, gender, address, phone_number, email) values(?, ?, ?, ?, ?, ?, ?, ?)";
	public static final String GET_USERS = "SELECT * FROM users";
	public static final String UPDATE_USER = "UPDATE users SET first_name = ?, last_name = ?, user_name = ?, password = ?, gender = ?, address = ?, phone_number = ?, email = ? WHERE user_id = ?";
	public static final String REMOVE_USER = "DELETE FROM users WHERE user_id = ?";
	public static final String CREATE_ITEM = "INSERT INTO items (item_name, item_price, item_description, image, active, category) values(?, ?, ?, ?, ?, ?)";
	public static final String GET_ITEMS = "SELECT * FROM items";
	public static final String UPDATE_ITEM = "UPDATE items SET item_name = ?, item_price = ?, item_description = ?, image = ?, active = ?, category = ? WHERE item_id = ?";
	public static final String GET_ITEM = "SELECT * FROM items WHERE item_id = ?";
	public static final String CREATE_REVIEW = "INSERT INTO reviews (user_id, item_id, description) values(?, ?, ?)";
	public static final String GET_REVIEWS = "SELECT * FROM reviews WHERE item_id = ?";
	public static final String GET_REVIEWS_BY_USER = "SELECT * FROM reviews WHERE user_id = ?";
	public static final String REMOVE_REVIEW = "DELETE FROM reviews WHERE review_id = ?";
	public static final String CREATE_STORE = "INSERT INTO stores (store_name, address, city, zipcode, staff_number, image) values(?, ?, ?, ?, ?, ?)";
	public static final String GET_STORES = "SELECT * FROM stores";
	public static final String REMOVE_STORE = "DELETE FROM stores WHERE store_id = ?";
	public static final String CREATE_ORDER = "INSERT INTO orders (user_id, store_id, payment_id, subtotal, tax_amount, total_price) values(?, ?, ?, ?, ?, ?)";
	public static final String CREATE_ORDER_ITEMS = "INSERT INTO order_items (order_id, item_id) values(?, ?)";
	public static final String GET_ORDERS_BY_USERID = "SELECT * FROM orders WHERE user_id = ?";
	public static final String GET_ORDERS = "SELECT * FROM orders";
	public static final String DELETE_ORDER = "DELETE FROM orders WHERE order_id = ?";
	public static final String CREATE_PAYMENT = "INSERT INTO payments (credit_card_number, secure_code, zipcode) values(?, ?, ?)";
	public static final String GET_STORE = "SELECT * FROM STORES WHERE store_id= ?";
	public static final String UPDATE_STORE = "UPDATE stores SET store_name = ?, address= ?, city= ?, zipcode = ?, staff_number = ?, image = ? WHERE store_id = ?";
	public static final String DELETE_PAYMENT = "DELETE FROM payments WHERE payment_id = ?";
	public static final String REMOVE_ITEM = "DELETE FROM items WHERE item_id = ?";


}
