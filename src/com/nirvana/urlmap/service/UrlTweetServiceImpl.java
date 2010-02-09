package com.nirvana.urlmap.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.logging.Logger;

import javax.cache.Cache;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.inject.Inject;
import com.nirvana.urlmap.domain.UrlTweet;
import com.nirvana.urlmap.domain.User;
import com.nirvana.urlmap.dto.UrlTweetDTO;
import com.nirvana.urlmap.util.DTOHelper;
import com.nirvana.urlmap.util.TweetComparator;
import java.util.logging.Level;


public class UrlTweetServiceImpl implements UrlTweetService{

    private final TwitterSearchService twitterSearchService;
    private final UserService userService;

    private final Cache cache;

    private final PersistenceManagerFactory persistenceManagerFactory;
    private final DateFormat tweetDateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
    
    private static final Logger logger = Logger.getLogger(UrlTweetServiceImpl.class.getName());

    
    @Inject    
    public UrlTweetServiceImpl(TwitterSearchService twitterSearchService ,UserService userService,PersistenceManagerFactory persistenceManagerFactory, Cache cache) {
        super();
        this.twitterSearchService = twitterSearchService;
        this.persistenceManagerFactory=persistenceManagerFactory;
        this.userService = userService;
        this.cache = cache;
    }

    @Override
    public Set<UrlTweet> getUrlTweets(String screenName) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<UrlTweet> getUrlTweets(Long userId) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<UrlTweet> getUrlTweets(Set<Long> userIds) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void persistUrlTweets(Set<UrlTweet> urlTweets) {
        
        PersistenceManager pm = getPersistenceManager();

        if(urlTweets.size()==0){
            logger.log(Level.SEVERE, "Empty list cannot be persisted");
            return;
        }

          for(UrlTweet urlTweet:urlTweets){

               Key key = KeyFactory.createKey(UrlTweet.class.getSimpleName(), urlTweet.getStatusId().toString());
               urlTweet.setKey(key);
       } 

      try{
       pm.makePersistentAll(urlTweets);
      }finally{
          pm.close();
      }
    }

    @Override
    public void deleteAllUrlTweets() {
        
        PersistenceManager pm = getPersistenceManager();

        Query query = pm.newQuery(UrlTweet.class);
        query.setRange(0, 100);
        try{
        
        query.deletePersistentAll();
        
        } finally{
            query.closeAll();
            pm.close();
        }
    }

    @Override
    public Set<UrlTweet> fetchLatestUrlTweets(String screenName, Long userId,Long sinceId) {
        String sinceIdParam = (sinceId==null)?"":"&since_id="+sinceId;
        String jsonResponse = twitterSearchService.doSearch("http://search.twitter.com/search.json"+("?from=" + screenName)+"&filter=links&rpp=100"+sinceIdParam);
        Set<UrlTweet> urlTweets = new HashSet<UrlTweet>();
        JSONArray jsonArray = null;

        if (jsonResponse == null) {return new HashSet<UrlTweet>();}
        
            JSONObject jsonObject;
            try {

                jsonObject = new JSONObject(jsonResponse);
                jsonArray = jsonObject.getJSONArray("results");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonResult;

                        jsonResult = jsonArray.getJSONObject(i);
                        UrlTweet urlTweet = new UrlTweet(jsonResult.getString("text"), jsonResult.getLong("id"),parseTweetPostedDate(jsonResult.getString("created_at")),jsonResult.getString("from_user"), screenName, userId);
                        urlTweets.add(urlTweet);
                }

                
            } catch (JSONException e) {
                logger.log(Level.SEVERE, " error while parsing this json tweets fromthis user " + e.getMessage()  + "     " +screenName);
            }


         // end of loop when json response is null

