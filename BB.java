package brick_breaker;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class BB extends JFrame {

	BB() {
		initUI();
	}

	private void initUI() {
		add(new Board());

		setResizable(false);
		pack();

		setTitle("Brick Breaker");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {

		EventQueue.invokeLater(() -> {
			JFrame ex = new BB();
			ex.setVisible(true);
		});
	}
}
