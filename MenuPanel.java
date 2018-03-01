import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

/*////////////
GUI

MenuPanel (BorderLayout)
	->mainPanel (GridLayout)
		->buttons2 (FlowLayout()
				->buttons (BoxLayout)
					->RadioButtons
		->Highscores (GridLayout)
			->TextArea
	->buttonPanel (FlowLayout)
		->button
////////////*/

public class MenuPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	
	static Scanner sc;
	
	JPanel mainPanel;
	
	ButtonGroup buttonGroup;
	JRadioButton[] levels = {new JRadioButton("First", false), new JRadioButton("Big", false), new JRadioButton("Falling", false),
							new JRadioButton("Swag", false), new JRadioButton("Blah", false), new JRadioButton("Yolo", false),
							new JRadioButton("Maze", false), new JRadioButton("Crunk", false),
							new JRadioButton("Marksdontmatter", false), new JRadioButton("Final", false)};
	JPanel buttons;
	JPanel buttons2;
	
	JPanel highscoresPanel;
	JTextArea highscoresArea;
	
	JPanel buttonPanel;
	JButton button;
	public static String level;
	
	/*/// Constructor method ///*/
	public MenuPanel(){
		setLayout(new BorderLayout());

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new GridLayout(1, 2));
		buttonGroup = new ButtonGroup();
		buttons = new JPanel();
		buttons.setLayout(new BoxLayout(buttons, BoxLayout.PAGE_AXIS));
		for (int i=0; i<levels.length; i++){
			buttonGroup.add(levels[i]);
			buttons.add(levels[i]);
			levels[i].addActionListener(this);
		}
		buttons2 = new JPanel();
		buttons2.setLayout(new FlowLayout());
		buttons2.add(buttons);
		mainPanel.add(buttons2);
		add(mainPanel, BorderLayout.CENTER);
		level = levels[0].getText();
		
		highscoresArea = new JTextArea();
		highscoresArea.setEditable(false);
		highscoresPanel = new JPanel();
		highscoresPanel.setLayout(new GridLayout(1, 1));
		highscoresPanel.add(highscoresArea);
		mainPanel.add(highscoresPanel);
		
		buttonPanel = new JPanel();
		button = new JButton("Play");
		button.addActionListener(this);
		button.setActionCommand("Play");
		buttonPanel.setLayout(new FlowLayout());
		buttonPanel.add(button);
		add(buttonPanel, BorderLayout.SOUTH);
	}
	
	/*/// getScore method ///*/
	public static String getScore(String level){
		String highScores = "Highscores\n\n\n";
		try{
			sc = new Scanner(new File(level+"\\"+level+"Scores.txt"));
		} catch (Exception ex){
			ex.printStackTrace();
		}
		int[] scores = new int[GamePanel.highscoreslength];
		String[] names = new String[GamePanel.highscoreslength];
		for (int i=0; i<GamePanel.highscoreslength; i++){
			scores[i] = sc.nextInt();
			names[i] = sc.nextLine();
		}
		for (int i=0; i<GamePanel.highscoreslength; i++){
			highScores += scores[i];
			highScores += names[i];
			highScores += "\n";
		}
		sc.close();
		return highScores;
	}
	
	/*/// actionPerformed method ///*/
	public void actionPerformed(ActionEvent e) {
		level = levels[0].getText();
		for (int i=0; i<levels.length; i++){
			if (levels[i].isSelected()){
				level = levels[i].getText();
			}
		}
		
		if (e.getActionCommand().equals("Play")){
			Game.frame.layout.show(Game.frame.c, "Game");
			JOptionPane.showMessageDialog(Game.gamePanel, "Collect all the coins as fast as possible");
			Game.gamePanel.newGame();
			highscoresArea.setText("");
			buttonGroup.clearSelection();
		}
		else{
			highscoresArea.setText(getScore(level));
		}
	}
}
