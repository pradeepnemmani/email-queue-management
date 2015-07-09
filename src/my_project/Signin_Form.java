package my_project;

/**
 * DOCUMENTATION
 * 
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * dots2drops
 * * Saturday April 05, 2014, 13:06:08
 */

import java.io.IOException;
import java.io.Writer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import listiners.Connect_db;

import org.neo4j.graphdb.GraphDatabaseService;


import crudclasses.User_CRUDOperation;

/**
 * Servlet implementation class Signin_form
 */
/**
 * @WebServlet("/signin_form") is the servelt annotation for this servlet
 */
@WebServlet("/signin_form")
public class Signin_Form extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Signin_Form() {
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
				"/signin_form.html");
		rd.include(request, response);
		/**
		 * RequestDispatcher holding the signin_form.html and including the
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

		GraphDatabaseService gds = null;
		String userId = (String) request.getParameter("userId").trim();
		String password = request.getParameter("password").trim().toString();

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
		 * parsing the Object into GraphdatabaseService
		 */
		if (obj instanceof GraphDatabaseService) {
			gds = (GraphDatabaseService) obj;
		}
		/**
		 * here errorMessage is String type that holds the error message which
		 * is returned by method CheckUserDetails.checkUserDetails() this method
		 * checks for validation inputs
		 */

		String errorMessage=User_CRUDOperation.checkUserDetails(gds, userId, password);
		/**
		 * if errorMessage is null allowing to creatinmg the seperate node for
		 * every mail and allowing to show same servlet for add extra mails id's
		 */
		if (errorMessage == null) {
			/**
			 * getServletConfig().getServletContext().getAttribute("userId")
			 * will share the object or variable between two servlets Now we are
			 * sharing the "userId";
			 */
			getServletContext().setAttribute("userId", userId);
			/**
			 * if user has 1 or more smtp's, user can enter as many no of smtp's
			 * if there is no errorMessage
			 */
			getServletContext().getRequestDispatcher("/option.html").include(request, response);
		
		}
		/**
		 * if errorMessage!=null that means wrong parameters passed by user and
		 * printing the particular error message and asking the user to enter
		 * valid inut
		 */
		else {
			sb.append("<p>WARNING MESSAGE:::::</p>");

			sb.append(errorMessage);
			sb.append("</p>");
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
			
			getServletContext().getRequestDispatcher("/signin_form.html")
					.include(request, response);
			/**
			 * RequestDispatcher holding the signin_form.html and including the
			 * request and response of this servlet
			 */
		}
		out.write(sb.toString());

	}
}