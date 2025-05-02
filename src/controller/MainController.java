package controller;

import dao.LoginDAO.implement.UserResposittoryImpl;
import respository.userRespositorty;
import service.AuthService;
import view.LoginView;
import view.MainView;
import view.UserView;

import java.awt.event.ActionListener;

public class MainController {
    private MainView view;

    public MainController(MainView view) {
        this.view = view;

        // Đăng ký lắng nghe sự kiện từ MainView
        this.view.addPetsListener(e -> onPetsClicked());
        this.view.addUsersListener(e -> onUsersClicked());
        this.view.addCustomersListener(e -> onCustomersClicked());
        this.view.addBillingsListener(e -> onBillingsClicked());
        this.view.addLogoutListener(e -> onLogoutClicked());
        this.view.addProductListener(e -> onProductClicked());
        this.view.addHomeListener(e -> onHomeClicked());
    }

    private void onHomeClicked() {
        System.out.println("Home button clicked!");
        view.showPanel("Home");
    }

    private void onPetsClicked() {
        System.out.println("Pets button clicked!");
        view.showPanel("Pets");
    }

    private void onUsersClicked() {
        System.out.println("Users button clicked!");
        view.showPanel("Admin");
    }

    private void onCustomersClicked() {
        System.out.println("Customers button clicked!");
        view.showPanel("Customers");
    }

    private void onBillingsClicked() {
        System.out.println("Billings button clicked!");
        view.showPanel("Billings");
    }

    private void onProductClicked() {
        System.out.println("Product button clicked!");
        view.showPanel("Product");
    }
    private void onLogoutClicked() {
        System.out.println("Logout clicked!");
        view.dispose(); // Đóng MainView hiện tại

        // Tạo LoginView và đăng ký LoginController mới để quay lại màn hình đăng nhập
        LoginView loginView = new LoginView();
        userRespositorty user = new UserResposittoryImpl();
        new LoginController(loginView,new AuthService(user));
        loginView.setVisible(true);
    }
}