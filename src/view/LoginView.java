package view;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    public static Color getPrimaryColor() {
        return new Color(139, 69, 19); // Màu nâu
    }
    class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
                backgroundImage = new ImageIcon(getClass().getResource(imagePath)).getImage();
            }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (backgroundImage != null) {
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    public LoginView() {

        this.setTitle("Cửa hàng thú cưng");
        this.setSize(650, 450);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
//        this.setResizable(false);

        // Tạo icon
        ImageIcon appIcon = new ImageIcon(getClass().getResource("/view/icon_Login.png"));
        this.setIconImage(appIcon.getImage());
        // Giao diện chính
        JPanel mainPanel = new JPanel(new BorderLayout());

        // ===== Sidebar (Bên trái) =====
        JPanel sidebar = new BackgroundPanel("/view/background_Login.png");
        sidebar.setPreferredSize(new Dimension(250, 350));
        sidebar.setBackground(new Color(255, 224, 102));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        // Logo
        JLabel logoLabel = new JLabel(new ImageIcon("background_Login.png"));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(logoLabel);

        mainPanel.add(sidebar, BorderLayout.WEST);

        // ===== Form đăng nhập (Bên phải) =====
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());
        loginPanel.setPreferredSize(new Dimension(400,350));
        loginPanel.setBackground(new Color(255,255,204));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Tiêu đề
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JLabel title = new JLabel("Cửa Hàng Thú Cưng HKT");
        title.setFont(new Font("Dialog", Font.BOLD, 22));
        gbc.insets = new Insets(-10, 0, 35, 0);
        title.setForeground(getPrimaryColor());
        loginPanel.add(title, gbc);

        // Ảnh logo
        gbc.gridy = 1;
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/view/logo_Login.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        JLabel loginLogo = new JLabel(new ImageIcon(scaledImage));
        gbc.insets = new Insets(-10, 0, 30, 0);
        loginPanel.add(loginLogo, gbc);

        // Username
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblUser = new JLabel("Username :");
        lblUser.setForeground(getPrimaryColor());
        loginPanel.add(lblUser, gbc);

        gbc.gridx = 1;
        JTextField txtUser = new JTextField(15);
        loginPanel.add(txtUser, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblPass = new JLabel("Password :");
        lblPass.setForeground(getPrimaryColor());
        loginPanel.add(lblPass, gbc);

        gbc.gridx = 1;
        JPasswordField txtPass = new JPasswordField(15);
        loginPanel.add(txtPass, gbc);

        // Nút Login và Cancel
        Dimension buttonSize = new Dimension(75, 25);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;// gộp 2 button thành 1 khối
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setOpaque(false);
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBackground(Color.white);
        btnCancel.setForeground(getPrimaryColor());
        btnCancel.setBorder(new LineBorder(getPrimaryColor(), 2));

        JButton btnLogin = new JButton("Login");
        btnLogin.setBackground(Color.white);
        btnLogin.setForeground(getPrimaryColor());
        btnLogin.setBorder(new LineBorder(getPrimaryColor(), 2));

// Thêm nút vào buttonPanel
        buttonPanel.add(btnCancel);
        buttonPanel.add(btnLogin);

// Thêm panel vào loginPanel
        loginPanel.add(buttonPanel, gbc);

        btnLogin.setPreferredSize(buttonSize);
        btnCancel.setPreferredSize(buttonSize);
        // Sự kiện cho nút Cancel
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Đóng cửa sổ
            }
        });

        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // Hiển thị giao diện
        this.setContentPane(mainPanel);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginView::new);
    }
}
