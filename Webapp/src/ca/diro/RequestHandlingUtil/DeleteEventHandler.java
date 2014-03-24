package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ca.diro.Main;
import ca.diro.DataBase.DataBase;
import ca.diro.DataBase.Command.DeleteEvent;

public class DeleteEventHandler extends RequestHandler {
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
		System.out.println("In delete event");
		try
		{
//			HttpSession session = request.getSession(true);
//			boolean isLoggedIn=session.getAttribute("auth")==null? false:true;

			
//			String userID = "2";
			String eventID = (String) request.getParameter("id");//"65";
			Boolean deletedSuccessfully = false;
			System.out.println("eventID:"+eventID);
//			if(isLoggedIn){
//				userID = (String)session.getAttribute(USER_ID_ATTRIBUTE);
				//TODO:Remove the event from the database
				DataBase myDb = Main.getDatabase();//new DataBase(restore);
				String info = "{eventId: "+eventID+"}" ;//"1}" ;
				DeleteEvent cmd = new DeleteEvent(info,myDb);
//				if( myDb.executeDb(cmd)){ 
				if(cmd.removeEvent(eventID)){
//					ResultSet rs = cmd.getResultSet();
//					if (rs.next());
					System.out.println("Deleted event: "+eventID);
					deletedSuccessfully=true;
//				}
				
				}
//			if(!isLoggedIn) deletedSuccessfully =false;
				System.out.println("deletedSuccessfully:"+deletedSuccessfully);

			if(deletedSuccessfully){
				//redirects the current request to the list of events
				String setLocation = "/Webapp/liste-des-evenements/";
				response.sendRedirect(setLocation);
			}else{
				//TODO:stay on current page and show error message
				System.out.println("failled to create new user account");
				String setLocation = "/Webapp/evenement/"+eventID;
				response.sendRedirect(setLocation);
			}
		}
		catch (Exception e){
			System.out.println("In deleteEventHanler catch exception");
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
//	public void doGet(
//			HttpServletRequest request, HttpServletResponse response)
//					throws IOException, ServletException {
//		// TODO Implement handling logic for simple requests (and command
//		// validation) and forwarding for requests that require specific
//		// permissions or handling.
//		System.out.println("In delete event");
//		try
//		{
////			HttpSession session = request.getSession(true);
////			boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
//
//			
//			String userID = "2";
//			String eventID = (String) request.getParameter("id")+"";//"65";
//			Boolean deletedSuccessfully = false;
//			System.out.println("eventID:"+eventID);
////			if(isLoggedIn){
////				userID = (String)session.getAttribute(USER_ID_ATTRIBUTE);
//				//TODO:Remove the event from the database
//				DataBase myDb = Main.getDatabase();//new DataBase(restore);
//				String info = "{eventId: "+eventID+"}" ;//"1}" ;
//				DeleteEvent cmd = new DeleteEvent(info,myDb);
////				if( myDb.executeDb(cmd)){ 
//				if(cmd.removeEvent(info)){
////					ResultSet rs = cmd.getResultSet();
////					if (rs.next());
//					System.out.println("Deleted event: "+eventID);
//					deletedSuccessfully=true;
////				}
//				
//				}
////			if(!isLoggedIn) deletedSuccessfully =false;
//				System.out.println("deletedSuccessfully:"+deletedSuccessfully);
//
//			if(deletedSuccessfully){
//				//redirects the current request to the list of events
//				String setLocation = "/Webapp/liste-des-evenements/";
//				response.sendRedirect(setLocation);
//			}else{
//				//TODO:stay on current page and show error message
//				System.out.println("failled to create new user account");
//				String setLocation = "/Webapp/evenement/"+userID;
//				response.sendRedirect(setLocation);
//			}
//		}
//		catch (Exception e){
//			System.out.println("In deleteEventHanler catch exception");
//			catchHelper( request, response, e);		
//		}
//	}

}
