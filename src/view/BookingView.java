package view;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

public class BookingView extends JPanel {

    private JTextField petNameField;
    private JTextField appointmentDateField;
    private JTextArea noteArea;
    private JButton datePickerButton;
    private JButton bookAppointmentButton;
    private JPanel tabsPanel;
    private JComboBox<String> ownerNameComboBox;
    private JPopupMenu suggestionPopup;
    private List<String> customerList = Arrays.asList(
            "Nguyễn Văn Anh", "Trần Thị Bình", "Lê Văn Cường", "Nguyễn Thị Dung", "Phạm Minh Anh", "Anh Nguyễn", "Anh Thư", "Anh Tú", "Anh Đào"
    );

    public BookingView() {
        setLayout(new BorderLayout(0, 15));
        setBackground(new Color(194, 216, 240));
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(194, 216, 240));

        JLabel titleLabel = new JLabel("Pet Care Services");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30));
        titleLabel.setForeground(new Color(40, 40, 40));
        titlePanel.add(titleLabel);

        // Tabs panel
        createTabsPanel();

        // Form panel
        JPanel formPanel = createFormPanel();

        // North: title
        add(titlePanel, BorderLayout.NORTH);

        // Center: tabs
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(194, 216, 240));
        centerPanel.add(tabsPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        // South: form
        add(formPanel, BorderLayout.SOUTH);
    }

    private void createTabsPanel() {
        tabsPanel = new JPanel();
        tabsPanel.setBackground(new Color(194, 216, 240));
        tabsPanel.setLayout(new BoxLayout(tabsPanel, BoxLayout.Y_AXIS));
        tabsPanel.add(Box.createVerticalStrut(10));

        JPanel tabContainerPanel = new JPanel();
        tabContainerPanel.setOpaque(false);
        tabContainerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        String[] serviceNames = {"Spa", "Grooming", "Bathing", "Vaccination"};

        JPanel servicesPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(245, 245, 250));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2d.dispose();
            }
        };

        servicesPanel.setLayout(new GridLayout(1, serviceNames.length, 0, 0));
        servicesPanel.setOpaque(false);

        Dimension panelSize = new Dimension(500, 45);
        servicesPanel.setMinimumSize(panelSize);
        servicesPanel.setPreferredSize(panelSize);
        servicesPanel.setMaximumSize(panelSize);

        // Create checkboxes for services
        for (String serviceName : serviceNames) {
            JCheckBox checkBox = new JCheckBox(serviceName) {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Draw background
                    if (isSelected()) {
                        g2.setColor(new Color(121, 162, 219));
                    } else if (getModel().isRollover()) {
                        g2.setColor(new Color(215, 222, 235));
                    } else {
                        g2.setColor(Color.WHITE);
                    }
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

                    // Draw text
                    FontMetrics fm = g2.getFontMetrics();
                    Rectangle textRect = new Rectangle(0, 0, getWidth(), getHeight());
                    String text = getText();
                    int textWidth = fm.stringWidth(text);
                    int x = (getWidth() - textWidth) / 2;
                    int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();

                    g2.setColor(isSelected() ? Color.WHITE : new Color(60, 60, 60));
                    g2.setFont(getFont());
                    g2.drawString(text, x, y);
                    g2.dispose();
                }
            };

            checkBox.setFont(new Font("SansSerif", Font.BOLD, 16));
            checkBox.setBorderPainted(false);
            checkBox.setFocusPainted(false);
            checkBox.setContentAreaFilled(false);
            checkBox.setCursor(new Cursor(Cursor.HAND_CURSOR));
            checkBox.setOpaque(false);

            servicesPanel.add(checkBox);
        }

        tabContainerPanel.add(servicesPanel);
        tabsPanel.add(tabContainerPanel);
    }

    public List<String> getSelectedServices() {
        List<String> selectedServices = new ArrayList<>();
        // Get the services panel (index 1 is tabContainerPanel, index 0 is servicesPanel)
        JPanel servicesPanel = (JPanel) ((JPanel)tabsPanel.getComponent(1)).getComponent(0);

        // Iterate through all components in services panel
        for (Component component : servicesPanel.getComponents()) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                if (checkBox.isSelected()) {
                    selectedServices.add(checkBox.getText());
                }
            }
        }
        return selectedServices;
    }

    public void setSelectedServices(List<String> services) {
        // Get the services panel
        JPanel servicesPanel = (JPanel) ((JPanel)tabsPanel.getComponent(1)).getComponent(0);

        // Iterate through all components
        for (Component component : servicesPanel.getComponents()) {
            if (component instanceof JCheckBox) {
                JCheckBox checkBox = (JCheckBox) component;
                // Select checkbox if its text is in the services list
                checkBox.setSelected(services.contains(checkBox.getText()));
            }
        }
    }

    // Custom rounded text field with black border
    private JTextField createRoundedTextField() {
        JTextField textField = new JTextField() {
            private static final int ARC = 10;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Color.WHITE);
                g2.fillRoundRect(1, 1, getWidth()-2, getHeight()-2, ARC, ARC);

                // Black border
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, ARC, ARC);

                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
            }
        };

        textField.setPreferredSize(new Dimension(250, 40));
        textField.setFont(new Font("SansSerif", Font.PLAIN, 15));
        return textField;
    }

    // Custom rounded combo box with custom arrow button
    private JComboBox<String> createRoundedComboBox() {
        JComboBox<String> comboBox = new JComboBox<>();
        comboBox.setEditable(true);
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 15));
        comboBox.setPreferredSize(new Dimension(250, 40));
        comboBox.setMaximumRowCount(8);

        // Custom UI for round border and custom arrow
        comboBox.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = new JButton() {
                    @Override
                    public void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                        int w = getWidth();
                        int h = getHeight();
                        // Fill background for arrow button
                        g2.setColor(new Color(230, 230, 230));
                        g2.fillRoundRect(0, 0, w, h, 10, 10);
                        // Draw custom arrow
                        int size = 10;
                        int x = w/2 - size/2, y = h/2 - size/4;
                        g2.setColor(new Color(80, 80, 80));
                        Polygon arrow = new Polygon();
                        arrow.addPoint(x, y);
                        arrow.addPoint(x+size, y);
                        arrow.addPoint(x+size/2, y+size/2);
                        g2.fill(arrow);
                        g2.setColor(new Color(180, 180, 180));
                        g2.drawRoundRect(0, 0, w-1, h-1, 10, 10);
                        g2.dispose();
                    }
                };
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                button.setPreferredSize(new Dimension(30, 28));
                return button;
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                // Background for the main area
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 10, 10);
                g2.dispose();
            }

            @Override
            public void paint(Graphics g, JComponent c) {
                super.paint(g, c);
                // Draw border for the whole combo box
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, c.getWidth() - 3, c.getHeight() - 3, 10, 10);
                g2.dispose();
            }
        });

        // Remove native border and set padding for editor
        comboBox.setBorder(BorderFactory.createEmptyBorder());
        Component editorComp = comboBox.getEditor().getEditorComponent();
        if (editorComp instanceof JTextField) {
            JTextField editor = (JTextField) editorComp;
            editor.setOpaque(false);
            editor.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        }
        return comboBox;
    }

    // Rounded scroll pane for note area with black border
    private JScrollPane createRoundedScrollPane(JTextArea textArea) {
        JScrollPane scrollPane = new JScrollPane() {
            private static final int ARC = 10;

            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                g2.setColor(Color.WHITE);
                g2.fillRoundRect(1, 1, getWidth()-2, getHeight()-2, ARC, ARC);

                // Black border
                g2.setColor(Color.BLACK);
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth()-3, getHeight()-3, ARC, ARC);

                g2.dispose();
                super.paintComponent(g);
            }

            @Override
            public void updateUI() {
                super.updateUI();
                setOpaque(false);
                setBorder(BorderFactory.createEmptyBorder());
                getViewport().setOpaque(false);
            }
        };

        textArea.setBackground(Color.WHITE);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setBorder(BorderFactory.createEmptyBorder(8, 12, 8, 12));
        textArea.setFont(new Font("SansSerif", Font.PLAIN, 15));

        scrollPane.setViewportView(textArea);
        return scrollPane;
    }

    // Lớp RoundedButton tùy chỉnh để đảm bảo luôn có góc bo tròn
    private class RoundedButton extends JButton {
        private int radius;
        private Color buttonColor;
        public RoundedButton(String text, int radius, Color buttonColor) {
            super(text);
            this.radius = radius;
            this.buttonColor = buttonColor;
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
            setOpaque(false);
        }
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            if (getModel().isPressed()) {
                g2d.setColor(buttonColor.darker());
            } else if (getModel().isRollover()) {
                g2d.setColor(buttonColor.brighter());
            } else {
                g2d.setColor(buttonColor);
            }
            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            FontMetrics fm = g2d.getFontMetrics();
            Rectangle textRect = new Rectangle(0, 0, getWidth(), getHeight());
            String text = getText();
            int textWidth = fm.stringWidth(text);
            int x = (getWidth() - textWidth) / 2;
            int y = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g2d.setColor(getForeground());
            g2d.setFont(getFont());
            g2d.drawString(text, x, y);
            g2d.dispose();
        }
    }

    // Tạo nút Book Appointment với góc bo tròn
    private JButton createBookAppointmentButton() {
        JButton button = new RoundedButton("Book Appointment", 10, new Color(121, 162, 219));
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(220, 45));
        button.addActionListener(e -> System.out.println("Booking appointment for: " + getPetName()));
        return button;
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(255, 250, 245));
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2d.dispose();
            }
        };
        formPanel.setLayout(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // Pet Name row
        GridBagConstraints gbc_petNameLabel = new GridBagConstraints();
        gbc_petNameLabel.gridx = 0; gbc_petNameLabel.gridy = 0;
        gbc_petNameLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_petNameLabel.insets = new Insets(12, 12, 12, 12);
        gbc_petNameLabel.weightx = 0.3;

        JLabel petNameLabel = new JLabel("Pet Name:");
        petNameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        petNameLabel.setForeground(new Color(60, 60, 60));
        formPanel.add(petNameLabel, gbc_petNameLabel);

        GridBagConstraints gbc_petNameField = new GridBagConstraints();
        gbc_petNameField.gridx = 1; gbc_petNameField.gridy = 0;
        gbc_petNameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_petNameField.insets = new Insets(12, 12, 12, 12);
        gbc_petNameField.weightx = 0.7;
        petNameField = createRoundedTextField();
        formPanel.add(petNameField, gbc_petNameField);

        // Owner Name row (AutoSuggest with custom UI)
        GridBagConstraints gbc_ownerNameLabel = new GridBagConstraints();
        gbc_ownerNameLabel.gridx = 0; gbc_ownerNameLabel.gridy = 1;
        gbc_ownerNameLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_ownerNameLabel.insets = new Insets(12, 12, 12, 12);
        gbc_ownerNameLabel.weightx = 0.3;

        JLabel ownerNameLabel = new JLabel("Owner Name:");
        ownerNameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        ownerNameLabel.setForeground(new Color(60, 60, 60));
        formPanel.add(ownerNameLabel, gbc_ownerNameLabel);

        GridBagConstraints gbc_ownerNameField = new GridBagConstraints();
        gbc_ownerNameField.gridx = 1; gbc_ownerNameField.gridy = 1;
        gbc_ownerNameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_ownerNameField.insets = new Insets(12, 12, 12, 12);
        gbc_ownerNameField.weightx = 0.7;

        // Custom AutoSuggest ComboBox with beautiful arrow & border
        ownerNameComboBox = createRoundedComboBox();
        JTextField editor = (JTextField) ownerNameComboBox.getEditor().getEditorComponent();

        // Suggestion logic
        suggestionPopup = new JPopupMenu();
        suggestionPopup.setFocusable(false);

        editor.getDocument().addDocumentListener(new DocumentListener() {
            private void updateSuggestions() {
                String input = editor.getText().trim().toLowerCase();
                suggestionPopup.setVisible(false);
                suggestionPopup.removeAll();
                if (input.isEmpty()) return;
                List<String> filtered = new ArrayList<>();
                for (String s : customerList) {
                    if (s.toLowerCase().contains(input)) filtered.add(s);
                }
                if (filtered.isEmpty()) return;
                for (String s : filtered) {
                    JMenuItem item = new JMenuItem(s);
                    item.setFont(editor.getFont());
                    item.setBackground(Color.WHITE);
                    item.addActionListener(e -> {
                        editor.setText(s);
                        suggestionPopup.setVisible(false);
                    });
                    suggestionPopup.add(item);
                }
                try {
                    suggestionPopup.setPreferredSize(new Dimension(ownerNameComboBox.getWidth(), filtered.size() * 24 + 8));
                    suggestionPopup.show(editor, 0, editor.getHeight());
                } catch (Exception ignored) {}
            }
            public void insertUpdate(DocumentEvent e) { updateSuggestions(); }
            public void removeUpdate(DocumentEvent e) { updateSuggestions(); }
            public void changedUpdate(DocumentEvent e) { updateSuggestions(); }
        });

        editor.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) { suggestionPopup.setVisible(false); }
        });

        formPanel.add(ownerNameComboBox, gbc_ownerNameField);

        // Appointment Date row
        GridBagConstraints gbc_appointmentDateLabel = new GridBagConstraints();
        gbc_appointmentDateLabel.gridx = 0; gbc_appointmentDateLabel.gridy = 2;
        gbc_appointmentDateLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_appointmentDateLabel.insets = new Insets(12, 12, 12, 12);
        gbc_appointmentDateLabel.weightx = 0.3;

        JLabel appointmentDateLabel = new JLabel("Appointment Date");
        appointmentDateLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        appointmentDateLabel.setForeground(new Color(60, 60, 60));
        formPanel.add(appointmentDateLabel, gbc_appointmentDateLabel);

        GridBagConstraints gbc_datePanel = new GridBagConstraints();
        gbc_datePanel.gridx = 1; gbc_datePanel.gridy = 2;
        gbc_datePanel.fill = GridBagConstraints.HORIZONTAL;
        gbc_datePanel.insets = new Insets(12, 12, 12, 12);
        gbc_datePanel.weightx = 0.7;

        JPanel datePanel = new JPanel(new BorderLayout(5, 0));
        datePanel.setOpaque(false);

        appointmentDateField = createRoundedTextField();
        appointmentDateField.setEditable(false);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        appointmentDateField.setText(dateFormat.format(new Date()));

        datePickerButton = new JButton();
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/view/Icon/date.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            datePickerButton.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            datePickerButton.setIcon(createCalendarIcon());
        }
        datePickerButton.setPreferredSize(new Dimension(38, 38));
        datePickerButton.setContentAreaFilled(false);
        datePickerButton.setBorderPainted(false);
        datePickerButton.setFocusPainted(false);
        datePickerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        datePickerButton.addActionListener(e -> showDatePicker());

        datePanel.add(appointmentDateField, BorderLayout.CENTER);
        datePanel.add(datePickerButton, BorderLayout.EAST);

        formPanel.add(datePanel, gbc_datePanel);

        // Note row
        GridBagConstraints gbc_noteLabel = new GridBagConstraints();
        gbc_noteLabel.gridx = 0; gbc_noteLabel.gridy = 3;
        gbc_noteLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_noteLabel.insets = new Insets(12, 12, 12, 12);
        gbc_noteLabel.weightx = 0.3;
        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        noteLabel.setForeground(new Color(60, 60, 60));
        formPanel.add(noteLabel, gbc_noteLabel);

        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridx = 1; gbc_scrollPane.gridy = 3;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(12, 12, 12, 12);
        gbc_scrollPane.weightx = 0.7; gbc_scrollPane.weighty = 1.0;
        noteArea = new JTextArea(5, 20);
        JScrollPane scrollPane = createRoundedScrollPane(noteArea);
        formPanel.add(scrollPane, gbc_scrollPane);

        // Button row
        GridBagConstraints gbc_bookAppointmentButton = new GridBagConstraints();
        gbc_bookAppointmentButton.gridx = 0; gbc_bookAppointmentButton.gridy = 4;
        gbc_bookAppointmentButton.gridwidth = 2;
        gbc_bookAppointmentButton.fill = GridBagConstraints.NONE;
        gbc_bookAppointmentButton.anchor = GridBagConstraints.CENTER;
        gbc_bookAppointmentButton.weighty = 0;
        gbc_bookAppointmentButton.insets = new Insets(18, 12, 8, 12);

        bookAppointmentButton = createBookAppointmentButton();
        formPanel.add(bookAppointmentButton, gbc_bookAppointmentButton);

        return formPanel;
    }

    // ==== GETTERS & SETTERS ====
    public String getPetName() {
        return petNameField.getText();
    }
    public void setPetName(String name) {
        petNameField.setText(name);
    }
    public String getOwnerName() {
        Object selectedItem = ownerNameComboBox.getSelectedItem();
        if (selectedItem == null) {
            return ownerNameComboBox.getEditor().getItem().toString().trim();
        }
        return selectedItem.toString().trim();
    }
    public void setOwnerName(String name) {
        ownerNameComboBox.getEditor().setItem(name);
    }
    public String getAppointmentDate() {
        return appointmentDateField.getText();
    }
    public void setAppointmentDate(String date) {
        appointmentDateField.setText(date);
    }
    public String getNote() {
        return noteArea.getText();
    }
    public void setNote(String note) {
        noteArea.setText(note);
    }
    public JButton getBookAppointmentButton() {
        return bookAppointmentButton;
    }
    public JComboBox<String> getOwnerNameComboBox() {
        return ownerNameComboBox;
    }
    public JTextField getPetNameField() {
        return petNameField;
    }
    public JTextArea getNoteArea() {
        return noteArea;
    }
    public JTextField getAppointmentDateField() {
        return appointmentDateField;
    }
    public JButton getDatePickerButton() {
        return datePickerButton;
    }

    // ==== END GETTERS & SETTERS ====

    // Show date picker dialog (giữ nguyên như cũ)
    private void showDatePicker() {
        final JDialog dialog = new JDialog();
        dialog.setTitle("Select Date");
        dialog.setModal(true);
        dialog.setResizable(false);
        JPanel calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        final Calendar calendar = Calendar.getInstance();
        JPanel navigationPanel = new JPanel(new FlowLayout());
        JButton prevMonthButton = new JButton("<");
        prevMonthButton.setFocusPainted(false);
        prevMonthButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        final JLabel monthYearLabel = new JLabel();
        monthYearLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        updateMonthYearLabel(monthYearLabel, calendar);
        JButton nextMonthButton = new JButton(">");
        nextMonthButton.setFocusPainted(false);
        nextMonthButton.setFont(new Font("SansSerif", Font.BOLD, 16));
        navigationPanel.add(prevMonthButton);
        navigationPanel.add(Box.createHorizontalStrut(20));
        navigationPanel.add(monthYearLabel);
        navigationPanel.add(Box.createHorizontalStrut(20));
        navigationPanel.add(nextMonthButton);
        final JPanel daysPanel = new JPanel(new GridLayout(7, 7, 8, 8));
        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String dayName : dayNames) {
            JLabel label = new JLabel(dayName, SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            label.setForeground(new Color(60, 60, 60));
            daysPanel.add(label);
        }
        final JButton[][] dayButtons = new JButton[6][7];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                final JButton dayButton = new JButton();
                dayButton.setFocusPainted(false);
                dayButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
                dayButton.setContentAreaFilled(true);
                dayButton.setBackground(Color.WHITE);
                dayButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                dayButton.setPreferredSize(new Dimension(45, 35));
                final int finalRow = row;
                final int finalCol = col;
                dayButton.addActionListener(e -> {
                    JButton selectedButton = (JButton) e.getSource();
                    if (!selectedButton.getText().isEmpty()) {
                        calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(selectedButton.getText()));
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        String selectedDate = dateFormat.format(calendar.getTime());
                        appointmentDateField.setText(selectedDate);
                        dialog.dispose();
                    }
                });
                dayButtons[row][col] = dayButton;
                daysPanel.add(dayButton);
            }
        }
        updateCalendarDays(dayButtons, calendar);
        prevMonthButton.addActionListener(e -> {
            calendar.add(Calendar.MONTH, -1);
            updateMonthYearLabel(monthYearLabel, calendar);
            updateCalendarDays(dayButtons, calendar);
        });
        nextMonthButton.addActionListener(e -> {
            calendar.add(Calendar.MONTH, 1);
            updateMonthYearLabel(monthYearLabel, calendar);
            updateCalendarDays(dayButtons, calendar);
        });
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton todayButton = new JButton("Today");
        todayButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        todayButton.addActionListener(e -> {
            calendar.setTime(new Date());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String today = dateFormat.format(calendar.getTime());
            appointmentDateField.setText(today);
            dialog.dispose();
        });
        cancelButton.addActionListener(e -> dialog.dispose());
        buttonsPanel.add(todayButton);
        buttonsPanel.add(Box.createHorizontalStrut(10));
        buttonsPanel.add(cancelButton);
        calendarPanel.add(navigationPanel, BorderLayout.NORTH);
        calendarPanel.add(daysPanel, BorderLayout.CENTER);
        calendarPanel.add(buttonsPanel, BorderLayout.SOUTH);
        dialog.getContentPane().add(calendarPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(datePickerButton);
        dialog.setVisible(true);
    }

    private void updateCalendarDays(JButton[][] dayButtons, Calendar calendar) {
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                dayButtons[row][col].setText("");
                dayButtons[row][col].setBackground(Color.WHITE);
                dayButtons[row][col].setForeground(Color.BLACK);
                dayButtons[row][col].setEnabled(false);
            }
        }
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1;
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int day = 1;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (row == 0 && col < firstDayOfMonth) continue;
                if (day > daysInMonth) break;
                dayButtons[row][col].setText(String.valueOf(day));
                dayButtons[row][col].setEnabled(true);
                Calendar today = Calendar.getInstance();
                if (today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                        today.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                        today.get(Calendar.DAY_OF_MONTH) == day) {
                    dayButtons[row][col].setBackground(new Color(121, 162, 219));
                    dayButtons[row][col].setForeground(Color.WHITE);
                }
                day++;
            }
        }
        calendar.set(Calendar.DAY_OF_MONTH, currentDay);
    }

    private void updateMonthYearLabel(JLabel label, Calendar calendar) {
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        label.setText("tháng " + month + " " + year);
    }

    private ImageIcon createCalendarIcon() {
        BufferedImage img = new BufferedImage(22, 22, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = img.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(80, 80, 80));
        g2d.drawRoundRect(1, 3, 19, 18, 4, 4);
        g2d.drawLine(1, 9, 20, 9);
        g2d.drawLine(6, 3, 6, 1);
        g2d.drawLine(15, 3, 15, 1);
        g2d.fillRect(5, 12, 3, 3);
        g2d.fillRect(10, 12, 3, 3);
        g2d.fillRect(15, 12, 3, 3);
        g2d.fillRect(5, 17, 3, 3);
        g2d.fillRect(10, 17, 3, 3);
        g2d.fillRect(15, 17, 3, 3);
        g2d.dispose();
        return new ImageIcon(img);
    }

    public static void main(String[] args) {
        // Run on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            // Create and setup frame
            JFrame frame = new JFrame("Pet Care Services");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Create and add panel
            BookingView panel = new BookingView();
            frame.add(panel);

            // Size and display
            frame.setSize(800, 900);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}