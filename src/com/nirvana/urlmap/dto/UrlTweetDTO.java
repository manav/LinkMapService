package com.nirvana.urlmap.dto;

import java.io.Serializable;


/**
 * A tweet with an url.
 */
public  class UrlTweetDTO implements Serializable{

	/**
	 * The tweet with URL.
	 */
	private String urlTweet;
	
	/**
	 * The tweet with URL.
	 */
	private String screenName;
	
	/**
	 * The tweet with URL.
	 */
	private String name;
	
	private String createdAt;
	
	private Long statusId;

	public UrlTweetDTO(String urlTweet, String screenName,String name, String createdAt,Long statusId) {
		this.urlTweet = urlTweet;
		this.screenName = screenName;
		this.createdAt = createdAt;
		this.name = name;
		this.statusId = statusId;
	}

	public UrlTweetDTO() {
		super();
	}

	/**
	 * @return the urlTweet
	 */
	public String getUrlTweet() {
		return urlTweet;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the createdAt
	 */
	public String getCreatedAt() {
		return createdAt;
	}

	/**
	 * @return the statusId
	 */
	public Long getStatusId() {
		return statusId;
	}


	
}
