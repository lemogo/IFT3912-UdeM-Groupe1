/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * This Abstract class allowing to create and encapsulate  query that has to be executed 
 * on the database using inheritance to implement specific query on subclass 
 * 
 * @author william
 */
public abstract class Command {

	/**
	 * Constructor
	 */
	public Command() {
	}
	
	/**
	 * Method to execute query 
	 * @param stat <code>Statement</code> for connection to database
	 * @return true if good connection or false when connection failed.
	 */
	public boolean executeCommand(Statement stat) {
		boolean returnValue = false;
		try {
			if(this.query_ != null){
			result_ = stat.executeQuery(query_);
			returnValue = true;
			}
			else return returnValue ;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return returnValue;
	}
	/**
	 * This method has to be more generic in order to parse Json string in Map type
	 * @return a <code>Map</code> Object with string key and string value 
	 */
	protected Map<String, String> parseToMap(String info){
		//TODO implement generic parsing
		return null ;
	}
		
	/**
	 * getter 
	 * @return result_ <code>ReSultset</code> Object   
	 */
	public String getQuery()
	{
		return query_;
	}
	
	/**
	 * getter 
	 * @return a resultset  
	 */
	public ResultSet getResultSet()
	{
		return result_;
	}

	/**
	 * query which has to be executed
	 */
	 protected String query_;
	/**
	 * Result getting from database
	 */
	protected  ResultSet result_;
}
