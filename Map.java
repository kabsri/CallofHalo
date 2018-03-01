import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Map {
	final static int EMPTYSPACE=0, BLOCK=1, COIN=2;
	static Color mapColor;
	int[][] tiles;
	Block[] blocks;
	ArrayList<Block> blocksList = new ArrayList<Block>();
	Coin[] coins;
	ArrayList<Coin> coinList = new ArrayList<Coin>();
	public double rightEnd, bottomEnd;
	
	/*/// Constructor method ///*/
	public Map(int[][] tiles){
		this.tiles = new int[tiles.length][tiles[0].length];
		for (int i=0; i<tiles.length; i++){
			for (int j=0; j<tiles[i].length; j++){
				this.tiles[i][j] = tiles[i][j];
				if (tiles[i][j]==BLOCK){
					blocksList.add(new Block(j*Block.height, i*Block.width));
				}
				else if (tiles[i][j]==COIN){
					coinList.add(new Coin(j*Block.height, i*Block.width));
				}
			}
		}
		
		blocks = new Block[blocksList.size()];
		for (int i=0; i<blocks.length; i++){
			blocks[i] = blocksList.get(i);
		}
		rightEnd = blocks[blocks.length-1].xPos+Block.width-10;
		bottomEnd = blocks[blocks.length-1].yPos+Block.height-10;
		
		coins = new Coin[coinList.size()];
		Coin.numCoins=0;
		for (int i=0; i<coins.length; i++){
			coins[i] = coinList.get(i);
			Coin.numCoins++;
		}
	}
	
	/*/// draw method ///*/
	public void draw(Graphics2D g2){
		for (int i=0; i<blocks.length; i++){
			blocks[i].draw(g2);
		}
		for (int i=0; i<coins.length; i++){
			coins[i].draw(g2);
		}
	}
	
	/*/// moveX method ///*/
	public void moveX(double xVel){
		for (int i=0; i<blocks.length; i++){
			blocks[i].xPos+=xVel;
		}
		for (int i=0; i<coins.length; i++){
			coins[i].xPos+=xVel;
		}
	}
	
	/*/// moveY method ///*/
	public void moveY(double yVel){
		for (int i=0; i<blocks.length; i++){
			blocks[i].yPos+=yVel;
		}
		for (int i=0; i<coins.length; i++){
			coins[i].yPos+=yVel;
		}
	}
}
