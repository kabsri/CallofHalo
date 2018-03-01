import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Coin {
	public static int numCoins;
	public double xPos, yPos;
	public static int width=20, height=20;
	public boolean collected=false;
	public static Color coinColor;
	
	/*/// Constructor method ///*/
	public Coin(int x, int y){
		xPos=x;
		yPos=y;
	}
	
	/*/// draw method ///*/
	public void draw(Graphics2D g2){
		if (!collected){
			g2.setColor(coinColor);
			g2.fill(getBounds());
		}
	}
	
	/*/// getBounds method ///*/
	public Rectangle getBounds(){
		return new Rectangle((int)Math.round(xPos+Block.width/2-Coin.width/2), (int)Math.round(yPos+Block.height/2-Coin.height/2), width, height);
	}
}
