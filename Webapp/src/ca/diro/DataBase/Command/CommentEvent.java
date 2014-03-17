/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.json.JSONException;

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
	public CommentEvent(String info, DataBase db) throws ClassNotFoundException, SQLException {
		
		myDb = db ;
		try {
			jsonInfo = parseToJson(info);
		} catch (JSONException e) {
			
			e.printStackTrace();
		}
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
	private void keepComment(String info) {
		

		 try {
			 
			String  eventId = jsonInfo.getString("eventId") ;
			String userId = jsonInfo.getString("userId") ;
			String description = jsonInfo.getString("description") ;
			myDb.statement().executeUpdate("insert into commentevent (description, datecreation, eventid, suserid) " +
					"values("+"'" + description +"', CURRENT_TIMESTAMP(), "+ eventId + " , "+ userId + ")");
		 } catch (SQLException e) {
			 System.err.println(e.getMessage());
		}
		 catch (JSONException e1) {
			 	System.err.println(e1.getMessage());
				e1.printStackTrace();
			}
		// TODO parse queryinsert
	}
	
	/**
	 * object DataBase 
	 */
	DataBase myDb ; 
	/**
	 * result giving list of users who have to be notify after a cancelled event 
	 */
	ResultSet result = null ;
}
