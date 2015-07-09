package crudclasses;

/**
 * DOCUMENTATION
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * dots2drops
 * Saturday April 05, 2014, 11:19:50
 */
import java.util.LinkedList;

import java.util.Map;
import java.util.Queue;
import java.util.UUID;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.ResourceIterable;
import org.neo4j.graphdb.Transaction;

public class Mail_CRUDOperation  implements IMail_CRUDOperation{

	public static String checkForNull(GraphDatabaseService gds, String m) {
		if (gds == null) {
			/**
			 * returns the "DATABASE CONNECTION LOSSED PLEASE TRY LATER" when
			 * ever database connection lossed
			 */

			return "DATABASE CONNECTION LOSSED PLEASE TRY LATER";
		}
		if (!(m.trim().length() > 0)) {
			/**
			 * returns "TO BLOCK CANNOT BE EMPTY" when ever user keep to block
			 * empty
			 */
			return "TO BLOCK CANNOT BE EMPTY";
		}

		return null;

	}

	public static void addMail(GraphDatabaseService gds, IMail mail, String id) {
		/**
		 * Algorithem:- 1)Take dynamicLabel for Email 2)And take Dynamiclabel
		 * for User 3)create node for Email label 4)set the properties 5)make
		 * relation user as EMAIL_DETAILS
		 * 
		 */
		try (Transaction tx = gds.beginTx()) {
			Label mailLabel = DynamicLabel.label("Email");
			Label userLabel = DynamicLabel.label("User");

			Node mailNode = gds.createNode(mailLabel);
			String mid = UUID.randomUUID().toString();
			mailNode.setProperty("mid", mid);
			System.out.println(mail);
			mailNode.setProperty("to", mail.getTo());
			mailNode.setProperty("subject", mail.getSubject());
			mailNode.setProperty("body", mail.getBody());
			mailNode.setProperty("entryDate", mail.getDateOfEnter());
			mailNode.setProperty("entryTime", System.currentTimeMillis());
			ResourceIterable<Node> userNode = gds.findNodesByLabelAndProperty(
					userLabel, "userId", id);
			System.out.println(id);
			System.out.println(mid);
			System.out.println(mail.getTo() + " is added into database ");

			for (Node entry : userNode) {
				Relationship rs = mailNode.createRelationshipTo(entry,
						ERelation.EMAIL_DETAILS);
				if (rs != null) {
					System.out
							.println(" mail stored into database and relation created with user ");

				} else {
					System.out.println("mail not stored");

				}
			}

			tx.success();

		}

	}

	public static Queue<Mail> mailRetriver(GraphDatabaseService gds,
			String userId, int skip, int limit) {
		/**
		 * Algorithem:- 1)create variable mails of type Queue<Mail> and
		 * initialise as LinkedList<>(); 2)execute the query ang retrieve the
		 * paqrameters 3)create a Mail object and set the properties and add to
		 * mails. 4)return mails
		 */
		Queue<Mail> mails = new LinkedList<>();
		ExecutionEngine ee = new ExecutionEngine(gds);
		ExecutionResult er = ee
				.execute("MATCH(u:User{userId:'"
						+ userId
						+ "'})<-[r:EMAIL_DETAILS]-(e:Email) RETURN e.mid,e.to,e.subject,e.body,e.entryTime,e.entryDate ORDER BY e.entrytime ASC SKIP"
						+ "\t" + skip + "\t" + "LIMIT" + "\t" + limit + " ");
		System.out.println("skip===" + skip + "\n" + "limit===" + limit);
		for (Map<String, Object> entry : er) {
			Mail mail = new Mail();

			mail.setTo(entry.get("e.to").toString());
			mail.setSubject(entry.get("e.subject").toString());
			mail.setBody(entry.get("e.body").toString());
			mail.setId(entry.get("e.mid").toString());
			mails.add(mail);

		}
		System.out.println("no of mails retrived from database===="
				+ mails.size());
		return mails;

	}

	public static void deleteMail(GraphDatabaseService gds, String userId,
			String mailId) {
		/**
		 * Algorithem:- 1)execute the queue and get tge parameters 2)and delete
		 * the mail node with respect to the userId
		 */
		if (!(mailId == null)) {
			ExecutionEngine ee = new ExecutionEngine(gds);
			ExecutionResult er1 = ee.execute("MATCH(u:User{userId:'" + userId
					+ "'})<-[r:EMAIL_DETAILS]-(e:Email{mid:'" + mailId
					+ "'}) DELETE r,e");
			if (er1 != null) {
				System.out.println("mail with mid==" + mailId + " is deleted ");
			} else {
				System.out.println("not deleted");
			}

		}
	}



}
