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
	 * Executes a query command 
	 * @param stat statement for connection to database
	 * @return false or true useful to check status 
	 */
	public boolean execute(Statement stat) {
		boolean returnValue = false;
		//TODO execute command
		return returnValue;
	}
		
	/**
	 * Gets executed query
	 * @return query to be executed
	 */
	public String getQuery()
	{
		return query_;
	}
	
	/**
	 * Gets result set 
	 * @return a resultset  
	 */
	public ResultSet getResultSet()
	{
		return result_;
	}
	
	//--------------------------------------------------------

	/**
	 * query which has to be executed
	 */
	 protected String query_;
	/**
	 * Result getting from database
	 */
	protected ResultSet result_;
}
