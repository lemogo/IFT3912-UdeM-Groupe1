package ca.diro.UserHandlingUtils.Actions;

/**
 * Exception thrown when an <code>IAction</code> did not meet the required
 * <code>UserPermissions</code>.
 * 
 * @author lavoiedn
 * 
 */
public class ActionPermissionsException extends Exception {

	private static final long serialVersionUID = 7476076574782124906L;
	/**
	 * The default exception message.
	 */
	private final String DEFAULT_MESSAGE = "This action could not be performed due to unmet permissions restrictions: ";
	/**
	 * The specifics that follow the default exception message.
	 */
	private String specifics = "Unspecified permission exception.";

	/**
	 * Empty constructor.
	 */
	public ActionPermissionsException() {
		super();
	}

	/**
	 * Constructor with specifics.
	 * 
	 * @param msg
	 *            The specifics of this exception.
	 */
	public ActionPermissionsException(final String msg) {
		super(msg);
		specifics = msg;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Throwable#toString()
	 */
	public String toString() {
		return DEFAULT_MESSAGE + specifics;
	}

}
