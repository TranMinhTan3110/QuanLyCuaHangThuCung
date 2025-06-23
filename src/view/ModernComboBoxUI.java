package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.ComboPopup;
import java.awt.*;

public class ModernComboBoxUI extends BasicComboBoxUI {
    @Override
    protected JButton createArrowButton() {
        JButton button = new JButton() {
            @Override
            public void paint(Graphics g) {
                int w = getWidth();
                int h = getHeight();
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int size = 12;
                int x = (w - size) / 2;
                int y = (h - size) / 2 + 2;
                g2.setColor(new Color(120, 120, 120));
                int[] xPoints = {x, x + size, x + size / 2};
                int[] yPoints = {y, y, y + size / 2};
                g2.fillPolygon(xPoints, yPoints, 3);
                g2.dispose();
            }
        };
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setContentAreaFilled(false);
        button.setOpaque(false);
        return button;
    }

    @Override
    public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(255,255,255));
        g2.fillRoundRect(bounds.x, bounds.y, bounds.width, bounds.height, 16, 16);
    }

    @Override
    protected void installDefaults() {
        super.installDefaults();
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220), 1, true));
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(new Color(40,40,40));
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        comboBox.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        comboBox.setOpaque(false);
    }

    @Override
    protected ComboPopup createPopup() {
        ComboPopup popup = super.createPopup();
        if (popup.getList() != null) {
            popup.getList().setBorder(new EmptyBorder(4, 4, 4, 4));
            popup.getList().setBackground(Color.WHITE);
            popup.getList().setSelectionBackground(new Color(220,235,255));
            popup.getList().setSelectionForeground(new Color(40,40,40));
            popup.getList().setFont(new Font("Segoe UI", Font.PLAIN, 16));
        }
        if (popup instanceof JPopupMenu) {
            ((JPopupMenu) popup).setBorder(BorderFactory.createLineBorder(new Color(180, 200, 220), 1, true));
        }
        return popup;
    }
}