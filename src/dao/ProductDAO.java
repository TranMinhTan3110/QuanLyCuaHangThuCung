package dao;

import model.entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProductDAO implements DaoInterface <Product>{

    @Override
    public boolean insert(Product product) {
        String sql = "INSERT INTO Product(name, price, categoryID) VALUES(?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, product.getName());
            st.setDouble(2, product.getPrice());
            st.setInt(3, product.getCategoryID());

            int check = st.executeUpdate(); // Thực thi lệnh SQL
            return check > 0; // Nếu có ít nhất 1 dòng bị ảnh hưởng thì trả về true

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean update(Product product) {
        String sql = "UPDATE Product SET name = ? ,price = ?, categoryID = ? WHERE ProductID = ?";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement st = con.prepareStatement(sql);
        ){
                st.setString(1,product.getName());
                st.setDouble(2,product.getPrice());
                st.setInt(3,product.getCategoryID());
                st.setInt(4,product.getProductID());
                int check = st.executeUpdate();
                return check >0;
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

            int check = st.executeUpdate();
            return check > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa sản phẩm: " + e.getMessage());
            return false;
        }
    }


    @Override
    public ArrayList<Product> getAll() {
        ArrayList<Product> products = new ArrayList<>();
        String sql = "SELECT * FROM Product";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement st  = con.prepareStatement(sql);
        ){
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                int productID = rs.getInt("productID");
                String name = rs.getString("name");
                Double price = rs.getDouble("price");
                int categoryID = rs.getInt("categoryID");
                Product product = new Product(productID,name,price,categoryID);
                products.add(product);

            }
        }catch (SQLException e){
            e.printStackTrace();

        }

        return products;
    }

    @Override
    public Product selectByID(int id) {
        return null;
    }


}
