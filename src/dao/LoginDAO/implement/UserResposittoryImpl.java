package dao.LoginDAO.implement;

import dao.DatabaseConnection;
import model.entity.User;
import model.request.LoginRequest;
import respository.userRespositorty;

import java.sql.*;

public class UserResposittoryImpl implements userRespositorty {

    @Override
    public User getUserWithUserNameAndPassWord(LoginRequest loginRequest) {

        // Không dùng roleId, JOIN trực tiếp bằng roleName
        String query = "SELECT u.username, u.password, u.roleName " +
                "FROM [User] u " +
                "JOIN Role r ON u.roleName = r.roleName " +
                "WHERE u.username = ? AND u.password = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
//            System.out.println("Kết nối thành công!");

            // Gán giá trị vào câu lệnh SQL
            stmt.setString(1, loginRequest.getUsername());
            stmt.setString(2, loginRequest.getPassword());

            // Thực thi truy vấn
            ResultSet rs = stmt.executeQuery();

            // Nếu có dữ liệu trả về
            if (rs.next()) {
                String role = rs.getString("roleName"); // Lấy role từ bảng User

                if ("admin".equalsIgnoreCase(role)) {  // Kiểm tra role phải là "admin"
                    User user = new User();
                    user.setUsername(rs.getString("username"));
                    user.setPassword(rs.getString("password"));
                    return user;
                } else {
                    System.out.println("Tài khoản không phải admin!");
                    return null; // Nếu không phải admin thì không cho đăng nhập
                }
            }

        } catch (SQLException e) {
            System.out.println("Kết nối thất bại!");
            e.printStackTrace();
        }

        return null; // Không tìm thấy user
    }
}
