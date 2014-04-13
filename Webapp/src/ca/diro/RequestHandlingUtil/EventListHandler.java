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
import ca.diro.DataBase.Command.Command;
import ca.diro.DataBase.Command.ListCancelledEvent;
import ca.diro.DataBase.Command.ListComingEvent;
import ca.diro.DataBase.Command.ListPassedEvent;
import ca.diro.DataBase.Command.PageInfoEvent;

public class EventListHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5818151764848416043L;
	
	private static final String EVENT_FUTURE = "0";
	private static final String EVENT_PAST = "1";
	private static final String EVENT_CANCELLED = "2";

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
			String pathInfo = request.getPathInfo().substring(1);

			//The current request must be a file -> redirect to requestHandler
			if(	isKnownFileExtention(pathInfo)) {
				handleSimpleRequest(request, response, pathInfo);
				return;
			}
			if(!pathInfo.equals("passes")&&!pathInfo.equals("annules")&&!pathInfo.equals("futur")&&!pathInfo.equals(""))
				if(isAnotherContext(pathInfo)){ 	        
					response.sendRedirect("/Webapp/"+pathInfo);
					return;
				}

			processRequest(request, response, pathInfo);
		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response, String pathInfo) throws IOException,
			UnsupportedEncodingException, FileNotFoundException, SQLException, ServletException {

		// create a handle to the resource
		String filename = "liste-des-evenements.html"; 
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

	private void processRequestHelper(HttpServletRequest request,
			HttpServletResponse response, String pathInfo, String filename)
			throws SQLException, UnsupportedEncodingException,
			FileNotFoundException, IOException {
		setDefaultResponseContentCharacterAndStatus(response);

		Command getListCommand;
		if(pathInfo.equals("passes"))
			getListCommand = new ListPassedEvent();
		else if(pathInfo.equals("annules"))
			getListCommand = new ListCancelledEvent();
		else getListCommand= new ListComingEvent();

		if( Main.getDatabase().executeDb(getListCommand)){ 
			ResultSet listResultSet = getListCommand.getResultSet();
			List<Event> eventList = new LinkedList<Event>();  
			while(listResultSet.next()) {
				PageInfoEvent getEventCommand = new PageInfoEvent(listResultSet.getString("eventId"), Main.getDatabase());
				int numPlacesLeft = getEventCommand.getAvailablePlaces();
				String badgeClasse = computeBadgeColor(numPlacesLeft);
				String username = "Error_retrieving_username";
				if(Main.getDatabase().executeDb(getEventCommand)){
				ResultSet currEventResultSet = getEventCommand.getResultSet();
					
					if(currEventResultSet.next())
						username = getEventCommand.getResultSet().getString("username");
				}

				eventList.add(
						new Event(username, listResultSet.getString("title"), listResultSet.getString("dateevent"),
						listResultSet.getString("location"), listResultSet.getString("description"), listResultSet.getString("eventId"),
						badgeClasse, ""+numPlacesLeft));
			}
			//add event info here!!
			HashMap<String, Object> sources = new HashMap<String, Object>();
			sources.put("events",eventList);

			//to display success message
			HttpSession session = request.getSession(true);
			boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
			
			sources.put("deleteSuccess", showDeleteSucessMessage(response));
			sources.put("addSuccess", showAddSucessMessage(response));
			sources.put("user", isLoggedIn);

			String loggedUserId = session.getAttribute(USER_ID_ATTRIBUTE)==null?"-1":(String) session.getAttribute(USER_ID_ATTRIBUTE);
			sources.put("notifications_number", countUserNotification(loggedUserId));

			processTemplate(request, response, "header.html", sources);
			processTemplate(request, response, filename, sources);
			processTemplate(request, response, "footer.html");
		}
	}

	@Override
	public void doPost(
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		try{
			String pathInfo = "";
			
			//The current request must be a file -> redirect to requestHandler
			if(	isKnownFileExtention(pathInfo)) {
				handleSimpleRequest(request, response, pathInfo);
				return;
			}
			if(!pathInfo.equals(EVENT_PAST)&&!pathInfo.equals(EVENT_FUTURE)&&!pathInfo.equals(EVENT_CANCELLED)&&!pathInfo.equals(""))
				if(isAnotherContext(pathInfo)){ 	        
					request.getRequestDispatcher("/"+pathInfo).forward(request, response);
					return;
				}

			processRequest(request, response, pathInfo);
		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}
	}
	
}
