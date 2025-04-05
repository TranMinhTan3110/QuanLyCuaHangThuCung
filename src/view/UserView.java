package view;

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
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
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
import view.UI.Hover;
public class UserView extends JPanel {

	private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JTextField textField_1;
    private JTextField textField_2;
    private JTextField textField_3;
    private JTextField textField_4;
    private JTextField textField_5;
    private JTable table;

    public UserView() {
        setLayout(null);
        setBounds(0, 0, 950, 750);


        JPanel panel_top = new JPanel();
        panel_top.setBackground(new Color(255, 255, 223));
        panel_top.setBounds(0, 0, 950, 240);
        panel_top.setLayout(null);
        add(panel_top);

        JLabel lblNewLabel_1 = new JLabel("ID:");
        lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNewLabel_1.setBounds(95, 46, 67, 21);
        panel_top.add(lblNewLabel_1);

        textField = new JTextField();
        textField.setBounds(203, 43, 125, 32);
        Hover.addPlaceholder(textField, "Enter ID");
        panel_top.add(textField);
        textField.setColumns(10);

        JLabel lblNewLabel_1_1 = new JLabel("Phone:");
        lblNewLabel_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNewLabel_1_1.setBounds(95, 96, 68, 21);
        panel_top.add(lblNewLabel_1_1);

        textField_1 = new JTextField();
        textField_1.setColumns(10);
        textField_1.setBounds(203, 93, 125, 32);
        Hover.addPlaceholder(textField_1, "Enter Phone");
        panel_top.add(textField_1);

        JLabel lblNewLabel_1_1_1 = new JLabel("UserName:");
        lblNewLabel_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNewLabel_1_1_1.setBounds(95, 148, 88, 21);
        panel_top.add(lblNewLabel_1_1_1);

        textField_2 = new JTextField();
        textField_2.setColumns(10);
        textField_2.setBounds(203, 145, 125, 32);
        Hover.addPlaceholder(textField_2, "Enter Username");
        panel_top.add(textField_2);

        JLabel lblNewLabel_1_2 = new JLabel("Name:");
        lblNewLabel_1_2.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNewLabel_1_2.setBounds(419, 46, 67, 21);
        panel_top.add(lblNewLabel_1_2);

        textField_3 = new JTextField();
        textField_3.setColumns(10);
        textField_3.setBounds(530, 43, 125, 32);
        Hover.addPlaceholder(textField_3, "Enter Name");
        panel_top.add(textField_3);

        JLabel lblNewLabel_1_1_2 = new JLabel("Address:");
        lblNewLabel_1_1_2.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNewLabel_1_1_2.setBounds(419, 96, 68, 21);
        panel_top.add(lblNewLabel_1_1_2);

        textField_4 = new JTextField();
        textField_4.setColumns(10);
        textField_4.setBounds(530, 93, 125, 32);
        Hover.addPlaceholder(textField_4, "Enter Address");
        panel_top.add(textField_4);

        JLabel lblNewLabel_1_1_2_1 = new JLabel("Password:");
        lblNewLabel_1_1_2_1.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNewLabel_1_1_2_1.setBounds(419, 148, 88, 21);
        panel_top.add(lblNewLabel_1_1_2_1);

        textField_5 = new JTextField();
        textField_5.setColumns(10);
        textField_5.setBounds(530, 145, 125, 32);
        Hover.addPlaceholder(textField_5, "Enter Password");
        panel_top.add(textField_5);

        JComboBox comboBox = new JComboBox();
        comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
        comboBox.setModel(new DefaultComboBoxModel(new String[] {"Admin", "User"}));
        comboBox.setBounds(203, 192, 76, 32);
        panel_top.add(comboBox);

        JLabel lblNewLabel_1_1_1_1 = new JLabel("Role");
        lblNewLabel_1_1_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
        lblNewLabel_1_1_1_1.setBounds(95, 190, 88, 21);
        panel_top.add(lblNewLabel_1_1_1_1);

        JButton btnAdd = new JButton("Add");
        btnAdd.setBackground(new Color(255, 255, 204));
        btnAdd.setFont(new Font("Arial", Font.PLAIN, 16));
        btnAdd.setBounds(727, 40, 88, 32);
        btnAdd.setFocusPainted(false);
        Hover.addHoverEffect(btnAdd, new Color(128, 128, 100), new Color(255, 255, 204));
        panel_top.add(btnAdd);

        JButton btnEdit = new JButton("Edit");
        btnEdit.setBackground(new Color(255, 255, 204));
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Edit functionality here
            }
        });
        btnEdit.setFont(new Font("Arial", Font.PLAIN, 16));
        btnEdit.setBounds(727, 93, 88, 32);
        btnEdit.setFocusPainted(false);
        Hover.addHoverEffect(btnEdit, new Color(128, 128, 100), new Color(255, 255, 204));
        panel_top.add(btnEdit);

        JButton btnDel = new JButton("Delete");
        btnDel.setBackground(new Color(255, 255, 204));
        btnDel.setFont(new Font("Arial", Font.PLAIN, 16));
        btnDel.setBounds(727, 145, 88, 32);
        btnDel.setFocusPainted(false);
        Hover.addHoverEffect(btnDel, new Color(128, 128, 100), new Color(255, 255, 204));
        panel_top.add(btnDel);

        // Table to display user data.
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setFont(new Font("Arial", Font.PLAIN, 16));
        scrollPane.setBounds(0, 244, 950, 500);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK),
            "Manager Users",
            TitledBorder.CENTER,
            TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 16),
            Color.BLACK
        ));
        add(scrollPane);

        DefaultTableModel model = new DefaultTableModel(
            new String[]{"ID", "Name", "Phone", "Username", "Password", "Address", "Role"}, 0);
        table = new JTable(model);
        scrollPane.setViewportView(table);

        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String id = textField.getText();
                String name = textField_3.getText();
                String phone = textField_1.getText();
                String username = textField_2.getText();
                String password = textField_5.getText();
                String address = textField_4.getText();
                String role = comboBox.getSelectedItem().toString();
                model.addRow(new Object[]{id, name, phone, username, password, address, role});
                textField.setText("");
                textField_3.setText("");
                textField_1.setText("");
                textField_2.setText("");
                textField_4.setText("");
                textField_5.setText("");
            }
        });
    }

    
}
