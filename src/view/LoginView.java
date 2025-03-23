package view;

import controller.LoginController;
import service.AuthService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private AuthService authService;

    public LoginView(AuthService authService) { // Sửa constructor
        this.authService = authService;
        this.init();
        this.setVisible(true);
    }

    private void init() {
        setTitle("Đăng nhập");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2));

        add(new JLabel("Tài khoản:"));
        txtUsername = new JTextField();
        add(txtUsername);

        add(new JLabel("Mật khẩu:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        btnLogin = new JButton("Đăng nhập");
        btnLogin.setActionCommand("btnLogin"); // Đặt ID cho button
        add(btnLogin);
    }

    public void addLoginListener(ActionListener listener) { // Sửa tên method
        btnLogin.addActionListener(listener);
    }

    public String getUsername() {
        return txtUsername.getText();
    }

    public String getPassword() {
        return new String(txtPassword.getPassword());
    }
}
