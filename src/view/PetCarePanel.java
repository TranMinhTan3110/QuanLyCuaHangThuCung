package view;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PetCarePanel extends JPanel {

    private JTextField petNameField;
    private JTextField ownerNameField;
    private JTextField appointmentDateField;
    private JTextArea noteArea;
    private JButton datePickerButton;
    private JButton bookAppointmentButton;
    private JPanel tabsPanel;

    public PetCarePanel() {
        setLayout(new BorderLayout(0, 15));
        setBackground(new Color(200, 220, 240)); // Light blue background
        setBorder(new EmptyBorder(20, 20, 20, 20));

        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(new Color(200, 220, 240));

        JLabel titleLabel = new JLabel("Pet Care Services");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 30)); // Larger font
        titleLabel.setForeground(new Color(40, 40, 40)); // Darker for better contrast
        titlePanel.add(titleLabel);

        // Tabs panel
        createTabsPanel();

        // Form panel
        JPanel formPanel = createFormPanel();

        // Add components to main panel
        add(titlePanel, BorderLayout.NORTH);

        // Thêm một panel vào giữa để kiểm soát kích thước tab tốt hơn
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(200, 220, 240));
        centerPanel.add(tabsPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        add(formPanel, BorderLayout.SOUTH);
    }

    private void createTabsPanel() {
        tabsPanel = new JPanel();
        tabsPanel.setBackground(new Color(200, 220, 240)); // Light blue background

        // Sử dụng BoxLayout để kiểm soát kích thước tốt hơn
        tabsPanel.setLayout(new BoxLayout(tabsPanel, BoxLayout.Y_AXIS));

        // Thêm khoảng trống phía trên
        tabsPanel.add(Box.createVerticalStrut(10));

        // Panel chứa tab, đặt ở giữa với margins hai bên
        JPanel tabContainerPanel = new JPanel();
        tabContainerPanel.setOpaque(false);
        tabContainerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

        String[] tabNames = {"Spa", "Grooming", "Bathing", "Vaccination"};

        // Create tab buttons in a rounded panel
        JPanel tabButtonsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(new Color(245, 245, 250)); // Lighter white background for tabs
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
                g2d.dispose();
            }
        };

        tabButtonsPanel.setLayout(new GridLayout(1, tabNames.length, 0, 0));
        tabButtonsPanel.setOpaque(false);

        // Đặt kích thước cố định thay vì preferred size
        Dimension tabSize = new Dimension(500, 45);  // Điều chỉnh kích thước này
        tabButtonsPanel.setMinimumSize(tabSize);
        tabButtonsPanel.setPreferredSize(tabSize);
        tabButtonsPanel.setMaximumSize(tabSize);

        // Create individual tab buttons
        JButton[] tabButtons = new JButton[tabNames.length];

        for (int i = 0; i < tabNames.length; i++) {
            final int index = i;
            final JButton tabButton = new JButton(tabNames[i]);
            tabButtons[i] = tabButton;

            // Điều chỉnh font size phù hợp với không gian
            tabButton.setFont(new Font("SansSerif", Font.BOLD, 16));
            tabButton.setBorderPainted(false);
            tabButton.setFocusPainted(false);
            tabButton.setContentAreaFilled(false);
            tabButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            tabButton.setOpaque(false);
            tabButton.setForeground(Color.BLACK);

            // Giảm margin của nút để text hiển thị tốt hơn
            tabButton.setMargin(new Insets(0, 0, 0, 0));

            // Set Spa as default selected tab as shown in the image
            if (i == 0) { // Spa is at index 0
                tabButton.putClientProperty("selected", true);
            } else {
                tabButton.putClientProperty("selected", false);
            }

            // Custom UI for tab button
            tabButton.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
                @Override
                public void paint(Graphics g, JComponent c) {
                    Graphics2D g2d = (Graphics2D) g.create();
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                    boolean isSelected = Boolean.TRUE.equals(tabButton.getClientProperty("selected"));

                    // Draw background with rounded corners if selected
                    if (isSelected) {
                        // Màu nút tab được chọn - giống màu trong ảnh
                        g2d.setColor(new Color(121, 162, 219));

                        // Create custom path for rounded corners
                        int arc = 25; // Arc size for rounded corners
                        int w = c.getWidth();
                        int h = c.getHeight();

                        // Create a rounded rectangle depending on the tab position
                        if (index == 0) { // First tab - round left corners
                            g2d.fillRoundRect(0, 0, w, h, arc, arc);
                            g2d.fillRect(w - arc/2, 0, arc/2, h);
                        } else if (index == tabNames.length - 1) { // Last tab - round right corners
                            g2d.fillRect(0, 0, arc/2, h);
                            g2d.fillRoundRect(0, 0, w, h, arc, arc);
                        } else { // Middle tabs - create connected look
                            g2d.fillRect(0, 0, w, h);
                        }
                    }

                    // Draw text
                    FontMetrics fm = g2d.getFontMetrics();
                    Rectangle textRect = new Rectangle(0, 0, c.getWidth(), c.getHeight());
                    String text = tabButton.getText();

                    int textWidth = fm.stringWidth(text);
                    int x = (c.getWidth() - textWidth) / 2;
                    int y = (c.getHeight() - fm.getHeight()) / 2 + fm.getAscent();

                    // Text color - selected tab has WHITE text
                    g2d.setColor(isSelected ? Color.WHITE : new Color(60, 60, 60));
                    g2d.drawString(text, x, y);

                    g2d.dispose();
                }
            });

            tabButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Update selection state for all tabs
                    for (int j = 0; j < tabButtons.length; j++) {
                        tabButtons[j].putClientProperty("selected", j == index);
                        tabButtons[j].repaint();
                    }

                    System.out.println("Selected tab: " + tabNames[index]);
                }
            });

            tabButtonsPanel.add(tabButton);
        }

        tabContainerPanel.add(tabButtonsPanel);
        tabsPanel.add(tabContainerPanel);
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

            // Vẽ nền với góc bo tròn
            if (getModel().isPressed()) {
                g2d.setColor(buttonColor.darker());
            } else if (getModel().isRollover()) {
                g2d.setColor(buttonColor.brighter());
            } else {
                g2d.setColor(buttonColor);
            }

            g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

            // Vẽ text
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
        // Sử dụng lớp RoundedButton để đảm bảo luôn có góc bo tròn
        JButton button = new RoundedButton("Book Appointment", 10, new Color(121, 162, 219));
        button.setFont(new Font("SansSerif", Font.BOLD, 16));
        button.setForeground(Color.WHITE); // Chữ màu trắng
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(220, 45));

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Booking appointment for: " + petNameField.getText());
                // Thêm logic đặt lịch ở đây
            }
        });

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

        // Pet Name row - Label
        GridBagConstraints gbc_petNameLabel = new GridBagConstraints();
        gbc_petNameLabel.gridx = 0;
        gbc_petNameLabel.gridy = 0;
        gbc_petNameLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_petNameLabel.insets = new Insets(12, 12, 12, 12);
        gbc_petNameLabel.weightx = 0.3;
        gbc_petNameLabel.weighty = 0;

        JLabel petNameLabel = new JLabel("Pet Name:");
        petNameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        petNameLabel.setForeground(new Color(60, 60, 60));
        formPanel.add(petNameLabel, gbc_petNameLabel);

        // Pet Name row - Field
        GridBagConstraints gbc_petNameField = new GridBagConstraints();
        gbc_petNameField.gridx = 1;
        gbc_petNameField.gridy = 0;
        gbc_petNameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_petNameField.insets = new Insets(12, 12, 12, 12);
        gbc_petNameField.weightx = 0.7;
        gbc_petNameField.weighty = 0;

        petNameField = createRoundedTextField();
        formPanel.add(petNameField, gbc_petNameField);

        // Owner Name row - Label
        GridBagConstraints gbc_ownerNameLabel = new GridBagConstraints();
        gbc_ownerNameLabel.gridx = 0;
        gbc_ownerNameLabel.gridy = 1;
        gbc_ownerNameLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_ownerNameLabel.insets = new Insets(12, 12, 12, 12);
        gbc_ownerNameLabel.weightx = 0.3;
        gbc_ownerNameLabel.weighty = 0;

        JLabel ownerNameLabel = new JLabel("Owner Name:");
        ownerNameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        ownerNameLabel.setForeground(new Color(60, 60, 60));
        formPanel.add(ownerNameLabel, gbc_ownerNameLabel);

        // Owner Name row - Field
        GridBagConstraints gbc_ownerNameField = new GridBagConstraints();
        gbc_ownerNameField.gridx = 1;
        gbc_ownerNameField.gridy = 1;
        gbc_ownerNameField.fill = GridBagConstraints.HORIZONTAL;
        gbc_ownerNameField.insets = new Insets(12, 12, 12, 12);
        gbc_ownerNameField.weightx = 0.7;
        gbc_ownerNameField.weighty = 0;

        ownerNameField = createRoundedTextField();
        formPanel.add(ownerNameField, gbc_ownerNameField);

        // Appointment Date row - Label
        GridBagConstraints gbc_appointmentDateLabel = new GridBagConstraints();
        gbc_appointmentDateLabel.gridx = 0;
        gbc_appointmentDateLabel.gridy = 2;
        gbc_appointmentDateLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_appointmentDateLabel.insets = new Insets(12, 12, 12, 12);
        gbc_appointmentDateLabel.weightx = 0.3;
        gbc_appointmentDateLabel.weighty = 0;

        JLabel appointmentDateLabel = new JLabel("Appointment Date");
        appointmentDateLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        appointmentDateLabel.setForeground(new Color(60, 60, 60));
        formPanel.add(appointmentDateLabel, gbc_appointmentDateLabel);

        // Appointment Date row - Field and Button
        GridBagConstraints gbc_datePanel = new GridBagConstraints();
        gbc_datePanel.gridx = 1;
        gbc_datePanel.gridy = 2;
        gbc_datePanel.fill = GridBagConstraints.HORIZONTAL;
        gbc_datePanel.insets = new Insets(12, 12, 12, 12);
        gbc_datePanel.weightx = 0.7;
        gbc_datePanel.weighty = 0;

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BorderLayout(5, 0));
        datePanel.setOpaque(false);

        appointmentDateField = createRoundedTextField();
        appointmentDateField.setEditable(false);
        // Đặt ngày hiện tại
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        appointmentDateField.setText(dateFormat.format(new Date()));

        // Tạo biểu tượng lịch đẹp hơn
        datePickerButton = new JButton();
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getResource("/view/Icon/date.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            datePickerButton.setIcon(new ImageIcon(scaledImage));
        } catch (Exception e) {
            // Fallback to default calendar icon if resource not found
            datePickerButton.setIcon(createCalendarIcon());
        }
        datePickerButton.setPreferredSize(new Dimension(38, 38)); // Larger button
        datePickerButton.setContentAreaFilled(false);
        datePickerButton.setBorderPainted(false);
        datePickerButton.setFocusPainted(false);
        datePickerButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add date picker functionality
        datePickerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDatePicker();
            }
        });

        datePanel.add(appointmentDateField, BorderLayout.CENTER);
        datePanel.add(datePickerButton, BorderLayout.EAST);

        formPanel.add(datePanel, gbc_datePanel);

        // Note row - Label
        GridBagConstraints gbc_noteLabel = new GridBagConstraints();
        gbc_noteLabel.gridx = 0;
        gbc_noteLabel.gridy = 3;
        gbc_noteLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_noteLabel.insets = new Insets(12, 12, 12, 12);
        gbc_noteLabel.weightx = 0.3;
        gbc_noteLabel.weighty = 0;

        JLabel noteLabel = new JLabel("Note:");
        noteLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        noteLabel.setForeground(new Color(60, 60, 60));
        formPanel.add(noteLabel, gbc_noteLabel);

        // Note row - TextArea
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridx = 1;
        gbc_scrollPane.gridy = 3;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.insets = new Insets(12, 12, 12, 12);
        gbc_scrollPane.weightx = 0.7;
        gbc_scrollPane.weighty = 1.0;

        noteArea = new JTextArea(5, 20);
        JScrollPane scrollPane = createRoundedScrollPane(noteArea);
        formPanel.add(scrollPane, gbc_scrollPane);

        // Button row - sử dụng phương thức tạo nút mới
        GridBagConstraints gbc_bookAppointmentButton = new GridBagConstraints();
        gbc_bookAppointmentButton.gridx = 0;
        gbc_bookAppointmentButton.gridy = 4;
        gbc_bookAppointmentButton.gridwidth = 2;
        gbc_bookAppointmentButton.fill = GridBagConstraints.NONE;
        gbc_bookAppointmentButton.anchor = GridBagConstraints.CENTER;
        gbc_bookAppointmentButton.weighty = 0;
        gbc_bookAppointmentButton.insets = new Insets(18, 12, 8, 12);

        bookAppointmentButton = createBookAppointmentButton();
        formPanel.add(bookAppointmentButton, gbc_bookAppointmentButton);

        return formPanel;
    }

    // Show date picker dialog
    private void showDatePicker() {
        // Create a JDialog to contain the date picker
        final JDialog dialog = new JDialog();
        dialog.setTitle("Select Date");
        dialog.setModal(true);
        dialog.setResizable(false);

        // Create a JPanel for the calendar
        JPanel calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Get current date
        final Calendar calendar = Calendar.getInstance();

        // Create month and year selector panel
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

        // Create days panel
        final JPanel daysPanel = new JPanel(new GridLayout(7, 7, 8, 8));

        // Create day names
        String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String dayName : dayNames) {
            JLabel label = new JLabel(dayName, SwingConstants.CENTER);
            label.setFont(new Font("SansSerif", Font.BOLD, 14));
            label.setForeground(new Color(60, 60, 60));
            daysPanel.add(label);
        }

        // Add calendar days
        final JButton[][] dayButtons = new JButton[6][7];
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                final JButton dayButton = new JButton();
                dayButton.setFocusPainted(false);
                dayButton.setFont(new Font("SansSerif", Font.PLAIN, 14));
                dayButton.setContentAreaFilled(true);  // Đảm bảo nút hiển thị nền
                dayButton.setBackground(Color.WHITE);  // Nền trắng mặc định
                dayButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                dayButton.setPreferredSize(new Dimension(45, 35));

                final int finalRow = row;
                final int finalCol = col;

                dayButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton selectedButton = (JButton) e.getSource();
                        if (!selectedButton.getText().isEmpty()) {
                            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(selectedButton.getText()));
                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            String selectedDate = dateFormat.format(calendar.getTime());
                            appointmentDateField.setText(selectedDate);
                            dialog.dispose();
                        }
                    }
                });

                dayButtons[row][col] = dayButton;
                daysPanel.add(dayButton);
            }
        }

        // Update calendar days
        updateCalendarDays(dayButtons, calendar);

        // Add action listeners for navigation buttons
        prevMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.MONTH, -1);
                updateMonthYearLabel(monthYearLabel, calendar);
                updateCalendarDays(dayButtons, calendar);
            }
        });

        nextMonthButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.add(Calendar.MONTH, 1);
                updateMonthYearLabel(monthYearLabel, calendar);
                updateCalendarDays(dayButtons, calendar);
            }
        });

        // Add buttons panel
        JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton todayButton = new JButton("Today");
        todayButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("SansSerif", Font.BOLD, 14));

        todayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calendar.setTime(new Date());
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String today = dateFormat.format(calendar.getTime());
                appointmentDateField.setText(today);
                dialog.dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });

        buttonsPanel.add(todayButton);
        buttonsPanel.add(Box.createHorizontalStrut(10));
        buttonsPanel.add(cancelButton);

        // Add components to calendar panel
        calendarPanel.add(navigationPanel, BorderLayout.NORTH);
        calendarPanel.add(daysPanel, BorderLayout.CENTER);
        calendarPanel.add(buttonsPanel, BorderLayout.SOUTH);

        // Set up dialog
        dialog.getContentPane().add(calendarPanel);
        dialog.pack();
        dialog.setLocationRelativeTo(datePickerButton);
        dialog.setVisible(true);
    }

    // Update calendar days in date picker - đã sửa lỗi hiển thị "..."
    private void updateCalendarDays(JButton[][] dayButtons, Calendar calendar) {
        // Lưu lại ngày hiện tại để khôi phục sau khi tính toán
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        // Clear all buttons
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                // Xóa text và đặt lại các thuộc tính
                dayButtons[row][col].setText("");
                dayButtons[row][col].setBackground(Color.WHITE);
                dayButtons[row][col].setForeground(Color.BLACK);
                dayButtons[row][col].setEnabled(false); // Vô hiệu hóa các ô trống
            }
        }

        // Get current month details
        calendar.set(Calendar.DAY_OF_MONTH, 1); // Đặt về ngày đầu tiên của tháng
        int firstDayOfMonth = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 0=Sunday, 1=Monday, etc.
        int daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        // Fill calendar with days
        int day = 1;
        for (int row = 0; row < 6; row++) {
            for (int col = 0; col < 7; col++) {
                if (row == 0 && col < firstDayOfMonth) {
                    // Empty cells before first day of month
                    continue;
                }
                if (day > daysInMonth) {
                    // Empty cells after last day of month
                    break;
                }

                dayButtons[row][col].setText(String.valueOf(day));
                dayButtons[row][col].setEnabled(true); // Cho phép chọn ngày hợp lệ

                // Highlight today
                Calendar today = Calendar.getInstance();
                if (today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) &&
                        today.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
                        today.get(Calendar.DAY_OF_MONTH) == day) {

                    dayButtons[row][col].setBackground(new Color(121, 162, 219)); // Màu phù hợp với mẫu
                    dayButtons[row][col].setForeground(Color.WHITE); // Chữ trắng trên nền xanh
                }

                day++;
            }
        }

        // Khôi phục ngày hiện tại trong calendar
        calendar.set(Calendar.DAY_OF_MONTH, currentDay);
    }

    // Update month and year label in date picker - hiển thị định dạng "tháng M yyyy"
    private void updateMonthYearLabel(JLabel label, Calendar calendar) {
        int month = calendar.get(Calendar.MONTH) + 1; // Tháng trong Calendar bắt đầu từ 0
        int year = calendar.get(Calendar.YEAR);
        label.setText("tháng " + month + " " + year);
    }

    // Utility method to create a calendar icon
    private ImageIcon createCalendarIcon() {
        BufferedImage img = new BufferedImage(22, 22, BufferedImage.TYPE_INT_ARGB); // Larger icon
        Graphics2D g2d = img.createGraphics();

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(new Color(80, 80, 80)); // Darker for better visibility

        // Draw calendar outline
        g2d.drawRoundRect(1, 3, 19, 18, 4, 4);

        // Draw top part of calendar
        g2d.drawLine(1, 9, 20, 9);

        // Draw calendar hang tabs
        g2d.drawLine(6, 3, 6, 1);
        g2d.drawLine(15, 3, 15, 1);

        // Draw day dots
        g2d.fillRect(5, 12, 3, 3);
        g2d.fillRect(10, 12, 3, 3);
        g2d.fillRect(15, 12, 3, 3);
        g2d.fillRect(5, 17, 3, 3);
        g2d.fillRect(10, 17, 3, 3);
        g2d.fillRect(15, 17, 3, 3);

        g2d.dispose();

        return new ImageIcon(img);
    }

    // For testing purposes
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Tắt Look and Feel hệ thống để tránh ghi đè lên UI tùy chỉnh
                    // UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                    UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JFrame frame = new JFrame("Pet Care Services");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(650, 580); // Larger frame for bigger UI elements
                frame.add(new PetCarePanel());
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            }
        });
    }
}