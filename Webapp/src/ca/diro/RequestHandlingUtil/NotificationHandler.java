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
import ca.diro.DataBase.Command.ListUserNotification;

public class NotificationHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 705551389229047945L;

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
			if(pathInfo!=null)pathInfo=request.getPathInfo().startsWith("/")?request.getPathInfo().substring(1):request.getPathInfo();
			else pathInfo = "";

			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")) {
//				super.doGet(request, response);
				handleSimpleRequest(request, response, pathInfo);
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

//	/*
//	 * (non-Javadoc)
//	 * 
//	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
//	 * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
//	 * javax.servlet.http.HttpServletResponse)
//	 */
//	@Override
//	public void doPost(
//			HttpServletRequest request, HttpServletResponse response)
//					throws IOException, ServletException {
//		// TODO Implement handling logic for simple requests (and command
//		// validation) and forwarding for requests that require specific
//		// permissions or handling.
//		try{
//			String pathInfo = request.getPathInfo().startsWith("/")?request.getPathInfo().substring(1):request.getPathInfo();
//
//			//The current request must be a file -> redirect to requestHandler
//			if(	pathInfo.contains(".")) {
//				handleToTheRessource(request, response, pathInfo);
//				return;
//			}else if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
//				String setLocation = "/Webapp/"+pathInfo;
//				response.sendRedirect(setLocation);
//				return;
//			}
//			processRequestHelper(request, response);
//		}
//		catch (Exception e){
//			catchHelper( request, response, e);		
//		}
//	}

	private void processRequestHelper(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			FileNotFoundException, IOException, JSONException, SQLException {
		// create a handle to the resource
		String pathInfo = request.getPathInfo();
		if(pathInfo!=null)pathInfo=request.getPathInfo().startsWith("/")?request.getPathInfo().substring(1):request.getPathInfo();
		else pathInfo = "";

//		String pathInfo = request.getPathInfo();
//		if(pathInfo.startsWith("/")) pathInfo = pathInfo.substring(1);
		
		if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
			String setLocation = "/Webapp/"+pathInfo;
			response.sendRedirect(setLocation);
			return;
		}

		if(pathInfo.equals("")){
			//check if user is logged in

			//TODO:if user is not logged in redirect user to login page to view is page
		}

		String filename = "notifications.html"; 
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
	}

	private HashMap<String, Object> addAllInfoToMustacheSources(
			HttpServletRequest request, HttpServletResponse response)
			throws JSONException, SQLException {
		//Add User info here!!
		HashMap<String, Object> sources = new HashMap<String, Object>();

		//TODO:Get the user id using the database and/or if there's no path info the id from the session variable 
		HttpSession session = request.getSession(true);
		boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
		
		int userId = Integer.parseInt((String) (session.getAttribute(USER_ID_ATTRIBUTE)==null?-1:session.getAttribute(USER_ID_ATTRIBUTE)));
//		boolean isOwner = userId>0;
//		sources.put("isOwner", isOwner);

		List<Notification> notificationList = new LinkedList<Notification>();
		ListUserNotification cmd = new ListUserNotification(""+userId);
		Main.getDatabase().executeDb(cmd);
		
		ResultSet rs = cmd.getResultSet();
		while (rs.next()){
			//eventid, title, location, dateevent, description
			//TODO: get username and tittle;
			notificationList.add(
					new Notification(rs.getString("eventid"), 
//							"Bidon_Username",//
							rs.getString("username"), 
							rs.getString("title")));
		}
		sources.put("notificationsList", notificationList);

		addSuccessMessagesToMustacheSources(response, sources, isLoggedIn);
		
		String loggedUserId = session.getAttribute(USER_ID_ATTRIBUTE)==null?"-1":(String) session.getAttribute(USER_ID_ATTRIBUTE);
		sources.put("notifications_number", countUserNotification(loggedUserId));
		return sources;
	}

	private void addSuccessMessagesToMustacheSources(
			HttpServletResponse response, HashMap<String, Object> sources,
			boolean isLoggedIn) {
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
	}

	private void setResponseContentCharacterAndStatus(
			HttpServletResponse response) {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
	}

}
