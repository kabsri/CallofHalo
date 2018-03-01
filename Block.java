import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Block{
	
	public double xPos, yPos;
	static int width=50, height=50;
	public static Color blockColor;
	
	/*/// Constructor method ///*/
	public Block(int x, int y){
		xPos = x;
		yPos = y;
	}
	
	/*/// draw method ///*/
	public void draw(Graphics2D g2){
		g2.setColor(blockColor);
		g2.fill(getBounds());
	}
	
	/*/// getBounds method ///*/
	public Rectangle getBounds(){
		return new Rectangle((int)Math.round(xPos), (int)Math.round(yPos), width, height);
	}

}
