package ca.diro.RequestHandlingUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
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
		// TODO Implement handling logic for simple requests (and command
		// validation) and forwarding for requests that require specific
		// permissions or handling.
		try{
			String pathInfo = request.getPathInfo().startsWith("/")?request.getPathInfo().substring(1):request.getPathInfo();

			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")) {
				super.doGet(request, response);
				handleToTheRessource(request, response, pathInfo);
				return;
			}else if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
				String setLocation = "/Webapp/"+pathInfo;//"/";
				response.sendRedirect(setLocation);
				return;
			}
			processRequestHelper(request, response);
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
		// TODO Implement handling logic for simple requests (and command
		// validation) and forwarding for requests that require specific
		// permissions or handling.
		try{
			String pathInfo = request.getPathInfo().startsWith("/")?request.getPathInfo().substring(1):request.getPathInfo();

			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")) {
				handleToTheRessource(request, response, pathInfo);
				return;
			}else if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
				String setLocation = "/Webapp/"+pathInfo;
				response.sendRedirect(setLocation);
				return;
			}
			processRequestHelper(request, response);
		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}
	}

	private void processRequestHelper(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			FileNotFoundException, IOException,  SQLException {
//		System.out.println("In member handler");
		// create a handle to the resource

		String pathInfo = request.getPathInfo();
		if(pathInfo.startsWith("/")) pathInfo = pathInfo.substring(1);
		
		if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
			String setLocation = "/Webapp/"+pathInfo;
			response.sendRedirect(setLocation);
			return;
		}

		if(pathInfo.equals("")){
			//check if user is logged in

			//TODO:if user is not logged in redirect user to login page to view is page
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
			setResponseContentCharacterAndStatus(response);

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
		//Add User info here!!
		HashMap<String, Object> sources = new HashMap<String, Object>();

		//TODO:Get the user id using the database and/or if there's no path info the id from the session variable 
		HttpSession session = request.getSession(true);
		boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
		
		int loggedUserId = Integer.parseInt((String) (session.getAttribute(USER_ID_ATTRIBUTE)==null?-1:session.getAttribute(USER_ID_ATTRIBUTE)));
		String loggedUserUsername = (String) (session.getAttribute(USERNAME_ATTRIBUTE)==null?-1:session.getAttribute(USERNAME_ATTRIBUTE));
		String displayedUserUsername = request.getPathInfo().startsWith("/")?request.getPathInfo().substring(1).trim():request.getPathInfo().trim();
		
		if(displayedUserUsername.equals("")||displayedUserUsername==null) displayedUserUsername = loggedUserUsername;
		boolean isOwner = loggedUserUsername.equals( displayedUserUsername);
		sources.put("isOwner", isOwner);

		sources.putAll(buildMustacheSourcesUserInfo( displayedUserUsername));
		int displayedUserUserId = getUserId(displayedUserUsername)>0?getUserId(displayedUserUsername):loggedUserId;

		if(isLoggedIn){
		sources.putAll(buildMustacheSourcesUserEventList(displayedUserUserId, displayedUserUsername));
		sources.putAll(buildMustacheSourcesUserRegisteredEventList(displayedUserUserId, displayedUserUsername));
		}
		
		sources.putAll(buildMustacheSourcesSuccessMessages(response, isLoggedIn));
		
		sources.put("notifications_number", countUserNotification(""+loggedUserId));
		return sources;
	}

	private HashMap<String, Object> buildMustacheSourcesSuccessMessages(
			HttpServletResponse response, boolean isLoggedIn) {
		HashMap<String, Object> sources = new HashMap<String, Object>();
		//to display success message
		Boolean addSuccess = response.getHeader("addSuccess") == null? false:true;
		sources.put("addSuccess", addSuccess);
		Boolean registerSuccess = response.getHeader("registerSuccess") == null? false:true;
		sources.put("registerSuccess", registerSuccess);
		Boolean unregisterSuccess = response.getHeader("unregisterSuccess") == null? false:true;
		sources.put("unregisterSuccess", unregisterSuccess);
		Boolean modifySuccess = response.getHeader("modifySuccess") == null? false:true;
		sources.put("modifySuccess", modifySuccess);
		if(isLoggedIn)sources.put("user", isLoggedIn);
		return sources;
	}

	private void setResponseContentCharacterAndStatus(
			HttpServletResponse response) {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
	}

	private HashMap<String, Object> buildMustacheSourcesUserRegisteredEventList(
			 int userId, String username)
			throws SQLException {
		//TODO: Get the user's registeredEventsList from the database
		HashMap<String, Object> sources = new HashMap<String, Object>();
		ListRegisterEvent userRegisterEvent= new ListRegisterEvent(""+userId);
		Boolean asExecuted3 = Main.getDatabase().executeDb(userRegisterEvent);
		if (!asExecuted3) return sources;
		
		ResultSet rs2 = userRegisterEvent.getResultSet();
		List<Event> registeredEventList = new LinkedList<Event>();  
		if(rs2!=null)
		while(rs2.next()){
			registeredEventList.add(							
					new Event(username, rs2.getString("title"), rs2.getString("dateevent"),
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
					new Event(username, rs1.getString("title"), rs1.getString("dateevent"),
							rs1.getString("location"), rs1.getString("description"), rs1.getString("eventid"),
							"Event_badgeClass1"));
		}
		sources.put("ownerEventsList",eventList);
		return sources;
	}

	private HashMap<String, Object> buildMustacheSourcesUserInfo(String displayedUserUsername)
			throws  SQLException {
		HashMap<String, Object> sources = new HashMap<String, Object>();
		PageInfoUser cmd = new PageInfoUser(displayedUserUsername) ; //add cast if necessary
		Boolean asExecuted = Main.getDatabase().executeDb(cmd); //true check si la requete est bien exécuté 
		ResultSet rs = cmd.getResultSet(); //retourne (username,password,fullname,email,age,description)

		String username="",fullname="",//email="",
				age="",description="";
		if (asExecuted){
			if(rs.next()){
				username = rs.getString("username");
				fullname = rs.getString("fullname");
//				email = rs.getString("email");
				age  = rs.getString("age"); 
				description = rs.getString("description");
			}
		}else{
			//TODO:send error message to user and return to login page
		}
		sources.put("username",username);
		sources.put("fullname",fullname);
		
		//TODO:ownerRegisteredSince
		sources.put("registeredSince","ownerRegisteredSince");
		
		sources.put("age",age);
		sources.put("description",description);
		return sources;
	}
	private int getUserId(String displayedUserUsername)
			throws SQLException {
		PageInfoUser cmd = new PageInfoUser(displayedUserUsername) ; //add cast if necessary
		Boolean asExecuted = Main.getDatabase().executeDb(cmd); //true check si la requete est bien exécuté 
		ResultSet rs = cmd.getResultSet(); //retourne (username,password,fullname,email,age,description)

		int userId=-1;
		if (asExecuted){
			if(rs.next()){
				userId= rs.getInt("suserid");
			}
		}else{
			//TODO:send error message to user and return to login page
		}
		return userId;
	}

}
