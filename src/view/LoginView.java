package view;

import service.AuthService;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.http.WebSocket;

public class LoginView extends JFrame {
    JTextField txtUser;
    JPasswordField txtPass;
    JButton btnLogin;
    AuthService authService;
    public LoginView(AuthService authService){
        this();
        this.authService = authService;
    }

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
        this.setSize(650, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        // Tạo icon
        ImageIcon appIcon = new ImageIcon(getClass().getResource("/view/Icon/icon_Login.png"));        this.setIconImage(appIcon.getImage());
        // Giao diện chính
        JPanel mainPanel = new JPanel(new BorderLayout());

        // ===== Sidebar (Bên trái) =====
        JPanel sidebar = new BackgroundPanel("/view/Icon/background_Login.png");        sidebar.setPreferredSize(new Dimension(250, 350));
//        sidebar.setMinimumSize(new Dimension(150, 300));  // Kích thước tối thiểu
//        sidebar.setMaximumSize(new Dimension(400, 600));  // Kích thước tối đa

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
        ImageIcon originalIcon = new ImageIcon(getClass().getResource("/view/Icon/logo_Login.png"));
        Image scaledImage = originalIcon.getImage().getScaledInstance(150, 150, Image.SCALE_SMOOTH);
        JLabel loginLogo = new JLabel(new ImageIcon(scaledImage));
        gbc.insets = new Insets(-10, 0, 30, 0);
        loginPanel.add(loginLogo, gbc);


        Font biggerFont = new Font("Arial", Font.BOLD, 14); // Đặt font Arial, đậm, cỡ 18




        // Username
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel lblUser = new JLabel("Username :");
        lblUser.setFont(biggerFont);
        lblUser.setForeground(getPrimaryColor());
        loginPanel.add(lblUser, gbc);

        gbc.gridx = 1;
         txtUser = new JTextField(15);
//        txtUser.setPreferredSize(new Dimension(200, 30));
        txtUser.setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19), 2));
        //focus viền
        txtUser.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtUser.setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19), 2)); // Viền xanh khi nhập
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtUser.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Viền xám khi mất focus
            }
        });



        //
        txtUser.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(100, 100, 100), 1, true),  // Viền màu xám, bo tròn
                BorderFactory.createEmptyBorder(5, 10, 5, 5) // Padding trong ô nhập
        ));
         //tạo placehoder
        txtUser.setText("Nhập tên đăng nhập...");
        txtUser.setForeground(Color.GRAY); // Đặt màu xám khi hiển thị placeholder

        txtUser.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (txtUser.getText().equals("Nhập tên đăng nhập...")) {
                    txtUser.setText("");
                    txtUser.setForeground(Color.BLACK); // Khi nhập, đổi màu chữ thành đen
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (txtUser.getText().isEmpty()) {
                    txtUser.setText("Nhập tên đăng nhập...");
                    txtUser.setForeground(Color.GRAY); // Khi trống, đặt lại placeholder với màu xám
                }
            }
        });



        loginPanel.add(txtUser, gbc);

        // Password
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel lblPass = new JLabel("Password :");
        lblPass.setFont(biggerFont);
        lblPass.setForeground(getPrimaryColor());
        loginPanel.add(lblPass, gbc);

        gbc.gridx = 1;
        txtPass = new JPasswordField(15);
//        txtPass.setPreferredSize(new Dimension(200, 30));
        txtPass.setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19), 2));
        //focus viền
        txtPass.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtPass.setBorder(BorderFactory.createLineBorder(new Color(139, 69, 19), 2)); // Viền xanh khi nhập
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtPass.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Viền xám khi mất focus
            }
        });

        //
        txtPass.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(100, 100, 100), 1, true),  // Viền màu xám, bo tròn
                BorderFactory.createEmptyBorder(5, 10, 5, 5) // Padding trong ô nhập
        ));
        //tạo placehoder
        txtPass.setEchoChar((char) 0); // Ẩn dấu chấm tròn ban đầu
        txtPass.setText("Nhập mật khẩu...");
        txtPass.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (new String(txtPass.getPassword()).equals("Nhập mật khẩu...")) {
                    txtPass.setText("");
                    txtPass.setForeground(Color.BLACK); // Khi nhập, đổi về màu đen
                    txtPass.setEchoChar('•'); // Khi nhập, hiện dấu chấm tròn
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtPass.getPassword().length == 0) {
                    txtPass.setText("Nhập mật khẩu...");
                    txtPass.setForeground(Color.GRAY); // Placeholder mờ
                    txtPass.setEchoChar((char) 0); // Khi trống, hiển thị chữ thường
                }
            }
        });


        loginPanel.add(txtPass, gbc);

        // Nút Login và Cancel
        Dimension buttonSize = new Dimension(70, 25);

        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.gridwidth = 2;// gộp 2 button thành 1 khối
        gbc.anchor = GridBagConstraints.CENTER;

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0)); // Dịch sang phải 50px

        buttonPanel.setOpaque(false);
        JButton btnCancel = new JButton("Cancel");
        btnCancel.setBackground(Color.white);
        btnCancel.setForeground(getPrimaryColor());
        btnCancel.setBorder(new LineBorder(getPrimaryColor(), 2));
        //tắt focus
        btnCancel.setFocusable(false);
        //hiệu ứng khi hover
        btnCancel.addMouseListener(new  java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCancel.setBackground(getPrimaryColor());
                btnCancel.setForeground(Color.white);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCancel.setBackground(Color.white);
                btnCancel.setForeground(getPrimaryColor());
            }
        });

        btnLogin = new JButton("Login");
        btnLogin.setBackground(Color.white);
        btnLogin.setForeground(getPrimaryColor());
        btnLogin.setBorder(new LineBorder(getPrimaryColor(), 2));
        //tắt focus
        btnLogin.setFocusable(false);
        //hiệu ứng khi hover
        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(getPrimaryColor());  // Đổi màu nền khi hover
                btnLogin.setForeground(Color.white);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(Color.white);  // Quay lại nền trắng khi rời chuột
                btnLogin.setForeground(getPrimaryColor());
            }
        });
// Thêm hai nút vào buttonPanel
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
                dispose();
                setDefaultCloseOperation(EXIT_ON_CLOSE);
                System.exit(0);
                // Đóng cửa sổ
            }
        });

        mainPanel.add(loginPanel, BorderLayout.CENTER);

        // Hiển thị giao diện
        this.setContentPane(mainPanel);
        this.setVisible(true);
    }
    public String getUsername() {
        return txtUser.getText();
    }

    // Phương thức lấy Password
    public String getPassword() {
        return new String(txtPass.getPassword());
    }

    //phương thức lắng nghe  sự kiện
    public void addLoginListener(ActionListener listener){
        btnLogin.addActionListener(listener);
    }

}
