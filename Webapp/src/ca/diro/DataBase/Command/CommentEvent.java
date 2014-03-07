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
	 * @param info String 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 */
	public CommentEvent(String info) throws ClassNotFoundException, SQLException {
		query_ = buildQuery(info);	
		keepComment(info) ;
	}
	
	/**
	 * Method to parse String from JSON format in order to retrieve parameters
	 * and build the right query
	 * @param info String Object
	 * @return str <code>String</code> Object which is the query
	 */
	private String buildQuery(String info) {
		String str = "";
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
