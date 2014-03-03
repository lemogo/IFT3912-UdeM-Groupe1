package ca.diro.UserHandlingUtils;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.security.authentication.*;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.server.session.*;
import org.json.JSONArray;

import ca.diro.UserHandlingUtils.Actions.UnauthorizedAction;
import ca.diro.UserHandlingUtils.Actions.UserAction;
import ca.diro.UserHandlingUtils.UserPermission;

/**
 * Permission handling for database access and other operations.
 * Will handle requests forwarded from {@link ca.diro.RequestHandler}.
 * 
 * @author girardil
 * @version 1.1
 */
public class UserPermissionHandler extends AbstractHandler{
	
	/**
	 * The <code>ResultSet</code> of a database query.
	 */
	private ResultSet resultSet;
	/**
	 * The <code>resultSet</code> as a <code>JSONArray</code>.
	 */
	private JSONArray JSONResult;

	/* (non-Javadoc)
	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String, org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Handle requests that require particular permission handling.
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
			HttpServletResponse response) throws ActionPermissionsException {
		UserAction requestedAction = new UnauthorizedAction(0, 0);
		// TODO Find requested action and return it.
		return requestedAction;
	}


}