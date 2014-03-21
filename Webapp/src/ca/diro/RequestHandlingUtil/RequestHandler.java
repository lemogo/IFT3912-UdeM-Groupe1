package ca.diro.RequestHandlingUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.eclipse.jetty.rewrite.handler.RedirectPatternRule;
import org.eclipse.jetty.rewrite.handler.RewriteHandler;
import org.eclipse.jetty.rewrite.handler.RuleContainer;
import org.eclipse.jetty.server.Request;
import org.json.JSONArray;

import ca.diro.Main;
import ca.diro.DataBase.DataBase;
import ca.diro.DataBase.Command.AddEvent;
import ca.diro.DataBase.Command.CreateUserAccount;
import ca.diro.DataBase.Command.OpenSession;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

/**
 * Handler for the Jetty server.
 * 
 * @author girardil, lavoiedn
 * @version 1.0
 */
public class RequestHandler extends RewriteHandler {

	/**
	 * The list of supported commands in requests.
	 */
	private final static String[] SUPPORTED_COMMANDS = {};
	/**
	 * The <code>ResultSet</code> of a database query.
	 */
	private ResultSet resultSet;
	/**
	 * The <code>resultSet</code> as a <code>JSONArray</code>.
	 */
	private JSONArray JSONResult;


	// La racine des ressources a presenter
	private static File	rootDir		= new File(".");

	// Ressources statiques -> ne seront pas interpretees par Mustache
	protected static File	staticDir	= new File(rootDir, "./static");

	// Ressources dynamiques -> seront interpretees par Mustache
	protected static File	dynamicDir	= new File(rootDir, "templates");

	protected RedirectPatternRule redirect;

	public RequestHandler() {
		super();
		redirect = new RedirectPatternRule();
		this.addRule(redirect);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
	 * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		// TODO Implement handling logic for simple requests (and command
		// validation) and forwarding for requests that require specific
		// permissions or handling.
		try
		{
			System.out.println("In requestHandler\t"+baseRequest.getMethod()+"\ttarget:"+target+"\t"+baseRequest.getPathInfo()+"\tContext:"+request.getContextPath());

			String pathInfo = request.getPathInfo();
			if(pathInfo.startsWith("/"))pathInfo = pathInfo.substring(1);

			if(pathInfo.length()<=1){
				baseRequest.setHandled(true);
				return;
			}
			if ( (request.getContextPath().equalsIgnoreCase("/Webapp")&&target.equals("/") )||pathInfo.equals("accueil")) pathInfo = "accueil.html";
			else if ( pathInfo.equals("enregistrement") || pathInfo.equals("ajouter-un-evenement") 
					||pathInfo.equals("notifications")||pathInfo.equals("connexion")) pathInfo = pathInfo+".html";
			else if(isAnotherContext(pathInfo)){
				redirectToPathContext(target, baseRequest, request, response, pathInfo);
			}

			// create a handle to the resource
			File staticResource = new File(staticDir, pathInfo);
			File dynamicResource = new File(dynamicDir, pathInfo);

			setResponseContentType(target, response);
			response.setCharacterEncoding("utf-8");

			String filename = pathInfo;

			// Ressource existe
			if(pathInfo.startsWith("https://")){
				response.setStatus(HttpServletResponse.SC_OK);
				copy(staticResource, response.getOutputStream());
			}
			else if (!staticResource.exists() && !dynamicResource.exists())
			{
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				processTemplate(request, response, "404.html");
			}
			else if (staticResource.exists())
			{
				response.setStatus(HttpServletResponse.SC_OK);
				copy(staticResource, response.getOutputStream());
			}
			else
			{
				response.setStatus(HttpServletResponse.SC_OK);
				processTemplate(request, response, "header.html");

				//to display user navbar
				HashMap<String, Object> sources = new HashMap<String, Object>();
				sources.put("user", "false");

				processTemplate(request, response, filename,sources);
				processTemplate(request, response, "footer.html");
			}

			baseRequest.setHandled(true);
		}
		catch (Exception e)
		{
			catchHelper(baseRequest, request, response, e);
		}

	}

	protected void setResponseContentType(String target, HttpServletResponse response) {
		// A changer pour supporter des images, peut-etre par extension ou
		// par repertoire
		if (target.endsWith(".css"))
		{
			response.setContentType("text/css");
		}
		else if (target.endsWith(".js"))
		{
			response.setContentType("text/javascript");
		}
		else if (target.endsWith(".png"))
		{
			response.setContentType("image/png");
		}
		else if (target.endsWith(".jpg"))
		{
			response.setContentType("image/jpg");
		}
		else if (target.endsWith(".ico"))
		{
			response.setContentType("image/x-icon");
		}
		else if (target.endsWith(".ttf"))
		{
			response.setContentType("application/x-font-ttf");
		}
		else if (target.endsWith(".woff"))
		{
			response.setContentType("application/x-font-woff");
		}
		else
		{
			response.setContentType("text/html");
		}
	}

