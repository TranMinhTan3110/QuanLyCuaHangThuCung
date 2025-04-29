package dao;

import model.entity.Pet;
import model.entity.Role;
import model.entity.User;

import java.sql.*;
import java.util.ArrayList;

public class UserDAO implements DaoInterface<User>{



    public boolean isIdExists(int id) {
        String sql = "SELECT COUNT(*) FROM [User] WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean isPhoneExists(String phone) {
        String sql = "SELECT COUNT(*) FROM Person WHERE phone = ?"; // Sửa từ [User] thành Person
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phone);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean isUsernameExists(String username) {
        String sql = "SELECT COUNT(*) FROM [User] WHERE username = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next() && rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    @Override
    public boolean insert(User user) {
        String sqlPerson = "INSERT INTO Person (name, phone, address) VALUES (?, ?, ?)";
        String sqlUser = "INSERT INTO [User] (id, username, password, roleName) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Tắt auto-commit để đảm bảo cả hai bảng được chèn cùng lúc

            int personId; // ID của Person sau khi insert

            // Chèn vào bảng Person và lấy ID
            try (PreparedStatement stmtPerson = conn.prepareStatement(sqlPerson, Statement.RETURN_GENERATED_KEYS)) {
                stmtPerson.setString(1, user.getName());
                stmtPerson.setString(2, user.getPhone());
                stmtPerson.setString(3, user.getAddress());
                stmtPerson.executeUpdate();

                try (ResultSet generatedKeys = stmtPerson.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        personId = generatedKeys.getInt(1);
                        user.setId(personId);//gán id tu database
                    } else {
                        throw new SQLException("Không lấy được ID của Person.");
                    }
                }
            }

            // Chèn vào bảng User với ID của Person
            try (PreparedStatement stmtUser = conn.prepareStatement(sqlUser)) {
                stmtUser.setInt(1, personId);
                stmtUser.setString(2, user.getUsername());
                stmtUser.setString(3, user.getPassword());
                stmtUser.setString(4, user.getRole().name()); // Chuyển Enum thành String
                stmtUser.executeUpdate();
            }

            conn.commit(); // Commit cả hai bảng
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    @Override
    public boolean update(User user) {
        String sqlUser = "UPDATE [User] SET username = ?, password = ?, roleName = ? WHERE id = ?";
        String sqlPerson = "UPDATE Person SET name = ?, phone = ?, address = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false); // 🚀 Bắt đầu transaction

            try (PreparedStatement stmtPerson = conn.prepareStatement(sqlPerson)) {
                stmtPerson.setString(1, user.getName());
                stmtPerson.setString(2, user.getPhone());
                stmtPerson.setString(3, user.getAddress());
                stmtPerson.setInt(4, user.getId());
                stmtPerson.executeUpdate();
            }

            try (PreparedStatement stmtUser = conn.prepareStatement(sqlUser)) {
                stmtUser.setString(1, user.getUsername());
                stmtUser.setString(2, user.getPassword());
                stmtUser.setString(3, user.getRole().toString());
                stmtUser.setInt(4, user.getId());
                stmtUser.executeUpdate();
            }

            conn.commit(); // ✅ Nếu không có lỗi, lưu thay đổi vào CSDL
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            try (Connection conn = DatabaseConnection.getConnection()) {
                conn.rollback(); // ❌ Nếu có lỗi, hủy thay đổi
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false;
    }



    @Override
    public boolean delete(User user) {
        String sql = "DELETE FROM [User] WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, user.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public ArrayList<User> getAll() {
        ArrayList<User> userList = new ArrayList<>();
        String sql = "SELECT U.id, P.name, P.phone, P.address, U.username, U.password, U.roleName " +
                "FROM [User] U " +
                "JOIN Person P ON U.id = P.id";  // Kết hợp bảng User với bảng Person để lấy đầy đủ thông tin

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                userList.add(new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("roleName")) // Chuyển từ String sang Enum
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }



    @Override
    public User selectByID(int id) {
        String sql = "SELECT p.id, p.name, p.phone, p.address, u.username, u.password, u.roleName " +
                "FROM Person p " +
                "JOIN [User] u ON p.id = u.id " +
                "WHERE p.id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Tạo đối tượng User từ dữ liệu lấy ra
                return new User(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getString("username"),
                        rs.getString("password"),
                        Role.valueOf(rs.getString("roleName"))
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Trả về null nếu không tìm thấy
    }

    @Override
    public ArrayList<Pet> selectByName(String name) {
        return null;
    }

}
