package ca.diro.RequestHandlingUtil;

import java.io.IOException;

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
			Boolean modifiedSuccessfully = true;
			HttpSession session = request.getSession(true);

			String userId = getLoggedUserId(session);
			String username = (String) session.getAttribute(USERNAME_ATTRIBUTE); 
			String password = request.getParameter("passwordNew")==""?
					request.getParameter("passwordOld"):request.getParameter("passwordOld");
			String age = request.getParameter("age");
			String description = request.getParameter("description");
			//TODO:Check if the user new info is legal(no illegal characters or malicious scripts)
			
			//Modify User's Information in the database
			ModifyAccount cmd = new ModifyAccount(userId, username, age, description);
			modifiedSuccessfully = Main.getDatabase().executeDb(cmd);
			if(!password.equals("")&&modifiedSuccessfully){
				ModifyAccountPassword cmdPassword = new ModifyAccountPassword(password,userId);
				modifiedSuccessfully = Main.getDatabase().executeDb(cmdPassword);
				
			}
			if(modifiedSuccessfully){
				//add a header to the response to show a modification success message to the user
				response.setHeader("success", "Votre compte a ete modifie avec succes!");
				request.getRequestDispatcher("/membre/"+username).forward(request, response);
			}else{
				response.setHeader("error", "Il y a eu une erreur lors de la modification du compte!");
				request.getRequestDispatcher("/modifier-mes-informations/"+userId).forward(request, response);
			}
		}
		catch (Exception e){
			catchHelper( request, response, e);
		}

	}
}
