package main;
import java.awt.*;
import java.util.Random;

import model.*;

@SuppressWarnings("serial")
public class GameCanvas extends Canvas implements Runnable{

	ObjectPool objectpool;
	Title title;
	Score score;
	KeyInput keyinput;
	Image imgBuf;
	Graphics gBuf;
	Random random;
	
	//畫面切換
	public int scene;
	static final int SCENE_TITLE = 0;
	static final int SCENE_SCORE = 1;
	static final int SCENE_INFO = 2;
	static final int SCENE_GAMEMAIN = 3;
	static final int SCENE_PAUSE = 4;
		
	public boolean gameover;
	int counter;
	
	static final int PRESSED = 1;	//短按
	static final int DOWN = 2;		//按下
	int shotkey_state;
	int bomb_state;

	GameCanvas() {
		keyinput = new KeyInput();
		addKeyListener(keyinput);
		setFocusable(true);			//獲得焦點可以操作
		random = new Random();		//隨機數
		title = new Title();
		score = new Score();
	}

	/**
	 * 遊戲初始化
 	 * 在啟動和重啟時調用
 	 * 初始化遊戲變數
	 */
	public void init() {
		objectpool = new ObjectPool();
		scene = SCENE_TITLE;//初始化後進入標題畫面
		gameover = false;	//重置遊戲狀態
		Score.initScore();	//分数初始化
		Title.initMenuLock();	//選單選項初始化
	}
	
	public void initThread() {	//初始化多線程
		Thread thread = new Thread(this);
		thread.start();
	}
	
	/**
	 * 繪圖過程，在重繪每幀時調用執行
	 * 從螢幕緩衝區複製並顯示
	 */
	public void paint(Graphics g) {
		g.drawImage(imgBuf, 0, 0, this);
	}
	
	@Override
	public void run() {
		//創建螢幕緩衝區
		imgBuf = createImage(600, 800);
		gBuf = imgBuf.getGraphics();
		
		for(counter = 0; ; counter++) {
			shotkey_state = keyinput.checkShotKey();
			bomb_state = keyinput.checkBombKey();

			gBuf.setColor(Color.black);		//背景色設定
			gBuf.fillRect(0, 0, 600, 800);

			switch (scene) {	//畫面選擇
				case SCENE_TITLE:		//進入標題畫面
				title.drawTitle(gBuf);
				Title.menuSelect(keyinput.keymenu,4);
				if (shotkey_state == DOWN) {	//按下Z鍵進入遊戲
					switch (Title.menuLock) {
						case 1:
						scene = SCENE_GAMEMAIN;
						ObjectPool.newEnemyBoss(0, 200);
				        ObjectPool.newMagicCircle(0, 200, 5, 180,60);
				        break;
						case 2:
						Score.loadScore();
						scene = SCENE_SCORE;
						break;
						case 3:
						scene = SCENE_INFO;
						break;
						case 4:
						System.exit(0);
						break;
					}
				}
				break;		//進入分數畫面
				case SCENE_SCORE:
				score.drawHiScore(gBuf);
				if (shotkey_state == DOWN) {
					scene = SCENE_TITLE;
					
				}
				break;
				case SCENE_INFO:		//進入資訊畫面
				title.drawInfo(gBuf);
				if (shotkey_state == DOWN) {
					scene = SCENE_TITLE;
				}
				break;
				case SCENE_GAMEMAIN:		//進入遊戲主畫面
				gameMain();
				if(keyinput.pause) {
					scene = SCENE_PAUSE;
				}
				break;
				case SCENE_PAUSE:		//進入暫停畫面
				title.drawPause(gBuf);
				Title.menuSelect(keyinput.keymenu,3);
				if (shotkey_state == DOWN) {	//按下Z鍵進入遊戲
					switch (Title.menuLock) {
						case 1:
						scene = SCENE_GAMEMAIN;
				        break;
						case 2:
						init();
						break;
						case 3:
						System.exit(0);
						break;
					}
				}
				break;
			}
			
			repaint();	//重繪
			
			try {
				Thread.sleep(16);	//通過延時調整遊戲速度(約一秒60幀)
			}
			catch(InterruptedException e)
			{}
		}
	}

	public void update(Graphics g) {	//提高效率不每幀清除螢幕
		paint(g);
	}
	
	void gameMain() {	//遊戲開始
		//是否開始遊戲
		if(objectpool.isGameover()||objectpool.isWin()) {
			if(objectpool.isGameover())title.drawGameover(gBuf);
			if(objectpool.isWin()) {
				score.drawScore(gBuf);
			}
			if (shotkey_state == DOWN) {
				Score.compareScore();
				//重新初始化遊戲
				init();
			}
		}
		//碰撞偵測
		objectpool.getCollision();
		objectpool.movePlayer(keyinput);
		
		//以相等間隔發射子彈
		if ((shotkey_state == PRESSED)&&(counter % 3 == 0)) {
			objectpool.shotPlayer(counter%360);
		}
		
		//偵測爆彈啟動
		if(bomb_state == DOWN) {
			objectpool.bombPlayer();
		}
		
		//遊戲物件統一繪製
		objectpool.drawAll(gBuf);
	}
}
