package ca.diro;

public class User {
	private String username;
	private String fullname;
	private String registeredSince;
	private String age;
	private String description;

	public User(String username, String fullname, String registeredSince,
			String age, String description) {
		this.username = username;
		this.fullname = fullname;
		this.registeredSince = registeredSince;
		this.age = age;
		this.description = description;
	}

	public String getUsername() {
		return username;
	}

	public String getFullname() {
		return fullname;
	}

	public String getRegisteredSince() {
		return registeredSince;
	}

	public String getAge() {
		return age;
	}

	public String getDescription() {
		return description;
	}
	
	
}
