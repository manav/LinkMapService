package com.nirvana.urlmap.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.SimpleTimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

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

public class UserServiceImpl implements UserService {

    private final TwitterRestService        twitterApiService;
    private final PersistenceManagerFactory persistenceManagerFactory;
    private final DateFormat                userDateFormat  = new SimpleDateFormat("EEE MMM d HH:mm:ss Z yyyy");

    private static Map<String, String>      robotFeedLookUp = new HashMap<String, String>();

    private static final Logger             logger          = Logger.getLogger(UserServiceImpl.class.getName());

    static {
        robotFeedLookUp.put("us-b", "usBizRobot");
        robotFeedLookUp.put("us-s", "usSportsFeed");
        robotFeedLookUp.put("us-e", "usEtFeed");
        robotFeedLookUp.put("us-p", "usPoliticsFeed");

        robotFeedLookUp.put("mumbai-b", "mumbaiBizFeed");
        robotFeedLookUp.put("mumbai-s", "mumbaiSports");
        robotFeedLookUp.put("mumbai-e", "mumbaiEtFeed");
        robotFeedLookUp.put("mumbai-p", "mumbaiPolitics");

        robotFeedLookUp.put("chennai-b", "chennaiBizRobot");
        robotFeedLookUp.put("chennai-s", "chennaiSports");
        robotFeedLookUp.put("chennai-e", "chennaiEtFeed");
        robotFeedLookUp.put("chennai-p", "chennaiPolitics");

        robotFeedLookUp.put("delhi-b", "delhiBizFeed");
        robotFeedLookUp.put("delhi-s", "delhiSportsFeed");
        robotFeedLookUp.put("delhi-e", "delhiEtFeed");
        robotFeedLookUp.put("delhi-p", "delhiPolitics");

        robotFeedLookUp.put("bangalore-b", "bangaloreBiz");
        robotFeedLookUp.put("bangalore-s", "bangaloreSports");
        robotFeedLookUp.put("bangalore-e", "bangaloreEt");
        robotFeedLookUp.put("bangalore-p", "blorePolitics");

    }

    @Inject
    public UserServiceImpl(TwitterRestService twitterApiService, PersistenceManagerFactory persistenceManagerFactory) {
        super();
        this.twitterApiService = twitterApiService;
        this.persistenceManagerFactory = persistenceManagerFactory;
    }

