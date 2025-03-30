package controller;

import model.entity.Role;
import model.entity.User;
import service.UserService;
import utils.RoleUtil;
import view.UserView;

import javax.swing.*;
import java.util.ArrayList;

public class UserController {
    private UserView userView;
    private UserService userService; // Sử dụng DAOUser để thao tác với database

    public UserController(UserView userView,  UserService userService) {
        this.userView = userView;
        this.userService = userService;
        initController();
        loadEmployeesFromDB();
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

    // Load danh sách nhân viên từ database và hiển thị lên bảng của userView
    private void loadEmployeesFromDB() {
        ArrayList<User> users = userService.getAll();
        for (User user : users) {
            // Sử dụng RoleUtil.formatRole() để chuyển enum Role thành chuỗi theo định dạng mong muốn (ví dụ "ADMIN" nếu cần)
            userView.addEmployeeToTable(
                    String.valueOf(user.getId()),
                    user.getName(),
                    user.getPhone(),
                    user.getAddress(),
                    user.getUsername(),
                    user.getPassword(),
                    RoleUtil.formatRole(user.getRole()));
        }
    }

    private void addEmployee() {
        try {
            int id = Integer.parseInt(userView.getEmployeeId());
            String name = userView.getEmployeeName();
            String phone = userView.getEmployeePhone();
            String address = userView.getEmployeeAddress();
            String username = userView.getEmployeeUsername();
            String password = userView.getEmployeePassword();
            String roleStr = userView.getEmployeeRole(); // Ví dụ trả về "ADMIN"

            // Sử dụng RoleUtil để chuyển đổi thành enum Role (ví dụ "ADMIN" -> "Admin")
            Role role = RoleUtil.parseRole(roleStr);

            User user = new User(id, name, phone, address, username, password, role);
            if (userService.insert(user)) {
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
            String address = userView.getEmployeeAddress();
            String username = userView.getEmployeeUsername();
            String password = userView.getEmployeePassword();
            String roleStr = userView.getEmployeeRole();
            Role role = RoleUtil.parseRole(roleStr);

            User user = new User(id, name, phone, address, username, password, role);
            if (userService.update(user)) {
                userView.updateEmployeeInTable(selectedRow, String.valueOf(id), name, phone, address, username, password, RoleUtil.formatRole(role));
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
            if (userService.delete(user)) {
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
            String address = userView.getTable().getValueAt(selectedRow, 3).toString();
            String username = userView.getTable().getValueAt(selectedRow, 4).toString();
            String password = userView.getTable().getValueAt(selectedRow, 5).toString();
            String role = userView.getTable().getValueAt(selectedRow, 6).toString();

            // Nếu userView có các setter, bạn có thể cập nhật dữ liệu vào form.
            // Ví dụ:
            // userView.setEmployeeId(id);
            // userView.setEmployeeName(name);
            // userView.setEmployeePhone(phone);
            // userView.setEmployeeAddress(address);
            // userView.setEmployeeUsername(username);
            // userView.setEmployeePassword(password);
            // userView.setEmployeeRole(role);
        }
    }
}
