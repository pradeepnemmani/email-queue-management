package my_project;
/**
 * DOCUMENTATION
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * dots2drops
 * Saturday April 05, 2014, 13:06:08
 */

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @WebServlet("/home") is the servelt annotation for the servlet home
 */
@WebServlet("/home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Home() {
		super();

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
				"/home.html");
		rd.include(request, response);

	}

}
