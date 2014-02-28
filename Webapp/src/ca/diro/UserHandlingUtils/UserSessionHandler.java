package ca.diro.UserHandlingUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.SessionManager;
import org.eclipse.jetty.server.session.HashSessionManager;
import org.eclipse.jetty.server.session.SessionHandler;

/**
 * <code>SessionHandler</code> for permissions management.
 * 
 * @author lavoiedn
 *
 */
public class UserSessionHandler extends SessionHandler {
	
	/**
	 * The <code>SessionManager</code> we'll be using.
	 */
	private HashSessionManager sessionManager = new HashSessionManager();
	
	/**
	 * Initializes this <code>SessionHandler</code>.
	 */
	public UserSessionHandler() {
		super();
		initializeSessionManager();
	}
	
	/**
	 * Overrides the constructor since we set our own <code>SessionManager</code>.
	 * 
	 * @param manager The session manager.
	 */
	public UserSessionHandler(SessionManager manager) {
		super();
		initializeSessionManager();
	}

	/**
	 * Initializes the <code>SessionManager</code> for this <code>SessionHandler</code>.
	 */
	private void initializeSessionManager() {
		//TODO Internet magic.
		this.setSessionManager(sessionManager);
	}
}