	protected void catchHelper(Request baseRequest, HttpServletRequest request,
			HttpServletResponse response, Exception e) throws IOException,
			UnsupportedEncodingException, FileNotFoundException {
		// Pour deboggage, on va afficher le stacktrace
		Map<String, String> params = new HashMap<String, String>();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		PrintStream pout = new PrintStream(out);
		e.printStackTrace(pout);
		params.put("stacktrace", out.toString());
		out.close();

		// Template d'erreur
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		processTemplate(request, response, "500.html", params);
		baseRequest.setHandled(true);
	}

	protected void redirectRequest(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response,
			String setPattern, String setLocation) throws IOException,
			ServletException {
		if (isStarted())
		{
			redirect.setPattern(setPattern);
			redirect.setLocation(setLocation);  
			RuleContainer _rules = new RuleContainer();
			_rules.setRules(this.getRules());
			String returned = _rules.matchAndApply(target, request, response);
			target = (returned == null) ? target : returned;

			if (!baseRequest.isHandled())
				super.handle(target, baseRequest, request, response);
		}
	}


	/**
	 * Utility method. Returns the ResultSet as a JSONArray, to be converted
	 * into a String for the response.
	 * 
	 * @return The ResultSet as a JSONArray.
	 */
	private JSONArray resultSetToJSON() {
		// TODO Convert ResulSet to workable JSONArray.
		// The result could also be stored in the class attribute "JSONResult"
		// instead of being returned.
		return JSONResult;
	}


	/**
	 * 
	 * @param staticres
	 *            non null, existing file, not checked here
	 * @param outputStream
	 *            non null
	 * @throws IOException
	 *             if there is a copy error
	 */
	private void copy(File staticres, OutputStream outputStream) throws IOException
	{
		FileInputStream fis = new FileInputStream(staticres);
		copy(fis, outputStream);
		fis.close();
	}

