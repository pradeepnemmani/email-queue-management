package my_project;

/**
 * DOCUMENTATION
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * Saturday April 05, 2014, 13:08:24
 * dots2drops
 */
/**
 *This is Mail controller calss which has a control of retriving mails from database, sending the mail ,and deleting the perticular mail is the aspect of this class
 *
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.neo4j.graphdb.GraphDatabaseService;


import crudclasses.IUser_SMTP_Settings;
import crudclasses.Mail;
import crudclasses.Mail_CRUDOperation;
import crudclasses.User_SMTP_CRUD_Operations;
import crudclasses.User_SMTP_Settings;

/**
 * Algorithem:- when ever this class MailController parameterized get invoke,
 * gds,userId,List<User_SMTP_Settigs>,skip get initialised for every smtp ckeck
 * quotaremaining then retrive the limit no of mails SKIP 0 LIMIT quotaremaining
 * for that particular smtp and store into the queue after reset the skip which
 * equal to no of mails retrived by first smtp then create Seperate thread for
 * every smtp create a annonymus Runnable object which creates the object
 * SendMail, SendMail Object holdes the emailId, password, smtp details like
 * smtp port_no, serverAddresss for every mail in a queue send a mail, if mail
 * has sent sucessfully then reset the quota into getQuotaRemaining to
 * --GetQuotaRemaining then delete delete the particular mail from database
 * after sent
 */
public class MailController implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GraphDatabaseService gds;
	private String userId;

	private List<User_SMTP_Settings> userSmtp;

	public MailController(GraphDatabaseService g, String userId) {
		this.gds = g;
		this.userId = userId;
		userSmtp = new ArrayList<>();
		this.userSmtp = new ArrayList<>();
//		userSmtp = User_SMTP_CRUD_Operations.smtpDetails(gds, userId);

	}

	public synchronized void retriveMail_sendMail() {
		userSmtp = User_SMTP_CRUD_Operations.smtpDetails(gds, userId);

		int skip = 0;
		List<Thread> threadList = new ArrayList<>();
		for (final IUser_SMTP_Settings smtp : userSmtp) {
			final Queue<Mail> queue = Mail_CRUDOperation.mailRetriver(gds,
					userId, skip, smtp.getQuotaRemaining());
			/**
			 * this instruction is every importance that indicates how many no
			 * of mails to skip, because form first smtp wee skip 0 and retrive
			 * the particular no of mails, if second smtp should not retrive the
			 * same mails because first smtp already loaded and that smtp will
			 * send that mails,so next smtp has to skip mails which has been already
			 * retrived.
			 */
			skip = skip + smtp.getQuotaRemaining();

			/**
			 * creation of thread for every smtp
			 */
			Thread t = new Thread(new Runnable() {

				@Override
				public void run() {
					System.out.println(smtp.getEmailId() + "\n" + "priority=="
							+ Thread.currentThread().getPriority());
					System.out.println("quota remaining before sending==="
							+ smtp.getQuotaRemaining());
					if (smtp.getQuotaRemaining() > 0)
					/**
					 * if block exist, then retrives the mails and sends the
					 * mails if quota of that perticular smtp remains
					 */

					{

						SendMail sendMail = new SendMail(smtp);

						for (Mail mail : queue) {

							if (sendMail.sendMail(mail)) {
								/**
								 * after sent sucessfully deleting the mail from
								 * database
								 */
								Mail_CRUDOperation.deleteMail(gds, userId,
										mail.getId());

								int updatedQuota = smtp.getQuotaRemaining();
								smtp.setQuotaRemaining(--updatedQuota);
								System.out.println("quota remaining==="
										+ smtp.getQuotaRemaining());

							}

						}
						/**
						 * printing this statement when ever quota gets 0
						 */
					} else {
						System.out
								.println("there is no quota remaining from this smtp=== "
										+ smtp.getEmailId());
					}

				}
			});

			/**
			 * t.start() is a method which  starts the Runnable, and runnable object will call
			 * the run method
			 */

			if (smtp.getPriority().trim().length() == 0) {
				/**
				 * if priority of this smtp==null then we are setting the
				 * priority to 10 i.e least priority
				 */
				t.setPriority(10);
			} else {
				/**
				 * setting the priority according to user mentioned
				 */
				t.setPriority(Integer.parseInt(smtp.getPriority()));
			}
			/**
			 * adding the thread into threadList because we need to start all
			 * the thread at same time, because through this only higher
			 * priority thread will get execute first
			 */
			threadList.add(t);

		}
		for (Thread thread : threadList) {
			thread.start();
		}

	}

	public synchronized void resetQuota() {
		userSmtp = User_SMTP_CRUD_Operations.smtpDetails(gds, userId);
		for (IUser_SMTP_Settings smtpdetails : userSmtp) {
			System.out.println("quota reset for mailId====="
					+ smtpdetails.getEmailId());
			System.out.println("quota remaining before reset==="
					+ smtpdetails.getQuotaRemaining());
			smtpdetails.setQuotaRemaining(Integer.parseInt(smtpdetails
					.getNo_of_mailsPermited()));
			System.out
					.println("now quota ==" + smtpdetails.getQuotaRemaining());
		}
	}
}