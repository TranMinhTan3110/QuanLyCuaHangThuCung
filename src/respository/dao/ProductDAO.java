package respository.dao;

import model.entity.Category;
import model.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO implements DaoInterface<Product> {

	@Override
	public boolean insert(Product product) {
		String sql = "INSERT INTO Product(name, price, quantity, categoryID, trangThai) VALUES(?, ?, ?, ?, ?)";
		try (Connection con = DatabaseConnection.getConnection();
			 PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

			st.setString(1, product.getName());
			st.setDouble(2, product.getPrice());
			st.setInt(3, product.getQuantity());
			st.setInt(4, product.getCategory().getCategoryID());
			st.setString(5, "Còn hàng"); // default trangThai

			int check = st.executeUpdate();
			if (check > 0) {
				try (ResultSet generatedKeys = st.getGeneratedKeys()) {
					if (generatedKeys.next()) {
						int newID = generatedKeys.getInt(1);
						product.setProductID(newID);
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
		String sql = "UPDATE Product SET name = ?, price = ?, quantity = ?, categoryID = ?, trangThai = ? WHERE productID = ?";
		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setString(1, product.getName());
			st.setDouble(2, product.getPrice());
			st.setInt(3, product.getQuantity());
			st.setInt(4, product.getCategory().getCategoryID());
			if(product.getQuantity() > 0) {
				st.setString(5, "Còn hàng");
			} else {
				st.setString(5, "Ngừng bán");
			}
			st.setInt(6, product.getProductID());

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

			stmt.setInt(1, soldQuantity);
			stmt.setInt(2, productID);
			stmt.setInt(3, soldQuantity);

			int affectedRows = stmt.executeUpdate();
			return affectedRows > 0;

		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean delete(Product product) {
		String sql = "UPDATE Product SET trangThai = ? WHERE productID = ?";
		try (Connection con = DatabaseConnection.getConnection();
			 PreparedStatement st = con.prepareStatement(sql)) {

			st.setString(1, "Ngừng bán");
			st.setInt(2, product.getProductID());

			int rowsAffected = st.executeUpdate();
			return rowsAffected > 0;

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
                   c.categoryID, c.categoryName, p.trangThai
            FROM Product p JOIN Category c ON p.categoryID = c.categoryID
            WHERE p.trangThai = 'Còn hàng'
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
				String trangThai = rs.getString("trangThai");

				Category category = new Category(categoryID, categoryName);
				Product product = new Product();
				product.setProductID(productID);
				product.setName(name);
				product.setPrice(price);
				product.setQuantity(quantity);
				product.setCategory(category);
				product.settrangThai(trangThai);
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
                   c.categoryID, c.categoryName, p.trangThai
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
				String trangThai = rs.getString("trangThai");

				Category category = new Category(categoryID, categoryName);
				Product product = new Product();
				product.setProductID(id);
				product.setName(name);
				product.setPrice(price);
				product.setQuantity(quantity);
				product.setCategory(category);
				product.settrangThai(trangThai);

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
                   c.categoryID, c.categoryName, p.trangThai
            FROM Product p
            JOIN Category c ON p.categoryID = c.categoryID
            WHERE p.name LIKE ? AND p.trangThai = 'Còn hàng'
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
				String trangThai = rs.getString("trangThai");

				Category category = new Category(categoryID, categoryName);
				Product product = new Product();
				product.setProductID(productID);
				product.setName(productName);
				product.setPrice(price);
				product.setQuantity(quantity);
				product.setCategory(category);
				product.settrangThai(trangThai);

				products.add(product);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return products;
	}

	public boolean isProductExists(String name) {
		String sql = "SELECT COUNT(*) FROM Product WHERE name = ?";

		try (Connection con = DatabaseConnection.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {

			st.setString(1, name);
			try (ResultSet rs = st.executeQuery()) {
				if (rs.next()) {
					int count = rs.getInt(1);
					return count > 0;
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

	public ArrayList<Product> getAllDiscontinued() {
		ArrayList<Product> products = new ArrayList<>();
		String sql = """
        SELECT p.productID, p.name, p.price, p.quantity,
               c.categoryID, c.categoryName, p.trangThai
        FROM Product p
        JOIN Category c ON p.categoryID = c.categoryID
        WHERE p.trangThai = 'Ngừng bán'
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
				String trangThai = rs.getString("trangThai");

				Category category = new Category(categoryID, categoryName);
				Product product = new Product();
				product.setProductID(productID);
				product.setName(name);
				product.setPrice(price);
				product.setQuantity(quantity);
				product.setCategory(category);
				product.settrangThai(trangThai);

				products.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return products;
	}


}