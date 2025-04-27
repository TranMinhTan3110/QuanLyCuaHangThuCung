package view;

import java.awt.Color;
import java.awt.Font;

import javax.management.StringValueExp;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.management.StringValueExp;
import view.UI.Hover;

import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class CustomerView extends JPanel {

<<<<<<< Updated upstream
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField Name_textField;
	private JTextField Address_textField;
	private JTextField ID_textField;
	private JTextField Phone_textField;
	private JTextField Score_textField;
	private JTextField Rank_textField;

	private DefaultTableModel model;
	//cac nut
	private JButton Delete_Button, Edit_Button, Save_Button,ID_Button;

	public String getName_textField() {
		return Name_textField.getText();
	}

	public String getAddress_textField() {
		return Address_textField.getText();
	}

	public String getID_textField() {
		return ID_textField.getText();
	}

	public String getPhone_textField() {
		return Phone_textField.getText();
	}

	public String getScore_textField() {
		return Score_textField.getText();
	}

	/**
	 * Create the panel.
	 */
	public customerView() {
		setLayout(null);
		setBounds(0, 0, 950, 750);

		JPanel panel_top = new JPanel();
		panel_top.setBackground(new Color(255, 255, 223));
		panel_top.setBounds(0, 0, 950, 240);
		add(panel_top);
		panel_top.setLayout(null);


		JLabel lblID = new JLabel("ID");
		lblID.setFont(new Font("Arial", Font.PLAIN, 16));
		lblID.setBounds(new Rectangle(176, 36, 55, 21));
		panel_top.add(lblID);

		JLabel lblName = new JLabel("Name");
		lblName.setFont(new Font("Arial", Font.PLAIN, 16));
		lblName.setBounds(new Rectangle(176, 54, 55, 21));
		lblName.setBounds(370, 36, 97, 21);
		panel_top.add(lblName);

		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAddress.setBounds(new Rectangle(176, 36, 55, 21));
		lblAddress.setBounds(176, 121, 100, 21);
		panel_top.add(lblAddress);

		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPhone.setBounds(new Rectangle(176, 54, 55, 21));
		lblPhone.setBounds(575, 36, 55, 21);
		panel_top.add(lblPhone);



//        JLabel lblRank = new JLabel("Rank");
//        lblRank.setFont(new Font("Arial", Font.PLAIN, 16));
//        lblRank.setBounds(new Rectangle(176, 36, 55, 21));
//        lblRank.setBounds(370, 126, 55, 21);
//        panel_top.add(lblRank);

		JLabel lblScore = new JLabel("Score");
		lblScore.setFont(new Font("Arial", Font.PLAIN, 16));
		lblScore.setBounds(new Rectangle(176, 36, 55, 21));
		lblScore.setBounds(370, 121, 55, 21);
		panel_top.add(lblScore);


		// Sửa lại phần khởi tạo các JTextField trong constructor:
		ID_textField = new JTextField();
		ID_textField.setColumns(10);
		ID_textField.setBounds(176, 67, 109, 25);  // Đặt ID ở vị trí ban đầu của Name
		panel_top.add(ID_textField);
		Hover.addPlaceholder(ID_textField, "Enter ID");

		Name_textField = new JTextField();
		Name_textField.setBounds(370, 67, 137, 25);  // Đặt Name ở vị trí ban đầu của Address
		panel_top.add(Name_textField);
		Hover.addPlaceholder(Name_textField, "Enter Name");

		// Sửa vị trí của Phone_textField
		Phone_textField = new JTextField();
		Phone_textField.setColumns(10);
		Phone_textField.setBounds(575, 67, 109, 25); // Cùng vị trí X với JLabel "Phone", Y có thể điều chỉnh
		panel_top.add(Phone_textField);
		Hover.addPlaceholder(Phone_textField, "Enter Phone");

		// Sửa vị trí của Address_textField
		Address_textField = new JTextField();
		Address_textField.setColumns(10);
		Address_textField.setBounds(176, 152, 102, 25); // Cùng vị trí X với JLabel "Address" (gần đúng), Y có thể điều chỉnh
		panel_top.add(Address_textField);
		Hover.addPlaceholder(Address_textField, "Enter Address");



		Score_textField = new JTextField();
		Score_textField.setColumns(10);
		Score_textField.setBounds(370, 152, 109, 25);  // Đặt Score ở vị trí ban đầu của Rank
		panel_top.add(Score_textField);
		Hover.addPlaceholder(Score_textField, "Enter Score");


		Edit_Button = new JButton("Edit");
		Edit_Button.setBackground(new Color(255, 255, 204));
		Edit_Button.setFont(new Font("Arial", Font.PLAIN, 16));
		Edit_Button.setBounds(720, 62, 113, 30);
		Edit_Button.setFocusPainted(false);
		panel_top.add(Edit_Button);
		Hover.addHoverEffect(Edit_Button, new Color(128, 128, 100), new Color(255, 255, 204));

		Save_Button = new JButton("Save");
		Save_Button.setBackground(new Color(255, 255, 204));
		Save_Button.setFont(new Font("Arial", Font.PLAIN, 16));
		Save_Button.setBounds(720, 121, 113, 30);
		Save_Button.setFocusPainted(false);
		panel_top.add(Save_Button);
		Hover.addHoverEffect(Save_Button, new Color(128, 128, 100), new Color(255, 255, 204));


		Delete_Button = new JButton("Delete");
		Delete_Button.setBackground(new Color(255, 255, 204));
		Delete_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		Delete_Button.setFont(new Font("Arial", Font.PLAIN, 16));
		Delete_Button.setBounds(720, 175, 113, 30);
		Delete_Button.setFocusPainted(false);
		panel_top.add(Delete_Button);
		Hover.addHoverEffect(Delete_Button, new Color(128, 128, 100), new Color(255, 255, 204));

		JScrollPane cus_List = new JScrollPane();
		cus_List.setBounds(0, 244, 950, 500);
		add(cus_List);
		cus_List.setFont(new Font("Arial", Font.PLAIN, 16));
		// tạo title cho table
		cus_List.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.BLACK),
				"Customer List",
				TitledBorder.CENTER,
				TitledBorder.TOP,
				new Font("Arial", Font.BOLD, 16),
				Color.BLACK
		));

		add(cus_List);


