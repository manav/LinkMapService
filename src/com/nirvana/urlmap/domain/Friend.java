package com.nirvana.urlmap.domain;

public class Friend {


    private  Long friendId;
    
    private  String friendsScreenName;
    
    private  String name;
    
    
    
	public Friend(String friendsScreenName, String name,Long friendId) {
		super();
		this.friendId = friendId;
		this.friendsScreenName = friendsScreenName;
		this.name = name;
	}

	public Friend(Long friendsId) {
		this.friendId = friendsId;
	}

	/**
	 * @return the friendId
	 */
	public Long getFriendId() {
		return friendId;
	}

	/**
	 * @param friendId the friendId to set
	 */
	public void setFriendId(Long friendId) {
		this.friendId = friendId;
	}

	/**
	 * @return the friendsScreenName
	 */
	public String getFriendsScreenName() {
		return friendsScreenName;
	}

	/**
	 * @param friendsScreenName the friendsScreenName to set
	 */
	public void setFriendsScreenName(String friendsScreenName) {
		this.friendsScreenName = friendsScreenName;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((friendId == null) ? 0 : friendId.hashCode());
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
		Friend other = (Friend) obj;
		if (friendId == null) {
			if (other.friendId != null)
				return false;
		} else if (!friendId.equals(other.friendId))
			return false;
		return true;
	}

	
	
}
