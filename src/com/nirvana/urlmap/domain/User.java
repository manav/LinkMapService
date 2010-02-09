package com.nirvana.urlmap.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.NotPersistent;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;

import com.google.appengine.api.datastore.Key;


@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable="true")
public class User {
	
	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;
	
    @Persistent
    @Unique
    private Long userId;
	
    @Persistent( defaultFetchGroup="true")
    private Set<Long> friendsBatchOne;    

    @Persistent( defaultFetchGroup="true")
    private Set<Long> friendsBatchTwo;    

    @Persistent( defaultFetchGroup="true")
    private Set<Long> friendsBatchThree;    

    @Persistent( defaultFetchGroup="true")
    private Set<Long> friendsBatchFour;    

    @Persistent( defaultFetchGroup="true")
    private Set<Long> friendsBatchFive;    

    @Persistent( defaultFetchGroup="true")
    private Set<Long> friendsBatchSix;    

    @Persistent( defaultFetchGroup="true")
    private Set<Long> friendsBatchSeven;    

    @Persistent( defaultFetchGroup="true")
    private Set<Long> friendsBatchEight;    

    @Persistent( defaultFetchGroup="true")
    private Set<Long> friendsBatchNine;    

    @Persistent( defaultFetchGroup="true")
    private Set<Long> friendsBatchTen;    

    
    @NotPersistent
    private Set<Long> friendsIds;
    
    @Persistent
	private OpenAuthToken openAuthToken ;

    @Persistent
    private String name;
   
    @Persistent
	private String screenName;

    @Persistent
	private String loweredScreenName;
    
    @Persistent
	private String description;
    
    @Persistent
	private String location;
    
    @Persistent
	private String profileImageUrl;
    
    @Persistent
	private String url;
    
    @Persistent
    private boolean isProtected;
    
    @Persistent
    private Integer followersCount;
    
    @Persistent
    private String profileBackgroundColor;
    
    @Persistent
    private String profiletextColor;
    
    @Persistent
    private String profileLinkColor;
    
    @Persistent
    private String profileSidebarBorderColor;
    
    @Persistent
    private String profileSidebarFillColor;
    
    
    @Persistent
    private Integer friendsCount;
    
    @Persistent
    private Date createdAt;
    
    @Persistent
    private Integer favouritesCount;
    
    @Persistent
    private Integer utcOffset;
    
    @Persistent
    private String timeZone;
    
    @Persistent
    private String profileBackgroundImageUrl;
    
    @Persistent
    private Integer statusesCount;
    
    @Persistent
    private boolean notificationEnabled;
    
    @Persistent
    private boolean isFollowing;
    
    @Persistent
    private boolean isVerified;
    
    @Persistent
    private String profileBackgroundTile;
    
    @Persistent
    private Date lastLoginDate;

    @Persistent
    private Date lastSyncDate;

	public User (Long userId,String name, String screenName, Integer friendsCount,  Date createdAt, Integer statusCount, String profileBackgroundImageUrl,
	        String description,boolean notificationEnabled,Integer utcOffset, String profileLinkColor, boolean isFollowing, String profileBackgroundTile,
	        String profileBackgroundColor,boolean isVerified, Integer favouritesCount, Integer followersCount, String url,String profileImageUrl,String profileSidebarFillColor,
	        boolean isProtected,String timeZone,String profileSidebarBorderColor,String location, String profiletextColor){
		this.userId = userId;
		this.name = name;
		this.screenName = screenName;
		this.friendsCount = friendsCount;
		this.createdAt = createdAt;
		this.loweredScreenName = screenName.toLowerCase();
		this.statusesCount = statusCount;
		this.profileBackgroundImageUrl = profileBackgroundImageUrl;
		this.description = description;
		this.notificationEnabled =  notificationEnabled;
		this.utcOffset = utcOffset;
		this.profileLinkColor = profileLinkColor;
		this.isFollowing = isFollowing ;
		this.profileBackgroundTile = profileBackgroundTile;
		this.profileBackgroundColor = profileBackgroundColor;
		this.isVerified = isVerified;
		this.favouritesCount = favouritesCount;
		this.followersCount =followersCount;
		this.url = url;
		this.profileImageUrl= profileImageUrl;
		this.profileSidebarFillColor= profileSidebarFillColor;
		this.isProtected = isProtected;
		this.timeZone = timeZone;
		this.profileSidebarBorderColor = profileSidebarBorderColor;
		this.location = location;
		this.profiletextColor =profiletextColor;
	}
	

