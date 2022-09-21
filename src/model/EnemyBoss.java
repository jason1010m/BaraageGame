package model;
import java.awt.*;
import java.awt.geom.*;
import java.util.Random;

import main.*;

//敵人類
public class EnemyBoss extends GameObject {
    int counter = 0; 		//生存時間
    int life = 0; 			//生命值
    int type = 0; 			//敵人模式
    boolean able = false;	//判定點
    Player player; 			//玩家的類別方法
    boolean is_changemode = false;		//是否為模式切換中
    boolean startshoot; 	//開始標記
    
    int[] type_initxpos= {300,300,300,300,300,300,300,300,300,300,300};		//模式切換時移動至X座標
    int[] type_initypos= {200,300,200,300,200,250,200,350,200,250,200};		//模式切換時移動至Y座標
    
    Random random=new Random();		//隨機數
	double speedX;		//X座標加速度
	double speedY;		//Y座標加速度
    double nx;		//新X座標
    double ny;		//新Y座標
    
	static final int EnemyBoss_LIFE = 300;
	static final int EnemyBoss_TYPEMAX = 10;
    
    public EnemyBoss(Player iplayer) {
        player = iplayer;	//取得玩家位置
        active = false;		//避免初始化時激活
    }

    /**
     * 實體化
     * 參數 ix(double)：生成處的X座標
     * 參數 iy(double)：生成處的Y座標
     */
    public void activate(double ix, double iy) {
        x = ix;
        y = iy;
        type = 0;
        active = true;	//啟用敵方子彈
        life = EnemyBoss_LIFE;		//敵人生命值
        counter = 0;

        startshoot = false;
    }

    public void hit() {	//當與玩家子彈碰撞時
        if(!is_changemode)life--;			//減少生命值
        able = true;

        if (life < 0) {		//敵人生命歸0
    		ObjectPool.EnemyBulletBreak();	//清除本階段彈幕
    		if(type < EnemyBoss_TYPEMAX)is_changemode = true;	//開始變換模式
    		else {
    			active = false;	//敵人消失
    			Score.addScore(Bomb.getBomb()*100000);
    		}
    		Score.addScore((((EnemyBoss_LIFE*1000)-(counter*100))>0?((EnemyBoss_LIFE*1000)-(counter*100)):0)+1000);		//分數計算
    		life = EnemyBoss_LIFE;	//避免重覆計算分數
        }
    }

    public void move() {	//循環一次執行一次
        //按敵人模式行動
    	if(is_changemode) {
            is_changemode = true;
    		move_location(type_initxpos[type],type_initypos[type]);
    	}
    	else {
	        switch (type) {
	        	case 0:
	            is_changemode = true;
	            break;
	        	case 1:
			    move_FirRound1();
	            break;
	        	case 2:
		        move_FireHurricane();
	            break;
	        	case 3:
			    move_FirRound2();
	            break;
	        	case 4:
			    move_FireRegress();
	            break;
	        	case 5:
			    move_FirRound3();
	            break;
	        	case 6:
				move_WaterFountain();
		        break;
	        	case 7:
			    move_FirRound4();
	            break;
	        	case 8:
		        move_CardSpin();
	            break;
	        	case 9:
			    move_FirRound5();
	            break;
	        	case 10:
			    move_WaterRipple();
	            break;
	        	default:
	        	break;
	        }
    	}
    }
    
    void move_location(double x_pos,double y_pos) {	//敵人動作效果（移動至定點）
        if(!(x+5>x_pos&&x<x_pos+5)) {
        	x+=(x-x_pos<0)?5:-5;
        }
        if(!(y+5>y_pos&&y<y_pos+5)) {
        	y+=(y-y_pos<0)?5:-5;
        }
        if((x+5>x_pos&&x<x_pos+5)&&(y+5>y_pos&&y<y_pos+5)) {
            speedX=0;
            speedY=0;
        	counter=-30;
        	life = EnemyBoss_LIFE;
        	type++;
            is_changemode = false;
        }
        
    }
    
    void move_FirRound1() {	//敵人動作效果1（範圍內移動並發射模式1彈幕）
	    if(counter%80==0) {		//間隔一定時間移動
	        double m=80;		//移動距離
	    	double direction = random.nextInt(360);		//隨機方向移動
	    	double radian = Math.toRadians(direction);	//度數轉弧度
	    	nx=x+m*Math.cos(radian);	//新X座標點計算
	    	ny=y+m*Math.sin(radian);	//新Y座標點計算
	    	speedX = m/50 * Math.cos(radian);		//X軸加值（限制速度）
	    	speedY = m/50 * Math.sin(radian);	    //Y軸加值（限制速度）
	    }
			
		if(!((x>nx-3)&&(x<nx+3))&&(x+speedX)>50&&(x+speedX)<550)x+=speedX;		//限制敵人X行動範圍
		if(!((y>ny-3)&&(y<ny+3))&&(y+speedY)>50&&(y+speedY)<225)y+=speedY;		//限制敵人Y行動範圍
			
	    //間隔一定時間發射彈幕
	    if ((counter % 50) == 0 && counter >= 0) {
	        EnemyBullet.FireRound(x, y, 4);
	    }
	    counter++;		//生存時間計算
    }
    
