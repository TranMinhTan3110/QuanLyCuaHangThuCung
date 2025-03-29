package controller;

import model.entity.User;
import service.AuthService;
import utils.RoleUtil;
import view.LoginView;
import view.MainView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginView views;
    private AuthService authService;

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
            // Tạo MainView và khởi tạo MainController
            RoleUtil roleUtil = new RoleUtil();
            MainView mainView = new MainView(roleUtil.formatRole(check.getRole()));
            new MainController(mainView);
            mainView.setVisible(true);
            return true;
        } else {
            System.out.println("Đăng nhập thất bại!");
            return false;
        }
    }
}
