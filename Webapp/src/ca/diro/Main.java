package ca.diro;

import java.sql.SQLException;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import ca.diro.DataBase.DataBase;
import ca.diro.RequestHandlingUtil.ConnectUserHandler;
import ca.diro.RequestHandlingUtil.CreateEventHandler;
import ca.diro.RequestHandlingUtil.CreateUserHandler;
import ca.diro.RequestHandlingUtil.DeleteEventHandler;
import ca.diro.RequestHandlingUtil.DisconnectUserHandler;
import ca.diro.RequestHandlingUtil.EventHandler;
import ca.diro.RequestHandlingUtil.EventListHandler;
import ca.diro.RequestHandlingUtil.EventModificationPageHandler;
import ca.diro.RequestHandlingUtil.MemberHandler;
import ca.diro.RequestHandlingUtil.ModifyEventHandler;
import ca.diro.RequestHandlingUtil.ModifyUserInfoHandler;
import ca.diro.RequestHandlingUtil.RegisterToEventHandler;
import ca.diro.RequestHandlingUtil.RequestHandler;
import ca.diro.RequestHandlingUtil.UnregisterToEventHandler;

/**
 * The main class. It serves to initialize the server and its security measures.
 * 
 * @author lavoiedn
 * 
 */
public class Main {

	public final static int DEFAULT_PORT = 8080;
	private static Server server;

	/**
	 * Stores the constant name for the Realm used for login and auth.
	 */
	private static String realmName = "webRealm";
	/**
	 * Stores the path of the Realm's properties.
	 */
	private static String realmProperties = "resources/realm.properties";
	private static DataBase database;

	/**
	 * This is the main. It makes things run.
	 * 
	 * @param args
	 *            Whatever you type in the console as an array of Strings.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		//restoreDatabase();
		database = new DataBase();
//		database.createTables(); // 
//		database.populateTable(); 

		initSecureServer();
		server.start();
		server.join();

		AddShutdownHook hook = new AddShutdownHook();
		hook.attachShutDownHook();
		System.exit(0);
	}

	private static void restoreDatabase() {
		String restore = "DataBaseRestore.sql" ;
		try {
			database = new DataBase(restore);
			database.createTables();
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		database.populateTable();
	}

	public static class AddShutdownHook {
		public void attachShutDownHook(){
			Runtime.getRuntime().addShutdownHook(new Thread() {

				@Override
				public void run() {
					//database.emptyDataBase();
					try {
						database.dbClose();
					} catch (SQLException e) {
						e.printStackTrace();
					}
					database = null;
					
					//TODO:remove all logged users
				}

			});
		}
	}

	/**
	 * Sets up the LoginService and other required security measures for the
	 * current server. The required classes are listed in this class' imports.
	 */
	private static void initSecureServer() {
		ContextHandlerCollection handlerCollection = createHandlerCollection();

		server = new Server(DEFAULT_PORT);
		
		HashSessionIdManager sessionIdManager = new HashSessionIdManager();
		
//		ServletContextHandler servelet = new ServletContextHandler(handlerCollection, "/Webapp", new SessionHandler(), new SecurityHandler)
		server.setHandler(handlerCollection);
		server.setSessionIdManager(sessionIdManager);
	}

	private static ContextHandlerCollection createHandlerCollection() {
		ContextHandlerCollection handlerCollection = new ContextHandlerCollection();

		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);//new ServletContextHandler() ;
		handler.setContextPath("/Webapp");
		handlerCollection.addHandler(  handler );
		
		handler.addServlet(new ServletHolder( new RequestHandler()), "/*");
		handler.addServlet(new ServletHolder( new EventListHandler()), "/liste-des-evenements/*");
		handler.addServlet(new ServletHolder( new EventHandler()), "/evenement/*");
		handler.addServlet(new ServletHolder( new CreateUserHandler()), "/create-user");
		handler.addServlet(new ServletHolder( new MemberHandler()), "/membre/*");
		handler.addServlet(new ServletHolder( new ConnectUserHandler()), "/connect-user");
		handler.addServlet(new ServletHolder( new CreateEventHandler()), "/create-event");

		handler.addServlet(new ServletHolder( new DisconnectUserHandler()), "/deconnexion");
		handler.addServlet(new ServletHolder( new DeleteEventHandler()), "/delete-event");
		handler.addServlet(new ServletHolder( new RegisterToEventHandler()), "/register-event");
		handler.addServlet(new ServletHolder( new UnregisterToEventHandler()), "/unregister-event");
		handler.addServlet(new ServletHolder( new ModifyUserInfoHandler()), "/modify-user");
		handler.addServlet(new ServletHolder( new EventModificationPageHandler()), "/evenement-modification/*");
		handler.addServlet(new ServletHolder( new ModifyEventHandler()), "/modify-event");

		return handlerCollection;
	}

	public static Server getServer() {
		return server;		
	}

	public static DataBase getDatabase() {
		return database;
	}
}
