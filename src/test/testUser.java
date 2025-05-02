package test;

import dao.UserDAO;
import model.entity.Role;
import model.entity.User;

public class testUser {
    public static void DatabaseConnectionin(String[] args) {
        User u = new User(1,"Beo ","036471952","SG","admin123","1234567", Role.admin);
        UserDAO ud = new UserDAO();
        ud.insert(u);
    }
}
