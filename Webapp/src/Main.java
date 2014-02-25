import org.eclipse.jetty.server.Server;


public class Main {
	public static final int DEFAULT_PORT = 8080;
	public static void main(String[] args) throws Exception {
		Server server = new Server(DEFAULT_PORT);
		server.setHandler(new MustacheHandler());
		server.start();
		server.join();
	}
}
