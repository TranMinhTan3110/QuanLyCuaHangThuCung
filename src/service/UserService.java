package service;

import dao.DaoInterface;
import model.entity.User;

import java.util.ArrayList;

public class UserService {
    private DaoInterface daoInterface;

    // Constructor nhận vào một repository để dễ dàng thay đổi hoặc kiểm thử
    public UserService(DaoInterface  userRepo) {
        this.daoInterface = daoInterface;
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
}
