package ca.diro;

class Event{
	private String username;
	private String title;
	private String date;
	private String location;
	private String description;
	private String id;
	private String badgeClass;
	
	private int numPeople=0;
	
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

	public int getNumPeople(){
		return numPeople;
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

}