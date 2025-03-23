package controller;

import service.AuthService;
import view.LoginView;

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

    public void getRequest() {
        String password = views.getPassword();
        String userName = views.getUsername();
        boolean check = authService.checkLogin(userName, password);
        if (check == true) {
            System.out.println("Đăng nhập thành công!");
        } else {
            System.out.println("Đăng nhập thất bại!");
        }
    }
}
