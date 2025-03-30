package view.notificationView;

import controller.LoginController;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static view.LoginView.getPrimaryColor;

public class Notification extends JFrame {
    JButton button;

    public Notification(String notificationTitle, LoginController loginController) {
        this.setSize(400, 150);
        this.setTitle("Thông báo");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);

        // Tạo màu nền
        getContentPane().setBackground(new Color(255, 228, 181));

        // Tạo icon
        ImageIcon appIcon = new ImageIcon(getClass().getResource("/view/icon_Login.png"));
        this.setIconImage(appIcon.getImage());

        // Tạo thông báo
        JLabel notification = new JLabel(notificationTitle);
        notification.setFont(new Font("Serif", Font.BOLD, 16));
        notification.setForeground(Color.black);
        notification.setHorizontalAlignment(SwingConstants.LEFT);
        notification.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 0)); // Trên:10px, Trái:15px

        // Tạo button
        button = new JButton("OK");
        button.setFocusable(false);
        button.setBackground(Color.white);
        button.setForeground(getPrimaryColor());
        button.setBorder(new LineBorder(getPrimaryColor(), 2));
        button.setBounds(20, 30, 60, 30);

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(getPrimaryColor());  // Đổi màu nền khi hover
                button.setForeground(Color.white);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.white);  // Quay lại nền trắng khi rời chuột
                button.setForeground(getPrimaryColor());
            }
        });

        // Xử lý sự kiện khi ấn OK
        button.addActionListener(e -> {
            this.dispose(); // Đóng thông báo

            loginController.showLoginView(); // Quay lại màn hình đăng nhập
        });

        // Panel chứa button
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 228, 181));
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(100, 40)); // Đặt kích thước cố định
        panel.add(button);
        this.add(panel, BorderLayout.EAST);

        this.add(notification, BorderLayout.NORTH);
        this.setVisible(true);
    }
}
