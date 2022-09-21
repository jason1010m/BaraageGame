package model;

import java.io.*;
import java.awt.*;

//得分的控制和顯示
public class Score {
	static int myscore;
	static int hiscore;
	Font scoreFont;
	
	public Score() {
		scoreFont = new Font("DFKai-SB", Font.BOLD, 30); 	//字體字型及大小
		myscore = 0;
	}

	public void drawScore(Graphics g) {	//得分的顯示
		g.setColor(Color.white);
		g.setFont(scoreFont);
		g.drawString("得分:"+String.format("%08d", myscore), 190, 350);
	}
	
	public void drawHiScore(Graphics g) {	//最高分顯示
		g.setColor(Color.white);
		g.setFont(scoreFont);
		g.drawString("最高分:"+String.format("%08d", hiscore), 175, 350);
	}	
	
	public static void addScore(int gain) {	//分數增加
		myscore += gain;
	}

	public static void compareScore() {	//最高分更新
		if (myscore > hiscore) {
			hiscore = myscore;
			saveScore();
		}
	}


	/**
	 * 得分保存為 data.dat
	 */
	static void saveScore() {
		try {
			FileWriter fw=new FileWriter("./hiscore/score.dat");
			BufferedWriter bw=new BufferedWriter(fw);
			bw.write(String.format("%08d", hiscore));
			bw.flush();
			fw.close();
			bw.close();
			
		}
		catch (IOException e) {}
	}

	/**
	 * 從score.dat讀取分數
	 */
	public static void loadScore() {
		try {
			File file = new File("./hiscore/score.dat");
			FileReader fr=new FileReader(file);
			BufferedReader br=new BufferedReader(fr);
			hiscore = Integer.valueOf(br.readLine());
			br.close();
			fr.close();
		}
		catch (IOException e) {}
	}


	public static void initScore() {
		myscore = 0;
	}
}