//		model = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Username", "Password", "Address", "Role"}, 0);
		String[] columnNames = {"Id", "Name", "Phone", "Address","Score","Rank"};
		model = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		table = new JTable(model);
		cus_List.setViewportView(table);
	}


	public void removeCustomerFromTable(int row) {
		model.removeRow(row);
	}

	public void setAddButtonListener(ActionListener listener) {
		Save_Button.addActionListener(listener);
	}

	public void setEditButtonListener(ActionListener listener) {
		Edit_Button.addActionListener(listener);
	}

	public void setDeleteButtonListener(ActionListener listener) {
		Delete_Button.addActionListener(listener);
	}

	public JTable getTable() {
		return table;
	}
	public int getSeclectedRow(){
		return table.getSelectedRow();
	}
	public void clear(){
		Name_textField.setText("");
		Address_textField.setText("");
		Phone_textField.setText("");
		ID_textField.setText("");
		Score_textField.setText("");
	}
	public void addCustomerToTable(String id, String name,String  phone, String address ,int loyaltyPoints,String membershipLevels) {
		model.addRow(new Object[]{id, name,phone ,address, loyaltyPoints,membershipLevels});
	}

	public void updateCustomerInTable(int row, String id, String name, String phone, String address  ) {
		model.setValueAt(id, row, 0);
		model.setValueAt(name, row, 1);
		model.setValueAt(phone, row, 2);
		model.setValueAt(address, row, 3);


	}


	public void setEmployeeData(String id, String name ,String phone,String address) {
		ID_textField.setText(id);
		Name_textField.setText(name);
		Phone_textField.setText(phone);
		Address_textField.setText(address);
//        Score_textField.setText(String.valueOf(Score));

	}
}


