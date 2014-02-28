package ca.diro.UserHandlingUtils;

import org.eclipse.jetty.server.Request;

import ca.diro.UserHandlingUtils.Actions.UserAction;

/**
 * Permission handling for databse access and other operations.
 * 
 * @author girardil
 * @version 1.1
 */
public class UserPermissionHandling {

	public void handleAction(UserAction userAction, Request request) {
		// TODO Check if the given action is allowed to be performed from the
		// given request. If it is, call performAction(request).
	}
	
	private void checkAndHandle(UserAction userAction) {
		
	}
}
