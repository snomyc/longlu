package com.longlu.test;
import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class AutoPress {
	JFrame jfFrame = new JFrame();

	public AutoPress() {

		JLabel jLabel = new JLabel("按F1开启自动按键，F2退出!");
		jfFrame.setTitle("自动按键");
		jfFrame.setSize(200, 200);
		jfFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jfFrame.add(jLabel);
		jfFrame.addKeyListener(new AutoRobot());
		// jfFrame.addMouseListener(new AutoRobot());
		jfFrame.setVisible(true);
		// jfFrame.dispose();
	}

	public static void main(String[] args) throws AWTException {
		new AutoPress();
	}

	// }

	class AutoRobot implements KeyListener, MouseListener {

		public void flash() throws AWTException {
			Robot robot = new Robot();
			robot.delay(100);
			//按f5
			//robot.keyPress(116);
			//先按w
			robot.keyPress(KeyEvent.VK_W);
			//再按2
			robot.keyPress(KeyEvent.VK_2);
		}

		public void flash2() throws AWTException {
			Robot robot = new Robot();
			robot.delay(3000);
			//robot.keyPress(116);
			//先按w
			robot.keyPress(KeyEvent.VK_W);
			//再按2
			robot.keyPress(KeyEvent.VK_2);
			// robot.mousePress(1);
		}

		// 按下某个键时调用此方法。
		public void keyPressed(KeyEvent e) {
			// System.out.println(e.getKeyCode());
			while (true) {
				// System.out.println(1);
				if (e.getKeyCode() == KeyEvent.VK_F2) {
					jfFrame.dispose();
				}
				if (e.getKeyCode() == KeyEvent.VK_F1)
					try {
						flash();
					} catch (AWTException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		}

		public void keyReleased(KeyEvent e) {

		}

		public void keyTyped(KeyEvent e) {

		}

		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		public void mousePressed(MouseEvent e) {
			// System.out.println(e.getButton());
			while (true) {

				try {
					flash2();
				} catch (AWTException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// System.out.println(1);
			}

		}

		public void mouseReleased(MouseEvent e) {

		}

		public void mouseEntered(MouseEvent e) {

		}

		public void mouseExited(MouseEvent e) {

		}

	}
}//