import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class ChatBot {
	static Scanner scanner = new Scanner(System.in);
	static String response = new String();
	static String[] terms;
	static ArrayList<Word> words = new ArrayList<Word>();
	
	static String angryWords[] = {"fuck", "fucking", "hate", "mad", "angry", "stupid", "damn", "terrible", "kill", "kills", "swear", "ass", "asshole", "hell", "shit"};
	static String sadWords[] = {"sad", "depressed", "gloomy", "bad", "cry", "crying", "sinking", "tears", "hurt", "heartbroken"};
	
	static String angryResponses[] = {
			"Wow, that is a lot of emotional baggage. Is there any way I can help you?",
			"You seem really angry. Are you okay?",
			"Do you need a therapist? Or do I need to call anger management?"
	};
	
	static String sadResponses[] = {
			"I'm sorry, love. I hope you'll feel better.",
			"Forgive me for being a computer, but I can't really detect emotion. Are you sad?",
			"You seem a little under the weather. Try drinking some lavender tea."
	};

	public enum Emotion {
		NONE,
		ANGRY,
		SAD
	}	
	
	static void askUserInput(){
		response = scanner.nextLine();
	}
	
	static private Emotion evaluateUserInput(){
		terms = response.split("\\s+");
		for (int i = 0; i < terms.length; i++){
			terms[i] = terms[i].replaceAll("[^\\w]", "");
	
			Word word = new Word(terms[i], null);
			words.add(word);
		}
		
		/*for (Word word: words){
			System.out.println(word.getWord());
		}*/
		int numAngryWords = 0;
		int numSadWords = 0;
		
		for (int i = 0; i < words.size(); i++){
			for (String word : angryWords){
				if (words.get(i).getWord().equalsIgnoreCase(word)){
					numAngryWords++;
					words.get(i).emotion = Emotion.ANGRY;
				}
			}
			for (String word : sadWords){
				if (words.get(i).getWord().equalsIgnoreCase(word)){
					numSadWords++;
					words.get(i).emotion = Emotion.SAD;
				}
			}
			words.get(i).emotion = Emotion.NONE;
		}
		
		/*for (Word word : words){
			System.out.println(word.getWord() + " " + word.getEmotion());
		}*/
		
		Emotion emotion;
		int max = Math.max(numAngryWords, numSadWords);
		if (max == 0){
			emotion = Emotion.NONE;
		}
		
		else if (max == numAngryWords) {
			//System.out.println("Max: angry - " + max);
			emotion = Emotion.ANGRY;
			return emotion;
		}
		else {
			//System.out.println("Max: sad - " + max);
			emotion = Emotion.SAD;
			return emotion;
		}
		
		//System.out.println("Max: none - " + max);
		//System.out.println("User is " + emotion);
		
		return emotion;
	}
	
	public static void respond(Emotion emotion){	
		Random rand = new Random();
		int randomNumber = rand.nextInt(2 + 1 - 0) + 0;
		switch (emotion){
		case ANGRY:
			System.out.println(angryResponses[randomNumber]);
			break;
		case SAD:
			System.out.println(sadResponses[randomNumber]);
			break;
		default:
			System.out.println("Today I discovered that cats have emotions too.");
		}
		words.clear();
		terms = null;
	}
	
	public static class Word {
		String string;
		Emotion emotion;
		public Word (String string, Emotion emotion){
			this.string = string;
			this.emotion = emotion;
		}
		
		public Emotion getEmotion(){
			return this.emotion;
		}
		
		public String getWord(){
			return this.string;
		}
	}
		
	static private void prompt(){
		askUserInput();
		Emotion userEmotion = evaluateUserInput();
		respond(userEmotion);
	}
	
	public static void main (String [] args){
		System.out.println("How are you?");
		while(true){
			prompt();
		}
		
	}
}