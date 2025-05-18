//package controller;
//
//import respository.dao.BillDAO;
//import respository.dao.OrderDAO;
//import respository.dao.OrderDetailDAO;
//import model.Bill;
//import model.Order;
//import model.OrderDetail;
//import view.BillView;
//
//import javax.swing.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.math.BigDecimal;
//import java.util.List;
//
//public class CheckoutController {
//    private BillView view;
//    private OrderDAO orderDAO;
//    private OrderDetailDAO orderDetailDAO;
//    private BillDAO billDAO;
//
//    public CheckoutController(BillView view) {
//        this.view = view;
//        this.orderDAO = new OrderDAO();
//        this.orderDetailDAO = new OrderDetailDAO();
//        this.billDAO = new BillDAO();
//
//        this.view.addCheckoutListener(new CheckoutListener());
//    }
//
//    class CheckoutListener implements ActionListener {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            try {
//                // 1. Lấy dữ liệu từ View
//                int userID = view.getUserID(); // ID người tạo đơn
//                int customerID = view.getCustomerID(); // khách hàng
//                List<OrderDetail> details = view.getOrderDetails(); // danh sách sản phẩm/thú cưng
//                BigDecimal totalPrice = view.getTotalAmount();
//                String paymentMethod = view.getPaymentMethod();
//
//                // Kiểm tra đầu vào
//                if (details == null || details.isEmpty()) {
//                    JOptionPane.showMessageDialog(view, "Không có sản phẩm/thú cưng nào trong hóa đơn.");
//                    return;
//                }
//
//                // 2. Tạo đối tượng Order
//                Order order = new Order();
//                order.setUserID(userID);
//                order.setCustomerID(customerID);
//                order.setTotalPrice(totalPrice);
//
//                // 3. Lưu Order
//                int orderID = orderDAO.saveOrder(order);
//                if (orderID == -1) {
//                    JOptionPane.showMessageDialog(view, "Không thể tạo đơn hàng.");
//                    return;
//                }
//
//                // 4. Lưu OrderDetail
//                orderDetailDAO.saveOrderDetails(details, orderID);
//
//                // 5. Tạo và lưu Bill
//                Bill bill = new Bill();
//                bill.setOrderID(orderID);
//                bill.setAmount(totalPrice);
//                bill.setBillMethod(paymentMethod);
//                billDAO.saveBill(bill);
//
//                JOptionPane.showMessageDialog(view, "Thanh toán thành công!");
//                view.clearForm(); // reset form
//
//            } catch (Exception ex) {
//                ex.printStackTrace();
//                JOptionPane.showMessageDialog(view, "Lỗi khi thanh toán: " + ex.getMessage());
//            }
//        }
//    }
//}