	/**
	 * @param in
	 *            non null input stream
	 * @param out
	 *            non null output stream
	 * @return the number of bytes
	 * @throws IOException
	 *             if underlying error arises
	 */
	private long copy(InputStream in, OutputStream out) throws IOException
	{
		long bytes = 0;
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);
		bytes += len;
		while (len != -1)
		{
			out.write(buffer, 0, len);
			len = in.read(buffer);
			bytes += len;
		}
		return bytes;
	}

	/**
	 * Delegates template execution
	 * 
	 * @param req
	 *            sent by the http container
	 * @param res
	 *            sent by the http container
	 * @param filename
	 *            , identifies the template used
	 * @param scopes
	 *            , extra parameters set by caller
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	@SuppressWarnings("serial")
	protected void processTemplate(HttpServletRequest req, HttpServletResponse res, String filename, Object... scopes) throws UnsupportedEncodingException, FileNotFoundException, IOException
	{
		MustacheFactory mc = new DefaultMustacheFactory(dynamicDir);
		mc = new DefaultMustacheFactory(dynamicDir);
		Mustache mustache = mc.compile(filename);

		Map<Object, Object> parameters = new HashMap<Object, Object>(req.getParameterMap())
				{
			@Override
			public Object get(Object o)
			{
				Object result = super.get(o);
				if (result instanceof String[])
				{
					String[] strings = (String[]) result;
					if (strings.length == 1)
					{
						return strings[0];
					}
				}
				return result;
			}
				};
				List<Object> scs = new ArrayList<Object>();
				scs.add(parameters);
				for (Object o : scopes)
				{
					scs.add(o);
				}
				mustache.execute(res.getWriter(), scs.toArray());
	}


	private String simpleEscape(String string){
		return string.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	protected void redirectToPathContext(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response,
			String path) throws IOException, ServletException {
		System.out.println("in Event redirect- pathInfo:"+path);
		String setPattern = "*/"+path;
		String setLocation = "/Webapp/"+path;//+"*";

		if(path.startsWith("create-event")){
			handleCreate(path, baseRequest, request, response);
			return;
		}
		if(path.startsWith("connect-user")){
			handleConnectUser(path, baseRequest, request, response);
			return;
		}

		redirectRequest(target, baseRequest, request, response, setPattern,
				setLocation);

	}

	protected boolean isAnotherContext(String pathInfo) {
		return pathInfo.equals("accueil")||pathInfo.startsWith("liste-des-evenements")||pathInfo.startsWith("modifier-un-evenement")
				||pathInfo.startsWith("membre")||pathInfo.equals("notifications")||pathInfo.equals("connexion")||pathInfo.equals("deconnexion")
				||pathInfo.equals("enregistrement")||pathInfo.startsWith("ajouter-un-evenement")
				||pathInfo.equals("evenement")||pathInfo.startsWith("evenement/")
				||pathInfo.equals("deconnexion")||pathInfo.startsWith("evenement-modification/")||pathInfo.startsWith("modify-event")||pathInfo.startsWith("delete-event")
				||pathInfo.equals("register-event")||pathInfo.startsWith("unregister-event")||pathInfo.equals("connect-user")||pathInfo.equals("create-user")
				||pathInfo.startsWith("create-event")
				;
	}

	public void handleCreate(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		// TODO Implement handling logic for simple requests (and command
		// validation) and forwarding for requests that require specific
		// permissions or handling.
		try
		{

			String pathInfo = request.getPathInfo().substring(1);

			String userId = "";//request.getParameter("id");
			String title = request.getParameter("eventName");
			String date = request.getParameter("eventDate");
			String location = request.getParameter("eventLocation");
			String nbplace = request.getParameter("eventNumPeople");
			String description = request.getParameter("eventDescription");

			System.out.println("\nIn create event - Parameters:"
					+pathInfo
					//					+ request.getParameterNames()					//);
					+"\t"+userId
					+"\t"+title
					+"\t"+date
					+"\t"+location
					+"\t"+nbplace
					+"\t"+description);


			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")) {
				super.handle(target, baseRequest, request, response);
				return;
			}

			//				System.out.println("before database call");

			//TODO: Add the event to the database	
			DataBase db = Main.getDatabase();//new DataBase(restore);	
			AddEvent cmd = new AddEvent(db);
			boolean addedSuccessfully = cmd.addNewEvent(						
					"2",//userId, 
					//						"Vollerrrrr",//
					title, 
					//						"2015-05-11 10:30:00.00",
					date,
					//						"Unversity","50",
					location,
					nbplace.equals("Illimité") ? ""+Integer.MAX_VALUE : nbplace, 
							description
							//						"description"
					);

			System.out.println("database addedSuccessfully:"+addedSuccessfully);



			if (addedSuccessfully){
				//redirects the current request to the newly created event
				String setPattern = "/";
				String setLocation = "/Webapp/liste-des-evenements/";
				redirectRequest(target, baseRequest, request, response, setPattern,
						setLocation);
			}else{
				System.out.println("failled to add event");
				//redirect the user to the event page with the same info
				//if possible indicate to the user the reason of the failure to create the event 
			}
		}
		catch (Exception e)
		{
			catchHelper(baseRequest, request, response, e);
		}

	}

	public void handleConnectUser(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		// TODO Implement handling logic for simple requests (and command
		// validation) and forwarding for requests that require specific
		// permissions or handling.
		try
		{

			//TODO:Verify User's credential and retrieve User id from the database
			String username = request.getParameter("username");
			String password = request.getParameter("password");

			Boolean authenticatedSuccessfully = true;
			//TODO:Authenticate the user here

			String JSONRequest = "{ userName :" + username + ", password :" +
					password + " }";
			System.out.println(JSONRequest);

			OpenSession userCreation = new OpenSession(JSONRequest);

			authenticatedSuccessfully = Main.getDatabase().executeDb(userCreation);
			ResultSet results = userCreation.getResultSet();
			System.out.println("authenticatedSuccessfully:"+authenticatedSuccessfully);
			int userID =-1 ;
			if(results.next()){
				//				System.out.println("before get Int"+userID);
				userID = results.getInt(1);

				System.out.println(userID);
//				if(request.getSession()==null){
				HttpSession newUserSession = request.getSession(true);
				System.out.println("session created with id :"+userID);
				newUserSession.setAttribute("UserID", userID);
				System.out.println("session attribute was set");
//				}else{
//					//do nothing session already exist
//					System.out.println("session already exist");
//				}
			}
			else{
				authenticatedSuccessfully = false;
				System.out.println("no authetification ID");
			}

			if (authenticatedSuccessfully){
				//Redirects the current request to the newly created event
				System.out.println("redirecting request to member/"+username);
				String setPattern = "/";
				String setLocation = "/Webapp/membre/"+username;
				redirectRequest(target, baseRequest, request, response, setPattern,
						setLocation);
			}else{
				//TODO:send error message to user and return to login page
			}

		}
		catch (Exception e)
		{
			catchHelper(baseRequest, request, response, e);		
		}

	}


}
