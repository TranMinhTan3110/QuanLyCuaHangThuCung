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
    private UserService userService;

    public UserController(UserView userView, UserService userService) {
        this.userView = userView;
        this.userService = userService;
        setupListeners();
        initController();
        loadEmployeesFromDB();
    }

    private void initController() {
        // Khi chọn dòng trong bảng, load dữ liệu vào form
        userView.getTable().getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && userView.getTable().getSelectedRow() != -1) {
                loadSelectedEmployeeIntoForm();
            }
        });
    }

    // Load danh sách nhân viên từ database
    private void loadEmployeesFromDB() {
        ArrayList<User> users = userService.getAll();
        for (User user : users) {
            userView.addUserToTable(
                    String.valueOf(user.getId()),
                    user.getName(),
                    user.getPhone(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getAddress(),
                    RoleUtil.formatRole(user.getRole())
            );
        }
    }

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
    private void setupListeners() {
        userView.setAddButtonListener(e -> addEmployee());
        userView.setEditButtonListener(e -> editEmployee());
        userView.setDeleteButtonListener(e -> deleteEmployee());
    }

    // Thêm nhân viên mới vào database
    public void addEmployee() {
        try {
            int id = Integer.parseInt(userView.getIdField());
            String name = userView.getNameField();
            String phone = userView.getPhoneField();
            String username = userView.getUsernameField();
            String password = userView.getPasswordField();
            String address = userView.getAddressField();
            String roleStr = userView.getRoleField();
            Role role = RoleUtil.parseRole(roleStr);

            User user = new User(id, name, phone, address, username, password, role);
            if (userService.insert(user)) {
                userView.addUserToTable(
                        String.valueOf(id), name, phone, username, password, address, RoleUtil.formatRole(role)
                );
                JOptionPane.showMessageDialog(userView, "Thêm nhân viên thành công!");
                userView.clearFields();
            } else {
                JOptionPane.showMessageDialog(userView, "Thêm nhân viên thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(userView, "ID phải là số!");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(userView, "Lỗi chuyển đổi role: " + ex.getMessage());
        }
    }

    // Sửa thông tin nhân viên
    public void editEmployee() {
        int selectedRow = userView.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(userView, "Chọn nhân viên cần chỉnh sửa!");
            return;
        }
        try {
            int id = Integer.parseInt(userView.getIdField());
            String name = userView.getNameField();
            String phone = userView.getPhoneField();
            String username = userView.getUsernameField();
            String password = userView.getPasswordField();
            String address = userView.getAddressField();
            String roleStr = userView.getRoleField();
            Role role = RoleUtil.parseRole(roleStr);

            User user = new User(id, name, phone, address, username, password, role);
            if (userService.update(user)) {
                userView.updateUserInTable(selectedRow, String.valueOf(id), name, phone, username, password, address, RoleUtil.formatRole(role));
                JOptionPane.showMessageDialog(userView, "Cập nhật nhân viên thành công!");
                userView.clearFields();
            } else {
                JOptionPane.showMessageDialog(userView, "Cập nhật nhân viên thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(userView, "ID phải là số!");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(userView, "Lỗi chuyển đổi role: " + ex.getMessage());
        }
    }

    // Xóa nhân viên khỏi database
    public void deleteEmployee() {
        int selectedRow = userView.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(userView, "Chọn nhân viên cần xóa!");
            return;
        }
        String idStr = userView.getSelectedUserId();
        try {
            int id = Integer.parseInt(idStr);
            User user = new User();
            user.setId(id);
            if (userService.delete(user)) {
                userView.removeUserFromTable(selectedRow);
                JOptionPane.showMessageDialog(userView, "Xóa nhân viên thành công!");
                userView.clearFields();
            } else {
                JOptionPane.showMessageDialog(userView, "Xóa nhân viên thất bại!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(userView, "ID nhân viên không hợp lệ!");
        }
    }
}
