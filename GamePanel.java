import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePanel extends JPanel implements KeyListener, ActionListener{
	private static final long serialVersionUID = 1L;
	
	public static int highscoreslength = 10;
	public static PrintWriter pr;
	public static Scanner sc;
	public static final int WIDTH = 600, HEIGHT = 400;
	public static int[][] tiles;
	public static Map map;
	public static int time;
	Player player;
	Timer tm;
	public static boolean clearScreen;
	static Color backgroundColor;
	
	 /*/// Constructor method ///*/
	public GamePanel(){
		tm = new Timer(10, this);
		newGame();
		tm.stop();
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		addKeyListener(this);
		setFocusable(true);
		clearScreen=true;
	}
	
	 /*/// update method ///*/
	public void update(){
		player.update();
		repaint();
		time+=1;
		if (Coin.numCoins==0){
			gameOver();
			newGame();
		}
	}
	
	 /*/// newGame method ///*/
	public void newGame(){
		time=0;
		map = readMap(MenuPanel.level);
		player = new Player(WIDTH/2, HEIGHT/2);
		tm.start();
	}
	
	 /*/// gameOver method ///*/
	public void gameOver(){
		tm.stop();
		JOptionPane.showMessageDialog(this, "Your time was "+time, "You win", JOptionPane.DEFAULT_OPTION);
		updateScores(MenuPanel.level);
	}
	
	/*/// updateScores method ///*/
	public void updateScores(String level){
		int[] scores = new int[highscoreslength];
		String[] names = new String[highscoreslength];
		try{
			sc = new Scanner(new File(level+"/"+level+"Scores.txt"));
		} catch (Exception e){
			e.printStackTrace();
		}
		for (int i=0; i<highscoreslength; i++){
			scores[i]=sc.nextInt();
			names[i]=sc.nextLine().trim();
		}
		if (time<scores[highscoreslength-1]){
			String name = JOptionPane.showInputDialog(this, "enter your name");
			if (name != null){
				name = name.trim();
			}
			if (name==null||name.length()==0){
				name = "Mystery Man";
			}
			names[highscoreslength-1] = name;
			scores[highscoreslength-1]=time;
			sortScores(scores, names);
		}
		sc.close();
		try{
			pr = new PrintWriter(new File(level+"/"+level+"Scores.txt"));
		} catch (Exception e){
			e.printStackTrace();
		}
		for (int i=0; i<highscoreslength; i++){
			pr.format("%-10s%s", scores[i], names[i]);
			pr.println();
		}
		pr.close();
	}
	
	/*/// sortScores method ///*/
	public void sortScores(int[] scores, String[] names){
		int min;
		String minName;
		int minIndex;
		for (int i=0; i<scores.length; i++){
			min = scores[i];
			minName = names[i];
			minIndex=i;
			for (int j=i; j<scores.length; j++){
				if (scores[j]<min){
					min = scores[j];
					minName = names[j];
					minIndex=j;
				}
			}
			scores[minIndex] = scores[i];
			scores[i] = min;
			names[minIndex] = names[i];
			names[i] = minName;
		}
	}
	
	/*/// readMap method ///*/
	public Map readMap(String level){
		try{
			sc = new Scanner(new File(level+"/"+level+".txt"));
		} catch (Exception e){
			e.printStackTrace();
		}
		backgroundColor = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());
		Player.playerColor = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());
		Block.blockColor = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());
		Coin.coinColor = new Color(sc.nextInt(), sc.nextInt(), sc.nextInt());
		int rows=sc.nextInt();
		int columns = sc.nextInt();
		tiles = new int[rows][columns];
		for (int i=0; i<tiles.length; i++){
			for (int j=0; j<tiles[i].length; j++){
				tiles[i][j] = sc.nextInt();
			}
		}
		sc.close();
		return new Map(tiles);
	}
	
	 /*/// selectLevel method ///*/
	public void selectLevel(){
		tm.stop();
		clearScreen=true;
		Game.frame.layout.show(Game.frame.c, "Menu");
	}
	
	/*/// readInstructions method ///*/
	public static String readInstructions(Scanner sc){
		String instructions = "";
		try{
			sc = new Scanner(new File("Instructions/Instructions.txt"));
		} catch (Exception e){
			e.printStackTrace();
		}
		while (sc.hasNext()){
			instructions += sc.nextLine()+ "\n";
		}
		sc.close();
		return instructions;
	}
	
	 /*/// paintComponent method ///*/
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(backgroundColor);
		g2.fill(new Rectangle(0, 0, WIDTH+10, HEIGHT+10));
		player.draw(g2);
		map.draw(g2);
		g2.setFont(new Font("Verdana", Font.BOLD, 30));
		g2.setColor(Coin.coinColor);
		g2.drawString(""+Coin.numCoins, 10, HEIGHT);
		g2.drawString(""+time, 10, 30);
		if (clearScreen){
			g2.setColor(Color.WHITE);
			g2.fill(new Rectangle(0, 0, WIDTH+10, HEIGHT+10));
			clearScreen = !clearScreen;
		}
	}
	
	/*/// actionPerformed method ///*/
	public void actionPerformed(ActionEvent e) {

		if (e.getSource()==tm){
			update();
		}
		else{
			JButton b = (JButton)e.getSource();
			if (b.getText().equals("Restart")){
				newGame();
			}
			else if (b.getText().equals("Level Select")){
				selectLevel();
			}
			else if (b.getText().equals("Help")){
				JOptionPane.showMessageDialog(this, GamePanel.readInstructions(sc), "Instructions", JOptionPane.DEFAULT_OPTION);
			}
			else if (b.getText().equals("Quit")){
				System.exit(0);
			}
		}
		
	}

	 /*/// keyPressed method ///*/
	public void keyPressed(KeyEvent e) {
		player.keyPressed(e);
	}

	 /*/// keyReleased method ///*/
	public void keyReleased(KeyEvent e) {
		player.keyReleased(e);
		
	}

	/*/// keyTyped method ///*/
	public void keyTyped(KeyEvent e) {
		//intentional empty body
	}

}
