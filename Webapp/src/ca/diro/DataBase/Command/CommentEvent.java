/**
 * 
 */
package ca.diro.DataBase.Command;
import java.sql.ResultSet;
import java.sql.SQLException;
import ca.diro.DataBase.DataBase;

/**
* this class permit to set query in order to allow an signed user to comment event 
 * @author William
 */
public class CommentEvent extends Command{
	
	/**
	 * Constructor 
	 * @param eventId : String 
	 * @param userId : String
	 * @param description : String
	 * @param db : DataBase Object 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public CommentEvent(String  eventId,String userId,String description, DataBase db) throws ClassNotFoundException, SQLException {
		
		myDb = db ;
		stroreComment(eventId,userId,description) ;
		query_ = buildQuery(userId, eventId);	
	}
	
	/**
	 * Method to build the right query
	 * @param eventId TODO
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	private String buildQuery(String userId, String eventId) {
		
		String str = "select username, commentevent.description, datecreation from  signeduser, commentevent " +
						"where 	signeduser.suserid = "+ userId +" and " +
								"signeduser.suserid = commentevent.suserid and " +
								"commentevent.eventid = "+ eventId + "" ;

		return str;
	}
	/**
	 * This method execute the insertion of comment in the database 
	 * @param eventId
	 * @param userId
	 * @param description
	 * @return true if execution done well else false 
	 */
	private boolean stroreComment(String  eventId,String userId,String description ) {
		boolean returnValue = false;

		 try {
			 
			myDb.statement().executeUpdate("insert into commentevent (description, datecreation, eventid, suserid) " +
					"values("+"'" + description +"', CURRENT_TIMESTAMP(), "+ eventId + " , "+ userId + ")");
			returnValue = true ;
		 } catch (SQLException e) {
			 System.err.println(e.getMessage());
		}
		 
		 return returnValue ;
	}
	public boolean ListComment(String  eventId) {
		boolean returnValue = false;
		
		 try {
			 
			 String str =" select username, commentevent.description, datecreation from  signeduser, commentevent " +
						"where signeduser.suserid = commentevent.suserid and " +
						"commentevent.eventid = "+ eventId + ""  ;
			 listComment = myDb.statement().executeQuery(str);
			returnValue = true ;
		 } catch (SQLException e) {
			 System.err.println(e.getMessage());
		}
		 
		 return returnValue ;
	}
	
	/**
	 * @return listComment <code>ResultSet</code>
	 */
	public ResultSet getListComment() {
		return listComment;
	}
	
	/**
	 * object DataBase 
	 */
	DataBase myDb ; 
	
	ResultSet listComment = null ;

	
	
}
