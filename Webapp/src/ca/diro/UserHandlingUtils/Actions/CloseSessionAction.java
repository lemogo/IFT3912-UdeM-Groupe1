package ca.diro.UserHandlingUtils.Actions;

import ca.diro.UserHandlingUtils.UserPermission;

/**
 * The <code>UserAction</code> used for user log out.
 * 
 * @author lavoiedn
 * 
 */
public class CloseSessionAction extends UserAction {

	/**
	 * Constructor for a <code>CloseSessionAction</code>. Calls the
	 * <code>UserAction</code> constructor.
	 * 
	 * 
	 * @param userID
	 *            The ID of the user who initiated this <code>UserAction</code>.
	 * @param targetID
	 *            The ID of the target of this <code>UserAction</code>.
	 * 
	 * @see ca.diro.UserHandlingUtils.Actions.UserAction#UserAction(int, int)
	 */
	public CloseSessionAction(int userID, int targetID) {
		super(userID, targetID);
		// TODO Set command to appropriate AbstractCommand.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.diro.UserHandlingUtils.Actions.IAction#getRequiredUserPermission()
	 */
	@Override
	public UserPermission getRequiredUserPermission() {
		return UserPermission.LOGGED_USER;
	}

}