package view;

import javax.swing.JFrame;


public class testPanel {
	public static void main(String[] args) {
        JFrame frame = new JFrame("Manage Users");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950, 750);
        frame.add(new petView());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
