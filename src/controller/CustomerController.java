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
import java.util.ArrayList;

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
        this.customerView.setAddButtonListener(e -> addCustomer());
        this.customerView.setEditButtonListener(e -> editCustomer());
        this.customerView.setDeleteButtonListener(e -> deleteCustomer());
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
            String id = customerView.getTable().getValueAt(selectedRow, 0).toString();
            String name = customerView.getTable().getValueAt(selectedRow, 1).toString();
            String phone = customerView.getTable().getValueAt(selectedRow, 2).toString();
            String address = customerView.getTable().getValueAt(selectedRow, 3).toString();
//            String Score = customerView.getTable().getValueAt(selectedRow,5).toString();
            System.out.println("DEBUG: ID=" + id + ", Name=" + name + ", Phone=" + phone + ", Address=" + address);
            customerView.setEmployeeData(id, name, phone, address);
            // Cập nhật currentCustomer từ DB dựa trên ID
            try {
                int current_id = Integer.parseInt(id);
                currentCustomer = customerService.selectedByID(current_id); // Lấy người dùng theo ID từ DB
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(userView, "ID không hợp lệ khi chọn dòng!");
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
        String name = customerView.getName_textField();
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
        int loyaltyPoints = 0;
        String membershipLevel = "Basic";
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setLoyaltyPoints(loyaltyPoints);
        customer.setMembershipLevel(membershipLevel);
        customerService.insert(customer);
        loadEmployeesFromDB();
//        if (customerService.insert(customer)) {
//            customerView.addCustomerToTable(String.valueOf(customer.getId()), name, phone, address, loyaltyPoints, membershipLevel);
//            JOptionPane.showMessageDialog(customerView, "Thêm khách hàng thành công");
//            customerView.clear();
//        } else {
//            JOptionPane.showMessageDialog(customerView, "Thêm khách hàng thất bại");
//        }

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
        Customer customer = new Customer(0, name, phone, address);
        if (customerService.update(customer)) {
            JOptionPane.showMessageDialog(customerView, "Cập nhật  khách hàng thành công");
            customerView.updateCustomerInTable(selectedRow, String.valueOf(customer.getId()), name, phone, address);
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
}


