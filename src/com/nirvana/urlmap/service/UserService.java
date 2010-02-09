package com.nirvana.urlmap.service;

import java.util.Set;

import com.nirvana.urlmap.domain.User;

public interface UserService {

    public User fetchUser(String screenName);
    
    public User getUser(String screenName);
    
    public User getUser(Long userId);
    
    public Set<Long> fetchFriendIds(String screenName,int friendsCount);
    
    public Set<User> fetchFriends(String screenName, int friendsCount);

    public Set<User> fetchFollowers(String screenName, int followersCount);
    
    public void updateUser(String screenName);
        
    public void addUser(String screenName);
    
    public String getRobotScreenName(String categoryName, String host);

    public void deleteAllUsers();
    

}
