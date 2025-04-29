package controller;

import model.entity.Role;
import model.entity.User;
import service.UserService;
import utils.RoleUtil;
import utils.inputUtil;
import view.UserView;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class UserController {
    private UserView userView;
    private UserService userService;
    private User currentUser;
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
        // Thêm MouseListener cho bảng
//        userView.getTable().addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                int selectedRow = userView.getTable().getSelectedRow(); // Lấy chỉ mục dòng được chọn
//                if (selectedRow != -1) {
//                        String idStr = userView.getIdField();
//                    try {
//                        int id = Integer.parseInt(idStr);
//                        currentUser = userService.selectedById(id); // Lấy người dùng theo ID
//                    } catch (NumberFormatException ex) {
//                        JOptionPane.showMessageDialog(userView, "ID không hợp lệ khi chọn dòng!");
//                    }
//                }
//            }
//        });
//    }


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
            String idStr = userView.getTable().getValueAt(selectedRow, 0).toString();
            String name = userView.getTable().getValueAt(selectedRow, 1).toString();
            String phone = userView.getTable().getValueAt(selectedRow, 2).toString();
            String username = userView.getTable().getValueAt(selectedRow, 3).toString();
            String password = userView.getTable().getValueAt(selectedRow, 4).toString();
            String address = userView.getTable().getValueAt(selectedRow, 5).toString();
            String role = userView.getTable().getValueAt(selectedRow, 6).toString();
            userView.setEmployeeData(idStr, name, phone, address, username, password, role);

            // Cập nhật currentUser từ DB dựa trên ID
            try {
                int id = Integer.parseInt(idStr);
                currentUser = userService.selectedById(id); // Lấy người dùng theo ID từ DB
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(userView, "ID không hợp lệ khi chọn dòng!");
            }
        }
    }

    private void setupListeners() {
        userView.setAddButtonListener(e -> addEmployee());
        userView.setEditButtonListener(e -> editEmployee());
        userView.setDeleteButtonListener(e -> deleteEmployee());
    }


    // Thêm nhân viên mới vào database
    public void addEmployee() {

            // Lấy thông tin từ các ô nhập liệu
//            String idStr = userView.getIdField();
            String name = userView.getNameField();
            String phone = userView.getPhoneField();
            String username = userView.getUsernameField();
            String password = userView.getPasswordField();
            String address = userView.getAddressField();
            String roleStr = userView.getRoleField();

            // Kiểm tra nếu ID là hợp lệ
            if (
                    name == null || name.trim().isEmpty() || name.equals("Enter Name") ||
                    phone == null || phone.trim().isEmpty() || phone.equals("Enter Phone") ||
                    password == null || password.trim().isEmpty() || password.equals("Enter Password") ||
                    address == null || address.trim().isEmpty() || address.equals("Enter Address") ||
                    username == null || username.trim().isEmpty() || username.equals("Enter Username")) {

                JOptionPane.showMessageDialog(userView, "Vui lòng nhập đầy đủ thông tin User");
                return;
            }



            // Kiểm tra ID hợp lệ

//            if (!inputUtil.isValidID(idStr)) {
//                JOptionPane.showMessageDialog(userView, "ID không hợp lệ! Phải là số nguyên dương.");
//                return;
//            }
//            int id = Integer.parseInt(idStr);
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
                JOptionPane.showMessageDialog(userView, "Tên đăng nhập chỉ gồm chữ, số, dấu gạch dưới và dài 3–20 ký tự!");
                return;
            }
            //Kiểm tra tên không hợp lệ
            if (!inputUtil.isValidName(name)) {
                JOptionPane.showMessageDialog(userView, "Tên không hợp lệ!");
                return;
            }
            //Kiểm tra địa chỉ không hợp lệ
            if (!inputUtil.isValidAddress(address)) {
                JOptionPane.showMessageDialog(userView, "Địa chỉ chỉ được chứa chữ, số, khoảng trắng và các ký tự , . -");
                return;
            }
            //Kiểm tra mật khẩu  không hợp lệ
            if (!inputUtil.isValidPassword(password)) {
                JOptionPane.showMessageDialog(userView, "Mật khẩu phải có ít nhất 8 ký tự và chứa chữ hoa, chữ thường, số và ký tự đặc biệt!");
                return;
            }

            // Kiểm tra nếu ID, tên đăng nhập hoặc số điện thoại đã tồn tại trong DB
