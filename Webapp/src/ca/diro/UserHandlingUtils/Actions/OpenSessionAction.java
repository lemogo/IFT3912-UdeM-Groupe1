package ca.diro.UserHandlingUtils.Actions;

import ca.diro.DataBase.Command.OpenSession;
import ca.diro.UserHandlingUtils.UserPermissions;

/**
 * The <code>UserAction</code> used for user log in.
 * 
 * @author lavoiedn
 * 
 */
public class OpenSessionAction extends UserAction {

	/**
	 * Constructor for a <code>OpenSessionAction</code>. Calls the
	 * <code>UserAction</code> constructor.
	 * 
	 * 
	 * @param userID
	 *            The ID of the user who initiated this <code>UserAction</code>.
	 * @param targetID
	 *            The ID of the target of this <code>UserAction</code>.
	 * @param JSONRequest
	 *            The JSON request for this <code>UserAction</code>.
	 * 
	 * @see ca.diro.UserHandlingUtils.Actions.UserAction#UserAction(int, int)
	 */
	public OpenSessionAction(int userID, int targetID, String JSONRequest) {
		super(userID, targetID, JSONRequest);
//		associatedCommand = new OpenSession(JSONRequest);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.diro.UserHandlingUtils.Actions.IAction#getRequiredUserPermission()
	 */
	@Override
	public UserPermissions getRequiredUserPermissions() {
		return UserPermissions.REGISTERED_USER;
	}

}
