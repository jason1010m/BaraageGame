package model;
import java.awt.*;
import java.awt.geom.Ellipse2D;

import main.GameObject;

public class ParticleHalo extends GameObject {
	int counter;
	int delay;
	int size;
	
	public ParticleHalo(){
		active = false;
	}
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		if(counter==delay)active = false;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		counter++;
		if(counter>5) {
			Graphics2D g2 = (Graphics2D)g;
			g2.setColor(Color.blue);
			g2.fill(new Ellipse2D.Double(x-size/2,y-size/2,size,size));
			g2.setColor(Color.white);
			g2.fill(new Ellipse2D.Double(x-(size/2-size/10),y-(size/2-size/10),(size/2-size/10)*2,(size/2-size/10)*2));
		}
	}
	
	public void activate(double ix, double iy, int idelay, int isize) {
		x=ix;
		y=iy;
		delay=idelay;
		size=isize;
		counter=0;
		active=true;
	}
}
