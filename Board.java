package brick_breaker;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Board extends JPanel implements ActionListener {

	private final int B_width = 800;
	private final int B_height = 600;
	private final int Ball_size = 50;
	private final int Break_size = 50;
	private final int All_Brick = 80;
	private final int DELAY = 140;

	private Image ball;
	private Image paddle;
	private Image brick;

	private Boolean leftDirection = false;
	private Boolean rightDirection = false;
	private Boolean spaceStart = false;

	private int paddle_x = 0;
	private int paddle_y = 580;

	private int ball_x = 65;
	private int ball_y = 560;

	private Boolean ingame = true;

	private Timer timer;

	private int score = 0;

	private Boolean left = true;
	private Boolean right = false;
	private Boolean up = true;
	private Boolean down = false;

	private int x[] = new int[All_Brick];
	private int y[] = new int[All_Brick];

	Board() {
		initboard();
	}

	private void initboard() {

		addKeyListener(new TAdapter());
		setBackground(Color.black);
		setFocusable(true);
		setPreferredSize(new Dimension(B_width, B_height));
		loadImages();
		initgame();

	}

	private void loadImages() {
		ImageIcon i1 = new ImageIcon("C:\\Users\\Mansi\\eclipse-workspace\\games\\src\\brick_breaker\\ball.png");
		ImageIcon i2 = new ImageIcon("C:\\Users\\Mansi\\eclipse-workspace\\games\\src\\brick_breaker\\paddle.png");
		ImageIcon i3 = new ImageIcon("C:\\Users\\Mansi\\eclipse-workspace\\games\\src\\brick_breaker\\brick.png");
		ball = i1.getImage();
		paddle = i2.getImage();
		brick = i3.getImage();
	}

	private void initgame() {

		int j = 0;
		for (int i = 0; i < 800; i = i + 50) {
			x[j] = i;
			y[j] = 0;
			j++;
		}
		for (int i = 0; i < 800; i = i + 50) {
			x[j] = i;
			y[j] = 30;
			j++;
		}
		for (int i = 50; i < 750; i = i + 50) {
			x[j] = i;
			y[j] = 60;
			j++;
		}
		for (int i = 100; i < 700; i = i + 50) {
			x[j] = i;
			y[j] = 90;
			j++;
		}
		for (int i = 150; i < 650; i = i + 50) {
			x[j] = i;
			y[j] = 120;
			j++;
		}
		for (int i = 200; i < 600; i = i + 50) {
			x[j] = i;
			y[j] = 150;
			j++;
		}
		timer = new Timer(DELAY, this);
		timer.start();

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		doDrawing(g);
	}

	private void doDrawing(Graphics g) {
		if (ingame) {
			for (int i = 0; i < x.length; i++) {
				g.drawImage(brick, x[i], y[i], 50, 30, Color.MAGENTA, this);
			}
			g.drawImage(paddle, paddle_x, paddle_y, 150, 20, Color.lightGray, this);
			g.drawImage(ball, ball_x, ball_y, 20, 20, Color.YELLOW, this);

			Toolkit.getDefaultToolkit().sync();
		} else {
			if (score == 76) {
				winwin(g);
			} else {
				gameOver(g);
			}
		}
	}

	private void gameOver(Graphics g) {
		String msg = "Game Over";
		String sc = "Score is " + Integer.toString(score);
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_width - metr.stringWidth(msg)) / 2, B_height / 2);
		g.drawString(sc, (B_width - metr.stringWidth(sc)) / 2, B_height / 2 + 20);
	}

	private void winwin(Graphics g) {
		String msg = "You Won";
		String sc = "Score is " + Integer.toString(score);
		Font small = new Font("Helvetica", Font.BOLD, 14);
		FontMetrics metr = getFontMetrics(small);

		g.setColor(Color.white);
		g.setFont(small);
		g.drawString(msg, (B_width - metr.stringWidth(msg)) / 2, B_height / 2);
		g.drawString(sc, (B_width - metr.stringWidth(sc)) / 2, B_height / 2 + 20);
	}

	private void destroyBrick() {
		for (int i = 0; i < x.length; i++) {
			if (ball_y == y[i] + 30 && ball_x >= x[i] && ball_x <= x[i] + 50) {
				x[i] = 1000;
				up = false;
				down = true;
				score++;
				if (score == 76) {
					ingame = false;
				}
			}
		}
	}

	private void checkfall() {
		if (ball_y >= 560 && (ball_x < paddle_x || ball_x > paddle_x + 150)) {
			ingame = false;
		}
		if (!ingame) {
			timer.stop();
		}
	}

	private void moveball() {
		if (spaceStart) {
			if (left == true && up == true) {
				ball_x = ball_x - 10;
				ball_y = ball_y - 10;
				if (ball_x <= 0) {
					right = true;
					left = false;
				}
				if (ball_y <= 0) {
					down = true;
					up = false;
				}
			} else if (right == true && up == true) {
				ball_x = ball_x + 10;
				ball_y = ball_y - 10;
				if (ball_y <= 0) {
					down = true;
					up = false;
				}
				if (ball_x >= 780) {
					left = true;
					right = false;
				}

			} else if (right == true && down == true) {
				ball_x = ball_x + 10;
				ball_y = ball_y + 10;
				if (ball_x >= 780) {
					left = true;
					right = false;
				}
				if (ball_y >= 560) {
					up = true;
					down = false;
				}

			} else if (left == true && down == true) {
				ball_x = ball_x - 10;
				ball_y = ball_y + 10;
				if (ball_y >= 560) {
					up = true;
					down = false;
				}
				if (ball_x <= 0) {
					right = true;
					left = false;
				}

			}

		}

	}

	private void movepaddle() {
		if (leftDirection == true && paddle_x > 0) {
			paddle_x = paddle_x - 20;
		} else if (rightDirection == true && paddle_x < 650) {
			paddle_x = paddle_x + 20;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (ingame) {

			destroyBrick();
			checkfall();
			moveball();
			movepaddle();
			leftDirection = false;
			rightDirection = false;
		}

		repaint();

	}

	private class TAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {

			int key = e.getKeyCode();

			if ((key == KeyEvent.VK_LEFT)) {
				leftDirection = true;
				rightDirection = false;
			}

			if ((key == KeyEvent.VK_RIGHT)) {
				rightDirection = true;
				leftDirection = false;
			}
			if ((key == KeyEvent.VK_SPACE)) {
				spaceStart = !spaceStart;
			}
		}
	}

}
