package ca.diro.UserHandlingUtils.Actions;

import org.eclipse.jetty.server.Request;

/**
 * The <code>UserAction</code> for account creation.
 * 
 * @author lavoiedn
 * 
 */
public class AccountCreationAction extends UserAction {

	/**
	 * Constructor for a <code>AccountCreationAction</code>. Calls the
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
	public AccountCreationAction(int userID, int targetID) {
		super(userID, targetID);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.diro.UserHandlingUtils.Actions.IAction#getRequiredUserPermission()
	 */
	@Override
	public UserPermission getRequiredUserPermission() {
		return UserPermission.None;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandlingUtils.IAction#performAction()
	 */
	@Override
	public void performAction(Request request) {
		// TODO Create a user in the database.
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandlingUtils.Actions.IAction#getActionCmd()
	 */
	@Override
	public String getActionCmd() {
		return "";
	}

}
