package com.jae.PlaneFight;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.*;

public class MainFrame {
	public static final int GameWidth = 400;
	public static final int GameHeight = 600;
	
	public static void main(String[] args) {
		// 调用showFrame函数启动程序。
		showFrame("飞机大战", 400, 20, GameWidth, GameHeight);

	}
	public static void showFrame(String title, int x, int y, int width,
			int height) {
		// 声明并实例化一个JFrame对象
		JFrame frame = new JFrame();
		// 用传进来的title，设置frame的标题
		frame.setTitle(title);
		// 设置frame的位置及大小
		frame.setBounds(x, y, width, height);
		// 设置frame无法改变大小
		frame.setResizable(false);
		// 设置关闭窗口的默认操作 为退出程序
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 创建JPanel类的对象，并实例化
		PlaneJPanel panel = new PlaneJPanel();
		// 将panel添加到frame上
		frame.add(panel);
		//将鼠标事件添加到frame
		frame.addMouseMotionListener(panel);
		// 显示frame
		frame.setVisible(true);
		
		new Thread(panel).start();
		
		
	}
}

// 创建JPanel画布类
class PlaneJPanel extends JPanel  implements Runnable,MouseMotionListener{
	/**
	 * 
	 */
	public int scare = 0;
	public int scare2 = 0;
	public int level = 5;
	private static final long serialVersionUID = 1L;
	// List planes 存敌机
	List<Plane> planes = new ArrayList<Plane>();
	//List bubbles 存子弹
	List<Bubble> bubbles = new ArrayList<Bubble>();
	// 定义随机的x,y坐标
	Random random = new Random();
	int myPlaneX = 10;
	int myPlaneY = MainFrame.GameHeight-75;
	Plane myPlane = new Plane(myPlaneX,myPlaneY, true, this);

	boolean allDone = true;
	// SP1:定义一个getPic方法，传进去一个图片路径，返回这个图片。
	public Image getPic(String imgUrl) {
		// SP2:由于Images类是抽象类，不能实例化对象。先实例化ImageIcon类的对象
		ImageIcon Icon = new ImageIcon(imgUrl);
		// SP3:用它的方法getImage()；得到该图片。
		Image img = Icon.getImage();
		// SP4:返回该图片
		return img;
	}

