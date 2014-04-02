package ca.diro.RequestHandlingUtil;

import java.io.IOException;
import java.sql.ResultSet;
import java.util.Date;
import java.util.Properties;

import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.DataBase;
import ca.diro.DataBase.Command.CancelEvent;
import ca.diro.DataBase.Command.DeleteEvent;
import ca.diro.DataBase.Command.PageInfoEvent;

//import javax.mail.*;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
public class CancelEventHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5278158439261391279L;

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
			HttpSession session = request.getSession(true);
			boolean isLoggedIn=session.getAttribute("auth")==null? false:true;

			String userID = (String) session.getAttribute(USER_ID_ATTRIBUTE);
			String username = (String) session.getAttribute(USERNAME_ATTRIBUTE);
			String eventID = (String) request.getParameter("id");
			Boolean deletedSuccessfully = false;

			//TODO:Get the user event info from the database
			DataBase myDb = Main.getDatabase();//new DataBase(restore);
			PageInfoEvent cmd = new PageInfoEvent(eventID,myDb);

			CancelEvent cmd2 = null;
			if( myDb.executeDb(cmd)){ 
				ResultSet rs = cmd.getResultSet();

				if (rs.next()) {
					//check if the logged user is really the owner of the event
					if(rs.getString("username").equals(username)){

						//TODO:Remove the event from the database
//						DeleteEvent cmd2 = new DeleteEvent(eventID);// (myDb);
						cmd2 = new CancelEvent(eventID, myDb);// (myDb);
						if(myDb.executeDb(cmd2)){//cmd.removeEvent(Integer.parseInt(eventID))){
//							System.out.println("Deleted event: "+eventID);
							deletedSuccessfully=true;
						}
					}
				}
			}

			if(deletedSuccessfully){
				//redirects the current request to the list of events
				//				String setLocation = "/Webapp/liste-des-evenements/";
				//				response.sendRedirect(setLocation);
				
//				CancelEvent cmd3 = new CancelEvent(eventID, myDb);
				cmd2.nofifySignedUser(eventID);
				ResultSet rs = cmd2.getListToNotify();
				if(rs.next()){
					
				}
				
				//send mail to all registered users
//				InitialContext ic = new InitialContext();
//				String snName = "java:comp/env/mail/MyMailSession";
//				Session mailSession = (Session)ic.lookup(snName);
//				
//				Properties props = mailSession.getProperties();
//				props.put("mail.from", "user2@mailserver.com");
//				
//				Message msg = new MimeMessage(mailSession);
//				msg.setSubject("msgSubject");
//				msg.setSentDate(new Date());
//				msg.setFrom();
//				msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse("bounce_sound@hotmail.com", false));
//				MimeBodyPart mbp = new MimeBodyPart();
//				mbp.setText("msgTxt");
//				Multipart mp = new MimeMultipart();
//				mp.addBodyPart(mbp);
//				msg.setContent(mp);
//				Transport.send(msg);
				
				String setLocation = "/liste-des-evenements/";
				response.addHeader("deleteSuccess", "true");
				RequestDispatcher dispacher = request.getRequestDispatcher(setLocation);
				dispacher.forward(request, response);
			}else{
				//TODO:stay on current page and show error message
				System.out.println("failled to delete event:"+eventID);
				String setLocation = "/Webapp/evenement/"+eventID;
				response.sendRedirect(setLocation);
			}
		}
		catch (Exception e){
			System.out.println("In deleteEventHanler catch exception");
			catchHelper( request, response, e);		
		}
	}

}
