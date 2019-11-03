package spelling;

import java.util.List;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/** 
 * An trie data structure that implements the Dictionary and the AutoComplete ADT
 * @author You
 *
 */
public class AutoCompleteMatchCase implements Dictionary, AutoComplete {
	//
	//	It is easily observed that this class, AutoCompleteMatchCase, 
	// 		also has the feature of a dictionary, storing words.
	//
	//	Though this seems redundant since we already have a dictionary realized by HashSet,
	//		and the .addWord(), .size() and .isWord() are never called from AutoCompleteMatchCase,
	//		it is reasonable to keep both structures,
	//		because Trie makes it easier for spelling suggestions, or BFS,
	//		while HashSet stands out in insertion and search.
	//
	//	However, if we want only keep only the .predictCompletions() function in this class,
	//		it is NOT plausible,
	//		because our current implementation of TRIE depends heavily on TRIE structure.
	//
	//	If we only keep one of them, it would be TRIE because it has all functions we need.
	//
	//	Note: for an entirely capitalized word, the suggestions are all in lower case.
	//			This is a potential update to be made.
	//

    private TrieNode root;
    private int size;
    
    /** This is a constructor.
	 * Methods .addWord(), .size() and .isWord() are just copied 
	 * from class AutoCompleteDictionaryTrie and DictionaryHashSetMatchCase.
	 * Only small modifications are made.
	 */
    public AutoCompleteMatchCase()
	{
		root = new TrieNode();
	}
	
	
	/** Insert a word into the trie.
	 * For the basic part of the assignment (part 2), you should convert the 
	 * string to all lower case before you insert it. 
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes 
	 * into the trie, as described outlined in the videos for this week. It 
	 * should appropriately use existing nodes in the trie, only creating new 
	 * nodes when necessary. E.g. If the word "no" is already in the trie, 
	 * then adding the word "now" would add only one additional node 
	 * (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already exists
	 * in the dictionary.
	 */
	public boolean addWord(String word)
	{
	    //TODO: Implement this method.
		if (word == null)
			return false;
//		word = processString(word);
		word = word.toLowerCase();
		char[] charArray = word.toCharArray();
		TrieNode temp = this.root;
		for (char c : charArray) {
			if (temp.getChild(c) == null) 
				temp.insert(c);
			temp = temp.getChild(c);
		}
		boolean addResult = !temp.endsWord();
		if (temp.endsWord() == false) {
			temp.setEndsWord(true);
			this.size++;
		}
		return addResult;
	}
	
	/** 
	 * Return the number of words in the dictionary.  This is NOT necessarily the same
	 * as the number of TrieNodes in the trie.
	 */
	public int size()
	{
	    //TODO: Implement this method
	    return this.size;
	}
	
	
	/** Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week. */
	@Override
	public boolean isWord(String s) 
	{
	    // TODO: Implement this method
		if (s == null)
			return false;
		s = this.processString(s);
		if (s == null) // it is NOT redundant, if s equals to null it means the capitalization is invalid
			return false;
		char[] charArray = s.toCharArray();
		TrieNode temp = this.root;
		for (char c : charArray) {
			if (temp != null)
				temp = temp.getChild(c);
			else
				break;
		}
		if (temp == null)
			return false;
		else
			return temp.endsWord();
	}
	
	private String processString(String s) {
		// I know for sure that s is NOT null
		if (s.toLowerCase().compareTo(s) == 0 || s.toUpperCase().compareTo(s) == 0)
			return s.toLowerCase();
		// now s is at least of length 2
		if (s.substring(1).toLowerCase().compareTo(s.substring(1)) == 0)
			// only the first letter is capital
			return s.toLowerCase();
		return null;
	}