    void move_FirRound2() {	//敵人動作效果1（範圍內移動並發射模式1彈幕）
	    if(counter%50==0) {		//間隔一定時間移動
	        double m=80;		//移動距離
	    	double direction = random.nextInt(360);		//隨機方向移動
	    	double radian = Math.toRadians(direction);	//度數轉弧度
	    	nx=x+m*Math.cos(radian);	//新X座標點計算
	    	ny=y+m*Math.sin(radian);	//新Y座標點計算
	    	speedX = m/35 * Math.cos(radian);		//X軸加值（限制速度）
	    	speedY = m/35 * Math.sin(radian);	    //Y軸加值（限制速度）
	    }
			
		if(!((x>nx-3)&&(x<nx+3))&&(x+speedX)>50&&(x+speedX)<550)x+=speedX;		//限制敵人X行動範圍
		if(!((y>ny-3)&&(y<ny+3))&&(y+speedY)>50&&(y+speedY)<225)y+=speedY;		//限制敵人Y行動範圍
			
	    //間隔一定時間發射彈幕
	    if ((counter % 35) == 0 && counter >= 0) {
	        EnemyBullet.FireRound(x, y, 6);
	    }
	    counter++;		//生存時間計算
    }
    
    void move_FirRound3() {	//敵人動作效果1（範圍內移動並發射模式1彈幕）
	    if(counter%60==0) {		//間隔一定時間移動
	        double m=80;		//移動距離
	    	double direction = random.nextInt(360);		//隨機方向移動
	    	double radian = Math.toRadians(direction);	//度數轉弧度
	    	nx=x+m*Math.cos(radian);	//新X座標點計算
	    	ny=y+m*Math.sin(radian);	//新Y座標點計算
	    	speedX = m/40 * Math.cos(radian);		//X軸加值（限制速度）
	    	speedY = m/40 * Math.sin(radian);	    //Y軸加值（限制速度）
	    }
			
		if(!((x>nx-3)&&(x<nx+3))&&(x+speedX)>50&&(x+speedX)<550)x+=speedX;		//限制敵人X行動範圍
		if(!((y>ny-3)&&(y<ny+3))&&(y+speedY)>50&&(y+speedY)<225)y+=speedY;		//限制敵人Y行動範圍
			
	    //間隔一定時間發射彈幕
	    if ((counter % 40) == 0 && counter >= 0) {
	        EnemyBullet.FireRound(x, y, 3);
	        EnemyBullet.FireRound(x, y, 4);
	    }
	    counter++;		//生存時間計算
    }
    
    void move_FirRound4() {	//敵人動作效果1（範圍內移動並發射模式1彈幕）
	    if(counter%60==0) {		//間隔一定時間移動
	        double m=80;		//移動距離
	    	double direction = random.nextInt(360);		//隨機方向移動
	    	double radian = Math.toRadians(direction);	//度數轉弧度
	    	nx=x+m*Math.cos(radian);	//新X座標點計算
	    	ny=y+m*Math.sin(radian);	//新Y座標點計算
	    	speedX = m/40 * Math.cos(radian);		//X軸加值（限制速度）
	    	speedY = m/40 * Math.sin(radian);	    //Y軸加值（限制速度）
	    }
			
		if(!((x>nx-3)&&(x<nx+3))&&(x+speedX)>50&&(x+speedX)<550)x+=speedX;		//限制敵人X行動範圍
		if(!((y>ny-3)&&(y<ny+3))&&(y+speedY)>50&&(y+speedY)<225)y+=speedY;		//限制敵人Y行動範圍
			
	    //間隔一定時間發射彈幕
	    if ((counter % 40) == 0 && counter >= 0) {
	        EnemyBullet.FireRound(x, y, 4);
	        EnemyBullet.FireRound(x, y, 5);
	    }
	    counter++;		//生存時間計算
    }
    
    void move_FirRound5() {	//敵人動作效果1（範圍內移動並發射模式1彈幕）
	    if(counter%80==0) {		//間隔一定時間移動
	        double m=80;		//移動距離
	    	double direction = random.nextInt(360);		//隨機方向移動
	    	double radian = Math.toRadians(direction);	//度數轉弧度
	    	nx=x+m*Math.cos(radian);	//新X座標點計算
	    	ny=y+m*Math.sin(radian);	//新Y座標點計算
	    	speedX = m/50 * Math.cos(radian);		//X軸加值（限制速度）
	    	speedY = m/50 * Math.sin(radian);	    //Y軸加值（限制速度）
	    }
			
		if(!((x>nx-3)&&(x<nx+3))&&(x+speedX)>50&&(x+speedX)<550)x+=speedX;		//限制敵人X行動範圍
		if(!((y>ny-3)&&(y<ny+3))&&(y+speedY)>50&&(y+speedY)<225)y+=speedY;		//限制敵人Y行動範圍
			
	    //間隔一定時間發射彈幕
	    if ((counter % 50) == 0 && counter >= 0) {
	        EnemyBullet.FireRound(x, y, 3);
	        EnemyBullet.FireRound(x, y, 4);
	        EnemyBullet.FireRound(x, y, 5);
	    }
	    counter++;		//生存時間計算
    }

