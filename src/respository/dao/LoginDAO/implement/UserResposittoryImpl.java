package respository.dao.LoginDAO.implement;

import respository.dao.DatabaseConnection;
import model.entity.User;
import model.request.LoginRequest;
import respository.userRespositorty;
import utils.RoleUtil;

import java.sql.*;

public class UserResposittoryImpl implements userRespositorty {

	@Override
	public User getUserWithUserNameAndPassWord(LoginRequest loginRequest) {

		String query = "SELECT u.id, p.name, u.username, u.password, u.roleName " + "FROM [User] u "
				+ "JOIN Person p ON u.id = p.id " + "WHERE u.username = ? AND u.password = ?";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query)) {

			stmt.setString(1, loginRequest.getUsername());
			stmt.setString(2, loginRequest.getPassword());

			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				String roleName = rs.getString("roleName");

				if ("admin".equalsIgnoreCase(roleName) || "employee".equalsIgnoreCase(roleName)) {
					User user = new User();
					RoleUtil roleUtil = new RoleUtil();

					user.setId(rs.getInt("id"));
					user.setName(rs.getString("name"));
					user.setUsername(rs.getString("username"));
					user.setPassword(rs.getString("password"));
					user.setRole(roleUtil.parseRole(roleName));
					System.out.println(user.getId() + user.getName());
					System.out.println("Đăng nhập thành công: " + user.getName() + " [" + roleName + "]");
					return user;
				} else {
					System.out.println("Tài khoản không có quyền truy cập hệ thống.");
				}
			} else {
				System.out.println("Sai tên đăng nhập hoặc mật khẩu.");
			}

		} catch (SQLException e) {
			System.out.println("Lỗi kết nối cơ sở dữ liệu:");
			e.printStackTrace();
		}

		return null;
	}
}
