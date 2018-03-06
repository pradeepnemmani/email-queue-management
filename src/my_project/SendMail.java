package my_project;

/**
 DOCUMENTATION
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * Saturday Jan 05, 2014, 14:10:56
 * dots2drops
 */
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import crudclasses.IMail;
import crudclasses.IUser_SMTP_Settings;

/**
 * the SendMail is class the asert of this class is to send the mail
 */
/**
 * Algorithem:- 1)Create an default constructor fro this class that has to take
 * parameter User_SMTP_Settings & and create an Object for Properties this
 * object has to load the smtp details like serveraddress,smtp port no..........
 * 2)in that constructor create an object foe PassWordAutheticator that object
 * holds the user emaiId,password this userid & password is field of
 * User_SMTP_Settings 3)Create an object for Autheticator which is an interface
 * which implements a method getPasswordAuthenticator that method will return
 * PasswordAuthetication object 4)create an Object for Session,
 * getinstansce.Session() returns the Session Object 5) create an Object for
 * MimeMessage which takes an argument Session Object and returns Message object
 * 6)using Message object set to address, body, subject
 */
public class SendMail {

	private IUser_SMTP_Settings settings;

	private Properties prop;
	private PasswordAuthentication passWordAuthen;
	private Authenticator authenticator;
	private Session session;

	public SendMail(IUser_SMTP_Settings s) {
		this.settings = s;

		this.prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");

		prop.put("mail.smtp.host", s.getServerAddress().toString());

		prop.put("mail.smtp.port", s.getSmtp_PortNo().toString());
		try
		{

			this.passWordAuthen = new PasswordAuthentication(s.getEmailId(),
					s.getPassword());
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		try {

			authenticator = new Authenticator() {

				@Override
				protected PasswordAuthentication getPasswordAuthentication() {

					return passWordAuthen;
				}

			};
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		try {
			session = Session.getInstance(prop, authenticator);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public boolean sendMail(IMail mail) {

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(settings.getEmailId()));

			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(mail.getTo()));
			/**
			 * message.addHeader("X-Priority", "1");
			 */
			message.setSubject(mail.getSubject());
			message.setText(mail.getBody());
			try {
				/**
				 * if mesage send sucessfully return true so, this method caller
				 * can delete this mail from database
				 */
				Transport.send(message);
				System.out.println("message sent id===" + mail.getTo());
				return true;
			} catch (Exception sfe) {
				sfe.printStackTrace();
				System.out.println(sfe.getMessage());
				/**
				 * if message send fail retutn false so,this method caller will
				 * not delete the meil from database
				 */
				return false;
			}

		} catch (MessagingException me) {
			System.out.println("message sent failed==" + me.getMessage());

		}
		return false;
	}
}
