package respository.dao;

import model.entity.AppointmentService;
import respository.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentServiceDAO implements DaoInterface<AppointmentService> {

    @Override
    public boolean insert(AppointmentService as) {
        String sql = "INSERT INTO AppointmentService(appointmentID, serviceID) VALUES (?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, as.getAppointmentID());
            st.setInt(2, as.getServiceID());

            int check = st.executeUpdate();
            return check > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(AppointmentService as) {
        // Usually, composite PK tables do not have an update (only delete/insert).
        // If needed, you can implement logic to update serviceID for a given appointmentID, or vice versa.
        return false;
    }

    @Override
    public boolean delete(AppointmentService as) {
        String sql = "DELETE FROM AppointmentService WHERE appointmentID = ? AND serviceID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, as.getAppointmentID());
            st.setInt(2, as.getServiceID());

            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<AppointmentService> getAll() {
        ArrayList<AppointmentService> list = new ArrayList<>();
        String sql = "SELECT * FROM AppointmentService";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                AppointmentService as = new AppointmentService(
                        rs.getInt("appointmentID"),
                        rs.getInt("serviceID")
                );
                list.add(as);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public AppointmentService selectByID(int id) {
        // Not applicable for composite PK, but you can implement a method to get by both keys if needed.
        return null;
    }

    // Optional: get by appointmentID or serviceID
    public ArrayList<AppointmentService> getByAppointmentID(int appointmentID) {
        ArrayList<AppointmentService> list = new ArrayList<>();
        String sql = "SELECT * FROM AppointmentService WHERE appointmentID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, appointmentID);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                AppointmentService as = new AppointmentService(
                        rs.getInt("appointmentID"),
                        rs.getInt("serviceID")
                );
                list.add(as);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * Lưu nhiều dịch vụ cho một cuộc hẹn
     */
    public boolean insertServices(int appointmentId, List<String> serviceNames) {
        String sql = "INSERT INTO AppointmentService(appointmentID, serviceID) VALUES (?, ?)";
        try (Connection con = DatabaseConnection.getConnection()) {
            con.setAutoCommit(false);
            try (PreparedStatement st = con.prepareStatement(sql)) {
                for (String serviceName : serviceNames) {
                    int serviceId = getServiceIdByName(serviceName);
                    st.setInt(1, appointmentId);
                    st.setInt(2, serviceId);
                    st.addBatch();
                }

                int[] results = st.executeBatch();
                con.commit();

                // Kiểm tra xem tất cả các dịch vụ đều được thêm thành công
                for (int result : results) {
                    if (result <= 0) return false;
                }
                return true;
            } catch (SQLException e) {
                con.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Lấy ID dịch vụ từ tên dịch vụ
     */
    private int getServiceIdByName(String serviceName) throws SQLException {
        // Sửa lại tên cột từ 'name' thành 'serviceName' theo đúng schema
        String sql = "SELECT serviceID FROM Service WHERE serviceName = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, serviceName);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("serviceID");
                }
                throw new SQLException("Service not found: " + serviceName);
            }
        }
    }

    /**
     * Lấy danh sách dịch vụ của một cuộc hẹn
     */
    public List<String> getServicesByAppointmentId(int appointmentId) {
        List<String> services = new ArrayList<>();
        String sql = "SELECT s.name FROM Service s " +
                "JOIN AppointmentService aps ON s.id = aps.serviceID " +
                "WHERE aps.appointmentID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, appointmentId);
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                services.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

}