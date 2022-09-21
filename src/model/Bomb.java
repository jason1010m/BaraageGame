package model;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

import main.GameObject;

public class Bomb extends GameObject {
	static int amount;
	int counter;
	int size;
	
	Player player;
	
	final int duration = 180;
	
	public Bomb(){
		active = false;
		amount = 3;
	}
	
	@Override
	public void move() {
		// TODO Auto-generated method stub
		x=player.x;
		y=player.y;
		size += (counter > duration / 2)?-5:(size<180)?5:0;
		if(size < 25)active = false;
	}

    public Area getBounds() {
    	return new Area(new Ellipse2D.Double(x-size/2,y-size/2,size,size));
    }
	
	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		counter++;
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.green);
		g2.draw(new Ellipse2D.Double(x-size/2,y-size/2,size,size));
	}
	
	public static void lessBomb() {
		amount --;
	}
	
	public static int getBomb() {
		return amount;
	}
	
	public void activate(Player iplayer) {
		player = iplayer;
		x=player.x;
		y=player.y;
		size=25;
		counter=0;
		active=true;
	}
}
