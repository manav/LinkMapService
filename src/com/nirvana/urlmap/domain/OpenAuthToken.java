package com.nirvana.urlmap.domain;

import java.io.Serializable;

import javax.jdo.annotations.Embedded;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.jdo.annotations.Column;
import javax.jdo.annotations.Unique;


import com.google.appengine.api.datastore.Key;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class OpenAuthToken implements Serializable {


	@PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
	private String screenName;
	
	@Persistent
	@Unique
	private String userId;
	
	@Persistent
    @Embedded(members = {
            @Persistent(name="tokenId", columns=@Column(name="requestTokenId")),
            @Persistent(name="tokenSecret", columns=@Column(name="requestTokenSecret")),
        })
    private Token requestToken;

    @Persistent
    @Embedded(members = {
        @Persistent(name="tokenId", columns=@Column(name="accessTokenId")),
        @Persistent(name="tokenSecret", columns=@Column(name="accessTokenSecret")),
    })
    private Token accessToken;

	public OpenAuthToken(String screenName, Token requestToken) {
		super();
		this.screenName = screenName;
		this.requestToken = requestToken;
	}

	/**
	 * @return the requestToken
	 */
	public Token getRequestToken() {
		return requestToken;
	}

	/**
	 * @param requestToken the requestToken to set
	 */
	public void setRequestToken(Token requestToken) {
		this.requestToken = requestToken;
	}

	/**
	 * @return the accessToken
	 */
	public Token getAccessToken() {
		return accessToken;
	}

	/**
	 * @param accessToken the accessToken to set
	 */
	public void setAccessToken(Token accessToken) {
		this.accessToken = accessToken;
	}

	/**
	 * @return the screenName
	 */
	public String getScreenName() {
		return screenName;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
}
