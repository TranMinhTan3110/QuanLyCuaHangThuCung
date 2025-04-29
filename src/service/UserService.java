package service;

import dao.DaoInterface;
import dao.UserDAO;
import model.entity.User;
import utils.inputUtil;
import view.UserView;

import javax.swing.*;
import java.util.ArrayList;

public class UserService {
    private DaoInterface daoInterface;
    private UserView userView;
    private UserDAO userDAO;

    // Constructor nhận vào một repository để dễ dàng thay đổi hoặc kiểm thử
    public UserService(DaoInterface userRepo) {
        this.daoInterface = userRepo;
        this.userDAO = new UserDAO();
    }

    // Lấy danh sách user
    public ArrayList<User> getAll() {
        return daoInterface.getAll();
    }

    // Thêm user mới
    public boolean insert(User user) {
        return daoInterface.insert(user);
    }

    // Cập nhật thông tin user
    public boolean update(User user) {
        return daoInterface.update(user);
    }

    // Xóa user
    public boolean delete(User user) {
        return daoInterface.delete(user);
    }
    public User selectedById(int id){
        return (User) daoInterface.selectByID(id);
    }
    public boolean checkInput(String s){
        if (!inputUtil.isValidPhoneNumber(s)) {
            JOptionPane.showMessageDialog(userView, "Số điện thoại không hợp lệ! Vui lòng nhập 10 chữ số bắt đầu bằng số 0.");
            return false;
        }
        return true;
    }
    public boolean isIdExists(int id) {
        return userDAO.isIdExists(id);
    }

    public boolean isPhoneExists(String phone) {
        return userDAO.isPhoneExists(phone);
    }

    public boolean isUsernameExists(String username) {
        return userDAO.isUsernameExists(username);
    }




}
