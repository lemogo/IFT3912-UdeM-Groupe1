package ca.diro.UserHandlingUtils;

/**
 * Description of the required permission to perform a certain action.
 * 
 * Do not confuse those with the "roles" given to users. "Roles" only define the
 * different kind of users, these <code>UserPermissions</code> define different
 * states the users can be in, using an enum along with a quick way to compare
 * two values.
 * 
 * @author lavoiedn
 * 
 */
public enum UserPermissions {
	/**
	 * No one can use this. Opposite to "NONE", mostly equivalent to "null".
	 */
	UNAUTHORIZED,
	/**
	 * Absolute permission. For testing and administration purposes.
	 */
	ADMIN,
	/**
	 * The user must be the owner of an event to alter it.
	 * No longer in use now that the database enforces ownership.
	 */
	EVENT_OWNER,
	/**
	 * The user must be the owner of a comment to alter it.
	 * No longer in use now that the database enforces ownership.
	 */
	COMMENT_OWNER,
	/**
	 * We like money so charging users for more permissions is good.
	 */
	PREMIUM,
	/**
	 * The user must be logged in to perform this action.
	 */
	LOGGED_USER,
	/**
	 * The user must have an existing account to perform this action.
	 */
	REGISTERED_USER,
	/**
	 * Any user can perform this action.
	 */
	NONE;

	/**
	 * Returns <code>true</code> if this <code>UserPermissions</code> is allowed
	 * to perform an action with the given target <code>UserPermissions</code>,
	 * else <code>false</code>.
	 * 
	 * @param targetPermission
	 *            The <code>UserPermissions</code> required.
	 * @return <code>true</code> if this <code>UserPermissions</code> is allowed
	 *         to perform an action with the given target
	 *         <code>UserPermissions</code>, else <code>false</code>.
	 */
	public boolean hasPermission(UserPermissions targetPermission) {
		if (this == UNAUTHORIZED) {
			return false;
		} else if (this == ADMIN) {
			return true;
		} else if (targetPermission == EVENT_OWNER && this != EVENT_OWNER) {
			return false;
		} else if (targetPermission == COMMENT_OWNER && this != COMMENT_OWNER) {
			return false;
		} else if (this.compareTo(targetPermission) < 0) {
			return true;
		}
		return false;
	}
}
