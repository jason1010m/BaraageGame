package model;
import java.awt.*;
import java.awt.geom.*;

import main.*;

//敵人子彈類
public class EnemyBullet extends GameObject {
	double direction;
	double speed;
	double speedX;
	double speedY;
	int loiter;
	int counter;
	double turn;
	boolean is_right;
	boolean do_rebound;
	boolean do_right;
	boolean do_color;
	public int btype;
	Color color;
	
	public EnemyBullet() {	//敵人子彈建構方法
		active = false;
	}
	
	public void move() {
		switch(btype) {
			case 0:
			x += speedX;
			y += speedY;
			break;
			case 1:
			x += speedX;
			y += speedY;
			if(loiter!=0) {
				if(counter==loiter) {
					direction+=turn;
					double radian;
					radian = Math.toRadians(direction);	//度數轉弧度
					speedX = speed / 3 * Math.cos(radian);
					speedY = speed / 3 * Math.sin(radian);
				}
			}
			if(do_right) {
				if(counter<360/4) {
					direction+=is_right?4:-4;
					double radian;
					radian = Math.toRadians(direction);	//度數轉弧度
					speedX = speed * Math.cos(radian);
					speedY = speed * Math.sin(radian);
				}
				else {
					direction+=is_right?0.5:-0.5;
					double radian;
					radian = Math.toRadians(direction);	//度數轉弧度
					speedX = speed * Math.cos(radian);
					speedY = speed * Math.sin(radian);
				}
			}
			counter++;
			break;
			case 2:
				x += speedX;
				y += speedY;
				if(counter==30) {
					direction+=is_right?90:-90;
					speed-=4;
					double radian;
					radian = Math.toRadians(direction);	//度數轉弧度
					speedX = speed * Math.cos(radian);
					speedY = speed * Math.sin(radian);
				}
				if(counter>30) {
					direction+=is_right?0.2:-0.2;
					double radian;
					radian = Math.toRadians(direction);	//度數轉弧度
					speedX = speed * Math.cos(radian);
					speedY = speed * Math.sin(radian);
				}
				counter++;
				break;
			case 3:
			x += speedX;
			y += speedY;
			break;
			default:
			break;
		}
		if ( (x < 0)||(600 < x)||(y < 0)||(800 < y) ) {
			double radian;
			if((x < 0)||(600 < x))direction=180-direction;
			if((y < 0)||(800 < y))direction=-direction;
			radian = Math.toRadians(direction);	//度數轉弧度
			speedX = speed * Math.cos(radian);
			speedY = speed * Math.sin(radian);
			if(!do_rebound) active = false;
			if(do_rebound)do_rebound=false;
		}
	}
	
