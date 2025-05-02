package dao;

import model.entity.Customer;
import model.entity.Pet;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
                customer.setId(personId);
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
        // SQL để cập nhật các thông tin của khách hàng (thuộc Person)
        String sql = "UPDATE Person SET name = ?, phone = ?, address = ? WHERE id = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)
        ) {
            // Set các giá trị vào câu lệnh SQL
            st.setString(1, customer.getName());
            st.setString(2, customer.getPhone());
            st.setString(3, customer.getAddress());
            st.setInt(4, customer.getId());  // Lưu ý lấy ID từ Customer

            // Thực hiện câu lệnh SQL
            return st.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
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


    public boolean findByPhone(String phone) {
        String sql = "SELECT 1 FROM Person p INNER JOIN Customer c ON p.id = c.id WHERE p.phone = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, phone);

            try (ResultSet rs = st.executeQuery()) {
                return rs.next(); // Nếu có kết quả, trả về true
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Nếu không tìm thấy
    }
    public List<Customer> customerListByName(String name) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.phone, p.address, c.loyaltyPoints, c.membershiplevel " + // Lấy thêm các cột cần thiết
                "FROM Customer c " +
                "JOIN Person p ON c.id = p.id " + // Giả sử có cột person_id liên kết
                "WHERE LOWER(p.name) LIKE ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, "%" + name.toLowerCase().trim() + "%"); // Thêm % vào chuỗi tìm kiếm và chuyển về chữ thường
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setLoyaltyPoints(rs.getInt("loyaltyPoints"));
                customer.setMembershipLevel(rs.getString("membershipLevel")); // Giả sử có cột rank trong Customer hoặc Person
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers; // Trả về danh sách khách hàng
    }

    public List<Customer> customerListByPhone(String phone) {
        List<Customer> customers = new ArrayList<>();
        String sql = "SELECT p.id, p.name, p.phone, p.address, c.loyaltyPoints, c.membershiplevel " + // Lấy thêm các cột cần thiết
                "FROM Customer c " +
                "JOIN Person p ON c.id = p.id " + // Giả sử có cột person_id liên kết
                "WHERE LOWER(p.phone) LIKE ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1,  phone.toLowerCase().trim() + "%"); // Thêm % vào chuỗi tìm kiếm và chuyển về chữ thường
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                customer.setAddress(rs.getString("address"));
                customer.setLoyaltyPoints(rs.getInt("loyaltyPoints"));
                customer.setMembershipLevel(rs.getString("membershipLevel")); // Giả sử có cột rank trong Customer hoặc Person
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers; // Trả về danh sách khách hàng
    }

}
