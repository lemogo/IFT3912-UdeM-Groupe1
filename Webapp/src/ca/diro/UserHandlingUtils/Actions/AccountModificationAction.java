package ca.diro.UserHandlingUtils.Actions;

import org.eclipse.jetty.server.Request;

/**
 * The <code>UserAction</code> for account modification.
 * 
 * @author lavoiedn
 * 
 */
public class AccountModificationAction extends UserAction {

	/**
	 * Constructor for a <code>AccountModificationAction</code>. Calls the
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
	public AccountModificationAction(int userID, int targetID) {
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
		return UserPermission.RegisteredUser;
	}

	@Override
	public void performAction(Request request) {
		// TODO Modify database entries.

	}

	@Override
	public String getActionCmd() {
		return "";
	}

}
