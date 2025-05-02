package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import view.UI.Hover;

import javax.swing.JLabel;
import java.awt.Rectangle;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerView extends JPanel {


    /**
     * Create the panel.
     */

    private static final long serialVersionUID = 1L;
    private JTable table;
    private JTextField ID_textField;
    private JTextField Name_textField;
    private JTextField Address_textField;
    private JTextField Phone_textField;
    private JTextField Score_textField;
    private JTextField Rank_textField;
    private JTextField Search_textField;
    private DefaultTableModel model;
    //cac nut
    private JButton btnDel, btnEdit, btnSave, ID_Button;

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
    public String getSearch_textField(){return Search_textField.getText();}
    public String getScore_textField() {
        return Score_textField.getText();
    }
    public JTable getCustomerTable() {
        return table;
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

        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Arial", Font.PLAIN, 16));
        lblName.setBounds(new Rectangle(176, 54, 55, 21));
        lblName.setBounds(122, 55, 97, 21);
        panel_top.add(lblName);

        JLabel lblAddress = new JLabel("Address");
        lblAddress.setFont(new Font("Arial", Font.PLAIN, 16));
        lblAddress.setBounds(new Rectangle(176, 36, 55, 21));
        lblAddress.setBounds(122, 121, 100, 21);
        panel_top.add(lblAddress);

        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setFont(new Font("Arial", Font.PLAIN, 16));
        lblPhone.setBounds(new Rectangle(176, 54, 55, 21));
        lblPhone.setBounds(454, 55, 55, 21);
        panel_top.add(lblPhone);


//        JLabel lblRank = new JLabel("Rank");
//        lblRank.setFont(new Font("Arial", Font.PLAIN, 16));
//        lblRank.setBounds(new Rectangle(176, 36, 55, 21));
//        lblRank.setBounds(370, 126, 55, 21);
//        panel_top.add(lblRank);

        JLabel lblScore = new JLabel("Score");
        lblScore.setFont(new Font("Arial", Font.PLAIN, 16));
        lblScore.setBounds(new Rectangle(176, 36, 55, 21));
        lblScore.setBounds(454, 121, 55, 21);
        panel_top.add(lblScore);

        Name_textField = new JTextField();
        Name_textField.setBounds(238, 55, 140, 25);  // Đặt Name ở vị trí ban đầu của Address
        panel_top.add(Name_textField);
        Hover.addPlaceholder(Name_textField, "Enter Name");
        Hover.roundTextField(Name_textField, 15, Color.WHITE, Color.LIGHT_GRAY);

        // Sửa vị trí của Phone_textField
        Phone_textField = new JTextField();
        Phone_textField.setColumns(10);
        Phone_textField.setBounds(543, 55, 140, 25); // Cùng vị trí X với JLabel "Phone", Y có thể điều chỉnh
        panel_top.add(Phone_textField);
        Hover.addPlaceholder(Phone_textField, "Enter Phone");
        Hover.roundTextField(Phone_textField, 15, Color.WHITE, Color.LIGHT_GRAY);

        // Sửa vị trí của Address_textField
        Address_textField = new JTextField();
        Address_textField.setColumns(10);
        Address_textField.setBounds(238, 121, 140, 25); // Cùng vị trí X với JLabel "Address" (gần đúng), Y có thể điều chỉnh
        panel_top.add(Address_textField);
        Hover.addPlaceholder(Address_textField, "Enter Address");
        Hover.roundTextField(Address_textField, 15, Color.WHITE, Color.LIGHT_GRAY);


        Score_textField = new JTextField();
        Score_textField.setColumns(10);
        Score_textField.setBounds(543, 121, 140, 25);  // Đặt Score ở vị trí ban đầu của Rank
        panel_top.add(Score_textField);
        Hover.addPlaceholder(Score_textField, "Enter Score");
        Hover.roundTextField(Score_textField, 15, Color.WHITE, Color.LIGHT_GRAY);


        btnEdit = new JButton("Edit");
        btnEdit.setIcon(new ImageIcon(ProductView.class.getResource("/view/Icon/Edit_Icon.png")));
        btnEdit.setBackground(new Color(255, 255, 204));
        btnEdit.setFont(new Font("Arial", Font.PLAIN, 16));
        btnEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnEdit.setBounds(110, 167, 69, 63);
        btnEdit.setFocusPainted(false);
        btnEdit.setBorderPainted(false);
        btnEdit.setContentAreaFilled(false);
        btnEdit.setHorizontalTextPosition(SwingConstants.CENTER);
        btnEdit.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel_top.add(btnEdit);
        Hover.addHoverButtonEffect(btnEdit, new Color(0, 102, 204), 0.8f);

        btnSave = new JButton("Save");
        btnSave.setIcon(new ImageIcon(ProductView.class.getResource("/view/Icon/save_Icon.png")));
        btnSave.setBackground(new Color(255, 255, 223));
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnSave.setFont(new Font("Arial", Font.PLAIN, 16));
        btnSave.setBounds(20, 167, 69, 63);
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setContentAreaFilled(false);
        btnSave.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel_top.add(btnSave);
        Hover.addHoverButtonEffect(btnSave, new Color(0, 102, 204), 0.8f);

        btnDel = new JButton("Delete");
        btnDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnDel.setIcon(new ImageIcon(ProductView.class.getResource("/view/Icon/delete_Icon.png")));
        btnDel.setBackground(new Color(255, 255, 204));
        btnDel.setFont(new Font("Arial", Font.PLAIN, 16));
        btnDel.setBounds(189, 167, 87, 63);
        btnDel.setFocusPainted(false);
        btnDel.setBorderPainted(false);
        btnDel.setContentAreaFilled(false);
        btnDel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnDel.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel_top.add(btnDel);
        Hover.addHoverButtonEffect(btnDel, new Color(0, 102, 204), 0.8f);

        ImageIcon searchIcon = new ImageIcon(CustomerView.class.getResource("/view/Icon/Search_Icon.png"));

        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBounds(684, 187, 234, 24);
        searchPanel.setBackground(Color.WHITE);

        JLabel searchLabel = new JLabel(searchIcon);
        searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        searchPanel.add(searchLabel, BorderLayout.WEST);

        Search_textField = new JTextField();
        Search_textField.setBorder(null);
        Search_textField.setColumns(10);
        searchPanel.add(Search_textField, BorderLayout.CENTER);
        Hover.addPlaceholder(Search_textField, "search...");
        Hover.roundPanel(searchPanel, 20, Color.WHITE, Color.GRAY);

        panel_top.add(searchPanel);

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
        String[] columnNames = {"Id", "Name", "Phone", "Address", "Score", "Rank"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        cus_List.setViewportView(table);
        Hover.customizeTableHeader(table);
    }

    public void removeCustomerFromTable(int row) {
        model.removeRow(row);
    }

    public void setAddButtonListener(ActionListener listener) {
        btnSave.addActionListener(listener);
    }

    public void setEditButtonListener(ActionListener listener) {
        btnEdit.addActionListener(listener);
    }

    public void setDeleteButtonListener(ActionListener listener) {
        btnDel.addActionListener(listener);
    }
    public void setSearchListener(DocumentListener listener){
        Search_textField.getDocument().addDocumentListener(listener);
    }

    public JTable getTable() {
        return table;
    }

    public int getSeclectedRow() {
        return table.getSelectedRow();
    }

    public void clear() {
        Name_textField.setText("");
        Address_textField.setText("");
        Phone_textField.setText("");
        Score_textField.setText("");
    }

    public void addCustomerToTable(String id, String name, String phone, String address, int loyaltyPoints, String membershipLevels) {
        model.addRow(new Object[]{id, name, phone, address, loyaltyPoints, membershipLevels});
    }

    public void updateCustomerInTable(int row, String id, String name, String phone, String address ,int point, String member) {
        model.setValueAt(id, row, 0);
        model.setValueAt(name, row, 1);
        model.setValueAt(phone, row, 2);
        model.setValueAt(address, row, 3);
        model.setValueAt(point, row, 4);
        model.setValueAt(member, row, 5);

    }

    public void setEmployeeData(String id, String name, String phone, String address, int point) {
        Name_textField.setText(name);
        Phone_textField.setText(phone);
        Address_textField.setText(address);
        Score_textField.setText(String.valueOf(point));
    }

    public JTextField getScoreTextField() {
        return Score_textField;
    }

    public void setScore_textField(String score) {
        this.Score_textField.setText(score);
    }
    public JTextField getRankTextField() {
        return Rank_textField;
    }

    public JTextField getIDTextField() {
        return ID_textField;
    }

}