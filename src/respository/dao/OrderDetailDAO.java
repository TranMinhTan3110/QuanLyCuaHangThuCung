package respository.dao;

import model.entity.OrderDetail;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class OrderDetailDAO {
	public boolean insertOrderDetails(List<OrderDetail> details) {
		String sql = "INSERT INTO OrderDetail (orderID, productID, petID, quantity, price) VALUES (?, ?, ?, ?, ?)";

		try (Connection conn = DatabaseConnection.getConnection();
				PreparedStatement stmt = conn.prepareStatement(sql)) {

			for (OrderDetail detail : details) {
				// Chỉ cho phép 1 trong 2: productID hoặc petID khác null
				boolean hasProduct = detail.getProductID() != null;
				boolean hasPet = detail.getPetID() != null;

				if (hasProduct == hasPet) { // cả 2 null hoặc cả 2 đều có -> vi phạm ràng buộc
					System.err.println("Lỗi: Dòng OrderDetail phải có duy nhất 1 trong productID hoặc petID.");
					return false;
				}

				stmt.setInt(1, detail.getOrderID());

				if (detail.getProductID() != null) {
					stmt.setInt(2, detail.getProductID());
				} else {
					stmt.setNull(2, java.sql.Types.INTEGER);
				}

				if (detail.getPetID() != null) {
					stmt.setInt(3, detail.getPetID());
				} else {
					stmt.setNull(3, java.sql.Types.INTEGER);
				}

				stmt.setInt(4, detail.getQuantity());
				stmt.setBigDecimal(5, detail.getPrice() != null ? detail.getPrice() : BigDecimal.ZERO);

				stmt.addBatch();
			}

			stmt.executeBatch();
			return true;

		} catch (SQLException e) {
			System.err.println("Lỗi khi chèn OrderDetail: " + e.getMessage());
			return false;
		}
	}

}
