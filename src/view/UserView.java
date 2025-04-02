package view;

import controller.LoginController;
import dao.LoginDAO.implement.UserResposittoryImpl;
import service.AuthService;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.Toolkit;

public class UserView extends JFrame {

	public static String currenUser;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private final JPanel panel = new JPanel();
	private JLabel lblEmployeeName;
	private JLabel lblEmployeeID;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTable table;
    DefaultTableModel model;
	private JComboBox comboBox;
	private JButton btnUsers;
	JButton btnLogout;
	//ba nút thao tác
 	 private JButton btnEdit;
    private  JButton btnDel;
    private  JButton btnAdd;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					UserView frame = new UserView();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
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
	public UserView(String role) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(UserView.class.getResource("/view/Icon/users_Icon.png")));
		setFont(new Font("Arial", Font.PLAIN, 14));
		setTitle("Manage Users");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 750);
		setLocationRelativeTo(null);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		panel.setBackground(new Color(255, 255, 204));
		panel.setBounds(0, -16, 238, 729);
		contentPane.add(panel);
		panel.setLayout(null);
		//
		if (!"admin".equalsIgnoreCase(role)) {
			btnUsers.setVisible(false);
		}


		//
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


		btnUsers = new JButton("Admin");
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
		btnCusTomers.setBounds(34, 478, 173, 31);
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
		btnBills.setBounds(12, 532, 173, 31);
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

		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/user_main_Icon.png")));
		lblNewLabel.setBounds(55, 81, 131, 135);
		panel.add(lblNewLabel);

		lblEmployeeName = new JLabel("Name: ");
		lblEmployeeName.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblEmployeeName.setBounds(23, 226, 163, 25);
		panel.add(lblEmployeeName);

//		lblEmployeeID = new JLabel("ID: ");
//		lblEmployeeID.setFont(new Font("Tahoma", Font.BOLD, 14));
//		lblEmployeeID.setBounds(23, 261, 163, 25);
//		panel.add(lblEmployeeID);

		JButton btnCategory = new JButton("Category");
		btnCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnCategory.setIconTextGap(20);
		btnCategory.setIcon(new ImageIcon(UserView.class.getResource("/view/Icon/Category_Icon.png")));
		btnCategory.setFont(new Font("Tahoma", Font.BOLD, 16));
		btnCategory.setFocusPainted(false);
		btnCategory.setBorder(null);
		btnCategory.setBackground(new Color(255, 255, 204));
		btnCategory.setBounds(28, 426, 173, 31);
		addHoverEffect(btnCategory, new Color(128, 128, 100), new Color(255, 255, 204));
		panel.add(btnCategory);

		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 223));
		panel_1.setBounds(237, 0, 949, 244);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("ID:");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1.setBounds(95, 46, 67, 21);
		panel_1.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(203, 43, 125, 32);
		addPlaceholder(textField, "Enter ID");
		panel_1.add(textField);
		textField.setColumns(10);

		JLabel lblNewLabel_1_1 = new JLabel("Phone:");
		lblNewLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_1.setBounds(95, 96, 68, 21);
		panel_1.add(lblNewLabel_1_1);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(203, 93, 125, 32);
		addPlaceholder(textField_1, "Enter Phone");
		panel_1.add(textField_1);

		JLabel lblNewLabel_1_1_1 = new JLabel("UserName:");
		lblNewLabel_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_1_1.setBounds(95, 148, 88, 21);
		panel_1.add(lblNewLabel_1_1_1);

		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(203, 145, 125, 32);
		addPlaceholder(textField_2, "Enter Username");
		panel_1.add(textField_2);

		JLabel lblNewLabel_1_2 = new JLabel("Name:");
		lblNewLabel_1_2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_2.setBounds(419, 46, 67, 21);
		panel_1.add(lblNewLabel_1_2);

		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(530, 43, 125, 32);
		addPlaceholder(textField_3, "Enter Name");
		panel_1.add(textField_3);

		JLabel lblNewLabel_1_1_2 = new JLabel("Address:");
		lblNewLabel_1_1_2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_1_2.setBounds(419, 96, 68, 21);
		panel_1.add(lblNewLabel_1_1_2);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(530, 93, 125, 32);
		addPlaceholder(textField_4, "Enter Address");
		panel_1.add(textField_4);

		JLabel lblNewLabel_1_1_2_1 = new JLabel("Password:");
		lblNewLabel_1_1_2_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_1_2_1.setBounds(419, 148, 88, 21);
		panel_1.add(lblNewLabel_1_1_2_1);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(530, 145, 125, 32);
		addPlaceholder(textField_5, "Enter Password");
		panel_1.add(textField_5);

		comboBox = new JComboBox();
		comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		comboBox.setModel(new DefaultComboBoxModel(new String[]{"Admin", "User"}));
		comboBox.setBounds(203, 192, 76, 32);
		panel_1.add(comboBox);

		JLabel lblNewLabel_1_1_1_1 = new JLabel("Role");
		lblNewLabel_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_1_1_1.setBounds(95, 190, 88, 21);
		panel_1.add(lblNewLabel_1_1_1_1);

		btnAdd = new JButton("Add");
		btnAdd.setBackground(new Color(255, 255, 204));
		btnAdd.setFont(new Font("Arial", Font.PLAIN, 16));
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnAdd.setBounds(727, 40, 88, 32);
		btnAdd.setFocusPainted(false);
		addHoverEffect(btnAdd, new Color(128, 128, 100), new Color(255, 255, 204));
		panel_1.add(btnAdd);

		btnEdit = new JButton("Edit");
		btnEdit.setBackground(new Color(255, 255, 204));

		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnEdit.setFont(new Font("Arial", Font.PLAIN, 16));
		btnEdit.setBounds(727, 93, 88, 32);
		btnEdit.setFocusPainted(false);
		addHoverEffect(btnEdit, new Color(128, 128, 100), new Color(255, 255, 204));
		panel_1.add(btnEdit);

		btnDel = new JButton("Delete");
		btnDel.setBackground(new Color(255, 255, 204));
		btnDel.setFont(new Font("Arial", Font.PLAIN, 16));
		btnDel.setBounds(727, 145, 88, 32);
		btnDel.setFocusPainted(false);
		addHoverEffect(btnDel, new Color(128, 128, 100), new Color(255, 255, 204));
		panel_1.add(btnDel);

		//tạo  table
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setFont(new Font("Arial", Font.PLAIN, 16));
		scrollPane.setBounds(237, 242, 949, 471);
		scrollPane.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				"Manager Users",
				TitledBorder.CENTER,
				TitledBorder.TOP,
				new Font("Arial", Font.BOLD, 16),
				Color.BLACK
		));
		contentPane.add(scrollPane);

		table = new JTable();
		String[] columnNames = {"ID", "Name", "Phone", "Username", "Password", "Address", "Role"};
		model = new DefaultTableModel(columnNames, 0);
		table = new JTable(model);
		scrollPane.setViewportView(table);
