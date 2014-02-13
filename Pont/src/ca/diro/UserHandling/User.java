package ca.diro.UserHandling;

import java.util.Date;
import java.util.List;

import ca.diro.EventHandling.Event;

/**
 * Interface for user representation.
 * 
 * @author lavoiedn
 * @version 1.0
 */
public interface User {

	/**
	 * Returns this <code>User</code>'s ID.
	 * 
	 * @return The ID of this <code>User</code>.
	 */
	public long getUserID();

	/**
	 * Returns the <code>String</code> representing the URL of this
	 * <code>User</code>'s avatar.
	 * 
	 * @return The <code>String</code> representing the URL of this
	 *         <code>User</code>'s avatar.
	 */
	public String getAvatarURL();

	/**
	 * Returns the <code>User</code>'s full name.
	 * 
	 * @return The <code>String</code> containing this <code>User</code>'s full
	 *         name.
	 */
	public String getFullName();

	/**
	 * Returns the date of birth of this <code>User</code>.
	 * 
	 * @return The date of birth of this <code>User</code>.
	 */
	public Date getBirthDate();

	/**
	 * Returns the "calendar" of the <code>Event</code>s this <code>User</code>
	 * has been invited to.
	 * 
	 * @return The <code>List</code> list of all the <code>Event</code>s this
	 *         <code>User</code> has been invited to.
	 */
	public List<Event> getEventCalendar();

	/**
	 * Returns whether or not this user is anonymous.
	 * 
	 * @return <code>true</code> if this user is anonymous, else
	 *         <code>false</code>.
	 */
	public boolean isAnonymous();
}
