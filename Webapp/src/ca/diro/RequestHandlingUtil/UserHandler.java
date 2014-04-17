package ca.diro.RequestHandlingUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.Command.ListEventByUser;
import ca.diro.DataBase.Command.ListRegisterEvent;
import ca.diro.DataBase.Command.PageInfoUser;

public class UserHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6612271874747826901L;

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
			String pathInfo = request.getPathInfo().startsWith("/")?request.getPathInfo().substring(1):request.getPathInfo();
			//The current request must be a file -> redirect to requestHandler
			if(	isKnownFileExtention(pathInfo)) {
				handleSimpleRequest(request, response, pathInfo);
				return;
			}else if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
				//				request.getRequestDispatcher("/"+pathInfo).forward(request, response);
				String setLocation = "/Webapp/"+pathInfo;//"/";
				response.sendRedirect(setLocation);
				return;
			}
			processRequest(request, response);
		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
	 * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void doPost(
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		try{
			String pathInfo = request.getPathInfo().startsWith("/")?request.getPathInfo().substring(1):request.getPathInfo();

			//The current request must be a file -> redirect to requestHandler
			if(	isKnownFileExtention(pathInfo)) {
				handleSimpleRequest(request, response, pathInfo);
				return;
			}else if(isAnotherContext(pathInfo)){ 	        
				request.getRequestDispatcher("/"+pathInfo).forward(request, response);
				return;
			}
			processRequest(request, response);
		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			FileNotFoundException, IOException,  SQLException {
		String pathInfo = request.getPathInfo();
		if(pathInfo.startsWith("/")) pathInfo = pathInfo.substring(1);

		if(isAnotherContext(pathInfo)){ 	        
			String setLocation = "/Webapp/"+pathInfo;
			response.sendRedirect(setLocation);
			return;
		}

		if(pathInfo.equals("")&& !isLoggedIn(request.getSession(true))){
			//if user is not logged in redirect user to login page to view is page
			response.sendRedirect("/Webapp/connexion");
			return;
		}

		String filename = "membre.html"; 
		File staticResource = new File(staticDir, filename);
		File dynamicResource = new File(dynamicDir, filename);

		// Ressource existe
		if (!staticResource.exists() && !dynamicResource.exists()){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			processTemplate(request, response, "404.html");
		}
		else{
			setDefaultResponseContentCharacterAndStatus(response);
			HashMap<String, Object> sources = buildMustacheSourcesInfo(
					request, response);

			processTemplate(request, response, "header.html",sources);
			processTemplate(request, response, filename,sources);
			processTemplate(request, response, "footer.html");
		}
	}

	private HashMap<String, Object> buildMustacheSourcesInfo(
			HttpServletRequest request, HttpServletResponse response)
					throws  SQLException {
		HashMap<String, Object> sources = new HashMap<String, Object>();

		//TODO:Get the user id using the database and/or if there's no path info the id from the session variable 
		HttpSession session = request.getSession(true);

		int loggedUserId = Integer.parseInt(getLoggedUserId(session));
//				(String) (session.getAttribute(USER_ID_ATTRIBUTE)==null?"-1":(String)session.getAttribute(USER_ID_ATTRIBUTE)));
		//		int loggedUserId = session.getAttribute(USER_ID_ATTRIBUTE)==null?-1:(int)session.getAttribute(USER_ID_ATTRIBUTE);
		String loggedUserUsername = (String) (session.getAttribute(USERNAME_ATTRIBUTE)==null?"-1":session.getAttribute(USERNAME_ATTRIBUTE));
		String displayedUserUsername = request.getPathInfo().startsWith("/")?request.getPathInfo().substring(1).trim():request.getPathInfo().trim();

		if(displayedUserUsername.equals("")||displayedUserUsername==null) displayedUserUsername = loggedUserUsername;
		boolean isOwner = loggedUserUsername.equals( displayedUserUsername);
		sources.put("isOwner", isOwner);

		sources.putAll(buildMustacheSourcesUserInfo( displayedUserUsername));
		int displayedUserUserId = getUserId(displayedUserUsername)>0?getUserId(displayedUserUsername):loggedUserId;

		if(isLoggedIn(session)){
			sources.putAll(buildMustacheSourcesUserEventList(displayedUserUserId, displayedUserUsername));
			sources.putAll(buildMustacheSourcesUserRegisteredEventList(displayedUserUserId, displayedUserUsername));
		}
		String[] sourceHeaders = new String[]{"addSuccess","registerSuccess","isRegistered","unregisterSuccess","modifySuccess"};
		sources.putAll(buildMustacheSourcesFromHeaders(response, sourceHeaders));
		sources.put("user", isLoggedIn(session));
		sources.put("notifications_number", countUserNotification(""+loggedUserId));
		return sources;
	}

	private HashMap<String, Object> buildMustacheSourcesUserRegisteredEventList(
			int userId, String username)
					throws SQLException {
		//Get the user's registeredEventsList from the database
		HashMap<String, Object> sources = new HashMap<String, Object>();
		ListRegisterEvent userRegisterEvent= new ListRegisterEvent(""+userId);
		Boolean asExecuted3 = Main.getDatabase().executeDb(userRegisterEvent);
		if (!asExecuted3) return sources;

		ResultSet rs2 = userRegisterEvent.getResultSet();
		List<Event> registeredEventList = new LinkedList<Event>();  
		if(rs2!=null)
			while(rs2.next()){
				registeredEventList.add(							
						new Event(rs2.getString("username"), rs2.getString("title"), rs2.getString("dateevent"),
								rs2.getString("location"), 
								rs2.getString("description"), rs2.getString("eventid"),
								"Event_badgeClass1"));
			}
		sources.put("registeredEventsList",registeredEventList);
		return sources;
	}

	private HashMap<String, Object> buildMustacheSourcesUserEventList(
			int userId, String username)
					throws SQLException {
		//Get Users Event list
		HashMap<String, Object> sources = new HashMap<String, Object>();
		ListEventByUser userEventList = new ListEventByUser(""+userId);
		Boolean asExecuted2 = Main.getDatabase().executeDb(userEventList);
		if(!asExecuted2) return sources;

		ResultSet rs1 = userEventList.getResultSet();
		List<Event> eventList = new LinkedList<Event>();  
		while(rs1.next()){
			eventList.add(							
					new Event(rs1.getString("username"), rs1.getString("title"), rs1.getString("dateevent"),
							rs1.getString("location"), rs1.getString("description"), rs1.getString("eventid"),
							"Event_badgeClass1"));
		}
		sources.put("ownerEventsList",eventList);
		return sources;
	}

	private HashMap<String, Object> buildMustacheSourcesUserInfo(String displayedUserUsername)
			throws  SQLException {
		HashMap<String, Object> sources = new HashMap<String, Object>();
		PageInfoUser pageInfoUserCommand = new PageInfoUser(displayedUserUsername) ; 

		String username="",fullname="",//email="",
				age="",description="";
		if(Main.getDatabase().executeDb(pageInfoUserCommand)){  
			ResultSet rs = pageInfoUserCommand.getResultSet(); 
			if(rs.next()){
				username = rs.getString("username");
				fullname = rs.getString("fullname");
				//				email = rs.getString("email");
				age = ""+computeAge(rs.getDate("age").getTime());
				description = rs.getString("description");
				sources.put("registeredSince",rs.getTimestamp("datecreation"));
			}
		}else{
			//TODO:send error message to user and return to login page
		}
		sources.put("username",username);
		sources.put("fullname",fullname);
		sources.put("age",age);
		sources.put("description",description);
		return sources;
	}

	private int getUserId(String displayedUserUsername)
			throws SQLException {
		PageInfoUser cmd = new PageInfoUser(displayedUserUsername) ; 
		int userId=-1;
		if( Main.getDatabase().executeDb(cmd)){  
			ResultSet rs = cmd.getResultSet();
			if(rs.next())
				userId= rs.getInt("suserid");
		}else{
			//TODO:send error message to user and return to login page
		}
		return userId;
	}
}
