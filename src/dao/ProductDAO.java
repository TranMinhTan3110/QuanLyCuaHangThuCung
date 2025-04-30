package dao;

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
                        product.setProductID(newID);  // cập nhật lại ID cho đối tượng product
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

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

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

    @Override
    public boolean delete(Product product) {
        String sql = "DELETE FROM Product WHERE productID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, product.getProductID());
            return st.executeUpdate() > 0;

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

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

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

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

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

    //kiểm tra sản phẩm đã tồn tại chưa
    public boolean isProductExists(String name) {
        String sql = "SELECT COUNT(*) FROM Product WHERE NAME = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

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

}
