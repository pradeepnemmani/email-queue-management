package listiners;

/**
 * DOCUMENTATION
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * dots2drops
 * Saturday April 05, 2014, 10:19:20
 */
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

/**
 * @WebListener is the annotation for the class We know that using
 *              ServletContext, we can create an attribute with application
 *              scope that all other servlets can access but we can initialize
 *              ServletContext init parameters as String only in deployment
 *              descriptor (web.xml). What if our application is database
 *              oriented and we want to set an attribute in ServletContext for
 *              Database Connection. If you application has a single entry point
 *              (user login), then you can do it in the first servlet request
 *              but if we have multiple entry points then doing it everywhere
 *              will result in a lot of code redundancy. Also if database is
 *              down or not configured properly, we won’t know until first
 *              client request comes to server. To handle these scenario,
 *              servlet API provides Listener interfaces that we can implement
 *              and configure to listen to an event and do certain operations
 */
/**
 * Algorithem:-
 * 
 * 1)create servletlistener that has two fieldsKEY_GRAPH_DATABASE,DB_LOCATION
 * 2)in contextInitialize method connect to the database and create an object
 * for GraphDatabaseService 3)and add as attribute of servlet with key
 * KEY_GRAPH_DATABASE 4)in contextDestroyed method destroy the
 * GraphDatabaseService connection
 * 
 */
@WebListener
public class Connect_db implements ServletContextListener {
	public static final String KEY_GRAPH_DATABASE = "GraphDatabase";
	private static final String DB_LOCATION = "C:\\Users\\re\\Desktop\\neo4j-community-2.0.1\\data\\projectdatabase-1";
	private GraphDatabaseService graphDatabase = null;

	public Connect_db() {
		System.out.println("in constructor");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Shutting down the database....");
		if (graphDatabase != null) {
			graphDatabase.shutdown();
		}
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println(sce.getServletContext().getServletContextName());
		System.out.println("Succes");
		System.out.println("Initializing the database....");
		graphDatabase = new GraphDatabaseFactory()
				.newEmbeddedDatabase(Connect_db.DB_LOCATION);
		// Add the graphDatabase object as a attribute on the servlet context
		sce.getServletContext().setAttribute(Connect_db.KEY_GRAPH_DATABASE,
				graphDatabase);
		
	}

}
