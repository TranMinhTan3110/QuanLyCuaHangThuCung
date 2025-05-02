package dao;

import model.entity.*;

import java.sql.*;
import java.util.ArrayList;

public class BillDAO implements DaoInterface<Bill> {

    @Override
    public boolean insert(Bill bill) {
        String sql = "INSERT INTO Bill (orderID, billMethod, amount, billTime) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bill.getOrderID());
            ps.setString(2, bill.getbillMethod());
            ps.setDouble(3, bill.getAmount());
            ps.setDate(4, bill.getbillTime());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Bill bill) {
        String sql = "UPDATE Bill SET orderID = ?, billMethod = ?, amount = ?, billTime = ? WHERE billID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bill.getOrderID());
            ps.setString(2, bill.getbillMethod());
            ps.setDouble(3, bill.getAmount());
            ps.setDate(4, bill.getbillTime());
            ps.setInt(5, bill.getbillID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(Bill bill) {
        String sql = "DELETE FROM Bill WHERE billID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bill.getbillID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<Bill> getAll() {
        ArrayList<Bill> list = new ArrayList<>();
        String sql = "SELECT * FROM Bill";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Bill bill = new Bill(
                        rs.getInt("billID"),
                        rs.getInt("orderID"),
                        rs.getDouble("amount"),
                        rs.getString("billMethod"),
                        rs.getDate("billTime")

                );
                list.add(bill);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Bill selectByID(int id) {
        String sql = "SELECT * FROM Bill WHERE billID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Bill(
                        rs.getInt("billID"),
                        rs.getInt("orderID"),
                        rs.getDouble("amount"),
                        rs.getString("billMethod"),
                        rs.getDate("billTime")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Product> getAllProduct() {
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


    public ArrayList<Pet> getAllPet() {
        ArrayList<Pet> pets = new ArrayList<>();
        String sql = "SELECT * FROM Pet WHERE trangThai = N'Chưa bán'";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Pet pet = new Pet(
                        rs.getInt("petID"),
                        rs.getString("name"),
                        rs.getString("species"),
                        rs.getString("breed"),
                        rs.getInt("age"),
                        rs.getDouble("price"),
                        rs.getString("gender")
                );
                pets.add(pet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pets;
    }

    public ArrayList<Customer> getAllCustomer() {
        String sql = "SELECT p.id, p.name, p.phone, p.address, c.loyaltyPoints, c.membershipLevel " +
                "FROM Person p INNER JOIN Customer c ON p.id = c.id";

        ArrayList<Customer> customers = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String phone = rs.getString("phone");
                String address = rs.getString("address");
                int loyaltyPoints = rs.getInt("loyaltyPoints");
                String membershipLevel = rs.getString("membershipLevel");

                Customer customer = new Customer(id, name, phone, address, loyaltyPoints, membershipLevel);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public void saveBill(Bill bill) throws SQLException {
        String sql = "INSERT INTO Bill(billMethod, amount, billTime, orderID) VALUES (?, ?, GETDATE(), ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, bill.getbillMethod());
            ps.setDouble(2, bill.getAmount());
            ps.setInt(3, bill.getOrderID());
            ps.executeUpdate();
        }
    }
}
