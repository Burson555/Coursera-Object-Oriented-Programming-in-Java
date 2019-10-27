package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;

/** 
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team 
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList; 
	
	// The starting "word"
	private String starter;
	
	// The random number generator
	private Random rnGenerator;
	
	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}
	
	
	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		if (sourceText == null)
			throw new NullPointerException();
		String[] wordListRaw = sourceText.split("[ ]+");
		this.starter = wordListRaw[0];
		String prevWord = wordListRaw[0];
		// go through each pair of words
		int i;
		for (i = 1; i < wordListRaw.length; i++) {
			this.trainPair(prevWord, wordListRaw[i]);
			prevWord = wordListRaw[i];
		}
		// deal with beginning and end of source text
		i--;
		this.trainPair(wordListRaw[i], this.starter);
	}
	
	/** 
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		if (numWords < 0)
			throw new IndexOutOfBoundsException();
		String currentWord = this.starter;
		String output = "";
		if (numWords > 0) {
			output += currentWord;
			numWords--;
		}
		while (numWords > 0) {
			for (ListNode tempNode : this.wordList) {
				if (tempNode.getWord().compareTo(currentWord) == 0) {
					currentWord = tempNode.getRandomNextWord(rnGenerator);
					break;
				}
			}
			output += " ";
			output += currentWord;
			numWords--;
		}
		return output;
	}
	
	
	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}
	
	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		wordList = new LinkedList<ListNode>();
		this.train(sourceText);
	}
	
	// TODO: Add any private helper methods you need here.
	private void trainPair(String prevWord, String currentWord) {
		boolean prevWordFound = false;
		for (ListNode temp : wordList) {
			if (temp.getWord().compareTo(prevWord) == 0) {
				temp.addNextWord(currentWord);
				prevWordFound = true;
				break;
			}
		}
		if (!prevWordFound) {
			ListNode tempNode = new ListNode(prevWord);
			tempNode.addNextWord(currentWord);
			wordList.add(tempNode);
		}
	}
	
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.   
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(81));
//		String textString = "hi";

//		String textString3 = "Hello there Hello Leo";
//		System.out.println(textString3);
//		gen.train(textString3);
//		System.out.println(gen);
//
//		String textString4 = "Hello hello hello I don't know. I don't know how to say hello, halo, hi, hi, hi";
//		System.out.println(textString4);
//		gen.train(textString4);
//		System.out.println(gen);
//		
//		String textString5 = "Hello";
//		System.out.println(textString5);
//		gen.train(textString5);
//		System.out.println(gen);
//		
//		String textString6 = "";
//		System.out.println(textString6);
//		gen.train(textString6);
//		System.out.println(gen);
		
		String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		System.out.println(textString);
		gen.train(textString);
		System.out.println(gen);
		
		System.out.println(gen.generateText(20));
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();
		
		
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		gen.retrain(textString2);
		System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list 
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;
	
	// The next words that could follow it
	private List<String> nextWords;
	
	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}
	
	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}
	
	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from 
	    // the MarkovTextGeneratorLoL class
	    return nextWords.get(generator.nextInt(nextWords.size()));
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}
	
}


