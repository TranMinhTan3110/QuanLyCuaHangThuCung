package controller;

import model.entity.Role;
import model.entity.User;
import service.UserService;
import utils.RoleUtil;
import utils.inputUtil;
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
            // Lấy thông tin từ các ô nhập liệu
            String idStr = userView.getIdField();
            String name = userView.getNameField();
            String phone = userView.getPhoneField();
            String username = userView.getUsernameField();
            String password = userView.getPasswordField();
            String address = userView.getAddressField();
            String roleStr = userView.getRoleField();

            // Kiểm tra nếu ID là hợp lệ
            if (idStr.isEmpty() || name.isEmpty() || phone.isEmpty() || password.isEmpty() || address.isEmpty()) {
                JOptionPane.showMessageDialog(userView, "Vui lòng nhập đầy đủ thông tin User");
                return;
            }

            // Kiểm tra ID hợp lệ
            int id = Integer.parseInt(idStr);
            if (!inputUtil.isValidID(idStr)) {
                JOptionPane.showMessageDialog(userView, "ID không hợp lệ! Phải là số nguyên dương.");
                return;
            }

            // Kiểm tra các trường bắt buộc còn lại không được để trống
            if (name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(userView, "Tên không được để trống!");
                return;
            }
            if (phone.trim().isEmpty()) {
                JOptionPane.showMessageDialog(userView, "Số điện thoại không được để trống!");
                return;
            }
            if (username.trim().isEmpty()) {
                JOptionPane.showMessageDialog(userView, "Tên đăng nhập không được để trống!");
                return;
            }
            if (password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(userView, "Mật khẩu không được để trống!");
                return;
            }
            if (address.trim().isEmpty()) {
                JOptionPane.showMessageDialog(userView, "Địa chỉ không được để trống!");
                return;
            }

            // Kiểm tra số điện thoại hợp lệ
            if (!inputUtil.isValidPhoneNumber(phone)) {
                JOptionPane.showMessageDialog(userView, "Số điện thoại không hợp lệ!");
                return;
            }

            // Kiểm tra tên đăng nhập hợp lệ
            if (!inputUtil.isValidUserName(username)) {
                JOptionPane.showMessageDialog(userView, "Tên đăng nhập không hợp lệ!");
                return;
            }

            // Kiểm tra nếu ID, tên đăng nhập hoặc số điện thoại đã tồn tại trong DB
            if (userService.isIdExists(id)) {
                JOptionPane.showMessageDialog(userView, "ID đã tồn tại!");
                return;
            }
            if (userService.isPhoneExists(phone)) {
                JOptionPane.showMessageDialog(userView, "Số điện thoại đã tồn tại!");
                return;
            }
            if (userService.isUsernameExists(username)) {
                JOptionPane.showMessageDialog(userView, "Tên đăng nhập đã tồn tại!");
                return;
            }

            // Chuyển đổi và kiểm tra role
            Role role = RoleUtil.parseRole(roleStr);
            if (role == null) {
                JOptionPane.showMessageDialog(userView, "Role không hợp lệ!");
                return;
            }

            // Tạo đối tượng User và thêm vào cơ sở dữ liệu
            User user = new User(id, name, phone, address, username, password, role);
            if (userService.insert(user)) {
                userView.addUserToTable(String.valueOf(id), name, phone, username, password, address, RoleUtil.formatRole(role));
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