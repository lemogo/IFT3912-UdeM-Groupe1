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
import ca.diro.DataBase.DataBase;
import ca.diro.DataBase.Command.PageInfoEvent;

public class EventModificationPageHandler extends RequestHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8940116469004925945L;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
	 * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doGet(
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		try{
			String pathInfo = request.getPathInfo();
			if(pathInfo.startsWith("/")) pathInfo = pathInfo.substring(1);
			//The current request must be a file -> redirect to requestHandler
			if(	isKnownFileExtention(pathInfo)) {
				handleSimpleRequest(request, response, pathInfo);
				return;
			}else if(isAnotherContext(pathInfo)){ 	        
				//				String setLocation = "/"+pathInfo;//"/";
				//				request.getRequestDispatcher(setLocation).forward(request, response);
				String setLocation = "/Webapp/"+pathInfo;//"/";
				response.sendRedirect(setLocation);
				return;
			}

			String filename = "modifier-un-evenement.html"; 
			File staticResource = new File(staticDir, filename);
			File dynamicResource = new File(dynamicDir, filename);

			// Ressource existe
			if (!staticResource.exists() && !dynamicResource.exists()){
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				processTemplate(request, response, "404.html");
			}else{
				processRequest(request, response, pathInfo, filename);
			}
		}
		catch (Exception e){
			catchHelper( request, response, e);
		}
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response, String pathInfo, String filename)
			throws SQLException, UnsupportedEncodingException,
			FileNotFoundException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		//TODO:find out if the logged user is the owner of the event, if not return to event page with message you are not the owner
		String eventID = pathInfo;
		HttpSession session = request.getSession(true);
//		String loggedUsername = session.getAttribute(USERNAME_ATTRIBUTE)==null?"Anonymous":(String) session.getAttribute(USERNAME_ATTRIBUTE);

		DataBase myDb = Main.getDatabase();
		PageInfoEvent pageInfoEventCommand = new PageInfoEvent(eventID,myDb);
		HashMap<String, Object> sources = new HashMap<String, Object>();
		if( myDb.executeDb(pageInfoEventCommand)){ 
			ResultSet rs = pageInfoEventCommand.getResultSet();
			if (rs.next())
				sources.put("event",
						new Event(rs.getString("username"), rs.getString("title"), rs.getString("dateevent"),
								rs.getString("location"), rs.getString("description"), eventID,
								rs.getString("numberplaces"))
						);
			sources.put("id", eventID);
			sources.putAll(buildIsEventOwnerMustacheSource( session,rs.getString("username")));
			sources.put("options", buildSelectOptionsTag(1,1000,rs.getInt("numberplaces")));
			sources.put("user", isLoggedIn(session));
			if(isLoggedIn(session)){
				String loggedUserId = session.getAttribute(USER_ID_ATTRIBUTE)==null?"-1":(String) session.getAttribute(USER_ID_ATTRIBUTE);
				sources.put("notifications_number", countUserNotification(loggedUserId));
			}

			processTemplate(request, response, "header.html", sources);
			processTemplate(request, response, filename, sources);
			processTemplate(request, response, "footer.html");
		}
	}
}
