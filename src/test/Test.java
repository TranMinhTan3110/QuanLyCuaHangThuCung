package test;

import controller.LoginController;
import respository.userRespositorty;
import dao.LoginDAO.implement.UserResposittoryImpl;
import service.AuthService;
import view.LoginView;

public class Test {
    public static void main(String[] args) {
        userRespositorty user = new UserResposittoryImpl();
        AuthService authService = new AuthService(user);
        LoginView loginView = new LoginView(authService);
        LoginController login = new LoginController(loginView,authService);
    }
}