=======
    private static final long serialVersionUID = 1L;
    private JTable table;
    private JTextField Name_textField;
    private JTextField Address_textField;
    private JTextField ID_textField;
    private JTextField Phone_textField;
    private JTextField Score_textField;
    private JTextField Rank_textField;

    private DefaultTableModel model;
    //cac nut
    private JButton Delete_Button, Edit_Button, Save_Button,ID_Button;

    public String getName_textField() {
        return Name_textField.getText();
    }

    public String getAddress_textField() {
        return Address_textField.getText();
    }

    public String getID_textField() {
        return ID_textField.getText();
    }

    public String getPhone_textField() {
        return Phone_textField.getText();
    }

    public String getScore_textField() {
        return Score_textField.getText();
    }

    /**
     * Create the panel.
     */
    public CustomerView() {
        setLayout(null);
        setBounds(0, 0, 950, 750);

        JPanel panel_top = new JPanel();
        panel_top.setBackground(new Color(255, 255, 223));
        panel_top.setBounds(0, 0, 950, 240);
        add(panel_top);
        panel_top.setLayout(null);


        JLabel lblID = new JLabel("ID");
        lblID.setFont(new Font("Arial", Font.PLAIN, 16));
        lblID.setBounds(new Rectangle(176, 36, 55, 21));
        panel_top.add(lblID);

        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Arial", Font.PLAIN, 16));
        lblName.setBounds(new Rectangle(176, 54, 55, 21));
        lblName.setBounds(370, 36, 97, 21);
        panel_top.add(lblName);

        JLabel lblAddress = new JLabel("Address");
        lblAddress.setFont(new Font("Arial", Font.PLAIN, 16));
        lblAddress.setBounds(new Rectangle(176, 36, 55, 21));
        lblAddress.setBounds(176, 121, 100, 21);
        panel_top.add(lblAddress);

        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setFont(new Font("Arial", Font.PLAIN, 16));
        lblPhone.setBounds(new Rectangle(176, 54, 55, 21));
        lblPhone.setBounds(575, 36, 55, 21);
        panel_top.add(lblPhone);



//        JLabel lblRank = new JLabel("Rank");
//        lblRank.setFont(new Font("Arial", Font.PLAIN, 16));
//        lblRank.setBounds(new Rectangle(176, 36, 55, 21));
//        lblRank.setBounds(370, 126, 55, 21);
//        panel_top.add(lblRank);

        JLabel lblScore = new JLabel("Score");
        lblScore.setFont(new Font("Arial", Font.PLAIN, 16));
        lblScore.setBounds(new Rectangle(176, 36, 55, 21));
        lblScore.setBounds(370, 121, 55, 21);
        panel_top.add(lblScore);


        // Sửa lại phần khởi tạo các JTextField trong constructor:
        ID_textField = new JTextField();
        ID_textField.setColumns(10);
        ID_textField.setBounds(176, 67, 109, 25);  // Đặt ID ở vị trí ban đầu của Name
        panel_top.add(ID_textField);
        Hover.addPlaceholder(ID_textField, "Enter ID");

        Name_textField = new JTextField();
        Name_textField.setBounds(370, 67, 137, 25);  // Đặt Name ở vị trí ban đầu của Address
        panel_top.add(Name_textField);
        Hover.addPlaceholder(Name_textField, "Enter Name");

        // Sửa vị trí của Phone_textField
        Phone_textField = new JTextField();
        Phone_textField.setColumns(10);
        Phone_textField.setBounds(575, 67, 109, 25); // Cùng vị trí X với JLabel "Phone", Y có thể điều chỉnh
        panel_top.add(Phone_textField);
        Hover.addPlaceholder(Phone_textField, "Enter Phone");

        // Sửa vị trí của Address_textField
        Address_textField = new JTextField();
        Address_textField.setColumns(10);
        Address_textField.setBounds(176, 152, 102, 25); // Cùng vị trí X với JLabel "Address" (gần đúng), Y có thể điều chỉnh
        panel_top.add(Address_textField);
        Hover.addPlaceholder(Address_textField, "Enter Address");



        Score_textField = new JTextField();
        Score_textField.setColumns(10);
        Score_textField.setBounds(370, 152, 109, 25);  // Đặt Score ở vị trí ban đầu của Rank
        panel_top.add(Score_textField);
        Hover.addPlaceholder(Score_textField, "Enter Score");


        Edit_Button = new JButton("Edit");
        Edit_Button.setBackground(new Color(255, 255, 204));
        Edit_Button.setFont(new Font("Arial", Font.PLAIN, 16));
        Edit_Button.setBounds(720, 62, 113, 30);
        Edit_Button.setFocusPainted(false);
        panel_top.add(Edit_Button);
        Hover.addHoverEffect(Edit_Button, new Color(128, 128, 100), new Color(255, 255, 204));

        Save_Button = new JButton("Save");
        Save_Button.setBackground(new Color(255, 255, 204));
        Save_Button.setFont(new Font("Arial", Font.PLAIN, 16));
        Save_Button.setBounds(720, 121, 113, 30);
        Save_Button.setFocusPainted(false);
        panel_top.add(Save_Button);
        Hover.addHoverEffect(Save_Button, new Color(128, 128, 100), new Color(255, 255, 204));


        Delete_Button = new JButton("Delete");
        Delete_Button.setBackground(new Color(255, 255, 204));
        Delete_Button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        Delete_Button.setFont(new Font("Arial", Font.PLAIN, 16));
        Delete_Button.setBounds(720, 175, 113, 30);
        Delete_Button.setFocusPainted(false);
        panel_top.add(Delete_Button);
        Hover.addHoverEffect(Delete_Button, new Color(128, 128, 100), new Color(255, 255, 204));

        JScrollPane cus_List = new JScrollPane();
        cus_List.setBounds(0, 244, 950, 500);
        add(cus_List);
        cus_List.setFont(new Font("Arial", Font.PLAIN, 16));
        // tạo title cho table
        cus_List.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                "Customer List",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 16),
                Color.BLACK
        ));

        add(cus_List);


