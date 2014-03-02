package ca.diro.UserHandlingUtils;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.security.authentication.*;
import org.eclipse.jetty.server.session.*;

import ca.diro.UserHandlingUtils.Actions.UnauthorizedAction;
import ca.diro.UserHandlingUtils.Actions.UserAction;

/**
 * Permission handling for database access and other operations.
 * 
 * @author girardil
 * @version 1.1
 */
public class UserPermissionHandling {

	/**
	 * Performs the given <code>HttpServletRequest</code>.
	 * 
	 * @param request
	 *            The client's <code>HttpServletRequest</code>.
	 * @param response
	 *            The bridge's response to the request.
	 * 
	 * @return The <code>ResultSet</code> that results from this request.
	 * @throws ActionPermissionsException
	 */
	public ResultSet makeRequest(HttpServletRequest request,
			HttpServletResponse response) throws ActionPermissionsException {
		ResultSet results;
		UserAction requestedAction = handleRequestPermissions(request, response);
		results = requestedAction.performAction(request);
		return results;
	}

	/**
	 * Verifies if the <code>HttpServletRequest</code> was made with appropriate
	 * permissions for the command and returns the appropriate
	 * <code>UserAction</code>.
	 * 
	 * @param request
	 *            The client's <code>HttpServletRequest</code>.
	 * @param response
	 *            The bridge's response to the request.
	 * 
	 * @return The requested <code>UserAction</code>.
	 */
	private UserAction handleRequestPermissions(HttpServletRequest request,
			HttpServletResponse response) {
		UserAction requestedAction = new UnauthorizedAction(0, 0);
		// TODO Find requested action and return it.
		return requestedAction;
	}
}
