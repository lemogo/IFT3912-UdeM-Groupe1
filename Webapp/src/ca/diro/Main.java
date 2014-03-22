package ca.diro;

import java.sql.SQLException;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.session.HashSessionIdManager;
import org.eclipse.jetty.server.session.SessionHandler;

import ca.diro.DataBase.DataBase;
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
//	private static 	ContextHandlerCollection handlerCollection;


	/**
	 * Stores the constant name for the Realm used for login and auth.
	 */
	private static String realmName = "webRealm";
	/**
	 * Stores the path of the Realm's properties.
	 */
	private static String realmProperties = "resources/realm.properties";
	private static DataBase database;

	public static DataBase getDatabase() {
		return database;
	}

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
						// TODO Auto-generated catch block
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
		
		server.setHandler(handlerCollection);
		server.setSessionIdManager(sessionIdManager);
	}

	private static ContextHandlerCollection createHandlerCollection() {
		ContextHandlerCollection handlerCollection = new ContextHandlerCollection();

		ModifyEventHandler modifyEventHandler = new ModifyEventHandler();
		modifyEventHandler.setRewriteRequestURI(true);

		ContextHandler modifyContext = new ContextHandler("/Webapp/modify-event");
		modifyContext.setResourceBase(".");
		modifyContext.setClassLoader(Thread.currentThread().getContextClassLoader());
		modifyContext.setHandler(modifyEventHandler);

		handlerCollection.addHandler(modifyContext);
		SessionHandler session = new SessionHandler();
		session.setHandler(new RequestHandler());
		handlerCollection.addHandler(createContextHandler("", session));
//		handlerCollection.addHandler(createContextHandler("create-event", new CreateEventHandler()));
		handlerCollection.addHandler( createContextHandler("/evenement", new EventHandler()) );
		handlerCollection.addHandler( createContextHandler("/membre", new MemberHandler()) );
		handlerCollection.addHandler( createContextHandler("/liste-des-evenements", new EventListHandler()) );
		handlerCollection.addHandler( createContextHandler("/evenement-modification", new EventModificationPageHandler()) );

		handlerCollection.addHandler(createContextHandler("/deconnexion", new DisconnectUserHandler()));
		handlerCollection.addHandler(createContextHandler("/delete-event", new DeleteEventHandler()));
		handlerCollection.addHandler(createContextHandler("/register-event", new RegisterToEventHandler()));
		handlerCollection.addHandler(createContextHandler("/unregister-event", new UnregisterToEventHandler()));
		handlerCollection.addHandler(createContextHandler("/create-user", new CreateUserHandler()));
		handlerCollection.addHandler(createContextHandler("/modify-user", new ModifyUserInfoHandler()));
//		handlerCollection.addHandler(createContextHandler("connect-user", new ConnectUserHandler()));
		return handlerCollection;
	}

	private static ContextHandler createContextHandler(String contextPath, Handler handler) {
		ContextHandler contextHandler = new ContextHandler("/Webapp"+contextPath);
		contextHandler.setResourceBase(".");
		contextHandler.setClassLoader(Thread.currentThread().getContextClassLoader());
		contextHandler.setHandler(handler);
//		contextHandler.setResourceBase("/Webapp");
//		handler.setOriginalPathAttribute("*/Webapp/");
//		handler.setRewritePathInfo(true);//handler.setRewriteRequestURI(true);
		return contextHandler;
	}

	public static Server getServer() {
		return server;		
	}

}
