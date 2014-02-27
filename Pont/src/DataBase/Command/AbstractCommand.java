/**
 * 
 */
package DataBase.Command;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

/**
 * Class allowing to create and encapsulate  query that has to be executed on the database.
 * this class also get the query's result
 * @author william
 */
public abstract class AbstractCommand {

	/**
	 * Constructor
	 */
	public AbstractCommand() {
	}
	
	/**
	 * @param stat statement for connection to database
	 * @return false or true useful to check status 
	 */
	public boolean execute(Statement stat) {
		boolean returnValue = false;
		//TODO execute command
		return returnValue;
	}

	
	
	/**
	 * @return query to be executed
	 */
	public String getQuery()
	{
		return query_;
	}
	
	public ResultSet getResultSet()
	{
		return result_;
	}

	/**
	 * query that has to be executed
	 */
	 protected String query_;
	/**
	 * Result getting from database
	 */
	private ResultSet result_;
}