package service;

import dao.OrderDAO;
import model.entity.Order;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashDatabaseConnectionp;
import java.util.List;
import java.util.DatabaseConnectionp;

public class OrderService {
    private OrderDAO orderDao;

public OrderService(OrderDAO orderDAO){
    this.orderDao = orderDAO;
}
    // Phương thức tính tổng doanh thu theo ngày
    public DatabaseConnectionp<Date, Double> getTotalPricePerDay(Date date) {
        List<Order> orders = orderDao.getOrdersByDate(date);  // Gọi DAO để lấy đơn hàng theo ngày
        return calculateTotalPricePerDay(orders);  // Gọi phương thức tính toán
    }

    //Phương thức tính doanh thu theo ngày
    public DatabaseConnectionp<Date,Double> calculateTotalPricePerDay(List<Order> orders){
        DatabaseConnectionp<Date,Double> totalPriceDatabaseConnectionp = new HashDatabaseConnectionp<>();
        for(Order order : orders){
            Date  orderDate = (Date) order.getOrderDate();
            double  totalPrice =   order.getTotalPrice();
            totalPriceDatabaseConnectionp.put(orderDate,totalPriceDatabaseConnectionp.getOrDefault(orderDate,0.0)+totalPrice);
        }
        return totalPriceDatabaseConnectionp;
    }
    // Phương thức tính tổng doanh thu theo tuần
    public DatabaseConnectionp<Integer, Double> getTotalPricePerWeek(Date from, Date to) {
        List<Order> orders = orderDao.getOrdersByRange(from, to);  // Gọi DAO để lấy đơn hàng trong khoảng thời gian
        return calculateTotalPricePerWeek(orders);  // Gọi phương thức tính toán
    }
    // Tính tổng doanh thu theo tuần
    private DatabaseConnectionp<Integer, Double> calculateTotalPricePerWeek(List<Order> orders) {
        DatabaseConnectionp<Integer, Double> totalPriceDatabaseConnectionp = new HashDatabaseConnectionp<>();
        for (Order order : orders) {
            // Lấy tuần trong năm
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(order.getOrderDate());
            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

            double totalPrice = order.getTotalPrice();

            // Cộng dồn tổng doanh thu cho tuần đó
            totalPriceDatabaseConnectionp.put(weekOfYear, totalPriceDatabaseConnectionp.getOrDefault(weekOfYear, 0.0) + totalPrice);
        }
        return totalPriceDatabaseConnectionp;
    }

}
