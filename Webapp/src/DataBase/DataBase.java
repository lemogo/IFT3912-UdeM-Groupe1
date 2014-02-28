package DataBase;

import java.sql.*;

import DataBase.Command.AbstractCommand;

/**
 * THis class handle all queries in order to connect database by executing queries commands 
 * @author william
 * 
 */
public class DataBase {

	/**
	 * Constructor 
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	
	public DataBase() {
		//TODO database constructor
	}

	/**
	 * méthode de connection à la base de donnée
	 */
	/**
	 * @return a boolean which is true when good connection established
	 */
	public boolean dbConnect() {

		boolean returnValue = false;
		//TODO connection DB
		
		return returnValue;
	}

	/**
	 * fermeture de la base de données
	 * 
	 * @throws SQLException
	 */
	public void dbClose()  {
		//TODO Close DB
	}

	/**
	 * delete data base
	 * 
	 * @throws SQLException
	 */
	public void dbDelete()  {
		//TODO Delete DB
		
	}

	/**
	 * @return un objet Statement
	 * @throws SQLException
	 */
	private Statement statement()  {
		Statement stat = null;
		//TODO statement connection to DB
		return stat;
	}

	/**
	 * Méthode pour créer des tables de la base de données
	 * 
	 * @throws SQLException
	 */
	public void createTable()  {
		//TODO Creation table
	}

	/**
	 * Méthod for insert data in database
	 * 
	 * @throws SQLException
	 */
	public void insertTable()  {
		//TODO inser data populate DB
		
	}

	/**
	 * execute a command with query .
	 * @param cmd la commande
	 * @return true if good connection or false when no connection.
	 * @throws SQLException
	 */
	public boolean execute(AbstractCommand cmd) {
		//TODO execute statement
			return false;
	}	
	
	
	

	/**
	 * connection to database
	 */
	public Connection con = null;
	/**
	 * restore database
	 */
	public String restoreScript = null;
	/**
	 * database name
	 */
	public String dbName = "dbgroup1";
	
	/**
	 * directory for the embedded database 
	 */
	public String dbpath = "jdbc:h2:data/database/dbgroup1";
	/**
	 * data base password
	 */
	public final String dbPassword = "";
	/**
	 * data base user name
	 */
	public final String dbUserName = "group1";
	/**
	 * state of successful transaction 
	 */
	public static final int SUCCESSFUL = 0;
	/**
	 * state of failed transaction 
	 */
	public static final int FAILED = -1;
	/**
	 * constant for recovering database in case of shutdown 
	 */
	public static final String SCRIPT_RESTORE = "DbRestore.sql";

}
