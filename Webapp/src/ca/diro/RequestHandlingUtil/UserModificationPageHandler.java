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

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		doGet(request, response);
	}

	private void processRequestHelper(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			FileNotFoundException, IOException, SQLException, ServletException {

		String pathInfo = request.getPathInfo() == null? "":request.getPathInfo();
		if(pathInfo.startsWith("/")) pathInfo = pathInfo.substring(1);

		if(!isLoggedIn(request.getSession(true))){
			response.setHeader("error", "Veillez vous connecter!");
			request.getRequestDispatcher("/connexion").forward(request, response);
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
		HashMap<String, Object> sources = new HashMap<String, Object>();

		HttpSession session = request.getSession(true);
		int userId = Integer.parseInt(getLoggedUserId(session));

		sources.putAll(addUserInfoToMustacheSources(userId));

		if(isLoggedIn(session))sources.put("user", isLoggedIn(session));
		String loggedUserId = session.getAttribute(USER_ID_ATTRIBUTE)==null?"-1":(String) session.getAttribute(USER_ID_ATTRIBUTE);
		sources.put("notifications_number", countUserNotification(loggedUserId));
		sources.putAll(buildErrorMessagesMustacheSource(response));
		return sources;
	}

	private HashMap<String, Object> addUserInfoToMustacheSources(int userId)
			throws SQLException {
		HashMap<String, Object> sources = new HashMap<String, Object>();
		PageInfoUser cmd = new PageInfoUser(userId) ; 

		String username="bidon_age",fullname="bidon_fullname",email="bidon_email",
				age="-1",description="bidon_description";
		if(Main.getDatabase().executeDb(cmd)){  
			ResultSet rs = cmd.getResultSet();
			if(rs.next()){
				username = rs.getString("username");
				fullname = rs.getString("fullname");
				email = rs.getString("email");
				age  = rs.getString("age"); 
				description = rs.getString("description");
				sources.put("registeredSince",rs.getTimestamp("datecreation"));
			}
			sources.put("age", rs.getTimestamp("age"));
			//			sources.put("options", buildSelectOptionsTag(1,121,rs.getInt("age")));
		}else{
//			response.setHeader("error", "La modification de votre compete a echoue");
//			request.getRequestDispatcher("/connexion").forward(request, response);
		}
		sources.put("username",username);
		sources.put("fullname",fullname);
		sources.put("age",age);
		sources.put("email",email);
		sources.put("description",description);
		sources.put("age",age);
		sources.put("description",description);
		return sources;
	}
}
