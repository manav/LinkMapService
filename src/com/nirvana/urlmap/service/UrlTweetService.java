package com.nirvana.urlmap.service;

import java.util.LinkedList;
import java.util.Set;

import com.nirvana.urlmap.domain.UrlTweet;
import com.nirvana.urlmap.dto.UrlTweetDTO;


public interface UrlTweetService {

    /**
     * UserId is needed until twitter consolidates their REST/SEARCH API
     * @param screenName
     * @param userId
     * @return
     */
    public Set<UrlTweet> fetchLatestUrlTweets(String screenName, Long userId,Long sinceId) ;

    public Set<UrlTweet> fetchLatestUrlTweets(String categoryName, String host);
    
    public Set<UrlTweet> getUrlTweets(String screenName);
    
    public Set<UrlTweet> getUrlTweets(Long userId);
    
    public Set<UrlTweet> getUrlTweets(Set<Long> userIds);
    
    public void persistUrlTweets(Set<UrlTweet> urlTweets);

    public Long getLastStatusIdOfUrlTweet(Long friendId);
    
    public LinkedList<UrlTweetDTO> getTweetsWithUrlInThisSocialGraph(String categoryName,String host, Long beforeStatusId, int rpp,int daysAgo);

    public void deleteAllUrlTweets();
    
    
}
