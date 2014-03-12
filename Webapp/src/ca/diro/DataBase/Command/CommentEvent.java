/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.ResultSet;
import java.sql.SQLException;

import ca.diro.DataBase.DataBase;

/**
* this class permit to set query in order to allow an signed user to comment event 
 * @author william
 */
public class CommentEvent extends Command{
	/**
	 * @param info string 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public CommentEvent(String info) throws ClassNotFoundException, SQLException {
		keepComment(info) ;
		query_ = buildQuery(info);	
	}
	
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	private String buildQuery(String info) {
		
		//String description = "blablabla" ;
		int  S_userId = Integer.parseInt(info);
		
		String str = "select username, datecreation from  signeduser, commentevent " +
						"where 	signeduser.suserid = "+ S_userId +" and " +
								"signeduser.suserid = commentevent.suserid" ;
		// TODO parse query
		return str;
	}
	/**
	 * This method execute the insertion of comment in the database 
	 * @param info String the description of comment 
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public void keepComment(String info) throws SQLException, ClassNotFoundException{
		
		String desc = "blablabla" ;
		int  S_userId = Integer.parseInt(info);
		int  eventId = Integer.parseInt(info);
		
		 db.statement().executeUpdate("insert into commentevent (description, datecreation, eventid, suserid) " +
				"values("+"'" + desc +"', CURRENT_TIMESTAMP(), "+ eventId + " , "+ S_userId + ")");
		// TODO parse queryinsert
	}
	
	/**
	 * object DataBase 
	 */
	DataBase db = new DataBase() ;
	/**
	 * result giving list of users who have to be notify after a cancelled event 
	 */
	ResultSet result = null ;
}
