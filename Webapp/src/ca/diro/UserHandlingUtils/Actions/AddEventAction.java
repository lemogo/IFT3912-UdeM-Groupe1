package ca.diro.UserHandlingUtils.Actions;

/**
 * The <code>UserAction</code> for event creation.
 * 
 * @author lavoiedn
 * 
 */
public class AddEventAction extends UserAction {

	/**
	 * Constructor for a <code>AddEventAction</code>. Calls the
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
	public AddEventAction(int userID, int targetID) {
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
