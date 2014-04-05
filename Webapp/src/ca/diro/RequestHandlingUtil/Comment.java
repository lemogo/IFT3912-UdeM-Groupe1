package ca.diro.RequestHandlingUtil;

public class Comment implements Comparable<Comment>{
	String username, description, commentdate, suserid;

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
		this.commentdate = datecreation;
		this.suserid = suserid;
	}

	public String getUsername() {
		return username;
	}

	public String getDescription() {
		return description;
	}

	public String getCommentdate() {
		return commentdate;
	}

	public String getSuserid() {
		return suserid;
	}

	@Override
	public int compareTo(Comment arg0) {
		// TODO Auto-generated method stub
//		if(commentdate.equals(arg0.getCommentdate()))return 0;
//		if(commentdate.equals(arg0.getCommentdate()))return 0;
		return commentdate.compareTo(arg0.getCommentdate());
	}
	
//	public int equals()
	
}
