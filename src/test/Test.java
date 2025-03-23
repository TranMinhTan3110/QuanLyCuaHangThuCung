package test;

import controller.LoginController;
import dao.LoginDAO.implement.UserResposittoryImpl;
import respository.userRespositorty;
import service.AuthService;
import view.LoginView;

public class Test {
    public static void main(String[] args) {
        UserResposittoryImpl user = new UserResposittoryImpl();
        AuthService authService = new AuthService(user);
        LoginView loginView = new LoginView(authService);
        LoginController login = new LoginController(loginView,authService);
    }
}
