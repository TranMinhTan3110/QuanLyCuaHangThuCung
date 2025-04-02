package controller;

import dao.LoginDAO.implement.UserResposittoryImpl;
import dao.UserDAO;
import model.entity.Role;
import model.entity.User;
import service.AuthService;
import utils.RoleUtil;
import view.LoginView;
import view.UserView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.util.ArrayList;

import static com.sun.org.apache.xerces.internal.util.DOMUtil.setVisible;

public class UserController {
    private UserView userView;
    private UserDAO userDAO; // Sử dụng DAOUser để thao tác với database

    public UserController(UserView userView) {
        this.userView = userView;
        this.userDAO = new UserDAO();
        initController();
        userView.setEmployeeInfo();
        loadEmployeesFromDB();
        //sự kiện logout
        this.userView.addLogoutActionListener(e -> handleLogout());

    }

    private void initController() {
        // Đăng ký sự kiện cho các nút
        userView.getBtnAdd().addActionListener(e -> addEmployee());
        userView.getBtnEdit().addActionListener(e -> editEmployee());
        userView.getBtnDelete().addActionListener(e -> deleteEmployee());


        // Khi chọn dòng trong bảng, load dữ liệu vào form
        userView.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && userView.getTable().getSelectedRow() != -1) {
                loadSelectedEmployeeIntoForm();
            }
        });
    }

        //logout
    public void handleLogout(){
        userView.setVisible(false);
        // Xóa thông tin đăng nhập hiện tại
//        userView.setcurrenUser(null) = null;
        userView.clearForm();

        // Khởi tạo lại LoginView và liên kết với LoginController
        LoginView loginView = new LoginView();
        UserResposittoryImpl user = new UserResposittoryImpl();
        AuthService authService = new AuthService(user); // Đảm bảo đối tượng này vẫn hoạt động
        new LoginController(loginView, authService); // Gán lại controller
        // Hiển thị màn hình đăng nhập
        loginView.setVisible(true);
    }
    // Load danh sách nhân viên từ database và hiển thị lên bảng của userView
    private void loadEmployeesFromDB() {
        ArrayList<User> users = userDAO.getAll();
        for (User user : users) {
            // Sử dụng RoleUtil.formatRole() để chuyển enum Role thành chuỗi theo định dạng mong muốn (ví dụ "ADMIN" nếu cần)
            userView.addEmployeeToTable(
                    String.valueOf(user.getId()),
                    user.getName(),
                    user.getPhone(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getAddress(),
                    RoleUtil.formatRole(user.getRole()));
        }
    }


    private void addEmployee() {
        try {
            int id = Integer.parseInt(userView.getEmployeeId());
            String name = userView.getEmployeeName();
            String phone = userView.getEmployeePhone();
            String username = userView.getEmployeeUsername();
            String password = userView.getEmployeePassword();
            String address = userView.getEmployeeAddress();
            String roleStr = userView.getEmployeeRole(); // Ví dụ trả về "ADMIN"

            // Sử dụng RoleUtil để chuyển đổi thành enum Role (ví dụ "ADMIN" -> "Admin")
            Role role = RoleUtil.parseRole(roleStr);

            User user = new User(id, name, phone, address, username, password, role);
            if (userDAO.insert(user)) {
                userView.addEmployeeToTable(String.valueOf(id), name, phone, address, username, password, RoleUtil.formatRole(role));
                JOptionPane.showMessageDialog(userView, "Thêm nhân viên thành công!");
                userView.clearForm();
            } else {
                JOptionPane.showMessageDialog(userView, "Thêm nhân viên thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(userView, "ID phải là số!");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(userView, "Lỗi chuyển đổi role: " + ex.getMessage());
        }
    }

    private void editEmployee() {
        int selectedRow = userView.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(userView, "Chọn nhân viên cần chỉnh sửa!");
            return;
        }
        try {
            int id = Integer.parseInt(userView.getEmployeeId());
            String name = userView.getEmployeeName();
            String phone = userView.getEmployeePhone();
            String username = userView.getEmployeeUsername();
            String password = userView.getEmployeePassword();
            String address = userView.getEmployeeAddress();
            String roleStr = userView.getEmployeeRole();
            Role role = RoleUtil.parseRole(roleStr);

            User user = new User(id, name, phone, address, username, password, role);
            if (userDAO.update(user)) {
                userView.updateEmployeeInTable(selectedRow, String.valueOf(id), name, phone,username , password,address , RoleUtil.formatRole(role));
                JOptionPane.showMessageDialog(userView, "Cập nhật nhân viên thành công!");
                userView.clearForm();
            } else {
                JOptionPane.showMessageDialog(userView, "Cập nhật nhân viên thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(userView, "ID phải là số!");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(userView, "Lỗi chuyển đổi role: " + ex.getMessage());
        }
    }
    private void deleteEmployee() {
        int selectedRow = userView.getTable().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(userView, "Chọn nhân viên cần xóa!");
            return;
        }
        String idStr = userView.getTable().getValueAt(selectedRow, 0).toString();
        try {
            int id = Integer.parseInt(idStr);
            User user = new User();
            user.setId(id);
            if (userDAO.delete(user)) {
                userView.removeEmployeeFromTable(selectedRow);
                JOptionPane.showMessageDialog(userView, "Xóa nhân viên thành công!");
                userView.clearForm();
            } else {
                JOptionPane.showMessageDialog(userView, "Xóa nhân viên thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(userView, "ID nhân viên không hợp lệ!");
        }
    }

    // Khi chọn dòng trong bảng, load dữ liệu vào form
    private void loadSelectedEmployeeIntoForm() {
        int selectedRow = userView.getTable().getSelectedRow();
        if (selectedRow != -1) {
            String id = userView.getTable().getValueAt(selectedRow, 0).toString();
            String name = userView.getTable().getValueAt(selectedRow, 1).toString();
            String phone = userView.getTable().getValueAt(selectedRow, 2).toString();
            String username = userView.getTable().getValueAt(selectedRow, 3).toString();
            String password = userView.getTable().getValueAt(selectedRow, 4).toString();
            String address = userView.getTable().getValueAt(selectedRow, 5).toString();
            String role = userView.getTable().getValueAt(selectedRow, 6).toString();
            userView.setEmployeeData(id, name, phone, address, username, password, role);
        }
    }
}

