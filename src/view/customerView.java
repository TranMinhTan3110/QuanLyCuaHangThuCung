package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import view.UI.Hover;

import javax.swing.JLabel;
import java.awt.Rectangle;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

import javax.swing.JTextField;
import javax.swing.JButton;
import view.UI.Hover;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class customerView extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable table;
	private JTextField Name_textField;
	private JTextField Address_textField;
	private JTextField Phone_textField;
	private JTextField ID_textField;

	/**
	 * Create the panel.
	 */
	public customerView() {
		setLayout(null);
		setBounds(0,0,950,750);
		
		JPanel panel_top = new JPanel();
		panel_top.setBackground(new Color(255, 255, 223));
		panel_top.setBounds(0, 0, 950, 240);
		add(panel_top);
		panel_top.setLayout(null);
		
		JLabel lblName= new JLabel("Name");
		lblName.setFont(new Font("Arial", Font.PLAIN, 16));
		lblName.setBounds(new Rectangle(380, 36, 55, 21));
		panel_top.add(lblName);
		
		JLabel lblAddress = new JLabel("Address");
		lblAddress.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAddress.setBounds(new Rectangle(176, 54, 55, 21));
		lblAddress.setBounds(380, 126, 97, 21);
		panel_top.add(lblAddress);
		
		JLabel lblPhone = new JLabel("Phone");
		lblPhone.setFont(new Font("Arial", Font.PLAIN, 16));
		lblPhone.setBounds(new Rectangle(176, 54, 55, 21));
		lblPhone.setBounds(176, 126, 55, 21);
		panel_top.add(lblPhone);
		
		JLabel lblID = new JLabel("ID");
		lblID.setFont(new Font("Arial", Font.PLAIN, 16));
		lblID.setBounds(new Rectangle(176, 36, 55, 21));
		lblID.setBounds(176, 36, 55, 21);
		panel_top.add(lblID);
		
		Name_textField = new JTextField();
		Name_textField.setBounds(380, 67, 109, 25);
		panel_top.add(Name_textField);
		Hover.addPlaceholder(Name_textField, "Enter Name");
		Name_textField.setColumns(10);
		
		
		Address_textField = new JTextField();
		Address_textField.setColumns(10);
		Address_textField.setBounds(380, 157, 109, 25);
		panel_top.add(Address_textField);
		Hover.addPlaceholder(Address_textField, "Enter Address");
		
		Phone_textField = new JTextField();
		Phone_textField.setColumns(10);
		Phone_textField.setBounds(176, 157, 109, 25);
		panel_top.add(Phone_textField);
		Hover.addPlaceholder(Phone_textField, "Enter Phone");
		
		ID_textField = new JTextField();
		ID_textField.setColumns(10);
		ID_textField.setBounds(176, 67, 109, 25);
		panel_top.add(ID_textField);
		Hover.addPlaceholder(ID_textField, "Enter ID");
		
		JButton Edit_Button = new JButton("Edit");
		Edit_Button.setBackground(new Color(255, 255, 204));
		Edit_Button.setFont(new Font("Arial", Font.PLAIN, 16));
		Edit_Button.setBounds(600, 36, 113, 30);
		Edit_Button.setFocusPainted(false);
		panel_top.add(Edit_Button);
		Hover.addHoverEffect(Edit_Button, new Color(128, 128, 100), new Color(255, 255, 204));
		
		JButton Save_Button = new JButton("Save");
		Save_Button.setBackground(new Color(255, 255, 204));
		Save_Button.setFont(new Font("Arial", Font.PLAIN, 16));
		Save_Button.setBounds(600, 106, 113, 30);
		Save_Button.setFocusPainted(false);
		panel_top.add(Save_Button);
		Hover.addHoverEffect(Save_Button, new Color(128, 128, 100), new Color(255, 255, 204));
		
		JButton Delete_Button = new JButton("Delete");
		Delete_Button.setBackground(new Color(255, 255, 204));
		Delete_Button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		Delete_Button.setFont(new Font("Arial", Font.PLAIN, 16));
		Delete_Button.setBounds(600, 175, 113, 30);
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
		
		table = new JTable();
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Id", "Name", "Address", "Phone","Rank","Score"
			}
		));
		cus_List.setViewportView(table);

	}
}
