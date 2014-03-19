package ca.diro.UserHandlingUtils.Actions;

import java.sql.SQLException;

import ca.diro.Main;
import ca.diro.DataBase.Command.CommentEvent;
import ca.diro.UserHandlingUtils.UserPermissions;

/**
 * The <code>UserAction</code> for comment creation.
 * 
 * @author lavoiedn
 * 
 */
public class CommentEventAction extends UserAction {

	/**
	 * Constructor for a <code>CommentEventAction</code>. Calls the
	 * <code>UserAction</code> constructor.
	 * 
	 * 
	 * @param userID
	 *            The ID of the user who initiated this <code>UserAction</code>.
	 * @param targetID
	 *            The ID of the target of this <code>UserAction</code>.
	 * @param JSONRequest
	 *            The JSON request for this <code>UserAction</code>.
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @see ca.diro.UserHandlingUtils.Actions.UserAction#UserAction(int, int)
	 */
	public CommentEventAction(int userID, int targetID, String JSONRequest) throws ClassNotFoundException, SQLException {
		super(userID, targetID, JSONRequest);
		associatedCommand = new CommentEvent(JSONRequest, Main.getDatabase());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ca.diro.UserHandlingUtils.IAction#getRequiredPermission()
	 */
	@Override
	public UserPermissions getRequiredUserPermissions() {
		// TODO Check CommentActionType before returning required permission.
		return UserPermissions.LOGGED_USER;
	}
}
