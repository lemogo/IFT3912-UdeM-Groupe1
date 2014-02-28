package ca.diro.UserHandlingUtils.Actions;

import org.eclipse.jetty.server.Request;

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
	 *            The request containing the required information for this
	 *            <code>IAction</code>.
	 */
	public void performAction(Request request);

	/**
	 * Returns this <code>IAction</code>'s request command.
	 * 
	 * @return This <code>IAction</code>'s request command.
	 */
	public String getActionCmd();

}
