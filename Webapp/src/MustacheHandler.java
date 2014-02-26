import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

/**
 * Exemple de code qui utilise jMustache pour formatter du html
 * 
 * @author vaucher
 * 
 */
public class MustacheHandler extends AbstractHandler
{

	// La racine des ressources a presenter
	private static File	rootDir		= new File(".");

	// Ressources statiques -> ne seront pas interpretees par Mustache
	private static File	staticDir	= new File(rootDir, "static");

	// Ressources dynamiques -> seront interpretees par Mustache
	private static File	dynamicDir	= new File(rootDir, "templates");

	@Override
	public void handle(String target, Request r, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	{
		try
		{

			String pathInfo = request.getPathInfo();
			if (pathInfo.endsWith("/"))
				pathInfo += "index.html";

			// create a handle to the resource
			File staticResource = new File(staticDir, pathInfo.substring(1));
			File dynamicResource = new File(dynamicDir, pathInfo.substring(1));

			// A changer pour supporter des images, peut-etre par extension ou
			// par repertoire
			if (target.endsWith(".css"))
			{
				response.setContentType("text/css");
			}
			else
			{
				response.setContentType("text/html");
			}
			response.setCharacterEncoding("utf-8");

			String filename = pathInfo.substring(1);

			// Ressource existe
			if (!staticResource.exists() && !dynamicResource.exists())
			{
				response.setStatus(HttpServletResponse.SC_NOT_FOUND);
				processTemplate(request, response, "404.html");
			}
			else if (staticResource.exists())
			{
				response.setStatus(HttpServletResponse.SC_OK);
				copy(staticResource, response.getOutputStream());
			}
			else
			{
				response.setStatus(HttpServletResponse.SC_OK);
				processTemplate(request, response, filename);
			}

			r.setHandled(true);
		}
		catch (Exception e)
		{
			// Pour deboggage, on va afficher le stacktrace
			Map<String, String> params = new HashMap<String, String>();
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			PrintStream pout = new PrintStream(out);
			e.printStackTrace(pout);
			params.put("stacktrace", out.toString());
			out.close();

			// Template d'erreur
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			processTemplate(request, response, "500.html", params);
			r.setHandled(true);
		}
	}

	/**
	 * 
	 * @param staticres
	 *            non null, existing file, not checked here
	 * @param outputStream
	 *            non null
	 * @throws IOException
	 *             if there is a copy error
	 */
	private void copy(File staticres, OutputStream outputStream) throws IOException
	{
		FileInputStream fis = new FileInputStream(staticres);
		copy(fis, outputStream);
		fis.close();
	}

	/**
	 * @param in
	 *            non null input stream
	 * @param out
	 *            non null output stream
	 * @return the number of bytes
	 * @throws IOException
	 *             if underlying error arises
	 */
	private long copy(InputStream in, OutputStream out) throws IOException
	{
		long bytes = 0;
		byte[] buffer = new byte[1024];
		int len = in.read(buffer);
		bytes += len;
		while (len != -1)
		{
			out.write(buffer, 0, len);
			len = in.read(buffer);
			bytes += len;
		}
		return bytes;
	}

	/**
	 * Delegates template execution
	 * 
	 * @param req
	 *            sent by the http container
	 * @param res
	 *            sent by the http container
	 * @param filename
	 *            , identifies the template used
	 * @param scopes
	 *            , extra parameters set by caller
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void processTemplate(HttpServletRequest req, HttpServletResponse res, String filename, Object... scopes) throws UnsupportedEncodingException, FileNotFoundException, IOException
	{
		MustacheFactory mc = new DefaultMustacheFactory(dynamicDir);
		Mustache mustache = mc.compile(filename);

		Map parameters = new HashMap<Object, Object>(req.getParameterMap())
		{
			@Override
			public Object get(Object o)
			{
				Object result = super.get(o);
				if (result instanceof String[])
				{
					String[] strings = (String[]) result;
					if (strings.length == 1)
					{
						return strings[0];
					}
				}
				return result;
			}
		};

		List<Object> scs = new ArrayList<Object>();
		scs.add(parameters);
		for (Object o : scopes)
		{
			scs.add(o);
		}

		mustache.execute(res.getWriter(), scs.toArray());
	}

	private String simpleEscape(String string)
	{
		return string.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;");
	}
}
