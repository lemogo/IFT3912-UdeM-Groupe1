package ca.diro.UserHandlingUtils.Actions;

import java.sql.ResultSet;

import org.eclipse.jetty.server.Request;

/**
 * Class to represent when a <code>Request</code> did not meet the required
 * permissions to be executed.
 * 
 * @author lavoiedn
 * 
 */
public class UnauthorizedAction extends UserAction {

	/**
	 * Constructor for a <code>UnauthorizedAction</code>.
	 * 
	 * @param userID
	 *            The ID of the user who initiated this <code>UserAction</code>.
	 * @param targetID
	 *            The ID of the target of this <code>UserAction</code>.
	 */
	public UnauthorizedAction(int userID, int targetID) {
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
		return UserPermission.UNAUTHORIZED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.diro.UserHandlingUtils.Actions.UserAction#performAction(org.eclipse
	 * .jetty.server.Request)
	 */
	@Override
	public ResultSet performAction(Request request) throws ActionPermissionsException{
		throw new ActionPermissionsException("Unauthorized to perform requested action.");
	}

}
