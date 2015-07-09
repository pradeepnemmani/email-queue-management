
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

import crudclasses.IUser;
import crudclasses.User;
import crudclasses.User_CRUDOperation;

/**
 * Servlet implementation class Signup_form
 */
/**
 * @WebServlet("/signup_form") is the servelt annotation for this servlet
 */
@WebServlet("/signup_form")
public class Signup_Form extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Signup_Form() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/**
		 * response.setContentType("text/html"); is a method that is will sets
		 * the response in html formate
		 */
		response.setContentType("text/html");
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

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/signup_form.html");
		rd.include(request, response);
		/**
		 * RequestDispatcher holding the signup_form.html and including the
		 * request and response of this servlet
		 */

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
		 * response.getWriter() returns the Writer object using Writer object,
		 * we can write into servlet
		 */
		Writer out = response.getWriter();
		StringBuffer sb = new StringBuffer();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		System.out.println(dateFormat.format(date));

		/**
		 * getting the parameters which are entered by user and triming them, it
		 * removes the unnecessary white spaces between chars
		 */

		String firstName = request.getParameter("firstName").trim().toString();
		String lastName = request.getParameter("lastName").trim().toString();
		String password = request.getParameter("password").trim().toString();
		String reEnteredPassword=request.getParameter("reenterPassword").trim().toString();
		String gender = request.getParameter("gender").trim().toString();
		String age = request.getParameter("age").trim().toString();
		String dateOfBirth = request.getParameter("dateOfBirth").trim()
				.toString();
		System.out.println("entered password===" + password);
		System.out.println("reentered==="+reEnteredPassword);
		IUser user = new User(firstName, lastName, password, gender,
				dateOfBirth, age, dateFormat.format(date));
		GraphDatabaseService gds = null;
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
		System.out.println("object created" + obj);
		/**
		 * parsing the Object into GraphdatabaseService
		 */
		if (obj instanceof GraphDatabaseService) {
			gds = (GraphDatabaseService) obj;
		}

		String errorMessage = User_CRUDOperation.nullCheck(gds, user,reEnteredPassword);
		/**
		 * here errorMessage is String type that holds the error message which
		 * is returned by method User_CRUDOperation.nullCheck() this method
		 * checks for validation inputs
		 */
		if (errorMessage == null) {
			/**
			 * if errorMessage is null then if block exits here we sharing the
			 * userId between two servlets by using the method
			 * getServletContext().setAttribute(""); Creating an User object and
			 * setting the userFields, Now
			 * User_CRUDOpearstion.createUser(gds,user) creates the new user in
			 * database and generates the Unque userId
			 */
			User_CRUDOperation.createUser(gds, user);
			getServletContext().setAttribute("userId", user.getUserId());
			sb.append("<p>UserId  Generated please note </p>");
			sb.append("<p>userId:</p>" + user.getUserId());
			/**
			 * After retriving userId servlet will askvuser to fill smtp details
			 */
			sb.append("<p>Press Continue To Fill SMTP Details  </p>");

			request.setAttribute("userId", user.getUserId());
			/**
			 * to fill smtp details we are including submit.html which has
			 * Hyperlink to SmtpPage
			 */
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/submit.html");
			rd.include(request, response);
			out.write(sb.toString());
		
		}
		/**
		 * errorMessage not equal to null i.e there is wrong entered by user
		 * printing the particular errorMessage(Shows the Whats wrong that User
		 * entered ) and ask user to refill the form
		 */
		else {

			sb.append("<p>--------------------------------------------------------------------------</p>");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/signup_form.html");
			rd.include(request, response);
			/**
			 * RequestDispatcher holding the /signup_form.html and including the
			 * request and response of this servlet
			 */
			sb.append("<p>:::::WARNING MESSAGE::::</p>");
			sb.append("<p>");
			sb.append(errorMessage);
			sb.append("</p>");
			out.write(sb.toString());

		}

	}

}