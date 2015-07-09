package crudclasses;

public interface IMail {

	public abstract long getEntryTime();

	public abstract void setEntryTime(long entryTime);

	public abstract String getDateOfEnter();

	public abstract void setDateOfEnter(String dateOfEnter);

	public abstract String getTo();

	public abstract void setTo(String to);

	public abstract String getSubject();

	public abstract void setSubject(String subject);

	public abstract String getBody();

	public abstract void setBody(String body);

	public abstract String getId();

	public abstract void setId(String id);

}