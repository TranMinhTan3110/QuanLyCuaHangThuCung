package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

import view.UI.Hover;

import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CustomerView extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTable table;
    private JTextField Name_textField;
    private JTextField Phone_textField;
    private JTextField Address_textField;
    private JTextField Score_textField;
    private JTextField Search_textField;
    private DefaultTableModel model;
    private JButton btnSave, btnEdit, btnDel;

    // Biến phân trang
    private int currentPage = 1;
    private int rowsPerPage = 21; // Số hàng trên mỗi trang 
    private int totalRows = 0;
    private int totalPages = 0;
    private List<Object[]> allData = new ArrayList<>();
    private JLabel pageLabel;

    public CustomerView() {
        setLayout(null);
        setBounds(0, 0, 950, 750);

        JPanel panel_top = new JPanel();
        panel_top.setBackground(new Color(200, 220, 240)); // Màu nền xanh nhạt
        panel_top.setBounds(0, 0, 950, 240);
        add(panel_top);
        panel_top.setLayout(null);

        JLabel lblName = new JLabel("Name");
        lblName.setFont(new Font("Arial", Font.PLAIN, 16));
        lblName.setBounds(161, 32, 100, 25);
        panel_top.add(lblName);

        JLabel lblPhone = new JLabel("Phone");
        lblPhone.setFont(new Font("Arial", Font.PLAIN, 16));
        lblPhone.setBounds(497, 32, 100, 25);
        panel_top.add(lblPhone);

        JLabel lblAddress = new JLabel("Address");
        lblAddress.setFont(new Font("Arial", Font.PLAIN, 16));
        lblAddress.setBounds(161, 80, 100, 25);
        panel_top.add(lblAddress);

        JLabel lblScore = new JLabel("Score");
        lblScore.setFont(new Font("Arial", Font.PLAIN, 16));
        lblScore.setBounds(497, 80, 100, 25);
        panel_top.add(lblScore);

        Name_textField = new JTextField();
        Name_textField.setBounds(288, 32, 100, 25);
        panel_top.add(Name_textField);
        Hover.addPlaceholder(Name_textField, "Enter Name");
//        Hover.roundTextField(Name_textField, 15, Color.WHITE, Color.LIGHT_GRAY);

        Phone_textField = new JTextField();
        Phone_textField.setBounds(607, 30, 100, 25);
        panel_top.add(Phone_textField);
        Hover.addPlaceholder(Phone_textField, "Enter Phone");
//        Hover.roundTextField(Phone_textField, 15, Color.WHITE, Color.LIGHT_GRAY);

        Address_textField = new JTextField();
        Address_textField.setBounds(288, 82, 100, 25);
        panel_top.add(Address_textField);
        Hover.addPlaceholder(Address_textField, "Enter Address");
//        Hover.roundTextField(Address_textField, 15, Color.WHITE, Color.LIGHT_GRAY);

        Score_textField = new JTextField();
        Score_textField.setBounds(607, 82, 100, 25);
        panel_top.add(Score_textField);
        Hover.addPlaceholder(Score_textField, "Enter Score");
//        Hover.roundTextField(Score_textField, 15, Color.WHITE, Color.LIGHT_GRAY);

        btnSave = new JButton("Save");
        btnSave.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Thêm logic lưu dữ liệu tại đây
            }
        });
        btnSave.setIcon(new ImageIcon(CustomerView.class.getResource("/view/Icon/save_Icon.png")));
        btnSave.setBackground(new Color(144, 238, 144)); // Màu xanh lá nhạt
        btnSave.setFont(new Font("Arial", Font.PLAIN, 16));
        btnSave.setBounds(20, 164, 80, 57);
        btnSave.setFocusPainted(false);
        btnSave.setBorderPainted(false);
        btnSave.setContentAreaFilled(false);
        btnSave.setHorizontalTextPosition(SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel_top.add(btnSave);
        Hover.addHoverButtonEffect(btnSave, new Color(0, 100, 0), 0.8f);

        btnEdit = new JButton("Edit");
        btnEdit.setIcon(new ImageIcon(CustomerView.class.getResource("/view/Icon/Edit_Icon.png")));
        btnEdit.setBackground(new Color(173, 216, 230)); // Màu xanh nhạt
        btnEdit.setFont(new Font("Arial", Font.PLAIN, 16));
        btnEdit.setBounds(100, 164, 80, 57);
        btnEdit.setFocusPainted(false);
        btnEdit.setBorderPainted(false);
        btnEdit.setContentAreaFilled(false);
        btnEdit.setHorizontalTextPosition(SwingConstants.CENTER);
        btnEdit.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel_top.add(btnEdit);
        Hover.addHoverButtonEffect(btnEdit, new Color(0, 0, 139), 0.8f);

        btnDel = new JButton("Delete");
        btnDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Thêm logic xóa dữ liệu tại đây
            }
        });
        btnDel.setIcon(new ImageIcon(CustomerView.class.getResource("/view/Icon/delete_Icon.png")));
        btnDel.setBackground(new Color(255, 182, 193)); // Màu hồng nhạt
        btnDel.setFont(new Font("Arial", Font.PLAIN, 16));
        btnDel.setBounds(181, 164, 80, 57);
        btnDel.setFocusPainted(false);
        btnDel.setBorderPainted(false);
        btnDel.setContentAreaFilled(false);
        btnDel.setHorizontalTextPosition(SwingConstants.CENTER);
        btnDel.setVerticalTextPosition(SwingConstants.BOTTOM);
        panel_top.add(btnDel);
        Hover.addHoverButtonEffect(btnDel, new Color(139, 0, 0), 0.8f);

        ImageIcon searchIcon = new ImageIcon(CustomerView.class.getResource("/view/Icon/Search_Icon.png"));
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBounds(718, 191, 200, 30);
        searchPanel.setBackground(Color.WHITE);
        JLabel searchLabel = new JLabel(searchIcon);
        searchLabel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        Hover.roundPanel(searchPanel, 15, Color.WHITE, Color.GRAY);
        panel_top.add(searchPanel);
        Search_textField = new JTextField();
        searchPanel.add(Search_textField, BorderLayout.CENTER);
        Search_textField.setBorder(null);
        Search_textField.setColumns(10);
        Hover.addPlaceholder(Search_textField, "search...");
        
        JButton DMButton = new JButton("DARK MODE");
        DMButton.setBackground(new Color(240, 240, 240));
        DMButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
        DMButton.setBounds(288, 182, 120, 30);
        DMButton.setFocusPainted(false);
        panel_top.add(DMButton);

        JScrollPane cus_List = new JScrollPane();
        cus_List.setBounds(0, 250, 940, 395);
        add(cus_List);
        cus_List.setFont(new Font("Arial", Font.PLAIN, 16));
        cus_List.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK),
                "Customer List", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), Color.BLACK));

        String[] columnNames = {"ID", "NAME", "PHONE", "ADDRESS", "SCORE", "RANK"};
        model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);
        cus_List.setViewportView(table);
        Hover.customizeTableHeader(table);

        // Thêm sự kiện nhấp chuột để hiển thị popup
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row >= 0 && row < table.getRowCount()) {
                    showDetailsDialog(row);
                }
            }
        });

        // Thêm điều khiển phân trang
        JPanel paginationPanel = new JPanel();
        paginationPanel.setBounds(0, 643, 950, 50);
        paginationPanel.setBackground(new Color(240, 240, 240));
        add(paginationPanel);
        paginationPanel.setLayout(null);

        JButton btnPrev = new JButton("Prev");
        btnPrev.setBounds(679, 10, 70, 30);
        btnPrev.setFont(new Font("Arial", Font.PLAIN, 16));
        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                updateTableForCurrentPage();
            }
        });
        paginationPanel.add(btnPrev);

        pageLabel = new JLabel("Trang");
        pageLabel.setBounds(766, 10, 70, 30);
        pageLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        paginationPanel.add(pageLabel);

        JButton btnNext = new JButton("Next");
        btnNext.setBounds(846, 10, 70, 30);
        btnNext.setFont(new Font("Arial", Font.PLAIN, 16));
        btnNext.addActionListener(e -> {
            if (currentPage < totalPages) {
                currentPage++;
                updateTableForCurrentPage();
            }
        });
        paginationPanel.add(btnNext);

    }

    private void updateTableForCurrentPage() {
        model.setRowCount(0); // Xóa bảng
        int startIndex = (currentPage - 1) * rowsPerPage;
        int endIndex = Math.min(startIndex + rowsPerPage, allData.size());

        for (int i = startIndex; i < endIndex; i++) {
            model.addRow(allData.get(i));
        }
        updatePaginationLabel();
    }

    private void updatePaginationLabel() {
        pageLabel.setText("Page " + currentPage + "/" + totalPages);
    }

    public void removeCustomerFromTable(int row) {
        int globalIndex = (currentPage - 1) * rowsPerPage + row;
        if (globalIndex >= 0 && globalIndex < allData.size()) {
            allData.remove(globalIndex);
            totalRows = allData.size();
            totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
            if (currentPage > totalPages && currentPage > 1) currentPage--;
            updateTableForCurrentPage();
        }
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

    public void setSearchListener(DocumentListener listener) {
        Search_textField.getDocument().addDocumentListener(listener);
    }

    public JTable getTable() {
        return table;
    }

    public int getSelectedRow() {
        return table.getSelectedRow();
    }

    public void clear() {
        allData.clear();
        totalRows = 0;
        totalPages = 0;
        currentPage = 1;
        updateTableForCurrentPage();
        Name_textField.setText("");
        Phone_textField.setText("");
        Address_textField.setText("");
        Score_textField.setText("");
    }

    public void addCustomerToTable(String id, String name, String phone, String address, String score, String rank) {
        allData.add(new Object[]{id, name, phone, address, score, rank});
        totalRows = allData.size();
        totalPages = (int) Math.ceil((double) totalRows / rowsPerPage);
        updateTableForCurrentPage();
    }

    public void updateCustomerInTable(int row, String id, String name, String phone, String address, String score, String rank) {
        int globalIndex = (currentPage - 1) * rowsPerPage + row;
        if (globalIndex >= 0 && globalIndex < allData.size()) {
            allData.set(globalIndex, new Object[]{id, name, phone, address, score, rank});
            updateTableForCurrentPage();
        }
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

    public String getName_textField() {
        return Name_textField.getText();
    }

    public String getAddress_textField() {
        return Address_textField.getText();
    }

    public String getPhone_textField() {
        return Phone_textField.getText();
    }

    public String getSearch_textField() {
        return Search_textField.getText();
    }

    public String getScore_textField() {
        return Score_textField.getText();
    }

    public JTable getCustomerTable() {
        return table;
    }

    // Phương thức hiển thị cửa sổ chi tiết
    private void showDetailsDialog(int row) {
        // Lấy dữ liệu từ hàng được chọn trong bảng
        Object[] data = allData.get((currentPage - 1) * rowsPerPage + row);

        // Tạo JDialog
        JDialog dialog = new JDialog((JDialog) null, "Customer Details", true);
        dialog.getContentPane().setLayout(null);
        dialog.setSize(400, 350);
        dialog.setLocationRelativeTo(this);

        // Thêm các label để hiển thị thông tin
        JLabel lblId = new JLabel("ID: " + data[0]);
        lblId.setBounds(20, 20, 250, 20);
        dialog.getContentPane().add(lblId);

        JLabel lblName = new JLabel("Name: " + data[1]);
        lblName.setBounds(20, 50, 250, 20);
        dialog.getContentPane().add(lblName);

        JLabel lblPhone = new JLabel("Phone: " + data[2]);
        lblPhone.setBounds(20, 80, 250, 20);
        dialog.getContentPane().add(lblPhone);

        JLabel lblAddress = new JLabel("Address: " + data[3]);
        lblAddress.setBounds(20, 110, 250, 20);
        dialog.getContentPane().add(lblAddress);

        JLabel lblScore = new JLabel("Score: " + data[4]);
        lblScore.setBounds(20, 140, 250, 20);
        dialog.getContentPane().add(lblScore);

        JLabel lblRank = new JLabel("Rank: " + data[5]);
        lblRank.setBounds(20, 170, 250, 20);
        dialog.getContentPane().add(lblRank);

        // Thêm nút Edit và Delete 
        JButton btnEDit = new JButton("Edit");
        btnEDit.setBounds(200, 270, 80, 25);
        btnEDit.setFocusPainted(false);
        dialog.getContentPane().add(btnEDit);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(300, 270, 80, 25);
        btnDelete.setFocusPainted(false);
        dialog.getContentPane().add(btnDelete);

        // Hiển thị dialog
        dialog.setVisible(true);
    }

    public int getSeclectedRow() {
        return table.getSelectedRow();
    }
}