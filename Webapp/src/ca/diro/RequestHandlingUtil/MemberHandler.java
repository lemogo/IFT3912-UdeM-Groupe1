package ca.diro.RequestHandlingUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;

import ca.diro.Main;
import ca.diro.DataBase.Command.ListEventByUser;
import ca.diro.DataBase.Command.ListRegisterEvent;
import ca.diro.DataBase.Command.PageInfoUser;

public class MemberHandler extends RequestHandler {
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
			String pathInfo = request.getPathInfo().substring(1);

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
			String pathInfo = request.getPathInfo().substring(1);

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
			response.setContentType("text/html");
			response.setCharacterEncoding("utf-8");
			response.setStatus(HttpServletResponse.SC_OK);

			//Add User info here!!
			HashMap<String, Object> sources = new HashMap<String, Object>();
			sources.put("isOwner", "true");
							System.out.println("before executing first database command");

			//TODO:Get the user id using the database and/or if there's no path info the id from the session variable 
			int userId = 2;
			PageInfoUser cmd = new PageInfoUser(userId) ; //add cast if necessary
			Boolean asExecuted = Main.getDatabase().executeDb(cmd); //true check si la requete est bien exécuté 
			ResultSet rs = cmd.getResultSet(); //retourne (username,password,fullname,email,age,description)

			String username="",fullname="",email="",age="",description="";
			if (asExecuted){
				if(rs.next()){
					username = rs.getString("username");
					fullname = rs.getString("fullname");
					email = rs.getString("email");
					age  = rs.getString("age"); 
					description = rs.getString("description");
				}
			}else{
				//TODO:send error message to user and return to login page
			}

							System.out.println("username="+username+"\tfullname="+fullname+"\temail="+email+"age="+age+"\tdescription="+description);

			sources.put("username",username);
			sources.put("fullname",fullname);
			sources.put("registeredSince","ownerRegisteredSince");
			sources.put("age",age);
			sources.put("description",description);

			//Get Users Event list 
			ListEventByUser userEventList = new ListEventByUser(userId);
			System.out.println("before executing second database command");
			asExecuted = Main.getDatabase().executeDb(userEventList);
			rs = userEventList.getResultSet();
			List<Event> eventList = new LinkedList<Event>();  

//			System.out.println("before looping on resultset");
			while(rs.next()){
				eventList.add(							
						new Event(username, rs.getString("title"), rs.getString("dateevent"),
								rs.getString("location"), rs.getString("description"), rs.getString("eventid"),
								"Event_badgeClass1"));
				//					System.out.println("in while loop - adding:"+rs.getString("title"));
			}
			sources.put("ownerEventsList",eventList);

//			System.out.println("after looping on resultset");

			//TODO: Get the user's registeredEventsList from the database
			ListRegisterEvent userRegisterEvent= new ListRegisterEvent("{userId:"+userId+"}");
			asExecuted = Main.getDatabase().executeDb(userEventList);

			System.out.println("before looping on resultset");
			
//			rs = userRegisterEvent.getResultSet();
			List<Event> registeredEventList = new LinkedList<Event>();  
////eventid, title, location, dateevent, event.description
//			while(rs.next()){
//				registeredEventList.add(							
//						new Event(username, rs.getString("title"), rs.getString("dateevent"),
//								rs.getString("location"), rs.getString("description"), rs.getString("eventid"),
//								"Event_badgeClass1"));
//				//					System.out.println("in while loop - adding:"+rs.getString("title"));
//			}
			sources.put("registeredEventsList",registeredEventList);
			
//			sources.put("registeredEventsList",Arrays.asList(
			registeredEventList.add(							
					new Event("BIDON_registeredEvents_username1", "BIDON_registeredEvents_title1", "BIDON_registeredEvents_date1",
							"BIDON_registeredEvents_location1", "BIDON_registeredEvents_description1", "BIDON_registeredEvents_id1",
							"BIDON_registeredEvents_badgeClass1")
//					));
					);

			//to display success message
			sources.put("addSuccess", "false");
			//				sources.put("registerSuccess", "true");
			//				sources.put("unregisterSuccess", "false");

			boolean isLoggedIn=request.getRequestedSessionId()==null? false:true;
			sources.put("user", isLoggedIn);
			sources.put("notifications_number", "0");

			processTemplate(request, response, "header.html",sources);
			processTemplate(request, response, filename,sources);
			processTemplate(request, response, "footer.html");
		}

		//			baseRequest.setHandled(true);
	}

}
