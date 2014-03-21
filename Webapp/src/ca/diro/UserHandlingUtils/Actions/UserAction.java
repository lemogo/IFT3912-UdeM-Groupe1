package ca.diro.UserHandlingUtils.Actions;

import java.sql.ResultSet;

import ca.diro.DataBase.Command.Command;
import ca.diro.UserHandlingUtils.ActionPermissionsException;

/**
 * Represents the actions that can be performed by a user that affect the user
 * itself. This could also have been done using a multitude of
 * <code>AbstractHandler</code>s. However, I find this way cleaner and I wanted
 * a way to call every command using a list of <code>UserAction</code>s if
 * required. (For instance, if we need to use a queue for requests.)
 * 
 * @author lavoiedn
 * @deprecated
 * 
 */
public abstract class UserAction implements IAction {

	/**
	 * The ID of the user who initiated this <code>UserAction</code>.
	 */
	private int userID;
	/**
	 * The ID of the target of this <code>UserAction</code>.
	 */
	private int targetID;

	/**
	 * The JSON request associated with this <code>UserAction</code>.
	 */
	private String JSONRequest;
	/**
	 * The {@link ca.diro.DataBase.Command.Command} associated with this
	 * <code>UserAction</code>.
	 */
	protected Command associatedCommand;

	/**
	 * Constructor for a <code>UserAction</code>.
	 * 
	 * @param userID
	 *            The ID of the user who initiated this <code>UserAction</code>.
	 * @param targetID
	 *            The ID of the target of this <code>UserAction</code>.
	 * @param JSONRequest
	 *            The JSON request for this <code>UserAction</code>.
	 */
	public UserAction(int userID, int targetID, String JSONRequest) {
		this.userID = userID;
		this.targetID = targetID;
		this.JSONRequest = JSONRequest;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandlingUtils.IAction#getUser()
	 */
	@Override
	public int getCallerID() {
		return userID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandlingUtils.IAction#getTarget()
	 */
	@Override
	public int getTargetID() {
		return targetID;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandlingUtils.Actions.IAction#getJSONRequest()
	 */
	@Override
	public String getJSONRequest() {
		return JSONRequest;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.diro.UserHandlingUtils.Actions.IAction#performAction(org.eclipse.jetty
	 * .server.Request)
	 */
	@Override
	public ResultSet performAuthorizedAction(String request)
			throws ActionPermissionsException {
		ResultSet results = null;
		if (associatedCommand != null) {
			// TODO Feed the given request to this action's command.
		}
		return results;
	}

}
