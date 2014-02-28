package ca.diro.UserHandlingUtils.Actions;

/**
 * Description of the required permission to perform a certain action.
 * 
 * @author lavoiedn
 * 
 */
public enum UserPermission {
	/**
	 * Absolute permission. For testing and administration purposes.
	 */
	Admin,
	/**
	 * The user must be the owner of an event to alter it.
	 */
	EventOwner,
	/**
	 * The user must be the owner of a comment to alter it.
	 */
	CommentOwner,
	/**
	 * We like money so charging users for more permissions is good.
	 */
	Premium,
	/**
	 * The user must be logged in to perform this action.
	 */
	RegisteredUser,
	/**
	 * Any user can perform this action.
	 */
	None;

	/**
	 * Returns <code>true</code> if this <code>UserPermission</code> is allowed
	 *         to perform an action with the given target
	 *         <code>UserPermission</code>, else <code>false</code>.
	 * 
	 * @param targetPermission
	 *            The <code>UserPermission</code> required.
	 * @return <code>true</code> if this <code>UserPermission</code> is allowed
	 *         to perform an action with the given target
	 *         <code>UserPermission</code>, else <code>false</code>.
	 */
	public boolean hasPermission(UserPermission targetPermission) {
		if (this == Admin) {
			return true;
		} else if (targetPermission == EventOwner && this != EventOwner) {
			return false;
		} else if (targetPermission == CommentOwner && this != CommentOwner) {
			return false;
		} else if (this.compareTo(targetPermission) < 0) {
			return true;
		}
		return false;
	}
}
