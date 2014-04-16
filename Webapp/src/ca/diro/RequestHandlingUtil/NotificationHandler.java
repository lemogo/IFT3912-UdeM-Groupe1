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
		try{
			processRequestHelper(request, response);
		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}
	}

	private void processRequestHelper(HttpServletRequest request,
			HttpServletResponse response) throws UnsupportedEncodingException,
			FileNotFoundException, IOException, JSONException, SQLException, ServletException {
		//pathInfo should be null
		String pathInfo = request.getPathInfo();
		if(pathInfo!=null)pathInfo=request.getPathInfo().startsWith("/")?request.getPathInfo().substring(1):request.getPathInfo();
		else pathInfo = "";

		//The current request must be a file -> redirect to requestHandler
		if(	isKnownFileExtention(pathInfo)) {
			handleSimpleRequest(request, response, pathInfo);
			return;
		}
		if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
			String setLocation = "/Webapp/"+pathInfo;
			response.sendRedirect(setLocation);
			//			request.getRequestDispatcher("/"+pathInfo).forward(request, response);
			return;
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
					throws JSONException, SQLException {
		HashMap<String, Object> sources = new HashMap<String, Object>();

		HttpSession session = request.getSession(true);
//		boolean isLoggedIn=session.getAttribute("auth")==null? false:(boolean)session.getAttribute("auth");
		if(!isLoggedIn(session)){
			//TODO:if user is not logged in redirect user to login page to view is page
			return sources;
		}

		List<Notification> notificationList = new LinkedList<Notification>();
		String loggedUserId = getLoggedUserId(session);
		if(!loggedUserId.equals("-1")){
		ListUserNotification listUserNotificationCommand = new ListUserNotification(loggedUserId);
		if(Main.getDatabase().executeDb(listUserNotificationCommand)){
			ResultSet rs = listUserNotificationCommand.getResultSet();
			while (rs.next()){
				//eventid, title, location, dateevent, description
				notificationList.add(
						new Notification(rs.getString("eventid"), 
								//							"Bidon_Username",//
								rs.getString("username"), 
								rs.getString("title")));
			}}
		}
		sources.put("notificationsList", notificationList);
		sources.putAll(addSuccessMessagesToMustacheSources(response, isLoggedIn(session)));
		sources.put("notifications_number", countUserNotification(getLoggedUserId(session)));
		return sources;
	}

	private HashMap<String, Object> addSuccessMessagesToMustacheSources(
			HttpServletResponse response,
			boolean isLoggedIn) {
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
}
