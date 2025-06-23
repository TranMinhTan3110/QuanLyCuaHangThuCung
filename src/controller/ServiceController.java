package controller;

import view.ServiceView;
import javax.swing.JOptionPane;

public class ServiceController {
    private ServiceView view;

    public ServiceController(ServiceView view) {
        this.view = view;
        initController();
    }

    private void initController() {
        view.addBookingListener(e -> {
            try {
                System.out.println("Switching to PetCarePanel");
                view.showPanel("Booking");
            } catch (Exception ex) {
                showError("Không thể mở trang đặt lịch: " + ex.getMessage());
            }
        });

        view.addViewOrdersListener(e -> {
            try {
                System.out.println("Switching to PetServicesOrdersPanel");
                view.showPanel("Check");
            } catch (Exception ex) {
                showError("Không thể mở trang xem đơn: " + ex.getMessage());
            }
        });
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(view, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }


}