	public User(String name, String screenName, String description,
			String location, String profileImageUrl, String url,
			Integer followersCount, String profileBackgroundColor,
			String profileTextColor, String profileLinkColor,
			String profileSidebarBorderColor, Integer friendsCount,
			Date createdAt, Integer utcOffset, String timeZone,
			String profileBackgroundImageUrl,String profileBackgroundTile) {
		
		super();
		this.name = name;
		this.screenName = screenName;
		this.description = description;
		this.location = location;
		this.profileImageUrl = profileImageUrl;
		this.url = url;
		this.followersCount = followersCount;
		this.profileBackgroundColor = profileBackgroundColor;
		this.profiletextColor = profileTextColor;
		this.profileLinkColor = profileLinkColor;
		this.profileSidebarBorderColor = profileSidebarBorderColor;
		this.friendsCount = friendsCount;
		this.createdAt = createdAt;
		this.utcOffset = utcOffset;
		this.timeZone = timeZone;
		this.profileBackgroundImageUrl = profileBackgroundImageUrl;
		this.profileBackgroundTile = profileBackgroundTile;
		this.loweredScreenName = screenName.toLowerCase();

	}

	public User(Long userId,String name, String screenName, String description,
			String location, String profileImageUrl, String url,
			Integer followersCount, String profileBackgroundColor,
			String profileTextColor, String profileLinkColor,
			String profileSidebarBorderColor, Integer friendsCount,
			Date createdAt, Integer utcOffset, String timeZone,
			String profileBackgroundImageUrl,String profileBackgroundTile) {
		
		super();
		this.userId = userId;
		this.name = name;
		this.screenName = screenName;
		this.description = description;
		this.location = location;
		this.profileImageUrl = profileImageUrl;
		this.url = url;
		this.followersCount = followersCount;
		this.profileBackgroundColor = profileBackgroundColor;
		this.profiletextColor = profileTextColor;
		this.profileLinkColor = profileLinkColor;
		this.profileSidebarBorderColor = profileSidebarBorderColor;
		this.friendsCount = friendsCount;
		this.createdAt = createdAt;
		this.utcOffset = utcOffset;
		this.timeZone = timeZone;
		this.profileBackgroundImageUrl = profileBackgroundImageUrl;
		this.profileBackgroundTile = profileBackgroundTile;
		this.loweredScreenName = screenName.toLowerCase();

	}


	public User(Long userId,String name, String screenName) {
		super();
		this.userId = userId;
		this.name = name;
		this.screenName = screenName;
		this.loweredScreenName = screenName.toLowerCase();
	}



	/**
	 * @return the friends
	 */
	public Set<Long> getFriendsIds() {
	    Set<Long> friends = new HashSet<Long>();
	    if(friendsBatchOne !=null)friends.addAll(this.friendsBatchOne);
	    if(friendsBatchTwo !=null)friends.addAll(this.friendsBatchTwo);
	    if(friendsBatchThree !=null)friends.addAll(this.friendsBatchThree);
	    if(friendsBatchFour !=null)friends.addAll(this.friendsBatchFour);
	    if(friendsBatchFive !=null)friends.addAll(this.friendsBatchFive);
	    if(friendsBatchSix !=null)friends.addAll(this.friendsBatchSix);
	    if(friendsBatchSeven !=null)friends.addAll(this.friendsBatchSeven);
	    if(friendsBatchEight !=null)friends.addAll(this.friendsBatchEight);
	    if(friendsBatchNine !=null)friends.addAll(this.friendsBatchNine);
	    if(friendsBatchTen !=null)friends.addAll(this.friendsBatchTen);
	    return friends;
	}


