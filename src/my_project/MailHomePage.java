package my_project;

/**
 DOCUMENTATION
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * Saturday April 05, 2014, 13:08:24
 * dots2drops
 */
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import listiners.Connect_db;

import org.neo4j.graphdb.GraphDatabaseService;

import crudclasses.IMail;
import crudclasses.Mail;
import crudclasses.Mail_CRUDOperation;

/**
 * Servlet implementation class MailHomePage
 */
/**
 * MailHomePage Servlet from this servet user can send mails
 */

/*
 * @WebServlet("/home") is the servelt annotation for the servlet home
 */
@WebServlet("/mailhomepage")
public class MailHomePage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GraphDatabaseService gds;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MailHomePage() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		/**
		 * An object of ServletContext is created by the web container at time
		 * of deploying the project. This object can be used to get
		 * configuration information from web.xml file. There is only one
		 * ServletContext object per web application from
		 * getServletContext().getAttribute(Connect_db.KEY_GRAPH_DATABASE);
		 * return the object
		 */

		Object obj = getServletContext().getAttribute(
				Connect_db.KEY_GRAPH_DATABASE);
		/**
		 * after getting the object convert the object into graphDatabaseService
		 */
		if (obj instanceof GraphDatabaseService) {
			gds = (GraphDatabaseService) obj;
		}
		/**
		 * response.setContentType("text/html"); is a method that is will sets
		 * the response in html formate
		 */

		response.setContentType("text/html");
		/**
		 * The StringBuffer class are used when there is a necessity to make a
		 * lot of modifications to Strings of characters. Unlike Strings objects
		 * of type StringBuffer can be modified over and over again .
		 */
		StringBuffer sb = new StringBuffer();
		/**
		 * defines an object to assist a servlet in sending a response to the
		 * client. The servlet container creates a ServletResponse object and
		 * passes it as an argument to the servlet's service method.
		 */
		Writer out = response.getWriter();
		sb.append("<p>USER ID:");
		System.out.println((String) getServletConfig().getServletContext()
				.getAttribute("userId"));
		/**
		 * getServletConfig().getServletContext().getAttribute("userId") will
		 * share the object or variable between two servlets
		 */
		sb.append((String) getServletConfig().getServletContext().getAttribute(
				"userId"));
		sb.append("</p>");
		getServletContext().getRequestDispatcher("/addMails.html").include(
				request, response);
		/**
		 * A RequestDispatcher object can forward a client's request to a
		 * resource or include the resource itself in the response back to the
		 * client. A resource can be another servlet, or an HTML file, or a JSP
		 * file, etc. You can also think of a RequestDispatcher object as a
		 * wrapper for the resource located at a given path that is supplied as
		 * an argument to the getRequestDispatcher method. For constructing a
		 * RequestDispatcher object, you can use either the
		 * ServletRequest.getRequestDispatcher() method or the
		 * ServletContext.getRequestDispatcher() method.
		 */
		getServletContext().getRequestDispatcher("/signout.html").include(
				request, response);
		sb.append("<p>USER ID:</p>");
		out.write(sb.toString());
		/**
		 * when ever new user created ThreadControl object will created and
		 * starts execution like retriving and sending mails from database
		 */
//		new ThreadControl(gds, getServletConfig().getServletContext()
//				.getAttribute("userId").toString());
		System.out.println("in MailHomePage===="+getServletConfig().getServletContext().getAttribute("userId").toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/**
		 * response.setContentType("text/html"); is a method that is will sets
		 * the response in html formate
		 */
		response.setContentType("text/html");
		/**
		 * defines an object to assist a servlet in sending a response to the
		 * client. The servlet container creates a ServletResponse object and
		 * passes it as an argument to the servlet's service method.
		 */
		Writer out = response.getWriter();
		/**
		 * The StringBuffer class are used when there is a necessity to make a
		 * lot of modifications to Strings of characters. Unlike Strings objects
		 * of type StringBuffer can be modified over and over again .
		 */
		StringBuffer sb = new StringBuffer();
		/**
		 * DateFormat is an abstract class for date/time formatting subclasses
		 * which formats and parses dates or time in a language-independent
		 * manner. The date/time formatting subclass, such as SimpleDateFormat,
		 * allows for formatting (i.e., date -> text), parsing (text -> date),
		 * and normalization.The date is represented as a Date object or as the
		 * milliseconds
		 */
		DateFormat dateFormate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		GraphDatabaseService gds = null;
		/**
		 * retriving the parameters from same servlets and parsing to string the
		 * trim method of String class that removes unnecessary white spaces
		 * between two characters
		 */
		String to = request.getParameter("to").toString().trim();
		String subject = request.getParameter("subject").toString().trim();
		String body = request.getParameter("message").toString().trim();
		String entryDate = dateFormate.format(date);
		System.out.println(to + subject + body);

		Object obj = getServletContext().getAttribute(
				Connect_db.KEY_GRAPH_DATABASE);
		if (obj instanceof GraphDatabaseService) {
			gds = (GraphDatabaseService) obj;
		}
		/**
		 * here errorMessage is String type that holds the error message which
		 * is returned by method Mail_CRUDOperation.checkForNull this method
		 * checks for validation inputs
		 */
		String errorMessage = Mail_CRUDOperation.checkForNull(gds, to);
		/**
		 * if errorMessage is null allowing to creatinmg the seperate node for
		 * every mail and allowing to show same servlet for add extra mails id's
		 */
		if (errorMessage == null) {
			sb.append("<p>SUCESS</p>");

			String[] allTo = to.split(",");
			System.out.println("allto=========" + allTo.length);
			for (int inx = 0; inx < allTo.length; inx++) {
				IMail mail = new Mail(allTo[inx], subject, body, entryDate);
				Mail_CRUDOperation.addMail(gds, mail, getServletConfig()
						.getServletContext().getAttribute("userId").toString());

			}
			/**
			 * A RequestDispatcher object can forward a client's request to a
			 * resource or include the resource itself in the response back to
			 * the client. A resource can be another servlet, or an HTML file,
			 * or a JSP file, etc. You can also think of a RequestDispatcher
			 * object as a wrapper for the resource located at a given path that
			 * is supplied as an argument to the getRequestDispatcher method.
			 * For constructing a RequestDispatcher object, you can use either
			 * the ServletRequest.getRequestDispatcher() method or the
			 * ServletContext.getRequestDispatcher() method.
			 */

			getServletContext().getRequestDispatcher("/addMails.html").include(
					request, response);
			getServletContext().getRequestDispatcher("/signout.html").include(
					request, response);

		}

		/**
		 * if errorMessage!=null that means wrong parameters passed by user and
		 * printing the particular error message and asking the user to enter
		 * valid inut
		 */
		else {
			sb.append("<h5>");
			sb.append(errorMessage);
			sb.append("<h5>");
			getServletContext().getRequestDispatcher("/addMails.html").include(
					request, response);
			getServletContext().getRequestDispatcher("/signout.html").include(
					request, response);

		}
		out.write(sb.toString());

	}

}
