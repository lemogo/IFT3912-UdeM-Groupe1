package ca.diro.UserHandlingUtils.Actions;

/**
 * The <code>UserAction</code> for comment creation.
 * 
 * @author lavoiedn
 * 
 */
public class CommentEventAction extends UserAction {

	/**
	 * Constructor for a <code>CommentCreationAction</code>. Calls the
	 * <code>UserAction</code> constructor.
	 * 
	 * 
	 * @param userID
	 *            The ID of the user who initiated this <code>UserAction</code>.
	 * @param targetID
	 *            The ID of the target of this <code>UserAction</code>.
	 * 
	 * @see ca.diro.UserHandlingUtils.Actions.UserAction#UserAction(int, int,
	 *      String)
	 */
	public CommentEventAction(int userID, int targetID) {
		super(userID, targetID);
		// TODO Set command to appropriate AbstractCommand.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandlingUtils.IAction#getRequiredPermission()
	 */
	@Override
	public UserPermission getRequiredUserPermission() {
		// TODO Check CommentActionType before returning required permission.
		return UserPermission.LOGGED_USER;
	}
}
