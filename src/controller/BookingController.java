package controller;

import model.entity.Customer;
import model.entity.Appointment;
import service.AppointmentService;
import view.BookingView;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BookingController {
    public static final String PENDING = "Đang đợi";
    public static final String IN_PROGRESS = "Đang làm";
    public static final String COMPLETED = "Hoàn thành";
    private BookingView view;
    private AppointmentService appointmentService;
    private List<Customer> currentCustomers;
    private Timer searchTimer;
    private boolean isUpdating = false;

    public BookingController(BookingView view, AppointmentService appointmentService) {
        this.view = view;
        this.appointmentService = appointmentService;
        this.currentCustomers = new ArrayList<>();
        this.searchTimer = new Timer(300, e -> performSearch());
        this.searchTimer.setRepeats(false);
        initController();
    }

    private void initController() {
        loadCustomersToComboBox();
        setupSearchListener();
        view.getBookAppointmentButton().addActionListener(e -> handleBooking());
    }

    private void loadCustomersToComboBox() {
        SwingUtilities.invokeLater(() -> {
            if (isUpdating) return;
            isUpdating = true;

            try {
                JComboBox<String> ownerComboBox = view.getOwnerNameComboBox();
                String currentText = ownerComboBox.getEditor().getItem().toString();

                // Refresh danh sách khách hàng từ database
                currentCustomers = appointmentService.getAllCustomers();
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

                for (Customer customer : currentCustomers) {
                    String displayText = String.format("%s - %s", customer.getName(), customer.getPhone());
                    model.addElement(displayText);
                }

                ownerComboBox.setModel(model);

                // Giữ lại text đã nhập
                if (!currentText.isEmpty()) {
                    ownerComboBox.getEditor().setItem(currentText);
                }
            } finally {
                isUpdating = false;
            }
        });
    }

    private void setupSearchListener() {
        JTextField editorComponent = (JTextField) view.getOwnerNameComboBox().getEditor().getEditorComponent();
        editorComponent.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (!isUpdating) searchTimer.restart();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (!isUpdating) searchTimer.restart();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if (!isUpdating) searchTimer.restart();
            }
        });
    }

    private void performSearch() {
        SwingUtilities.invokeLater(() -> {
            if (isUpdating) return;
            isUpdating = true;

            try {
                JComboBox<String> ownerComboBox = view.getOwnerNameComboBox();
                JTextField editor = (JTextField) ownerComboBox.getEditor().getEditorComponent();
                String keyword = editor.getText().trim();

                if (keyword.isEmpty()) {
                    loadCustomersToComboBox();
                    return;
                }

                List<Customer> searchResults;
                if (keyword.matches(".*\\d+.*")) {
                    searchResults = appointmentService.searchCustomersByPhone(keyword);
                } else {
                    searchResults = appointmentService.searchCustomersByName(keyword);
                }

                currentCustomers = searchResults;
                DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();

                for (Customer customer : searchResults) {
                    String displayText = String.format("%s - %s", customer.getName(), customer.getPhone());
                    model.addElement(displayText);
                }

                ownerComboBox.setModel(model);
                editor.setText(keyword);
                editor.setCaretPosition(keyword.length());

                if (!searchResults.isEmpty() && !ownerComboBox.isPopupVisible()) {
                    ownerComboBox.showPopup();
                }
            } finally {
                isUpdating = false;
            }
        });
    }

    private Customer getSelectedCustomer() {
        String selectedItem = view.getOwnerName();
        System.out.println("Selected item: " + selectedItem);

        if (selectedItem == null || selectedItem.isEmpty()) {
            return null;
        }

        // Debug current customers list
        System.out.println("Current customers in list:");
        for (Customer c : currentCustomers) {
            System.out.println("ID: " + c.getId() + ", Name: " + c.getName() + ", Phone: " + c.getPhone());
        }

        // Tách tên và số điện thoại
        String[] parts = selectedItem.split("-");
        if (parts.length < 2) {
            System.out.println("Invalid format");
            return null;
        }

        String name = parts[0].trim();
        String phone = parts[1].trim();
        System.out.println("Searching for - Name: " + name + ", Phone: " + phone);

        // Tìm khách hàng trong danh sách hiện tại với so sánh không phân biệt chữ hoa/thường
        for (Customer customer : currentCustomers) {
            if (customer.getName().equalsIgnoreCase(name) &&
                    customer.getPhone().trim().equals(phone.trim())) {
                System.out.println("Found customer: " + customer.getId());
                return customer;
            }
        }

        // Nếu không tìm thấy trong danh sách hiện tại, thử tìm trực tiếp từ database
        List<Customer> searchResults = appointmentService.searchCustomersByPhone(phone);
        if (!searchResults.isEmpty()) {
            Customer customer = searchResults.get(0);
            System.out.println("Found customer from database: " + customer.getId());
            return customer;
        }

        System.out.println("Customer not found");
        return null;
    }

    /**
     * Xử lý logic đặt lịch và lưu dịch vụ
     */
    private void handleBooking() {
        String petName = view.getPetName();
        Customer selectedCustomer = getSelectedCustomer();
        String date = view.getAppointmentDate();
        String note = view.getNote();
        List<String> selectedServices = view.getSelectedServices();

        System.out.println(petName + " - " + selectedCustomer + " - " + date + " - " + note + " - " + selectedServices);

        if (petName.isEmpty() || selectedCustomer == null || date.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Vui lòng điền đầy đủ thông tin",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (selectedServices.isEmpty()) {
            JOptionPane.showMessageDialog(view,
                    "Vui lòng chọn ít nhất một dịch vụ",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date appointmentDate = dateFormat.parse(date);
            Date bookingDate = new Date();

            Appointment appointment = new Appointment();
            appointment.setCustomerID(selectedCustomer.getId());
            appointment.setPetName(petName);
            appointment.setAppointmentDate(appointmentDate);
            appointment.setBookingDate(bookingDate);
            appointment.setNote(note);
            appointment.setStatus(PENDING);
            if (appointmentService.insertWithServices(appointment, selectedServices)) {
                JOptionPane.showMessageDialog(view,
                        "Đặt lịch thành công!",
                        "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                clearForm();
            } else {
                JOptionPane.showMessageDialog(view,
                        "Đặt lịch thất bại!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(view,
                    "Định dạng ngày không hợp lệ!",
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void clearForm() {
        SwingUtilities.invokeLater(() -> {
            isUpdating = true;
            try {
                view.setPetName("");
                view.setOwnerName("");
                view.setNote("");
                view.setSelectedServices(new ArrayList<>());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                view.setAppointmentDate(dateFormat.format(new Date()));
                loadCustomersToComboBox();
            } finally {
                isUpdating = false;
            }
        });
    }
}