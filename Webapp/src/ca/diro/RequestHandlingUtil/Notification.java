package ca.diro.RequestHandlingUtil;

public class Notification {
	String eventid, username, title;

	/**
	 * @param eventid
	 * @param username
	 * @param title
	 */
	public Notification(String eventid, String username, String title) {
		super();
		this.eventid = eventid;
		this.username = username;
		this.title = title;
	}

	public String getEventid() {
		return eventid;
	}

	public String getUsername() {
		return username;
	}

	public String getTitle() {
		return title;
	}
	
}
