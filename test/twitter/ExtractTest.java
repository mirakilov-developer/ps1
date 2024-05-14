/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class ExtractTest {

    
    private static final Instant d1 = Instant.parse("2024-05-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2024-05-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2024-05-17T11:01:50Z");
    private static final Instant d4 = Instant.parse("2024-05-17T11:24:58Z");
    
    private static final Tweet tweet1 = new Tweet(1, "Useful contacts.", "loypugo@gmail.com, @Madeline @Theo", d1);
    private static final Tweet tweet2 = new Tweet(2, "Madeline", "Started to climb Celeste mountain.   @  @Wierd@@ #climbing #celeste", d2);
    private static final Tweet tweet3 = new Tweet(3, "Theo", "I found interesting person while climbing to mountain! @madeline #celeste #selfie", d3);
    private static final Tweet tweet4 = new Tweet(4, "Madeline", "Reached Summit! (Selfie with Theo).\n@Theo #climbing #celeste #summit", d4);
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }

    /*
     * 
     * Testing strategy for getTimespan:
     *      max - min: 0, >0
     *      tweets.length: 1, 2, >2
     * 
     */
    
    @Test
    public void testGetTimespanTwoTweets() {
        
        // covers max-min=0, tweets.length=1
        List<Tweet> tweetsA = List.of(tweet1);
        Timespan timespanA = Extract.getTimespan(tweetsA);
        assertEquals("expected start", d1, timespanA.getStart());
        assertEquals("expected end", d1, timespanA.getEnd());
        
        // covers tweets.length=2, max-min>0
        List<Tweet> tweetsB = List.of(tweet2, tweet1);
        Timespan timespanB = Extract.getTimespan(tweetsB);
        assertEquals("expected start", d1, timespanB.getStart());
        assertEquals("expected end", d2, timespanB.getEnd());
        
        // covers tweets.length>2, max-min>0
        List<Tweet> tweetsC = List.of(tweet2, tweet3, tweet4);
        Timespan timespanC = Extract.getTimespan(tweetsC);
        assertEquals("expected start", d2, timespanC.getStart());
        assertEquals("expected end", d4, timespanC.getEnd());
    }


    /*
     * 
     * Testing strategy for getTimespan:
     *      tweetsLength: 1, >1
     *      resultLength: 0, >0
     *      containsEmail
     *      containsSameMentions
     *      containsEndl
     *      containsWierdUsageOf@
     */

    @Test
    public void testGetMentionedUsersNoMention() {
        
        // covers tweetsLength=1, resultLength=0, containsWierdUsageOf@
        List<Tweet> tweetsA = List.of(tweet2);
        Set<String> mentionedUsersA = Extract.getMentionedUsers(tweetsA);
        assertEquals(Set.of(), mentionedUsersA);

        // covers tweetsLength=1, resultLength>0, containsEmail
        List<Tweet> tweetsB = List.of(tweet1);
        Set<String> mentionedUsersB = Extract.getMentionedUsers(tweetsB);
        assertEquals(Set.of("@madeline", "@theo"), mentionedUsersB);
        
        // covers tweetsLength>1, resultLength>0
        List<Tweet> tweetsC = List.of(tweet2, tweet3);
        Set<String> mentionedUsersC = Extract.getMentionedUsers(tweetsC);
        assertEquals(Set.of("@madeline"), mentionedUsersC);

        // covers tweetsLength>1, resultLength>0, containsEmail, containsSameMentions
        List<Tweet> tweetsD = List.of(tweet1, tweet3);
        Set<String> mentionedUsersD = Extract.getMentionedUsers(tweetsD);
        assertEquals(Set.of("@madeline", "@theo"), mentionedUsersD);
        
        // covers tweetsLength=1, resultLength>0, containsEndl
        List<Tweet> tweetsE = List.of(tweet4);
        Set<String> mentionedUsersE = Extract.getMentionedUsers(tweetsE);
        assertEquals(Set.of("@theo"), mentionedUsersE);
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * Extract class that follows the spec. It will be run against several staff
     * implementations of Extract, which will be done by overwriting
     * (temporarily) your version of Extract with the staff's version.
     * DO NOT strengthen the spec of Extract or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in Extract, because that means you're testing a
     * stronger spec than Extract says. If you need such helper methods, define
     * them in a different class. If you only need them in this test class, then
     * keep them in this test class.
     */

}
