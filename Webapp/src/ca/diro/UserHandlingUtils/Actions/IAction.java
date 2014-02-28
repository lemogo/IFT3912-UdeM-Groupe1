package ca.diro.UserHandlingUtils.Actions;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

/**
 * The interface for user actions. Every action type that can be performed by a
 * user that requires a permission should implement this interface.
 * 
 * @author lavoiedn
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
	 * Returns the <code>UserPermission</code> required to perform this action.
	 * 
	 * @return The <code>UserPermission</code> required to perform this action.
	 */
	public UserPermission getRequiredUserPermission();

	/**
	 * Applies the <code>IAction</code>'s effects. Note that permission handling
	 * must be done by another class before the <code>IAction</code> is
	 * performed.
	 * 
	 * @param request
	 *            The <code>HttpServletRequest</code> containing the required
	 *            information for this <code>IAction</code>.
	 * @return The <code>ResultSet</code> resulting from the execution of the
	 *         given request.
	 * 
	 * @throws ActionPermissionsException
	 */
	public ResultSet performAction(HttpServletRequest request)
			throws ActionPermissionsException;

}
