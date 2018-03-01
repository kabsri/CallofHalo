import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Player {
	public double xPos, yPos;
	public double xVel=0, yVel=0;
	public static int width=20, height=20;
	public boolean right=false, left=false, inAir=false, wallSliding=false;;
	public static double moveSpeed=4;
	public static int maxJumps = 3;
	public int jumps = maxJumps-1;
	public static double terminalVel=10, gravity = 0.11, jumpSpeed = -4.2;
	public double playerToFloor=0, playerToWall=0, playerToTop=0;
	public static Color playerColor;
	public boolean showCoins;
	
	/*/// Constructor method ///*/
	public Player(int x, int y){
		xPos = x;
		yPos = y;
		showCoins=false;
		inAir=true;
	}
	
	/*/// update method ///*/
	public void update(){
		/*/// Update x position ///*/
		xVel=0;
		if (right){
			xVel = 1*moveSpeed;
			wallSliding=false;
			if (rightCollision(GamePanel.map)){
				xVel=playerToWall;
				if (inAir){
					wallSliding=true;
				}
				else{
					wallSliding=false;
				}
			}
			else{
				wallSliding=false;
			}
		}
		else if (left){
			xVel = -1*moveSpeed;
			wallSliding=false;
			if (leftCollision(GamePanel.map)){
				xVel=-1*playerToWall;
				if (inAir){
					wallSliding=true;
				}
				else{
					wallSliding=false;
				}
			}
			else{
				wallSliding=false;
			}
		}
		else{
			wallSliding = false;
		}
		playerToWall=0;
		
		if (((GamePanel.map.blocks[0].xPos-xVel<=-1*(GamePanel.map.rightEnd-GamePanel.WIDTH))
			|| GamePanel.map.blocks[0].xPos-xVel>=0)){
			xPos+=xVel;
		}
		else if(Math.abs(xPos-GamePanel.WIDTH/2)>3.1){
			xPos+=xVel;
		}
		else{
			GamePanel.map.moveX(-1*xVel);
		}
		
		/*/// Update y position ///*/
		if (yVel<terminalVel){
			yVel+=gravity;
		}
		if (bottomCollision(GamePanel.map)){
			jumps = maxJumps;
			yVel=playerToFloor;
			inAir = false;
		}
		else{
			if (!inAir){
				jumps--;
				inAir = true;
			}
		}
		if (topCollision(GamePanel.map)){
			yVel=-1*playerToTop+1;
		}
		
		if ((GamePanel.map.blocks[0].yPos-yVel<=-1*(GamePanel.map.bottomEnd-GamePanel.HEIGHT) 
			|| GamePanel.map.blocks[0].yPos-yVel>=0)){
			yPos+=yVel;
		}
		else if (Math.abs(yPos-GamePanel.HEIGHT/2.0)>5.3){
			yPos+=yVel;
		}
		else{
			GamePanel.map.moveY(-1*yVel);
		}
		playerToFloor=0;
		
		/*/// collect coins ///*/
		collectCoins(GamePanel.map);

	}
	
	/*/// rightCollision method ///*/
	public boolean rightCollision(Map map){
		if (xVel<0){
			return false;
		}
		else{
			for (int i=0; i<map.blocks.length; i++){
				if (map.blocks[i].getBounds().contains(new Point((int)Math.round(xPos+width+xVel),(int)Math.round(yPos)))||
					(map.blocks[i].getBounds().contains(new Point((int)Math.round(xPos+width+xVel),(int)Math.round(yPos+height-1))))){
					playerToWall=map.blocks[i].xPos-(xPos+width)-1;
					return true;
				}
			}
			return false;
		}
		
	}
	
	/*/// leftCollision method ///*/
	public boolean leftCollision(Map map){
		if (xVel>0){
			return false;
		}
		else{
			for (int i=0; i<map.blocks.length; i++){
				if (map.blocks[i].getBounds().contains(new Point((int)Math.round(xPos+xVel),(int)Math.round(yPos)))||
					(map.blocks[i].getBounds().contains(new Point((int)Math.round(xPos+xVel),(int)Math.round(yPos+height-1))))){
					playerToWall=xPos-(map.blocks[i].xPos+Block.width);
					return true;
				}
			}
			return false;
		}
	}
	
	/*/// bottomCollision method ///*/
	public boolean bottomCollision(Map map){
		if (yVel<0){
			return false;
		}
		else{
			for (int i=0; i<map.blocks.length; i++){
				if (map.blocks[i].getBounds().contains(new Point((int)Math.round(xPos),(int)Math.round(yPos+height+yVel)))||
					(map.blocks[i].getBounds().contains(new Point((int)Math.round(xPos+width),(int)Math.round(yPos+height+yVel))))){
					playerToFloor=map.blocks[i].yPos-(yPos+height);
					return true;
				}
			}
			return false;
		}
	}
	
	/*/// topCollision method ///*/
	public boolean topCollision(Map map){
		if (yVel>0){
			return false;
		}
		else{
			for (int i=0; i<map.blocks.length; i++){
				if (map.blocks[i].getBounds().contains(new Point((int)Math.round(xPos),(int)Math.round(yPos+yVel)))||
					(map.blocks[i].getBounds().contains(new Point((int)Math.round(xPos+width),(int)Math.round(yPos+yVel))))){
					playerToTop=yPos-(map.blocks[i].yPos+Block.height);
					return true;
				}
			}
			return false;
		}
	}
	
	/*/// collectCoins method ///*/
	public void collectCoins(Map map){
		for (int i=0; i<map.coins.length; i++){
			if (getBounds().intersects(map.coins[i].getBounds())){
				if (!map.coins[i].collected){
					map.coins[i].collected=true;
					Coin.numCoins--;
				}
			}
		}
	}
	
	/*/// draw method ///*/
	public void draw(Graphics2D g2){
		g2.setColor(playerColor);
		g2.fill(getBounds());
		if (showCoins){
			for (int i=0; i<GamePanel.map.coins.length; i++){
				if (!GamePanel.map.coins[i].collected){
					g2.drawLine((int)Math.round(xPos+width/2), (int)Math.round(yPos+height/2),
					(int)Math.round(GamePanel.map.coins[i].xPos+Block.width/2), (int)Math.round(GamePanel.map.coins[i].yPos+Block.height/2));
				}
			}
		}
	}
	
	/*/// getBounds method ///*/
	public Rectangle getBounds(){
		return new Rectangle((int)Math.round(xPos+xVel), (int)Math.round(yPos+yVel), width, height);
	}
	
	/*/// keyTyped method ///*/
	public void keyPressed(KeyEvent e){
		if (e.getKeyCode()==KeyEvent.VK_RIGHT||e.getKeyCode()==KeyEvent.VK_D){
			right = true;
		}
		else if (e.getKeyCode()==KeyEvent.VK_LEFT||e.getKeyCode()==KeyEvent.VK_A){
			left = true;
		}
		
		if (e.getKeyCode()==KeyEvent.VK_UP||e.getKeyCode()==KeyEvent.VK_W){
			if (wallSliding){
				yVel=jumpSpeed;
			}
			else if (jumps>0){
				jumps--;
				yVel=jumpSpeed;
				inAir = true;
			}
		}
		if (e.getKeyCode()==KeyEvent.VK_DOWN||e.getKeyCode()==KeyEvent.VK_S){
			if (!showCoins){
				showCoins = true;
				GamePanel.time += 10000;
			}
		}
	}
	
	/*/// keyReleased method ///*/
	public void keyReleased(KeyEvent e){
		if (e.getKeyCode()==KeyEvent.VK_RIGHT||e.getKeyCode()==KeyEvent.VK_D){
			right = false;
		}
		else if (e.getKeyCode()==KeyEvent.VK_LEFT||e.getKeyCode()==KeyEvent.VK_A){
			left = false;
		}
	}

}
