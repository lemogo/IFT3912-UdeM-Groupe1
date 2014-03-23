package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;

import ca.diro.Main;
import ca.diro.DataBase.DataBase;
import ca.diro.DataBase.Command.AddEvent;

public class CreateEventHandler extends RequestHandler {
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

			String userId = "2";//request.getParameter("id");
			String title = request.getParameter("eventName");
			String date = request.getParameter("eventDate");
			String location = request.getParameter("eventLocation");
			String nbplace = request.getParameter("eventNumPeople");
			String description = request.getParameter("eventDescription");

			System.out.println("\nIn create event - Parameters:"+pathInfo+"\t"+userId+"\t"+title+"\t"+date+"\t"+location+"\t"+nbplace+"\t"+description);

			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")||pathInfo.startsWith("http://")) {
				handleToTheRessource(request, response, pathInfo);
				return;
			}

			System.out.println("before database call");

			DataBase db = Main.getDatabase();//new DataBase(restore);
			//			String info = "{eventId:"+eventID+"}" ;//"1}" ;

			AddEvent cmd = new AddEvent(db);
			boolean addedSuccessfully = cmd.addNewEvent(						
					userId, 
					//					"Testing123",//
					title, 
					//					"2015-05-11 10:30:00.00",
					date,
					//					"Unversity","50",
					location,
					nbplace.equals("Illimité") ? ""+Integer.MAX_VALUE : nbplace, 
							description
							//					"description"
					);
			System.out.println("database addedSuccessfully:"+addedSuccessfully);
			//TODO: Add the event to the database

			if (addedSuccessfully){
				//redirects the current request to the newly created event
				String setLocation = "Webapp/evenement/";
				response.sendRedirect(setLocation);
//				request.getDispatcherType();
//				response.sendRedirect(setLocation);
				
//				RequestDispatcher dispatcher = request.getRequestDispatcher(setLocation);
//				dispatcher.forward(request, response);
			}else{
				//redirect the user to the event page with the same info
				//if possible indicate to the user the reason of the failure to create the event 
			}
		}
		catch (Exception e)
		{
			System.out.println("In catch exception");
//			catchHelper(baseRequest, request, response, e);
		}

	}


}
