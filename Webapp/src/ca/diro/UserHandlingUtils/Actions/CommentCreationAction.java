package ca.diro.UserHandlingUtils.Actions;

import org.eclipse.jetty.server.Request;

/**
 * The <code>UserAction</code> for comment deletion, modification and creation.
 * 
 * @author lavoiedn
 * 
 */
public class CommentCreationAction extends UserAction {

	/**
	 * Constructor for a <code>CommentAction</code>. Calls the
	 * <code>UserAction</code> constructor.
	 * 
	 * 
	 * @param userID
	 *            The ID of the user who initiated this <code>UserAction</code>.
	 * @param targetID
	 *            The ID of the target of this <code>UserAction</code>.
	 * @param commentActionType
	 *            The <code>CommentActionType</code> of this
	 *            <code>CommentAction</code>.
	 * 
	 * @see ca.diro.UserHandlingUtils.Actions.UserAction#UserAction(int, int,
	 *      String)
	 */
	public CommentCreationAction(int userID, int targetID) {
		super(userID, targetID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandlingUtils.IAction#getRequiredPermission()
	 */
	@Override
	public UserPermission getRequiredUserPermission() {
		// TODO Check CommentActionType before returning required permission.
		return UserPermission.RegisteredUser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandlingUtils.IAction#performAction()
	 */
	@Override
	public void performAction(Request request) {
		// TODO Check permission and perform action.
	}

	@Override
	public String getActionCmd() {
		return "";
	}

}
