/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class FilterTest {

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
     
    
    private static final Instant d1 = Instant.parse("2024-05-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2024-05-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2024-05-17T11:01:50Z");
    private static final Instant d4 = Instant.parse("2024-05-17T11:24:58Z");

    private static final Tweet tweet1 = new Tweet(1, "Clara", "loypugo@gmail.com, @Madeline @Theo", d1);
    private static final Tweet tweet2 = new Tweet(2, "Madeline", "Started to climb Celeste mountain.   @  @Wierd@@ #climbing #celeste", d2);
    private static final Tweet tweet3 = new Tweet(3, "Theo", "I found interesting person while climbing to mountain! @madeline #celeste #selfie", d3);
    private static final Tweet tweet4 = new Tweet(4, "Madeline", "Reached Summit! (Selfie with Theo).\n@Theo #climbing #celeste #summit", d4);

    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    /*
     * 
     * Testing strategy for writtenBy:
     *      resultLength: 0, 1, >1
     *      order: increasing, shuffled
     */

    @Test
    public void testWrittenBy() {
        List<Tweet> tweetsA = List.of(tweet1, tweet2, tweet3);
        
        // covers resultLength=1, order=increasing
        List<Tweet> writtenByA = Filter.writtenBy(tweetsA, "Clara");
        assertEquals(List.of(tweet1), writtenByA);
        
        // covers resultLength=0
        List<Tweet> writtenByB = Filter.writtenBy(tweetsA, "Adam");
        assertEquals(List.of(), writtenByB);

        // covers resultLength>1, order=shuffled
        List<Tweet> tweetsC = List.of(tweet3, tweet4, tweet2);
        List<Tweet> writtenByC = Filter.writtenBy(tweetsC, "Madeline");
        assertEquals(List.of(tweet4, tweet2), writtenByC);
    }
    
    /*
     * 
     * Testing strategy for inTimespan:
     *      resultLength: 0, 1, >1
     *      order: increasing, shuffled
     */

    @Test
    public void testInTimespan() {
        // covers resultLength=1, order=shuffled
        List<Tweet> tweetsA = List.of(tweet1, tweet3, tweet2);
        Timespan timespanA = new Timespan(Instant.parse("2024-05-17T10:30:00Z"), Instant.parse("2024-05-17T11:30:00Z"));
        List<Tweet> inTimespanA = Filter.inTimespan(tweetsA, timespanA);
        assertEquals(List.of(tweet3, tweet2), inTimespanA);
        
        // covers resultLength=0
        List<Tweet> tweetsB = List.of(tweet1, tweet3, tweet2);
        Timespan timespanB = new Timespan(Instant.parse("2024-05-17T09:30:00Z"), Instant.parse("2024-05-17T09:32:00Z"));
        List<Tweet> inTimespanB = Filter.inTimespan(tweetsB, timespanB);
        assertEquals(List.of(), inTimespanB);

        // covers resultLength>1, order=increasing
        List<Tweet> tweetsC = List.of(tweet1, tweet2, tweet3, tweet4);
        Timespan timespanC = new Timespan(Instant.parse("2024-05-17T10:00:01Z"), Instant.parse("2024-05-17T12:00:00Z"));
        List<Tweet> inTimespanC = Filter.inTimespan(tweetsC, timespanC);
        assertEquals(List.of(tweet2, tweet3, tweet4), inTimespanC);
    }
    
    /*
     * 
     * Testing strategy for inTimespan:
     *      wordsLength: 1, >1
     *      resultLength: 0, 1, >1
     *      order: increasing, shuffled
     */

    /*
     * 
     * Testing strategy for inTimespan:
     *      resultLength: 0, 1, >1
     *      order: increasing, shuffled
     *      caseInsensitivity
     */

    @Test
    public void testContaining() {
        // covers resultLength>1, order=increasing, caseInsensitivity
        assertEquals(List.of(tweet2, tweet3, tweet4), Filter.containing(List.of(tweet1, tweet2, tweet3, tweet4), List.of("mountain", "summit")));
        
        // covers resultLength>1, order=shuffled
        assertEquals(List.of(tweet4, tweet3), Filter.containing(List.of(tweet1, tweet4, tweet3), List.of("#celeste")));
        
        // covers resultLength=0
        assertEquals(List.of(), Filter.containing(List.of(tweet1, tweet2), List.of("abandon")));
        
        // covers resultLength=1, caseInsensitivity
        assertEquals(List.of(tweet4), Filter.containing(List.of(tweet4, tweet3), List.of("MOUNTAIN")));
    }

    /*
     * Warning: all the tests you write here must be runnable against any Filter
     * class that follows the spec. It will be run against several staff
     * implementations of Filter, which will be done by overwriting
     * (temporarily) your version of Filter with the staff's version.
     * DO NOT strengthen the spec of Filter or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Filter, because that means you're testing a stronger
     * spec than Filter says. If you need such helper methods, define them in a
     * different class. If you only need them in this test class, then keep them
     * in this test class.
     */

}
