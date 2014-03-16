package ca.diro;

import java.util.Collections;

import org.eclipse.jetty.security.ConstraintMapping;
import org.eclipse.jetty.security.ConstraintSecurityHandler;
import org.eclipse.jetty.security.HashLoginService;
import org.eclipse.jetty.security.LoginService;
import org.eclipse.jetty.security.authentication.BasicAuthenticator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.util.security.Constraint;

/**
 * The main class. It serves to initialize the server and its security measures.
 * 
 * @author lavoiedn
 * 
 */
public class Main {

	public final static int DEFAULT_PORT = 8080;
	private static Server server;
	private static 	HandlerCollection handlerCollection;


	/**
	 * Stores the constant name for the Realm used for login and auth.
	 */
	private static String realmName = "webRealm";
	/**
	 * Stores the path of the Realm's properties.
	 */
	private static String realmProperties = "resources/realm.properties";

	/**
	 * This is the main. It makes things run.
	 * 
	 * @param args
	 *            Whatever you type in the console as an array of Strings.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		initSecureServer();
		server.start();
		server.join();
	}

	/**
	 * Sets up the LoginService and other required security measures for the
	 * current server. The required classes are listed in this class' imports.
	 */
	private static void initSecureServer() {
		handlerCollection = new HandlerCollection();
		RequestHandler handler = new RequestHandler();
		handlerCollection.addHandler(handler);
//		handlerCollection.addHandler(new HomePageHandler());
		handlerCollection.addHandler(new EventListHandler());
		handlerCollection.addHandler(new CreateEventHandler());
		handlerCollection.addHandler(new ModifyEventHandler());
		handlerCollection.addHandler(new EventHandler());
		handlerCollection.addHandler(new MemberHandler());
		
		server = new Server(DEFAULT_PORT);
		server.setHandler(handlerCollection);
	}

}