//		currenUsser = textField_2.getText();
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Lấy dữ liệu từ các ô nhập liệu
				String id = textField.getText();
				String name = textField_3.getText();
				String phone = textField_1.getText();
				String username = textField_2.getText();
				String password = textField_5.getText();
				String address = textField_4.getText();
				String role = comboBox.getSelectedItem().toString();

				// Thêm dữ liệu vào bảng
				model.addRow(new Object[]{id, name, phone, username, address, role});

				// Xóa nội dung các ô nhập liệu sau khi thêm
				textField.setText("");
				textField_3.setText("");
				textField_1.setText("");
				textField_2.setText("");
				textField_4.setText("");
				textField_5.setText("");
			}
		});

		//Logout

	}
	public void addLogoutActionListener (ActionListener listener){
		btnLogout.addActionListener(listener);
	}
	public void setEmployeeInfo() {
	    lblEmployeeName.setText("Name: " + getCurrenUser());
//	    lblEmployeeID.setText("ID: " + id);
	}


    // Các getter cho các trường nhập liệu
    public String getEmployeeId() {
        return textField.getText().trim();
    }

    public String getEmployeeName() {
        return textField_3.getText().trim();
    }

    public String getEmployeePhone() {
        return textField_1.getText().trim();
    }

    public String getEmployeeAddress() {
        return textField_4.getText().trim();
    }

    public String getEmployeeUsername() {
        return textField_2.getText().trim();
    }

    public String getEmployeePassword() {
        return textField_5.getText().trim();
    }

    public String getEmployeeRole() {
        return (String) comboBox
                .getSelectedItem();
    }
    public JTable getTable() {
        return table;
    }

	public static String getCurrenUser() {
		return currenUser;
	}

	public static void setCurrenUser(String currenUsser) {
		UserView.currenUser = currenUsser;
	}

	// Các hàm thao tác bảng
    public void addEmployeeToTable(String id, String name, String phone,String username ,String password, String address, String role) {
        model.addRow(new Object[]{id, name, phone,username , password,address, role});
    }

    public void updateEmployeeInTable(int row, String id, String name, String phone,String username , String password,String address , String role) {
        model.setValueAt(id, row, 0);
        model.setValueAt(name, row, 1);
        model.setValueAt(phone, row, 2);
        model.setValueAt(username, row, 3);
        model.setValueAt(password, row, 4);
        model.setValueAt(address, row, 5);
        model.setValueAt(role, row, 6);
    }

    public void removeEmployeeFromTable(int row) {
        model.removeRow(row);
    }
	public void setEmployeeData(String id,String name,String  phone,String address,String username,String password,String role){

			textField.setText(id);
            textField_1.setText(phone);
        	textField_2.setText(username);
        	textField_3.setText(name);
        	textField_4.setText(address);
        	textField_5.setText(password);
			comboBox.setSelectedItem(role);
    }
    public void clearForm() {
        textField.setText("");
        textField_1.setText("");
        textField_2.setText("");
        textField_3.setText("");
        textField_4.setText("");
        textField_5.setText("");
        comboBox.setSelectedIndex(0);
    }

    //trả về các nút
    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnEdit() {
        return btnEdit;
    }

    public JButton getBtnDelete() {
        return btnDel;
    }

}
