package ca.diro.UserHandlingUtils.Actions;

import ca.diro.Main;
import ca.diro.DataBase.Command.AddEvent;
import ca.diro.UserHandlingUtils.UserPermissions;

/**
 * The <code>UserAction</code> for event creation.
 * 
 * @author lavoiedn
 * 
 */
public class AddEventAction extends UserAction {

	/**
	 * Constructor for a <code>AddEventAction</code>. Calls the
	 * <code>UserAction</code> constructor.
	 * 
	 * 
	 * @param userID
	 *            The ID of the user who initiated this <code>UserAction</code>.
	 * @param targetID
	 *            The ID of the target of this <code>UserAction</code>.
	 * @param JSONRequest
	 *            The JSON request for this <code>UserAction</code>.
	 * 
	 * @see ca.diro.UserHandlingUtils.Actions.UserAction#UserAction(int, int)
	 */
	public AddEventAction(int userID, int targetID, String JSONRequest) {
		super(userID, targetID, JSONRequest);
		associatedCommand = new AddEvent(JSONRequest, Main.getDatabase());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ca.diro.UserHandlingUtils.Actions.IAction#getRequiredUserPermission()
	 */
	@Override
	public UserPermissions getRequiredUserPermissions() {
		return UserPermissions.LOGGED_USER;
	}

}
