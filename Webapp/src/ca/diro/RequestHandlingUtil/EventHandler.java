package ca.diro.RequestHandlingUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.DataBase;
import ca.diro.DataBase.Command.Command;
import ca.diro.DataBase.Command.PageInfoEvent;
import ca.diro.DataBase.Command.VerifyUserRegisterToEvent;

public class EventHandler extends RequestHandler {
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
		processRequestHelper(request, response);

	}


	public void processRequestHelper(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			UnsupportedEncodingException, FileNotFoundException {
		try{
			String pathInfo = request.getPathInfo().substring(1);
//			System.out.println("in Event GET - pathInfo:"+pathInfo+"\tcontextPath:"+request.getContextPath());
//			System.out.println("in Event GET - pathInfo:"+pathInfo+"\tcontextPath:"+("/Webapp/"+pathInfo));

			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")) {
				handleToTheRessource(request, response, pathInfo);
				return;
			}
			if(pathInfo.startsWith("modify-event")||pathInfo.startsWith("delete-event")
					||pathInfo.startsWith("register-event")){
				String setLocation = "/Webapp/"+pathInfo;
				response.sendRedirect(setLocation);
				return;
			}else if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
				String setLocation = "/Webapp/"+pathInfo;//"/";
				response.sendRedirect(setLocation);
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

				//TODO:Get the user event info from the database
				DataBase myDb = Main.getDatabase();//new DataBase(restore);
//				String info = "{eventId:"+eventID+"}" ;//"1}" ;
				PageInfoEvent cmd = new PageInfoEvent(eventID,myDb);

				if( myDb.executeDb(cmd)){ 
					ResultSet rs = cmd.getResultSet();

					if (rs.next()) {
						//title,dateevent,location, numberplaces, description
						//					System.out.println("Database value:"+rs.getString("title")+"\t"+rs.getString("dateevent"));

						String username =rs.getString("username");
						//TODO:Add event info here!!
						HashMap<String, Object> sources = new HashMap<String, Object>();
						sources.put("event",
								new Event(username, rs.getString("title"), rs.getString("dateevent"),
										rs.getString("location"), rs.getString("description"), eventID,
										rs.getString("numberplaces"))
								);

						String userId = "null";
						//to display success message
						sources.put("id", eventID);

						HttpSession session = request.getSession(true);
						boolean isLoggedIn=session.getAttribute("auth")==null? false:true;

						System.out.println("Event isLoggedIn:"+isLoggedIn+"\t"+request.getRequestedSessionId());//.request.getRequestedSessionId()==null? false:true;
						
						if (isLoggedIn){
							sources.put("user", isLoggedIn);
							userId = (String) session.getAttribute(USER_ID_ATTRIBUTE);
						}

						boolean addSuccess = response.getHeader("addSuccess")==null ? false:Boolean.parseBoolean(response.getHeader("addSuccess"));
						if(addSuccess)sources.put("addSuccess", addSuccess);
						sources.put("addSuccess", addSuccess);
						
						boolean isOwner = response.getHeader("isOwner")!=null?true:false;
//							System.out.println("\nisOwner=true");
						if(username.equals(session.getAttribute(USERNAME_ATTRIBUTE))) isOwner = true;
						if(isOwner)sources.put("isOwner", isOwner);

						boolean registerSuccess = response.getHeader("registerSuccess")==null ? false:Boolean.parseBoolean(response.getHeader("registerSuccess"));
						if(registerSuccess)sources.put("registerSuccess", registerSuccess);
						
						boolean isRegistered = response.getHeader("isRegistered")==null ? false:Boolean.parseBoolean(response.getHeader("isRegistered"));
						if(!isRegistered){
							VerifyUserRegisterToEvent cmd2 = new VerifyUserRegisterToEvent(userId, eventID);
							if( myDb.executeDb(cmd2)){ 
								rs = cmd2.getResultSet();
								if (rs.next()) isRegistered = true;
							}
						}
						if(isRegistered)sources.put("isRegistered", isRegistered);
						
						boolean unregisterSuccess = response.getHeader("unregisterSuccess")==null ? false:Boolean.parseBoolean(response.getHeader("unregisterSuccess"));
						sources.put("unregisterSuccess", unregisterSuccess);
System.out.println("unregisterSuccess:"+unregisterSuccess+"registerSuccess:"+registerSuccess+"\tisRegistered:"+isRegistered+" "+response.getHeader("isRegistered"));
						
						sources.put("notifications_number", "0");

						processTemplate(request, response, "header.html",sources);
						processTemplate(request, response, filename,sources);
						processTemplate(request, response, "footer.html");
					}else{
						//TODO:show error message -- The event ____ does not exist
					}
				}

				//				baseRequest.setHandled(true);
			}
		}
		catch (Exception e){
			System.out.println("In eventHanler GET catch exception");
			catchHelper(request, response, e);
		}
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
		try
		{
			//redirects, via post, the form
//			System.out.println("before checking isOwner"+request.getHeader("isOwner"));
//			if(response.getHeader("isOwner")!=null) {
//				System.out.println("\nisOwner=true");
//			}
			
			String pathInfo = request.getPathInfo().startsWith("/")?request.getPathInfo().substring(1):request.getPathInfo();
			System.out.println("in Event, context Post - pathInfo:"+pathInfo+"\tcontextPath:"+request.getContextPath());
			System.out.println("in Event, context Post - pathInfo:"+pathInfo+"\tcontextPath:"+("/Webapp/"+pathInfo));

			//The current request must be a file -> redirect to requestHandler
			if(	pathInfo.contains(".")) {
				handleToTheRessource(request, response, pathInfo);
				return;
			}
			if(pathInfo.startsWith("modify-event")||pathInfo.startsWith("delete-event")
//					){
				||pathInfo.startsWith("register-event")){
				String setLocation = 
//						request.getServletPath();
				"/"+pathInfo;
				System.out.println("\nredirecting to:"+setLocation+"\tid:"+request.getParameter("id"));
//				if(request.getMethod().equals("POST")){
				RequestDispatcher dispatcher = request.getRequestDispatcher(setLocation);
				dispatcher.forward(request, response);
//				}else
//				{
//					setLocation = "/"+pathInfo;
//				response.sendRedirect(setLocation);
//				}
				return;
			}else if(isAnotherContext(pathInfo)&&!pathInfo.equals("")){ 	        
				String setLocation = "/"+pathInfo;//"/";
				RequestDispatcher dispatcher = request.getRequestDispatcher(setLocation);
				dispatcher.forward(request, response);
				return;
			}

			processRequestHelper(request, response);
		
		}catch (Exception e){
			System.out.println("In eventHanler Post catch exception");
			catchHelper(request, response, e);
		}

	}

	
}
