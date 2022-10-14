package main;

import java.awt.*;
import java.awt.geom.*;

import model.*;

public class ObjectPool {
	boolean GodMode = true;	//無敵模式（測試用）
	
	static EnemyBoss[] EnemyBoss;	//提前生成保存敵人列表
	static EnemyBullet[] EnemyBullet;	//提前生成保存敵彈列表
	static MagicCircle[] MagicCircle;	//提前生成保存魔法陣特效列表
	static ParticleHalo[] ParticleHalo;	//提前生成保存特效列表
	static PlayerChild[] PlayerChild;	//提前生成保存玩家子機列表
	static PlayerBullet[] PlayerBullet;	//提前生成保存玩家子彈列表
	static Bomb[] bomb;		//提前生成保存爆彈列表
	Player player;	//提前生成保存玩家
	BossLifeBar bosslifebar;	//提前生成保存敵人血條

	static final int EnemyBullet_MAX = 3000;
	static final int ENEMYBOSS_MAX = 1;
	static final int MagicCircle_MAX = 1;
	static final int ParticleHalo_MAX = 60;
	static final int PlayerChild_MAX = 2;
	static final int PlayerBullet_MAX = 60;
	static final int Bomb_MAX = 1;
	
	ObjectPool() {
		//初始化玩家
		player = new Player(300, 600, 5);	//位置及速度
		player.active = true;

		//數量限制
		EnemyBoss = new EnemyBoss[ENEMYBOSS_MAX];
		for(int i = 0; i < EnemyBoss.length; i++) {
				EnemyBoss[i] = new EnemyBoss(player);
				bosslifebar = new BossLifeBar(EnemyBoss[i]);
		}

		//數量限制
		EnemyBullet = new EnemyBullet[EnemyBullet_MAX];
		for(int i = 0; i < EnemyBullet.length; i++) {
				EnemyBullet[i] = new EnemyBullet();
		}
		
		//數量限制
		MagicCircle = new MagicCircle[MagicCircle_MAX];
		for(int i = 0; i < MagicCircle.length; i++) {
			MagicCircle[i] = new MagicCircle(EnemyBoss);
		}
		
		//數量限制
		ParticleHalo = new ParticleHalo[ParticleHalo_MAX];
		for(int i = 0; i < ParticleHalo.length; i++) {
			ParticleHalo[i] = new ParticleHalo();
		}
		
		//數量限制
		PlayerBullet = new PlayerBullet[PlayerBullet_MAX];
		for(int i = 0; i < PlayerBullet.length; i++) {
				PlayerBullet[i] = new PlayerBullet();
		}
		
		//數量限制
		PlayerChild = new PlayerChild[PlayerChild_MAX];
		for(int i = 0; i < PlayerChild.length; i++) {
			PlayerChild[i] = new PlayerChild(player,i);
			PlayerChild[i].active = true;
		}

		//數量限制
		bomb = new Bomb[Bomb_MAX];
		for(int i = 0; i < bomb.length; i++) {
				bomb[i] = new Bomb();
		}
	}
	
	public void drawAll(Graphics g) {	//繪製所有遊戲物件
        doGameObjects(g, MagicCircle);
        doGameObjects(g, EnemyBoss);
        doGameObjects(g, EnemyBullet);
        doGameObjects(g, PlayerBullet);
        doGameObjects(g, ParticleHalo);
        doGameObjects(g, bomb);
        doGameObjects(g, PlayerChild);
        if(!isWin())bosslifebar.drawBossLifeBar(g);
		player.draw(g);
	}
	
	public void doGameObjects(Graphics g, GameObject[] objary) {	//按照排列規則
        for (int i = 0; i < objary.length; i++) {			
            if (objary[i].active == true) {
                objary[i].move();
                objary[i].draw(g);
            }
        }
    }
	
    /**
     * 敵人的生成、初始化
     * 參數 ix(double)：生成處x座標
     * 參數 iy(double)：生成處y座標
     * 返回 子彈ID(int)：(如果沒有空缺：-1)
     */
	public static int newEnemyBoss(double ix, double iy) {
		for (int i = 0; i < ENEMYBOSS_MAX; i++) {
			if ((EnemyBoss[i].active) == false) {
				EnemyBoss[i].activate(ix, iy);
				return i;
			}
		}
		return -1;
	}
	
