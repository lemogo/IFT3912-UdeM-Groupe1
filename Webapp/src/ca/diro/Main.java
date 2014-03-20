package ca.diro;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.rewrite.handler.RedirectPatternRule;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RewritePatternRule;
import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.security.Constraint;

import ca.diro.DataBase.DataBase;
import ca.diro.RequestHandlingUtil.CreateEventHandler;
import ca.diro.RequestHandlingUtil.CreateUserHandler;
import ca.diro.RequestHandlingUtil.DeleteEventHandler;
import ca.diro.RequestHandlingUtil.EventHandler;
import ca.diro.RequestHandlingUtil.EventListHandler;
import ca.diro.RequestHandlingUtil.ConnectUserHandler;
import ca.diro.RequestHandlingUtil.DisconnectUserHandler;
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
	private static 	ContextHandlerCollection handlerCollection;


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
		restoreDatabase();
		AddDatabaseShutdownHook hook = new AddDatabaseShutdownHook();
		hook.attachShutDownHook();

		initSecureServer();
		server.start();
		server.join();

		System.exit(0);
		//		database = new DataBase();
		//		database.dbConnect();
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

	public static class AddDatabaseShutdownHook {
		public void attachShutDownHook(){
			Runtime.getRuntime().addShutdownHook(new Thread() {

				@Override
				public void run() {

					database.emptyDataBase();
					try {
						database.dbClose();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					database = null;
				}

			});
//			System.out.println("Shut Down Hook Attached.");
		}
	}

	/**
	 * Sets up the LoginService and other required security measures for the
	 * current server. The required classes are listed in this class' imports.
	 */
	private static void initSecureServer() {
		handlerCollection = new ContextHandlerCollection();

		ModifyEventHandler modifyEventHandler = new ModifyEventHandler();
		//		modifyEventHandler.setOriginalPathAttribute("*/Webapp/modify-event");
		modifyEventHandler.setRewriteRequestURI(true);

		ContextHandler modifyContext = new ContextHandler("/Webapp/modify-event");
		modifyContext.setResourceBase(".");
		modifyContext.setClassLoader(Thread.currentThread().getContextClassLoader());
		modifyContext.setHandler(modifyEventHandler);

		handlerCollection.addHandler(modifyContext);
		ContextHandler webappContextHandler = createContextHandler("/Webapp","", new RequestHandler());
		handlerCollection.addHandler(webappContextHandler);
		//		handlerCollection.addHandler(createContextHandler("/Webapp/evenement-modification", new RequestHandler()));
		//		handlerCollection.addHandler(createContextHandler("/Webapp", new RequestHandler()));
		handlerCollection.addHandler(createContextHandler("/Webapp/","create-event", new CreateEventHandler()));
		handlerCollection.addHandler( createContextHandler("/Webapp/","evenement", new EventHandler()) );
		handlerCollection.addHandler( createContextHandler("/Webapp/","membre", new MemberHandler()) );
		handlerCollection.addHandler( createContextHandler("/Webapp/","liste-des-evenements", new EventListHandler()) );
		handlerCollection.addHandler( createContextHandler("/Webapp/","evenement-modification", new EventModificationPageHandler()) );

		handlerCollection.addHandler(createContextHandler("/Webapp/","deconnexion", new DisconnectUserHandler()));
		handlerCollection.addHandler(createContextHandler("/Webapp/","delete-event", new DeleteEventHandler()));
		handlerCollection.addHandler(createContextHandler("/Webapp/","register-event", new RegisterToEventHandler()));
		handlerCollection.addHandler(createContextHandler("/Webapp/","unregister-event", new UnregisterToEventHandler()));
		handlerCollection.addHandler(createContextHandler("/Webapp/","create-user", new CreateUserHandler()));
		handlerCollection.addHandler(createContextHandler("/Webapp/","modify-user", new ModifyUserInfoHandler()));
		handlerCollection.addHandler(createContextHandler("/Webapp/","connect-user", new ConnectUserHandler()));

		server = new Server(DEFAULT_PORT);
		server.setHandler(handlerCollection);
	}

	private static ContextHandler createContextHandler(String baseContextPath, String contextPath, RequestHandler handler) {
		ContextHandler contextHandler = new ContextHandler(baseContextPath+contextPath);
		contextHandler.setResourceBase(".");
		contextHandler.setClassLoader(Thread.currentThread().getContextClassLoader());
		contextHandler.setHandler(handler);
		handler.setRewriteRequestURI(true);
		return contextHandler;
	}

	public static Server getServer() {
		return server;		
	}

}
