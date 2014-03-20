package ca.diro.RequestHandlingUtil;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import ca.diro.Main;
import ca.diro.DataBase.DataBase;
import ca.diro.DataBase.Command.Command;
import ca.diro.DataBase.Command.PageInfoEvent;

public class EventHandler extends RequestHandler {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jetty.server.Handler#handle(java.lang.String,
	 * org.eclipse.jetty.server.Request, javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse)
	 */
	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		// TODO Implement handling logic for simple requests (and command
		// validation) and forwarding for requests that require specific
		// permissions or handling.
		try
		{

			String pathInfo = request.getPathInfo().substring(1);
			System.out.println("in Event - pathInfo:"+pathInfo+"\tcontextPath:"+request.getContextPath());
			System.out.println("in Event - pathInfo:"+pathInfo+"\tcontextPath:"+("/Webapp/"+pathInfo));

			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")) {
				super.handle(target, baseRequest, request, response);
				return;
			}
			if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
				redirectToPathContext(target, baseRequest, request, response,
						pathInfo);
				return;
			}


			// create a handle to the resource
			String filename = "evenement.html"; 

			File staticResource = new File(staticDir, filename);
			File dynamicResource = new File(dynamicDir, filename);

			// Ressource existe
			if (!staticResource.exists() && !dynamicResource.exists())
			{
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				processTemplate(request, response, "404.html");
			}
			else
			{
				response.setContentType("text/html");
				response.setCharacterEncoding("utf-8");
				response.setStatus(HttpServletResponse.SC_OK);
				String eventID = pathInfo;

				processTemplate(request, response, "header.html");


				//TODO:Get the user event info from the database
				DataBase myDb = Main.getDatabase();//new DataBase(restore);
				String info = "{eventId:"+eventID+"}" ;//"1}" ;
				Command cmd = new PageInfoEvent(info,myDb);
				boolean boo = myDb.executeDb(cmd); 
				ResultSet rs = cmd.getResultSet();

				if (rs.next()) {
					//title,dateevent,location, numberplaces, description
//					System.out.println("Database value:"+rs.getString("title")+"\t"+rs.getString("dateevent"));

					//TODO:Add event info here!!
					HashMap sources = new HashMap();
					sources.put("event",
							new Event("Event_username1", rs.getString("title"), rs.getString("dateevent"),
									rs.getString("location"), rs.getString("description"), eventID,
									rs.getString("numberplaces"))
							);

					//to display success message
					sources.put("addSuccess", "false");
					sources.put("isOwner", "false");
					//				sources.put("registerSuccess", "true");
					//				sources.put("unregisterSuccess", "false");
					sources.put("user", "false");
					sources.put("notifications_number", "0");


					processTemplate(request, response, filename,sources);
					//				System.out.println("in eventHandler after template");
					processTemplate(request, response, "footer.html");
				}else{
					//TODO:show error message -- The event ____ does not exist
				}
			}

			baseRequest.setHandled(true);
		}
		catch (Exception e)
		{
			catchHelper(baseRequest, request, response, e);
		}

	}

}
