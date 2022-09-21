package model;
import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;

import main.*;

//玩家子彈類
public class PlayerBullet extends GameObject {
	
	final int speed = 20;
	double speedX;
	double speedY;
	
	public PlayerBullet() {	//玩家子彈建構方法
		active = false;
	}
	
	public void move() {	//玩家子彈的移動
        x += speedX;
        y += speedY;
		//移動出螢幕後消失
		if ( (y < 0) ){
			active = false;
		}
	}
	
    public Area getBounds() {
    	return new Area(new Rectangle2D.Double(x-3, y-10, 6, 20));
    }
	
	public void draw(Graphics g) {	//循環一次執行一次
		Graphics2D g2 = (Graphics2D)g;
		g2.setColor(Color.red);
		//繪製長方形
		g2.draw(new Rectangle2D.Double(x-3, y-10, 6, 20));
	}
	
	//在玩家位置進行初始化
	public void activate(double ix, double iy, double idirection) {
		x = ix;
		y = iy;
		//提高處理速度，從極座標轉到xy
		double radian;
		radian = Math.toRadians(idirection);	//度數轉弧度
		speedX = speed * Math.cos(radian);
		speedY = speed * Math.sin(radian);
		active = true;	//可視化子彈
	}
}
