package model;
import java.awt.*;
import java.awt.geom.Ellipse2D;

import main.GameObject;

//魔法陣特效
public class MagicCircle extends GameObject {
	static final double radians[]= {Math.toRadians(330),Math.toRadians(90),Math.toRadians(210),Math.toRadians(30),Math.toRadians(150),Math.toRadians(270)};
	
	EnemyBoss enemy;
	
	double direction;
	double speed;
	int size;
	int sizeLow;
	int sizeHigh;
	boolean sV=false;
	
	public MagicCircle(EnemyBoss ienemy[]){
		enemy = ienemy[0];
		active = false;
	}
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		x=enemy.x;
		y=enemy.y;
		direction=(direction+5)%360;
		size+=sV?-1:1;
		if(size==sizeLow||size==sizeHigh) {sV=!sV;}
		if(enemy.active==false)active=false;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(new Color(255,0,0,100));
		g2.setStroke(new BasicStroke(5.0f));
		g2.rotate(Math.toRadians(direction),x,y);
		g2.draw(new Ellipse2D.Double(x-size/2-size/10/2,y-size/2-size/10/2,size+size/10,size+size/10));
		g2.draw(new Ellipse2D.Double(x-size/2,y-size/2,size,size));
		g2.drawLine((int)(x-size/2*Math.cos(radians[0])), (int)(y-size/2*Math.sin(radians[0])), (int)(x-size/2*Math.cos(radians[1])), (int)(y-size/2*Math.sin(radians[1])));
		g2.drawLine((int)(x-size/2*Math.cos(radians[1])), (int)(y-size/2*Math.sin(radians[1])), (int)(x-size/2*Math.cos(radians[2])), (int)(y-size/2*Math.sin(radians[2])));
		g2.drawLine((int)(x-size/2*Math.cos(radians[2])), (int)(y-size/2*Math.sin(radians[2])), (int)(x-size/2*Math.cos(radians[0])), (int)(y-size/2*Math.sin(radians[0])));
		g2.drawLine((int)(x-size/2*Math.cos(radians[3])), (int)(y-size/2*Math.sin(radians[3])), (int)(x-size/2*Math.cos(radians[4])), (int)(y-size/2*Math.sin(radians[4])));
		g2.drawLine((int)(x-size/2*Math.cos(radians[4])), (int)(y-size/2*Math.sin(radians[4])), (int)(x-size/2*Math.cos(radians[5])), (int)(y-size/2*Math.sin(radians[5])));
		g2.drawLine((int)(x-size/2*Math.cos(radians[5])), (int)(y-size/2*Math.sin(radians[5])), (int)(x-size/2*Math.cos(radians[3])), (int)(y-size/2*Math.sin(radians[3])));
		g2.setStroke(new BasicStroke(1.0f));
		g2.rotate(-Math.toRadians(direction),x,y);
	}
	
	public void activate(double ix, double iy, double ispeed, int isize, int isizeVariable) {
		x=ix;
		y=iy;
		direction=0;
		speed=ispeed;
		size=isize;
		sizeLow=size;
		sizeHigh=size+isizeVariable;
		active=true;
	}
}
