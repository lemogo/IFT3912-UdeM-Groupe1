package ca.diro.UserHandlingUtils.Actions;

import ca.diro.UserHandlingUtils.UserPermissions;

/**
 * The <code>UserAction</code> for event deletion.
 * 
 * @author lavoiedn
 * 
 */
public class CancelEventAction extends UserAction {

	/**
	 * Constructor for a <code>CancelEventAction</code>. Calls the
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
	public CancelEventAction(int userID, int targetID) {
		super(userID, targetID);
		// TODO Set command to the appropriate AbstractCommand.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.diro.UserHandlingUtils.Actions.IAction#getRequiredUserPermission()
	 */
	@Override
	public UserPermissions getRequiredUserPermissions() {
		return UserPermissions.EVENT_OWNER;
	}

}
