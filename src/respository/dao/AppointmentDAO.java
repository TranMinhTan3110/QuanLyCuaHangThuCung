package respository.dao;

import model.entity.Appointment;
import java.sql.*;
import java.util.ArrayList;

public class AppointmentDAO implements DaoInterface<Appointment> {

    @Override
    public boolean insert(Appointment appointment) {
        String sql = "INSERT INTO Appointment(customerID, petName, bookingDate, completionDate, appointmentDate, note, status) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            st.setInt(1, appointment.getCustomerID());
            st.setString(2, appointment.getPetName());
            st.setTimestamp(3, appointment.getBookingDate() != null ? new Timestamp(appointment.getBookingDate().getTime()) : new Timestamp(System.currentTimeMillis()));
            if (appointment.getCompletionDate() != null) {
                st.setTimestamp(4, new Timestamp(appointment.getCompletionDate().getTime()));
            } else {
                st.setNull(4, Types.TIMESTAMP);
            }
            st.setTimestamp(5, new Timestamp(appointment.getAppointmentDate().getTime()));
            st.setString(6, appointment.getNote());
            st.setString(7, appointment.getStatus());

            int check = st.executeUpdate();
            if (check > 0) {
                try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newID = generatedKeys.getInt(1);
                        appointment.setAppointmentID(newID);
                    }
                }
            }
            return check > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Appointment appointment) {
        String sql = "UPDATE Appointment SET customerID = ?, petName = ?, bookingDate = ?, completionDate = ?, appointmentDate = ?, note = ?, status = ? WHERE appointmentID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, appointment.getCustomerID());
            st.setString(2, appointment.getPetName());
            st.setTimestamp(3, appointment.getBookingDate() != null ? new Timestamp(appointment.getBookingDate().getTime()) : new Timestamp(System.currentTimeMillis()));
            if (appointment.getCompletionDate() != null) {
                st.setTimestamp(4, new Timestamp(appointment.getCompletionDate().getTime()));
            } else {
                st.setNull(4, Types.TIMESTAMP);
            }
            st.setTimestamp(5, new Timestamp(appointment.getAppointmentDate().getTime()));
            st.setString(6, appointment.getNote());
            st.setString(7, appointment.getStatus());
            st.setInt(8, appointment.getAppointmentID());

            int check = st.executeUpdate();
            return check > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Appointment appointment) {
        String sql = "DELETE FROM Appointment WHERE appointmentID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, appointment.getAppointmentID());
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Appointment> getAll() {
        ArrayList<Appointment> list = new ArrayList<>();
        String sql = "SELECT * FROM Appointment";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                Appointment a = new Appointment(
                        rs.getInt("appointmentID"),
                        rs.getInt("customerID"),
                        rs.getString("petName"),
                        rs.getTimestamp("bookingDate"),
                        rs.getTimestamp("completionDate"),
                        rs.getTimestamp("appointmentDate"),
                        rs.getString("note"),
                        rs.getString("status")
                );
                list.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Appointment selectByID(int id) {
        String sql = "SELECT * FROM Appointment WHERE appointmentID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Appointment(
                        rs.getInt("appointmentID"),
                        rs.getInt("customerID"),
                        rs.getString("petName"),
                        rs.getTimestamp("bookingDate"),
                        rs.getTimestamp("completionDate"),
                        rs.getTimestamp("appointmentDate"),
                        rs.getString("note"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}