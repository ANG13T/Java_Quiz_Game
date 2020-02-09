
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.*;
import java.util.*;


public class Builder {
	
	private JFrame frame;
	private BackEnd questionBackEnd;
	private JTextArea questionfield;
	private JTextArea answerfield;

	public static void main(String[] args) {
		new Builder().go();
	}
	
	public void go() {
		questionBackEnd = new BackEnd();
		
		JMenuBar menubar = new JMenuBar();
		menubar.setBackground(Color.DARK_GRAY);
		JMenu fileMenu = new JMenu("File");
		JMenuItem menuItem = new JMenuItem("Save");
		menuItem.addActionListener(new FilePickerClicked());
		fileMenu.add(menuItem);
		menubar.add(fileMenu);
		JPanel panel = new JPanel();
		///
		JLabel question = new JLabel("Question:");
		question.setHorizontalAlignment(SwingConstants.LEFT);
		question.setFont(new Font("sanserif", Font.BOLD, 30));
		///
		JLabel answer = new JLabel("Answer:  ");
		answer.setFont(new Font("sanserif", Font.BOLD, 30));
		///
		questionfield = new JTextArea(6,20);
		questionfield.setSize(700, 30);
		questionfield.setLineWrap(true);
		questionfield.setWrapStyleWord(true);
		////
		///
		answerfield = new JTextArea(6,21);
		answerfield.setSize(700, 30);
		answerfield.setLineWrap(true);
		answerfield.setWrapStyleWord(true);
		///
		JButton button = new JButton("Next Question >");
		button.addActionListener(new nextQuestionClicked());
		button.setBackground(Color.BLUE);
		button.setOpaque(true);
		button.setBorderPainted(false);
		button.setForeground(Color.white);
		button.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(question);
		panel.add(questionfield);
		panel.add(answer);
		panel.add(answerfield);
		panel.add(button);
		
		frame = new JFrame("Quiz Builder");
		frame.setJMenuBar(menubar);
		frame.getContentPane().add(panel);
		frame.setSize(900, 300);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
		private class nextQuestionClicked implements ActionListener{
			public void actionPerformed(ActionEvent ev) {
				questionBackEnd.setValues(questionfield.getText(), answerfield.getText());
				questionfield.setText("");
				answerfield.setText("");
			}
		}
		
		private class FilePickerClicked implements ActionListener{
			public void actionPerformed(ActionEvent ev) {
				JFileChooser fileSave = new JFileChooser();
				fileSave.showSaveDialog(frame);
				saveFile(fileSave.getSelectedFile());
			}
		}
		
		public void saveFile(File file) {
			try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(file));
				ArrayList<String> questions = questionBackEnd.getQuestions();
				ArrayList<String> answers = questionBackEnd.getAnswers();
				
				for(int i = 0; i < questions.size(); i++) {
					System.out.println(questions.get(i));
					System.out.println(answers.get(i));
					writer.write(questions.get(i) + "/");
					writer.write(answers.get(i) + "\n");
				}
				writer.close();
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		
		}

}
