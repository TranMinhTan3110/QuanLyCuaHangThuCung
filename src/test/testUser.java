package test;

import dao.UserDAO;
import model.entity.Role;
import model.entity.User;

public class testUser {
    public static void main(String[] args) {
        User u = new User(1,"Tấn ","0364713952","Bình long","employee123","12346", Role.employee);
        UserDAO ud = new UserDAO();
        ud.insert(u);
    }
}
