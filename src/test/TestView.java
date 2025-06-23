package test;

import javax.swing.*;
import controller.LoginController;
import respository.dao.LoginDAO.implement.UserResposittoryImpl;
import service.AuthService;
import view.LoginView;

public class TestView {
	public static void main(String[] args) {
		// Set the Look and Feel before creating any UI components
		try {
			// Use system Look and Feel (Windows style on Windows)
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

			// Alternative: Use Nimbus Look and Feel for a modern appearance
			// UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Initialize application components
		UserResposittoryImpl user = new UserResposittoryImpl();
		AuthService authService = new AuthService(user);
		LoginView loginView = new LoginView(authService);
		LoginController login = new LoginController(loginView, authService);
	}
}