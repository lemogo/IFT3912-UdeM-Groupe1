package DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.h2.tools.DeleteDbFiles;

import DataBase.Command.AbstractCommand;

/**
 * La classe Database gère toutes les requêtes SQL et transactions de l'application avec la base de donées
 * @author  Groupe1
 *
 */
/**
 * @author william
 * 
 */
public class DataBase {

	/**
	 * Costructeur défaut
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public DataBase() throws ClassNotFoundException, SQLException {

		Class.forName("org.h2.Driver");
		// this.dbConnect();
	}

	/**
	 * méthode de connection à la base de donnée
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
		// System.out.println("connecté :"+ con) ;

		return returnValue;
	}

	/**
	 * fermeture de la base de données
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
	 * Méthode pour créer des tables de la base de données
	 * 
	 * @throws SQLException
	 */
	public void createTable() throws SQLException {
		this.statement().execute(
				"create table test(" + "id int NOT NULL AUTO_INCREMENT,"
						+ " name varchar(255) ," + "PRIMARY KEY (id))");
	}

	/**
	 * Méthode pour insérer des données dans la base de données
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
	 * attribut de connexion à la base
	 */
	private Connection con = null;
	/**
	 * restauration de la base
	 */
	private String restoreScript = null;
	/**
	 * Nom de la base de donnée
	 */
	private String dbName = "dbgroup1";
	
	/**
	 * path contenant le fichier de la base de donnée
	 */
	private String dbpath = "jdbc:h2:data/database/dbgroup1";
	/**
	 * passeword de la base de données
	 */
	private final String dbPassword = "";
	/**
	 * passeword de la base de données
	 */
	private final String dbUserName = "group1";
	/**
	 * Contante d'état d'une transation réussite
	 */
	public static final int SUCCESSFUL = 0;
	/**
	 * constante d'état d'une transaction échouée
	 */
	public static final int FAILED = -1;
	/**
	 * constante de restauration de la base de donnée en cas de panne
	 */
	private static final String SCRIPT_RESTORE = "DbRestore.sql";

}
