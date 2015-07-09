package crudclasses;
/**
 * DOCUMENTATION
 * Title:-EMAIL QUEUEING MANAGENENT USING MULTIPLE SMTP PROVIDERS.
 * @author N. Pradeep Kumar, ij13b
 * dots2drops
 * Saturday April 05, 2014, 13:06:08
 */

import java.io.Serializable;
/**
 * 
 *Algorithem:-
 *1)craete a fields like firstName,lastName,age,gender,dateOfBirth,userId,registrationDate
 *2)create an parameterized costructor for creating an User
 *3)generate getter and setters for for the fields
 */
public class User implements Serializable, IUser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String firstName;
	private String lastName;
	private String gender;
	private String age;
	private String dateOfBirth;
	private String userId;
	private String password;
	private String registeredDate;
	public User()
	{
		
	}

	public User(String firstName, String lastName,String password,
			String gender,String dateOfBirth, String age,String regDate) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.password=password;
		this.gender = gender;
		this.age = age;
		this.dateOfBirth=dateOfBirth;

		this.registeredDate=regDate;
	}
	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser#getFirstName()
	 */
	@Override
	public String getFirstName() {
		return firstName;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser#setFirstName(java.lang.String)
	 */
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser#getLastName()
	 */
	@Override
	public String getLastName() {
		return lastName;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser#setLastName(java.lang.String)
	 */
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser#getPassword()
	 */
	@Override
	public String getPassword() {
		return password;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser#setPassword(java.lang.String)
	 */
	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser#getDateOfBirth()
	 */
	@Override
	public String getDateOfBirth() {
		return dateOfBirth;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser#setDateOfBirth(java.lang.String)
	 */
	@Override
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser#getGender()
	 */
	@Override
	public String getGender() {
		return gender;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser#setGender(java.lang.String)
	 */
	@Override
	public void setGender(String gender) {
		this.gender = gender;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser#getAge()
	 */
	@Override
	public String getAge() {
		return age;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser#setAge(java.lang.String)
	 */
	@Override
	public void setAge(String age) {
		this.age = age;
	}


	/* (non-Javadoc)
	 * @see crudclasses.IUser#getUserId()
	 */
	@Override
	public String getUserId() {
		return userId;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser#setUserId(java.lang.String)
	 */
	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser#getRegisteredDate()
	 */
	@Override
	public String getRegisteredDate() {
		return registeredDate;
	}

	/* (non-Javadoc)
	 * @see crudclasses.IUser#setRegisteredDate(java.lang.String)
	 */
	@Override
	public void setRegisteredDate(String registeredDate) {
		this.registeredDate = registeredDate;
	}
	

}
