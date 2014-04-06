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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import ca.diro.Main;
import ca.diro.DataBase.Command.CountUserNotification;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

/**
 * Handler for the Jetty server.
 * 
 * @author girardil, lavoiedn
 * @version 1.0
 */
public class RequestHandler extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7170338465207325789L;
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
	//	protected static File	staticDir	= new File("static");
	protected static File	staticDir	= new File(rootDir, "./static");

	// Ressources dynamiques -> seront interpretees par Mustache
	//	protected static File	dynamicDir	= new File( "templates");
	protected static File	dynamicDir	= new File(rootDir, "templates");

	public static final String USER_ID_ATTRIBUTE="UserID";
	public static final String USERNAME_ATTRIBUTE="username";
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
	 * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Implement handling logic for simple requests (and command
		// validation) and forwarding for requests that require specific
		// permissions or handling.
		try{
			String pathInfo = request.getPathInfo();
			if(pathInfo.startsWith("/"))pathInfo = pathInfo.substring(1);
//			if(pathInfo.length()==0)return;
			if ( pathInfo.equals("accueil")||pathInfo.equals("")) pathInfo = "accueil.html";
			handleSimpleRequest(request, response, pathInfo);
		}
		catch (Exception e){
						System.out.println("In Get request handler catch exception");
			catchHelper(request, response, e);
		}

	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// TODO Implement handling logic for simple requests (and command
		// validation) and forwarding for requests that require specific
		// permissions or handling.
		try{
			String pathInfo = request.getPathInfo();
			if(pathInfo.startsWith("/"))pathInfo = pathInfo.substring(1);
//			if(pathInfo.length()==0)return;
			if ( pathInfo.equals("accueil")||pathInfo.equals("")) pathInfo = "accueil.html";
			handleSimpleRequest(request, response, pathInfo);
		}
		catch (Exception e){
				System.out.println("In request helper catch exception");
			catchHelper(request, response, e);
		}
	}

	protected void handleSimpleRequest(HttpServletRequest request,
			HttpServletResponse response, String pathInfo) throws IOException,
			UnsupportedEncodingException, FileNotFoundException, ServletException {
		if ( pathInfo.equals("accueil")) pathInfo = "accueil.html";
		else if ( pathInfo.equals("ajouter-un-evenement") ) {
			//if user is not logged in redirect him to sign up page (or maybe sign in) 
			if (request.getSession(true).getAttribute("auth")==null){ 
				response.sendRedirect("/Webapp/connexion");
				return;
			}
			pathInfo = pathInfo+".html";
		}
		else if ( pathInfo.equals("enregistrement") || pathInfo.equals("ajouter-un-evenement") ||pathInfo.equals("modifier-mes-informations")
				||pathInfo.equals("connexion")) pathInfo = pathInfo+".html";
		else if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
			String setLocation = "/"+pathInfo;//"/";
//			request.getRequestDispatcher(setLocation);
			request.getRequestDispatcher("/"+pathInfo).forward(request, response);
			return;
		}
		String filename = pathInfo;
		File staticResource = new File(staticDir, filename);
		File dynamicResource = new File(dynamicDir, filename);
		setResponseContentType(pathInfo, response);
		response.setCharacterEncoding("utf-8");

		// Ressource existe
		if(pathInfo.startsWith("https://")){
			response.setStatus(HttpServletResponse.SC_OK);
			//			copy(staticResource, response.getOutputStream());
			//TODO:find out how to deal with this case
		}
		else if (!staticResource.exists() && !dynamicResource.exists()){
			System.out.println("NOT FOUND :: \tstatic ressource:\t"+staticResource+"\tdynamicResource:\t"+dynamicResource);
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			processTemplate(request, response, "404.html");
		}
		else if (staticResource.exists()){
			response.setStatus(HttpServletResponse.SC_OK);
			copy(staticResource, response.getOutputStream());
		}
		else{
			response.setStatus(HttpServletResponse.SC_OK);
			HttpSession session = request.getSession(true);
			boolean isLoggedIn=session.getAttribute("auth")==null? false:true;

			HashMap<String, Object> sources = new HashMap<String, Object>();
			sources.put("user", isLoggedIn);
			sources.put("options", buildSelectTagOptions());

			processTemplate(request, response, "header.html", sources);
			processTemplate(request, response, filename,sources);
			processTemplate(request, response, "footer.html");
		}
	}

	protected void setResponseContentType(String target, HttpServletResponse response) {
		// A changer pour supporter des images, peut-etre par extension ou
		// par repertoire
		if (target.endsWith(".css")){
			response.setContentType("text/css");
		}
		else if (target.endsWith(".js")){
			response.setContentType("text/javascript");
		}
		else if (target.endsWith(".png")){
			response.setContentType("image/png");
		}
		else if (target.endsWith(".jpg")){
			response.setContentType("image/jpg");
		}
		else if (target.endsWith(".ico")){
			response.setContentType("image/x-icon");
		}
		else if (target.endsWith(".ttf")){
			response.setContentType("application/x-font-ttf");
		}
		else if (target.endsWith(".woff")){
			response.setContentType("application/x-font-woff");
		}
		else{
			response.setContentType("text/html");
		}
	}

	protected void catchHelper( HttpServletRequest request,
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
	private void copy(File staticres, OutputStream outputStream) throws IOException{
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
	private long copy(InputStream in, OutputStream out) throws IOException{
		long bytes = 0;
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);
		bytes += len;
		while (len != -1){
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
	protected void processTemplate(HttpServletRequest req, HttpServletResponse res, String filename, Object... scopes) throws UnsupportedEncodingException, FileNotFoundException, IOException{
		MustacheFactory mc = new DefaultMustacheFactory(dynamicDir);
//		mc = new DefaultMustacheFactory(dynamicDir);
		Mustache mustache = mc.compile(filename);

		Map<Object, Object> parameters = new HashMap<Object, Object>(req.getParameterMap()){
			@Override
			public Object get(Object o){
				Object result = super.get(o);
				if (result instanceof String[]){
					String[] strings = (String[]) result;
					if (strings.length == 1){
						return strings[0];
					}
				}
				return result;
			}
		};
		List<Object> scs = new ArrayList<Object>();
		scs.add(parameters);
		for (Object o : scopes){
			scs.add(o);
		}
		mustache.execute(res.getWriter(), scs.toArray());
	}

	private String simpleEscape(String string){
		return string.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}

	protected boolean isAnotherContext(String pathInfo) {
		return pathInfo.equals("accueil")||pathInfo.startsWith("liste-des-evenements")||pathInfo.startsWith("modifier-un-evenement")
				||pathInfo.startsWith("membre")||pathInfo.startsWith("notifications")
				||pathInfo.startsWith("connexion")||pathInfo.equals("deconnexion")
				||pathInfo.startsWith("enregistrement")||pathInfo.startsWith("ajouter-un-evenement")
				||pathInfo.startsWith("evenement")||pathInfo.startsWith("evenement/")||pathInfo.startsWith("modifier-mes-informations")
				||pathInfo.startsWith("deconnexion")||pathInfo.startsWith("evenement-modification/")
				||pathInfo.startsWith("modify-event")||pathInfo.startsWith("delete-event")
				||pathInfo.equals("register-event")||pathInfo.startsWith("unregister-event")
				||pathInfo.startsWith("connect-user")||pathInfo.equals("create-user")
				||pathInfo.startsWith("create-event")||pathInfo.startsWith("add-comment")
				||pathInfo.startsWith("remove-notification")
				||pathInfo.startsWith("search-all")
				;
	}

	protected String countUserNotification(HttpSession session)
			throws SQLException {
		String loggedUserId = session.getAttribute(USER_ID_ATTRIBUTE)==null?"-1":(String) session.getAttribute(USER_ID_ATTRIBUTE);
		return countUserNotification(loggedUserId);
		}
	
	protected String countUserNotification(String loggedUserId)
			throws SQLException {
				String notificationNumber = "0";
				CountUserNotification cmdCount = new CountUserNotification(loggedUserId);
				Main.getDatabase().executeDb(cmdCount);
				ResultSet rs2 = cmdCount.getResultSet();
				if(rs2.next()) notificationNumber = rs2.getNString(1);
				return notificationNumber;
			}

	protected String computeBadgeColor(int numPlacesLeft) {
		//!badgeGreen si + que 3, badgeYellow si - que 3 et badgeRed si 0
		String badgeClasse = "badgeGreen";
		if (numPlacesLeft<=0) badgeClasse = "badgeRed";
		else if (numPlacesLeft<3) badgeClasse = "badgeYellow";
		return badgeClasse;
	}

	protected String buildSelectTagOptions() {
		String options = "";
		for(int i = 0; i<99;i++){
			options+="<option value=\""+i+"\">"+i+"</option>\n";
		}
		return options;
	}

	protected void setDefaultResponseContentCharacterAndStatus(HttpServletResponse response) {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
