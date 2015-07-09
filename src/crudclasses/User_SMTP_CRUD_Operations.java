package crudclasses;

/**
 * DOCUMENTATION
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * dots2drops
 * Saturday April 05, 2014, 15:26:12
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;

public class User_SMTP_CRUD_Operations implements IUser_SMTP_CRUD_Operations,Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static String nullCheck(GraphDatabaseService gds, String userId,
			IUser_SMTP_Settings newSmtp) {
		/**
		 * when ever this method invokes it checks for null's
		 */

		if (gds == null) {
			/**
			 * returns WE ARE NOT ABLE TO CONNECTED TO DATABASE, PLEASE TRY
			 * LATER, so user can under stand database,
			 */

			return "WE ARE NOT  ABLE TO CONNECTED TO DATABASE, PLEASE TRY LATER";
		}
		if (newSmtp.getEmailId().trim().length() <= 0) {
			/**
			 * returns EMAILID CANNOT BE EMPTY PLEASE ENTER THE VALID EMAILID" +
			 * "\n" + "EXAMPE FOR GMAIL USER ENTER abc@gmail.com if user enter
			 * wrong emailId
			 */
			return "EMAILID CANNOT BE EMPTY PLEASE ENTER THE VALID EMAILID"
					+ "\n" + "EXAMPE FOR GMAIL USER ENTER abc@gmail.com";
		}
		if (newSmtp.getPassword().trim().length() <= 0) {
			/**
			 * returns PASSWORD CANNOT BE EMPTY PLEASE ENTER THE VALID PASSWORD
			 * when ever user keep password textbox empty
			 */
			return "PASSWORD CANNOT BE EMPTY PLEASE ENTER THE VALID PASSWORD";
		}

		if (newSmtp.getServerAddress().trim().length() <= 0) {
			/**
			 * returns SERVERADDRESS CANNOT BE EMPTY PLEASE FILL THE VALID
			 * SERVERADDRESS" + "\n" + "Ex:- FOR GMAIL USER ENTER smtp.gmail.com
			 * when ever user keep serverAddress textbox empty
			 */
			return "SERVERADDRESS CANNOT BE EMPTY PLEASE FILL THE VALID SERVERADDRESS"
					+ "\n" + "Ex:- FOR GMAIL USER ENTER smtp.gmail.com";
		}
		if (newSmtp.getSmtp_PortNo().length() <= 0) {
			/**
			 * returns PORT NUMBER CANNOT BE EMPTY PLEASE FILL THE VALID VALID
			 * PORTNO" + "\n" + "Ex:-FOR GMAIL USER ENTER PORT NO==587 when ever
			 * user keep smtp_portNo textbox empty
			 */
			return "PORT NUMBER CANNOT BE EMPTY PLEASE FILL THE VALID VALID PORTNO"
					+ "\n" + "Ex:-FOR GMAIL USER ENTER PORT NO==587";
		}
		try {
			int i = Integer.parseInt(newSmtp.getSmtp_PortNo());
			if (i <= 0) {
				/**
				 * returns the errormessage mentioned below when ever user
				 * entered less than 0
				 */
				return "PORT NUMBER CANNOT BE 0  PLEASE FILL THE VALID VALID PORTNO"
						+ "\n" + "Ex:-FOR GMAIL USER ENTER PORT NO==587";
			}

		} catch (Exception e) {

			System.out.println(e.getMessage());
			/**
			 * returns methoned below when ever if user entered String for
			 * portno
			 */
			return "PORT NUMBER ACCEPTS ONLY NUMERIC VALUE  PORT NUMBER CANNOT BE EMPTY PLEASE FILL THE VALID VALID PORTNO"
					+ "\n" + "Ex:-FOR GMAIL USER ENTER PORT NO==587";
		}
		if(newSmtp.getPriority().length()!=0)
		{
		try {
			int i = Integer.parseInt(newSmtp.getPriority());
			if (i <= 0) {
				/**
				 * retruns if entered value for priority is 0 or lessthan
				 * 0,because this priority going to used for thread priority
				 */
				return "PRIORITY ACCEPTS  NUMERIC VALUE 1-10";
			}
			if (i > 10) {
				return "PRIORITY ACCEPTS  NUMERIC VALUE 1-10";
			}

		} catch (Exception e) {
			/**
			 * returnns back if entered value for priority is String, because
			 * this priority going to used for thread priority
			 */
			
		return "PRIORITY ACCEPTS ONLY NUMERIC VALUE 1-10,IT DOES NOT ACCEPTS NON NUMERIC ";
		}
		}
		if (newSmtp.getNo_of_mailsPermited().length() <= 0) {
			/**
			 * returns mentioned below when ever user entered
			 * no_of_mailspermitted is empty
			 */

			return "NO_OF_MAILS PERMITED CANNOT BE EMPTY PLEASE ENTER VALID NUMERIC VALUE"
					+ "\n" + "ENTER ONLY +INTEGERS";
		}
		try {
			int i = Integer.parseInt(newSmtp.getNo_of_mailsPermited());
			if (i <= 0) {
				/**
				 * returns mentioned below when ever user entered for
				 * No_of_mailsPermited is less than 0
				 */

				return "NO_OF_MAILPERMITED CANNOT BE 0 OR LESS THAN 0" + "\n"
						+ "ENTER ONLY +INTEGERS";
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			/**
			 * returns mentioned below when ever user enter for
			 * No_of_mailsPermited is String , because it acceptsonly integer
			 * value
			 */
			return "NO_OF_MAILSPERMITED ACCEPTS ONLY +NUMERIC VALUE " + "\n"
					+ "ENTER ONLY +INTEGERS";
		}

		/**
		 * checking for entered emailId exist in database
		 */
		ExecutionEngine ee = new ExecutionEngine(gds);
		ExecutionResult er = ee
				.execute("MATCH(u:smtp_details) RETURN u.emailId");
		for (Map<String, Object> entry : er) {
			if (newSmtp.getEmailId().equals(entry.get("u.emailId"))) {
				/**
				 * if user entered enterd emailid exist returns metioned as
				 * below
				 */
				return "----------------SORRY--------------" + "\n"
						+ "ENTERED EMAILID IS ALLREADY EXIST";
			}
		}
		return null;
	}

	public static void createUserSmtpDetails(GraphDatabaseService gds,
			IUser_SMTP_Settings newSmtp, String userId) {
		/**
		 * this method will craetes the newsmtp details for User
		 */
		ExecutionEngine ee = new ExecutionEngine(gds);
		int count = 1;
		ExecutionResult er = ee.execute("MATCH(u:User{userId:'" + userId
				+ "'})<-[r:SMTP_DETAILS]-(s:smtp_details) RETURN s");
		for (Map<String, Object> entry : er) {
			count++;
		}
		try (Transaction tx = gds.beginTx()) {
			/**
			 * Algorithem:- 1)take Dynamic label for User 2)Take a DynamicLable
			 * for Smtp_Details 3)create a node for DynamicLabel(Smtp_Details)
			 * 4)set prooperties for node 5)create a relation with User
			 * 
			 */
			Label label = DynamicLabel.label("smtp_details");
			Label userLabel = DynamicLabel.label("User");
			Node node = gds.createNode(label);
			node.setProperty("emailId", newSmtp.getEmailId());
			node.setProperty("password", newSmtp.getPassword());
			node.setProperty("serverAddress", newSmtp.getServerAddress());
			node.setProperty("smtp_portNo", newSmtp.getSmtp_PortNo());
			node.setProperty("priority", newSmtp.getPriority());
			node.setProperty("no_of_mailsPermitted",
					newSmtp.getNo_of_mailsPermited());
			node.setProperty("quotaRemaining", newSmtp.getNo_of_mailsPermited());
			node.setProperty("smtpIdNo", count);
			newSmtp.setSmtpIdNo(count);
			ResourceIterable<Node> nodes = gds.findNodesByLabelAndProperty(
					userLabel, "userId", userId);

			for (Node entry : nodes) {

				node.createRelationshipTo(entry, ERelation.SMTP_DETAILS);
				tx.success();

			}

		}

	}

	public static IUser_SMTP_Settings retrive(GraphDatabaseService gds,
			String userId)

	{
		/**
		 * Algorithem:- 1)Execute the query and get parameters 2)create
		 * User_SMTP-Details object 3)set the parameters of User_SMTP-Details
		 * object 4)return User_SMTP-Details object
		 */
		ExecutionEngine ee = new ExecutionEngine(gds);
		ExecutionResult er = ee
				.execute("MATCH(u:User{userId:'"
						+ userId
						+ "'})<-[:SMTP_DETAILS]-(s) RETURN s.serverAddress,s.smtp_portNo,s.no_of_mailsPermitted,s.quotaRemaining,s.smtpIdNo");
		for (Map<String, Object> entry : er) {
			System.out.println("retriving the user smtp details");
			IUser_SMTP_Settings userSmtp = new User_SMTP_Settings();
			userSmtp.setServerAddress(entry.get("s.serverAddress").toString());
			userSmtp.setSmtp_PortNo(entry.get("s.smtp_portNo").toString());
			userSmtp.setNo_of_mailsPermited(entry.get("s.no_of_mailsPermitted")
					.toString());
			userSmtp.setSmtpIdNo(Integer.parseInt(entry.get("s.smtpIdNo")
					.toString()));

			userSmtp.setQuotaRemaining(Integer.parseInt(entry.get(
					"s.quotaRemaining").toString()));

			return userSmtp;
		}
		return null;

	}

	public static List<User_SMTP_Settings> smtpDetails(
			GraphDatabaseService gds, String userId) {
		List<User_SMTP_Settings> smtpList = new ArrayList<>();

		ExecutionEngine ee = new ExecutionEngine(gds);
		ExecutionResult er = ee
				.execute("MATCH(u:User{userId:'"
						+ userId
						+ "'})<-[:SMTP_DETAILS]-(s) RETURN s.serverAddress,s.smtp_portNo,s.no_of_mailsPermitted,s.quotaRemaining,s.smtpIdNo,s.emailId,s.password,s.priority");
		for (Map<String, Object> entry : er) {

			User_SMTP_Settings smtp = new User_SMTP_Settings();
			smtp.setEmailId(entry.get("s.emailId").toString());
			smtp.setPassword(entry.get("s.password").toString());
			smtp.setServerAddress(entry.get("s.serverAddress").toString());
			smtp.setSmtp_PortNo(entry.get("s.smtp_portNo").toString());
smtp.setPriority(entry.get("s.priority").toString());
			smtp.setNo_of_mailsPermited(entry.get("s.no_of_mailsPermitted")
					.toString());
			smtp.setQuotaRemaining(Integer.parseInt(entry
					.get("s.quotaRemaining").toString().toString()));
			smtp.setSmtpIdNo(Integer.parseInt(entry.get("s.smtpIdNo")
					.toString()));

			smtpList.add(smtp);
		}
		System.out.println("no of smtp details returned===" + smtpList.size());
		return smtpList;

	}
	public static String deleteSmtp(GraphDatabaseService gds,String userId,String emailId)
	{
		if(emailId.length()==0)
		{
			return "PLEASE ENTER EMAILID";
		}
		ExecutionEngine ee=new ExecutionEngine(gds);
		ee.execute("MATCH(u:User{userId:'"+userId+"'})<-[r:SMTP_DETAILS]-(s:smtp_details{emailId:'"+emailId+"'}) DELETE r, s");
		return null;
		
	}
}
