package ca.diro.DataBase;

import java.sql.*;

import ca.diro.DataBase.Command.Command;

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
	 * Method to get get database connection
	 * @return a boolean which is true when good connection established
	 */
	public boolean dbConnect() {

		boolean returnValue = false;
		//TODO connection DB
		
		return returnValue;
	}

	/**
	 * Mrthod to clase database
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
	 * Method to create tables of the database
	 * 
	 * @throws SQLException
	 */
	public void createTable()  {
		//TODO Creation table
	}

	/**
	 * Method to populate database
	 * 
	 * @throws SQLException
	 */
	public void insertTable()  {
		//TODO inser data populate DB
		
	}

	/**
	 * Method to execute a query  command on database  .
	 * @param cmd la commande
	 * @return true if good connection or false when no connection.
	 * @throws SQLException
	 */
	public boolean execute(Command cmd) {
		//TODO execute statement
			return false;
	}	
	
	
	

	/**
	 * connection to database
	 */
	private Connection con = null;
	/**
	 * restore database
	 */
	private String restoreScript = null;
	/**
	 * database name
	 */
	private String dbName = "dbgroup1";
	
	/**
	 * directory for the embedded database 
	 */
	private String dbpath = "jdbc:h2:data/database/dbgroup1";
	/**
	 * database password
	 */
	private final String dbPassword = "";
	/**
	 * data base user name
	 */
	private  String dbUserName = "group1";
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
