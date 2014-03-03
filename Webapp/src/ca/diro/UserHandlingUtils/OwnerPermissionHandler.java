package ca.diro.UserHandlingUtils;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import ca.diro.UserHandlingUtils.Actions.UnauthorizedAction;
import ca.diro.UserHandlingUtils.Actions.UserAction;
import ca.diro.UserHandlingUtils.UserPermission;

/**
 * This class will be called by other handlers to handle permission that require
 * the user to be the "Owner" of a certain element, such as an account or event.
 * 
 * @author lavoiedn
 * 
 */
public class OwnerPermissionHandler extends AbstractHandler {

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

	}

	/**
	 * Used internally to return the appropriate <code>UserAction</code>.
	 * 
	 * @return The <code>UserAction</code> associated with this request.
	 */
	private UserAction identifyUserAction() throws ActionPermissionsException {
		return new UnauthorizedAction(0, 0);
	}

}