    /**
     * 敵彈初始化
     * 參數 ix(double)：生成處x座標
     * 參數 iy(double)：生成處y座標
     * 參數 idirection(double)：移動的方向
     * 參數 ispeed(double)：移動的速度
     * 參數 itype(int)：子彈型態
     * 返回 子弹ID(int)：(如果没有空缺：-1)
     */
	public static int newEnemyBullet(double ix, double iy, double idirection, double ispeed, int ibtype) {
		for (int i = 0; i < EnemyBullet_MAX; i++) {
			if ((EnemyBullet[i].active) == false) {
				EnemyBullet[i].activate(ix, iy, idirection, ispeed, ibtype);
				return i;
			}
		}
		return -1;
	}
	
    /**
     * 敵彈初始化
     * 參數 ix(double)：生成處x座標
     * 參數 iy(double)：生成處y座標
     * 參數 idirection(double)：移動的方向
     * 參數 ispeed(double)：移動的速度
     * 參數 itype(int)：子彈型態
     * 返回 子弹ID(int)：(如果没有空缺：-1)
     */
	public static int newEnemyBullet(double ix, double iy, double idirection, double ispeed, int ibtype, Color icolor) {
		for (int i = 0; i < EnemyBullet_MAX; i++) {
			if ((EnemyBullet[i].active) == false) {
				EnemyBullet[i].activate(ix, iy, idirection, ispeed, ibtype, icolor);
				return i;
			}
		}
		return -1;
	}
	
    /**
     * 敵彈初始化
     * 參數 ix(double)：生成處x座標
     * 參數 iy(double)：生成處y座標
     * 參數 idirection(double)：移動的方向
     * 參數 ispeed(double)：移動的速度
     * 參數 iloiter(int)：移動多少禎換動作
     * 參數 turn(double)：變化時角度變化量
     * 參數 itype(int)：子彈型態
     * 返回 子弹ID(int)：(如果没有空缺：-1)
     */
	public static int newEnemyBullet(double ix, double iy, double idirection, double ispeed, int iloiter, double iturn, boolean irebound, int ibtype) {
		for (int i = 0; i < EnemyBullet_MAX; i++) {
			if ((EnemyBullet[i].active) == false) {
				EnemyBullet[i].activate(ix, iy, idirection, ispeed, iloiter, iturn, irebound, ibtype);
				return i;
			}
		}
		return -1;
	}
	
    /**
     * 敵彈初始化
     * 參數 ix(double)：生成處x座標
     * 參數 iy(double)：生成處y座標
     * 參數 idirection(double)：移動的方向
     * 參數 ispeed(double)：移動的速度
     * 參數 itype(int)：子彈型態
     * 返回 子弹ID(int)：(如果没有空缺：-1)
     */
	public static int newEnemyBullet(double ix, double iy, double idirection, double ispeed, boolean iright, int ibtype) {
		for (int i = 0; i < EnemyBullet_MAX; i++) {
			if ((EnemyBullet[i].active) == false) {
				EnemyBullet[i].activate(ix, iy, idirection, ispeed, iright, ibtype);
				return i;
			}
		}
		return -1;
	}
	
    /**
     * 全螢幕子彈消除
     */
	public static void EnemyBulletBreak() {
		for (int i = 0; i < EnemyBullet_MAX; i++) {
			if ((EnemyBullet[i].active) == true) {
				EnemyBullet[i].active = false;
			}
		}
	}
	
	public static int newMagicCircle(double ix, double iy, double ispeed, int isize, int isizeVariable) {
		for (int i = 0; i < MagicCircle_MAX; i++) {
				if ((MagicCircle[i].active) == false) {
					MagicCircle[i].activate(ix, iy, ispeed, isize, isizeVariable);
					return i;
				}		
		}
			return -1;
	}
	
	public static int newParticleHalo(double ix, double iy, int idelay, int isize) {
		for (int i = 0; i < ParticleHalo_MAX; i++) {
				if ((ParticleHalo[i].active) == false) {
					ParticleHalo[i].activate(ix, iy, idelay, isize);
					return i;
				}		
		}
			return -1;
	}
	
    /**
     * 玩家彈的生成、初始化
     * 參數 ix(double)：生成處x座標
     * 參數 iy(double)：生成處y座標
	 * 返回 玩家弹ID：(如果没有空缺：-1)
     */
	public static int newPlayerBullets(double ix, double iy, double idirection) {
		for (int i = 0; i < PlayerBullet_MAX; i++) {
				if ((PlayerBullet[i].active) == false) {
					PlayerBullet[i].activate(ix, iy, idirection);
					return i;
				}		
		}
			return -1;
	}
	
