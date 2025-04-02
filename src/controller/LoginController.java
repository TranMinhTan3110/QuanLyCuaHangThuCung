package controller;

import model.entity.User;
import service.AuthService;
import utils.RoleUtil;
import view.LoginView;
//import view.MainView;
import view.UserView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginView views;
    private AuthService authService;
    private  UserView userView;

    public LoginController(LoginView view, AuthService authService) {
        this.views = view;
        this.authService = authService;
        this.views.addLoginListener(e -> getRequest());
    }

    public boolean getRequest() {
        String password = views.getPassword();
        String userName = views.getUsername();
        User check = authService.checkLogin(userName, password);
        if (check != null) {
//            if()
            System.out.println("Đăng nhập thành công!");
            views.setVisible(false);
            RoleUtil roleUtil = new RoleUtil();
            this.userView = new UserView(roleUtil.formatRole(check.getRole()));
            new UserController(userView);
            userView.setVisible(true);
            return true;
        } else {
            System.out.println("Đăng nhập thất bại!");
            return false;
        }
    }
}
