package controller;

import dao.LoginDAO.implement.UserResposittoryImpl;
import model.entity.User;
import model.request.LoginRequest;
import model.response.LoginResponse;
import respository.userRespositorty;
import service.AuthService;
import view.LoginView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController implements ActionListener {
    private LoginView view;
    private  AuthService authService;
    public LoginController(LoginView view,  AuthService authService) {
        this.view = view;
        this.authService = authService;
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand(); // Lấy action command của button

        if ("btnLogin".equals(command)) {
            System.out.println("Nút đăng nhập được nhấn!");
            LoginResponse response = authService.login(getRequest());
            if(!response.isSuccess()){
                System.out.println(response.getMessage());
            } else {
                System.out.println(response.getMessage());
            }
        }
    }

    public LoginRequest getRequest() {
        String password = view.getPassword();
        String userName = view.getUsername();
        LoginRequest loginRequest = new LoginRequest(userName, password);
        return loginRequest;
    }
}

