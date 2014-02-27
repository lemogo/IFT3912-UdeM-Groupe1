package ca.diro.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.DeleteDbFiles;

import ca.diro.DataBase.Command.AbstractCommand;


/**
 * THis class handle all query connection to database by receiving command 
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
	public DataBase() throws ClassNotFoundException, SQLException {
		//TODO database constructor
	}

	/**
	 * m�thode de connection � la base de donn�e
	 */
	/**
	 * @return a boolean that is true when good connection established
	 */
	public boolean dbConnect() {

		boolean returnValue = false;
		//TODO connection DB
		
		return returnValue;
	}

	/**
	 * fermeture de la base de donn�es
	 * 
	 * @throws SQLException
	 */
	public void dbClose() throws SQLException {
		//TODO Close DB
	}

	/**
	 * delete data base
	 * 
	 * @throws SQLException
	 */
	public void dbDelete() throws SQLException {
		//TODO Delete DB
		
	}

	/**
	 * @return un objet Statement
	 * @throws SQLException
	 */
	private Statement statement() throws SQLException {
		Statement stat = null;
		//TODO statement connection to DB
		return stat;
	}

	/**
	 * M�thode pour cr�er des tables de la base de donn�es
	 * 
	 * @throws SQLException
	 */
	public void createTable() throws SQLException {
		//TODO Creation table
	}

	/**
	 * M�thod for insert data in database
	 * 
	 * @throws SQLException
	 */
	public void insertTable() throws SQLException {
		//TODO inser data populate DB
		
	}

	/**
	 * execute a command with query .
	 * @param cmd la commande
	 * @return true if good connection or false when no connection.
	 * @throws SQLException
	 */
	public boolean execute(AbstractCommand cmd) throws SQLException{
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
	 * path database 
	 */
	private String dbpath = "jdbc:h2:data/database/dbgroup1";
	/**
	 * passeword de la base de donn�es
	 */
	private final String dbPassword = "";
	/**
	 * passeword de la base de donn�es
	 */
	private final String dbUserName = "group1";
	/**
	 * Contante d'�tat d'une transation r�ussite
	 */
	public static final int SUCCESSFUL = 0;
	/**
	 * constante d'�tat d'une transaction �chou�e
	 */
	public static final int FAILED = -1;
	/**
	 * constante de restauration de la base de donn�e en cas de panne
	 */
	private static final String SCRIPT_RESTORE = "DbRestore.sql";

}
