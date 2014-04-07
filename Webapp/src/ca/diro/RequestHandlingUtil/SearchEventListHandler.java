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
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.Command.PageInfoEvent;
import ca.diro.DataBase.Command.ResearchEvent;

public class SearchEventListHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5818151764848416043L;

	@Override
	public void doPost(
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		try{
			processRequest(request, response);
		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			UnsupportedEncodingException, FileNotFoundException, SQLException, ServletException {
		String pathInfo = request.getPathInfo();
		if (pathInfo == null) pathInfo="";
		else pathInfo = pathInfo.substring(1);

		//The current request must be a file -> redirect to requestHandler
		if(	pathInfo.contains(".")) {
			handleSimpleRequest(request, response, pathInfo);
			return;
		}
		
		//the pathInfo should be null
//		if(!pathInfo.equals("passes")&&!pathInfo.equals("annules")&&!pathInfo.equals("futur")&&!pathInfo.equals(""))
			if(isAnotherContext(pathInfo)){ 	        
				String setLocation = "/Webapp/"+pathInfo;//"/";
				response.sendRedirect(setLocation);
//				request.getRequestDispatcher("/"+pathInfo).forward(request, response);
				return;
			}

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
		
		HashMap<String, Object> sources = new HashMap<String, Object>();
		sources.putAll(buildSearchEventListMustacheSources(request));
		
		HttpSession session = request.getSession(true);
		sources.put("user", isLoggedIn(session));
		sources.put("notifications_number", countUserNotification(getLoggedUserId(session)));

		processTemplate(request, response, "header.html", sources);
		processTemplate(request, response, filename, sources);
		processTemplate(request, response, "footer.html");
	}

	private HashMap<String, Object> buildSearchEventListMustacheSources(
			HttpServletRequest request )
			throws SQLException {
		HashMap<String, Object> sources = new HashMap<String, Object>();
		LinkedList<String> searchInput = new LinkedList<String>(Arrays.asList(request.getParameter("searchInput").split(" ")));
		ResearchEvent researchComand = new ResearchEvent(searchInput);
		if(Main.getDatabase().executeDb(researchComand)){
			ResultSet listResultSet = researchComand.getResultSet();
			List<Event> eventList = new LinkedList<Event>();  
			while(listResultSet.next()) {
				PageInfoEvent getEventCommand = new PageInfoEvent(listResultSet.getString("eventId"), Main.getDatabase());
				int numPlacesLeft = getEventCommand.getAvailablePlaces();
				String badgeClasse = computeBadgeColor(numPlacesLeft);

				eventList.add(
						new Event("Event_Bidon_username", listResultSet.getString("title"), listResultSet.getString("dateevent"),
						listResultSet.getString("location"), listResultSet.getString("description"), listResultSet.getString("eventId"),
						badgeClasse, ""+numPlacesLeft));
			}
			//add event info here!!
			sources.put("events",eventList);
		}
		return sources;
	}
}
