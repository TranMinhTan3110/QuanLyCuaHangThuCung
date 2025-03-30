package controller;

import model.entity.User;
import service.AuthService;
import utils.LoginUtil;
import utils.RoleUtil;
import view.LoginView;
import view.MainView;
import view.notificationView.Notification;

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

    public void showLoginView() {
        views.setVisible(true);
    }

    public boolean getRequest() {
        String password = views.getPassword();
        String userName = views.getUsername();

    //Kiểm tra username và password không được bỏ trống
        if (LoginUtil.isEmpty(userName, password)) {
            new Notification("Tài khoản và mật khẩu không được bỏ trống.", this);
            return false;
        }

        // Kiểm tra username có đúng định dạng không
        if (!LoginUtil.isValidUsername(userName)) {
            new Notification("Tên đăng nhập chỉ được chứa chữ và số.", this);
            return false;
        }

        // Kiểm tra độ mạnh của mật khẩu
        if (!LoginUtil.isStrongPassword(password)) {
            new Notification("Mật khẩu phải có ít nhất 6 ký tự, gồm một chữ hoa và một số.", this);
            return false;
        }
        // Kiểm tra thông tin tài khoản
        User check = authService.checkLogin(userName, password);
        if (check != null) {
            // Đăg nhập thành công
            System.out.println("Đăng nhập thành công!");
            views.setVisible(false);
            // Hiển thị giao diện chính theo quyền
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
