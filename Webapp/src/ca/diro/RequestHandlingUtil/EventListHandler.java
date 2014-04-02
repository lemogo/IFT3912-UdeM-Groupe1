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

public class EventListHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5818151764848416043L;

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
		try{
			processRequest(request, response);
		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}
	}

	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			UnsupportedEncodingException, FileNotFoundException, SQLException {
		String pathInfo = request.getPathInfo().substring(1);
//		System.out.println("In event list - pathInfo="+pathInfo+"\tcontext="+request.getContextPath());

		//The current request must be a file -> redirect to requestHandler
		if(	pathInfo.contains(".")) {
//			System.out.println("In event list - pathInfo="+pathInfo+"\tcontext="+request.getContextPath());
			handleToTheRessource(request, response, pathInfo);
			return;
		}
		if(!pathInfo.equals("passes")&&!pathInfo.equals("annules")&&!pathInfo.equals("futur")&&!pathInfo.equals(""))
			if(isAnotherContext(pathInfo)){ 	        
				String setLocation = "/Webapp/"+pathInfo;//"/";
				response.sendRedirect(setLocation);
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
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		Command cmd;
		if(pathInfo.equals("passes"))
			cmd = new ListPassedEvent();
		else if(pathInfo.equals("annules"))
			cmd = new ListCancelledEvent();
		else cmd= new ListComingEvent();

		if( Main.getDatabase().executeDb(cmd)){ 
			ResultSet rs = cmd.getResultSet();
			List<Event> eventList = new LinkedList<Event>();  
			while(rs.next()) 
				eventList.add(
						new Event("Event_Bidon_username", rs.getString("title"), rs.getString("dateevent"),
						rs.getString("location"), rs.getString("description"), rs.getString("eventId"),
						"Event_badgeClass1"));

			//add event info here!!
			HashMap<String, Object> sources = new HashMap<String, Object>();
			sources.put("events",eventList);

			//to display success message
			HttpSession session = request.getSession(true);
			boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
			boolean addSuccess = showAddSucessMessage(request, response);
			boolean deleteSuccess = showDeleteSucessMessage(request,
					response);
			
			//to display user navbar
			sources.put("deleteSuccess", deleteSuccess);
			sources.put("addSuccess", addSuccess);
			sources.put("user", isLoggedIn);
			sources.put("notifications_number", "0");

			processTemplate(request, response, "header.html", sources);
			processTemplate(request, response, filename, sources);
			processTemplate(request, response, "footer.html");
		}
	}

	private boolean showAddSucessMessage(HttpServletRequest request,
			HttpServletResponse response) {
//		System.out.println("before checking addSuccess"+request.getHeader("addSuccess"));
		if(response.getHeader("addSuccess")!=null) {
			return Boolean.parseBoolean(response.getHeader("addSuccess"));
		}
		return false;
	}

	private boolean showDeleteSucessMessage(HttpServletRequest request,
			HttpServletResponse response) {
		if(response.getHeader("deleteSuccess")!=null) {
			return Boolean.parseBoolean(response.getHeader("deleteSuccess"));
		}
		return false;
	}

	@Override
	public void doPost(
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		// TODO Implement handling logic for simple requests (and command
		// validation) and forwarding for requests that require specific
		// permissions or handling.
		try{
			processRequest(request, response);
		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}
	}
	
}