    public User fetchUser(String screenName) {

        String baseUri = "http://twitter.com/users/show.json";
        String queryString = "?screen_name=" + screenName;
        User user = null;

        String jsonResponse = twitterApiService.doFetch(baseUri + queryString);

        if (jsonResponse != null) {

            try {

                JSONObject jsonObject = new JSONObject(jsonResponse);

                // create new user
                user = new User(

                jsonObject.getLong("id"), jsonObject.getString("name"), jsonObject.getString("screen_name"),
                        parseInt(jsonObject.get("friends_count")), parseUserCreatedDate(jsonObject
                                .getString("created_at")), parseInt(jsonObject.get("statuses_count")), jsonObject
                                .getString("profile_background_image_url"), jsonObject.getString("description"),
                        parseBoolean(jsonObject.get("notifications")), parseInt(jsonObject.get("utc_offset")),
                        jsonObject.getString("profile_link_color"), parseBoolean(jsonObject.get("following")),
                        jsonObject.getString("profile_background_tile"), jsonObject
                                .getString("profile_background_color"), parseBoolean(jsonObject.get("verified")),
                        parseInt(jsonObject.get("favourites_count")), parseInt(jsonObject.get("followers_count")),
                        jsonObject.getString("url"), jsonObject.getString("profile_image_url"), jsonObject
                                .getString("profile_sidebar_fill_color"), parseBoolean(jsonObject.get("protected")),
                        jsonObject.getString("time_zone"), jsonObject.getString("profile_sidebar_border_color"),
                        jsonObject.getString("location"), jsonObject.getString("profile_text_color"));

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return user;

    }

    public User getUser(String screenName) {

        // get persistence manager
        PersistenceManager pm = getPersistenceManager();
        pm.setDetachAllOnCommit(true);

        Transaction tx = pm.currentTransaction();

        // formulate query to get request token secret
        Query query = pm.newQuery(User.class);
        query.setFilter("loweredScreenName == screenNameParam");
        query.declareParameters("String screenNameParam");
        query.setUnique(true);

        User user = null;

        try {
            tx.begin();
            user = (User) query.execute(screenName.toLowerCase());
            tx.commit();

        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            query.closeAll();
            pm.close();
        }

        return user;

    }

    public User getUser(Long userId) {

        // get persistence manager
        PersistenceManager pm = getPersistenceManager();
        pm.setDetachAllOnCommit(true);

        Transaction tx = pm.currentTransaction();

        // formulate query to get request token secret
        Query query = pm.newQuery(User.class);
        query.setFilter("userId == userIdParam");
        query.declareParameters("java.lang.Long userIdParam");
        query.setUnique(true);

        User user = null;

        try {
            tx.begin();
            user = (User) query.execute(userId);
            tx.commit();

        } finally {
            if (tx.isActive()) {
                tx.rollback();
            }
            query.closeAll();
            pm.close();
        }

        return user;

    }

    @Override
    public String getRobotScreenName(String categoryName, String host) {
        logger.log(Level.INFO, "Looking up Robot Name : " + " categoryName " + categoryName + " host " + host);

        return UserServiceImpl.robotFeedLookUp.get(host.toLowerCase().trim() + "-" + categoryName.toLowerCase().trim());
    }

    public Set<Long> fetchFriendIds(String screenName, int friendsCount) {

        Set<Long> friendsIds = new HashSet<Long>();
        long cursor = -1;

        do {
            String jsonResponse = twitterApiService.doFetch("http://twitter.com/friends/ids.json"
                    + ("?screen_name=" + screenName) + ("&cursor=" + cursor));

            if (jsonResponse != null) {
                try {

                    JSONObject jsonObject = new JSONObject(jsonResponse);

                    cursor = jsonObject.getLong("next_cursor");
                    JSONArray jsonArray = jsonObject.getJSONArray("ids");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        friendsIds.add(jsonArray.getLong(i));
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }

        } while (cursor != 0);

        return friendsIds;

    }

    public Set<User> fetchFriends(String screenName, int friendsCount) {

        JSONArray jsonArray = null;
        Set<User> friends = new HashSet<User>();

        long cursor = -1;

        do {

            String jsonResponse = twitterApiService.doFetch("http://twitter.com/statuses/friends.json"
                    + ("?screen_name=" + screenName) + ("&cursor=" + cursor));

            try {

                JSONObject jsonObject = new JSONObject(jsonResponse);

                cursor = jsonObject.getLong("next_cursor");

                jsonArray = jsonObject.getJSONArray("users");

            } catch (JSONException e1) {
                logger.log(Level.SEVERE, "error while parsing json for fetch friends call" + e1.getMessage());
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonResult;
                try {

                    jsonResult = jsonArray.getJSONObject(i);

                    // create new user
                    User user = new User(

                    jsonResult.getLong("id"), jsonResult.getString("name"), jsonResult.getString("screen_name"),
                            parseInt(jsonResult.get("friends_count")), parseUserCreatedDate(jsonResult
                                    .getString("created_at")), parseInt(jsonResult.get("statuses_count")), jsonResult
                                    .getString("profile_background_image_url"), jsonResult.getString("description"),
                            parseBoolean(jsonResult.get("notifications")), parseInt(jsonResult.get("utc_offset")),
                            jsonResult.getString("profile_link_color"), parseBoolean(jsonResult.get("following")),
                            jsonResult.getString("profile_background_tile"), jsonResult
                                    .getString("profile_background_color"), parseBoolean(jsonResult.get("verified")),
                            parseInt(jsonResult.get("favourites_count")), parseInt(jsonResult.get("followers_count")),
                            jsonResult.getString("url"), jsonResult.getString("profile_image_url"), jsonResult
                                    .getString("profile_sidebar_fill_color"),
                            parseBoolean(jsonResult.get("protected")), jsonResult.getString("time_zone"), jsonResult
                                    .getString("profile_sidebar_border_color"), jsonResult.getString("location"),
                            jsonResult.getString("profile_text_color"));

                    friends.add(user);

                } catch (JSONException e) {
                    logger.log(Level.SEVERE, "error while parsing json for fetch friends call" + e.getMessage());
                }
            }

        } while (cursor != 0);

        return friends;

    }

    public Set<User> fetchFollowers(String screenName, int followersCount) {
        JSONArray jsonArray = null;

        Set<User> followers = new HashSet<User>();

        long cursor = -1;

        do {

            String jsonResponse = twitterApiService.doFetch("http://twitter.com/statuses/followers.json"
                    + ("?screen_name=" + screenName) + ("&cursor=" + cursor));

            try {

                JSONObject jsonObject = new JSONObject(jsonResponse);

                cursor = jsonObject.getLong("next_cursor");

                jsonArray = jsonObject.getJSONArray("users");

            } catch (JSONException e1) {
                logger.log(Level.SEVERE, "error while parsing json for fetch friends call" + e1.getMessage());
            }

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonResult;
                try {
                    jsonResult = jsonArray.getJSONObject(i);

                    // create new user
                    User user = new User(

                    jsonResult.getLong("id"), jsonResult.getString("name"), jsonResult.getString("screen_name"),
                            parseInt(jsonResult.get("friends_count")), parseUserCreatedDate(jsonResult
                                    .getString("created_at")), parseInt(jsonResult.get("statuses_count")), jsonResult
                                    .getString("profile_background_image_url"), jsonResult.getString("description"),
                            parseBoolean(jsonResult.get("notifications")), parseInt(jsonResult.get("utc_offset")),
                            jsonResult.getString("profile_link_color"), parseBoolean(jsonResult.get("following")),
                            jsonResult.getString("profile_background_tile"), jsonResult
                                    .getString("profile_background_color"), parseBoolean(jsonResult.get("verified")),
                            parseInt(jsonResult.get("favourites_count")), parseInt(jsonResult.get("followers_count")),
                            jsonResult.getString("url"), jsonResult.getString("profile_image_url"), jsonResult
                                    .getString("profile_sidebar_fill_color"),
                            parseBoolean(jsonResult.get("protected")), jsonResult.getString("time_zone"), jsonResult
                                    .getString("profile_sidebar_border_color"), jsonResult.getString("location"),
                            jsonResult.getString("profile_text_color"));

                    followers.add(user);

                } catch (JSONException e) {
                    System.out.println("error while parsign json : " + e.getMessage());
                }
            }

        } while (cursor != 0);

        return followers;

    }

    private void persistUser(User user) {
        PersistenceManager pm = getPersistenceManager();

        try {
            Key key = KeyFactory.createKey(User.class.getSimpleName(), user.getUserId().toString());
            user.setKey(key);
            pm.makePersistent(user);

        } finally {
            pm.close();
        }
    }

    private void persistUser(Set<User> users) {

        for (User user : users) {
            Key key = KeyFactory.createKey(User.class.getSimpleName(), user.getUserId().toString());
            user.setKey(key);
        }

        PersistenceManager pm = getPersistenceManager();

        try {
            pm.makePersistentAll(users);

        } finally {
            pm.close();
        }
    }

    public void updateUser(String screenName) {

        addUser(screenName);
    }

    public void addUser(String screenName) {

        User user = fetchUser(screenName);

        if (user == null || user.getFriendsCount() == 0) {
            logger.log(Level.WARNING, "This user could not be added either twitter call failed or he has no friends: "
                    + " screenname " + screenName);
            throw new RuntimeException("This user could not be added either twitter call failed or he has no friends: "
                    + " screenname " + screenName);
        }

        // fetch friend ids
        Set<Long> friendsIds = fetchFriendIds(screenName, user.getFriendsCount());

        // assign friendIds to user
        user.setFriendsIds(friendsIds);

        user.setLastSyncDate(getCurrentTime());

        // persist the user
        persistUser(user);

        // persist his friends so that we have the screen name
        Set<User> friends = fetchFriends(screenName, user.getFriendsCount());

        persistUser(friends);

    }

    private PersistenceManager getPersistenceManager() {
        return persistenceManagerFactory.getPersistenceManager();
    }

    private Integer parseInt(Object jsonObj) {
        Integer integerVal = null;
        try {
            integerVal = (jsonObj != null) ? new Integer(jsonObj.toString()) : null;
        } catch (NumberFormatException nFe) {
            System.out.println("could not parse int  number : " + jsonObj);
        }

        return integerVal;

    }

    private boolean parseBoolean(Object jsonObj) {

        boolean boolVal = (jsonObj != null) ? new Boolean(jsonObj.toString()).booleanValue() : false;
        return boolVal;
    }

    private Date parseUserCreatedDate(String userCreatedDate) {
        try {
            return userDateFormat.parse(userCreatedDate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            System.out.println("error while parsign DATE : " + e.getMessage());
        }
        return null;
    }

    public Date getCurrentTime() {
        Calendar calendar = new GregorianCalendar(new SimpleTimeZone(0, "UTC"), Locale.US);

        return calendar.getTime();
    }

    public User updateLastLoginDate(User user) {

        // get persistence manager
        PersistenceManager pm = getPersistenceManager();

        try {
            user.setLastLoginDate(getCurrentTime());

            return user;

        } finally {
            pm.close();
        }

    }

    @Override
    public void deleteAllUsers() {
        PersistenceManager pm = getPersistenceManager();

        Query query = pm.newQuery(User.class);

        try {

            query.deletePersistentAll();

        } finally {
            query.closeAll();
            pm.close();
        }

    }

}
