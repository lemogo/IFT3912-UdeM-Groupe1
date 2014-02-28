package ca.diro.UserHandlingUtils.Actions;

/**
 * The <code>UserAction</code> used for user log in.
 * 
 * @author lavoiedn
 * 
 */
public class OpenSessionAction extends UserAction {

	/**
	 * Constructor for a <code>OpenSessionAction</code>.
	 * 
	 * @param userID
	 *            The ID of the user who initiated this <code>UserAction</code>.
	 * @param targetID
	 *            The ID of the target of this <code>UserAction</code>.
	 */
	public OpenSessionAction(int userID, int targetID) {
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
		return UserPermission.REGISTERED_USER;
	}

}
