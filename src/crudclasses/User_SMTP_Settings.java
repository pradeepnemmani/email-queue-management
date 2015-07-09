package crudclasses;
/**
 * DOCUMENTATION
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * dots2drops
 * Saturday April 05, 2014, 15:42:54
 */

import java.io.Serializable;
/**
 * 
 *Algorithem:-
 *1)Create fields like emailId,password,serverAddress,smtp_portNo,smtpId,no_Of_mailsPermitted,quotaRemaining,dateOfCreated
 *2)craete a parameterized constructor for create newSmtpDetailsfro user which takes parameters all the fields
 *3)generate the getter and setters for every field
 */
public class User_SMTP_Settings implements Serializable, IUser_SMTP_Settings 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String emailId;
	private String password;
	private String serverAddress;
	private String smtp_PortNo;
	private String priority;
	private int smtpIdNo;
	private String no_of_mailsPermited;
	private  int quotaRemaining;
	private String dateOfCreated;

	
	public User_SMTP_Settings()
	{
		super();
	}
	public User_SMTP_Settings(String emailId,String password,String serverAddress, String smtp_PortNo,
		 String  no_of_mailsPermited,String priority,String dateOfCreated) {
		super();
		this.emailId=emailId;
		this.password=password;
		this.serverAddress = serverAddress;
		this.smtp_PortNo = smtp_PortNo;
		this.dateOfCreated=dateOfCreated;
		this.priority=priority;
		
		this.no_of_mailsPermited = no_of_mailsPermited;
	}

	
	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#getEmailId()
	 */
	@Override
	public String getEmailId() {
		return emailId;
	}
	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#setEmailId(java.lang.String)
	 */
	@Override
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}
	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#getServerAddress()
	 */
	@Override
	public String getServerAddress() {
		return serverAddress;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#setServerAddress(java.lang.String)
	 */
	@Override
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#getSmtp_PortNo()
	 */
	@Override
	public String getSmtp_PortNo() {
		return smtp_PortNo;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#setSmtp_PortNo(java.lang.String)
	 */
	@Override
	public void setSmtp_PortNo(String smtp_PortNo) {
		this.smtp_PortNo = smtp_PortNo;
	}

	

	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#getPriority()
	 */
	@Override
	public String getPriority() {
		return priority;
	}
	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#setPriority(java.lang.String)
	 */
	@Override
	public void setPriority(String priority) {
		this.priority = priority;
	}
	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#getNo_of_mailsPermited()
	 */
	@Override
	public String getNo_of_mailsPermited() {
		return no_of_mailsPermited;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#setNo_of_mailsPermited(java.lang.String)
	 */
	@Override
	public void setNo_of_mailsPermited(String no_of_mailsPermited) {
		this.no_of_mailsPermited = no_of_mailsPermited;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#getQuotaRemaining()
	 */
	@Override
	public int getQuotaRemaining() {
		
		return quotaRemaining;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#setQuotaRemaining(int)
	 */
	@Override
	public void setQuotaRemaining(int quotaRemaining) {
		this.quotaRemaining = quotaRemaining;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#getSmtpIdNo()
	 */
	@Override
	public int getSmtpIdNo() {
		return smtpIdNo;
	}
	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#setSmtpIdNo(int)
	 */
	@Override
	public void setSmtpIdNo(int smtpIdNo) {
		this.smtpIdNo = smtpIdNo;
	}
	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#getDateOfCreated()
	 */
	@Override
	public String getDateOfCreated() {
		return dateOfCreated;
	}
	/* (non-Javadoc)
	 * @see crudclasses.IUser_SMTP_Settings#setDateOfCreated(java.lang.String)
	 */
	@Override
	public void setDateOfCreated(String dateOfCreated) {
		this.dateOfCreated = dateOfCreated;
	}
	
	

}
