package service;

import dao.OrderDAO;
import model.entity.Order;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderService {
    private OrderDAO orderDao;

public OrderService(OrderDAO orderDAO){
    this.orderDao = orderDAO;
}
    // Phương thức tính tổng doanh thu theo ngày
    public Map<Date, Double> getTotalPricePerDay(Date date) {
        List<Order> orders = orderDao.getOrdersByDate(date);  // Gọi DAO để lấy đơn hàng theo ngày
        return calculateTotalPricePerDay(orders);  // Gọi phương thức tính toán
    }

    //Phương thức tính doanh thu theo ngày
    public Map<Date,Double> calculateTotalPricePerDay(List<Order> orders){
        Map<Date,Double> totalPriceMap = new HashMap<>();
        for(Order order : orders){
            Date  orderDate = (Date) order.getOrderDate();
            double  totalPrice =   order.getTotalPrice();
            totalPriceMap.put(orderDate,totalPriceMap.getOrDefault(orderDate,0.0)+totalPrice);
        }
        return totalPriceMap;
    }
    // Phương thức tính tổng doanh thu theo tuần
    public Map<Integer, Double> getTotalPricePerWeek(Date from, Date to) {
        List<Order> orders = orderDao.getOrdersByRange(from, to);  // Gọi DAO để lấy đơn hàng trong khoảng thời gian
        return calculateTotalPricePerWeek(orders);  // Gọi phương thức tính toán
    }
    // Tính tổng doanh thu theo tuần
    private Map<Integer, Double> calculateTotalPricePerWeek(List<Order> orders) {
        Map<Integer, Double> totalPriceMap = new HashMap<>();
        for (Order order : orders) {
            // Lấy tuần trong năm
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(order.getOrderDate());
            int weekOfYear = calendar.get(Calendar.WEEK_OF_YEAR);

            double totalPrice = order.getTotalPrice();

            // Cộng dồn tổng doanh thu cho tuần đó
            totalPriceMap.put(weekOfYear, totalPriceMap.getOrDefault(weekOfYear, 0.0) + totalPrice);
        }
        return totalPriceMap;
    }

}
