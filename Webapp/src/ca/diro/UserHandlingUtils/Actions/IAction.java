package ca.diro.UserHandlingUtils.Actions;

import java.sql.ResultSet;

import ca.diro.UserHandlingUtils.ActionPermissionsException;
import ca.diro.UserHandlingUtils.UserPermissions;

/**
 * The interface for user actions. Every action type that can be performed by a
 * user that requires a permission should implement this interface.
 * 
 * @author lavoiedn
 * @deprecated
 * 
 */
public interface IAction {

	/**
	 * Returns the ID of the target of this <code>IAction</code>.
	 * 
	 * @return The ID of the target of this <code>IAction</code>.
	 */
	public int getTargetID();

	/**
	 * Returns the user ID of the user who initiated this <code>IAction</code>.
	 * 
	 * @return The user ID of the user who initiated this <code>IAction</code>.
	 */
	public int getCallerID();
	
	/**
	 * Returns the JSON request associated with this action.
	 * 
	 * @return The JSON request associated with this action.
	 */
	public String getJSONRequest();

	/**
	 * Returns the <code>UserPermissions</code> required to perform this action.
	 * 
	 * @return The <code>UserPermissions</code> required to perform this action.
	 */
	public UserPermissions getRequiredUserPermissions();

	/**
	 * Applies the <code>IAction</code>'s effects. Note that permission handling
	 * must be done before the <code>IAction</code> is performed.
	 * 
	 * @param request
	 *            The String containing the required information for this
	 *            <code>IAction</code>.
	 * @return The <code>ResultSet</code> resulting from the execution of the
	 *         given request.
	 * 
	 * @throws ActionPermissionsException
	 */
	public ResultSet performAuthorizedAction(String request)
			throws ActionPermissionsException;

}
