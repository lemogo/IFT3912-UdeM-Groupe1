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

import org.json.JSONException;

import ca.diro.Main;
import ca.diro.DataBase.Command.ListEventByUser;
import ca.diro.DataBase.Command.ListRegisterEvent;
import ca.diro.DataBase.Command.PageInfoUser;

public class MemberHandler extends RequestHandler {
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
				//				super.doGet(request, response);
				handleToTheRessource(request, response, pathInfo);
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
			FileNotFoundException, IOException, JSONException, SQLException {
		System.out.println("In member handler");
		// create a handle to the resource

		String pathInfo = request.getPathInfo();
		if(pathInfo.startsWith("/")) pathInfo = pathInfo.substring(1);
		
		if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
			String setLocation = "/Webapp/"+pathInfo;//"/";
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

			HashMap<String, Object> sources = addAllInfoToMustacheSources(
					request, response);

			processTemplate(request, response, "header.html",sources);
			processTemplate(request, response, filename,sources);
			processTemplate(request, response, "footer.html");
		}

		//			baseRequest.setHandled(true);
	}

	private HashMap<String, Object> addAllInfoToMustacheSources(
			HttpServletRequest request, HttpServletResponse response)
			throws JSONException, SQLException {
		//Add User info here!!
		HashMap<String, Object> sources = new HashMap<String, Object>();

//			System.out.println("before executing first database command");

		//TODO:Get the user id using the database and/or if there's no path info the id from the session variable 
		HttpSession session = request.getSession(true);
		boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
		
		int userId = Integer.parseInt((String) (session.getAttribute(USER_ID_ATTRIBUTE)==null?-1:session.getAttribute(USER_ID_ATTRIBUTE)));

		
		boolean isOwner = userId>0;
		sources.put("isOwner", isOwner);

		
		String username = addUserInfoToMustacheSources(sources, userId);

		addUserEventListToMustacheSources(sources, userId, username);

		addUserRegisteredEventToMustacheSources(sources, userId, username);

		//to display success message
		Boolean addSuccess = response.getHeader("addSuccess") == null? false:true;
		sources.put("addSuccess", addSuccess);
		Boolean registerSuccess = response.getHeader("registerSuccess") == null? false:true;
		sources.put("registerSuccess", registerSuccess);
		Boolean unregisterSuccess = response.getHeader("unregisterSuccess") == null? false:true;
		sources.put("unregisterSuccess", unregisterSuccess);

		if(isLoggedIn)sources.put("user", isLoggedIn);
		//TODO:notification
		sources.put("notifications_number", "0");
		return sources;
	}

	private void setResponseContentCharacterAndStatus(
			HttpServletResponse response) {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
	}

	private void addUserRegisteredEventToMustacheSources(
			HashMap<String, Object> sources, int userId, String username)
			throws SQLException {
		//TODO: Get the user's registeredEventsList from the database
		ListRegisterEvent userRegisterEvent= new ListRegisterEvent(""+userId);
		Boolean asExecuted3 = Main.getDatabase().executeDb(userRegisterEvent);
		if (!asExecuted3) return;
//			System.out.println("before looping on resultset. asExecuted:"+asExecuted);
		
		ResultSet rs2 = userRegisterEvent.getResultSet();
		List<Event> registeredEventList = new LinkedList<Event>();  
//eventid, title, location, dateevent, event.description
		if(rs2!=null)
		while(rs2.next()){
			registeredEventList.add(							
					new Event(username, rs2.getString("title"), rs2.getString("dateevent"),
							rs2.getString("location"), 
							rs2.getString("description"), rs2.getString("eventid"),
							"Event_badgeClass1"));
//									System.out.println("\nin while loop - adding:"+rs2.getString("title"));
		}
		sources.put("registeredEventsList",registeredEventList);
		
//			sources.put("registeredEventsList",Arrays.asList(
//			registeredEventList.add(							
//					new Event("BIDON_registeredEvents_username1", "BIDON_registeredEvents_title1", "BIDON_registeredEvents_date1",
//							"BIDON_registeredEvents_location1", "BIDON_registeredEvents_description1", "BIDON_registeredEvents_id1",
//							"BIDON_registeredEvents_badgeClass1")
//					));
//					);

//			System.out.println("after adding registeredEventList");
	}

	private void addUserEventListToMustacheSources(
			HashMap<String, Object> sources, int userId, String username)
			throws SQLException {
		//Get Users Event list 
		ListEventByUser userEventList = new ListEventByUser(""+userId);
//			System.out.println("before executing second database command");
		Boolean asExecuted2 = Main.getDatabase().executeDb(userEventList);
		if(!asExecuted2) return;
		
		ResultSet rs1 = userEventList.getResultSet();
		List<Event> eventList = new LinkedList<Event>();  

//			System.out.println("before looping on resultset");
		while(rs1.next()){
			eventList.add(							
					new Event(username, rs1.getString("title"), rs1.getString("dateevent"),
							rs1.getString("location"), rs1.getString("description"), rs1.getString("eventid"),
							"Event_badgeClass1"));
			//					System.out.println("in while loop - adding:"+rs1.getString("title"));
		}
		sources.put("ownerEventsList",eventList);

//			System.out.println("after looping on resultset");
	}

	private String addUserInfoToMustacheSources(HashMap<String, Object> sources, int userId)
			throws JSONException, SQLException {
		PageInfoUser cmd = new PageInfoUser(""+userId) ; //add cast if necessary
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
//							System.out.println("username="+username+"\tfullname="+fullname+"\temail="+email+"age="+age+"\tdescription="+description);
		sources.put("username",username);
		sources.put("fullname",fullname);
		
		//TODO:
		sources.put("registeredSince","ownerRegisteredSince");
		
		sources.put("age",age);
		sources.put("description",description);
		return username;
	}

}