    void move_FireHurricane() { //敵機動作效果2（範圍內移動並發射模式2彈幕）
    	if(counter%150==0) {	//間隔一定時間移動
    	    double m=55;		//移動距離
        	double direction = random.nextInt(360);		//隨機方向移動
        	double radian = Math.toRadians(direction);	//度数转弧度
        	nx=x+m*Math.cos(radian);	//新X座標點計算
        	ny=y+m*Math.sin(radian);	//新Y座標點計算
    		speedX = m/100 * Math.cos(radian);		//X軸加值（限制速度）
    		speedY = m/100 * Math.sin(radian);		//Y軸加值（限制速度） 
        }
		
		if(!((x>nx-3)&&(x<nx+3))&&(x+speedX)>200&&(x+speedX)<400)x+=speedX;		//限制敵人X行動範圍
		if(!((y>ny-3)&&(y<ny+3))&&(y+speedY)>250&&(y+speedY)<350)y+=speedY;		//限制敵人Y行動範圍
		
	    //間隔一定時間發射彈幕
    	if(counter%2==0 && counter >= 0) {
    		EnemyBullet.FireHurricane(x, y, (counter/2)%360 , (((counter/2)/6)%2==0)?45:-45);
    	}
    	counter++;		//生存時間計算
    }
    
    void move_WaterFountain() { //敵機動作效果2（範圍內移動並發射模式3彈幕）
    	if(counter%120==105&&counter!=0) {	//間隔一定時間移動
		    double m=30;		//移動距離
	    	double direction = random.nextInt(360);		//隨機方向移動
	    	double radian = Math.toRadians(direction);	//度数转弧度
	    	nx=x+m*Math.cos(radian);	//新X座標點計算
	    	ny=y+m*Math.sin(radian);	//新Y座標點計算
			speedX = m/5 * Math.cos(radian);		//X軸加值（限制速度）
			speedY = m/5 * Math.sin(radian);		//Y軸加值（限制速度） 
    	}
	
    	if(!((x>nx-3)&&(x<nx+3))&&(x+speedX)>250&&(x+speedX)<350)x+=speedX;		//限制敵人X行動範圍
		if(!((y>ny-3)&&(y<ny+3))&&(y+speedY)>200&&(y+speedY)<300)y+=speedY;		//限制敵人Y行動範圍*
    	
    	if(counter % 1 == 0 && counter / 60 % 2 == 0 && counter >= 0) {
    		double posAdd=3*Math.sin(counter%360);
    		EnemyBullet.WaterFountain(x, y, (counter*18+(counter/20*2))%360);
			ObjectPool.newParticleHalo(x+posAdd, y+posAdd, 5, 40);
    	}
    	counter++;		//生存時間計算
    }
    
    void move_WaterRipple() { //敵機動作效果2（範圍內移動並發射模式4彈幕）
    	
	    //間隔一定時間發射彈幕
    	if(counter%(120-(50-life/(EnemyBoss_LIFE/50)))==0 && counter >= 0) {
    		EnemyBullet.WaterRipple(random.nextInt(401)+100, random.nextInt(251)+50);
    	}
    	counter++;		//生存時間計算
    }
    
    void move_CardSpin() { //敵機動作效果2（範圍內移動並發射模式5彈幕）
		
	    //間隔一定時間發射彈幕
	    if ((counter % 60) == 0 && counter >= 0) {
	        if(life<EnemyBoss_LIFE/2) {
	        	EnemyBullet.FireRoundBig(x, y, random.nextInt(21)-10, ((counter/60)%2)==0?true:false);
	        }
	        EnemyBullet.CardSpin(x, y, ((counter/60)%2)==0?true:false);
	    }
    	counter++;		//生存時間計算
    }
    
    void move_FireRegress() { //敵機動作效果2（範圍內移動並發射模式6彈幕）
		
	    //間隔一定時間發射彈幕
	    if ((counter % 20) == 0 && counter >= 0) {
	        EnemyBullet.FireRegress(x, y, true);
	        EnemyBullet.FireRegress(x, y, false);
	        EnemyBullet.FireAim(x, y, player);
	    }
    	counter++;		//生存時間計算
    }
    
    public Area getBounds() {
    	return new Area(new Rectangle2D.Double(x - 16, y - 16, 32, 32));
    }
    
    public void draw(Graphics g) { //循環一次繪製一次
		Graphics2D g2 = (Graphics2D)g;
        if (able) {
            g2.setColor(Color.orange);	//如果被玩家擊中的話，將顏色設置為橙色
        } 
        else {
            g2.setColor(Color.white);	//平常為白色
        }

        able = false;
        //繪製正方形
        g2.draw(new Rectangle2D.Double(x - 16, y - 16, 32, 32));
    }
}
