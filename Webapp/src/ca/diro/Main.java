package ca.diro;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
	public static ContextHandler reauestContext;
	public static ContextHandler eventContext;

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
		
		handlerCollection = new ContextHandlerCollection();
		ContextHandler modifyContext = new ContextHandler("/Webapp/modify-event");
		modifyContext.setResourceBase(".");
		modifyContext.setClassLoader(Thread.currentThread().getContextClassLoader());
		modifyContext.setHandler(new ModifyEventHandler());

		eventContext = new ContextHandler("/Webapp/evenement");
		eventContext.setResourceBase(".");
		eventContext.setClassLoader(Thread.currentThread().getContextClassLoader());
		eventContext.setHandler(new EventHandler());

		reauestContext = new ContextHandler("/Webapp");
		reauestContext.setResourceBase(".");
		reauestContext.setClassLoader(Thread.currentThread().getContextClassLoader());
		reauestContext.setHandler(new RequestHandler());
		
		handlerCollection.addHandler(reauestContext);
		handlerCollection.addHandler(modifyContext);
		handlerCollection.addHandler(eventContext);
        
		server = new Server(DEFAULT_PORT);
		server.setHandler(handlerCollection);
	}

}
