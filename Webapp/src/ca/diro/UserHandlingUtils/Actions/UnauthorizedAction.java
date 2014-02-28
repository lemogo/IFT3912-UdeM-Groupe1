package ca.diro.UserHandlingUtils.Actions;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.server.Request;

import ca.diro.UserHandlingUtils.UserPermission;

/**
 * Class to represent when a <code>HttpServletRequest</code> did not meet the required
 * permissions to be executed.
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
	public ResultSet performAction(HttpServletRequest request) throws ActionPermissionsException{
		throw new ActionPermissionsException("Unauthorized to perform requested action.");
	}

}
