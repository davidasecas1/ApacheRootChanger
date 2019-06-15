package main;

import javax.swing.JFrame;

public class Ventana extends JFrame {
	public Ventana() {
		this.setBounds(500, 150, 600,350);
		this.setTitle("Apache Root");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Panel panel = new Panel();
		this.add(panel);
		this.revalidate();
	}
}
