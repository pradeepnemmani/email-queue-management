package my_project;

/**
 DOCUMENTATION
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * Saturday April 05, 2014, 14:27:22
 * dots2drops
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.neo4j.graphdb.GraphDatabaseService;

import crudclasses.IMail;
import crudclasses.IUser_SMTP_Settings;
import crudclasses.Mail;
import crudclasses.Mail_CRUDOperation;
import crudclasses.User_SMTP_CRUD_Operations;
import crudclasses.User_SMTP_Settings;

public class RetriveMail_SendMail {
	private GraphDatabaseService gds;
	private String userId;

	private List<User_SMTP_Settings> userSmtp;

	public RetriveMail_SendMail(GraphDatabaseService g, String userId) {
		this.gds = g;
		this.userId = userId;
		userSmtp = new ArrayList<>();
		//this.userSmtp = User_SMTP_CRUD_Operations.smtpDetails(g, userId);

	}

	public synchronized void retriveMail_sendMail() {
		this.userSmtp = User_SMTP_CRUD_Operations.smtpDetails(gds, userId);

		for (IUser_SMTP_Settings smtpDetails : userSmtp) {
			System.out.println("first SMTP====" + smtpDetails.getEmailId());
			if (smtpDetails.getQuotaRemaining() > 0)

			{

				SendMail sendMail = new SendMail(smtpDetails);

				Queue<Mail> queue = Mail_CRUDOperation.mailRetriver(gds,
						userId, 0, smtpDetails.getQuotaRemaining());

				System.out.println("before sending the mails quota-==="
						+ smtpDetails.getQuotaRemaining());

				for (IMail m : queue) {
					System.out.println(m.getTo());
				}
				for (IMail mail : queue) {
					System.out.println(mail.getTo());
					if (sendMail.sendMail(mail)) {
						Mail_CRUDOperation
								.deleteMail(gds, userId, mail.getId());

						int updatedQuota = smtpDetails.getQuotaRemaining();
						smtpDetails.setQuotaRemaining(--updatedQuota);

						System.out.println("quota remaining ==="
								+ smtpDetails.getQuotaRemaining());

					}

				}

			} else {
				System.out
						.println("there is no quota remaining from this smtp=== "
								+ smtpDetails.getEmailId());
			}

		}
	}

	public synchronized void resetQuota() {
		this.userSmtp = User_SMTP_CRUD_Operations.smtpDetails(gds, userId);


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
