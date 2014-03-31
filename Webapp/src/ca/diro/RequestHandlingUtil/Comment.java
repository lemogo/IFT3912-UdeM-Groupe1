package ca.diro.RequestHandlingUtil;

public class Comment {
	String username, description, datecreation, suserid;

	/**
	 * @param username
	 * @param description
	 * @param datecreation
	 * @param suserid
	 */
	public Comment(String username, String description, String datecreation,
			String suserid) {
		this.username = username;
		this.description = description;
		this.datecreation = datecreation;
		this.suserid = suserid;
	}

	public String getUsername() {
		return username;
	}

	public String getDescription() {
		return description;
	}

	public String getDatecreation() {
		return datecreation;
	}

	public String getSuserid() {
		return suserid;
	}
	
}
