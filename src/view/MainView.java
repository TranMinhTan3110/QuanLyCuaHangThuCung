package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.*;
public class MainView extends JFrame {
	private JPanel panel;
	private JLabel lblEmployeeName;
	private JLabel lblEmployeeID;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainView frame = new MainView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
	public MainView() {
		panel = new JPanel(); 
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserView.class.getResource("/view/Icon/users_Icon.png")));
		setFont(new Font("Arial", Font.PLAIN, 14));
		setTitle("PetShop");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200,750 );
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));


		setContentPane(contentPane);
		contentPane.setLayout(null);
		panel.setBackground(new Color(255, 255, 204));
		panel.setBounds(0, -16, 250, 750);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnPets = new JButton("Pets");
		btnPets.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnPets.setBackground(new Color(255, 255, 204));
		btnPets.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/pets_Icon.png")));
		btnPets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPets.setBorder(null);
		btnPets.setFocusPainted(false);
		btnPets.setIconTextGap(20);
		btnPets.setBounds(13, 316, 173, 31);
		addHoverEffect(btnPets, new Color(128, 128, 100), new Color(255, 255, 204));
		panel.add(btnPets);
		
		
		
		JButton btnUsers = new JButton("Admin");
		btnUsers.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUsers.setIconTextGap(20);
		btnUsers.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/user1_Icon.png")));
		btnUsers.setFocusPainted(false);
		btnUsers.setBorder(null);
		btnUsers.setBackground(new Color(255, 255, 204));
		btnUsers.setBounds(18, 371, 173, 31);
		addHoverEffect(btnUsers, new Color(128, 128, 100), new Color(255, 255, 204));
		panel.add(btnUsers);
		
		JButton btnCusTomers = new JButton("Customers");
		btnCusTomers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCusTomers.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnCusTomers.setIconTextGap(20);
		btnCusTomers.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/users_Icon.png")));
		btnCusTomers.setFocusPainted(false);
		btnCusTomers.setBorder(null);
		btnCusTomers.setBackground(new Color(255, 255, 204));
		btnCusTomers.setBounds(33, 481, 173, 31);
		addHoverEffect(btnCusTomers, new Color(128, 128, 100), new Color(255, 255, 204));
		panel.add(btnCusTomers);
		
		JButton btnBills = new JButton("Bills");
		btnBills.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnBills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBills.setIconTextGap(20);
		btnBills.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/bill_Icon.png")));
		btnBills.setFocusPainted(false);
		btnBills.setBorder(null);
		btnBills.setBackground(new Color(255, 255, 204));
		btnBills.setBounds(11, 536, 173, 31);
		addHoverEffect(btnBills, new Color(128, 128, 100), new Color(255, 255, 204));
		panel.add(btnBills);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.ITALIC, 14));
		btnLogout.setIconTextGap(20);
		btnLogout.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/logout_Icon.png")));
		btnLogout.setFocusPainted(false);
		btnLogout.setBorder(null);
		btnLogout.setBackground(new Color(255, 255, 204));
		btnLogout.setBounds(0, 688, 112, 31);
		addHoverEffect(btnLogout, new Color(128, 128, 100), new Color(255, 255, 204));
		panel.add(btnLogout);
		
		JLabel lblNewLabel = new JLabel("New label");
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
		
		JButton btnCategory = new JButton("Product");
		btnCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCategory.setIconTextGap(20);
		btnCategory.setIcon(new ImageIcon(MainView.class.getResource("/view/Icon/product_Icon.png")));
		btnCategory.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnCategory.setFocusPainted(false);
		btnCategory.setBorder(null);
		btnCategory.setBackground(new Color(255, 255, 204));
		btnCategory.setBounds(18, 426, 173, 31);
		addHoverEffect(btnCategory, new Color(128, 128, 100), new Color(255, 255, 204));
		panel.add(btnCategory);
		
		JButton btnHome = new JButton("Home");
		btnHome.setIconTextGap(20);
		btnHome.setIcon(new ImageIcon(MainView.class.getResource("/view/Icon/Category_Icon.png")));
		btnHome.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnHome.setFocusPainted(false);
		btnHome.setBorder(null);
		btnHome.setBackground(new Color(255, 255, 204));
		btnHome.setBounds(12, 582, 173, 31);
		panel.add(btnHome);

	}


}
