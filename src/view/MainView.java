package view;

import dao.CustomerDao;
import dao.DaoInterface;
import dao.ProductDAO;
import dao.UserDAO;
import service.CustomerService;
import service.ProductService;
import service.UserService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JPanel panel;
    private JLabel lblEmployeeName;
    private JLabel lblEmployeeID;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JButton btnPets;
    private JButton btnAdmin ;
    private JButton btnBills;
    private JButton btnCusTomers ;
    private JButton btnProduct;
    private JButton btnHome;
    private JButton btnLogout;
    private CardLayout cardLayout;
    private JPanel centerPanel;

    private void addHoverEffect(JButton button, Color hoverColor, Color defaultColor) {
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(defaultColor);
            }
        });
    }
    private void addPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);

        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public MainView(String role) {
        panel = new JPanel();
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserView.class.getResource("/view/Icon/users_Icon.png")));
        setFont(new Font("Arial", Font.PLAIN, 14));
        setTitle("PetShop");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 750);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);
        setContentPane(contentPane);

        // SỬA Ở ĐÂY: gán cho biến class, không phải tạo biến cục bộ
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);
        centerPanel.setBounds(250, 0, 950, 720);
        contentPane.add(centerPanel);

        // Thêm các panel
        centerPanel.add(createPetsPanel(), "Pets");

        centerPanel.add(createCustomersPanel(), "Customers");
        centerPanel.add(createBillingsPanel(), "Bills");
        centerPanel.add(createHomePanel(), "Home");

        panel.setBackground(new Color(255, 255, 204));
        panel.setBounds(0, -16, 250, 750);
        panel.setLayout(null);
        contentPane.add(panel);

        btnPets = new JButton("Pets");
        btnPets.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnPets.setBackground(new Color(255, 255, 204));
        btnPets.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/pets_Icon.png")));
        btnPets.setBorder(null);
        btnPets.setFocusPainted(false);
        btnPets.setIconTextGap(20);
        btnPets.setBounds(13, 316, 173, 31);
        addHoverEffect(btnPets, new Color(128, 128, 100), new Color(255, 255, 204));
        panel.add(btnPets);

        // Nếu là admin thì mới hiển thị nút Admin
        if ("admin".equals(role)) {
            centerPanel.add(createUsersPanel(), "Admin");
            btnAdmin = new JButton("Admin");
            btnAdmin.setFont(new Font("Tahoma", Font.BOLD, 16));
            btnAdmin.setIconTextGap(20);
            btnAdmin.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/user1_Icon.png")));
            btnAdmin.setFocusPainted(false);
            btnAdmin.setBorder(null);
            btnAdmin.setBackground(new Color(255, 255, 204));
            btnAdmin.setBounds(13, 591, 173, 31);
            addHoverEffect(btnAdmin, new Color(128, 128, 100), new Color(255, 255, 204));
            panel.add(btnAdmin);
        }

        btnCusTomers = new JButton("Customers");
        btnCusTomers.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnCusTomers.setIconTextGap(20);
        btnCusTomers.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/users_Icon.png")));
        btnCusTomers.setFocusPainted(false);
        btnCusTomers.setBorder(null);
        btnCusTomers.setBackground(new Color(255, 255, 204));
        btnCusTomers.setBounds(33, 481, 173, 31);
        addHoverEffect(btnCusTomers, new Color(128, 128, 100), new Color(255, 255, 204));
        panel.add(btnCusTomers);

        btnBills = new JButton("Bills");
        btnBills.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnBills.setIconTextGap(20);
        btnBills.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/bill_Icon.png")));
        btnBills.setFocusPainted(false);
        btnBills.setBorder(null);
        btnBills.setBackground(new Color(255, 255, 204));
        btnBills.setBounds(11, 536, 173, 31);
        addHoverEffect(btnBills, new Color(128, 128, 100), new Color(255, 255, 204));
        panel.add(btnBills);

        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Tahoma", Font.ITALIC, 14));
        btnLogout.setIconTextGap(20);
        btnLogout.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/logout_Icon.png")));
        btnLogout.setFocusPainted(false);
        btnLogout.setBorder(null);
        btnLogout.setBackground(new Color(255, 255, 204));
        btnLogout.setBounds(0, 688, 112, 31);
        addHoverEffect(btnLogout, new Color(128, 128, 100), new Color(255, 255, 204));
        panel.add(btnLogout);

        JLabel lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/user_main_Icon.png")));
        lblNewLabel.setBounds(55, 81, 131, 135);
        panel.add(lblNewLabel);

        lblEmployeeName = new JLabel("Name: ");
        lblEmployeeName.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblEmployeeName.setBounds(23, 226, 163, 25);
        panel.add(lblEmployeeName);

        lblEmployeeID = new JLabel("ID: ");
        lblEmployeeID.setFont(new Font("Tahoma", Font.BOLD, 14));
        lblEmployeeID.setBounds(23, 261, 163, 25);
        panel.add(lblEmployeeID);

        btnProduct = new JButton("Product");
        btnProduct.setIconTextGap(20);
        btnProduct.setIcon(new ImageIcon(MainView.class.getResource("/view/Icon/Category_Icon.png")));
        btnProduct.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnProduct.setFocusPainted(false);
        btnProduct.setBorder(null);
        btnProduct.setBackground(new Color(255, 255, 204));
        btnProduct.setBounds(18, 426, 173, 31);
        addHoverEffect(btnProduct, new Color(128, 128, 100), new Color(255, 255, 204));
        panel.add(btnProduct);

        btnHome = new JButton("Home");
        btnHome.setIconTextGap(20);
        btnHome.setIcon(new ImageIcon(MainView.class.getResource("/view/Icon/Category_Icon.png")));
        btnHome.setFont(new Font("Tahoma", Font.BOLD, 16));
        btnHome.setFocusPainted(false);
        btnHome.setBorder(null);
        btnHome.setBackground(new Color(255, 255, 204));
        btnHome.setBounds(13,370, 173, 31);
        panel.add(btnHome);

        centerPanel.setVisible(true);

    }



    private JPanel createPetsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Manage Pets Panel", SwingConstants.CENTER), BorderLayout.NORTH);
        return panel;
    }

    private JPanel createUsersPanel() {
        System.out.println("Tạo User Panel");
        UserView employeeView = new UserView();
        DaoInterface userRepo = new UserDAO();
        UserService userService = new UserService(userRepo);
        new controller.UserController(employeeView, userService);
        return employeeView;
    }

    private JPanel createCustomersPanel() {
        System.out.println("Tạo Customer Panel");
        CustomerView customerView = new CustomerView();
        DaoInterface userRepo = new CustomerDao();
        CustomerService customerService = new CustomerService(userRepo);
        new controller.CustomerController(customerView, customerService);
        return customerView;
    }

    private JPanel createBillingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Manage Billings Panel", SwingConstants.CENTER), BorderLayout.NORTH);
        return panel;
    }

    private JPanel createHomePanel() {
//        JPanel panel = new JPanel(new BorderLayout());
        HomeView homeView = new HomeView();
        return homeView;
//        panel.add(new JLabel("Home Panel", SwingConstants.CENTER), BorderLayout.NORTH);
//        return panel;
    }

    public void addUsersListener(ActionListener listener) {
        // Kiểm tra xem btnAdmin có null không trước khi thêm ActionListener
        if (btnAdmin != null) {
            btnAdmin.addActionListener(listener);
        }
    }
    public void addPetsListener(ActionListener listener) {
        btnPets.addActionListener(listener);
    }

    public void addCustomersListener(ActionListener listener) {
        btnCusTomers.addActionListener(listener);
    }

    public void addBillingsListener(ActionListener listener) {
        btnBills.addActionListener(listener);
    }

    public void addProductListener(ActionListener listener) {
        btnProduct.addActionListener(listener);
    }

    public void addLogoutListener(ActionListener listener) {
        btnLogout.addActionListener(listener);
    }

    public void addHomeListener(ActionListener listener) {
        btnHome.addActionListener(listener);
    }

    public void showPanel(String panelName) {;
        cardLayout.show(centerPanel, panelName);
    }

    // Thêm phương thức cập nhật tên/ID
    public void setEmployeeInfo(String name, String id) {
        lblEmployeeName.setText("Name: " + name);
        lblEmployeeID.setText("ID: " + id);
    }
}
