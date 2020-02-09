import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

public class Player {
	
	private JFrame frame;
	private JTextField answerField;
	private BackEnd backend;
	private int questionIndex = 0;
	private ArrayList<String> questions; 
	private ArrayList<String> answers;
	private JLabel question;
	private JLabel scoreLabel;
	private int score = 0;

	public static void main(String[] args) {
		new Player().start();
	}

	
	public void start() {
		backend = new BackEnd();
		
		JMenuBar menubar = new JMenuBar();
		menubar.setBackground(Color.DARK_GRAY);
		JMenu fileMenu = new JMenu("Import");
		JMenuItem menuItem = new JMenuItem("Import Questions");
		menuItem.addActionListener(new FilePickerClicked());
		fileMenu.add(menuItem);
		menubar.add(fileMenu);
		JPanel panel = new JPanel();
		///
		question = new JLabel("Import Deck");
		question.setHorizontalAlignment(SwingConstants.CENTER);
		question.setFont(new Font("sanserif", Font.BOLD, 30));
		///
		scoreLabel = new JLabel("Score: " + score);
		scoreLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		scoreLabel.setFont(new Font("Mistral", Font.BOLD, 15));
		///
		answerField = new JTextField();
		answerField.setFont(new Font("sanserif", Font.PLAIN, 24));
		
		JButton button = new JButton("Next Question >");
		button.addActionListener(new nextQuestionClicked());
		button.setBackground(Color.BLUE);
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.setForeground(Color.white);
		button.setFont(new Font("Osaka", Font.PLAIN, 25));
		////
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.add(question);
		panel.add(scoreLabel);
		panel.add(answerField);
		panel.add(button);
		
		frame = new JFrame("Quiz Builder");
		frame.setJMenuBar(menubar);
		frame.getContentPane().add(panel);
		frame.setSize(900, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private class FilePickerClicked implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			JFileChooser filePicker = new JFileChooser();
			filePicker.showSaveDialog(frame);
			readFile(filePicker.getSelectedFile());
		}
	}
	
	private class nextQuestionClicked implements ActionListener{
		public void actionPerformed(ActionEvent ev) {
			try {
				if(questionIndex < questions.size()) {
					if(questionIndex > 0) {
						checkAnswer();
					}
					answerField.setText("");
					question.setText(questions.get(questionIndex));
					questionIndex++;
				}else {
					System.out.println("Deck Complete");
					questionIndex = 0;
				}
			}catch(Exception ex) {
				question.setText("Invalid Deck!");
				ex.printStackTrace();
			}
			
		}
	}
	
	
	private void readFile(File file) {
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine()) != null) {
				makeQuestion(line);
			}
			question.setText("Click the Button to Begin!");
			reader.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void makeQuestion(String input) {
		String[] quesAns = input.split("/");
		backend.setValues(quesAns[0], quesAns[1]);
		questions = backend.getQuestions();
		answers = backend.getAnswers();
	}
	
	private void checkAnswer() {
		if(answerField.getText().equals(answers.get(questionIndex - 1))) {
			System.out.println("Correct!");
			score++;
		}else {
			System.out.println("Incorrect!");
			score--;
		}
		scoreLabel.setText("Score: " + score);	
	}
}