    /**
     * 玩家彈的生成、初始化
     * 參數 ix(double)：生成處x座標
     * 參數 iy(double)：生成處y座標
	 * 返回 玩家弹ID：(如果没有空缺：-1)
     */
	public static int newBomb(Player iplayer) {
		for (int i = 0; i < Bomb_MAX; i++) {
				if ((bomb[i].active) == false) {
					bomb[i].activate(iplayer);
					return i;
				}
		}
			return -1;
	}
	
	public void shotPlayer(double direction) {	//玩家發射子彈
		//只在可視時發射
		if (player.active){
			ObjectPool.newPlayerBullets(PlayerChild[0].x, PlayerChild[0].y, 270);
			ObjectPool.newPlayerBullets(player.x-10, player.y, 270);
			ObjectPool.newPlayerBullets(player.x+10, player.y, 270);
			ObjectPool.newPlayerBullets(PlayerChild[1].x, PlayerChild[1].y, 270);
		}
	}
	
	public void bombPlayer() {	//玩家使用爆彈
		//只在可視且爆彈有餘量時發射
		if (player.active&&Bomb.getBomb()>0 || GodMode == true){
			if(ObjectPool.newBomb(player) != -1)Bomb.lessBomb();
		}
	}
	
	public void movePlayer(KeyInput keyinput) {	//玩家移動控制
		if (player.active) {
			player.move(keyinput.getXDirection(), keyinput.getYDirection(),keyinput.shift);
		}
	}
	
	/**
	 * 兩點之間距離測量
	 * 參數 ga(GameObject)：對象A
	 * 參數 gb(GameObject)：對象B
	 * 返回：距離
	 */
	public double getDistance(GameObject ga, GameObject gb) {
		//畢式定理
		double Xdiff = Math.abs(ga.x - gb.x);
		double Ydiff = Math.abs(ga.y - gb.y);
		return Math.sqrt(Math.pow(Xdiff,2) + Math.pow(Ydiff,2));
	}
	
	public boolean is_Collision(Area a1, Area a2) {		//偵測兩個區域是否交集
		a1.intersect(a2);		//將a1改為與a2的交集區域
		return !a1.isEmpty();		//若交集區域不為空則回傳True
	}

	public void getCollision() {	//碰撞偵測
        //敵人子彈與玩家的碰撞
        for (int i = 0; i < EnemyBullet.length && GodMode == false; i++) {
			if ((EnemyBullet[i].active)&&(player.active)&&getDistance(EnemyBullet[i],player)<48) {	//中彈判斷
				if (is_Collision(player.getBounds(), EnemyBullet[i].getBounds())) {
					player.active = false;	//玩家消失
					PlayerChild[0].active = false;
					PlayerChild[1].active = false;
					EnemyBullet[i].active = false;	//子彈消失
				}
			}
        }

        //敵人子彈與炸彈防護圈的碰撞
        for (int i = 0; i < bomb.length ; i++) {
        	if (bomb[i].active == true) {
        		for (int j = 0; j <EnemyBullet.length ; j++) {
        			if (EnemyBullet[j].active&&getDistance(EnemyBullet[j],bomb[i])<(bomb[i].getSize()/2+8)&&getDistance(EnemyBullet[j],bomb[i])>(bomb[i].getSize()/2-8)) {	//子彈碰觸防護圈
        				EnemyBullet[j].active = false;	//子彈消失
        			}
        		}
        	}
        }
        
		//玩家子彈和敵人的碰撞偵測
        for (int i = 0; i < EnemyBoss.length; i++) {
			if (EnemyBoss[i].active == true){
				for (int j = 0; j < PlayerBullet.length; j++) {
					if (PlayerBullet[j].active == true&&getDistance(PlayerBullet[j],EnemyBoss[i])<48){
						//中彈判斷
						if (is_Collision(PlayerBullet[j].getBounds(), EnemyBoss[i].getBounds())) {
							//敵軍生命減少
							EnemyBoss[i].hit();
							//子彈消失
							PlayerBullet[j].active = false;
						}
					}
				}
			}
        }

		//敵人和玩家的碰撞偵測
        for (int i = 0; i < EnemyBoss.length && GodMode == false; i++) {
			if ((EnemyBoss[i].active)&&(player.active)&&getDistance(EnemyBoss[i],player)<48) {
				//碰撞判斷
				if (is_Collision(player.getBounds(), EnemyBoss[i].getBounds())) {
					//玩家消失
					player.active = false;
					PlayerChild[0].active = false;
					PlayerChild[1].active = false;
				}
			}
        }
	}
	
	/**
	 * 返回遊戲結果
	 */
	public boolean isGameover() {
		return !player.active;
	}
	
	/**
	 * 返回遊戲結果
	 */
	public boolean isWin() {
		return !EnemyBoss[0].active;
	}
}
