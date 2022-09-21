package model;
import java.awt.*;
import java.awt.geom.*;

import main.*;

//玩家類
public class PlayerChild extends GameObject {
	int counter;
	int num;
	
	Player player;
	
    public PlayerChild(Player iplayer, int inum) {
        player = iplayer;
    	x = player.x;
		y = player.y;
		num = inum;
		active = false;
		counter = 0;
	}
    
    public void move() {
		double radian;
		radian = Math.toRadians(counter%360);	//度數轉弧度

		x = player.x+(player.is_slow?15:45)*Math.cos(radian+((num==1)?0:180));
		y = player.y+10*Math.sin(radian+((num==1)?0:180));
	}
    
	public void draw(Graphics g) {	//循環一次繪製一次
		counter+=3;
		Graphics2D g2=(Graphics2D)g;
		g2.setColor(Color.green);
		//繪製圖形
		g2.draw(new Line2D.Double(x, y-7, x-5, y+3.5));
		g2.draw(new Line2D.Double(x, y-7, x+5, y+3.5));
		g2.draw(new Line2D.Double(x-5, y+3.5, x+5, y+3.5));
	}
    
}
