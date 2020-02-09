import java.util.*; 
import java.io.*;

public class BackEnd {
	ArrayList<String> questions = new ArrayList<String>();
	ArrayList<String> answers = new ArrayList<String>();
	
	public ArrayList<String> getAnswers() {
		return answers;	
	}
	
	public ArrayList<String> getQuestions() {
		return questions;	
	}
	
	public void setValues(String question, String answer) {
		questions.add(question);
		answers.add(answer);
	}
}
