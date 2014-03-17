package ca.diro.UserHandlingUtils;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.json.JSONArray;

import ca.diro.UserHandlingUtils.Actions.UnauthorizedAction;
import ca.diro.UserHandlingUtils.Actions.UserAction;
import ca.diro.UserHandlingUtils.UserPermissions;

/**
 * This class will be called by other handlers to handle permission that require
 * the user to be the "Owner" of a certain element, such as an account or event.
 * Will handle requests forwarded from
 * {@link ca.diro.UserHandlingUtils.UserPermissionsHandler}.
 * 
 * @author lavoiedn
 * 
 */
public class OwnerPermissionsHandler extends AbstractHandler {

	/**
	 * The <code>ResultSet</code> of a database query.
	 */
	private ResultSet resultSet;
	/**
	 * The <code>resultSet</code> as a <code>JSONArray</code>.
	 */
	private JSONArray JSONResult;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
	 * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO: Handle requests that require ownership.
	}

	/**
	 * Used internally to return the appropriate <code>UserAction</code>.
	 * 
	 * @return The <code>UserAction</code> associated with this request.
	 */
	private UserAction identifyUserAction() throws ActionPermissionsException {
		// TODO: Implement UserAction identification.
		return new UnauthorizedAction(0, 0, "");
	}

}
