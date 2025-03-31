package service;

import dao.UserDAO;
import model.entity.User;

import java.util.ArrayList;

public class UserService {
        private UserDAO userDao;

        public UserService(UserDAO userDao){
            this.userDao = userDao;

        }
        public ArrayList<User> getAllUser(){
            return userDao.getAll();
        }

}
