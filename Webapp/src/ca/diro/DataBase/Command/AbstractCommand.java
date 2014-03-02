/**
 * 
 */
package ca.diro.DataBase.Command;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 * This Abstract class allowing to create and encapsulate  query that has to be executed 
 * on the database using inheritance to implement specific query on subclass 
 * 
 * @author william
 */
public abstract class AbstractCommand {

	/**
	 * Constructor
	 */
	public AbstractCommand() {
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
	 public String query_;
	/**
	 * Result getting from database
	 */
	public ResultSet result_;
}
