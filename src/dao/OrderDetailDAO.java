package dao;

import model.entity.OrderDetail;

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
                stmt.setInt(1, detail.getOrderID());

                // productID hoặc petID có thể null
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
                stmt.setBigDecimal(5, detail.getPrice());

                stmt.addBatch(); // thêm vào batch
            }

            stmt.executeBatch(); // thực thi batch
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
