package com.nirvana.urlmap.domain;

import javax.jdo.annotations.EmbeddedOnly;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.Unique;

@PersistenceCapable
@EmbeddedOnly
public  class Token {

	@Persistent
	@Unique
	private String tokenId;
	
	@Persistent
	@Unique
	private String tokenSecret;

	public Token(String tokenId, String tokenSecret) {
		super();
		this.tokenId = tokenId;
		this.tokenSecret = tokenSecret;
	}

	/**
	 * @return the tokenId
	 */
	public String getTokenId() {
		return tokenId;
	}

	/**
	 * @return the tokenSecret
	 */
	public String getTokenSecret() {
		return tokenSecret;
	}
	
	
}
