package ca.diro.UserHandling;

/**
 * Permission handling for databse access and other operations.
 * 
 * @author girardil
 * @version 1.1
 */
public class UserPermissionHandling {
	/**
	 * Returns whether or not the given <code>User</code> has 
	 * permissions to access the database
	 * 
	 * @param permsType
	 * 
	 * @return <code>true</code> if this <code>User</code> has 
	 * 		   permission to access database, <code>false</code> otherwise
	 */
	public boolean hasDBPermission(String permsType){
		// TODO
	}
	
	/**
	 * Returns whether or not the given <code>User</code> has 
	 * permissions to create events
	 * 
	 * @param permsType
	 * 
	 * @return <code>true</code> if this <code>User</code> has 
	 * 		   permission to create events, <code>false</code> otherwise
	 */
	public boolean hasEventPermissions(String permsType){
		// TODO
	}
	
	/**
	 * Returns whether or not the given <code>User</code> has 
	 * permissions to view events
	 * 
	 * @param permsType
	 * 
	 * @return <code>true</code> if this <code>User</code> has 
	 * 		   permission to view events, <code>false</code> otherwise
	 */
	public boolean hasEventViewingPermissions(String permsType){
		// TODO
	}
}