//		model = new DefaultTableModel(new String[]{"ID", "Name", "Phone", "Username", "Password", "Address", "Role"}, 0);
        String[] columnNames = {"Id", "Name", "Phone", "Address","Score","Rank"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        cus_List.setViewportView(table);
    }


    public void removeCustomerFromTable(int row) {
        model.removeRow(row);
    }

    public void setAddButtonListener(ActionListener listener) {
        Save_Button.addActionListener(listener);
    }

    public void setEditButtonListener(ActionListener listener) {
        Edit_Button.addActionListener(listener);
    }

    public void setDeleteButtonListener(ActionListener listener) {
        Delete_Button.addActionListener(listener);
    }

    public JTable getTable() {
        return table;
    }
    public int getSeclectedRow(){
        return table.getSelectedRow();
    }
    public void clear(){
        Name_textField.setText("");
        Address_textField.setText("");
        Phone_textField.setText("");
        ID_textField.setText("");
        Score_textField.setText("");
    }
    public void addCustomerToTable(String id, String name,String  phone, String address ,int loyaltyPoints,String membershipLevels) {
        model.addRow(new Object[]{id, name,phone ,address, loyaltyPoints,membershipLevels});
    }

    public void updateCustomerInTable(int row, String id, String name, String phone, String address  ) {
        model.setValueAt(id, row, 0);
        model.setValueAt(name, row, 1);
        model.setValueAt(phone, row, 2);
        model.setValueAt(address, row, 3);


    }


    public void setEmployeeData(String id, String name ,String phone,String address) {
        ID_textField.setText(id);
        Name_textField.setText(name);
        Phone_textField.setText(phone);
        Address_textField.setText(address);
//        Score_textField.setText(String.valueOf(Score));

    }
}
>>>>>>> Stashed changes
