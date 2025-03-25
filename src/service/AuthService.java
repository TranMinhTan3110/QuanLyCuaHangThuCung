package service;

import dao.LoginDAO.implement.UserResposittoryImpl;
import model.entity.User;
import model.request.LoginRequest;
import model.response.LoginResponse;
import respository.userRespositorty;

public class AuthService {
    //   private userRespositorty userRespositorty;
    private UserResposittoryImpl user;

    public AuthService(UserResposittoryImpl user) {
        this.user = user;
    }

    public boolean checkLogin(String userName, String password) {
        LoginResponse loginResponse = new LoginResponse();

        LoginRequest loginRequest = new LoginRequest(userName, password);
        User u = user.getUserWithUserNameAndPassWord(loginRequest);
        if (u == null) {
            return false;
        }
        return true;

    }
}