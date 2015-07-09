package crudclasses;

public interface IUser_SMTP_Settings {

	public abstract String getEmailId();

	public abstract void setEmailId(String emailId);

	public abstract String getPassword();

	public abstract void setPassword(String password);

	public abstract String getServerAddress();

	public abstract void setServerAddress(String serverAddress);

	public abstract String getSmtp_PortNo();

	public abstract void setSmtp_PortNo(String smtp_PortNo);

	public abstract String getPriority();

	public abstract void setPriority(String priority);

	public abstract String getNo_of_mailsPermited();

	public abstract void setNo_of_mailsPermited(String no_of_mailsPermited);

	public abstract int getQuotaRemaining();

	public abstract void setQuotaRemaining(int quotaRemaining);

	public abstract int getSmtpIdNo();

	public abstract void setSmtpIdNo(int smtpIdNo);

	public abstract String getDateOfCreated();

	public abstract void setDateOfCreated(String dateOfCreated);

}