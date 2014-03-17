package ca.diro.DataBase;

//import MustacheHandler;

import java.sql.*;
import java.util.*;

import org.eclipse.jetty.server.Server;
import org.h2.tools.DeleteDbFiles;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import ca.diro.DataBase.Command.*;

/**
 * THis class handle all queries in order to connect database by executing
 * queries commands
 * 
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
		this(DataBase.RESTORE_SCRIPT, false);
		// Class.forName("org.h2.Driver");
	}

	public DataBase(String restoreScript) throws SQLException,
			ClassNotFoundException {
		this(restoreScript, true);
	}

	private DataBase(String restoreScript, boolean inSameDb)
			throws SQLException, ClassNotFoundException {
		Class.forName("org.h2.Driver");

		this.restoreScript = restoreScript;

		if (inSameDb)
			this.dbName = this.sameDbName;// use copy for test
		else
			this.dbName = this.dbFileName;

		this.dbpath = "jdbc:h2:data/database/" + dbName;

		dbConnect();
	}

	/**
	 * Method to get get database connection
	 * 
	 * @return a boolean which is true when good connection established
	 */
	public boolean dbConnect() {

		boolean returnValue = false;

		try {
			this.con = DriverManager.getConnection(dbpath, dbUserName,
					dbPassword);
			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return returnValue;
	}

	/**
	 * Method to close database
	 * 
	 * @throws SQLException
	 */
	public void dbClose() throws SQLException {
		this.con.close();
	}

	/**
	 * Method to delete database
	 * 
	 * @throws SQLException
	 */
	public void dbDelete(String password) throws SQLException {
		if (password.equals("group1")) {
			DeleteDbFiles.execute("data/database", dbName, true);
		}
	}

	/**
	 * Method to get Statement for query
	 * 
	 * @return stat a <code>Statement</code> Object in order to run query
	 * @throws SQLException
	 */
	public Statement statement() throws SQLException {
		this.dbConnect();
		Statement stat = con.createStatement();
		return stat;
	}

	/**
	 * Method to execute a query command on database .
	 * 
	 * @param cmd
	 *            the command
	 * @return true if good connection or false when connection failed.
	 * @throws SQLException
	 */
	public boolean executeDb(Command cmd) throws SQLException {

		if (cmd != null && cmd.getQuery() != null) {

			Statement statement = this.statement();
			return cmd.executeCommand(statement);
		}
		else {
			return false;
		}
	}

	/**
	 * Method to create tables of the database
	 * 
	 * @throws SQLException
	 */

	public void createTables() throws SQLException {

		this.statement().setQueryTimeout(60);

		this.statement().executeUpdate("drop table if exists signeduser");
		this.statement().executeUpdate(
				"create table signeduser ("
						+ "suserId    int   AUTO_INCREMENT, "
						+ "username  varchar(50) NOT NULL , "
						+ "password  varchar(50) NOT NULL ,"
						+ "fullname  varchar(50) NOT NULL, "
						+ "email    varchar(50) NOT NULL ,"
						+ "age  int NOT NULL,"
						+ "description  char(500) NOT NULL, "
						+ "primary key (suserId , email)) ");

		// Account table
		this.statement().executeUpdate("drop table if exists sessionuser");
		this.statement()
				.executeUpdate(
						"create table sessionuser ("
								+ "sessionId    int  AUTO_INCREMENT, "
								+ "datecreation  date NOT NULL , "
								+ "dateclosing  date ,"
								+ "email    varchar(50) NOT NULL ,"
								+ "primary key (sessionId , email),"
								+ "foreign key (email) references signeduser(email) on update cascade ON DELETE CASCADE )");
		// Event table
		this.statement().executeUpdate("drop table if exists event");
		this.statement()
				.executeUpdate(
						"create table event ("
								+ "eventId    int primary key  AUTO_INCREMENT, "
								+ "title  varchar(255) NOT NULL, "
								+ "suserId   int NOT NULL ,"
								+ "dateEvent  datetime  NOT NULL , "
								+ "location  varchar(255) NOT NULL, "
								+ "description  char(500) NOT NULL, "
								// + "datecreation  datetime  , "
								+ "numberplaces   int NOT NULL ,"
								+ "heure  TIME  ,"
								+ "status  varchar(50) default 'running' check(status in('cancelled', 'deleted', 'running'))   ,"
								+ "type  varchar(50) , "
								+ "foreign key (suserId) references signeduser(suserId) on update cascade ON DELETE CASCADE)");
		// comment table
		this.statement().executeUpdate("drop table if exists commentEvent");
		this.statement()
				.executeUpdate(
						"create table commentEvent ("
								+ "commentId    int primary key  AUTO_INCREMENT, "
								+ "description  char(500) NOT NULL, "
								+ "datecreation  datetime  , "
								+ "eventId   int NOT NULL ,"
								+ "suserId   int NOT NULL ,"
								+ "foreign key (eventId) references event(eventId) ON DELETE CASCADE,"
								+ "foreign key (suserId) references signeduser(suserId) on update cascade  ON DELETE CASCADE)");

		// Subsctition table to event
		this.statement()
				.executeUpdate("drop table if exists  subsEventGeneral");
		this.statement()
				.executeUpdate(
						"create table subsEventGeneral("
								+ "eventId   int NOT NULL ,"
								+ "guserId  int  AUTO_INCREMENT,"
								+ "primary key (eventId, guserId ), "
								+ "foreign key (eventId) references event(eventId) ON DELETE CASCADE )");

		this.statement().executeUpdate("drop table if exists  subsEventSigned");
		this.statement()
				.executeUpdate(
						"create table subsEventSigned("
								+ "eventId   int NOT NULL ,"
								+ "suserId   int NOT NULL ,"
								+ "primary key (eventId, suserId), "
								+ "foreign key (eventId) references event(eventId) ON DELETE CASCADE, "
								+ "foreign key (suserId) references signeduser(suserId) on update cascade  ON DELETE CASCADE, )");

	}

	/**
	 * Method to drop all table and set the database empty
	 * 
	 * @return true if good execute else false
	 */
	public boolean emptyDataBase() {
		boolean returnValue = false;
		try {
			Statement gre = this.statement();
			this.statement().executeUpdate("drop table if exists signeduser");
			this.statement().executeUpdate("drop table if exists sessionuser");
			this.statement().executeUpdate("drop table if exists event");
			this.statement().executeUpdate("drop table if exists commentEvent");
			this.statement().executeUpdate(
					"drop table if exists  subsEventGeneral");
			this.statement().executeUpdate(
					"drop table if exists  subsEventSigned");

			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}

		return returnValue;
	}

	/**
	 * Method to populate database
	 * 
	 * @throws SQLException
	 */
	public boolean populateTable() {
		boolean returnValue = false;
		try {

			this.statement()
					.executeUpdate(
							"insert into signeduser (username, password,fullname,email,age,description)"
									+ "values('pat', 'patson','patrice m�chant', 'patrice@mechant.fun', 42,'Je suis un passionn� de jet extreme activit� trop cool')");
			this.statement()
					.executeUpdate(
							"insert into signeduser (username, password,fullname,email,age,description)"
									+ "values('will', 'wilson','willy gentle', 'matresack@ndjeuptien.va', 145,'Jaime faire du patin et tomber mille fois')");
			this.statement()
					.executeUpdate(
							"insert into signeduser (username, password,fullname,email,age,description)"
									+ "values('gil', 'gilson','beau goss', 'beau@ndjeuptien.va', 145,'la natation me passione')");

			this.statement().executeUpdate(
					"insert into sessionuser (datecreation, email ) "
							+ "values(CURRENT_DATE(),'patrice@mechant.fun')");
			this.statement()
					.executeUpdate(
							"insert into sessionuser (datecreation, email ) "
									+ "values(CURRENT_DATE(),'matresack@ndjeuptien.va')");
			this.statement().executeUpdate(
					"insert into sessionuser (datecreation, email ) "
							+ "values(CURRENT_DATE(),'beau@ndjeuptien.va')");

			this.statement()
					.executeUpdate(
							"insert into event (title, suserid, dateevent, location, description, numberplaces, heure, status, type)"
									+ "values('badminton ', 1, '2015-01-01 10:30:00','cote des neiges','venez jouer avec nous', 10,'01:02:00','running', 'sport' )");
			this.statement()
					.executeUpdate(
							"insert into event (title, suserid, dateevent, location, description, numberplaces, heure, status, type)"
									+ "values('course ', 2, '2000-05-15 12:00:00','gatineau','course folle', 8,'04:02:30','cancelled', 'danse' )");
			this.statement()
					.executeUpdate(
							"insert into event (title, suserid, dateevent, location, description, numberplaces, heure, status, type)"
									+ "values('course ', 3, '2002-11-11 08:00:00','gatineau','course folle', 8,'04:02:30','cancelled', 'danse' )");

			this.statement().executeUpdate(
					"insert into commentevent (description, datecreation, eventid, suserid)"
							+ "values('cool comme event', CURRENT_DATE(),1,1)");
			this.statement()
					.executeUpdate(
							"insert into commentevent (description, datecreation, eventid, suserid)"
									+ "values('genial je kiff', CURRENT_TIMESTAMP(), 1,2)");
			this.statement().executeUpdate(
					"insert into commentevent (description, datecreation, eventid, suserid)"
							+ "values('bon timing', CURRENT_DATE(), 2,1)");

			this.statement().executeUpdate(
					"insert into subsEventGeneral (eventId)" + "values(1),"
							+ "(2)," + "(1)");

			this.statement().executeUpdate(
					"insert into subsEventSigned values(1,1)," + "(2,2),"
							+ "(1,2)");

			returnValue = true;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		return returnValue;
	}

	/**
	 * Method to backup the database for security
	 * 
	 * @throws SQLException
	 */
	public void backupDataBase() throws SQLException {

		this.statement().setQueryTimeout(50);
		this.statement().execute(
				"script to 'data/database/" + this.restoreScript + "'");
		this.statement().close();
	}

	/**
	 * Method to restore the database for security
	 * 
	 * @throws SQLException
	 */
	public void restoreDataBase() throws SQLException {

		this.statement().setQueryTimeout(50);
		this.statement().execute(
				"runscript from 'data/database/" + this.restoreScript + "'");
		this.statement().close();
	}

	/**
	 * Method to get description of tables
	 * 
	 * @return setTable a <code>TreeSet</code> Object to keep all objects from
	 *         array
	 * @throws SQLException
	 */
	public Set<String> descTableSet() throws SQLException {

		dbConnect();
		Set<String> tableSet = new TreeSet<String>();
		DatabaseMetaData data = this.con.getMetaData();
		ResultSet rs = data.getTables(null, null, null,
				new String[] { "TABLE" });

		while (rs.next()) {
			String tablename = rs.getString(3);
			// System.out.println(tablename);
			tableSet.add(tablename);
		}

		return tableSet;
	}



	/*public static void main(String[] args) throws Exception {
		DataBase myDb = new DataBase();
		// String restore = "DataBaseRestore.sql" ;
		// DataBase myDb = new DataBase(restore);
		// myDb.dbConnect() ;
		// myDb.dbDelete();

		// myDb.createTables();
		// myDb.populateTable();
		String info = "{eventId:1}";
		Command cmd = new PageInfoEvent(info, myDb);
		boolean boo = myDb.executeDb(cmd);

		ResultSet rs = cmd.getResultSet();

		while (rs.next()) {
			System.out.println(rs.getString(2));
		}

		myDb.dbClose();
	}*/

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
	private String dbName = "";

	/**
	 * directory for the embedded database
	 */
	private String dbpath = "";
	/**
	 * database password
	 */
	private final String dbPassword = "";
	/**
	 * data base user name
	 */
	private String dbUserName = "group1";
	/**
	 * state of successful transaction
	 */
	// public static final int SUCCESSFUL = 0;
	/**
	 * state of failed transaction
	 */
	// public static final int FAILED = -1;
	/**
	 * constant for recovering database for security
	 */
	// public static final String SCRIPT_RESTORE = "DataBaseRestore.sql";
	/**
	 * default constant for recovering database for security
	 */
	private static final String RESTORE_SCRIPT = "restoreDataBase.sql";

	// private String restoreScript = null;
	/**
	 * same database
	 */
	private final String sameDbName = "samedbgroup1";
	/**
	 * Database file name
	 */
	private final String dbFileName = "dbgroup1";

}
