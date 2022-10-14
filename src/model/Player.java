package model;
import java.awt.*;
import java.awt.geom.*;

import main.*;

//玩家類
public class Player extends GameObject {
	double speed;
	boolean is_slow;
	/**
	 * 移動處理
	 * 參數 ix(double)：生成處的X座標
	 * 參數 iy(double)：生成處的Y座標
	 * 參數 ispeed(double)：移動速度
	 */	
    public Player(double ix, double iy, double ispeed) {
        x = ix;
		y = iy;
		speed = ispeed;
		active = false;
	}
    
    public void move() {
    	//抽象類中的抽象方法的參數必須一致
    }
    
	/**
	 * 移動處理
	 * 參數 mx(double)：x方向的移動（-1...+1） 
	 * 參數 my(double)：y方向的移動（-1...+1）
	 */
    public void move(int mx, int my, boolean i_slow) {
    	is_slow=i_slow;
		double postX = x + mx * (speed - (is_slow?3:0));
		double postY = y + my * (speed - (is_slow?3:0));
		//避免移動到Canvas外面
		if ((0 < postX)&&(postX < 595)) {
			x = postX;
		}
		if ((0 < postY)&&(postY < 765)) {
			y = postY;
		}
	}
    
    public Area getBounds() {
    	return new Area(new Ellipse2D.Double(x-4, y-4, 8, 8));
    }
    
	public void draw(Graphics g) {	//循環一次繪製一次
		if (active){
			Graphics2D g2=(Graphics2D)g;
			g.setColor(Color.green);
			//繪製玩家圖形
			g2.draw(new Ellipse2D.Double(x-12, y-12, 24, 24));
			//繪製碰撞點
			if(is_slow) {
				g.setColor(Color.red);
				g2.fill(new Ellipse2D.Double(x-4, y-4, 8, 8));
			}
		}
	}
    
}
