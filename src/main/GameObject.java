package main;
import java.awt.*;


public abstract class GameObject {

	public boolean active;	//有效標記值
	public double x;	//X座標
	public double y;	//Y座標
	
	public abstract void move();	//單步執行
	public abstract void draw(Graphics g);	//繪製
	
}
