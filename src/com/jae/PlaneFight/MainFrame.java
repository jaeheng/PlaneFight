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
		// ����showFrame������������
		showFrame("�ɻ���ս", 400, 20, GameWidth, GameHeight);

	}
	public static void showFrame(String title, int x, int y, int width,
			int height) {
		// ������ʵ����һ��JFrame����
		JFrame frame = new JFrame();
		// �ô�������title������frame�ı���
		frame.setTitle(title);
		// ����frame��λ�ü���С
		frame.setBounds(x, y, width, height);
		// ����frame�޷��ı��С
		frame.setResizable(false);
		// ���ùرմ��ڵ�Ĭ�ϲ��� Ϊ�˳�����
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// ����JPanel��Ķ��󣬲�ʵ����
		PlaneJPanel panel = new PlaneJPanel();
		// ��panel��ӵ�frame��
		frame.add(panel);
		//������¼���ӵ�frame
		frame.addMouseMotionListener(panel);
		// ��ʾframe
		frame.setVisible(true);
		
		new Thread(panel).start();
		
		
	}
}

// ����JPanel������
class PlaneJPanel extends JPanel  implements Runnable,MouseMotionListener{
	/**
	 * 
	 */
	public int scare = 0;
	public int scare2 = 0;
	public int level = 5;
	private static final long serialVersionUID = 1L;
	// List planes ��л�
	List<Plane> planes = new ArrayList<Plane>();
	//List bubbles ���ӵ�
	List<Bubble> bubbles = new ArrayList<Bubble>();
	// ���������x,y����
	Random random = new Random();
	int myPlaneX = 10;
	int myPlaneY = MainFrame.GameHeight-75;
	Plane myPlane = new Plane(myPlaneX,myPlaneY, true, this);

	boolean allDone = true;
	// SP1:����һ��getPic����������ȥһ��ͼƬ·�����������ͼƬ��
	public Image getPic(String imgUrl) {
		// SP2:����Images���ǳ����࣬����ʵ����������ʵ����ImageIcon��Ķ���
		ImageIcon Icon = new ImageIcon(imgUrl);
		// SP3:�����ķ���getImage()���õ���ͼƬ��
		Image img = Icon.getImage();
		// SP4:���ظ�ͼƬ
		return img;
	}

	public int num = 10;
	// ��дpaint�࣬������ͼ
	public void paint(Graphics g) {
		// ����super.paint();ʹ�ػ��ʱ������
		super.paint(g);
		// ������ 
		g.drawImage(getPic("background.png"), 0, 0, 400, 600, null);
		// ����л�����С�ڵ���0��������10���л�
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
		
		
		//�л�
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
		String str = (String)("������ʽ1��("+scare2*level+")");
		g.drawString(str, 10, 10);
		String str2 = (String)("������ʽ2("+scare+")");
		g.drawString(str2, 10, 30);
		String str3 = (String)("�ܷ�:("+(scare2*level+scare)+")");
		g.drawString(str3, 10, 50);
		
		if(myPlane.isLive() == false){
			Font font = new Font("����",Font.BOLD,36);
			g.setFont(font);
			g.setColor(Color.red);
			g.drawString("��Ϸ����", 100, 300);
			
			Font fontScr = new Font("����",Font.BOLD,15);
			g.setFont(fontScr);
			g.drawString("����"+str3,120,350);
		}
		
		
	}
	//run
	public void run(){
		while(allDone) {
			repaint();//�����ⲿ��װ��repaint�ػ�
			
			try{
				Thread.sleep(10);//��̬�̷߳�����ÿ��50����repaintһ��
			}catch (InterruptedException e){
				e.printStackTrace();
				}
			
			}
		
		}
	/*//�ӵ�ײ���ɻ�
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
		//������µ�ʱ��
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
		// ����ƶ��������ϵ�ʱ��
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