package my_project;

/**
 DOCUMENTATION
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * Saturday April 05, 2014, 15:10:32
 * dots2drops
 */
import java.io.IOException;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import listiners.Connect_db;

import org.neo4j.graphdb.GraphDatabaseService;

import crudclasses.IUser_SMTP_Settings;
import crudclasses.User_SMTP_CRUD_Operations;
import crudclasses.User_SMTP_Settings;

/**
 * Servlet implementation class SMTP_Configuration
 */
/**
 * @WebServlet("/signup_Configuration") is the servelt annotation for this
 *                                      servlet
 */
@WebServlet("/smtp_Configuration")
public class SMTP_Configuration extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GraphDatabaseService gds;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SMTP_Configuration() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		/**
		 * response.setContentType("text/html"); is a method that is will sets
		 * the response in html formate
		 */
		/**
		 * RequestDispatcher holding the user_smtp_settingd.html and including
		 * the request and response of this servlet
		 */
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/user_smtp_settings.html");
		rd.include(request, response);
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

		Object obj = getServletContext().getAttribute(
				Connect_db.KEY_GRAPH_DATABASE);
		/**
		 * after getting the object convert the object into graphDatabaseService
		 */
		if (obj instanceof GraphDatabaseService) {
			gds = (GraphDatabaseService) obj;
		}
		new ThreadControl(gds, getServletConfig().getServletContext()
				.getAttribute("userId").toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		/**
		 * response.getWriter() returns the Writer object using Writer object,
		 * we can write into servlet
		 */
		response.setContentType("text/html");

		Writer out = response.getWriter();
		GraphDatabaseService gds = null;
		StringBuffer sb = new StringBuffer();
		/**
		 * response.getWriter() returns the Writer object using Writer object,
		 * we can write into servlet
		 */
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));
		/**
		 * getting the parameters which are entered by user and triming them, it
		 * removes the unnecessary white spaces between chars
		 */
		String id = getServletConfig().getServletContext()
				.getAttribute("userId").toString();
		System.out.println("In POST==" + id);
		String emailId = request.getParameter("emailId").trim().toString();
		String password = request.getParameter("password").trim().toString();
		String serverAddress = request.getParameter("serverAddress").trim()
				.toString();
		String priority=request.getParameter("priority").trim().toString();
		String portNo = request.getParameter("portNo").trim().toString();
		String no_ofMailsPermited = request.getParameter("no_ofMailsPermited")
				.trim().toString();
		/**
		 * creating an object for User_SMTP_Settings that holdes
		 * emailId,password,serverAddress,portNo,no_of_mails permitted,date of
		 * registered
		 */

		IUser_SMTP_Settings newSmtp = new User_SMTP_Settings(emailId, password,
				serverAddress, portNo, no_ofMailsPermited,priority,
				dateFormat.format(date));
		/**
		 * An object of ServletContext is created by the web container at time
		 * of deploying the project. This object can be used to get
		 * configuration information from web.xml file. There is only one
		 * ServletContext object per web application from
		 * getServletContext().getAttribute(Connect_db.KEY_GRAPH_DATABASE);
		 * return the Object
		 */
		Object obj = getServletContext().getAttribute(
				Connect_db.KEY_GRAPH_DATABASE);
		/**
		 * parsing the Object into GraphdatabaseService
		 */
		if (obj instanceof GraphDatabaseService) {
			gds = (GraphDatabaseService) obj;
		}
		String errorMessage = User_SMTP_CRUD_Operations.nullCheck(gds,
				getServletConfig().getServletContext().getAttribute("userId")
						.toString(), newSmtp);
		/**
		 * here errorMessage is String type that holds the error message which
		 * is returned by method User_CRUDOperation.nullCheck() this method
		 * checks for validation inputs
		 */
		if (errorMessage == null) {
			/**
			 * if errorMessage is null create an node for User_SMTP_Details and
			 * creating relation with User for that we are passing userId
			 */
			User_SMTP_CRUD_Operations.createUserSmtpDetails(
					gds,
					newSmtp,
					getServletConfig().getServletContext()
							.getAttribute("userId").toString());
			System.out.println("smtp_loaded");
			/**
			 * this will ask user to fill another smtp if not exist siomplt user
			 * has to click continue to send mails
			 */
			sb.append("<p> IF YOU HAVE A ANOTHER SMTP SERVER  FILL THE FORM AGAIN ");
			getServletContext()
					.getRequestDispatcher("/user_smtp_settings.html").include(
							request, response);
			sb.append("<p>click CONTINUE to send mails</p>");
			sb.append("<a href='/my_project/mailhomepage'><i class='fa fa-plus-square fa-2x'></i>CONTINUE</a>");
			out.write(sb.toString());
		} else {
			/**
			 * if errorMessage is not null it means user has enterd a wrong details 
			 * so, this will prints the particular error and ask user to fill again
			 */
			sb.append("<p>");
			sb.append(errorMessage);
			sb.append("</p>");
			sb.append("<p>---------------------------------------------------------------------------------</p>");
			sb.append("<p>PLEASE FILL THE FORM AGAIN </p>");

			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/user_smtp_settings.html");
			rd.include(request, response);
			out.write(sb.toString());

		}

	}

}
