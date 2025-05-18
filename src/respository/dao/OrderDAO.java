package respository.dao;

import model.entity.Order;
import respository.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;

public class OrderDAO {
	public int insert(Order order) throws SQLException {
		int generatedID = -1;
		Connection conn = DatabaseConnection.getConnection();
		String sql = "INSERT INTO [Order](userID, customerID, totalPrice, orderDate) VALUES (?, ?, ?, ?)";

		try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, order.getUserID());
			ps.setInt(2, order.getCustomerID()); // Sửa lỗi ở đây
			ps.setDouble(3, order.getTotalPrice());
			ps.setDate(4, new java.sql.Date(order.getOrderDate().getTime()));

			ps.executeUpdate();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				generatedID = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}

		return generatedID;
	}

	public ArrayList<Order> getAll() {
		ArrayList<Order> list = new ArrayList<>();
		// Bổ sung nếu cần
		return list;
	}
}
