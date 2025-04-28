package view.notificationView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static view.LoginView.getPrimaryColor;

public class Notification extends JFrame {
    JButton button;

    public Notification(String s) {
        this.setTitle("Thông báo");
        this.setSize(400, 180); // Kích thước đủ để chứa cả text + nút
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setLayout(null); // Sử dụng null layout để kiểm soát vị trí thủ công

        // Màu nền
        getContentPane().setBackground(new Color(255, 228, 181));

        // Icon
        ImageIcon appIcon = new ImageIcon(getClass().getResource("/view/Icon/icon_Login.png"));
        this.setIconImage(appIcon.getImage());

        // Text thông báo
        JTextArea notification = new JTextArea(s);
        notification.setFont(new Font("Serif", Font.BOLD, 19));
        notification.setForeground(Color.black);
        notification.setEditable(false);
        notification.setFocusable(false);
        notification.setLineWrap(true);
        notification.setWrapStyleWord(true);
        notification.setBackground(new Color(255, 228, 181));
        notification.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        notification.setBounds(15, 10, 360, 80); // Cố định vị trí và kích thước
        this.add(notification);

        // Nút OK
        button = new JButton("OK");
        button.setFocusable(false);
        button.setBackground(Color.white);
        button.setForeground(getPrimaryColor());
        button.setBorder(new LineBorder(getPrimaryColor(), 2));
        button.setBounds(300, 95, 70, 30); // Cố định vị trí
        button.addActionListener(e -> this.setVisible(false));
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(getPrimaryColor());
                button.setForeground(Color.white);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.white);
                button.setForeground(getPrimaryColor());
            }
        });

        this.add(button);
        this.setVisible(true);
    }

    public JButton getButton() {
        return button;
    }
}
