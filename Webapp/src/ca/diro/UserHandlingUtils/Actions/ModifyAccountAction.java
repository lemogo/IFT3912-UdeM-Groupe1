package ca.diro.UserHandlingUtils.Actions;

import ca.diro.Main;
import ca.diro.DataBase.Command.ModifyAccount;
import ca.diro.UserHandlingUtils.UserPermissions;

/**
 * The <code>UserAction</code> for account modification.
 * 
 * @author lavoiedn
 * 
 */
public class ModifyAccountAction extends UserAction {

	/**
	 * Constructor for a <code>ModifyAccountAction</code>. Calls the
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
	public ModifyAccountAction(int userID, int targetID, String JSONRequest) {
		super(userID, targetID, JSONRequest);
//		associatedCommand = new ModifyAccount(JSONRequest, Main.getDatabase());
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
