package ca.diro.RequestHandlingUtil;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import ca.diro.Main;
import ca.diro.DataBase.DataBase;
import ca.diro.DataBase.Command.Command;
import ca.diro.DataBase.Command.ListCancelledEvent;
import ca.diro.DataBase.Command.ListComingEvent;
import ca.diro.DataBase.Command.ListPassedEvent;

public class EventListHandler extends RequestHandler {
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
		try
		{

			String pathInfo = request.getPathInfo().substring(1);
			System.out.println("In event list - pathInfo="+pathInfo+"\tcontext="+request.getContextPath());

			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")) {
				System.out.println("In event list - pathInfo="+pathInfo+"\tcontext="+request.getContextPath());
				//				super.handle(target, baseRequest, request, response);
				handleToTheRessource(request, response, pathInfo);
				return;
			}
			if(!pathInfo.equals("passes")&&!pathInfo.equals("annules")&&!pathInfo.equals("futur"))
				if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
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
				response.setContentType("text/html");
				response.setCharacterEncoding("utf-8");
				response.setStatus(HttpServletResponse.SC_OK);

//System.out.println("before database command");
				//TODO:Get the user lisr event info from the database
				Command cmd= new ListComingEvent();
				if(pathInfo.equals("passes"))
					cmd = new ListPassedEvent();
				else if(pathInfo.equals("annules"))
					cmd = new ListCancelledEvent();

				DataBase myDb = Main.getDatabase();//new DataBase(restore);
				if( myDb.executeDb(cmd)){ 
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
					//				sources.put("addSuccess", "true");
					boolean isLoggedIn=request.getRequestedSessionId()==null? false:true;
					System.out.println("\nUser is login is:"+request.getRequestedSessionId()+"\n");//.request.getRequestedSessionId()==null? false:true;
					//to display user navbar
					sources.put("user", isLoggedIn);

					sources.put("notifications_number", "0");

					processTemplate(request, response, "header.html", sources);
					processTemplate(request, response, filename, sources);
					processTemplate(request, response, "footer.html");
				}
				//			baseRequest.setHandled(true);
			}
		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}

	}

}
