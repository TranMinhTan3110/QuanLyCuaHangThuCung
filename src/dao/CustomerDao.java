package dao;

import model.entity.Customer;

import java.sql.*;
import java.util.ArrayList;

public class CustomerDao implements  DaoInterface<Customer> {
    public boolean insert(Customer customer) {
        String sqlPerson = "INSERT INTO Person(name, phone, address, type) VALUES(?, ?, ?, ?)";
        String sqlCustomer = "INSERT INTO Customer(id, loyaltyPoints, membershipLevel) VALUES (?, ?, ?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stPerson = con.prepareStatement(sqlPerson, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement stCustomer = con.prepareStatement(sqlCustomer)) {

            con.setAutoCommit(false); // Tắt auto-commit để đảm bảo tính toàn vẹn dữ liệu

            // Chèn vào bảng Person
            stPerson.setString(1, customer.getName());
            stPerson.setString(2, customer.getPhone());
            stPerson.setString(3, customer.getAddress());
            stPerson.setString(4, "Customer"); // Đặt type là "Customer"
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

        return false;
    }

    @Override
    public ArrayList<Customer> getAll() {
        return null;
    }

    @Override
    public Customer selectByID(int id) {
        return null;
    }
}
