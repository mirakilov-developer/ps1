/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.time.Instant;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *          list of tweets with distinct ids, not modified by this method.
     *          tweets.length > 0
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {
        Instant mn = tweets.get(0).getTimestamp();
        Instant mx = tweets.get(0).getTimestamp();
        for(Tweet tw: tweets) {
            if(mn.isAfter(tw.getTimestamp())) mn = tw.getTimestamp();
            if(mx.isBefore(tw.getTimestamp())) mx = tw.getTimestamp();
        }
        return new Timespan(mn, mx);
    }

    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     *            tweets.length > 0
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        Set<String> mentions = new HashSet<>();
        for(Tweet tw: tweets) {
            for(String mention: getMentions(tw.getText())) {
                mentions.add(mention);
            }
        }
        return mentions;
    }
    
    private static Set<String> getMentions(String tweetText) {
        Set<String> mentions = new HashSet<>();
        for(String word: tweetText.split("\\s")) {
            if(word.length() >= 2 && word.charAt(0) == '@' && word.chars().filter(ch -> ch == '@').count() == 1) {
                mentions.add(word.toLowerCase());
            }
        }

        return mentions;
    }
}
