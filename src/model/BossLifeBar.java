package model;

import java.awt.*;
import java.awt.geom.Line2D;

//敵人血量顯示
public class BossLifeBar {
	static int myscore;
	static int hiscore;
	EnemyBoss EnemyBoss;
	Font scoreFont;
	
	public BossLifeBar(EnemyBoss iEnemyBoss) {
		scoreFont = new Font("DFKai-SB", Font.BOLD, 30); 	//字體字型及大小
		EnemyBoss = iEnemyBoss;
	}

	public void drawBossLifeBar(Graphics g) {	//血量的顯示
		Graphics2D g2 = (Graphics2D)g;
		g2.setFont(scoreFont);
		if(EnemyBoss.type>0) {
			g2.setColor(new Color(255, 255, 153));
			g2.drawString("Enemy "+(model.EnemyBoss.EnemyBoss_TYPEMAX-EnemyBoss.type)/2, 0, 30);
			g2.setStroke(new BasicStroke(5.0f));
			if(EnemyBoss.counter<0&&EnemyBoss.type%2==1) {
				if(EnemyBoss.counter<=-20) {
					g2.setColor(new Color(255, 173, 153));
					g2.draw(new Line2D.Double(125, 20, 125+150/10*(30+EnemyBoss.counter), 20));
				}
				else {
					g2.setColor(new Color(255, 173, 153));
					g2.draw(new Line2D.Double(125, 20, 125+150, 20));
					g2.setColor(new Color(242, 242, 242));
					g2.draw(new Line2D.Double(125+150, 20, 125+150+300/20*(20+EnemyBoss.counter), 20));
				}
			}
			else {
				if(EnemyBoss.type%2==1&&!EnemyBoss.is_changemode) {
					g2.setColor(new Color(255, 173, 153));
					g2.draw(new Line2D.Double(125, 20, 125+150, 20));
					g2.setColor(new Color(242, 242, 242));
					g2.draw(new Line2D.Double(125+150, 20, 125+150+300-(model.EnemyBoss.EnemyBoss_LIFE-EnemyBoss.life)*(300.0/model.EnemyBoss.EnemyBoss_LIFE), 20));
				}
				else if((EnemyBoss.type%2==0&&!EnemyBoss.is_changemode)||(EnemyBoss.type%2==1&&EnemyBoss.is_changemode)){
					g2.setColor(new Color(255, 173, 153));
					g2.draw(new Line2D.Double(125, 20, 125+150-(model.EnemyBoss.EnemyBoss_LIFE-EnemyBoss.life)*(150.0/model.EnemyBoss.EnemyBoss_LIFE), 20));
				}
			}
			g2.setStroke(new BasicStroke(1.0f));
		}
	}
	
}
