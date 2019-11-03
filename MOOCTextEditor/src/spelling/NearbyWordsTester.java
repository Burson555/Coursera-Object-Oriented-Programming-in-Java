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
public class NearbyWordsTester {
	
	NearbyWords nbw;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception 
	{
		AutoCompleteDictionaryTrie largeDict = new AutoCompleteDictionaryTrie();
		DictionaryLoader.loadDictionary(largeDict, "data/words.small.txt");
		nbw = new NearbyWords(largeDict);
	}

	
	/** Test if the deletion method is working correctly.
	 */
	@Test
	public void testDeletions()
	{
		List<String> result = new LinkedList<String>();
		nbw.deletions("string", result, false);
		assertEquals("Testing size for deletion one list produced by deletion", 6, result.size());
	}
	

	
	
	
	
}
