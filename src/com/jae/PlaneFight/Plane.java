package com.jae.PlaneFight;

import java.awt.*;
import java.util.List;
import java.util.Random;

//飞机类
public class Plane{
	//定义飞机的x、y轴速度，及飞机区域大小.
	/*public static final int xSpeed = 5;
	public static final int ySpeed = 5;*/
	public static final int width = 30;
	public static final int height = 25;
	public static final int GoodWidth = 40;
	public static final int GoodHeight = 50;
	public static int speed = 1;
	Random random = new Random();
	//给飞机设定生死
		private boolean live = true;
		//isLive方法
		public boolean isLive() {
			return live;
		}
		public void setLive(boolean live) {
			this.live = live;
		}
		//MainFrame mf;
		PlaneJPanel pj;
		//好坏
		private boolean good;
		public boolean isGood() {
			return good;
		}
		
	//	private boolean bL=false, bU=false, bR=false, bD=false; //先写鼠标拖动的，再加键盘操作的。
	
		 int x,y;
		//int oldX, oldY;
		
		public Plane(int x,int y, boolean good){
			this.x = x;
			this.y = y;
			//this.oldX = x;
			//this.oldX = y;
			this.good = good;
			}
		public Plane(int x,int y, boolean good, PlaneJPanel pj) {
			this(x,y,good);
			
			this.pj = pj;
			}
	
	public void draw(Graphics g){
		//g.clearRect(0, 0, width, height);
		if(!live) {
			if(!good){
				pj.planes.remove(this);
			}
			return;
			}
		if(good){
			
			g.drawImage(pj.getPic("plane.png"), x, y, GoodWidth, GoodHeight, null);
		}else{
			g.drawImage(pj.getPic("ep2.png"), x, y, width, height, null);
		}		
	}
	/*Plane em;
	public void setPlane(Plane p){
		em = p;
	}*/
	public void move(){
		y += speed;
		if(y >= MainFrame.GameHeight){
			y = -random.nextInt(50);
			x = random.nextInt(MainFrame.GameWidth-width);
		}
	}
	public void fire(){
		pj.bubbles.add(new Bubble(x+15,y,pj));
	}
	public Rectangle getRect(){
		if(!good)
		{
			return new Rectangle(x, y, width, height);
		}else{
			return new Rectangle(x, y, GoodWidth, GoodHeight);
		}
	}
	public boolean hitPlane(Plane p){
		if(this.live && this.getRect().intersects(p.getRect()) && p.isLive()){
			p.setLive(false);
//			System.out.print("死了");
			pj.allDone = false;
			this.live = false;
			
		}
		
		return false;
	}
public boolean hitPlanes(List<Plane> planes){
	for(int i=0; i<planes.size(); i++){
		if(hitPlane(planes.get(i))) {
			return true;
		}
		
	}
	
	return false;
}
}
/*enum Direction{
	//枚举方向类 。
	L,
	R,
	U,
	D,
	LD,
	LU,
	RD,
	RU,
	STOP;
}*/