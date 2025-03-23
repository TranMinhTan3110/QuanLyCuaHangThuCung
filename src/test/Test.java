package test;

import respository.userRespositorty;
import service.AuthService;
import view.LoginView;

public class Test {
    public static void main(String[] args) {

        AuthService authService = new AuthService(null);
        LoginView loginView = new LoginView(null,authService);

    }
}
