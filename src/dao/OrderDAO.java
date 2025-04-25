package dao;

import model.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements DaoInterface<Order>{

    @Override
    public boolean insert(Order order) {
        // Kiểm tra null tránh lỗi
        if (order == null || order.getUser() == null || order.getCustomer() == null) {
            throw new IllegalArgumentException("Order, User, or Customer cannot be null");
        }

        String sql = "INSERT INTO [Order](userID, customerID, totalPrice, orderDate) VALUES(?,?,?,?)";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql) ){

            st.setInt(1, order.getUser().getId());
            st.setInt(2, order.getCustomer().getId());
            st.setDouble(3, order.getTotalPrice());
            st.setDate(4, new Date(order.getOrderDate().getTime()));

            int check = st.executeUpdate();
            return check > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi chèn Order: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean update(Order order) {
        String sql = "UPDATE [Order] SET userID = ?, customerID = ?, totalPrice = ?, orderDate = ? WHERE orderID = ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {
            st.setInt(1, order.getUser().getId());
            st.setInt(2, order.getCustomer().getId());
            st.setDouble(3, order.getTotalPrice());
            st.setDate(4, new Date(order.getOrderDate().getTime()));
            st.setInt(5, order.getOrderID());
            int check = st.executeUpdate();
            return check > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
}


        @Override
        public boolean delete(Order order) {
            String sql = "DELETE FROM [Order] WHERE orderID = ?";
            try (Connection con = DatabaseConnection.getConnection();
                 PreparedStatement st = con.prepareStatement(sql)) {
                st.setInt(1, order.getOrderID());
                return st.executeUpdate() > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }



    @Override
    public ArrayList<Order> getAll() {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT o.orderID, o.totalprice, o.orderDate, " +
                "u.userName, u.roleName, c.customerName " +
                "FROM [Order] o " +
                "JOIN [User] u ON o.userID = u.id " +
                "JOIN [Customer] c ON o.customerID = c.id";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql);
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                int orderID = rs.getInt("orderID");
                double totalPrice = rs.getDouble("totalprice");
                Date orderDate = rs.getDate("orderDate");

                // Tạo User với tên và role
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setRole(Role.valueOf(rs.getString("roleName"))); // Chuyển role từ String sang Enum

                // Tạo Customer với tên khách hàng
                Customer customer = new Customer();
                customer.setName(rs.getString("customerName"));

                // Tạo Order và thêm vào danh sách
                Order order = new Order(orderID, user, customer, totalPrice, orderDate);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


    @Override
    public Order selectByID(int id) {
        String sql = "SELECT o.orderID, o.totalprice, o.orderDate, " +
                "u.username, u.roleName, c.customerName " +
                "FROM [Order] o " +
                "JOIN [User] u ON o.userID = u.id " +
                "JOIN [Customer] c ON o.customerID = c.id " +
                "WHERE o.orderID = ?";

        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int orderID = rs.getInt("orderID");
                double totalPrice = rs.getDouble("totalprice");
                Date orderDate = rs.getDate("orderDate");

                // Tạo User với tên và role
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setRole(Role.valueOf(rs.getString("roleName"))); // Chuyển role từ String sang Enum

                // Tạo Customer với tên khách hàng
                Customer customer = new Customer();
                customer.setName(rs.getString("customerName"));

                // Trả về Order
                return new Order(orderID, user, customer, totalPrice, orderDate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Không tìm thấy đơn hàng
    }

    public List<Order> getOrdersByDate(Date date){
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM [Order] WHERE orderDate = ?";
        try(Connection con = DatabaseConnection.getConnection();
            PreparedStatement st = con.prepareStatement(sql)
        ){
            st.setDate(1,new java.sql.Date(date.getTime()));
            ResultSet rs = st.executeQuery();
            while(rs.next()){
                Order order = new Order();
                order.setOrderID(rs.getInt("orderID"));
                order.setOrderDate(rs.getDate("orderDate"));
                order.setTotalPrice(rs.getDouble("totalPrice"));
                orders.add(order);
            }

        }catch (SQLException e){
                e.printStackTrace();
        }
        return orders;
    }
    public ArrayList<Order> getOrdersByRange(Date from, Date to) {
        ArrayList<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM [Order] WHERE orderDate BETWEEN ? AND ?";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement st = con.prepareStatement(sql)) {

            st.setDate(1, new java.sql.Date(from.getTime()));
            st.setDate(2, new java.sql.Date(to.getTime()));
            ResultSet rs = st.executeQuery();

            while (rs.next()) {
                Order order = new Order();
                order.setOrderID(rs.getInt("orderID"));
                order.setTotalPrice(rs.getDouble("totalPrice"));
                order.setOrderDate(rs.getDate("orderDate"));
                // ... load thêm User, Customer nếu cần
                orders.add(order);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


}

