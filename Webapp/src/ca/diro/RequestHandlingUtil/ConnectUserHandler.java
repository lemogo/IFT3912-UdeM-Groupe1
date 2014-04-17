package ca.diro.RequestHandlingUtil;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ConnectUserHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1650415398532080100L;

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
			HttpSession newUserSession = request.getSession(true);
			if(newUserSession.getAttribute("auth")!=null && newUserSession.getAttribute("auth").equals("true")){
				//TODO:verify if the user is currently login another account. 
				//If so,ask the user if he wants to logout from account X and proceed with login 
				return;
			}
			//Verify User's credential and retrieve User id from the database
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			Integer userID = authentifyUser(username, password);

			if (userID != null){
				Integer accessCount = new Integer(0);
				newUserSession.setAttribute(USER_ID_ATTRIBUTE, ""+userID);
				newUserSession.setAttribute("auth", Boolean.TRUE);
				newUserSession.setAttribute(USERNAME_ATTRIBUTE, username);
				newUserSession.setAttribute(USER_PASSWORD_ATTRIBUTE, password);
				if (newUserSession.isNew()){
				}else{
					Integer oldAccessCount = (Integer)newUserSession.getAttribute("accessCount"); 
					if (oldAccessCount != null) {
						accessCount = new Integer(oldAccessCount.intValue() + 1);
					}
				}
				newUserSession.setAttribute("accessCount", accessCount);

				//Redirects the current request to the user info page
				request.getRequestDispatcher("/membre/"+username).forward(request, response);
			}else{
				//send error message to user and return to login page
				response.setHeader("error", "Le nom d'utilisateur ou le mot de passe est incorrect. Veillez reesayer!");
				request.getRequestDispatcher("/connexion").forward(request, response);
			}
		}
		catch (Exception e){
			catchHelper( request, response, e);		
		}
	}
}