//            if (userService.isIdExists(id)) {
//                JOptionPane.showMessageDialog(userView, "ID đã tồn tại!");
//                return;
//            }
            if (userService.isPhoneExists(phone)) {
                JOptionPane.showMessageDialog(userView, "Số điện thoại đã tồn tại!");
                userView.getTable().clearSelection();
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
            User user = new User(0, name, phone, address, username, password, role);
            if (userService.insert(user)) {
                userView.addUserToTable(String.valueOf(user.getId()), name, phone, username, password, address, RoleUtil.formatRole(role));
                JOptionPane.showMessageDialog(userView, "Thêm nhân viên thành công!");
                userView.clearFields();
            } else {
                JOptionPane.showMessageDialog(userView, "Thêm nhân viên thất bại!");
            }
        }
//        catch (NumberFormatException ex) {
//            JOptionPane.showMessageDialog(userView, "ID phải là số!");
//        }



    // Sửa thông tin nhân viên
    public void editEmployee() {
        if(currentUser == null){
            JOptionPane.showMessageDialog(userView,"Chọn nv can xóa");
        }
        int selectedRow = userView.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(userView, "Chọn nhân viên cần chỉnh sửa!");
            return;
        }
        try {
            String id = userView.getIdField();
            String name = userView.getNameField();
            String phone = userView.getPhoneField();
            String username = userView.getUsernameField();
            String password = userView.getPasswordField();
            String address = userView.getAddressField();
            String roleStr = userView.getRoleField();
            Role role = RoleUtil.parseRole(roleStr);
            int idStr = Integer.parseInt(id);


            if (!inputUtil.isValidID(id)) {
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
            //ko cho đổi id
            if(currentUser.getId() != idStr){
                JOptionPane.showMessageDialog(userView,"Không được phép đổi id");
                userView.getIdFieldJ().setText(String.valueOf(currentUser.getId()));
                return;
            }
            // Kiểm tra nếu userName không giống uername ban đầu thì cần check
            if (!currentUser.getUsername().equals(username)) {
                if(!inputUtil.isValidUserName(username)){
                    JOptionPane.showMessageDialog(userView,"User name không hơp lệ!");
                    return;
                }
            }

            // Kiểm tra nếu password không giống password ban đầu thì cần check
            if (!currentUser.getPassword().equals(password)) {
                if(!inputUtil.isValidPassword(password)){
                    JOptionPane.showMessageDialog(userView,"Mật khẩu không hơp lệ!");
                    return;
                }
            }
            // Kiểm tra nếu phone không giống phone ban đầu thì cần check
            if (!currentUser.getPhone().equals(phone)) {
                if(!inputUtil.isValidPhoneNumber(phone)){
                    JOptionPane.showMessageDialog(userView,"Số điện thoại không hơp lệ!");
                    return;
                }
            }
            // Kiểm tra nếu address không giống address ban đầu thì cần check
            if (!currentUser.getAddress().equals(address)) {
                if(!inputUtil.isValidAddress(address)){
                    JOptionPane.showMessageDialog(userView,"Địa chỉ không hơp lệ!");
                    return;
                }
            }
            // Kiểm tra nếu name không giống name ban đầu thì cần check
            if (!currentUser.getName().equals(name)) {
                if(!inputUtil.isValidName(name)){
                    JOptionPane.showMessageDialog(userView,"Tên không hơp lệ!");
                    return;
                }
            }

            // Chuyển đổi và kiểm tra role
//            Role role = RoleUtil.parseRole(roleStr);
            if (role == null) {
                JOptionPane.showMessageDialog(userView, "Role không hợp lệ!");
                return;
            }
            User user = new User(idStr, name, phone, address, username, password, role);
            if (userService.update(user)) {
                userView.updateUserInTable(selectedRow, String.valueOf(user.getId()), name, phone, username, password, address, RoleUtil.formatRole(role));
                JOptionPane.showMessageDialog(userView, "Cập nhật nhân viên thành công!");
                userView.clearFields();
                userView.getTable().clearSelection();
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