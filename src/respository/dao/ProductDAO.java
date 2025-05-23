package respository.dao;

import model.entity.Category;
import model.entity.Product;
import respository.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO implements DaoInterface<Product> {

	@Override
	public boolean insert(Product product) {
		String sql = "INSERT INTO Product(name, price, quantity, categoryID) VALUES(?, ?, ?, ?)";

		try (Connection con = DatabaseConnection.getConnection();
			 PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			st.setString(1, product.getName());
			st.setDouble(2, product.getPrice());
			st.setInt(3, product.getQuantity());
			st.setInt(4, product.getCategory().getCategoryID());

			int check = st.executeUpdate();
			if (check > 0) {
				try (ResultSet generatedKeys = st.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int newID = generatedKeys.getInt(1);
						product.setProductID(newID); // cập nhật lại ID cho đối tượng product
					}
				}
			}
			return check > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean update(Product product) {
		String sql = "UPDATE Product SET name = ?, price = ?, quantity = ?, categoryID = ? WHERE productID = ?";

		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setString(1, product.getName());
			st.setDouble(2, product.getPrice());
			st.setInt(3, product.getQuantity());
			st.setInt(4, product.getCategory().getCategoryID());
			st.setInt(5, product.getProductID());

			int check = st.executeUpdate();
			return check > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean updateQuantity(int productID, int soldQuantity) {
		String sql = "UPDATE Product SET quantity = quantity - ? WHERE productID = ? AND quantity >= ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			stmt.setInt(1, soldQuantity); // số lượng muốn trừ
			stmt.setInt(2, productID);
			stmt.setInt(3, soldQuantity); // để đảm bảo không trừ vượt quá số lượng hiện có

			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Product product) {
		String deleteOrderDetailSql = "DELETE FROM OrderDetail WHERE productID = ?";
		String deleteProductSql = "DELETE FROM Product WHERE productID = ?";

		try (Connection con = DatabaseConnection.getConnection()) {
			con.setAutoCommit(false); // Bắt đầu transaction

			try (PreparedStatement st1 = con.prepareStatement(deleteOrderDetailSql);
					PreparedStatement st2 = con.prepareStatement(deleteProductSql)) {

				st1.setInt(1, product.getProductID());
				st1.executeUpdate();

				st2.setInt(1, product.getProductID());
				int rowsAffected = st2.executeUpdate();

				con.commit(); // Nếu không có lỗi, commit transaction
				return rowsAffected > 0;

			} catch (SQLException e) {
				con.rollback(); // Có lỗi, rollback
				e.printStackTrace();
				return false;
			} finally {
				con.setAutoCommit(true); // Khôi phục trạng thái
			}

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public ArrayList<Product> getAll() {
		ArrayList<Product> products = new ArrayList<>();
		String sql = """
				    SELECT p.productID, p.name, p.price, p.quantity,
				           c.categoryID, c.categoryName
				    FROM Product p JOIN Category c ON p.categoryID = c.categoryID
				""";

		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement st = con.prepareStatement(sql);
				ResultSet rs = st.executeQuery()) {

			while (rs.next()) {
				int productID = rs.getInt("productID");
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				int categoryID = rs.getInt("categoryID");
				String categoryName = rs.getString("categoryName");

				Category category = new Category(categoryID, categoryName);
				Product product = new Product();
				product.setProductID(productID);
				product.setName(name);
				product.setPrice(price);
				product.setQuantity(quantity);
				product.setCategory(category);
				products.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	@Override
	public Product selectByID(int id) {
		String sql = """
				    SELECT p.productID, p.name, p.price, p.quantity,
				           c.categoryID, c.categoryName
				    FROM Product p JOIN Category c ON p.categoryID = c.categoryID
				    WHERE p.productID = ?
				""";

		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setInt(1, id);
			ResultSet rs = st.executeQuery();

			if (rs.next()) {
				String name = rs.getString("name");
				double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				int categoryID = rs.getInt("categoryID");
				String categoryName = rs.getString("categoryName");

				Category category = new Category(categoryID, categoryName);
				Product product = new Product();
				product.setProductID(id);
				product.setName(name);
				product.setPrice(price);
				product.setQuantity(quantity);
				product.setCategory(category);

				return product;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public ArrayList<Product> selectByNameLike(String name) {
		ArrayList<Product> products = new ArrayList<>();

		String sql = """
				    SELECT p.productID, p.name, p.price, p.quantity,
				           c.categoryID, c.categoryName
				    FROM Product p
				    JOIN Category c ON p.categoryID = c.categoryID
				    WHERE p.name LIKE ?
				""";

		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setString(1, "%" + name + "%");
			ResultSet rs = st.executeQuery();

			while (rs.next()) {
				int productID = rs.getInt("productID");
				String productName = rs.getString("name");
				double price = rs.getDouble("price");
				int quantity = rs.getInt("quantity");
				int categoryID = rs.getInt("categoryID");
				String categoryName = rs.getString("categoryName");

				Category category = new Category(categoryID, categoryName);
				Product product = new Product();
				product.setProductID(productID);
				product.setName(productName);
				product.setPrice(price);
				product.setQuantity(quantity);
				product.setCategory(category);

				products.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	// kiểm tra sản phẩm đã tồn tại chưa
	public boolean isProductExists(String name) {
		String sql = "SELECT COUNT(*) FROM Product WHERE name = ?";

		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setString(1, name);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt(1); // Lấy giá trị COUNT(*)
					return count > 0; // Nếu > 0 nghĩa là đã có sản phẩm
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static String getProductNameById(int productId) {
		String sql = "SELECT name FROM Product WHERE productID = ?";
		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {
			stmt.setInt(1, productId);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return rs.getString("name");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "Unknown Product";
	}
}
