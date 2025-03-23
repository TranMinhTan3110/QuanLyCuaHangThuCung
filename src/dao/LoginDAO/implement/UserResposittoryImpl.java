package dao.LoginDAO.implement;

import model.entity.Role;
import model.entity.User;
import model.request.LoginRequest;
import respository.userRespositorty;

import java.sql.*;

public class UserResposittoryImpl implements userRespositorty {
        private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=QuanLyCuaHangThuCung;encrypt=false";
        private static final String userName = "sa";
        private static final String password = "kha@1205";

    @Override
    public User getUserWithUserNameAndPassWord(LoginRequest loginRequest) {

        String query = "SELECT * FROM qltc.NhanVien WHERE userName = ? AND password = ?";

        try (Connection conn = DriverManager.getConnection(url, userName, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            System.out.println("Kết nối thành công!");

            // Gán giá trị vào câu lệnh SQL
            stmt.setString(1, loginRequest.getUsername());
            stmt.setString(2, loginRequest.getPassword());

            // Thực thi truy vấn
            ResultSet rs = stmt.executeQuery();

            // Nếu có dữ liệu trả về

            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setAddress(rs.getString("address"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                String role = rs.getString("role");
                Role roleEnum = StringToEnum(role);
                user.setRole(roleEnum);
                return user;
            }
            return null;
        } catch (SQLException e) {
            System.out.println("Kết nối thất bại!");
            e.printStackTrace();
        }
        return null; // Không tìm thấy user
    }
    private Role StringToEnum(String role) {
        if("admin".equals(role)) {
            return Role.admin;
        }
        if("user".equals(role)) {
            return Role.user;
        }
        return Role.user;
    }
}
