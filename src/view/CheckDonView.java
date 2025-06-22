package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.Consumer;

public class CheckDonView extends JPanel {
    private DefaultTableModel model;
    private JTable table;
    private JTextField searchField;
    private TableRowSorter<DefaultTableModel> sorter;
    private int hoveredRow = -1;

    // Pagination
    private int rowsPerPage = 4;
    private int currentPage = 1;
    private JLabel lblPageInfo;
    private JButton btnPrev, btnNext, btnEdit, btnDelete ;
    private Object[][] allData;
    private String[] columns;

    // Dark mode
    private boolean isDarkMode = false;
    private JButton btnToggleDarkMode;

    public CheckDonView() {
        setBackground(new Color(200, 220, 240));
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        // Title
        JLabel title = new JLabel("Pet Services Orders");
        title.setFont(new Font("SansSerif", Font.BOLD, 36));
        title.setForeground(new Color(40, 40, 40));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.insets = new Insets(30, 38, 10, 0);
        gbc.anchor = GridBagConstraints.WEST;
        add(title, gbc);

        // Search panel with icon inside
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(new Color(245, 245, 250));
        searchPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        searchPanel.setPreferredSize(new Dimension(260, 40));

        JLabel searchIcon = new JLabel("\uD83D\uDD0D"); // Unicode magnifier
        searchIcon.setBorder(new EmptyBorder(0, 8, 0, 0));
        searchPanel.add(searchIcon, BorderLayout.WEST);

        searchField = new JTextField();
        searchField.setFont(new Font("SansSerif", Font.PLAIN, 18));
        searchField.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
        searchField.setBackground(new Color(245, 245, 250));
        searchField.setForeground(new Color(60, 60, 60));
        searchField.setToolTipText("Type to search orders...");
        searchPanel.add(searchField, BorderLayout.CENTER);

        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 1;
        gbc.insets = new Insets(30, 0, 10, 38);
        gbc.anchor = GridBagConstraints.NORTHEAST;
        add(searchPanel, gbc);

        // Dark mode toggle button
        btnToggleDarkMode = new JButton("Dark Mode");
        btnToggleDarkMode.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnToggleDarkMode.setFocusPainted(false);
        btnToggleDarkMode.setBackground(new Color(220, 220, 220));
        btnToggleDarkMode.addActionListener(e -> toggleDarkMode());
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 1;
        gbc.insets = new Insets(75, 0, 0, 38);
        gbc.anchor = GridBagConstraints.NORTHEAST;
        add(btnToggleDarkMode, gbc);

        // Table panel with rounded corners
        JPanel tablePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(isDarkMode ? new Color(60, 63, 65) : new Color(255, 250, 245));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
                g2d.dispose();
            }
        };
        tablePanel.setOpaque(false);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(new EmptyBorder(12, 12, 12, 12));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 30, 0, 30);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1; gbc.weighty = 1;
        add(tablePanel, gbc);

        // Table model with status (no Actions column)
        columns = new String[]{"Pet Name", "Owner Name", "Service", "Appointment Date", "Status"};
        allData = new Object[][]{};
        model = new DefaultTableModel(null, columns) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    if (row == hoveredRow) {
                        c.setBackground(isDarkMode ? new Color(70, 80, 100) : new Color(230, 240, 255));
                    } else {
                        c.setBackground(isDarkMode ? new Color(60, 63, 65) : Color.WHITE);
                    }
                } else {
                    c.setBackground(isDarkMode ? new Color(90, 100, 120) : new Color(200, 220, 240));
                }
                c.setForeground(isDarkMode ? Color.WHITE : new Color(40, 40, 40));
                return c;
            }
        };
        table.setFont(new Font("SansSerif", Font.PLAIN, 18));
        table.setRowHeight(44);
        table.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 20));
        table.getTableHeader().setBackground(new Color(255, 250, 245));
        table.getTableHeader().setForeground(new Color(40, 40, 40));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));
        table.setBackground(Color.WHITE);
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);

        // Status renderer
        table.getColumnModel().getColumn(4).setCellRenderer(new StatusCellRenderer());

        // Tooltip for accessibility
        table.setToolTipText("Double-click a row to view details");

        // Hover effect
        table.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                if (row != hoveredRow) {
                    hoveredRow = row;
                    table.repaint();
                }
            }
        });
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                hoveredRow = -1;
                table.repaint();
            }
        });

        // Search/filter
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            private void filter() {
                String text = searchField.getText();
                if (text.trim().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Pagination controls
        JPanel paginationPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        paginationPanel.setOpaque(false);
        btnPrev = new JButton("Prev");
        btnNext = new JButton("Next");
        lblPageInfo = new JLabel();
        btnPrev.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnNext.setFont(new Font("SansSerif", Font.PLAIN, 14));
        btnPrev.addActionListener(e -> { if (currentPage > 1) { currentPage--; updateTablePage(); } });
        btnNext.addActionListener(e -> { if (currentPage < getTotalPages()) { currentPage++; updateTablePage(); } });
        paginationPanel.add(btnPrev);
        paginationPanel.add(lblPageInfo);
        paginationPanel.add(btnNext);
        tablePanel.add(paginationPanel, BorderLayout.SOUTH);

        // Load first page
        updateTablePage();
    }

    private void updateTablePage() {
        model.setRowCount(0);
        int start = (currentPage - 1) * rowsPerPage;
        int end = Math.min(start + rowsPerPage, allData.length);
        for (int i = start; i < end; i++) {
            model.addRow(allData[i]);
        }
        lblPageInfo.setText("Page " + currentPage + " / " + getTotalPages());
        btnPrev.setEnabled(currentPage > 1);
        btnNext.setEnabled(currentPage < getTotalPages());
    }

    private int getTotalPages() {
        return (int) Math.ceil((double) allData.length / rowsPerPage);
    }

    private void toggleDarkMode() {
        isDarkMode = !isDarkMode;
        Color bg = isDarkMode ? new Color(40, 44, 52) : new Color(200, 220, 240);
        Color fg = isDarkMode ? Color.WHITE : new Color(40, 40, 40);
        setBackground(bg);
        table.setBackground(isDarkMode ? new Color(60, 63, 65) : Color.WHITE);
        table.setForeground(fg);
        table.getTableHeader().setBackground(isDarkMode ? new Color(50, 54, 60) : new Color(255, 250, 245));
        table.getTableHeader().setForeground(fg);
        btnToggleDarkMode.setBackground(isDarkMode ? new Color(80, 80, 80) : new Color(220, 220, 220));
        btnToggleDarkMode.setForeground(fg);
        repaint();
    }

    // Status cell renderer with color
    static class StatusCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel lbl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String status = value.toString();
            switch (status) {
                case "Hoàn thành": lbl.setForeground(new Color(34, 139, 34)); break;
                case "Đang làm":   lbl.setForeground(new Color(255, 140, 0)); break;
                case "Đang chờ nhận": lbl.setForeground(new Color(220, 20, 60)); break;
                default:          lbl.setForeground(Color.GRAY);
            }
            lbl.setFont(lbl.getFont().deriveFont(Font.BOLD));
            return lbl;
        }
    }
    // CustomerComboItem class for ComboBox
    public class CustomerComboItem {
        private final int id;
        private final String name;

        public CustomerComboItem(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() { return id; }
        public String getName() { return name; }

        @Override
        public String toString() {
            return name;
        }
    }

    // Popup dialog for order details
// Popup dialog for order details (redesigned)
    public class OrderDetailDialog extends JDialog {
        private JTextField txtPetName;
        private JComboBox<CustomerComboItem> cbOwnerName;
        private JComboBox<String> cbStaff;
        private JCheckBox cbSpa, cbGrooming, cbBathing, cbVaccination;
        private JLabel lblDate;
        private Consumer<OrderUpdateData> onSaveCallback;
        private Consumer<OrderUpdateData> onDeleteCallback;
        private String originalOwnerName;


        public OrderDetailDialog(Object[] order) {
            // Dialog properties
            setTitle("Order Details");
            setModal(true);
            setSize(620, 500);
            setLocationRelativeTo(null);
            setLayout(new BorderLayout(10, 10));
            getContentPane().setBackground(new Color(245, 250, 255));

            // Initialize components first
            txtPetName = new JTextField(20);
            cbOwnerName = new JComboBox<>();
            cbStaff = new JComboBox<>(new String[]{"Nguyen Van A", "Tran Thi B", "Pham C"});
            cbSpa = new JCheckBox("Spa");
            cbGrooming = new JCheckBox("Grooming");
            cbBathing = new JCheckBox("Bathing");
            cbVaccination = new JCheckBox("Vaccination");
            lblDate = new JLabel();

            // Set initial values
            txtPetName.setText(order[0].toString());
            cbOwnerName.setSelectedItem(order[1].toString());
            lblDate.setText(order[3].toString());
            setServiceCheckBoxes(order[2].toString());

            // Info panel setup
            JPanel infoPanel = createInfoPanel(order);
            add(infoPanel, BorderLayout.CENTER);

            // Button panel setup
            JPanel buttonPanel = createButtonPanel();
            add(buttonPanel, BorderLayout.SOUTH);
        }

        private JPanel createInfoPanel(Object[] order) {
            JPanel infoPanel = new JPanel(new GridBagLayout());
            infoPanel.setBackground(new Color(245, 250, 255));
            infoPanel.setBorder(new EmptyBorder(20, 20, 10, 20));

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(12, 10, 12, 10);
            gbc.anchor = GridBagConstraints.WEST;
            gbc.fill = GridBagConstraints.HORIZONTAL;

            Font fontLabel = new Font("Segoe UI", Font.BOLD, 13);
            Font fontField = new Font("Segoe UI", Font.PLAIN, 13);

            // Add components to panel
            addLabelAndComponent(infoPanel, "Pet Name:", txtPetName, 0, fontLabel, fontField, gbc);
            addLabelAndComponent(infoPanel, "Owner Name:", cbOwnerName, 1, fontLabel, fontField, gbc);

            // Services panel
            JPanel servicePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
            servicePanel.setBackground(new Color(245, 250, 255));
            servicePanel.add(cbSpa);
            servicePanel.add(cbGrooming);
            servicePanel.add(cbBathing);
            servicePanel.add(cbVaccination);
            addLabelAndComponent(infoPanel, "Services:", servicePanel, 2, fontLabel, fontField, gbc);

            addLabelAndComponent(infoPanel, "Appointment Date:", lblDate, 3, fontLabel, fontField, gbc);
            addLabelAndComponent(infoPanel, "Assign Staff:", cbStaff, 4, fontLabel, fontField, gbc);

            return infoPanel;
        }

        private void addLabelAndComponent(JPanel panel, String labelText, Component component,
                                          int gridy, Font labelFont, Font fieldFont, GridBagConstraints gbc) {
            gbc.gridx = 0;
            gbc.gridy = gridy;
            gbc.weightx = 0.3;
            JLabel label = new JLabel(labelText);
            label.setFont(labelFont);
            panel.add(label, gbc);

            if (component instanceof JTextField) {
                ((JTextField) component).setFont(fieldFont);
            } else if (component instanceof JComboBox) {
                ((JComboBox<?>) component).setFont(fieldFont);
            } else if (component instanceof JLabel) {
                ((JLabel) component).setFont(fieldFont);
            }

            gbc.gridx = 1;
            gbc.weightx = 0.7;
            panel.add(component, gbc);
        }

        private JPanel createButtonPanel() {
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
            buttonPanel.setBackground(new Color(245, 250, 255));

            JButton btnSave = new JButton("✔ Save");
            JButton btnDelete = new JButton("🗑 Delete");

            btnSave.setBackground(new Color(46, 204, 113));
            btnSave.setForeground(Color.WHITE);
            btnDelete.setBackground(new Color(231, 76, 60));
            btnDelete.setForeground(Color.WHITE);
            btnSave.setFocusPainted(false);
            btnDelete.setFocusPainted(false);

            btnSave.addActionListener(e -> handleSave());
            btnDelete.addActionListener(e -> handleDelete());

            buttonPanel.add(btnSave);
            buttonPanel.add(btnDelete);
            return buttonPanel;
        }

        private void handleSave() {
            // Kiểm tra tên thú cưng không được để trống
            if (txtPetName.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng nhập tên thú cưng",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra phải chọn chủ nhân
            if (cbOwnerName.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn chủ nhân",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra phải chọn ít nhất 1 dịch vụ
            if (!cbSpa.isSelected() && !cbGrooming.isSelected() &&
                    !cbBathing.isSelected() && !cbVaccination.isSelected()) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn ít nhất một dịch vụ",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Kiểm tra phải chọn nhân viên
            if (cbStaff.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this,
                        "Vui lòng chọn nhân viên",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Nếu tất cả hợp lệ thì tạo dữ liệu cập nhật
            if (onSaveCallback != null) {
                OrderUpdateData updateData = new OrderUpdateData(
                        getPetName(),
                        getOwnerName(),
                        getSelectedStaff(),
                        getServiceList(),
                        getAppointmentDate(),
                        "Đang làm",
                        getSelectedCustomerId(),
                        isCustomerChanged()  // Thêm thông tin về việc khách hàng có thay đổi không
                );
                onSaveCallback.accept(updateData);
                dispose();
            }
        }

        private void handleDelete() {
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa cuộc hẹn này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION && onDeleteCallback != null) {
                OrderUpdateData updateData = new OrderUpdateData(
                        getPetName(),
                        getOwnerName(),
                        getSelectedStaff(),
                        getServiceList(),
                        getAppointmentDate(),
                        "Đã hủy đơn"
                );
                onDeleteCallback.accept(updateData);
                dispose();
            }
        }

        // Existing methods remain the same
        public void setStaffList(String[] staffNames) {
            cbStaff.setModel(new DefaultComboBoxModel<>(staffNames));
        }

        private void setServiceCheckBoxes(String services) {
            cbSpa.setSelected(services.contains("Spa"));
            cbGrooming.setSelected(services.contains("Grooming"));
            cbBathing.setSelected(services.contains("Bathing"));
            cbVaccination.setSelected(services.contains("Vaccination"));
        }

        private String getSelectedServices() {
            StringBuilder sb = new StringBuilder();
            if (cbSpa.isSelected()) sb.append("Spa, ");
            if (cbGrooming.isSelected()) sb.append("Grooming, ");
            if (cbBathing.isSelected()) sb.append("Bathing, ");
            if (cbVaccination.isSelected()) sb.append("Vaccination, ");
            return sb.length() > 0 ? sb.substring(0, sb.length() - 2) : "";
        }

        public String getPetName() { return txtPetName.getText().trim(); }
        public String getOwnerName() { return cbOwnerName.getSelectedItem().toString(); }
        public String getSelectedStaff() { return cbStaff.getSelectedItem().toString(); }
        public String getServiceList() { return getSelectedServices(); }
        public String getAppointmentDate() { return lblDate.getText(); }

        public void setOnSaveCallback(Consumer<OrderUpdateData> callback) {
            this.onSaveCallback = callback;
        }

        public void setOnDeleteCallback(Consumer<OrderUpdateData> callback) {
            this.onDeleteCallback = callback;
        }

        public boolean isCustomerChanged() {
            CustomerComboItem selected = (CustomerComboItem) cbOwnerName.getSelectedItem();
            return selected != null && !selected.getName().equals(originalOwnerName);
        }

        public int getSelectedCustomerId() {
            CustomerComboItem selected = (CustomerComboItem) cbOwnerName.getSelectedItem();
            return selected != null ? selected.getId() : 0;
        }

        public JComboBox<CustomerComboItem> getOwnerNameComboBox() {
            return cbOwnerName;
        }

    }

    public class OrderUpdateData {
        private String petName;
        private String ownerName;
        private String staffName;
        private String services;
        private String appointmentDate;
        private String status;
        private int customerId;
        private boolean customerChanged; // Thêm trường này

        public OrderUpdateData(String petName, String ownerName, String staffName,
                               String services, String appointmentDate, String status,
                               int customerId, boolean customerChanged) { // Thêm tham số
            this.petName = petName;
            this.ownerName = ownerName;
            this.staffName = staffName;
            this.services = services;
            this.appointmentDate = appointmentDate;
            this.status = status;
            this.customerId = customerId;
            this.customerChanged = customerChanged;
        }

        // Constructor cũ để tương thích ngược
        public OrderUpdateData(String petName, String ownerName, String staffName,
                               String services, String appointmentDate, String status) {
            this(petName, ownerName, staffName, services, appointmentDate, status, 0, false);
        }

        // Thêm phương thức này
        public boolean isCustomerChanged() {
            return customerChanged;
        }

        // Các getter hiện có
        public String getPetName() { return petName; }
        public String getOwnerName() { return ownerName; }
        public String getStaffName() { return staffName; }
        public String getServices() { return services; }
        public String getAppointmentDate() { return appointmentDate; }
        public String getStatus() { return status; }
        public int getCustomerId() { return customerId; }
    }

    // ----------- GETTER & SETTER -----------

    public DefaultTableModel getTableModel() {
        return model;
    }

    public void setTableModel(DefaultTableModel model) {
        this.model = model;
        table.setModel(model);
        sorter = new TableRowSorter<>(model);
        table.setRowSorter(sorter);
    }

    public JTable getTable() {
        return table;
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public void setSearchField(JTextField searchField) {
        this.searchField = searchField;
    }

    public TableRowSorter<DefaultTableModel> getSorter() {
        return sorter;
    }

    public void setSorter(TableRowSorter<DefaultTableModel> sorter) {
        this.sorter = sorter;
        table.setRowSorter(sorter);
    }

    public int getRowsPerPage() {
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage) {
        this.rowsPerPage = rowsPerPage;
        currentPage = 1;
        updateTablePage();
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        if (currentPage >= 1 && currentPage <= getTotalPages()) {
            this.currentPage = currentPage;
            updateTablePage();
        }
    }

    public JLabel getLblPageInfo() {
        return lblPageInfo;
    }

    public void setLblPageInfo(JLabel lblPageInfo) {
        this.lblPageInfo = lblPageInfo;
    }

    public JButton getBtnPrev() {
        return btnPrev;
    }

    public void setBtnPrev(JButton btnPrev) {
        this.btnPrev = btnPrev;
    }

    public JButton getBtnNext() {
        return btnNext;
    }

    public void setBtnNext(JButton btnNext) {
        this.btnNext = btnNext;
    }

    public Object[][] getAllData() {
        return allData;
    }

    public void setAllData(Object[][] allData) {
        this.allData = allData;
        currentPage = 1;
        updateTablePage();
    }

    public String[] getColumns() {
        return columns;
    }

    public void setColumns(String[] columns) {
        this.columns = columns;
    }

    public boolean isDarkMode() {
        return isDarkMode;
    }

    public void setDarkMode(boolean darkMode) {
        isDarkMode = darkMode;
        toggleDarkMode();
    }

    public JButton getBtnToggleDarkMode() {
        return btnToggleDarkMode;
    }

    public void setBtnToggleDarkMode(JButton btnToggleDarkMode) {
        this.btnToggleDarkMode = btnToggleDarkMode;
    }

}