        return urlTweets;

    }

    private Date parseTweetPostedDate(String tweetPostedDate) {
        try {
            return tweetDateFormat.parse(tweetPostedDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            logger.log(Level.SEVERE, " error while parsing this tweet date   " + e.getMessage()  + "     " +tweetPostedDate);


        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public Long getLastStatusIdOfUrlTweet(Long friendId) {

        // get persistence manager
        PersistenceManager pm = getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        // formulate query to get request token secret
        Query query = pm.newQuery(UrlTweet.class);
        query.setOrdering("statusId desc");
        query.setFilter("redundantUserId == redundantUserIdParam");
        query.declareParameters("java.lang.Long redundantUserIdParam ");
        query.setRange(0, 1);

        try {

            tx.begin();
            List<UrlTweet> results = (List<UrlTweet>) query.execute(friendId);
            tx.commit();

            if (results == null || results.size() == 0)
                return null;

            return results.get(0).getStatusId();

        } catch(Exception ex){
            return null;
        }finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            query.closeAll();
        }

    }
    
    private PersistenceManager getPersistenceManager() {
        return persistenceManagerFactory.getPersistenceManager();
    }

    @SuppressWarnings("unchecked")
    public LinkedList<UrlTweetDTO> getTweetsWithUrlInThisSocialGraph(String categoryName ,String host,Long beforeStatusId, int rpp, int daysAgo) {

        List<UrlTweet> accumulatedUrlTweets =new LinkedList<UrlTweet>();
        List<UrlTweet> cachedUrlTweets =null;
        
        String screenName = userService.getRobotScreenName(categoryName, host);

        User user = userService.getUser(screenName);
        
        if(user==null) {
            userService.addUser(screenName);
            user = userService.getUser(screenName);
       }

        if(beforeStatusId==null) beforeStatusId=Long.MAX_VALUE;

        //check to see if this data is already in cache
        cachedUrlTweets = (List<UrlTweet>) cache.get(user.getScreenName()+daysAgo);
        
        if(cachedUrlTweets!=null){
            accumulatedUrlTweets = cachedUrlTweets;
            UrlTweet dummyUrlTweet = new UrlTweet();
            dummyUrlTweet.setStatusId(beforeStatusId);
            int subListIndex = accumulatedUrlTweets.indexOf(dummyUrlTweet);
          if(subListIndex!=-1) accumulatedUrlTweets.subList(0,subListIndex).clear();
            if(accumulatedUrlTweets.size() >= rpp){
                if(accumulatedUrlTweets.size() >rpp)accumulatedUrlTweets.subList(rpp, accumulatedUrlTweets.size()).clear();
            }
            logger.log(Level.INFO, " returning from cache screenName : " + screenName);
            return DTOHelper.convertFrom(accumulatedUrlTweets);
        }
        
        Set<Long> friendsIds = user.getFriendsIds();
        
        List<UrlTweet> urlTweetsPerFreind = new LinkedList<UrlTweet>();

        // get persistence manager
        PersistenceManager pm = getPersistenceManager();
        Transaction tx = pm.currentTransaction();

        Map<String, Object> queryParams = new HashMap<String, Object>();

        //get the date query params
        int[] dateQueryParams = getDateParametersForQuery(daysAgo);

        queryParams.put("createdAtYearParam", new Integer(dateQueryParams[0]));
        queryParams.put("createdAtMonthParam", new Integer(dateQueryParams[1]));
        queryParams.put("createdAtDayParam", new Integer(dateQueryParams[2]));
        queryParams.put("beforeStatusIdParam", beforeStatusId);    

        // formulate query to get request token secret
        Query query = pm.newQuery(UrlTweet.class);
        query.setOrdering("statusId desc");
        query.setFilter("redundantUserId == redundantUserIdParam && createdAtYear == createdAtYearParam && createdAtMonth == createdAtMonthParam && createdAtDay == createdAtDayParam && statusId < beforeStatusIdParam");
        query.declareParameters("java.lang.Long redundantUserIdParam , java.lang.Integer createdAtYearParam, java.lang.Integer createdAtMonthParam, java.lang.Integer createdAtDayParam , java.lang.Long beforeStatusIdParam ");
        

          try{  
              
            //execute the query for every friend
            for (Long friendId:friendsIds) {
                
                queryParams.put("redundantUserIdParam", friendId);
                
                try {

                tx.begin();
                urlTweetsPerFreind = (List<UrlTweet>) query.executeWithMap(queryParams);
                accumulatedUrlTweets.addAll(urlTweetsPerFreind);
                tx.commit();
                

                } catch (Exception ex){
                    logger.log(Level.SEVERE, " error while parsing this tweet date   " + ex.getMessage()  + "     " +queryParams);
                }finally {
                    if (tx.isActive()) {
                        tx.rollback();
                    }
                }
                
            }
          }finally{
              query.closeAll();
              pm.close();
          }

          if(accumulatedUrlTweets.size()==0){
              logger.log(Level.WARNING, " DB returned empty list for tweets with links with this screenName : " + screenName);

              return null;
          }
           
          //cache this data
            Collections.sort(accumulatedUrlTweets, new TweetComparator());

            //put the collection in cache
             cache.put(user.getScreenName()+daysAgo, accumulatedUrlTweets);
            
            
            if(accumulatedUrlTweets.size() >= rpp){
                if(accumulatedUrlTweets.size() >rpp)accumulatedUrlTweets.subList(rpp, accumulatedUrlTweets.size()).clear();
            }
            
            return DTOHelper.convertFrom(accumulatedUrlTweets);

    }

    private int[] getDateParametersForQuery(int daysAgo) {
 
        Calendar calendar = new GregorianCalendar(new SimpleTimeZone(0, "UTC"), Locale.US);
        
        //today is 7th noon
        if (daysAgo==0) {

            //yesterday was 6th noon
        } else if (daysAgo==1) {
            calendar.roll(Calendar.DAY_OF_YEAR, -1);   // 5th midnight 23:59:59 
            //leave the date as it is               // 7th morning 00:00:00 
        }

        // 2days ago was 5th noon
        else if (daysAgo==2) {
            calendar.roll(Calendar.DAY_OF_YEAR, -2);    // 4th midnight 23:59:59
        }

        // 3days ago was 4th noon
        else if (daysAgo==3) {
            calendar.roll(Calendar.DAY_OF_YEAR, -3);    // 3rd midnight 23:59:59
        }
        
        else{
            throw new IllegalArgumentException("days can only be bewtween 0 to 3 ; the passed date was : " + daysAgo );
        }
        
        return new int[]{calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)};
        
    }

  
    @Override
    public Set<UrlTweet> fetchLatestUrlTweets(String categoryName, String host) {

        logger.log(Level.INFO, " fetchLatestUrlTweets Category name : " + categoryName + "  : host " + host);
        Set<UrlTweet> accumulatedUrlTweets = new HashSet<UrlTweet>();
        // get the user robot for this category
        String screenName = userService.getRobotScreenName(categoryName, host);
        
        logger.log(Level.INFO, "fetchLatestUrlTweets User screen name : " + screenName );

        User user = userService.getUser(screenName);
        
        if(user==null) {
             userService.addUser(screenName);
             user = userService.getUser(screenName);
        }
        // get this users friends ids 
        Set<Long> friendsIds = user.getFriendsIds();
        
        //iterate over every friend
        Set<UrlTweet> friendsUrlTweets = new HashSet<UrlTweet>();
        String friendsScreenName = null;
        for(Long friendId: friendsIds){
            
            try{
            
            friendsScreenName = userService.getUser(friendId).getScreenName();
            friendsUrlTweets = fetchLatestUrlTweets(friendsScreenName, friendId, getLastStatusIdOfUrlTweet(friendId));
            //get latest from twitter and add it to the accumulated collection
            accumulatedUrlTweets.addAll(friendsUrlTweets);
            }catch(Exception ex){
                logger.log(Level.WARNING, "fetchLatestUrlTweets falied for this friend id : " + friendId + " with screen name " + friendsScreenName ); 
            }
        }
        
        return accumulatedUrlTweets;
        
        
    }


}
