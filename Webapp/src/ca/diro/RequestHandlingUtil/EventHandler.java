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

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.DataBase;
import ca.diro.DataBase.Command.ListCommentEvent;
import ca.diro.DataBase.Command.PageInfoEvent;
import ca.diro.DataBase.Command.VerifyUserRegisterToEvent;

public class EventHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6294953383525213416L;

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
		// TODO Implement handling logic for simple requests (and command
		// validation) and forwarding for requests that require specific
		// permissions or handling.
		processRequest(request, response);

	}


	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			UnsupportedEncodingException, FileNotFoundException {
		try{
			String pathInfo = request.getPathInfo().substring(1);

			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")) {
				handleToTheRessource(request, response, pathInfo);
				return;
			}
			if(isAnotherContext(pathInfo)
//					||pathInfo.startsWith("modify-event")
//					||pathInfo.startsWith("delete-event")||pathInfo.startsWith("register-event")
					){
				String setLocation = "/Webapp/"+pathInfo;
				response.sendRedirect(setLocation);
				return;
			}

			// create a handle to the resource
			String filename = "evenement.html"; 
			File staticResource = new File(staticDir, filename);
			File dynamicResource = new File(dynamicDir, filename);

			// Ressource existe
			if (!staticResource.exists() && !dynamicResource.exists()){
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				processTemplate(request, response, "404.html");
			}
			else{
				processRequestHelper(request, response, pathInfo, filename);
			}
		}
		catch (Exception e){
			System.out.println("In eventHanler GET catch exception");
			catchHelper(request, response, e);
		}
	}


	private void processRequestHelper(HttpServletRequest request,
			HttpServletResponse response, String eventID, String filename)
			throws SQLException, UnsupportedEncodingException,
			FileNotFoundException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		//TODO:Get the user event info from the database
		DataBase myDb = Main.getDatabase();
		PageInfoEvent cmd = new PageInfoEvent(eventID,myDb);
		String userId = "-1";

		HttpSession session = request.getSession(true);
		boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
		HashMap<String, Object> sources = new HashMap<String, Object>();

		if (isLoggedIn) userId = (String) session.getAttribute(USER_ID_ATTRIBUTE);
		if( isRegisteredToEvent(eventID, userId)) response.setHeader("isRegistered", "true");

		if( myDb.executeDb(cmd)){ 
			ResultSet rs = cmd.getResultSet();

			if (rs.next()) {
				String username =rs.getString("username");

				addAllInfoToMustacheSources(response, eventID, session,
						isLoggedIn, sources, rs, username);

				processTemplate(request, response, "header.html",sources);
				processTemplate(request, response, filename,sources);
				processTemplate(request, response, "footer.html");
			}else{
				//TODO:show error message -- The event ____ does not exist
			}
		}
	}


	private void addAllInfoToMustacheSources(HttpServletResponse response,
			String eventID, HttpSession session, boolean isLoggedIn,
			HashMap<String, Object> sources, ResultSet rs, String username)
			throws SQLException {
		sources.put("event",
				new Event(username, rs.getString("title"), rs.getString("dateevent"),
						rs.getString("location"), rs.getString("description"), eventID,
						rs.getString("numberplaces"))
				);
		
		//username, description, datecreation, suserid
		ListCommentEvent cmd = new ListCommentEvent(eventID);
		boolean asExecuted=Main.getDatabase().executeDb(cmd);
		List<Comment> commentList = new LinkedList<Comment>();
		if (asExecuted) {
			rs=cmd.getResultSet();
			String loggedUsername = session.getAttribute(USERNAME_ATTRIBUTE)==null?"Anonymous":(String) session.getAttribute(USERNAME_ATTRIBUTE);

		while(rs.next()){
			commentList.add(new Comment(loggedUsername, rs.getString("description"), 
					rs.getString("datecreation"), rs.getString("suserid")));
		}
		sources.put("comment", commentList);
		}
		
		//to display success message
		sources.put("id", eventID);
		sources.put("user", isLoggedIn);
		
		sources.put("notifications_number", "0");
		
		addSuccessInfoToMustacheSources(response, session, sources, username);
	}

	private void addSuccessInfoToMustacheSources(HttpServletResponse response,
			HttpSession session, HashMap<String, Object> sources,
			String username) {
		boolean addSuccess = response.getHeader("addSuccess")==null ? false:Boolean.parseBoolean(response.getHeader("addSuccess"));
		if(addSuccess) sources.put("addSuccess", addSuccess);
		
		boolean isOwner = response.getHeader("isOwner")!=null?true:false;
		if(username.equals(session.getAttribute(USERNAME_ATTRIBUTE))) isOwner = true;
		if(isOwner)sources.put("isOwner", isOwner);

		boolean registerSuccess = response.getHeader("registerSuccess")==null ? false:Boolean.parseBoolean(response.getHeader("registerSuccess"));
		if(registerSuccess)sources.put("registerSuccess", registerSuccess);
		
		boolean isRegistered = response.getHeader("isRegistered")==null ? false:Boolean.parseBoolean(response.getHeader("isRegistered"));
		if(isRegistered)sources.put("isRegistered", isRegistered);
		
		boolean unregisterSuccess = response.getHeader("unregisterSuccess")==null ? false:Boolean.parseBoolean(response.getHeader("unregisterSuccess"));
		sources.put("unregisterSuccess", unregisterSuccess);

		boolean modifySuccess = response.getHeader("modifySuccess") == null? false:true;
		sources.put("modifySuccess", modifySuccess);
	}
	

	private boolean isRegisteredToEvent(String eventID,
			 String userId) throws SQLException {
			VerifyUserRegisterToEvent cmd = new VerifyUserRegisterToEvent(userId, eventID);
			if( Main.getDatabase().executeDb(cmd)){ 
				ResultSet rs = cmd.getResultSet();
				if (rs.next()) return true;
			}
		return false;
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
			}
			if(isAnotherContext(pathInfo)||pathInfo.startsWith("modify-event")||pathInfo.startsWith("delete-event")
				||pathInfo.startsWith("register-event")){
				String setLocation = "/"+pathInfo;
				RequestDispatcher dispatcher = request.getRequestDispatcher(setLocation);
				dispatcher.forward(request, response);
				return;
			}

			processRequest(request, response);
		
		}catch (Exception e){
			System.out.println("In eventHanler Post catch exception");
			catchHelper(request, response, e);
		}
	}
	
}
