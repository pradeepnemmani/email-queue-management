package crudclasses;

/**
 * DOCUMENTATION
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * dots2drops
 * Saturday April 05, 2014, 14:20:52
 */
import java.io.Serializable;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;

public class User_CRUDOperation implements Serializable, IUser_CRUDOperation{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String nullCheck(GraphDatabaseService gds, IUser user,String reenteredPassword) {

		if (gds == null) {
			/**
			 * returns if gds ==null
			 */
			return "UNABLE TO CONNECT TO DATABASE PLEASE TRY LATER";
		}
		if (user.getFirstName().length() <= 0) {
			/**
			 * returns "USERNAME CANNOT BE EMPTY" if user entered for label
			 * firstName is empty
			 */
			return "USERNAME CANNOT BE EMPTY";
		}
		if (user.getLastName().length() <= 0) {
			/**
			 * returns "LASTNAME CANNOT BE EMPTY" if user entered for label
			 * lastName is empty
			 */
			return "LASTNAME CANNOT BE EMPTY";
		}
		if (user.getPassword().length() <= 0) {
			/**
			 * returns "PASSWORD CANNOT BE EMPTY" if user entered for label
			 * firstName is empty
			 */
			return "PASSWORD CANNOT BE EMPTY";
		}
		if (reenteredPassword.length() <= 0) {
			/**
			 * returns "REENTERPASSWORD CANNOT BE EMPTY" if user entered for label
			 * REENTERPASSWORD is empty
			 */
			return "REENTERPASSWORD CANNOT BE EMPTY";
		}

		if (user.getDateOfBirth().length() <= 0) {
			/**
			 * returns "DATEOFBIRTH CANNOT BE EMPTY" if user entered for label
			 * dateOfBirth is empty
			 */
			return "DATEOFBIRTH CANNOT BE EMPTY";
		}
		try {
			int i = Integer.parseInt(user.getAge());
			if (i <= 0) {
				/**
				 * returns "USER AGE CANNOT BE 0 OR LESS THAN 0" if user entered
				 * for label age is less than 0
				 */
				return "USER AGE CANNOT BE 0 OR LESS THAN 0";
			}
		} catch (Exception e) {
			/**
			 * returns "USER AGE CANNOT BE 0 OR LESS THAN 0" if user entered for
			 * label age is String
			 */
			return "USER AGE ACCEPTS ONLY NUMERIC VALUE ";
		}
		if(!(user.getPassword().equals(reenteredPassword)))
		{
			return "PLEASE ENTER THE SAME PASSWORD IN REENTERPASSWORD";
		}
			
		return null;
	}

	public static void createUser(GraphDatabaseService gds, IUser user) {
		/**
		 * Algorithem:- 1)execute Query and get the parameters 2)count no of
		 * nodes returned 3)and increment count with 1, create a new user node
		 * and generate user id as count+1
		 */
		int count = 1;
		ExecutionEngine ee = new ExecutionEngine(gds);
		ExecutionResult er = ee.execute("MATCH(u:User) RETURN  u");
		for (Map<String, Object> entry : er) {
			count++;
		}
		try (Transaction tx = gds.beginTx();) {
			/**
			 * Algorithem:- 1)take a Dynamiclable for User 2)craete anode for
			 * lable 3)set the properties 4)and set userid
			 */
			Label users = DynamicLabel.label("User");
			Node userNode = gds.createNode(users);
			userNode.setProperty("firstName", user.getFirstName());

			userNode.setProperty("lastName", user.getLastName());
			userNode.setProperty("age", user.getAge());
			userNode.setProperty("dateOfBirth", user.getDateOfBirth());
			userNode.setProperty("gender", user.getGender());
			userNode.setProperty("userId", "idNo" + count);
			userNode.setProperty("password", user.getPassword());

			userNode.setProperty("dateOfRegistered", user.getRegisteredDate());

			user.setUserId("idNo" + count);

			System.out.println("user created with userId==" + user.getUserId());
			tx.success();

		}

	}

	public static String checkUserDetails(GraphDatabaseService gds, String id,
			String password) {
		if (gds == null) {
			/**
			 * returns "DATABASE CONNECTION LOSS PLEASE TRY LATER" if gds==null
			 */
			return "DATABASE CONNECTION LOSS PLEASE TRY LATER";
		}
		if (id == null) {
			/**
			 * returns "PLEASE ENTER THE VALID USERID"if id==null
			 */
			return "PLEASE ENTER THE VALID USERID";
		}
		if (id.trim().length() == 0) {
			/**
			 * returns "PLEASE ENTER THE VALID USERID"; if id.length==0
			 */
			return "PLEASE ENTER THE VALID USERID";
		}
		if (password.trim().length() == 0) {
			/**
			 * returns "PLEASE ENTER THE VALID PASSWORD"; if password.length==0
			 */
			return "PLEASE ENTER THE VALID PASSWORD";
		}
		/**
		 * execute the query retrieve the node and check for validation of id if
		 * exist returns null if not exist return "PLEASE ENTER VALID USERID"
		 */
		ExecutionEngine ee = new ExecutionEngine(gds);
		ExecutionResult er = ee.execute("MATCH(u:User{userId:'" + id
				+ "'}) RETURN u.password");
		if (er == null) {
			return "INVALID USERID";
		} else {
			for (Map<String, Object> enrty : er) {
				if (!(password.equals(enrty.get("u.password").toString()))) {
					return "INVALID PASSWORD";
				}
			}
			return null;

		}
	}
	
}
