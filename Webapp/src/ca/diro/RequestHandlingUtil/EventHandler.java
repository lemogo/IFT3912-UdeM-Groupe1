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
		try{
		processRequest(request, response);
		}catch (Exception e){
			System.out.println("In eventHanler GET catch exception");
			catchHelper(request, response, e);
		}
	}


	private void processRequest(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			UnsupportedEncodingException, FileNotFoundException, ServletException, SQLException {
			String pathInfo = request.getPathInfo().substring(1);

			//The current request must be a file -> redirect to requestHandler
			if(	isKnownFileExtention(pathInfo)) {
				handleSimpleRequest(request, response, pathInfo);
				return;
			}
			if(isAnotherContext(pathInfo)){
				request.getRequestDispatcher("/"+pathInfo).forward(request, response);
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

	private void processRequestHelper(HttpServletRequest request,
			HttpServletResponse response, String eventID, String filename)
					throws SQLException, UnsupportedEncodingException,
					FileNotFoundException, IOException {
		setDefaultResponseContentCharacterAndStatus(response);
		//TODO:Get the user event info from the database
		
		HttpSession session = request.getSession(true);
		boolean isLoggedIn=session.getAttribute("auth")==null? false:true;
		HashMap<String, Object> sources = new HashMap<String, Object>();
		
		String userId = "-1";
		if (isLoggedIn) userId = (String) session.getAttribute(USER_ID_ATTRIBUTE);
		if( isRegisteredToEvent(eventID, userId)) response.setHeader("isRegistered", "true");
		
		sources.putAll(buildAllMustacheSourcesInfo(response, eventID, session, isLoggedIn));

		processTemplate(request, response, "header.html",sources);
		processTemplate(request, response, filename,sources);
		processTemplate(request, response, "footer.html");
	}


	private HashMap<String, Object> isOwnerMustacheSource(
			String loggedUserUsername, String eventUsername)
			throws SQLException {
		HashMap<String, Object> sources = new HashMap<String, Object>();
		boolean isOwner = false;//response.getHeader("isOwner")!=null?true:false;
		if(eventUsername.equals(loggedUserUsername)) isOwner = true;
		if(isOwner)sources.put("isOwner", isOwner);
		return sources;
	}


	private HashMap<String, Object> buildMustacheSourcesEventInfo(String eventID,
			  HttpSession session) throws SQLException {
		HashMap<String, Object> sources = new HashMap<String, Object>();
		
		DataBase myDb = Main.getDatabase();
		PageInfoEvent pageInfoEventCommand = new PageInfoEvent(eventID,myDb);
		if( myDb.executeDb(pageInfoEventCommand)){ 
			ResultSet rs = pageInfoEventCommand.getResultSet();

			if (rs.next()) {
				int numPlacesLeft = pageInfoEventCommand.getAvailablePlaces();
				int numPlacesUsed = rs.getInt("numberplaces")-numPlacesLeft;
				sources.put("numPeople",""+numPlacesUsed);
				sources.put("creatorUsername",rs.getString("username"));
				
				String badgeClasse = computeBadgeColor(numPlacesLeft);
				sources.put("event",
						new Event(rs.getString("username"), rs.getString("title"), rs.getString("dateevent"),
								rs.getString("location"), rs.getString("description"), eventID,
								badgeClasse,""+numPlacesLeft)
						);
				sources.putAll(isOwnerMustacheSource((String)session.getAttribute(USERNAME_ATTRIBUTE), rs.getString("username")));

			}else{
				//TODO:show error message -- The event ____ does not exist
			}
		}
		return sources;
	}

	private HashMap<String, Object> buildAllMustacheSourcesInfo(HttpServletResponse response,
			String eventID, HttpSession session, boolean isLoggedIn)
					throws SQLException {
		HashMap<String, Object> sources = new HashMap<String, Object>();
		sources.put("comment", buildCommentList(eventID));
		sources.put("notifications_number", countUserNotification(session));
		sources.put("id", eventID);
		sources.put("user", isLoggedIn);
		sources.putAll(buildMustacheSourcesSuccessInfo(response, session));
		sources.putAll(buildMustacheSourcesEventInfo(eventID,session));

		return sources;
	}


	private List<Comment> buildCommentList(String eventID) throws SQLException {
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


	private HashMap<String, Object> buildMustacheSourcesSuccessInfo(HttpServletResponse response,
			HttpSession session) {
		HashMap<String, Object> sources = new HashMap<String, Object>();
		boolean addSuccess = response.getHeader("addSuccess")==null ? false:Boolean.parseBoolean(response.getHeader("addSuccess"));
		if(addSuccess) sources.put("addSuccess", addSuccess);

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
		try{
			processRequest(request, response);
		}catch (Exception e){
			System.out.println("In eventHanler Post catch exception");
			catchHelper(request, response, e);
		}
	}
}
