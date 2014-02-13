package ca.diro.EventHandling;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ca.diro.UserHandling.User;

/**
 * <code>Event</code> definition class. Made generic to allow easy
 * implementation of many different kinds of event.
 * 
 * @author lavoiedn
 * @version 1.1
 */
public class Event {

	/**
	 * Enum for the purpose of defining the <code>Visibility</code> of an
	 * <code>Event</code>.
	 * 
	 * @author lavoiedn
	 */
	public enum Visibility {
		GuestOnly, Public, Friends;
	}

	/**
	 * Constructor for an <code>Event</code> objet using the <code>User</code>
	 * ID of the creator.
	 * 
	 * @param creatorID
	 *            The <code>User</code> ID of the creator.
	 */
	public Event(long creatorID) {
		this.creatorID = creatorID;
	}

	/**
	 * Constructor for an <code>Event</code> object, using the <code>User</code>
	 * given as argument as its creator.
	 * 
	 * @param creator
	 *            The <code>User</code> who created this event.
	 */
	public Event(User creator) {
		creatorID = creator.getUserID();
	}

	/**
	 * Constructor for an <code>Event</code> objet using the <code>User</code>
	 * ID of the creator, the <code>Event</code>'s <code>visibility</code>, and
	 * its <code>eventType</code>.
	 * 
	 * @param creatorID
	 *            The <code>User</code> ID of the creator. .
	 * @param visibility
	 *            The <code>Visibility</code> of this <code>Event</code>.
	 * @param eventType
	 *            The {@link String} representing the <code>eventType</code> of
	 *            this <code>Event</code>.
	 */
	public Event(long creatorID, Visibility visibility, String eventType) {
		this.creatorID = creatorID;
		this.visibility = visibility;
		this.eventType = eventType;
	}

	/**
	 * Constructor for an <code>Event</code> objet using the <code>User</code>
	 * who created it, the <code>Event</code>'s visibility, and its type.
	 * 
	 * @param creator
	 *            The <code>User</code> who created this event.
	 * @param visibility
	 *            The <code>Visibility</code> of this <code>Event</code>.
	 * @param eventType
	 *            The {@link String} representing the type of this
	 *            <code>Event</code>.représente le type de cet
	 *            <code>Event</code>.
	 */
	public Event(User creator, Visibility visibility, String eventType) {
		creatorID = creator.getUserID();
		this.visibility = visibility;
		this.eventType = eventType;
	}

	/**
	 * Allows the addition of a given <code>User</code> as a guest of this
	 * <code>Event</code>.
	 * 
	 * @param guest
	 *            The <code>User</code> to add to the guest list.
	 * @return <code>True</code> if the <code>User</code> was successfully
	 *         added, else <code>False</code>.
	 */
	public boolean addGuest(User guest) {
		boolean added = true;
		switch (visibility) {
		case Public:
			added = false;
			break;
		case Friends:
			for (User u : guest.getFriendList()) {
				if (u.getUserID() == guest.getUserID()) {
					// TODO: Add new guest to database.
					added = true;
				}
			}
			added = false;
			break;
		case GuestOnly:
			if (isGuest(guest)) {
				added = false;
			} else {
				// TODO: Add new guests to database.
				added = true;
			}
			break;
		default:
			added = false;
		}
		return added;
	}

	/**
	 * Removes the given <code>guest</code> from the guest list if the given
	 * user was a guest and this <code>Event</code> allowed guests.
	 * 
	 * @param guest
	 *            The <code>User</code> to remove from the guest list.
	 * @return <code>true</code> if the <code>User</code> is successfully
	 *         deleted from the guest list, else <code>false</code>.
	 */
	public boolean removeGuest(User guest) {
		boolean deleted = true;
		switch (visibility) {
		case Public:
			deleted = false;
			break;
		default:
			// TODO: Delete guest from DB.
			deleted = false;
		}
		return deleted;
	}

	/**
	 * Sets the <code>Visibility</code> of this event <code>Event</code> to the
	 * given value.
	 * 
	 * @param visibility
	 *            The desired <code>Visibility</code> for this
	 *            <code>Event</code>
	 */
	public void setVisibility(Visibility visibility) {
		this.visibility = visibility;
	}

	/**
	 * Sets the type of this <code>Event</code> to the given value.
	 * 
	 * @param eventType
	 *            The {@link String} representing the desired type for this
	 *            event <code>Event</code>.
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/**
	 * Returns the ID of the <code>User</code> who created this
	 * <code>Event</code>.
	 * 
	 * @return The ID of the <code>User</code> who created this
	 *         <code>Event</code>.
	 */
	public long getCreatorID() {
		return creatorID;
	}

	/**
	 * Returns the <code>Date</code> where this <code>Event</code> will take
	 * place.
	 * 
	 * @return The <code>Date</code> where this <code>Event</code> will take
	 *         place.
	 */
	public Date getDate() {
		Date eventDate = new Date();
		// TODO: Fetch event date from DB.
		return eventDate;
	}

	/**
	 * Returns the {@link String} representing the location where
	 * <code>Event</code> will take place.
	 * 
	 * @return The {@link String} representing the location where
	 *         <code>Event</code> will take place.
	 */
	public String getLocation() {
		// TODO: Fetch location from DB.
		return "";
	}

	/**
	 * Returns the {@link Array} of IDs of the <code>User</code>s invited to
	 * this <code>Event</code>.
	 * 
	 * @return The {@link Array} of IDs of the <code>User</code>s invited to
	 *         this <code>Event</code>.
	 */
	public List<Long> getGuestList() {
		List<Long> guests = new ArrayList<Long>();
		switch (visibility) {
		case Friends:
			for (User u : (new User(creatorID)).getFriendList())
				guests.add(u.getUserID());
			break;
		case Public:
			// TODO: fetch all users from DB.
			/*
			 * Warning, avoid using if possible. Very costly - check if
			 * visibility is Public first.
			 */
			break;
		case GuestOnly:
			// TODO: Fetch guest list from DB.
		}
		return guests;
	}

	/**
	 * Returns <code>True</code> if this <code>User</code> is a guest, else
	 * <code>False</code>.
	 * 
	 * @param u
	 *            The <code>User</code> to verify.
	 * @return <code>True</code> if this <code>User</code> is a guest, else
	 *         <code>False</code>.
	 */
	public boolean isGuest(User u) {
		for (long l : getGuestList()) {
			if (l == u.getUserID())
				return true;
		}
		return false;
	}

	/**
	 * Returns the <code>Visibility</code> of this <code>Event</code>.
	 * 
	 * @return The <code>Visibility</code> of this <code>Event</code>.
	 */
	public Visibility getVisibility() {
		return visibility;
	}

	/**
	 * Returns the <code>eventType</code> of this <code>Event</code>.
	 * 
	 * @return The {@link String} representing the type of this
	 *         <code>Event</code>.
	 */
	public String getEventType() {
		return eventType;
	}

	protected long creatorID;
	protected Visibility visibility = Visibility.GuestOnly;
	protected String eventType = "Not specified";
}
