package controller;

import dao.UserSession;
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
    private Notification noti;

    public LoginController(LoginView view, AuthService authService) {
        this.views = view;
        this.authService = authService;
        this.views.addLoginListener(e -> {
            getRequest();
//            checkLogin();
        }
        );
    }

    public void showLoginView() {
        views.setVisible(true);
    }

    public boolean getRequest() {
        String password = views.getPassword();
        String userName = views.getUsername();

        // Kiểm tra username và password không được bỏ trống
//        if (LoginUtil.isEmpty(userName, password)) {
//            noti = new Notification("Tài khoản và mật khẩu không được bỏ trống!");
//            return false;
//        }
//
//        // Kiểm tra username có đúng định dạng không
//        if (!LoginUtil.isValidUsername(userName)) {
//         noti =   new Notification("Tên đăng nhập phải có 5-20 ký tự, chữ, số, _, và . được phép.");
//            return false;
//        }

        // Kiểm tra độ mạnh của mật khẩu
//        if (!LoginUtil.isStrongPassword(password)) {
//        noti =  new Notification("Mật khẩu phải có ít nhất 6 ký tự, gồm một chữ hoa và một số.");
//            return false;
//        }

        // Kiểm tra thông tin tài khoản qua authService
        User check = authService.checkLogin(userName, password);

        if (check != null) {
            // Đăng nhập thành công
            System.out.println("Đăng nhập thành công!");
            UserSession.getInstance().setUser(check);
            views.setVisible(false);

            // Hiển thị giao diện chính theo quyền
            RoleUtil roleUtil = new RoleUtil();
            MainView mainView = new MainView(roleUtil.formatRole(check.getRole()));
            new MainController(mainView);
            mainView.setVisible(true);
            return true;
        } else {
            System.out.println("Đăng nhập thất bại!");
            noti = new Notification("Đăng nhập thất bại");
            return false;
        }
    }


}
