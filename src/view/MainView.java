package view;

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
    private JButton btnLogout;

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

        // ========== Thêm sidebar và centerPanel vào Frame ==========
        add(sidebar, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createPetsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(new JLabel("Manage Pets Panel", SwingConstants.CENTER), BorderLayout.NORTH);
        // ... Thêm form, bảng, v.v. cho chức năng Pets
        return panel;
    }

    private JPanel createUsersPanel() {
        // Tạo EmployeeView
        UserView employeeView = new UserView();
        // Khởi tạo EmployeeController, đảm nhiệm xử lý CRUD cho EmployeeView
        new controller.UserController(employeeView);
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
    // 🟢 Cập nhật Controller khi khởi tạo MainView
    public void addUsersListener(ActionListener listener) {
        if (btnUsers.isVisible()) { // Chỉ đăng ký sự kiện nếu nút hiện
            btnUsers.addActionListener(listener);
        }
    }

    // ======== Các hàm cho phép Controller đăng ký lắng nghe sự kiện ========
    public void addPetsListener(ActionListener listener) {
        btnPets.addActionListener(listener);
    }

    public void addCustomersListener(ActionListener listener) {
        btnCustomers.addActionListener(listener);
    }

    public void addBillingsListener(ActionListener listener) {
        btnBillings.addActionListener(listener);
    }

    public void addLogoutListener(ActionListener listener) {
        btnLogout.addActionListener(listener);
    }

    // ======== Cho phép controller gọi để chuyển panel ========
    public void showPanel(String panelName) {
        cardLayout.show(centerPanel, panelName);
    }
}
