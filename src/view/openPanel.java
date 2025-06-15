package view;
import javax.swing.JFrame;

import view.BillView;


public class openPanel {
	public static void main(String[] args) {
		JFrame frame = new JFrame("Manage Users");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950, 750);
        frame.add(new BillView());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
	}
}
