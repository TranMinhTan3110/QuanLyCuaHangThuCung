package view.notificationView;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

import static view.LoginView.getPrimaryColor;

public class Notification extends JFrame {
    JButton button;
    public Notification(){
        this.setSize(400,150);
        this.setTitle("Thông báo");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        //tạo màu nền
        getContentPane().setBackground(new Color(255, 228, 181));

        //tạo icon
        ImageIcon appIcon = new ImageIcon(getClass().getResource("/view/icon_Login.png"));
        this.setIconImage(appIcon.getImage());

        //tạo thông báo
        JLabel notification  = new JLabel("Đăng nhập thất bại.Vui lòng kiểm tra lại!");
        notification.setFont(new Font("Serif", Font.BOLD, 18));
        notification.setForeground(Color.black);
        notification.setHorizontalAlignment(SwingConstants.LEFT);
        notification.setBorder(BorderFactory.createEmptyBorder(10, 15, 0, 0)); // Trên:10px, Trái:15px
        //tạo button
        button = new JButton("OK");
        button.setFocusable(false);
        button.setBackground(Color.white);
        button.setForeground(getPrimaryColor());
        button.setBorder(new LineBorder(getPrimaryColor(), 2));
        button.setBounds(20,30,60,30);
        //hover
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
        //panel chứa button
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 228, 181));
        panel.setLayout(null);
        panel.setPreferredSize(new Dimension(100, 40)); // Đặt kích thước cố định
        panel.add(button);
        this.add(panel, BorderLayout.EAST);


        this.add(notification, BorderLayout.NORTH);

        this.setVisible(true);
    }

    //hàm trả về nut button để làm lắng nghe sự kiện(khaaa)
    public JButton getButton(){
        return button;
    }

    public static void main(String[] args) {
        new Notification();
    }


}