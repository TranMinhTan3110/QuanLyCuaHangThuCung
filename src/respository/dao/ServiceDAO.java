package respository.dao;

import model.entity.Service;
import java.sql.*;
import java.util.ArrayList;

public class ServiceDAO implements DaoInterface<Service> {

    @Override
    public boolean insert(Service service) {
        String sql = "INSERT INTO Service(serviceName, description, price) VALUES (?, ?, ?)";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            st.setString(1, service.getServiceName());
            st.setString(2, service.getDescription());
            st.setDouble(3, service.getPrice());

            int check = st.executeUpdate();
            if (check > 0) {
                try (ResultSet generatedKeys = st.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int newID = generatedKeys.getInt(1);
                        service.setServiceID(newID);
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
    public boolean update(Service service) {
        String sql = "UPDATE Service SET serviceName = ?, description = ?, price = ? WHERE serviceID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setString(1, service.getServiceName());
            st.setString(2, service.getDescription());
            st.setDouble(3, service.getPrice());
            st.setInt(4, service.getServiceID());

            int check = st.executeUpdate();
            return check > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(Service service) {
        String sql = "DELETE FROM Service WHERE serviceID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, service.getServiceID());
            int rowsAffected = st.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public ArrayList<Service> getAll() {
        ArrayList<Service> services = new ArrayList<>();
        String sql = "SELECT * FROM Service";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {

            while (rs.next()) {
                Service service = new Service(
                        rs.getInt("serviceID"),
                        rs.getString("serviceName"),
                        rs.getString("description"),
                        rs.getDouble("price")
                );
                services.add(service);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }

    @Override
    public Service selectByID(int id) {
        String sql = "SELECT * FROM Service WHERE serviceID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return new Service(
                        rs.getInt("serviceID"),
                        rs.getString("serviceName"),
                        rs.getString("description"),
                        rs.getDouble("price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getPriceByName(String serviceName) {
        String sql = "SELECT price FROM Service WHERE serviceName = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setString(1, serviceName);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {
                return rs.getDouble("price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }
    // Add more methods as needed, e.g., selectByNameLike, isServiceExists, etc.
}