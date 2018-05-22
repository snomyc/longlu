package com.longlu.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
public class JFrameAutoPress {

	private JFrame jFrame = new JFrame();

	public JFrameAutoPress() {
		JLabel jLabel = new JLabel("按F1开启自动按键，F2退出!");
		jFrame.setTitle("自动按键工具");
		jFrame.setSize(200, 200);
		//窗口放置于屏幕中央
		jFrame.setLocationRelativeTo( null);
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.add(jLabel);
		//监听按键
		jFrame.addKeyListener(new AutoPress());
		//监听鼠标
		// jfFrame.addMouseListener(new AutoRobot());
		jFrame.setVisible(true);
	}

	public static void main(String[] args) throws AWTException {
		new JFrameAutoPress();
	}
	
	class AutoPress implements KeyListener,Runnable{
		
		public void run() {
			try {
				while(true) {
					Robot robot = new Robot();
					robot.delay(2000);
					//随机按下1个键 VK_A=65 VK_Z=90  VK_0=48 VK_9=57 VK_ENTER=10
					//生成65-90之间的随机数
					//int random=(int)(65+Math.random()*25);
					//int random = new Random().nextInt(25)+65;
					//robot.keyPress(random);
					//按回车键
					//robot.keyPress(10);
					//先按w
					robot.keyPress(KeyEvent.VK_W);
					//再按2
					robot.keyPress(KeyEvent.VK_2);
					//按回车键
					//robot.keyPress(10);
				}
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}

		//键入某个键时调用此方法 不产生 Unicode 字符的键组合（如 F1 和 HELP 键等动作键）不会生成 KEY_TYPED 事件
		public void keyTyped(KeyEvent e) {
		}

		//按下某个键时调用此方法
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_F2) {
				JOptionPane.showMessageDialog(null, "您已经按下F2键，程序即将退出!");
				//jFrame.dispose();
				//关闭程序
				System.exit(0);
			}
			if (e.getKeyCode() == KeyEvent.VK_F1) {
				try {
					JOptionPane.showMessageDialog(null, "您已经按下F1键，开启新线程开始运行!");
					//开启一个线程专门自动按键
					AutoPress autoPress = new AutoPress();
					new Thread(autoPress).start();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
				
		}

		//释放某个键时调用此方法
		public void keyReleased(KeyEvent e) {
			System.out.println("3");
		}

	}
}
