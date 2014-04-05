package ca.diro.RequestHandlingUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;





//import javax.mail.Message;
//import javax.mail.Multipart;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
import javax.naming.InitialContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.DataBase;
import ca.diro.DataBase.Command.ListCommentEvent;
import ca.diro.DataBase.Command.PageInfoEvent;
import ca.diro.DataBase.Command.VerifyUserRegisterToEvent;

public class EventHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6294953383525213416L;

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
		processRequest(request, response);

	}


	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			UnsupportedEncodingException, FileNotFoundException {
		try{
			String pathInfo = request.getPathInfo().substring(1);

			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")) {
				handleToTheRessource(request, response, pathInfo);
				return;
			}
			if(isAnotherContext(pathInfo)
					//					||pathInfo.startsWith("modify-event")
					//					||pathInfo.startsWith("delete-event")||pathInfo.startsWith("register-event")
					){
				String setLocation = "/Webapp/"+pathInfo;
				response.sendRedirect(setLocation);
				return;
			}

			// create a handle to the resource
			String filename = "evenement.html"; 
			File staticResource = new File(staticDir, filename);
			File dynamicResource = new File(dynamicDir, filename);

			// Ressource existe
			if (!staticResource.exists() && !dynamicResource.exists()){
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				processTemplate(request, response, "404.html");
			}
			else{
				processRequestHelper(request, response, pathInfo, filename);

				//				cmd2.nofifySignedUser(eventID);

				//send mail to all registered users
				//				InitialContext ic = new InitialContext();
				//				String snName = "java:comp/env/mail/MyMailSession";
				//				Session mailSession = (Session)ic.lookup(snName);
				//				
				//				Properties props = mailSession.getProperties();
				//				props.put("mail.from", "noreply@udmenforme.com");
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

			}
		}
		catch (Exception e){
			System.out.println("In eventHanler GET catch exception");
			catchHelper(request, response, e);
		}
	}


	private void processRequestHelper(HttpServletRequest request,
			HttpServletResponse response, String eventID, String filename)
					throws SQLException, UnsupportedEncodingException,
					FileNotFoundException, IOException {
		response.setContentType("text/html");
		response.setCharacterEncoding("utf-8");
		response.setStatus(HttpServletResponse.SC_OK);

		//TODO:Get the user event info from the database
		DataBase myDb = Main.getDatabase();
		PageInfoEvent cmd = new PageInfoEvent(eventID,myDb);
		String userId = "-1";

		HttpSession session = request.getSession(true);
		boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
		HashMap<String, Object> sources = new HashMap<String, Object>();

		if (isLoggedIn) userId = (String) session.getAttribute(USER_ID_ATTRIBUTE);
		if( isRegisteredToEvent(eventID, userId)) response.setHeader("isRegistered", "true");

		if( myDb.executeDb(cmd)){ 
			ResultSet rs = cmd.getResultSet();

			if (rs.next()) {

				sources.putAll(addAllInfoToMustacheSources(response, eventID, session, rs, cmd.getAvailablePlaces()));
				sources.put("user", isLoggedIn);
				sources.putAll(addSuccessInfoToMustacheSources(response, session, rs.getString("username")));

				processTemplate(request, response, "header.html",sources);
				processTemplate(request, response, filename,sources);
				processTemplate(request, response, "footer.html");
			}else{
				//TODO:show error message -- The event ____ does not exist
			}
		}
	}


	private HashMap<String, Object> addAllInfoToMustacheSources(HttpServletResponse response,
			String eventID, HttpSession session, ResultSet rs, int numPlacesLeft)
					throws SQLException {
		HashMap<String, Object> sources = new HashMap<String, Object>();
//		String username =rs.getString("username");

		String badgeClasse = computeBadgeColor(numPlacesLeft);
		sources.put("event",
				new Event(rs.getString("username"), rs.getString("title"), rs.getString("dateevent"),
						rs.getString("location"), rs.getString("description"), eventID,
						badgeClasse,""+numPlacesLeft)
				);
//		String numberplaces = rs.getString("numberplaces");
		int numPlacesUsed = Integer.parseInt(rs.getString("numberplaces"))-numPlacesLeft;
		sources.put("numPeople",""+numPlacesUsed);

		sources.put("title",rs.getString("title"));
		sources.put("creatorUsername",rs.getString("username"));
		//username, description, datecreation, suserid
		sources.put("comment", getCommentList(eventID));
//		sources.put("numPeople",rs.getString("numberplaces"));
		
//		String loggedUsername = session.getAttribute(USERNAME_ATTRIBUTE)==null?"Anonymous":(String) session.getAttribute(USERNAME_ATTRIBUTE);
//		String loggedUserId = session.getAttribute(USER_ID_ATTRIBUTE)==null?"-1":(String) session.getAttribute(USER_ID_ATTRIBUTE);
//		String notificationNumber = countUserNotification(loggedUserId);
		
		sources.put("notifications_number", countUserNotification(session));
		//to display success message
		sources.put("id", eventID);
		
		return sources;
	}


	private List<Comment> getCommentList(String eventID) throws SQLException {
		ListCommentEvent cmd = new ListCommentEvent(eventID);
		boolean asExecuted=Main.getDatabase().executeDb(cmd);
		List<Comment> commentList = new LinkedList<Comment>();
		if (asExecuted) {
			ResultSet rs=cmd.getResultSet();
			while(rs.next()){
				commentList.add(new Comment(
						rs.getString("username"),rs.getString("description"), 
						rs.getString("datecreation"), rs.getString("suserid")));
			}
		}
		Collections.sort(commentList,Collections.reverseOrder());
		return commentList;
	}


	private HashMap<String, Object> addSuccessInfoToMustacheSources(HttpServletResponse response,
			HttpSession session,
			String username) {
		HashMap<String, Object> sources = new HashMap<String, Object>();
		boolean addSuccess = response.getHeader("addSuccess")==null ? false:Boolean.parseBoolean(response.getHeader("addSuccess"));
		if(addSuccess) sources.put("addSuccess", addSuccess);

		boolean isOwner = response.getHeader("isOwner")!=null?true:false;
		if(username.equals(session.getAttribute(USERNAME_ATTRIBUTE))) isOwner = true;
		if(isOwner)sources.put("isOwner", isOwner);

		boolean registerSuccess = response.getHeader("registerSuccess")==null ? false:Boolean.parseBoolean(response.getHeader("registerSuccess"));
		if(registerSuccess)sources.put("registerSuccess", registerSuccess);

		boolean isRegistered = response.getHeader("isRegistered")==null ? false:Boolean.parseBoolean(response.getHeader("isRegistered"));
		if(isRegistered)sources.put("isRegistered", isRegistered);

		boolean unregisterSuccess = response.getHeader("unregisterSuccess")==null ? false:Boolean.parseBoolean(response.getHeader("unregisterSuccess"));
		sources.put("unregisterSuccess", unregisterSuccess);

		boolean modifySuccess = response.getHeader("modifySuccess") == null? false:true;
		sources.put("modifySuccess", modifySuccess);
		
		return sources;
	}


	private boolean isRegisteredToEvent(String eventID,
			String userId) throws SQLException {
		VerifyUserRegisterToEvent cmd = new VerifyUserRegisterToEvent(userId, eventID);
		if( Main.getDatabase().executeDb(cmd)){ 
			ResultSet rs = cmd.getResultSet();
			if (rs.next()) return true;
		}
		return false;
	}


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
			String pathInfo = request.getPathInfo().startsWith("/")?request.getPathInfo().substring(1):request.getPathInfo();

			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")) {
				handleToTheRessource(request, response, pathInfo);
				return;
			}
			if(isAnotherContext(pathInfo)||pathInfo.startsWith("modify-event")||pathInfo.startsWith("delete-event")
					||pathInfo.startsWith("register-event")){
				String setLocation = "/"+pathInfo;
				RequestDispatcher dispatcher = request.getRequestDispatcher(setLocation);
				dispatcher.forward(request, response);
				return;
			}

			processRequest(request, response);

		}catch (Exception e){
			System.out.println("In eventHanler Post catch exception");
			catchHelper(request, response, e);
		}
	}

}
