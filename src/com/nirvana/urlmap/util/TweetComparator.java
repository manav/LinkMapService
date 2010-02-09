package com.nirvana.urlmap.util;

import java.io.Serializable;
import java.util.Comparator;

import com.nirvana.urlmap.domain.UrlTweet;



@SuppressWarnings("serial")
public class TweetComparator implements Comparator<UrlTweet>,Serializable {

    /**
     * Compares two references.
     * 
     * @param urlTweetOne
     *            The first reference.
     * @param ref1
     *            The second reference.
     * @return The comparison result.
     * @see Comparator
     */
    public int compare(UrlTweet urlTweetOne, UrlTweet urlTweetTwo) {
        final Long statusIdOne = urlTweetOne.getStatusId();
        final Long statusIdTwo = urlTweetTwo.getStatusId();

        if (statusIdOne ==null || statusIdTwo ==null)  {
            throw new NullPointerException("StautsIds are null or one of them is null");
        }
        
        return statusIdTwo.compareTo(statusIdOne);
        
    }
}
