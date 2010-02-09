package com.nirvana.urlmap.domain;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Set;
import java.util.SimpleTimeZone;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Unique;

import com.google.appengine.api.datastore.Key;



@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class UrlTweet implements Serializable {

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private Set<Long> urlVisitors;

    @Persistent
    private String name;
    
    @Persistent
    private String postedByScreenName;

    @Persistent
    private Long postedByUserId;
    
    @Persistent
    private Long redundantUserId;

    @Persistent
	private String tweetWithTinyURL;
	
    @Persistent
    private String explodedURL;

    
    @Persistent
    @Unique
    private Long statusId;

    @Persistent
    private Date createdAt;
    
    @Persistent
    private int createdAtYear;

    @Persistent
    private int createdAtMonth;

    @Persistent
    private int createdAtDay; 
    
	public UrlTweet(String tweetWithTinyURL, Long statusId, Date createdAt,String postedByScreenName,String name,Long redundantUserId) {
		super();
		this.tweetWithTinyURL = tweetWithTinyURL;
		this.statusId = statusId;
		this.createdAt = createdAt;
		this.postedByScreenName = postedByScreenName;
		this.name = name;
		this.redundantUserId = redundantUserId;

		
		Calendar calendar = new GregorianCalendar(new SimpleTimeZone(0, "UTC"), Locale.US);
		calendar.setTime(createdAt);
		
		this.createdAtYear = calendar.get(Calendar.YEAR);
		this.createdAtMonth = calendar.get(Calendar.MONTH);
		this.createdAtDay = calendar.get(Calendar.DAY_OF_MONTH);
		
		
	}
	
	public UrlTweet(){
	}
	

	/**
	 * @return the tweetWithTinyURL
	 */
	public String getTweetWithTinyURL() {
		return tweetWithTinyURL;
	}

	/**
	 * @param tweetWithTinyURL the tweetWithTinyURL to set
	 */
	public void setTweetWithTinyURL(String tweetWithTinyURL) {
		this.tweetWithTinyURL = tweetWithTinyURL;
	}

	/**
	 * @return the postedByScreenName
	 */
	public String getPostedByScreenName() {
		return postedByScreenName;
	}

	/**
	 * @param postedByScreenName the postedByScreenName to set
	 */
	public void setPostedByScreenName(String postedByScreenName) {
		this.postedByScreenName = postedByScreenName;
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
  
	//

	/**
	 * @return the urlVisitors
	 */
	public Set<Long> getUrlVisitors() {
		return urlVisitors;
	}

	/**
	 * @return the postedByUserId
	 */
	public Long getPostedByUserId() {
		return postedByUserId;
	}

	/**
	 * @return the redundantUserId
	 */
	public Long getRedundantUserId() {
		return redundantUserId;
	}

	/**
	 * @return the createdAt
	 */
	public Date getCreatedAt() {
		return createdAt;
	}


	/**
	 * @return the statusId
	 */
	public Long getStatusId() {
		return statusId;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((statusId == null) ? 0 : statusId.hashCode());
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
		UrlTweet other = (UrlTweet) obj;
		if (statusId == null) {
			if (other.statusId != null)
				return false;
		} else if (!statusId.equals(other.statusId))
			return false;
		return true;
	}



    /**
     * @param statusId the statusId to set
     */
    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    /**
     * @param key the key to set
     */
    public void setKey(Key key) {
        this.key = key;
    }


}
