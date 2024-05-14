/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import static org.junit.Assert.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

public class SocialNetworkTest {

    private static final Instant d1 = Instant.parse("2024-05-17T10:00:00Z");
    private static final Instant d2 = Instant.parse("2024-05-17T11:00:00Z");
    private static final Instant d3 = Instant.parse("2024-05-17T11:01:50Z");
    private static final Instant d4 = Instant.parse("2024-05-17T11:24:58Z");
    private static final Instant d5 = Instant.parse("2024-05-17T13:00:00Z");
    private static final Instant d6 = Instant.parse("2024-05-17T14:30:00Z");
    private static final Instant d7 = Instant.parse("2024-05-18T09:15:00Z");
    private static final Instant d8 = Instant.parse("2024-05-18T12:45:00Z");
    private static final Instant d9 = Instant.parse("2024-05-18T15:20:00Z");
    private static final Instant d10 = Instant.parse("2024-05-19T08:45:00Z");
    private static final Instant d11 = Instant.parse("2024-05-19T10:00:00Z");
    private static final Instant d12 = Instant.parse("2024-05-19T13:30:00Z");
    private static final Instant d13 = Instant.parse("2024-05-20T11:10:00Z");
    private static final Instant d14 = Instant.parse("2024-05-20T13:45:00Z");
    private static final Instant d15 = Instant.parse("2024-05-20T17:00:00Z");
    private static final Instant d16 = Instant.parse("2024-05-21T09:30:00Z");
    private static final Instant d17 = Instant.parse("2024-05-21T11:15:00Z");
    private static final Instant d18 = Instant.parse("2024-05-21T14:40:00Z");

    // LOL USED GPT TO GENERATE TWEETS 😂
    private static final Tweet tweet1 = new Tweet(1, "Madeline", "Just finished my morning yoga session, feeling centered and ready to take on the day! #yoga #mindfulness @Lila @Emma", d1);
    private static final Tweet tweet2 = new Tweet(2, "Sam", "Enjoying a cup of coffee while brainstorming ideas for my next project. Any suggestions? ☕️💡 @Alex @Sophie", d2);
    private static final Tweet tweet3 = new Tweet(3, "Alex", "Caught up with an old friend over lunch today, reminiscing about the good old days. Friendship truly is timeless. 🍽️👫 @Madeline @Sophie", d3);
    private static final Tweet tweet4 = new Tweet(4, "Emma", "Feeling inspired after attending a thought-provoking lecture on sustainable living. Small changes can make a big difference! 🌱♻️ @Sam @Michael", d4);
    private static final Tweet tweet5 = new Tweet(5, "Chris", "Just finished a refreshing hike in the mountains, nature never fails to amaze me. 🏞️ @Lila @Sophie", d5);
    private static final Tweet tweet6 = new Tweet(6, "Ryan", "Spent the afternoon volunteering at the local soup kitchen, a reminder of the importance of giving back to the community. 🥣🤝 @Alex @Michael", d6);
    private static final Tweet tweet7 = new Tweet(7, "Sophia", "Thrilled to announce the launch of my new blog! Excited to share my thoughts and experiences with the world. 🚀✍️ @Ryan @Olivia", d7);
    private static final Tweet tweet8 = new Tweet(8, "Mark", "Just wrapped up a productive meeting with my team, feeling motivated and determined to achieve our goals. 💼👊 @Sophia @Ryan", d8);
    private static final Tweet tweet9 = new Tweet(9, "Ella", "Indulging in some self-care tonight with a bubble bath and my favorite book. 🛁📖 @Mark @Olivia", d9);
    private static final Tweet tweet10 = new Tweet(10, "Sophie", "Exploring the vibrant streets of the city, every corner holds a new adventure waiting to be discovered. 🌆🚶‍♂️ @Ella @Madeline", d10);
    private static final Tweet tweet11 = new Tweet(11, "Michael", "Reflecting on the power of kindness today - a small act can brighten someone's day more than we realize. 💖 @Sophie @Olivia", d11);
    private static final Tweet tweet12 = new Tweet(12, "Jack", "Impromptu road trip with friends, because sometimes the best memories are made on a whim. 🚗🎉 @Michael @Emma", d12);
    private static final Tweet tweet13 = new Tweet(13, "Olivia", "Just finished a challenging workout, feeling stronger both mentally and physically. 💪🧠 @Jack @Lila", d13);
    private static final Tweet tweet14 = new Tweet(14, "Max", "Cooking dinner with my partner tonight, always fun to experiment with new recipes together. 🍳👩‍🍳 @Olivia @Michael", d14);
    private static final Tweet tweet15 = new Tweet(15, "Liam", "Enjoying a quiet evening at home, sometimes solitude is the best company. 🏡🌙 @Max @Emma", d15);
    private static final Tweet tweet16 = new Tweet(16, "Emily", "Attended a fascinating lecture on astrophysics today, the universe never ceases to amaze me. 🌌🔭 @Liam @Jack", d16);
    private static final Tweet tweet17 = new Tweet(17, "Lucas", "Spontaneous dance party in the living room tonight - because why not? 💃🎶 @Emily @Sophie", d17);
    private static final Tweet tweet18 = new Tweet(18, "Mia", "Feeling grateful for the support of friends and family, couldn't do it without them. 🙏❤️ @Lucas @Max", d18);

    /*
     * TODO: your testing strategies for these methods should go here.
     * See the ic03-testing exercise for examples of what a testing strategy comment looks like.
     * Make sure you have partitions.
     */
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testGuessFollowsGraphEmpty() {
        
        Map<String, Set<String>> followsGraphA = SocialNetwork.guessFollowsGraph(List.of(tweet2, tweet3, tweet5));
        Map<String, Set<String>> correctOutputA = Map.of(
            "Sophie", Set.of("Sam", "Alex", "Chris"),
            "Alex", Set.of("Sam"),
            "Madeline", Set.of("Alex"),
            "Lila", Set.of("Chris")
            );
        assertEquals(correctOutputA, followsGraphA);

        Map<String, Set<String>> followsGraphB = SocialNetwork.guessFollowsGraph(List.of());
        assertEquals(Map.of(), followsGraphB);
    }
    
    @Test
    public void testInfluencersEmpty() {
        Map<String, Set<String>> followsGraphA = Map.of(
            "Sophie", Set.of("Sam", "Alex", "Chris", "Matthew"),
            "Alex", Set.of("Sam", "Egor"),
            "Madeline", Set.of("Alex", "Alva"),
            "Lila", Set.of("Chris"),
            "Saimon", Set.of()
        );

        List<String> influencersA = SocialNetwork.influencers(followsGraphA);
        assertEquals(List.of("Sophie", "Alex", "Madeline", "Lila", "Saimon"), influencersA);

        Map<String, Set<String>> followsGraphB = new HashMap<>();
        List<String> influencersB = SocialNetwork.influencers(followsGraphB);
        assertEquals(Map.of(), influencersB);
    }

    /*
     * Warning: all the tests you write here must be runnable against any
     * SocialNetwork class that follows the spec. It will be run against several
     * staff implementations of SocialNetwork, which will be done by overwriting
     * (temporarily) your version of SocialNetwork with the staff's version.
     * DO NOT strengthen the spec of SocialNetwork or its methods.
     * 
     * In particular, your test cases must not call helper methods of your own
     * that you have put in SocialNetwork, because that means you're testing a
     * stronger spec than SocialNetwork says. If you need such helper methods,
     * define them in a different class. If you only need them in this test
     * class, then keep them in this test class.
     */

}
