package controller;

import model.entity.Customer;
import model.entity.Role;
import model.entity.User;
import service.CustomerService;
import service.UserService;
import utils.RoleUtil;
import view.CustomerView;
import view.MainView;
import view.UserView;

import javax.swing.*;
import java.util.ArrayList;

public class CustomerController {
    private CustomerView customerView;
    private CustomerService customerService;
    private MainView mainView;

    public CustomerController(CustomerView customerView, CustomerService customerService) {
        this.customerService = customerService;
        this.customerView = customerView;
        initController();
        loadEmployeesFromDB();
        this.customerView.setAddButtonListener(e->addCustomer());
        this.customerView.setEditButtonListener(e->editCustomer());
        this.customerView.setDeleteButtonListener(e->deleteCustomer());
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
            String phone = customerView.getTable().getValueAt(selectedRow, 3).toString();
            String address = customerView.getTable().getValueAt(selectedRow, 2).toString();
            String Score = customerView.getTable().getValueAt(selectedRow,5).toString();
            customerView.setEmployeeData(id,name,address,phone, Integer.parseInt(Score));
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
    public void addCustomer(){
        try {
            int id = Integer.parseInt(customerView.getID_textField().trim());  // Thêm .trim() ở đây
            System.out.println("ID nhập vào: '" + id + "'");
            String name = (customerView.getName_textField());
            String phone = customerView.getPhone_textField();
            String address = customerView.getAddress_textField();
            int loyaltyPoints = customerService.getPoint();
            String membershipLevel = customerService.getRank();
            Customer customer = new Customer(id, name, phone, address, loyaltyPoints,membershipLevel);
            if (customerService.insert(customer)) {
                customerView.addCustomerToTable(String.valueOf(id), name, address, phone, loyaltyPoints,membershipLevel);
                JOptionPane.showMessageDialog(customerView, "Thêm khách hàng thành công");
                customerView.clear();
            } else {
                JOptionPane.showMessageDialog(customerView, "Thêm khách hàng thất bại");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(customerView, "ID phải là số!");
        }
    }
    //chỉnh sửa khách hàng
    public void editCustomer(){

    }
    //xóa khách hàng
    public void deleteCustomer(){

    }
}


