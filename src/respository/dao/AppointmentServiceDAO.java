package respository.dao;

import model.entity.AppointmentService;
import java.sql.*;
import java.util.ArrayList;

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
}