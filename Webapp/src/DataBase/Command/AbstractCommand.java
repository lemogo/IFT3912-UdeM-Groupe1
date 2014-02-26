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
	 * Abstract method be implemented in sub class class
	 * @param userInfo
	 * @return a string that is the query
	 */
	//abstract public String buildQuery(Map<String,String> userInfo);

	/**
	 * @param stat statement for connection to database
	 * @return false or true useful to check status 
	 */
	public boolean execute(Statement stat) {
		boolean returnValue = false;
		try {
			result_ = stat.executeQuery(query_);
			returnValue = true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
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