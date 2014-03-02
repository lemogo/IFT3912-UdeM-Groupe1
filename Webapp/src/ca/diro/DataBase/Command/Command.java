/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.ResultSet;
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
	 * @param stat statement for connection to database
	 * @return false or true useful to check status 
	 */
	public boolean execute(Statement stat) {
		boolean returnValue = false;
		//TODO execute command
		return returnValue;
	}
	/**
	 * This method has to be more generic in order to parse Json string in Map type
	 * @return a Map with string key and string value 
	 */
	protected Map<String, String> parseToMap(String info){
		//TODO implement generic parsing
		return null ;
	}
		
	/**
	 * getter
	 * @return query to be executed
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
