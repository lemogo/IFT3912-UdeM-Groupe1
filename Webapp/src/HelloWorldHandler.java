import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;



/**
 * Ce handler prend n'importe quelle requete et retourne la chaine de 
 * caractere: HelloWorld
 * @author vaucher
 *
 */
public class HelloWorldHandler extends AbstractHandler {

	@Override
	public void handle(String target, Request baseRequest,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		Map<String, String[]> parameterMap = request.getParameterMap();
		
		// Code HTTP -> 200 indique que la page a ete trouvee
		response.setStatus(200);
		ServletOutputStream outputStream = response.getOutputStream();
		PrintWriter writer = new PrintWriter(outputStream);
		writer.println("<html>");
		writer.println("  <head>");
		writer.println("  <meta content=\"text/html; charset=UTF-8\" http-equiv=\"content-type\"/>");
		writer.println("  <title>");
		writer.println("  </title>");
		writer.println("</head>");
		writer.println("<body>");
		writer.println("  HelloWorld");
		
		writer.println("form method=\"post\"><p>Nom : <input type=\"text\" name=\"nom\"/><input type=\"submit\" value=\"Go!\"/></p></form>");
		
		
		
		if(parameterMap.get("nom")!=null){
			writer.println("Bonjour " + parameterMap.get("nom")[0]);
			FileOutputStream fout = new FileOutputStream("C:\\Users\\Islem\\Desktop\\file.log", true);
			PrintWriter logWriter = new PrintWriter(fout);
			logWriter.println(parameterMap.get("nom")[0]+"\n" + new Date().toString());
			logWriter.close();
		}
		writer.println("</body>");
		writer.println("</html>");
		writer.close();
	}
}
