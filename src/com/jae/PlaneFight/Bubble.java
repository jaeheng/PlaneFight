package com.jae.PlaneFight;

import java.awt.Graphics;
import java.util.List;
import java.awt.Rectangle;

public class Bubble {
	// ×Óµ¯Àà
	public static int width = 5;
	public static int height = 10;
	int speed = 15;
	boolean live = true;
	public boolean islive(){
		return live;
	}
	
	
	int killBlood = 1;
	public static String imgUrl = "fire.png";
	/*public void setimgUrl(String imgUrl){
		Bubble.imgUrl = imgUrl;
	}*/
	PlaneJPanel pj;
	
	private int x;
	private int y;
	
	Bubble(int x, int y, PlaneJPanel pj){
		this.x = x;
		this.y = y;
		this.pj = pj;
	}
	public void draw(Graphics g){
		if(!live){
			pj.bubbles.remove(this);
			return;
			
		}
		g.drawImage(pj.getPic(imgUrl), x, y, width, height, null);
		move();
	}
	public void move(){
		y -= speed;
		if(y <= 0){
			pj.bubbles.remove(this);
		}
	}
	public Rectangle getRect(){
		return new Rectangle(x, y, width, height);
	}
	public boolean hitPlane(Plane p){
		if(this.live && this.getRect().intersects(p.getRect()) && p.isLive()){
			p.setLive(false);
			pj.scare2++;
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
	pj.scare ++;
	return false;
}
}
