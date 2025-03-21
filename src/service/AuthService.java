package service;

import model.entity.Role;
import model.entity.User;
import model.request.LoginRequest;
import model.response.LoginResponse;
import respository.userResponsitorty;

import java.sql.*;

public class AuthService {
    private userResponsitorty  userResponsitorty;

    public AuthService(userResponsitorty userResponsitorty) {
        this.userResponsitorty = userResponsitorty;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
        User user = userResponsitorty.getUserWithUserNameAndPassWord(loginRequest);
        if(user == null){
            loginResponse.setMessage("Không tồn tại tài khoản");
            loginResponse.setSuccess(false);
            return loginResponse;
        }
        loginResponse.setSuccess(true);
        loginResponse.setMessage("Dăng nhập thành công");
        loginResponse.setUser(user);
        return  loginResponse;

    }

}