    public Area getBounds() {
		Area bounds;
		AffineTransform at = new AffineTransform();
    	switch(btype) {
    		case 0:
    		bounds = new Area(new Ellipse2D.Double(x-8,y-8,16,16));
    		return bounds;
    		case 1:
    		at.rotate(Math.toRadians(direction+90),x,y);
    		bounds=new Area(new Ellipse2D.Double(x-3,y-8,6,16));
    		bounds.transform(at);
    		return bounds;
    		case 2:
    		at.rotate(Math.toRadians(direction+90),x,y);
    		bounds=new Area(new Rectangle2D.Double(x-8,y-8,16,16));
    		bounds.transform(at);
    		return bounds;
    		case 3:
    		bounds = new Area(new Ellipse2D.Double(x-16,y-16,32,32));
    		return bounds;
    		default:
    		return null;
    	}
    }
	
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D)g;
		switch(btype) {
			case 0:
			g2.setColor(do_color?color:Color.red);
			g2.fill(new Ellipse2D.Double(x-8,y-8,16,16));
			g2.setColor(Color.white);
			g2.fill(new Ellipse2D.Double(x-7,y-7,14,14));
			break;
			case 1:
			g2.setColor(do_color?color:Color.blue);
			g2.rotate(Math.toRadians(direction+90),x,y);
			g2.fill(new Ellipse2D.Double(x-3,y-8,6,16));
			g2.setColor(Color.white);
			g2.fill(new Ellipse2D.Double(x-2,y-7,4,14));
			g2.rotate(-Math.toRadians(direction+90),x,y);
			break;
			case 2:
			g2.setColor(Color.white);
			g2.rotate(Math.toRadians(direction+90),x,y);
			g2.fill(new Rectangle2D.Double(x-8,y-8,16,16));
			g2.setColor(is_right?new Color(204,51,255):new Color(77,77,255));
			g2.fill(new Rectangle2D.Double(x-6,y-6,12,12));
			g2.rotate(-Math.toRadians(direction+90),x,y);
			break;
			case 3:
			g2.setColor(Color.white);
			g2.fill(new Ellipse2D.Double(x-24,y-24,48,48));
			g2.setColor(is_right?Color.red:Color.blue);
			g2.fill(new Ellipse2D.Double(x-16,y-16,32,32));
			break;
			default:
			break;
		}
	}

	/**
	 * 移動處理
	 * 參數 ix(double)：生成處的X座標 
	 * 參數 iy(double)：生成處的Y座標
	 * 參數 ispeed(double)：速度
	 * 參數 idirection(double)：方向(0-360)
	 */	
	public void activate(double ix, double iy, double idirection, double ispeed, int ibtype) {
		x = ix;
		y = iy;
		direction = idirection;
		speed = ispeed;
		loiter = 0;
		counter = 0;
		btype = ibtype;
		turn = 0;
		do_right = false;
		do_rebound = false;
		do_color = false;
		active = true;
				
		//提高處理速度，從極座標轉到xy
		double radian;
		radian = Math.toRadians(direction);	//度數轉弧度
		speedX = speed * Math.cos(radian);
		speedY = speed * Math.sin(radian);
		
	}
	
	/**
	 * 移動處理
	 * 參數 ix(double)：生成處的X座標 
	 * 參數 iy(double)：生成處的Y座標
	 * 參數 ispeed(double)：速度
	 * 參數 idirection(double)：方向(0-360)
	 */	
	public void activate(double ix, double iy, double idirection, double ispeed, int ibtype, Color icolor) {
		x = ix;
		y = iy;
		direction = idirection;
		speed = ispeed;
		loiter = 0;
		counter = 0;
		btype = ibtype;
		turn = 0;
		do_right = false;
		do_rebound = false;
		do_color = true;
		color = icolor;
		active = true;
				
		//提高處理速度，從極座標轉到xy
		double radian;
		radian = Math.toRadians(direction);	//度數轉弧度
		speedX = speed * Math.cos(radian);
		speedY = speed * Math.sin(radian);
		
	}
	
	/**
	 * 移動處理
	 * 參數 ix(double)：生成處的X座標 
	 * 參數 iy(double)：生成處的Y座標
	 * 參數 ispeed(double)：速度
	 * 參數 idirection(double)：方向(0-360)
	 */	
	public void activate(double ix, double iy, double idirection, double ispeed, boolean iright, int ibtype) {
		x = ix;
		y = iy;
		direction = idirection;
		speed = ispeed;
		loiter = 0;
		counter = 0;
		btype = ibtype;
		turn = 0;
		is_right = iright;
		do_right = true;
		do_rebound = false;
		do_color = false;
		active = true;
				
		//提高處理速度，從極座標轉到xy
		double radian;
		radian = Math.toRadians(direction);	//度數轉弧度
		speedX = speed * Math.cos(radian);
		speedY = speed * Math.sin(radian);
		
	}
	
	/**
	 * 敵彈初始化
	 * 參數 ix(double)：生成處x座標
	 * 參數 iy(double)：生成處y座標
	 * 參數 idirection(double)：初始移動方向
	 * 參數 ispeed(double)：移動的速度
	 * 參數 iloiter(int)：移動多少禎換動作
	 * 參數 turn(double)：變化時角度變化量
	 */
	public void activate(double ix, double iy, double idirection, double ispeed, int iloiter, double iturn, boolean irebound, int ibtype) {
		x = ix;
		y = iy;
		direction = idirection;
		speed = ispeed;
		loiter = iloiter;
		counter = 0;
		btype = ibtype;
		turn = iturn;
		do_right = false;
		do_rebound = irebound;
		do_color = false;
		active = true;
				
		//提高處理速度，從極座標轉到xy
		double radian;
		radian = Math.toRadians(direction);	//度數轉弧度
		speedX = speed * Math.cos(radian);
		speedY = speed * Math.sin(radian);
	}
	
	public static void FireRound(double x, double y, double speed) {	//全方位雙重彈幕圈
		for (int i = 0; i < 360; i += 8 )
		{
			ObjectPool.newEnemyBullet(x, y, i, speed, 0);
		}
	}
	
	public static void FireHurricane(double x, double y, double idirection, double iturn) {	//颶風式交錯彈幕
		for (int i = 0; i < 6; i ++ )
		{
			ObjectPool.newEnemyBullet(x, y, (i*60+idirection*10)%360, 8, 30, iturn, false, 1);
		}
	}
	
	public static void WaterFountain(double x, double y, double idirection) {	//如水眼之美麗源泉
		for(int i = 0; i < 3; i ++) {
			for (int j = 0; j < 8; j ++ )
			{
				ObjectPool.newEnemyBullet(x, y, (idirection+i*120)%360, 7+j*0.5, 1);
			}
		}
	}
	
	public static void WaterRipple(double x, double y) {	//水波紋
		for (int i = 0; i < 360; i += 5 ) {
			ObjectPool.newEnemyBullet(x, y, i, 4, 0, 0, true, 1);
		}
	}
	
	public static void CardSpin(double x, double y, boolean iright) {	//旋轉卡牌
		for (int i = 0; i < 360; i += 10 ) {
			ObjectPool.newEnemyBullet(x, y, i, 6, iright, 2);
		}
	}
	
	public static void FireRoundBig(double x, double y, double idirection, boolean iright) {	//全方位雙重彈幕圈(大子彈)
		for (int i = 0; i < 360; i += 15 )
		{
			ObjectPool.newEnemyBullet(x, y, i+idirection, 2 , iright, 3);
		}
	}
	
	public static void FireRegress(double x, double y, boolean iright) {	//回歸旋轉彈幕
		for (int i = 0; i < 360; i += 10 ) {
			ObjectPool.newEnemyBullet(x, y, i, 6, iright, 1);
		}
	}
	
	//向玩家方向發射子彈
	public static void FireAim(double x, double y, Player player) {
		double degree = Math.toDegrees(Math.atan2(player.y - y, player.x - x));
		ObjectPool.newEnemyBullet(x, y, degree, 4, 1, Color.green);
		ObjectPool.newEnemyBullet(x, y, degree+10, 4, 1, Color.green);
		ObjectPool.newEnemyBullet(x, y, degree-10, 4, 1, Color.green);
	}
}
