package view;

import respository.dao.*;
import service.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
	private JPanel panel;
	private JLabel lblEmployeeName;
	private JLabel lblEmployeeID;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnPets;
	private JButton btnAdmin;
	private JButton btnBills;
	private JButton btnCusTomers;
	private JButton btnProduct;
	private JButton btnHome;
	private JButton btnLogout;
	private CardLayout cardLayout;
	private JPanel centerPanel;
	private JButton btnService;

	// Màu sắc chủ đạo và style hiện đại
	private static final Color SIDE_BG = new Color(255, 255, 204);
	private static final Color SIDE_HOVER = new Color(231, 234, 255);
	private static final Color BTN_BG = new Color(121, 162, 219);
	private static final Color BTN_BG_HOVER = new Color(80, 120, 180);
	private static final Color TEXT_MAIN = new Color(40, 40, 40);

	private void addHoverEffect(JButton button, Color hoverColor, Color defaultColor) {
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(hoverColor);
				button.setForeground(Color.WHITE);
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(defaultColor);
				button.setForeground(TEXT_MAIN);
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
		setUndecorated(false); // Đảm bảo vẫn có thanh tiêu đề, có thể thêm custom nếu muốn
		setResizable(false);   // LOCK không cho resize cửa sổ
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // LOCK không cho tắt trực tiếp, chỉ logout mới tắt
		setTitle("PetShop");
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserView.class.getResource("/view/Icon/users_Icon.png")));
		setFont(new Font("Arial", Font.PLAIN, 14));
		setBounds(100, 100, 1210, 775);
		setLocationRelativeTo(null);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);

		cardLayout = new CardLayout();
		centerPanel = new JPanel(cardLayout) {
			// Bo tròn content center
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(245, 250, 255));
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
				g2.dispose();
			}
		};
		centerPanel.setBounds(250, 0, 950, 750);
		centerPanel.setOpaque(false);
		contentPane.add(centerPanel);

		// Thêm các panel
		centerPanel.add(createPetsPanel(), "Pets");
		centerPanel.add(createProductPanel(), "Product");
		centerPanel.add(createCustomersPanel(), "Customers");
		centerPanel.add(createBillingsPanel(), "Bills");
		centerPanel.add(createHomePanel(), "Home");
		centerPanel.add(createServicePanel(), "Service");

		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(SIDE_BG);
				g2.fillRoundRect(0, 0, getWidth(), getHeight(), 22, 22);
				g2.dispose();
			}
		};
		panel.setBackground(SIDE_BG);
		panel.setBounds(0, -16, 250, 750);
		panel.setLayout(null);
		contentPane.add(panel);

		// Icon + Avatar
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/user_main_Icon.png")));
		lblNewLabel.setBounds(55, 81, 131, 135);
		panel.add(lblNewLabel);

		int id = UserSession.getInstance().getUser().getId();
		String name = UserSession.getInstance().getUser().getName();

		lblEmployeeName = new JLabel("Name: " + name);
		lblEmployeeName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmployeeName.setBounds(23, 226, 163, 25);
		panel.add(lblEmployeeName);

		lblEmployeeID = new JLabel("ID: " + id);
		lblEmployeeID.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmployeeID.setBounds(23, 261, 163, 25);
		panel.add(lblEmployeeID);

		btnHome = createSideBarButton("Home", "/view/Icon/Category_Icon.png", 13, 370);
		panel.add(btnHome);

		btnPets = createSideBarButton("Pets", "/view/Icon/pets_Icon.png", 13, 316);
		panel.add(btnPets);

		btnProduct = createSideBarButton("Product", "/view/Icon/Cate_Icon.png", 18, 426);
		panel.add(btnProduct);

		btnCusTomers = createSideBarButton("Customers", "/view/Icon/users_Icon.png", 33, 481);
		panel.add(btnCusTomers);

		btnBills = createSideBarButton("Bills", "/view/Icon/bill_Icon.png", 11, 536);
		panel.add(btnBills);

		if ("admin".equals(role)) {
			centerPanel.add(createUsersPanel(), "Admin");
			btnAdmin = createSideBarButton("Admin", "/view/Icon/user1_Icon.png", 13, 591);
			panel.add(btnAdmin);
		}

		btnService = createSideBarButton("Service", "/view/Icon/customer-service.png", 18, 626);
		panel.add(btnService);

		btnLogout = createSideBarButton("Logout", "/view/Icon/logout_Icon.png", 0, 688);
		btnLogout.setFont(new Font("Tahoma", Font.ITALIC, 14));
		panel.add(btnLogout);

		// Disable maximize, resizing, and lock close
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setResizable(false);

		centerPanel.setVisible(true);
	}


	private JButton createSideBarButton(String text, String iconPath, int x, int y) {
		JButton btn = new JButton(text);
		btn.setFont(new Font("Tahoma", Font.BOLD, 16));
		btn.setIconTextGap(20);
		btn.setIcon(new ImageIcon(getClass().getResource(iconPath)));
		btn.setFocusPainted(false);
		btn.setBorder(null);
		btn.setBackground(SIDE_BG);
		btn.setForeground(TEXT_MAIN);
		btn.setBounds(x, y, 173, 31);
		addHoverEffect(btn, BTN_BG, SIDE_BG);
		return btn;
	}

	private JPanel createPetsPanel() {
		PetView petView = new PetView();
		DaoInterface petRepo = new PetDAO();
		PetService petService = new PetService((PetDAO) petRepo);
		new controller.PetController(petView, petService);
		return petView;
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
		BillView billView = new BillView();
		BillDAO billDAO = new BillDAO();
		BillService billService = new BillService(billDAO);
		CustomerService customerService = new CustomerService(billDAO); // Sử dụng đúng BillDAO
		DaoInterface productRepo = new ProductDAO();
		ProductService productService = new ProductService(productRepo);
		DaoInterface petRepo = new PetDAO();
		PetService petService = new PetService((PetDAO) petRepo);
		new controller.BillController(billView, billService, productService, petService, customerService);
		return billView;
	}

	private JPanel createProductPanel() {
		ProductView productView = new ProductView();
		DaoInterface productRepo = new ProductDAO();
		ProductService productService = new ProductService(productRepo);
		new controller.ProductController(productView, productService);
		return productView;
	}

	private JPanel createHomePanel() {
		BookingView homePanel = new BookingView();
		CheckDonView checkDonView = new CheckDonView();
		return checkDonView;
	}

	private JPanel createServicePanel() {
		System.out.println("Tạo Service Panel");
		ServiceView Service = new ServiceView();
		return Service;
	}

	public void addUsersListener(ActionListener listener) {
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

	public void addServiceListener(ActionListener listener) {
		btnService.addActionListener(listener);
	}

	public void showPanel(String panelName) {
		cardLayout.show(centerPanel, panelName);
	}

	public void setEmployeeInfo(String name, String id) {
		lblEmployeeName.setText("Name: " + name);
		lblEmployeeID.setText("ID: " + id);
	}
}