package service;
import model.entity.User;
import model.request.LoginRequest;
import model.response.LoginResponse;
import respository.userRespositorty;
import utils.LoginUtil;
import view.notificationView.Notification;

public class AuthService {
    private userRespositorty user;

    public AuthService(userRespositorty user) {
        this.user = user;
    }

    public User checkLogin(String userName, String password) {
        LoginResponse loginResponse = new LoginResponse();
        LoginRequest loginRequest = new LoginRequest(userName, password);
        User u = user.getUserWithUserNameAndPassWord(loginRequest);
        if (u == null) {
            return null;
        }
        return u;
    }
    public  boolean checkLogin2(String username, String password){
        return LoginUtil.isEmpty(username,password);
    }
}
