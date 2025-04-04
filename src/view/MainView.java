package view;

import dao.DaoInterface;
import dao.ProductDAO;
import dao.UserDAO;
import service.ProductService;
import service.UserService;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel panel;
    private JLabel lblEmployeeName;
    private JLabel lblEmployeeID;

    // Thêm các button ở cấp lớp để dùng ở các phương thức khác
    private JButton btnPets;
    private JButton btnAdmin;
    private JButton btnCustomers;
    private JButton btnBills;
    private JButton btnLogout;
    private JButton btnProduct;
    private JButton btnHome;

    private JPanel centerPanel;
    private CardLayout cardLayout;

    public MainView(String role) {
        setIconImage(Toolkit.getDefaultToolkit().getImage(UserView.class.getResource("/view/Icon/users_Icon.png")));
        setTitle("PetShop");
        setBounds(100, 100, 1200, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        panel = new JPanel();
        panel.setBackground(new Color(255, 255, 204));
        panel.setBounds(0, 0, 250, 750);
        contentPane.add(panel);
        panel.setLayout(null);

        // Các nút điều hướng
        btnPets = createButton("Pets", "/view/Icon/pets_Icon.png", 316);
        panel.add(btnPets);

        // Kiểm tra role, nếu là "admin" thì hiển thị nút Admin
        if ("admin".equals(role)) {
            btnAdmin = createButton("Admin", "/view/Icon/user1_Icon.png", 371);
            panel.add(btnAdmin);
        }
        btnProduct = createButton("Product", "/view/Icon/Category_Icon.png", 426);
        panel.add(btnProduct);

        btnCustomers = createButton("Customers", "/view/Icon/users_Icon.png", 481);
        panel.add(btnCustomers);

        btnBills = createButton("Bills", "/view/Icon/bill_Icon.png", 536);
        panel.add(btnBills);

        btnHome = createButton("Home", "/view/Icon/Category_Icon.png", 582);
        panel.add(btnHome);

        btnLogout = createButton("Logout", "/view/Icon/logout_Icon.png", 688);
        btnLogout.setFont(new Font("Tahoma", Font.ITALIC, 14));
        panel.add(btnLogout);

        JLabel lblNewLabel = new JLabel();
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

        // Center panel + card layout
        cardLayout = new CardLayout();
        centerPanel = new JPanel(cardLayout);
        centerPanel.setBounds(250, 0, 950, 750);
        contentPane.add(centerPanel);

        // Thêm các panel vào card layout
        centerPanel.add(createHomePanel(), "home");
        centerPanel.add(createPetsPanel(), "pets");
        centerPanel.add(createUsersPanel(), "admin");
        centerPanel.add(createCustomersPanel(), "customers");
        centerPanel.add(createBillingsPanel(), "bills");

        // Product panel tạm thời
        JPanel productPanel = new JPanel(new BorderLayout());
        productPanel.add(new JLabel("Manage Products Panel", SwingConstants.CENTER), BorderLayout.NORTH);
        centerPanel.add(productPanel, "product");

        showPanel("home"); // Mặc định mở Home
    }

    private JButton createButton(String text, String iconPath, int y) {
        JButton button = new JButton(text);
        button.setFont(new Font("Tahoma", Font.BOLD, 16));
        button.setBackground(new Color(255, 255, 204));
        button.setIcon(new ImageIcon(UserView.class.getResource(iconPath)));
        button.setIconTextGap(20);
        button.setFocusPainted(false);
        button.setBorder(null);
        button.setBounds(18, y, 200, 31);
        addHoverEffect(button, new Color(128, 128, 100), new Color(255, 255, 204));
        return button;
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

    private JPanel createHomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Home Panel", SwingConstants.CENTER), BorderLayout.NORTH);
        return panel;
    }

    public void addUsersListener(ActionListener listener) {
        btnAdmin.addActionListener(listener);
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

    public void addProductListener(ActionListener listener) {
        btnProduct.addActionListener(listener);
    }

    public void addLogoutListener(ActionListener listener) {
        btnLogout.addActionListener(listener);
    }

    public void addHomeListener(ActionListener listener) {
        btnHome.addActionListener(listener);
    }

    public void showPanel(String panelName) {
        cardLayout.show(centerPanel, panelName);
    }

    // Thêm phương thức cập nhật tên/ID
    public void setEmployeeInfo(String name, String id) {
        lblEmployeeName.setText("Name: " + name);
        lblEmployeeID.setText("ID: " + id);
    }
}
