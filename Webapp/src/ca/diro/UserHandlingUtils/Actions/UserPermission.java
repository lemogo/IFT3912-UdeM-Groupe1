package ca.diro.UserHandlingUtils.Actions;

/**
 * Description of the required permission to perform a certain action.
 * 
 * @author lavoiedn
 * 
 */
public enum UserPermission {
	/**
	 * No one can use this. Alternative to "NONE", equivalent to "null".
	 */
	UNAUTHORIZED,
	/**
	 * Absolute permission. For testing and administration purposes.
	 */
	ADMIN,
	/**
	 * The user must be the owner of an event to alter it.
	 */
	EVENT_OWNER,
	/**
	 * The user must be the owner of a comment to alter it.
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
	 * Returns <code>true</code> if this <code>UserPermission</code> is allowed
	 * to perform an action with the given target <code>UserPermission</code>,
	 * else <code>false</code>.
	 * 
	 * @param targetPermission
	 *            The <code>UserPermission</code> required.
	 * @return <code>true</code> if this <code>UserPermission</code> is allowed
	 *         to perform an action with the given target
	 *         <code>UserPermission</code>, else <code>false</code>.
	 */
	public boolean hasPermission(UserPermission targetPermission) {
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
