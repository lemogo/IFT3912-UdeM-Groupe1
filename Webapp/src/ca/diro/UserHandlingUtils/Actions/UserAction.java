package ca.diro.UserHandlingUtils.Actions;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import ca.diro.DataBase.Command.AbstractCommand;

/**
 * Represents the actions that can be performed by a user that affect the user
 * itself.
 * 
 * @author lavoiedn
 * 
 */
public abstract class UserAction implements IAction {

	/**
	 * The ID of the user who initiated this <code>IAction</code>.
	 */
	private int userID;
	/**
	 * The ID of the target of this <code>IAction</code>.
	 */
	private int targetID;
	/**
	 * The {@link ca.diro.DataBase.Command.AbstractCommand} associated with this
	 * <code>UserAction</code>.
	 */
	protected AbstractCommand command;

	/**
	 * Constructor for a <code>UserAction</code>.
	 * 
	 * @param userID
	 *            The ID of the user who initiated this <code>UserAction</code>.
	 * @param targetID
	 *            The ID of the target of this <code>UserAction</code>.
	 */
	public UserAction(int userID, int targetID) {
		this.userID = userID;
		this.targetID = targetID;
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
	 * @see
	 * ca.diro.UserHandlingUtils.Actions.IAction#performAction(org.eclipse.jetty
	 * .server.Request)
	 */
	@Override
	public ResultSet performAction(HttpServletRequest request)
			throws ActionPermissionsException {
		ResultSet results = null;
		if (command != null) {
			// TODO Feed the given request to this action's command.
		}
		return results;
	}

}
