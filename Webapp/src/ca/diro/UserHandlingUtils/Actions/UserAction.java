package ca.diro.UserHandlingUtils.Actions;

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

}
