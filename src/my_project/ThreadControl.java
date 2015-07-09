package my_project;

/**
 * DOCUMENTATION
 * 
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * dots2drops
 * * Saturday April 05, 2014, 15:29:48
 */
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.neo4j.graphdb.GraphDatabaseService;

/**
 * 
 * The ThreadControl  is a class, the assert of this class is Scheduling the threads
 *
 */
/**
 * 
 * Algorithem:- 1)Create an parameterized constructor which takes parameters
 * GraphDatabaseService and userId of String 2)Creat an obect for the
 * mailController(when ever mailcontroller object created it means in
 * mailController class will executes and retrives User smtp details ,users
 * mails and ready for send) 3)create an Scheduled Thread for Runnable which
 * implementing the run() this metod will calls the retrieveMail_SendMail in
 * MailCotroller ,this method will retrieve and send the mails 4)and Create
 * another Scheduled Thread fro Runnalble which implementing the run() that will
 * call the reset() in MailController,this reset() will reset quota
 */

public class ThreadControl {

	private MailController mc;
	//private RetriveMail_SendMail rm_sm;

	public ThreadControl(GraphDatabaseService gds, String userId) {

		mc = new MailController(gds, userId);
		//rm_sm = new RetriveMail_SendMail(gds, userId);

		startSending();
	}

	public void startSending() {
		ScheduledThreadPoolExecutor stpe = (ScheduledThreadPoolExecutor) Executors
				.newScheduledThreadPool(1);
		ScheduledThreadPoolExecutor stpe2 = (ScheduledThreadPoolExecutor) Executors
				.newScheduledThreadPool(1);
		try {
			stpe.scheduleWithFixedDelay(retrieveMail_sendMail, 10, 500,
			
					TimeUnit.SECONDS);

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			stpe2.scheduleAtFixedRate(quotaReset, 2,3, TimeUnit.MINUTES);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	Runnable retrieveMail_sendMail = new Runnable() {

		@Override
		public void run() {
			try {
				mc.retriveMail_sendMail();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	};
	Runnable quotaReset = new Runnable() {

		@Override
		public void run() {
			try {
				mc.resetQuota();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
	};

}
