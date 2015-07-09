package my_project;

import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import listiners.Connect_db;

import org.neo4j.graphdb.GraphDatabaseService;

import crudclasses.User_SMTP_CRUD_Operations;

/**
 * Servlet implementation class DeleteSMTP
 */
@WebServlet("/deletesmtp")
public class DeleteSMTP extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteSMTP() {
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
		getServletContext().getRequestDispatcher("/deletesmtp.html").include(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		StringBuffer sb=new StringBuffer();
		GraphDatabaseService gds=null;
		
		Writer out=response.getWriter();
		String emailId=request.getParameter("emailId").trim().toString();
	 Object obj=getServletContext().getAttribute(Connect_db.KEY_GRAPH_DATABASE);
	 if(obj instanceof GraphDatabaseService)
	 {
		 gds=(GraphDatabaseService)obj;
	 }
	String errorMessage=User_SMTP_CRUD_Operations.deleteSmtp(gds, getServletConfig().getServletContext().getAttribute("userId").toString(), emailId);
			
		if(errorMessage==null)
		{
			getServletContext().getRequestDispatcher("/option.html").include(request, response);
		}
		else
		{
			sb.append(errorMessage);
			sb.append("<p>------------------------------------------------------------------</p>");
		getServletContext().getRequestDispatcher("/deletesmtp.html").include(request, response);
		}
	}

}
