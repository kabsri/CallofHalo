import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Container;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*////////////
GUI
Game (Frame)
	->Container (CardLayout)
		->mainPanel(BorderLayout)
			->GamePanel (see class)
			->buttonPanel (GridLayout)
				->restart
				->select level
				->help
				->quit
		->MenuPanel (see class)
////////////*/

public class Game extends JFrame{
	private static final long serialVersionUID = 1L;
	
    public static Game frame;
    
    Container c;
    
    JPanel mainPanel;
    public static MenuPanel menuPanel;
    
	public static GamePanel gamePanel;
    JPanel buttonPanel;
    
    JButton restartButton;
    JButton selectLevelButton;
    JButton helpButton;
    JButton quitButton;
    
    CardLayout layout;

    /*/// Constructor method ///*/
    public Game(String title){
		super(title);
		
	    menuPanel = new MenuPanel();
		gamePanel = new GamePanel();
	    buttonPanel = new JPanel();
	    restartButton = new JButton("Restart");
	    quitButton = new JButton("Quit");
	    helpButton = new JButton("Help");
	    selectLevelButton = new JButton("Level Select");
	    mainPanel = new JPanel();
		c = getContentPane();
		
		restartButton.addKeyListener(gamePanel);
		restartButton.addActionListener(gamePanel);
		quitButton.addKeyListener(gamePanel);
		quitButton.addActionListener(gamePanel);
		helpButton.addKeyListener(gamePanel);
		helpButton.addActionListener(gamePanel);
		selectLevelButton.addKeyListener(gamePanel);
		selectLevelButton.addActionListener(gamePanel);
		
		mainPanel.setLayout(new BorderLayout());
		mainPanel.add(gamePanel, BorderLayout.CENTER);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);
		
		buttonPanel.setLayout(new GridLayout(1, 4));
		buttonPanel.add(restartButton);
		buttonPanel.add(selectLevelButton);
		buttonPanel.add(helpButton);
		buttonPanel.add(quitButton);
		
		layout = new CardLayout();
		c.setLayout(layout);
		c.add("Game", mainPanel);
		c.add("Menu", menuPanel);

	}
    
    /*/// main method ///*/
	public static void main(String[] args) throws Exception{
		frame = new Game("Call of Halo: Combat Warfare");
		
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.layout.next(frame.c);
	}

}
