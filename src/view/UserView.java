package view;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UserView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JPanel panel = new JPanel();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserView frame = new UserView();
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

	/**
	 * Create the frame.
	 */
	public UserView() {
		setFont(new Font("Arial", Font.PLAIN, 14));
		setTitle("Manage Users");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 750);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		panel.setBackground(new Color(255, 255, 204));
		panel.setBounds(0, -16, 238, 729);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JButton btnPets = new JButton("Pets");
		btnPets.setBackground(new Color(255, 255, 204));
		btnPets.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/pets_Icon.png")));
		btnPets.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnPets.setBorder(null);
		btnPets.setFocusPainted(false);
		btnPets.setIconTextGap(20);
		btnPets.setBounds(10, 328, 146, 31);
		addHoverEffect(btnPets, new Color(128, 128, 100), new Color(255, 255, 204));
		panel.add(btnPets);
		
		
		
		JButton btnUsers = new JButton("Users");
		btnUsers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnUsers.setIconTextGap(20);
		btnUsers.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/user_Icon.png")));
		btnUsers.setFocusPainted(false);
		btnUsers.setBorder(null);
		btnUsers.setBackground(new Color(255, 255, 204));
		btnUsers.setBounds(10, 391, 146, 31);
		addHoverEffect(btnUsers, new Color(128, 128, 100), new Color(255, 255, 204));
		panel.add(btnUsers);
		
		JButton btnCusTomers = new JButton("Customers");
		btnCusTomers.setIconTextGap(20);
		btnCusTomers.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/users_Icon.png")));
		btnCusTomers.setFocusPainted(false);
		btnCusTomers.setBorder(null);
		btnCusTomers.setBackground(new Color(255, 255, 204));
		btnCusTomers.setBounds(22, 453, 146, 31);
		addHoverEffect(btnCusTomers, new Color(128, 128, 100), new Color(255, 255, 204));
		panel.add(btnCusTomers);
		
		JButton btnBills = new JButton("Bills");
		btnBills.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnBills.setIconTextGap(20);
		btnBills.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/bill_Icon.png")));
		btnBills.setFocusPainted(false);
		btnBills.setBorder(null);
		btnBills.setBackground(new Color(255, 255, 204));
		btnBills.setBounds(10, 507, 137, 31);
		addHoverEffect(btnBills, new Color(128, 128, 100), new Color(255, 255, 204));
		panel.add(btnBills);
		
		JButton btnLogout = new JButton("Logout");
		btnLogout.setIconTextGap(20);
		btnLogout.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/bill_Icon.png")));
		btnLogout.setFocusPainted(false);
		btnLogout.setBorder(null);
		btnLogout.setBackground(new Color(255, 255, 204));
		btnLogout.setBounds(0, 688, 112, 31);
		addHoverEffect(btnLogout, new Color(128, 128, 100), new Color(255, 255, 204));
		panel.add(btnLogout);
		
	
	}
}
