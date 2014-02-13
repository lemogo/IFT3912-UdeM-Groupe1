package ca.diro.UserHandling;

/**
 * <code>AnonymousUser</code> object, made for the sake of having a user with a
 * <code>userID</code> but no login/account.
 * 
 * @author lavoiedn
 * 
 */
public class AnonymousUser extends User {

	private final String DEFAULT_AVATAR_URL = "";

	public AnonymousUser(long userID) {
		super(userID);
		super.avatarURL = DEFAULT_AVATAR_URL;
	}
}
