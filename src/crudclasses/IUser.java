package crudclasses;

public interface IUser {

	public abstract String getFirstName();

	public abstract void setFirstName(String firstName);

	public abstract String getLastName();

	public abstract void setLastName(String lastName);

	public abstract String getPassword();

	public abstract void setPassword(String password);

	public abstract String getDateOfBirth();

	public abstract void setDateOfBirth(String dateOfBirth);

	public abstract String getGender();

	public abstract void setGender(String gender);

	public abstract String getAge();

	public abstract void setAge(String age);

	public abstract String getUserId();

	public abstract void setUserId(String userId);

	public abstract String getRegisteredDate();

	public abstract void setRegisteredDate(String registeredDate);

}