package ca.diro.RequestHandlingUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.Command.PageInfoUser;

public class UserModificationPageHandler extends RequestHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6314473478508050465L;

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
		try{
			if(request.getPathInfo()!=null){
				String pathInfo = request.getPathInfo().startsWith("/")?request.getPathInfo().substring(1):request.getPathInfo();

				//The current request must be a file -> redirect to requestHandler
				if(	isKnownFileExtention(pathInfo)) {
					handleSimpleRequest(request, response, pathInfo);
					return;
				}else if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
					String setLocation = "/Webapp/"+pathInfo;//"/";
					response.sendRedirect(setLocation);
					return;
				}
			}
			processRequestHelper(request, response);
		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}
	}

	private void processRequestHelper(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			FileNotFoundException, IOException, SQLException {

		String pathInfo = request.getPathInfo() == null? "":request.getPathInfo();
		if(pathInfo.startsWith("/")) pathInfo = pathInfo.substring(1);

		if(pathInfo.equals("")){
			//check if user is logged in

			//TODO:if user is not logged in redirect user to login page to view is page
		}
		String filename = "modifier-mes-informations.html"; 

		File staticResource = new File(staticDir, filename);
		File dynamicResource = new File(dynamicDir, filename);

		// Ressource existe
		if (!staticResource.exists() && !dynamicResource.exists()){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			processTemplate(request, response, "404.html");
		}
		else{
			setDefaultResponseContentCharacterAndStatus(response);
			HashMap<String, Object> sources = addAllInfoToMustacheSources(
					request, response);

			processTemplate(request, response, "header.html",sources);
			processTemplate(request, response, filename,sources);
			processTemplate(request, response, "footer.html");
		}
	}

	private HashMap<String, Object> addAllInfoToMustacheSources(
			HttpServletRequest request, HttpServletResponse response)
					throws SQLException {
		//Add User info here!!
		HashMap<String, Object> sources = new HashMap<String, Object>();

		//TODO:Get the user id using the database and/or if there's no path info the id from the session variable 
		HttpSession session = request.getSession(true);
		boolean isLoggedIn=session.getAttribute("auth")==null? false:true;

		int userId = Integer.parseInt((String) (session.getAttribute(USER_ID_ATTRIBUTE)==null?-1:session.getAttribute(USER_ID_ATTRIBUTE)));

		addUserInfoToMustacheSources(sources, userId);

		if(isLoggedIn)sources.put("user", isLoggedIn);
		String loggedUserId = session.getAttribute(USER_ID_ATTRIBUTE)==null?"-1":(String) session.getAttribute(USER_ID_ATTRIBUTE);
		sources.put("notifications_number", countUserNotification(loggedUserId));
		return sources;
	}

	private String addUserInfoToMustacheSources(HashMap<String, Object> sources, int userId)
			throws SQLException {
		PageInfoUser cmd = new PageInfoUser(userId) ; //add cast if necessary
		Boolean asExecuted = Main.getDatabase().executeDb(cmd); //true check si la requete est bien exécuté 
		ResultSet rs = cmd.getResultSet(); //retourne (username,password,fullname,email,age,description)

		//fullname, username, email, age, description
		String username="bidon_age",fullname="bidon_fullname",email="bidon_email",
				age="-1",description="bidon_description";
		if (asExecuted){
			if(rs.next()){
				username = rs.getString("username");
				fullname = rs.getString("fullname");
				email = rs.getString("email");
				age  = rs.getString("age"); 
				description = rs.getString("description");
			}
			sources.put("options", buildSelectOptionsTag(1,121,rs.getInt("age")));
		}else{
			//TODO:send error message to user and return to login page
		}
		sources.put("username",username);
		sources.put("fullname",fullname);
		sources.put("age",age);
		sources.put("email",email);
		sources.put("description",description);

		sources.put("registeredSince",computeOwnerRegisteredSince());
		sources.put("age",age);
		sources.put("description",description);
		return username;
	}
}