	/**
	 * @param friendsIds the friends to set
	 */
	public void setFriendsIds(Set<Long> friendsIds) {
	    if (this.friendsIds == null) this.friendsIds = new HashSet<Long>();
	    
	    if (friendsIds.size()>1000) {
	        List<Long> friendsIdsList = new LinkedList<Long>(friendsIds);
	        friendsIdsList.addAll(friendsIds);
	        friendsIdsList.subList(1000, friendsIdsList.size()).clear();
	        friendsIds.addAll(friendsIdsList);
	    };
	    
	    //else split the friends collection into 10 different columns
	    int remainder = ((friendsIds.size() % 100)>0)?1:0;
	    int roundedQuotient = friendsIds.size()/100 + remainder;
	    
	    List<Long> friendsIdsList = new LinkedList<Long>(friendsIds);
	    
	    switch (roundedQuotient){
	        
	        case 10:
	            friendsBatchTen = new HashSet<Long>();
	            friendsBatchTen.addAll(friendsIdsList.subList(900,friendsIdsList.size() ));
	            friendsIdsList.subList(900,friendsIdsList.size() ).clear();

	        case 9:
	               friendsBatchNine = new HashSet<Long>();
	               friendsBatchNine.addAll(friendsIdsList.subList(800,friendsIdsList.size()));
	               friendsIdsList.subList( 800,friendsIdsList.size()).clear();
            case 8:
                 friendsBatchEight = new HashSet<Long>();
                 friendsBatchEight.addAll(friendsIdsList.subList(700,friendsIdsList.size()));
                 friendsIdsList.subList(700,friendsIdsList.size()).clear();
            
            case 7:
                 friendsBatchSeven = new HashSet<Long>();
                 friendsBatchSeven.addAll(friendsIdsList.subList(600,friendsIdsList.size()));
                 friendsIdsList.subList(600,friendsIdsList.size()).clear();
            case 6:
                 friendsBatchSix = new HashSet<Long>();
                 friendsBatchSix.addAll(friendsIdsList.subList(500,friendsIdsList.size()));
                 friendsIdsList.subList( 500,friendsIdsList.size()).clear();
            case 5:
                 friendsBatchFive = new HashSet<Long>();
                 friendsBatchFive.addAll(friendsIdsList.subList(400,friendsIdsList.size()));
                 friendsIdsList.subList(400,friendsIdsList.size()).clear();
            case 4:
                 friendsBatchFour = new HashSet<Long>();
                 friendsBatchFour.addAll(friendsIdsList.subList(300,friendsIdsList.size()));
                 friendsIdsList.subList(300,friendsIdsList.size()).clear();
            case 3:
                 friendsBatchThree = new HashSet<Long>();
                 friendsBatchThree.addAll(friendsIdsList.subList(200,friendsIdsList.size()));
                 friendsIdsList.subList(199,friendsIdsList.size()).clear();
            case 2:
                 friendsBatchTwo = new HashSet<Long>();
                 friendsBatchTwo.addAll(friendsIdsList.subList(100,friendsIdsList.size()));
                 friendsIdsList.subList(100,friendsIdsList.size()).clear();
            default:
                 friendsBatchOne = new HashSet<Long>();
                 friendsBatchOne.addAll(friendsIdsList);
                 break;
	    }
	}
	
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}


	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}


	/**
	 * @param profileImageUrl the profileImageUrl to set
	 */
	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}


	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}


	/**
	 * @param isProtected the isProtected to set
	 */
	public void setProtected(boolean isProtected) {
		this.isProtected = isProtected;
	}


	/**
	 * @param followersCount the followersCount to set
	 */
	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
	}


	/**
	 * @param profileBackgroundColor the profileBackgroundColor to set
	 */
	public void setProfileBackgroundColor(String profileBackgroundColor) {
		this.profileBackgroundColor = profileBackgroundColor;
	}


	/**
	 * @param profiletextColor the profiletextColor to set
	 */
	public void setProfiletextColor(String profiletextColor) {
		this.profiletextColor = profiletextColor;
	}


	/**
	 * @param profileLinkColor the profileLinkColor to set
	 */
	public void setProfileLinkColor(String profileLinkColor) {
		this.profileLinkColor = profileLinkColor;
	}


	/**
	 * @param profileSidebarBorderColor the profileSidebarBorderColor to set
	 */
	public void setProfileSidebarBorderColor(String profileSidebarBorderColor) {
		this.profileSidebarBorderColor = profileSidebarBorderColor;
	}


	/**
	 * @param friendsCount the friendsCount to set
	 */
	public void setFriendsCount(Integer friendsCount) {
		this.friendsCount = friendsCount;
	}


	/**
	 * @param createdAt the createdAt to set
	 */
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	/**
	 * @param favouritesCount the favouritesCount to set
	 */
	public void setFavouritesCount(Integer favouritesCount) {
		this.favouritesCount = favouritesCount;
	}


	/**
	 * @param utcOffset the utcOffset to set
	 */
	public void setUtcOffset(Integer utcOffset) {
		this.utcOffset = utcOffset;
	}


	/**
	 * @param timeZone the timeZone to set
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}


	/**
	 * @param profileBackgroundImageUrl the profileBackgroundImageUrl to set
	 */
	public void setProfileBackgroundImageUrl(String profileBackgroundImageUrl) {
		this.profileBackgroundImageUrl = profileBackgroundImageUrl;
	}


	/**
	 * @param statusesCount the statusesCount to set
	 */
	public void setStatusesCount(Integer statusesCount) {
		this.statusesCount = statusesCount;
	}


	/**
	 * @param notificationEnabled the notificationEnabled to set
	 */
	public void setNotificationEnabled(boolean notificationEnabled) {
		this.notificationEnabled = notificationEnabled;
	}


	/**
	 * @param isFollowing the isFollowing to set
	 */
	public void setFollowing(boolean isFollowing) {
		this.isFollowing = isFollowing;
	}


	/**
	 * @param isVerified the isVerified to set
	 */
	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}



	/**
	 * @param openAuthToken the openAuthToken to set
	 */
	public void setOpenAuthToken(OpenAuthToken openAuthToken) {
		this.openAuthToken = openAuthToken;
	}


	/**
	 * @param profileBackgroundTile the profileBackgroundTile to set
	 */
	public void setProfileBackgroundTile(String profileBackgroundTile) {
		this.profileBackgroundTile = profileBackgroundTile;
	}


	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}


	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}


	/**
	 * @return the openAuthToken
	 */
	public OpenAuthToken getOpenAuthToken() {
		return openAuthToken;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @return the friendsCount
	 */
	public Integer getFriendsCount() {
		return friendsCount;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param screenName the screenName to set
	 */
	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	/**
	 * @return the lastLoginDate
	 */
	public Date getLastLoginDate() {
		return lastLoginDate;
	}

	/**
	 * @param lastLoginDate the lastLoginDate to set
	 */
	public void setLastLoginDate(Date lastLoginDate) {
		this.lastLoginDate = lastLoginDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userId == null) ? 0 : userId.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (userId == null) {
			if (other.userId != null)
				return false;
		} else if (!userId.equals(other.userId))
			return false;
		return true;
	}

    /**
     * @return the utcOffset
     */
    public Integer getUtcOffset() {
        return utcOffset;
    }


    /**
     * @return the timeZone
     */
    public String getTimeZone() {
        return timeZone;
    }


    /**
     * @return the lastSyncDate
     */
    public Date getLastSyncDate() {
        return lastSyncDate;
    }


    /**
     * @param lastSyncDate the lastSyncDate to set
     */
    public void setLastSyncDate(Date lastSyncDate) {
        this.lastSyncDate = lastSyncDate;
    }


    /**
     * @param key the key to set
     */
    public void setKey(Key key) {
        this.key = key;
    }
	

}
