package ca.diro.RequestHandlingUtil;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.diro.Main;
import ca.diro.DataBase.DataBase;
import ca.diro.DataBase.Command.Command;
import ca.diro.DataBase.Command.PageInfoEvent;

public class EventModificationPageHandler extends RequestHandler {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8940116469004925945L;

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
			String pathInfo = request.getPathInfo();
		if(pathInfo.startsWith("/")) pathInfo = pathInfo.substring(1);
//			System.out.println("in EventModification - pathInfo:"+pathInfo+"\tcontextPath:"+request.getContextPath());
//			System.out.println("in EventModification - pathInfo:"+pathInfo+"\tcontextPath:"+("/Webapp/"+pathInfo));

			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")) {
				handleToTheRessource(request, response, pathInfo);
				return;
			}else 
			
			if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
				String setLocation = "/"+pathInfo;//"/";
				request.getRequestDispatcher(setLocation).forward(request, response);
//				String setLocation = "/Webapp/"+pathInfo;//"/";
//				response.sendRedirect(setLocation);
				return;
			}

			// create a handle to the resource
			String filename = "modifier-un-evenement.html"; 

			File staticResource = new File(staticDir, filename);
			File dynamicResource = new File(dynamicDir, filename);

			// Ressource existe
			if (!staticResource.exists() && !dynamicResource.exists()){
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				processTemplate(request, response, "404.html");
			}else{
				response.setContentType("text/html");
				response.setCharacterEncoding("utf-8");
				response.setStatus(HttpServletResponse.SC_OK);

				String eventID = pathInfo;
//System.out.println("in event modification - eventID:"+eventID);
				DataBase myDb = Main.getDatabase();//new DataBase(restore);
//				String info = "{eventId:"+eventID+"}" ;//"1}" ;
				Command cmd = new PageInfoEvent(eventID,myDb);
				HashMap<String, Object> sources = new HashMap<String, Object>();

				if( myDb.executeDb(cmd)){ 
					ResultSet rs = cmd.getResultSet();

					if (rs.next())
						//TODO:Add event info here!!
						sources.put("event",
								new Event("Event_username1", rs.getString("title"), rs.getString("dateevent"),
										rs.getString("location"), rs.getString("description"), eventID,
										rs.getString("numberplaces"))
								);

					processTemplate(request, response, "header.html");

					//				String eventID = pathInfo;
					//TODO:Get the user event info from the database


					//TODO:Add event info here!!
					//				HashMap<String, Object> sources = new HashMap<String, Object>();

					//to display success message
					sources.put("id", pathInfo);
					//to display success message
//					sources.put("addSuccess", "true");
//					sources.put("isOwner", "true");
					//				sources.put("registerSuccess", "true");
					//				sources.put("unregisterSuccess", "false");
					sources.put("user", "true");
					sources.put("notifications_number", "0");

					processTemplate(request, response, filename,sources);
					processTemplate(request, response, "footer.html");
				}
			}
			//			baseRequest.setHandled(true);
		}
		catch (Exception e){
			catchHelper( request, response, e);
		}
	}
}
