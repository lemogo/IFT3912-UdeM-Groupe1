package ca.diro.UserHandling;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import ca.diro.EventHandling.Event;

/**
 * <code>AnonymousUser</code> object, made for the sake of having a user with a
 * <code>userID</code> but no login/account.
 * 
 * @author lavoiedn
 * @version 1.2
 */
public class AnonymousUser implements User {

	/**
	 * Basic constructor of the <code>AnonymousUser</code> class.
	 * 
	 * @param userID
	 *            The ID of this <code>AnonymousUser</code>.
	 */
	public AnonymousUser(long userID) {
		this.userID = userID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandling.User#getUserID()
	 */
	public long getUserID() {
		return userID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandling.User#getFullName()
	 */
	public String getFullName() {
		// TODO: Add the DB fetch for this User.
		return "";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandling.User#getAvatarURL()
	 */
	public String getAvatarURL() {
		return DEFAULT_AVATAR_URL;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandling.User#getBirthDate()
	 */
	public Date getBirthDate() {
		// TODO: Add the DB fetch for this User.
		return new Date();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandling.User#getEventCalendar()
	 */
	public List<Event> getEventCalendar() {
		// TODO: Add the DB fetch for this User.
		return new LinkedList<Event>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandling.User#isAnonymous()
	 */
	public boolean isAnonymous() {
		return true;
	}

	protected long userID;
	private final String DEFAULT_AVATAR_URL = "";
}