	/** 
     * Return a list, in order of increasing (non-decreasing) word length,
     * containing the numCompletions shortest legal completions 
     * of the prefix string. All legal completions must be valid words in the 
     * dictionary. If the prefix itself is a valid word, it is included 
     * in the list of returned words. 
     * 
     * The list of completions must contain 
     * all of the shortest completions, but when there are ties, it may break 
     * them in any order. For example, if there the prefix string is "ste" and 
     * only the words "step", "stem", "stew", "steer" and "steep" are in the 
     * dictionary, when the user asks for 4 completions, the list must include 
     * "step", "stem" and "stew", but may include either the word 
     * "steer" or "steep".
     * 
     * If this string prefix is not in the trie, it returns an empty list.
     * 
     * @param prefix The text to use at the word stem
     * @param numCompletions The maximum number of predictions desired.
     * @return A list containing the up to numCompletions best predictions
     */@Override
     public List<String> predictCompletions(String prefix, int numCompletions) 
     {
    	 // TODO: Implement this method
    	 // This method should implement the following algorithm:
    	 // 1. Find the stem in the trie.  If the stem does not appear in the trie, return an
    	 //    empty list
    	 // 2. Once the stem is found, perform a breadth first search to generate completions
    	 //    using the following algorithm:
    	 //    Create a queue (LinkedList) and add the node that completes the stem to the back
    	 //       of the list.
    	 //    Create a list of completions to return (initially empty)
    	 //    While the queue is not empty and you don't have enough completions:
    	 //       remove the first Node from the queue
    	 //       If it is a word, add it to the completions list
    	 //       Add all of its child nodes to the back of the queue
    	 // Return the list of completions
    	 List<String> predictions = new LinkedList<String>();
    	 boolean isFirstLetterCap = false;
    	 if (prefix == null)
    		 return predictions;
    	 // find the TRIE node to begin with
    	 if (64 < prefix.charAt(0) && prefix.charAt(0) < 91) {
    		 if (prefix.length() == 1)
    			 isFirstLetterCap = true;
    		 else if (prefix.length() > 1 && prefix.substring(1).toLowerCase().equals(prefix.substring(1)))
    			 isFirstLetterCap = true;
    	 }
    	 prefix = this.processString(prefix);
    	 if (prefix == null) // it is NOT redundant, if s equals to null it means the capitalization is invalid
    		 return predictions;
    	 char[] charArray = prefix.toCharArray();
    	 TrieNode temp = this.root;
    	 for (char c : charArray) {
    		 if (temp != null)
    			 temp = temp.getChild(c);
    		 else
    			 break;
    	 }
    	 if (temp == null)
    		 return predictions;
    	 // now we've found the TRIE node to begin with
    	 // we can do a breadth first search
    	 List<TrieNode> trieFIFO = new LinkedList<TrieNode>(); // the linked list itself has the feature of FIFO
    	 trieFIFO.add(temp);
    	 while (!trieFIFO.isEmpty() && numCompletions > 0) {
    		 temp = trieFIFO.remove(0);
    		 if (temp.endsWord()) {
    			 predictions.add(temp.getText());
        		 numCompletions--;
    		 }
    		 for (Character c : temp.getValidNextCharacters())
    			 trieFIFO.add(temp.getChild(c));
    	 }
    	 if (isFirstLetterCap) {
    		 for (int i = 0; i < predictions.size(); i++) {
    			 String prediction = predictions.get(i);
    			 if (prediction.length() > 1)
    				 prediction = Character.toString((char)(prediction.charAt(0) -32)) + prediction.substring(1);
    			 else
    				 prediction = prediction.toUpperCase();
    			 predictions.set(i, prediction);
    		 }
    	 }
    	 return predictions; // it is possible that predictions is empty
     }

 	// For debugging
 	public void printTree()
 	{
 		printNode(root);
 	}
 	
 	/** Do a pre-order traversal from this node down */
 	public void printNode(TrieNode curr)
 	{
 		if (curr == null) 
 			return;
 		
 		System.out.println(curr.getText());
 		
 		TrieNode next = null;
 		for (Character c : curr.getValidNextCharacters()) {
 			next = curr.getChild(c);
 			printNode(next);
 		}
 	}
 	

	
}