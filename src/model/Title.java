package model;
import java.awt.*;

//標題和遊戲結束畫面繪製
public class Title {
	int count;	//幀數
	public static int menuLock;
	static boolean selectlock;
	Font titleFont;
	Font menuFont;
	Font infoFont;
	

	public Title() {	//為標題實體化字型
		count = 0;
		menuLock = 1;
		selectlock = false;
		titleFont = new Font("DFKai-SB", Font.BOLD, 60);	//黑體
		menuFont = new Font("Comic Sans MS", Font.BOLD, 30);
		infoFont = new Font("PMingLiU", Font.BOLD, 18);
	}
	

	public void drawTitle(Graphics g) {	//遊戲開始介面
		g.setColor(Color.white);
		count++;
		g.setFont(titleFont);
		g.drawString("期",150,125);
		g.drawString("末",150,200);
		g.drawString("專",150,275);
		g.drawString("題",150,350);
		g.drawString("彈",150,425);
		g.drawString("幕",150,500);
		g.drawString("遊",150,575);
		g.drawString("戲",150,650);
		g.setFont(menuFont);
		if(menuLock == 1)g.setColor(Color.red);
		else g.setColor(Color.white);
		g.drawString("Start",350,232);
		if(menuLock == 2)g.setColor(Color.red);
		else g.setColor(Color.white);
		g.drawString("Score",350,332);
		if(menuLock == 3)g.setColor(Color.red);
		else g.setColor(Color.white);
		g.drawString("Info",350,432);
		if(menuLock == 4)g.setColor(Color.red);
		else g.setColor(Color.white);
		g.drawString("Quit",350,532);
	}

	public void drawInfo(Graphics g) {	//遊戲開始介面
		g.setColor(Color.white);
		count++;
		g.setFont(infoFont);
		g.drawString("JAVA期末專題-彈幕遊戲",200,75);
		g.drawString("製作者：楊勝峰",235,150);
		g.drawString("班級：資工三甲",235,225);
		g.drawString("學號：1106405010",225,300);

		g.drawString("遊戲操作；上下左右鍵操作玩家機體，被敵機子彈擊中即死亡。", 40, 400);
		g.drawString("　　　　　Ｓｈｉｆｔ慢速移動並集中射擊。", 40, 475);
		g.drawString("　　　　　Ｚ鍵發射子彈及選單操作、返回。", 40, 550);
		g.drawString("　　　　　Ｘ鍵使用爆彈，每輪遊戲可使用三次爆彈。", 40, 625);
		g.drawString("　　　　　遊戲中按下ＥＳＣ鍵可呼叫暫停選單。", 40, 700);
	}
	
	public void drawPause(Graphics g) {	//遊戲開始介面
		g.setColor(Color.white);
		count++;
		g.setFont(menuFont);
		if(menuLock == 1)g.setColor(Color.red);
		else g.setColor(Color.white);
		g.drawString("Resume",230,325);
		if(menuLock == 2)g.setColor(Color.red);
		else g.setColor(Color.white);
		g.drawString("Menu",245,400);
		if(menuLock == 3)g.setColor(Color.red);
		else g.setColor(Color.white);
		g.drawString("Quit",245,475);
	}
	
	public void drawGameover(Graphics g) {	//遊戲結束介面
		g.setColor(Color.white);
		count++;
		g.setFont(titleFont);
		g.drawString("GAMEOVER",175,400);
	}
	
	public static void menuSelect(int keymenu,int max) {
		if(keymenu == 2 && selectlock == false) {
			menuLock--;
			if(menuLock <= 0)menuLock=4;
			selectlock = true;
		}
		else if(keymenu == 4 && selectlock == false){
			menuLock++;
			if(menuLock > max)menuLock=1;
			selectlock = true;
		}
		else if(keymenu == 0)selectlock = false;
	}
	
	public static void initMenuLock() {
		menuLock = 1;
	}
	
}

