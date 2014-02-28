package ca.diro.UserHandling;

import java.util.List;

/**
 * Object used for the representation of a <code>RegisteredUser</code>.
 * 
 * @author lavoiedn
 * @version 1.2
 */
public class RegisteredUser extends AnonymousUser {

	/**
	 * Creates a new <code>RegisteredUser</code> using the given userID.
	 * 
	 * @param userID The userID of this <code>RegisteredUser</code>
	 */
	public RegisteredUser(long userID) {
		super(userID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandling.AnonymousUser#getAvatarURL()
	 */
	@Override
	public String getAvatarURL() {
		// TODO: Add the DB fetch for this User.
		return "";
	}


	/**
	 * Returns the <code>List</code> of <code>User</code> on this
	 * <code>User</code>'s friend list.
	 * 
	 * @return The <code>List</code> of <code>User</code> on this
	 *         <code>User</code>'s friend list.
	 */
	public List<User> getFriendList() {
		// TODO: Add the DB fetch for this User.
		return null;
	}

	/**
	 * Adds the given <code>RegisteredUser</code> to the friend list of this
	 * <code>RegisteredUser</code>.
	 * 
	 * @param friend
	 *            The <code>RegisteredUser</code> to add to this
	 *            <code>RegisteredUser</code>'s friend list.
	 * @return <code>true</code> if the friend was not already a friend of this
	 *         <code>RegisteredUser</code>, else <code>false</code>.
	 */
	public boolean addFriend(RegisteredUser friend) {
		if (isFriend(friend)) {
			return false;
		}
		// TODO: Add user "friend" to friend DB, verify that user exists.
		return true;
	}

	/**
	 * Returns whether or not the given <code>RegisteredUser</code> is a friend
	 * of this <code>RegisteredUser</code>.
	 * 
	 * @param f
	 *            The <code>RegisteredUser</code> to test for friendship.
	 * @return <code>true</code> if <code>f</code> is on this
	 *         <code>RegisteredUser</code> 's friend list, else
	 *         <code>false</code>.
	 */
	public boolean isFriend(RegisteredUser f) {
		// TODO: Add a user existence check, either here or in the permission
		// handling.
		for (User u : getFriendList()) {
			if (u.getUserID() == f.getUserID())
				return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandling.AnonymousUser#isAnonymous()
	 */
	@Override
	public boolean isAnonymous() {
		return false;
	}

}
