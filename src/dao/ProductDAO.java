package dao;

import model.entity.Product;
import java.sql.*;
import java.util.*;

public class ProductDAO implements DaoInterfaceProduct<Product>,DaoInterface<Product> {

    @Override
    public boolean insert(Product product) {
        String sql = "INSERT INTO Product(name, price, categoryID) VALUES(?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, product.getName());
            st.setDouble(2, product.getPrice());
            st.setInt(3, product.getCategoryID());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Product product) {
        String sql = "UPDATE Product SET name = ?, price = ?, categoryID = ? WHERE ProductID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, product.getName());
            st.setDouble(2, product.getPrice());
            st.setInt(3, product.getCategoryID());
            st.setInt(4, product.getProductID());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Product product) {
        if (product.getProductID() <= 0) {
            System.out.println("ID sản phẩm không hợp lệ!");
            return false;
        }
        String sql = "DELETE FROM Product WHERE ProductID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, product.getProductID());
            return st.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
            return false;
        }
    }

    @Override
    public ArrayList<Product> getAll() {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("ProductID"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("categoryID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    @Override
    public Product selectByID(int id) {
        String sql = "SELECT * FROM Product WHERE ProductID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("ProductID"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("categoryID")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<Product> selectByCondition(Map<String, Object> filters) {
        ArrayList<Product> productList = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Product WHERE 1=1");
        ArrayList<Object> params = new ArrayList<>();

        if (filters.containsKey("name")) {
            query.append(" AND name LIKE ?");
            params.add("%" + filters.get("name") + "%");
        }
        if (filters.containsKey("categoryID")) {
            query.append(" AND categoryID = ?");
            params.add(filters.get("categoryID"));
        }
        if (filters.containsKey("minPrice")) {
            query.append(" AND price >= ?");
            params.add(filters.get("minPrice"));
        }
        if (filters.containsKey("maxPrice")) {
            query.append(" AND price <= ?");
            params.add(filters.get("maxPrice"));
        }

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(query.toString())) {
            for (int i = 0; i < params.size(); i++) {
                st.setObject(i + 1, params.get(i));
            }
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                productList.add(new Product(
                        rs.getInt("ProductID"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getInt("categoryID")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
}
