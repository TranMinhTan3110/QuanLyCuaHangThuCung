package service;

import dao.LoginDAO.implement.UserResposittoryImpl;
import model.entity.User;
import model.request.LoginRequest;
import model.response.LoginResponse;
import respository.userRespositorty;

public class AuthService {
    private userRespositorty userRespositorty;

    public AuthService(userRespositorty userRespositorty ) {
        this.userRespositorty = userRespositorty;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();
//        userRespositorty.printAllNhanVien();
        User user = userRespositorty.getUserWithUserNameAndPassWord(loginRequest);
        if(user == null){
            loginResponse.setMessage("Không tồn tại tài khoản");
            loginResponse.setSuccess(false);
            System.out.println(loginResponse.getMessage());
            return loginResponse;
        }
        loginResponse.setSuccess(true);
        loginResponse.setMessage("Dăng nhập thành công");
        loginResponse.setUser(user);
        System.out.println(loginResponse.getMessage());
        return  loginResponse;
    }

}
