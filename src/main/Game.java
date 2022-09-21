package main;
import javax.swing.JFrame;
import java.awt.*;

public class Game{
    static JFrame jf;
	public static void main(String[] args){
		jf=new JFrame();
		jf.setTitle("Barrage");
		jf.setDefaultCloseOperation(3);
		jf.setResizable(false);
		jf.setBounds((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/2-300),100,600,800);
		GameCanvas gc = new GameCanvas();
		jf.getContentPane().add(gc);
		jf.setVisible(true);
		
		gc.init();
		gc.initThread();
	}
}