package ca.diro.RequestHandlingUtil;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ca.diro.Main;
import ca.diro.DataBase.Command.ModifyAccount;
import ca.diro.DataBase.Command.ModifyAccountPassword;

public class ModifyUserInfoHandler extends RequestHandler {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4302156479740043175L;

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
		try
		{
			HttpSession session = request.getSession(true);
			String userId = ""+authentifyUser(session);
			String username = (String) session.getAttribute(USERNAME_ATTRIBUTE); 
			String fullname = request.getParameter("fullname"); 
			String age = request.getParameter("age");
			String description = request.getParameter("description");
			//TODO:Check if the user new info is legal(no illegal characters or malicious scripts)
			
			//Modify User's Information in the database
			ModifyAccount cmd = new ModifyAccount(userId, fullname, age, description);
			if(Main.getDatabase().executeDb(cmd)){
				//add a header to the response to show a modification success message to the user
				response.setHeader("modificationSuccess", "true");
			}else{
				response.setHeader("error", "Il y a eu une erreur lors de la modification du compte!");
				request.getRequestDispatcher("/modifier-mes-informations/"+username).forward(request, response);
				return;
			}
			//modify password
			try{
				modifyPassword(request, response, session);
			}catch(Exception e){
				response.setHeader("error", "Il y a eu une erreur lors de du mot de passe!");
				request.getRequestDispatcher("/modifier-mes-informations/"+username).forward(request, response);
				return;
			}
			request.getRequestDispatcher("/membre/"+username).forward(request, response);
		}
		catch (Exception e){
			catchHelper( request, response, e);
		}
	}

	private void modifyPassword(HttpServletRequest request,
			HttpServletResponse response, HttpSession session
			) throws ServletException, IOException, SQLException {
		String password = request.getParameter("passwordNew")==""?
				request.getParameter("passwordOld"):request.getParameter("passwordNew");
		if(password.equals(""))return;
		String userId = getLoggedUserId(session);

		ModifyAccountPassword cmdPassword = new ModifyAccountPassword(password,userId);
		if(Main.getDatabase().executeDb(cmdPassword)){
			session.setAttribute(USER_PASSWORD_ATTRIBUTE, password);
			response.setHeader("modificationSuccess", "Votre compte a ete modifie avec succes!");
		}else{
			response.setHeader("error", "Il y a eu une erreur lors de du mot de passe! Velliez reesayer!");
		}
	}
}
