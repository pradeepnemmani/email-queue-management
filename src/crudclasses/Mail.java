package crudclasses;

/**
 * DOCUMENTATION
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * dots2drops
 * Saturday April 05, 2014, 11:46:39
 */
import java.io.Serializable;

/**
 * Algorithem:- 1)create a class Mail which implements Serializable and add
 * fields like to, subject,body,dateOfEntry,id.entryTime 2)create parameterized
 * constructor and tkaes parameters like String to, String subject, String body,
 * String entryDate 3)create setter and getters for every field
 * 
 */
public class Mail implements Serializable, IMail {

	private static final long serialVersionUID = 1L;
	private String to;
	private String subject;
	private String body;
	private String dateOfEnter;
	private String id;
	private long entryTime;

	public Mail() {
		super();
	}

	public Mail(String to, String subject, String body, String entryDate) {
		super();
		this.to = to;
		this.subject = subject;
		this.body = body;
		this.dateOfEnter = entryDate;

	}

	/* (non-Javadoc)
	 * @see crudclasses.IMail#getEntryTime()
	 */
	@Override
	public long getEntryTime() {
		return entryTime;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IMail#setEntryTime(long)
	 */
	@Override
	public void setEntryTime(long entryTime) {
		this.entryTime = entryTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IMail#getDateOfEnter()
	 */
	@Override
	public String getDateOfEnter() {
		return dateOfEnter;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IMail#setDateOfEnter(java.lang.String)
	 */
	@Override
	public void setDateOfEnter(String dateOfEnter) {
		this.dateOfEnter = dateOfEnter;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IMail#getTo()
	 */
	@Override
	public String getTo() {
		return to;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IMail#setTo(java.lang.String)
	 */
	@Override
	public void setTo(String to) {
		this.to = to;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IMail#getSubject()
	 */
	@Override
	public String getSubject() {
		return subject;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IMail#setSubject(java.lang.String)
	 */
	@Override
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IMail#getBody()
	 */
	@Override
	public String getBody() {
		return body;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IMail#setBody(java.lang.String)
	 */
	@Override
	public void setBody(String body) {
		this.body = body;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IMail#getId()
	 */
	@Override
	public String getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IMail#setId(java.lang.String)
	 */
	@Override
	public void setId(String id) {
		this.id = id;
	}

}
