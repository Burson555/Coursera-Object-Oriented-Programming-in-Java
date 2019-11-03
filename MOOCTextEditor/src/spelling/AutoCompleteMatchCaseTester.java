/**
 * 
 */
package spelling;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author UC San Diego MOOC team
 *
 */
public class AutoCompleteMatchCaseTester {

	private String dictFile = "data/words.small.txt"; 

	AutoCompleteMatchCase emptyDict; 
	AutoCompleteMatchCase smallDict;
	AutoCompleteMatchCase largeDict;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		emptyDict = new AutoCompleteMatchCase();
		smallDict = new AutoCompleteMatchCase();
		largeDict = new AutoCompleteMatchCase();

		smallDict.addWord("Hello");
		smallDict.addWord("HElLo");
		smallDict.addWord("help");
		smallDict.addWord("he");
		smallDict.addWord("hem");
		smallDict.addWord("hot");
		smallDict.addWord("hey");
		smallDict.addWord("a");
		smallDict.addWord("subsequent");
		
		DictionaryLoader.loadDictionary(largeDict, dictFile);
	}

	/** Test the isWord method */
	@Test
	public void testPredictCompletions()
	{
		assertEquals("Testing isWord on large: Hello", 5, largeDict.predictCompletions("Hel", 5).size());
		for (String prediction : largeDict.predictCompletions("Hel", 5))
			System.out.println(prediction);
		System.out.println();
		assertEquals("Testing isWord on large: hello", 3, largeDict.predictCompletions("hel", 3).size());
		for (String prediction : largeDict.predictCompletions("hel", 3))
			System.out.println(prediction);
		System.out.println();
		assertEquals("Testing isWord on large: Hello", 0, largeDict.predictCompletions("hEl", 5).size());
		assertEquals("Testing isWord on large: Hello", 5, largeDict.predictCompletions("HEL", 5).size());
		for (String prediction : largeDict.predictCompletions("HEL", 5))
			System.out.println(prediction);
		System.out.println();
		
	}
	
}