	public int num = 10;
	// 覆写paint类，用来画图
	public void paint(Graphics g) {
		// 调用super.paint();使重绘的时候清屏
		super.paint(g);
		// 画背景 
		g.drawImage(getPic("background.png"), 0, 0, 400, 600, null);
		// 如果敌机数量小于等于0，再生成10辆敌机
		if (planes.size() <= 0) {
			
			for (int i = 0; i < num; i++) {
				planes.add(new Plane(random.nextInt(MainFrame.GameWidth)-Plane.width,-random.nextInt(MainFrame.GameHeight), false, this));
				//planes.get(i).setPlane(planes.get(i));
				
			}
			num ++;
			
			
		}
		if(scare2 >30 && scare2 <60){Plane.speed = 2;}
		if(scare2 >60 && scare2 <90){Plane.speed = 3;}
		if(scare2 >90 && scare2 <120){Plane.speed = 4;}
		if(scare2 >120 && scare2 <150){Plane.speed = 5;}
		if(scare2 >150 && scare2 <300){Plane.speed = 6;}
		if(scare2 >300){Plane.speed = 10;}
		
		
		//敌机
		for (int i = 0; i < planes.size(); i++) {
			Plane p = planes.get(i);
			// t.collidesWithTanks(planes);
			
			//hitPlane(p.x, p.y, p);
			if(p.isLive() == true)
			{
				p.draw(g);
				p.move();
			}else{
				planes.remove(p);
			}
			
		}
		if(bubbles.size() <=0 && myPlane.isLive() == true){
			//for (int i = 0; i < 10; i++) {
			
				bubbles.add(new Bubble(myPlaneX+15,myPlaneY,this));
			
			//}
		}
		for (int i = 0; i < bubbles.size(); i++) {
			Bubble b = bubbles.get(i);
			b.hitPlanes(planes);
			if(myPlane.isLive() == true){
				b.draw(g);			
				b.move();
			}
			//for(int j = 0; j < 1000000; j ++);
		}
		myPlane.hitPlanes(planes);
		myPlane.draw(g);
		//myPlane.fire();
		String str = (String)("分数形式1：("+scare2*level+")");
		g.drawString(str, 10, 10);
		String str2 = (String)("分数形式2("+scare+")");
		g.drawString(str2, 10, 30);
		String str3 = (String)("总分:("+(scare2*level+scare)+")");
		g.drawString(str3, 10, 50);
		
		if(myPlane.isLive() == false){
			Font font = new Font("黑体",Font.BOLD,36);
			g.setFont(font);
			g.setColor(Color.red);
			g.drawString("游戏结束", 100, 300);
			
			Font fontScr = new Font("黑体",Font.BOLD,15);
			g.setFont(fontScr);
			g.drawString("您的"+str3,120,350);
		}
		
		
	}
	//run
	public void run(){
		while(allDone) {
			repaint();//调用外部包装类repaint重画
			
			try{
				Thread.sleep(10);//静态线程方法，每隔50毫秒repaint一次
			}catch (InterruptedException e){
				e.printStackTrace();
				}
			
			}
		
		}
	/*//子弹撞击飞机
		public void hitPlane(int ePlaneX, int ePlaneY, Plane p){
			//int juli = 30;
			int a = ePlaneX - myPlaneX;
			int b = ePlaneY - myPlaneY;
			int sum = a*a + b*b;
			if(sum <= 30*30){
				bubbles.remove(p);
			}
		}*/
	@Override
	public void mouseDragged(MouseEvent e) {
		//鼠标摁下的时候
		//new Plane(e.getX(),e.getY(),true);
				Bubble.imgUrl = "superFire.png";
				Bubble.height = 60;
				Bubble.width = 30;
				level = 10;
				myPlane.x = e.getX()-30;
				myPlane.y = e.getY()-50;
				myPlaneX = e.getX()-40;
				myPlaneY = e.getY()-70;
				if(e.getY() >= MainFrame.GameHeight-Plane.GoodHeight+25){
					myPlane.y = MainFrame.GameHeight-Plane.GoodHeight-25;
					myPlaneY = MainFrame.GameHeight-Plane.GoodHeight-25;
				}else if(e.getX() >= MainFrame.GameWidth-Plane.GoodWidth+30)
				{
					myPlane.x = MainFrame.GameWidth-Plane.GoodWidth;
					myPlaneX = MainFrame.GameWidth-Plane.GoodWidth;
					
				}else if(e.getX() <= 30){
					myPlane.x = 0;
					myPlaneX = 0;
				}
			}


	@Override
	public void mouseMoved(MouseEvent e) {
		// 鼠标移动到窗口上的时候
		Bubble.imgUrl = "fire.png";
		Bubble.height = 10;
		Bubble.width = 5;
		myPlane.x = e.getX()-30;
		myPlane.y = e.getY()-50;
		myPlaneX = e.getX()-30;
		myPlaneY = e.getY()-50;
		if(e.getY() >= MainFrame.GameHeight-Plane.GoodHeight+25){
			myPlane.y = MainFrame.GameHeight-Plane.GoodHeight-25;
			myPlaneY = MainFrame.GameHeight-Plane.GoodHeight-25;
		}else if(e.getX() >= MainFrame.GameWidth-Plane.GoodWidth+30)
		{
			myPlane.x = MainFrame.GameWidth-Plane.GoodWidth;
			myPlaneX = MainFrame.GameWidth-Plane.GoodWidth;
			
		}else if(e.getX() <= 30){
			myPlane.x = 0;
			myPlaneX = 0;
		}
	}
}