package controller;

import model.entity.Customer;
import model.entity.Role;
import model.entity.User;
import service.CustomerService;
import model.entity.User;
import service.UserService;
import utils.RoleUtil;
import utils.inputUtil;
import view.CustomerView;
import view.MainView;
import view.UserView;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class CustomerController {
    private CustomerView customerView;
    private CustomerService customerService;
    private MainView mainView;
    private UserView userView;
    private UserService userService;
    private Customer currentCustomer;

    public CustomerController(CustomerView customerView, CustomerService customerService) {
        this.customerService = customerService;
        this.customerView = customerView;
        initController();
        loadEmployeesFromDB();
        this.customerView.setAddButtonListener(e->addCustomer());
        this.customerView.setEditButtonListener(e->editCustomer());
        this.customerView.setDeleteButtonListener(e->deleteCustomer());
        this.customerView.setSearchListener(new searchCustomer());
    }

    private void initController() {
        // Khi chọn dòng trong bảng, load dữ liệu vào form
        customerView.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && customerView.getTable().getSelectedRow() != -1) {
                loadSelectedEmployeeIntoForm();
            }
        });
    }

    private void loadSelectedEmployeeIntoForm() {
        int selectedRow = customerView.getTable().getSelectedRow();
        if (selectedRow != -1) {
            try {
                String idStr = customerView.getTable().getValueAt(selectedRow, 0).toString().trim();
                String name = customerView.getTable().getValueAt(selectedRow, 1).toString().trim();
                String phone = customerView.getTable().getValueAt(selectedRow, 2).toString().trim();
                String address = customerView.getTable().getValueAt(selectedRow, 3).toString().trim();
                String scoreStr = customerView.getTable().getValueAt(selectedRow, 4).toString().trim();

                int id = Integer.parseInt(idStr);
                int score = Integer.parseInt(scoreStr);  // Kiểm tra xem score có thực sự là số?

                // In ra để debug
                System.out.println("DEBUG: ID=" + id + ", Name=" + name + ", Phone=" + phone + ", Address=" + address + ", Score=" + score);

                customerView.setEmployeeData(idStr, name, phone, address, score);

                currentCustomer = customerService.selectedByID(id);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(customerView, "Dữ liệu số không hợp lệ (ID hoặc Score)!\nChi tiết: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Load danh sách nhân viên từ database
    private void loadEmployeesFromDB() {
        ArrayList<Customer> customers = new ArrayList<>();
        customers = customerService.getAll();
        for (Customer customer : customers) {
            customerView.addCustomerToTable(
                    String.valueOf(customer.getId()),
                    customer.getName(),
                    customer.getPhone(),
                    customer.getAddress(),
                    customerService.getPoint(customer),
                    customerService.getRank(customer));

        }
    }

    //thêm khách hàng
    public void addCustomer() {
//            String idStr =customerView.getID_textField().trim();  // Thêm .trim() ở đây
        String name = (customerView.getName_textField());
        String phone = customerView.getPhone_textField();
        String address = customerView.getAddress_textField();
//        boolean checkPhone = customerService.checkPhone(phone);
//            String idStr = String.valueOf(id);
//            int id = Integer.parseInt(idStr);
        if (name == null || name.trim().isEmpty() || name.equals("Enter Name") ||
                phone == null || phone.trim().isEmpty() || phone.equals("Enter Phone") ||
                address == null || address.trim().isEmpty() || address.equals("Enter Address")) {

            JOptionPane.showMessageDialog(customerView, "Vui lòng nhập đầy đủ thông tin Customer!");
            return;
        }
        // Kiểm tra ID hợp lệ

//            if (!inputUtil.isValidID(idStr)) {
//                JOptionPane.showMessageDialog(userView, "ID không hợp lệ! Phải là số nguyên dương.");
//                return;
//            }

        // Kiểm tra các trường bắt buộc còn lại không được để trống
        if (name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(customerView, "Tên không được để trống!");
            return;
        }
        if (phone.trim().isEmpty()) {
            JOptionPane.showMessageDialog(customerView, "Số điện thoại không được để trống!");
            return;
        }

        if (address.trim().isEmpty()) {
            JOptionPane.showMessageDialog(customerView, "Địa chỉ không được để trống!");
            return;
        }

        // Kiểm tra số điện thoại hợp lệ
        if (!inputUtil.isValidPhoneNumber(phone)) {
            JOptionPane.showMessageDialog(customerView, "Số điện thoại không hợp lệ!");
            return;
        }

        // Kiểm tra nếu ID, tên đăng nhập hoặc số điện thoại đã tồn tại trong DB
//            if (userService.isIdExists(id)) {
//                JOptionPane.showMessageDialog(userView, "ID đã tồn tại!");
//                retur
        if (customerService.checkPhone(phone)) {
            JOptionPane.showMessageDialog(customerView, "Số điện thoại đã tồn tại!");
            return;
        }
//        if (checkPhone == true) {
//            JOptionPane.showMessageDialog(customerView, "Khách hàng này đã tồn tại");
//            return;
//        }

        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setAddress(address);
        int loyaltyPoints = Integer.parseInt(customerView.getScore_textField());
        System.out.println("DEBUG: loyaltyPoints=" + loyaltyPoints);
        String membershipLevel = convertPointMembershipLV(loyaltyPoints);
        System.out.println("DEBUG: membershipLevel=" + membershipLevel);
        customer.setLoyaltyPoints(loyaltyPoints);
        customer.setMembershipLevel(membershipLevel);

        if (customerService.insert(customer)) {
            customerView.addCustomerToTable(String.valueOf(customer.getId()), name, phone, address, loyaltyPoints, membershipLevel);
            JOptionPane.showMessageDialog(customerView, "Thêm khách hàng thành công");
            customerView.clear();
        } else {
            JOptionPane.showMessageDialog(customerView, "Thêm khách hàng thất bại");
        }

    }

    //chỉnh sửa khách hàng
    public void editCustomer() {
        int selectedRow = customerView.getSeclectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(customerView, "Chọn khách hàng cần chỉnh sửa!");
            return;
        }
//            int id = Integer.parseInt(customerView.getID_textField());

        String name = customerView.getName_textField();
        String phone = customerView.getPhone_textField();
        String address = customerView.getAddress_textField();

        // Kiểm tra các trường bắt buộc còn lại không được để trống
        //name không được  để trống
        if (name.trim().isEmpty()) {
            JOptionPane.showMessageDialog(customerView, "Tên không được để trống!");
            return;
        }
        //phone không được để trống
        if (phone.trim().isEmpty()) {
            JOptionPane.showMessageDialog(customerView, "Số điện thoại không được để trống!");
            return;
        }

        //address không được để trống
        if (address.trim().isEmpty()) {
            JOptionPane.showMessageDialog(customerView, "Địa chỉ không được để trống!");
            return;
        }

        // Kiểm tra nếu phone không giống phone ban đầu thì cần check
        if (!currentCustomer.getPhone().equals(phone)) {
            if (!inputUtil.isValidPhoneNumber(phone)) {
                JOptionPane.showMessageDialog(customerView, "Số điện thoại không hơp lệ!");
                return;
            }
        }
        // Kiểm tra nếu address không giống address ban đầu thì cần check
        if (!currentCustomer.getAddress().equals(address)) {
            if (!inputUtil.isValidAddress(address)) {
                JOptionPane.showMessageDialog(customerView, "Địa chỉ không hơp lệ!");
                return;
            }
        }
        // Kiểm tra nếu name không giống name ban đầu thì cần check
        if (!currentCustomer.getName().equals(name)) {
            if (!inputUtil.isValidName(name)) {
                JOptionPane.showMessageDialog(customerView, "Tên không hơp lệ!");
                return;
            }
        }
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setAddress(address);
        int loyaltyPoints = Integer.parseInt(customerView.getScore_textField());
        System.out.println("DEBUG: loyaltyPoints=" + loyaltyPoints);
        String membershipLevel = convertPointMembershipLV(loyaltyPoints);
        System.out.println("DEBUG: membershipLevel=" + membershipLevel);
        customer.setLoyaltyPoints(loyaltyPoints);
        customer.setMembershipLevel(membershipLevel);
        int id = Integer.parseInt(customerView.getTable().getValueAt(selectedRow, 0).toString());
        customer.setId(id);
        System.out.println(customer.getId());
        if (customerService.update(customer)) {
            JOptionPane.showMessageDialog(customerView, "Cập nhật  khách hàng thành công");
            customerView.updateCustomerInTable(selectedRow, String.valueOf(customer.getId()), name, phone, address, loyaltyPoints, membershipLevel);
            customerView.getTable().clearSelection();
        } else {
            JOptionPane.showMessageDialog(customerView, "Cập nhật khách hàng không thành công");
        }

    }

    //xóa khách hàng
    public void deleteCustomer() {
        try {
            int selectedRow = customerView.getSeclectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(customerView, "Chọn khách hàng cần xóa");
                return;
            }
            {
                String idStr = (String) customerView.getTable().getValueAt(selectedRow, 0);
                int id = Integer.parseInt(idStr);
                String phone = customerView.getPhone_textField();
                String address = customerView.getAddress_textField();
                String name = customerView.getName_textField();
                Customer customer = new Customer(id, name, phone, address);
                if (customerService.delete(customer)) {
                    JOptionPane.showMessageDialog(customerView, "Xóa khách hàng thành công");
                    customerView.removeCustomerFromTable(selectedRow);
                } else {
                    JOptionPane.showMessageDialog(customerView, "Xóa khách hàng thất bại");
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(customerView, "ID không hợp lệ");
        }

    }
    //search for customer
    public class searchCustomer implements DocumentListener {

        @Override
        public void insertUpdate(DocumentEvent e) {
            search();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            search();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            search();
        }
        public void search() {
            String str = customerView.getSearch_textField().toLowerCase();
            List<Customer> customers = new ArrayList<>();
            if(str.matches("\\d+")){
               customers = customerService.searchByCustomerPhone(str);
            }else{
                customers = customerService.searchByCustomerName(str);
            }
            DefaultTableModel model = (DefaultTableModel) customerView.getCustomerTable().getModel();
            model.setRowCount(0); // Xóa các dòng cũ
            // Thêm dữ liệu mới vào bảng
            for (Customer c : customers) {
                model.addRow(new Object[]{
                        c.getId(),
                        c.getName(),
                        c.getPhone(),
                        c.getAddress(),
                        c.getLoyaltyPoints(),
                        c.getMembershipLevel()

                });
            }
        }

    }

    //Phần liên quan đến điểm

    private String convertPointMembershipLV(int loyaltyPoints) {
        String membershipLevel;
        if (loyaltyPoints >= 1000) membershipLevel = "Platinum";
        else if (loyaltyPoints >= 500) membershipLevel = "Gold";
        else if (loyaltyPoints >= 100) membershipLevel = "Silver";
        else membershipLevel = "Basic";

        return membershipLevel;
    }
}


