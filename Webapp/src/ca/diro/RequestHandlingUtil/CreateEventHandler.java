package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
	public void doPost(
			HttpServletRequest request, HttpServletResponse response)
					throws IOException, ServletException {
		// TODO Implement handling logic for simple requests (and command
		// validation) and forwarding for requests that require specific
		// permissions or handling.
		try{
			System.out.println("In create event ");//- pathInfo:"+pathInfo);
			String userId = "2";
			HttpSession session = request.getSession(true);
			boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
System.out.println("isLoggedIn: "+isLoggedIn);			
			if(isLoggedIn){
				
				userId = (String)session.getAttribute(USER_ID_ATTRIBUTE);
				System.out.println("UserID: "+userId);			
				//TODO:Add the event from the database				
//				String userId = "2";//request.getParameter("id");
				String title = request.getParameter("eventName");
				String date = request.getParameter("eventDate");
				String location = request.getParameter("eventLocation");
				String nbplace = request.getParameter("eventNumPeople");
				String description = request.getParameter("eventDescription");

//				System.out.println("\nIn create event - Parameters:"+pathInfo+"\t"+userId+"\t"+title+"\t"+date+"\t"+location+"\t"+nbplace+"\t"+description);
				System.out.println("before database call");
				DataBase db = Main.getDatabase();//new DataBase(restore);
				//			String info = "{eventId:"+eventID+"}" ;//"1}" ;

				AddEvent cmd = new AddEvent(db);
				boolean addedSuccessfully = cmd.addNewEvent(						
						userId, 
						title, 
						date,
						location,
						nbplace.equals("Illimité") ? ""+Integer.MAX_VALUE : nbplace, 
								description
						);
				
				System.out.println("database addedSuccessfully:"+addedSuccessfully);
				//TODO: Add the event to the database
				if (addedSuccessfully){
					//TODO:get the event id
//					ResultSet rs = cmd.getResultSet();
					String id = "2";
//					if(rs.next()) id = rs.getString(1);
					System.out.println("resultset id:"+id);
					//redirects the current request to the newly created event
					String setLocation = "/Webapp/evenement/"+id;
					response.sendRedirect(setLocation);
				}else{
					//redirect the user to the create event page with the same info
					//if possible indicate to the user the reason of the failure to create the event 
				}
			}
			else{
				//redirect user to login page
				response.sendRedirect("/Webapp/connexion");
				return;
			}
		}
		catch (Exception e){
			System.out.println("In create event catch exception");
			catchHelper( request, response, e);
		}

	}


}
