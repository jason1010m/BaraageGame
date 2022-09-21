package main;
import java.awt.event.*;


/**
*鍵盤輸入
*空格開始射擊
*方向鍵控制方向
*/
public class KeyInput extends KeyAdapter {
	//鍵盤輸入狀態
	boolean keyup;
	boolean keydown;
	boolean keyleft;
	boolean keyright;
	boolean shift;
	boolean pause;
	
	/**
	  * 按鍵類型偵測
	 * 0:沒被按
	 * 1:被按了 
	 * 2:按一下就鬆開
	 */
	int keyshot;
	int keybomb;
	int keymenu;

	KeyInput() {
		keyup = false;
		keydown = false;
		keyleft = false;
		keyright = false;
		shift = false;
		pause = false;
		keybomb = 0;
		keyshot = 0;
		keymenu = 0;
	}


	public void keyPressed(KeyEvent e) {
		int keycode = e.getKeyCode();
		if (keycode == KeyEvent.VK_LEFT) {
			keyleft = true;
		}
		if (keycode == KeyEvent.VK_RIGHT) {
			keyright = true;
		}
		if (keycode == KeyEvent.VK_UP) {
			keyup = true;
		}
		if (keycode == KeyEvent.VK_DOWN) {
			keydown = true;
		}
		if (keycode == KeyEvent.VK_SHIFT) {
			shift = true;
		}
		if (keycode == KeyEvent.VK_Z) {
			if (keyshot == 0) {
				keyshot = 2;
			}else{
				keyshot = 1;
			}
		}
		if (keycode == KeyEvent.VK_X) {
			if (keybomb == 0) {
				keybomb = 2;
			}else{
				keybomb = 1;
			}
		}

		if (keycode == KeyEvent.VK_UP) {
			if (keymenu == 0) {
				keymenu = 2;
			}else{
				keymenu = 1;
			}
		}

		if (keycode == KeyEvent.VK_DOWN) {
			if (keymenu == 0) {
				keymenu = 4;
			}else{
				keymenu = 3;
			}
		}
		
		if (keycode == KeyEvent.VK_ESCAPE) {
			pause = true;
		}
	}
	

	public void keyReleased(KeyEvent e) {	//空開按鍵時處理
		int keycode = e.getKeyCode();
		if (keycode == KeyEvent.VK_LEFT) {
			keyleft = false;
		}
		if (keycode == KeyEvent.VK_RIGHT) {
			keyright = false;
		}
		if (keycode == KeyEvent.VK_UP) {
			keyup = false;
			keymenu = 0;
		}
		if (keycode == KeyEvent.VK_DOWN) {
			keydown = false;
			keymenu = 0;
		}
		if (keycode == KeyEvent.VK_SHIFT) {
			shift = false;
		}
		if (keycode == KeyEvent.VK_Z) {
			keyshot = 0;
		}
		if (keycode == KeyEvent.VK_X) {
			keybomb = 0;
		}
		if (keycode == KeyEvent.VK_ESCAPE) {
			pause = false;
		}
	}
	
	public int getXDirection() {	//獲取x軸輸入，-1右，0無，+1左
		int ret = 0;	//靜止狀態
		if (keyright) {
			ret = 1;
		}
		if (keyleft) {
			ret = -1;
		}
		return ret;
	}
	
	public int getYDirection() {	//獲取y軸輸入，-1下，0無，+1上
		int ret = 0;	//靜止狀態
		if (keydown){
			ret = 1;
		}
		if (keyup){
			ret = -1;
		}
		return ret;
	}
	
	public int checkShotKey() {	//獲取Z鍵，0:沒被按 ，1:被按了 ，2:按一下就鬆開
		int ret = keyshot;
		if (keyshot==2) {
			keyshot = 1;
		}
		return ret;
	}
	
	public int checkBombKey() {	//獲取X鍵，0:沒被按 ，1:被按了 ，2:按一下就鬆開
		int ret = keybomb;
		if (keybomb==2) {
			keybomb = 1;
		}
		return ret;
	}
	
}
