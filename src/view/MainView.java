package view;

import dao.DaoInterface;
import dao.ProductDAO;
import dao.UserDAO;
import service.ProductService;
import service.UserService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane, panel, centerPanel;
    private JLabel lblEmployeeName, lblEmployeeID;
    private JButton btnPets, btnAdmin, btnCustomers, btnBills, btnLogout, btnHome, btnProduct;
    private CardLayout cardLayout;

    public MainView(String role) {
        // Cài đặt cửa sổ chính
        setTitle("PetShop");
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserView.class.getResource("/view/Icon/users_Icon.png")));
        setFont(new Font("Arial", Font.PLAIN, 14));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1200, 750);
        setLocationRelativeTo(null);

        // Khởi tạo content pane
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout());
        setContentPane(contentPane);

        // Panel bên trái
        panel = new JPanel();
        panel.setBackground(new Color(255, 255, 204));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        contentPane.add(panel, BorderLayout.WEST);

        // Thêm các nút chức năng
        btnPets = addMenuButton("Pets", "/view/Icon/pets_Icon.png", panel);
        if ("admin".equalsIgnoreCase(role)) {
            btnAdmin = addMenuButton("Admin", "/view/Icon/user1_Icon.png", panel);
        }
        btnCustomers = addMenuButton("Customers", "/view/Icon/users_Icon.png", panel);
        btnBills = addMenuButton("Bills", "/view/Icon/bill_Icon.png", panel);
        btnProduct = addMenuButton("Product", "/view/Icon/product_Icon.png", panel);
        btnHome = addMenuButton("Home", "/view/Icon/Category_Icon.png", panel);
        btnLogout = addLogoutButton(panel);

        // Ảnh đại diện người dùng
        JLabel lblUserIcon = new JLabel("");
        lblUserIcon.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/user_main_Icon.png")));
        panel.add(lblUserIcon);

        // Panel trung tâm với CardLayout
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);
        contentPane.add(centerPanel, BorderLayout.CENTER);

        // Thêm các panel con vào centerPanel
        centerPanel.add(createPetsPanel(), "Pets");
        if ("admin".equalsIgnoreCase(role)) {
            centerPanel.add(createUsersPanel(), "Admin");
        }
        centerPanel.add(createCustomersPanel(), "Customers");
        centerPanel.add(createBillingsPanel(), "Bills");
        centerPanel.add(createProductPanel(), "Product");
        centerPanel.add(createHomePanel(), "Home");
    }

    private JButton addMenuButton(String text, String iconPath, JPanel panel) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setIcon(new ImageIcon(UserView.class.getResource(iconPath)));
        button.setBackground(new Color(255, 255, 204));
        button.setBorder(null);
        button.setFocusPainted(false);
        button.setIconTextGap(20);
        addHoverEffect(button, new Color(128, 128, 100), new Color(255, 255, 204));
        panel.add(button);
        return button;
    }

    private JButton addLogoutButton(JPanel panel) {
        JButton btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Tahoma", Font.ITALIC, 14));
        btnLogout.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/logout_Icon.png")));
        btnLogout.setBackground(new Color(255, 255, 204));
        btnLogout.setBorder(null);
        btnLogout.setFocusPainted(false);
        btnLogout.setIconTextGap(20);
        addHoverEffect(btnLogout, new Color(128, 128, 100), new Color(255, 255, 204));
        panel.add(btnLogout);
        return btnLogout;
    }

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

    private JPanel createPetsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Manage Pets Panel", SwingConstants.CENTER), BorderLayout.NORTH);
        return panel;
    }

    private JPanel createUsersPanel() {
        UserView employeeView = new UserView();
        DaoInterface userRepo = new UserDAO();
        UserService userService = new UserService(userRepo);
        new controller.UserController(employeeView, userService);
        return employeeView;
    }

    private JPanel createCustomersPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Manage Customers Panel", SwingConstants.CENTER), BorderLayout.NORTH);
        return panel;
    }

    private JPanel createBillingsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Manage Billings Panel", SwingConstants.CENTER), BorderLayout.NORTH);
        return panel;
    }

    private ProductView createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Manage Products Panel", SwingConstants.CENTER), BorderLayout.NORTH);
        ProductView productView = new ProductView();
        DaoInterface productRepo = new ProductDAO();
        ProductService productService = new ProductService(productRepo);
        new controller.ProductController(productService,productView);
        return productView;
    }

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Home Panel", SwingConstants.CENTER), BorderLayout.NORTH);
        return panel;
    }

    public void addUsersListener(ActionListener listener) {
        if (btnAdmin.isVisible()) {
            btnAdmin.addActionListener(listener);
        }
    }

    public void addPetsListener(ActionListener listener) {
        btnPets.addActionListener(listener);
    }

    public void addCustomersListener(ActionListener listener) {
        btnCustomers.addActionListener(listener);
    }

    public void addBillingsListener(ActionListener listener) {
        btnBills.addActionListener(listener);
    }

    public void addProductListener(ActionListener listener) { // Gắn sự kiện cho nút Product
        btnProduct.addActionListener(listener);
    }

    public void addLogoutListener(ActionListener listener) {
        btnLogout.addActionListener(listener);
    }

    public void showPanel(String panelName) {
        cardLayout.show(centerPanel, panelName);
    }
}
