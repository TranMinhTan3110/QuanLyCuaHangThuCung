package test;

//import controller.LoginController;
import controller.LoginController;
//import controller.DatabaseConnectioninController;
import respository.dao.LoginDAO.implement.UserResposittoryImpl;
import service.AuthService;
import view.LoginView;

public class TestView {
	public static void main(String[] args) {
//        SwingUtilities.invokeLater(LoginView::new);
		UserResposittoryImpl user = new UserResposittoryImpl();
		AuthService authService = new AuthService(user);
		LoginView loginView = new LoginView(authService);
		LoginController login = new LoginController(loginView, authService);
	}
}
