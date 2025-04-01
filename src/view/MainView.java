package view;

import dao.DaoInterface;
import dao.ProductDAO;
import dao.UserDAO;
import service.ProductService;
import service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {

    private JPanel sidebar;
    private JPanel centerPanel;
    private CardLayout cardLayout;

    private JButton btnPets;
    private JButton btnUsers;
    private JButton btnCustomers;
    private JButton btnBillings;
    private JButton btnProduct; // Thêm nút Product
    private JButton btnLogout;
    private UserService userService;

    public MainView(String role) { // 🟢 Thêm tham số role
        setTitle("Manage Pets");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // ========== Tạo sidebar bên trái ==========
        sidebar = new JPanel();
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBackground(new Color(200, 0, 100));
        sidebar.setPreferredSize(new Dimension(150, getHeight()));

        // Khởi tạo các nút
        btnPets      = new JButton("Pets");
        btnUsers     = new JButton("Users");
        btnCustomers = new JButton("Customers");
        btnBillings  = new JButton("Billings");
        btnProduct   = new JButton("Product"); // Khởi tạo nút Product
        btnLogout    = new JButton("Logout");

        // Ẩn nút Users nếu không phải admin
        if (!"admin".equalsIgnoreCase(role)) {
            btnUsers.setVisible(false);
        }

        // Thêm nút vào sidebar
        sidebar.add(Box.createVerticalStrut(20));
        sidebar.add(btnPets);
        sidebar.add(Box.createVerticalStrut(10));

        if ("admin".equalsIgnoreCase(role)) { // 🟢 Chỉ thêm Users nếu là admin
            sidebar.add(btnUsers);
            sidebar.add(Box.createVerticalStrut(10));
        }

        sidebar.add(btnCustomers);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnBillings);
        sidebar.add(Box.createVerticalStrut(10));
        sidebar.add(btnProduct); // Thêm nút Product vào sidebar
        sidebar.add(Box.createVerticalGlue());
        sidebar.add(btnLogout);
        sidebar.add(Box.createVerticalStrut(20));

        // ========== Panel trung tâm (CardLayout) ==========
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);

        // Thêm các panel con vào centerPanel
        centerPanel.add(createPetsPanel(),       "Pets");

        if ("admin".equalsIgnoreCase(role)) { // 🟢 Chỉ thêm panel Users nếu là admin
            centerPanel.add(createUsersPanel(), "Users");
        }

        centerPanel.add(createCustomersPanel(),  "Customers");
        centerPanel.add(createBillingsPanel(),   "Billings");
        centerPanel.add(createProductPanel(),    "Product"); // Thêm panel Product

        // ========== Thêm sidebar và centerPanel vào Frame ==========
        add(sidebar, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
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

    public void addUsersListener(ActionListener listener) {
        if (btnUsers.isVisible()) {
            btnUsers.addActionListener(listener);
        }
    }

    public void addPetsListener(ActionListener listener) {
        btnPets.addActionListener(listener);
    }

    public void addCustomersListener(ActionListener listener) {
        btnCustomers.addActionListener(listener);
    }

    public void addBillingsListener(ActionListener listener) {
        btnBillings.addActionListener(listener);
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
