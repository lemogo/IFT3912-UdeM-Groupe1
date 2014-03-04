package ca.diro.UserHandlingUtils.Actions;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import ca.diro.UserHandlingUtils.ActionPermissionsException;
import ca.diro.UserHandlingUtils.UserPermissions;

/**
 * Class to represent when a <code>HttpServletRequest</code> did not meet the
 * required permissions to be executed. Useful for a default case, for instance.
 * 
 * @author lavoiedn
 * 
 */
public class UnauthorizedAction extends UserAction {

	/**
	 * Constructor for a <code>UnauthorizedAction</code>. Calls the
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
	public UserPermissions getRequiredUserPermission() {
		return UserPermissions.UNAUTHORIZED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.diro.UserHandlingUtils.Actions.UserAction#performAction(org.eclipse
	 * .jetty.server.Request)
	 */
	@Override
	public ResultSet performAuthorizedAction(String request)
			throws ActionPermissionsException {
		throw new ActionPermissionsException(
				"Unauthorized to perform requested action.");
	}

}
