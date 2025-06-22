package respository.dao;

import model.entity.Appointment;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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

    public int insertAndGetId(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO Appointment(customerID, appointmentDate, status, petName, note, bookingDate) " +
                "VALUES (?, ?, ?, ?, ?, GETDATE())";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            st.setInt(1, appointment.getCustomerID());
            st.setTimestamp(2, new java.sql.Timestamp(appointment.getAppointmentDate().getTime()));
            st.setString(3, "Đang đợi"); // Theo constraint CHK_Appointment_Status
            st.setString(4, appointment.getPetName());
            st.setString(5, appointment.getNote());

            int affectedRows = st.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating appointment failed, no rows affected.");
            }

            try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating appointment failed, no ID obtained.");
                }
            }
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
                        rs.getInt("staffID"),  // Add staffID
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
                        rs.getInt("staffID"),  // Add staffID
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

    public List<String> getServiceNames(int appointmentId) throws SQLException {
        List<String> services = new ArrayList<>();
        String sql = """
            SELECT s.serviceName 
            FROM Service s 
            INNER JOIN AppointmentService aps ON s.serviceID = aps.serviceID 
            WHERE aps.appointmentID = ?
            """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, appointmentId);
            try (ResultSet rs = st.executeQuery()) {
                while (rs.next()) {
                    services.add(rs.getString("serviceName"));
                }
            }
        }
        return services;
    }

    public boolean updateStatus(int appointmentId, String newStatus) throws SQLException {
        String sql = "UPDATE Appointment SET status = ?, completionDate = ? WHERE appointmentID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, newStatus);
            // Set completionDate only when status is "Hoàn thành"
            if ("Hoàn thành".equals(newStatus)) {
                st.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            } else {
                st.setNull(2, Types.TIMESTAMP);
            }
            st.setInt(3, appointmentId);

            return st.executeUpdate() > 0;
        }
    }

    public int getAppointmentIdByInfo(String petName, String customerName, String appointmentDate) throws SQLException {
        String sql = """
        SELECT a.appointmentID
        FROM Appointment a
        INNER JOIN Customer c ON a.customerID = c.id
        INNER JOIN Person p ON c.id = p.id
        WHERE LOWER(a.petName) = LOWER(?)
        AND LOWER(p.name) LIKE LOWER(?)
        AND CAST(a.appointmentDate AS DATE) = ?
        """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, petName.trim());
            st.setString(2, "%" + customerName.trim() + "%");

            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                java.util.Date utilDate = inputFormat.parse(appointmentDate);
                java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
                st.setDate(3, sqlDate);

                // Print debug info
                System.out.println("Searching for appointment with:");
                System.out.println("Pet name: " + petName);
                System.out.println("Customer name: " + customerName);
                System.out.println("Date: " + sqlDate);

                try (ResultSet rs = st.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("appointmentID");
                    }
                    // Try a more relaxed search if exact match fails
                    return findAppointmentWithRelaxedCriteria(petName, customerName, sqlDate);
                }
            } catch (ParseException e) {
                throw new SQLException("Định dạng ngày không hợp lệ. Yêu cầu định dạng: dd/MM/yyyy");
            }
        }
    }

    private int findAppointmentWithRelaxedCriteria(String petName, String customerName, java.sql.Date date) throws SQLException {
        String sql = """
        SELECT a.appointmentID, a.petName, p.name, a.appointmentDate
        FROM Appointment a
        INNER JOIN Customer c ON a.customerID = c.id
        INNER JOIN Person p ON c.id = p.id
        WHERE CAST(a.appointmentDate AS DATE) = ?
        AND (LOWER(a.petName) = LOWER(?) OR LOWER(p.name) LIKE LOWER(?))
        """;

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setDate(1, date);
            st.setString(2, petName.trim());
            st.setString(3, "%" + customerName.trim() + "%");

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    System.out.println("Found with relaxed criteria:");
                    System.out.println("Pet: " + rs.getString("petName"));
                    System.out.println("Customer: " + rs.getString("name"));
                    System.out.println("Date: " + rs.getDate("appointmentDate"));
                    return rs.getInt("appointmentID");
                }
                throw new SQLException("Không tìm thấy cuộc hẹn với thông tin: Pet=" + petName +
                        ", Customer=" + customerName + ", Date=" + date);
            }
        }
    }
    public int getCustomerIdByInfo(String name, String phone) throws SQLException {
        String sql = "SELECT c.id FROM Customer c " +
                "INNER JOIN Person p ON c.id = p.id " +
                "WHERE p.name = ? AND p.phone = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, name);
            st.setString(2, phone);

            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                throw new SQLException("Không tìm thấy khách hàng với tên: " + name);
            }
        }
    }
    public String getCustomerNameById(int customerId) {
        String sql = "SELECT p.name FROM Person p " +
                "INNER JOIN Customer c ON p.id = c.id " +
                "WHERE c.id = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, customerId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("name");
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi truy vấn tên khách hàng: " + e.getMessage());
        }
        return "Unknown";
    }


    // Thêm vào AppointmentService.java
    public boolean updateAppointment(int appointmentId, int staffId, String status) {
        // Kiểm tra status phải là một trong các giá trị hợp lệ
        if (!status.equals("Hoàn thành") && !status.equals("Đang làm") && !status.equals("Đang đợi")) {
            return false;
        }

        String sql = "UPDATE Appointment SET " +
                "staffID = ?, " +
                "status = ?, " +
                "completionDate = ? " +
                "WHERE appointmentID = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, staffId);
            stmt.setString(2, status);
            // Set completionDate if status is "Hoàn thành"
            if ("Hoàn thành".equals(status)) {
                stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            } else {
                stmt.setNull(3, Types.TIMESTAMP);
            }
            stmt.setInt(4, appointmentId);

            int result = stmt.executeUpdate();
            return result > 0;

        } catch (SQLException e) {
            System.out.println("Lỗi khi cập nhật appointment: " + e.getMessage());
            return false;
        }
    }

    // In AppointmentDAO.java
    public boolean updateAppointmentWithServices(int appointmentId, int staffId, String status, List<String> services) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            // 1. Cập nhật thông tin cuộc hẹn
            String appointmentSql = "UPDATE Appointment SET " +
                    "staffID = ?, " +
                    "status = ?, " +
                    "completionDate = ? " +  // Thêm trường completionDate
                    "WHERE appointmentID = ?";

            try (PreparedStatement stmt = conn.prepareStatement(appointmentSql)) {
                stmt.setInt(1, staffId);

                stmt.setString(2, status);

                // Set completionDate based on status
                if (status.equals("Hoàn thành") || status.equals("Đã thanh toán")) {
                    stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
                } else {
                    stmt.setNull(3, Types.TIMESTAMP);
                }

                stmt.setInt(4, appointmentId);
                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected == 0) {
                    throw new SQLException("Không tìm thấy cuộc hẹn với ID: " + appointmentId);
                }
            }

            // 2. Xóa các dịch vụ cũ
            String deleteSql = "DELETE FROM AppointmentService WHERE appointmentID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(deleteSql)) {
                stmt.setInt(1, appointmentId);
                stmt.executeUpdate();
            }

            // 3. Thêm các dịch vụ mới
            if (services != null && !services.isEmpty()) {
                String insertSql = "INSERT INTO AppointmentService(appointmentID, serviceID) VALUES (?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertSql)) {
                    for (String serviceName : services) {
                        int serviceId = getServiceIdByName(serviceName);
                        stmt.setInt(1, appointmentId);
                        stmt.setInt(2, serviceId);
                        stmt.executeUpdate();
                    }
                }
            }

            conn.commit();
            return true;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            System.out.println("Lỗi khi cập nhật appointment và services: " + e.getMessage());
            return false;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private int getServiceIdByName(String serviceName) throws SQLException {
        String sql = "SELECT serviceID FROM Service WHERE serviceName = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, serviceName);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("serviceID");
                }
                throw new SQLException("Service not found with name: " + serviceName);
            }
        }
    }

    public boolean updateAppointmentWithCustomer(int appointmentId, int newCustomerId, String petName) {
        String sql = """
        UPDATE Appointment 
        SET customerID = ?, 
            petName = ?
        WHERE appointmentID = ?""";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, newCustomerId);
            st.setString(2, petName);
            st.setInt(3, appointmentId);

            // Debug info
            System.out.println("Updating appointment:");
            System.out.println("Appointment ID: " + appointmentId);
            System.out.println("New Customer ID: " + newCustomerId);
            System.out.println("Pet Name: " + petName);

            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Update successful");
                return true;
            } else {
                System.out.println("No rows updated");
                return false;
            }

        } catch (SQLException e) {
            System.out.println("Error updating appointment: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}