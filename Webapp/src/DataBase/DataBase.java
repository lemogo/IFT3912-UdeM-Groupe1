package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.DeleteDbFiles;

import DataBase.Command.AbstractCommand;

/**
 * La classe Database g�re toutes les requ�tes SQL et transactions de l'application avec la base de don�es
 * @author  Groupe1
 *
 */
/**
 * @author william
 * 
 */
public class DataBase {

	/**
	 * Costructeur d�faut
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public DataBase() throws ClassNotFoundException, SQLException {

		Class.forName("org.h2.Driver");
		// this.dbConnect();
	}

	/**
	 * m�thode de connection � la base de donn�e
	 */
	public boolean dbConnect() {

		boolean returnValue = false;

		try {
			this.con = DriverManager.getConnection(dbpath, dbUserName,dbPassword);
			returnValue = true;
		} catch (SQLException e) {

			e.printStackTrace();
			// System.out.println("Error connection");
		}
		// System.out.println("connect� :"+ con) ;

		return returnValue;
	}

	/**
	 * fermeture de la base de donn�es
	 * 
	 * @throws SQLException
	 */
	public void dbClose() throws SQLException {
		this.con.close();
	}

	/**
	 * delete data base
	 * 
	 * @throws SQLException
	 */
	public void dbDelete() throws SQLException {

		// String dbNa = dbName;
		DeleteDbFiles.execute("data/database", dbName, true);
	}

	/**
	 * @return un objet Statement
	 * @throws SQLException
	 */
	private Statement statement() throws SQLException {
		Statement stat = con.createStatement();
		return stat;
	}

	/**
	 * M�thode pour cr�er des tables de la base de donn�es
	 * 
	 * @throws SQLException
	 */
	public void createTable() throws SQLException {
		this.statement().execute(
				"create table test(" + "id int NOT NULL AUTO_INCREMENT,"
						+ " name varchar(255) ," + "PRIMARY KEY (id))");
	}

	/**
	 * M�thode pour ins�rer des donn�es dans la base de donn�es
	 * 
	 * @throws SQLException
	 */
	public void insertTable() throws SQLException {
		this.statement()
				.execute("insert into test values('1', 'Hello World1')");
		this.statement().execute("insert into test values('2','Hello World2')");
	}

	/**
	 * Permet d'executer une commande contenant la requete a effectuer.
	 * @param cmd la commande
	 * @return si l'execution c'est bien deroule.
	 * @throws SQLException
	 */
	public boolean execute(AbstractCommand cmd) throws SQLException{
		//TODO check Query
		if (cmd.getQuery() != null)
		{
			//
			Statement statement = this.statement();
			return cmd.execute(statement);
		}
		else
		{
			return false;
		}
	}	

	/**
	 * attribut de connexion � la base
	 */
	private Connection con = null;
	/**
	 * restauration de la base
	 */
	private String restoreScript = null;
	/**
	 * Nom de la base de donn�e
	 */
	private String dbName = "dbgroup1";
	
	/**
	 * path contenant le fichier de la base de donn�e
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
