/**
 * 
 */
package spelling;

import java.io.File;
import java.util.HashSet;
import java.util.Scanner;


/**
 * A class that implements the Dictionary interface with a HashSet
 */
public class DictionaryHashSet implements Dictionary 
{

    private HashSet<String> words;
	
	public DictionaryHashSet()
	{
	    words = new HashSet<String>();
	}
	
    /** Add this word to the dictionary.
     * @param word The word to add
     * @return true if the word was added to the dictionary 
     * (it wasn't already there). */
	@Override
	public boolean addWord(String word) 
	{
		return words.add(word.toLowerCase());
	}

	/** Return the number of words in the dictionary */
    @Override
	public int size()
	{
    	 return words.size();
	}

	/** Is this a word according to this dictionary? */
    @Override
	public boolean isWord(String s) {
    	return words.contains(s.toLowerCase());
	}
    
//	/** Is this a word according to this dictionary? */
//    @Override
//	public boolean isWord(String s) {
//		if (s == null)
//			return false;
//    	s = this.processString(s);
//		if (s == null)
//			return false;
//    	return words.contains(s.toLowerCase());
//	}
//    
//	private String processString(String s) {
//		// I know for sure that s is NOT null
//		if (s.toLowerCase().compareTo(s) == 0 || s.toUpperCase().compareTo(s) == 0)
//			return s.toLowerCase();
//		// now s is at least of length 2
//		if (s.substring(1).toLowerCase().compareTo(s.substring(1)) == 0)
//			// only the first letter is capital
//			return s.toLowerCase();
//		return null;
//	}
	
   
}
