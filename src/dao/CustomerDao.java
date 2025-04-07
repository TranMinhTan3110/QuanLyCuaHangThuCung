package dao;

import model.entity.Customer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDao implements  DaoInterface<Customer> {
    public boolean insert(Customer customer) {
        String sqlPerson = "INSERT INTO Person(name, phone, address) VALUES(?, ?, ?)";
        String sqlCustomer = "INSERT INTO Customer(id, loyaltyPoints, membershipLevel) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stPerson = con.prepareStatement(sqlPerson, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stCustomer = con.prepareStatement(sqlCustomer)) {

            con.setAutoCommit(false); // Tắt auto-commit để đảm bảo tính toàn vẹn dữ liệu

            // Chèn vào bảng Person
            stPerson.setString(1, customer.getName());
            stPerson.setString(2, customer.getPhone());
            stPerson.setString(3, customer.getAddress());
//            stPerson.setString(4, "Customer"); // Đặt type là "Customer"
            stPerson.executeUpdate();
            // Lấy ID của Person vừa chèn
            ResultSet resultset = stPerson.getGeneratedKeys();
            int personId;
            if (resultset.next()) {
                personId = resultset.getInt(1);
            } else {
                throw new SQLException("Không thể lấy ID của Person!");
            }
            // Chèn vào bảng Customer với ID vừa lấy
            stCustomer.setInt(1, personId);
            stCustomer.setInt(2, customer.getLoyaltyPoints());
            stCustomer.setString(3, customer.getMembershipLevel());
            stCustomer.executeUpdate();

            con.commit(); // Lưu thay đổi vào database
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Customer customer) {
        String sql = "UPDATE Customer set loyaltyPoints = ?, membershipLevel = ?";

        return false;
    }

    @Override
    public boolean delete(Customer customer) {
        String sqlCustomer = "DELETE FROM Customer WHERE id = ?";
        String sqlPerson = "DELETE FROM Person WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stCustomer = con.prepareStatement(sqlCustomer);
             PreparedStatement stPerson = con.prepareStatement(sqlPerson)) {

            con.setAutoCommit(false); // Tắt auto-commit để đảm bảo tính toàn vẹn dữ liệu

            // Xóa thông tin của Customer
            stCustomer.setInt(1, customer.getId());
            stCustomer.executeUpdate();

            // Xóa thông tin của Person
            stPerson.setInt(1, customer.getId());
            stPerson.executeUpdate();

            con.commit(); // Lưu thay đổi vào database
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public ArrayList<Customer> getAll() {
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

    @Override
    public Customer selectByID(int id) {
        String sql = "SELECT p.id, p.name, p.phone, p.address, c.loyaltyPoints, c.membershipLevel " +
                "FROM Person p INNER JOIN Customer c ON p.id = c.id WHERE p.id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    String name = rs.getString("name");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    int loyaltyPoints = rs.getInt("loyaltyPoints");
                    String membershipLevel = rs.getString("membershipLevel");

                    return new Customer(id, name, phone, address, loyaltyPoints, membershipLevel);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
