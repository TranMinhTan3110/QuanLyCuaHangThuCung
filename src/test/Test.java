package test;

//import respository.userRespositorty;
import dao.LoginDAO.implement.UserResposittoryImpl;
import respository.userRespositorty;
import service.AuthService;
import view.LoginView;

public class Test {
    public static void main(String[] args) {
        userRespositorty userRepository = new UserResposittoryImpl();
        AuthService authService = new AuthService(userRepository);
        new LoginView(authService);

    }
}
