package ca.diro.RequestHandlingUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

class Event implements Comparable<Event>{
	private String username;
	private String title;
	private String date;
	private String location;
	private String description;
	private String id;
	private String badgeClass;
	private String numPlacesLeft;
	
	//EventResultSet columns: event.title, event.dateevent, event.location, event.numberplaces, event.description, username, event.suserid
	private static final String DAT_USER = "username";
	private static final String DAT_TITLE = "event.title";
	private static final String DAT_DATE = "event.dateevent";
	private static final String DAT_LOCATION = "event.location";
	private static final String DAT_DESC = "event.description";
	private static final String DAT_ID = "event.suserid";
	private static final String DAT_NUMPLACESLEFT = "event.numberplaces";
	
	public Event(ResultSet eventData, String badgeClass) throws SQLException {

		this.username = eventData.getString(DAT_USER);
		this.title = eventData.getString(DAT_TITLE);
		this.date = eventData.getString(DAT_DATE);
		this.location = eventData.getString(DAT_LOCATION);
		this.description = eventData.getString(DAT_DESC);
		this.id = eventData.getString(DAT_ID);
		this.numPlacesLeft = eventData.getString(DAT_NUMPLACESLEFT);
		this.badgeClass = badgeClass;
	}
	
	public Event(String username, String title, String date,
			String location, String description, String id,
			String badgeClass) {
		this.username = username;
		this.title = title;
		this.date = date;
		this.location = location;
		this.description = description;
		this.id = id;
		this.badgeClass = badgeClass;
	}

	public Event(String username, String title, String date,
			String location, String description, String id,
			String badgeClass, String numPlacesLeft) {
		this.username = username;
		this.title = title;
		this.date = date;
		this.location = location;
		this.description = description;
		this.id = id;
		this.badgeClass = badgeClass;
		this.numPlacesLeft = numPlacesLeft;
	}
	
	public String getUsername() {
		return username;
	}

	public String getTitle() {
		return title;
	}

	public String getDate() {
		return date;
	}

	public String getLocation() {
		return location;
	}

	public String getDescription() {
		return description;
	}

	public String getId() {
		return id;
	}

	public String getBadgeClass() {
		return badgeClass;
	}

	public String getNumPlacesLeft() {
		return numPlacesLeft;
	}
	
	public Map<String,String> toMap() {
		Map<String,String> mapValues = new TreeMap<String,String>();
		mapValues.put("username", username);
		mapValues.put("title", title);
		mapValues.put("date", date);
		mapValues.put("location", location);
		mapValues.put("description", description);
		mapValues.put("id", id);
		mapValues.put("badgeClass", badgeClass);
		mapValues.put("numPlacesLeft", numPlacesLeft);
		return mapValues;
	}

	@Override
	public int compareTo(Event arg0) {
		return date.compareTo(arg0.getDate());
	}
}