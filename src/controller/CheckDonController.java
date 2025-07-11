package controller;

import model.entity.Appointment;
import model.entity.Customer;
import model.entity.User;
import service.AppointmentService;
import view.CheckDonView;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckDonController {
    private CheckDonView view;
    private AppointmentService appointmentService;
    private SimpleDateFormat dateFormat;
    private Timer refreshTimer;
    private List<User> staffList;
    private List<Customer> customerList;

    public CheckDonController(CheckDonView view, AppointmentService appointmentService) {
        this.view = view;
        this.appointmentService = appointmentService;
        this.dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        this.refreshTimer = new Timer(3000, e -> loadAppointments());
        this.staffList = appointmentService.getAllStaff();
        this.customerList = appointmentService.getAllCustomers();
        initController();
    }

    private void initController() {
        loadAppointments();

        view.getSearchField().getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filterTable(); }
        });

        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int row = view.getTable().getSelectedRow();
                    if (row != -1) {
                        row = view.getTable().convertRowIndexToModel(row);
                        handleDoubleClick(row);
                    }
                }
            }
        });

        refreshTimer.start();
    }

    private void loadAppointments() {
        try {
            List<Appointment> appointments = appointmentService.getAllAppointments();
            Object[][] data = new Object[appointments.size()][5];
            int row = 0;

            for (Appointment app : appointments) {
                Customer customer = appointmentService.getCustomerNameById(app.getCustomerID());
                Object[] rowData = new Object[]{
                        app.getPetName(),
                        customer.getName(),
                        String.join(", ", appointmentService.getServicesForAppointment(app.getAppointmentID())),
                        dateFormat.format(app.getAppointmentDate()),
                        app.getStatus()
                };
                data[row] = rowData;
                row++;
            }

            // Lưu lại trang hiện tại trước khi cập nhật
            int currentPage = view.getCurrentPage();

            view.setAllData(data);
            view.filterTable();

            // Khôi phục lại trang sau khi cập nhật
            if (currentPage <= view.getTotalPages()) {
                view.setCurrentPage(currentPage);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                    "Lỗi khi tải danh sách đơn: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void filterTable() {
        String searchText = view.getSearchField().getText().toLowerCase();
        view.getSorter().setRowFilter(RowFilter.regexFilter("(?i)" + searchText));
    }

    private void handleDoubleClick(int row) {
        Object[] orderData = new Object[view.getColumns().length];
        for (int i = 0; i < orderData.length; i++) {
            orderData[i] = view.getTableModel().getValueAt(row, i);
        }

        CheckDonView.OrderDetailDialog dialog = view.new OrderDetailDialog(orderData);
        String currentOwnerName = orderData[1].toString(); // Lấy tên chủ hiện tại

        // Tạo danh sách ComboItem với khách hàng hiện tại ở đầu
        List<CheckDonView.CustomerComboItem> sortedCustomers = new ArrayList<>();
        CheckDonView.CustomerComboItem currentCustomer = null;

        // Chuyển đổi và sắp xếp danh sách khách hàng
        for (Customer customer : customerList) {
            CheckDonView.CustomerComboItem comboItem = view.new CustomerComboItem(
                    customer.getId(),
                    customer.getName()
            );

            if (customer.getName().equals(currentOwnerName)) {
                currentCustomer = comboItem;
                sortedCustomers.add(0, comboItem); // Thêm vào đầu danh sách
            } else {
                sortedCustomers.add(comboItem); // Thêm vào cuối danh sách
            }
        }

        // Cập nhật model cho ComboBox
        DefaultComboBoxModel<CheckDonView.CustomerComboItem> model =
                new DefaultComboBoxModel<>(sortedCustomers.toArray(new CheckDonView.CustomerComboItem[0]));
        dialog.getOwnerNameComboBox().setModel(model);

        // Set selected item là khách hàng hiện tại
        if (currentCustomer != null) {
            dialog.getOwnerNameComboBox().setSelectedItem(currentCustomer);
        }

        // Set staff names from staffList
        String[] staffNames = staffList.stream()
                .map(User::getName)
                .toArray(String[]::new);
        dialog.setStaffList(staffNames);

        dialog.setOnSaveCallback(this::updateAppointment);
        dialog.setOnDeleteCallback(this::deleteAppointment);

        dialog.setVisible(true);
    }

    private void updateAppointment(CheckDonView.OrderUpdateData updateData) {
        try {
            // Kiểm tra tồn tại cuộc hẹn
            int appointmentId = appointmentService.getAppointmentIdByInfo(
                    updateData.getPetName(),
                    updateData.getOwnerName(),
                    updateData.getAppointmentDate()
            );

            // Tìm khách hàng được chọn
            Customer selectedCustomer = customerList.stream()
                    .filter(c -> c.getName().equals(updateData.getOwnerName()))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Không tìm thấy khách hàng: " + updateData.getOwnerName()));

            // Tìm nhân viên được chọn
            User staff = staffList.stream()
                    .filter(user -> user.getName().equals(updateData.getStaffName()))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Không tìm thấy nhân viên: " + updateData.getStaffName()));

            // Tách các dịch vụ thành list
            List<String> services = Arrays.asList(updateData.getServices().split(", "));

            // Cập nhật thông tin khách hàng nếu có thay đổi
            if (updateData.isCustomerChanged()) {
                boolean customerUpdateSuccess = appointmentService.updateAppointmentCustomer(
                        appointmentId,
                        selectedCustomer.getId(),
                        updateData.getPetName()
                );
                if (!customerUpdateSuccess) {
                    throw new Exception("Không thể cập nhật thông tin khách hàng");
                }
            }

            // Cập nhật trạng thái và dịch vụ
            boolean statusUpdateSuccess = appointmentService.updateAppointmentStatus(
                    appointmentId,
                    staff.getId(),
                    updateData.getStatus(),
                    services
            );

            if (statusUpdateSuccess) {
                loadAppointments();
                JOptionPane.showMessageDialog(view,
                        "Cập nhật thông tin thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                    "Lỗi khi cập nhật: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteAppointment(CheckDonView.OrderUpdateData updateData) {
        try {
            int appointmentId = appointmentService.getAppointmentIdByInfo(
                    updateData.getPetName(),
                    updateData.getOwnerName(),
                    updateData.getAppointmentDate()
            );

            User staff = staffList.stream()
                    .filter(user -> user.getName().equals(updateData.getStaffName()))
                    .findFirst()
                    .orElseThrow(() -> new Exception("Không tìm thấy nhân viên: " + updateData.getStaffName()));

            List<String> services = Arrays.asList(updateData.getServices().split(", "));

            boolean success = appointmentService.updateAppointmentStatus(
                    appointmentId,
                    staff.getId(),
                    "Đã hủy đơn",
                    services
            );

            if (success) {
                loadAppointments();
                JOptionPane.showMessageDialog(view,
                        "Hủy cuộc hẹn thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view,
                    "Lỗi khi hủy cuộc hẹn: " + e.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void cleanup() {
        refreshTimer.stop();
    }
}