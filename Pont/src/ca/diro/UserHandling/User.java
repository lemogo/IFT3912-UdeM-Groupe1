package ca.diro.UserHandling;

import java.util.Date;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import ca.diro.EventHandling.Event;

/**
 * Object used for the representation of a <code>User</code>.
 * 
 * @author lavoiedn
 * @version 1.1
 */
public class User {

	/**
	 * Basic constructor of the <code>User</code> class.
	 * 
	 * @param userID
	 *            The ID of this <code>User</code>.
	 */
	public User(long userID) {
		this.userID = userID;
	}

	/**
	 * Returns the ID of this <code>User</code>.
	 * 
	 * @return The ID of this <code>User</code>.
	 */
	public long getUserID() {
		return userID;
	}

	/**
	 * Returns the <code>User</code>'s full name.
	 * 
	 * @return The {@link String} containing this <code>User</code>'s full name.
	 */
	public String getFullName() {
		// TODO: Add the DB fetch for this User.
		return null;
	}

	/**
	 * Returns the date of birth of this <code>User</code>.
	 * 
	 * @return The {@link Date} of birth of this <code>User</code>.
	 */
	public Date getBirthDate() {
		// TODO: Add the DB fetch for this User.
		return new Date();
	}

	/**
	 * Returns the "calendar" of the <code>Event</code>s this <code>User</code>
	 * has been invited to.
	 * 
	 * @return The <code>List</code> list of all the <code>Event</code>s this
	 *         <code>User</code> has been invited to.
	 */
	public List<Event> getEventCalendar() {
		// TODO: Add the DB fetch for this User.
		return null;
	}

	/**
	 * Returns an <code>ImageView</code> containing the avatar of this
	 * <code>User</code>.
	 * 
	 * @return The <code>ImageView</code> containing the avatar of this
	 *         <code>User</code>.
	 */
	public ImageView getAvatar() {
		return new ImageView(new Image(avatarURL));
	}

	/**
	 * Returns the list of this <code>User</code>'s friend.
	 * 
	 * @return The <code>List</code> of all <code>User</code>s who are friends
	 *         with this <code>User</code>.
	 */
	public List<User> getFriendList() {
		// TODO: Add the DB fetch for this User.
		return null;
	}

	/**
	 * Adds the given <code>User</code> to the friend list of this
	 * <code>User</code>.
	 * 
	 * @param friend
	 *            The <code>User</code> to add to this <code>User</code>'s
	 *            friend list.
	 * @return <code>true</code> if the friend was not already a friend of this
	 *         user, else <code>false</code>.
	 */
	public boolean addFriend(User friend) {
		if (isFriend(friend)) {
			return false;
		}
		// TODO: Add user "friend" to friend DB.
		return true;
	}

	/**
	 * Returns whether or not the given <code>User</code> is a friend of this
	 * <code>User</code>.
	 * 
	 * @param f
	 *            The <code>User</code> to test for friendship.
	 * @return <code>true</code> if <code>f</code> is on this <code>User</code>
	 *         's friend list, else <code>false</code>.
	 */
	public boolean isFriend(User f) {
		// TODO: Add a user existence check, either here or in the permission
		// handling, to prevent a possible opening.
		for (User u : getFriendList()) {
			if (u.getUserID() == f.getUserID())
				return true;
		}
		return false;
	}

	protected long userID;
	protected String avatarURL;
}
