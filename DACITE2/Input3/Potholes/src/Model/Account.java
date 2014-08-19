package Model;

import java.util.ArrayList;

import DAL.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Account.
 */
public class Account {
	
	/** The id. */
	protected Integer id;
	
	/** The first name. */
	protected String firstName;
	
	/** The middle name. */
	protected String middleName;
	
	/** The last name. */
	protected String lastName;
	
	/** The address1. */
	protected String address1;
	
	/** The address2. */
	protected String address2;
	
	/** The city. */
	protected String city;
	
	/** The state. */
	protected String state;
	
	/** The zip code. */
	protected String zipCode;
	
	/** The username. */
	protected String username;
	
	/** The password. */
	protected String password;
	
	/** The favorites. */
	protected ArrayList<Movie> favorites;
	
	/** The reviews. */
	protected ArrayList<Review> reviews;
	

	/**
	 * Instantiates a new account.
	 *
	 * @param id the id
	 * @param firstName the first name
	 * @param middleName the middle name
	 * @param lastName the last name
	 * @param address1 the address1
	 * @param address2 the address2
	 * @param city the city
	 * @param state the state
	 * @param zipCode the zip code
	 * @param username the username
	 * @param password the password
	 */
	public Account(Integer id, String firstName, String middleName,
			String lastName, String address1, String address2, String city,
			String state, String zipCode, String username, String password) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * Instantiates a new account.
	 *
	 * @param firstName the first name
	 * @param middleName the middle name
	 * @param lastName the last name
	 * @param address1 the address1
	 * @param address2 the address2
	 * @param city the city
	 * @param state the state
	 * @param zipCode the zip code
	 * @param username the username
	 * @param password the password
	 */
	// TODO test this out
	public Account(String firstName, String middleName,
			String lastName, String address1, String address2, String city,
			String state, String zipCode, String username, String password) {
		super();
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.state = state;
		this.zipCode = zipCode;
		this.username = username;
		this.password = password;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * Gets the first name.
	 *
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the first name.
	 *
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Gets the middle name.
	 *
	 * @return the middleName
	 */
	public String getMiddleName() {
		return middleName;
	}

	/**
	 * Sets the middle name.
	 *
	 * @param middleName the middleName to set
	 */
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	/**
	 * Gets the last name.
	 *
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the last name.
	 *
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Gets the address1.
	 *
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * Sets the address1.
	 *
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * Gets the address2.
	 *
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * Sets the address2.
	 *
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * Gets the city.
	 *
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * Sets the city.
	 *
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Gets the state.
	 *
	 * @return the state
	 */
	public String getState() {
		return state;
	}

	/**
	 * Sets the state.
	 *
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}

	/**
	 * Gets the zip code.
	 *
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * Sets the zip code.
	 *
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Sets the username.
	 *
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	
	/**
	 * Gets the favorites.
	 *
	 * @return the favorites
	 */
	public ArrayList<Movie> getFavorites() {
		// get from DAL...
		if (favorites == null)
			favorites = DAL.get().getFavorites(this.id);
		
		return favorites;
	}
	
	/**
	 * Sets the favorites.
	 *
	 * @param favorites the new favorites
	 */
	public void setFavorites(ArrayList<Movie> favorites){
		this.favorites = favorites;
	}
	
	/**
	 * Sets the reviews.
	 *
	 * @param reviews The reviews to set
	 */
	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}
	
	/**
	 * Gets the reviews.
	 *
	 * @return the reviews
	 */
	public ArrayList<Review> getReviews() {
		// get from DAL...
		if (reviews == null)
			reviews = DAL.get().getUserReviews(this.id);
		
		return reviews;
	}
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int maxLen = 10;
		return "Account \n{\nid="
				+ id
				+ ", \nfirstName="
				+ firstName
				+ ", \nmiddleName="
				+ middleName
				+ ", \nlastName="
				+ lastName
				+ ", \naddress1="
				+ address1
				+ ", \naddress2="
				+ address2
				+ ", \ncity="
				+ city
				+ ", \nstate="
				+ state
				+ ", \nzipCode="
				+ zipCode
				+ ", \nusername="
				+ username
				+ ", \npassword="
				+ password
				+ ", \nfavorites="
				+ (favorites != null ? favorites.subList(0,
						Math.min(favorites.size(), maxLen)) : null) + "\n}";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((address1 == null) ? 0 : address1.hashCode());
		result = prime * result
				+ ((address2 == null) ? 0 : address2.hashCode());
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result
				+ ((favorites == null) ? 0 : favorites.hashCode());
		result = prime * result
				+ ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result
				+ ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result
				+ ((middleName == null) ? 0 : middleName.hashCode());
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((reviews == null) ? 0 : reviews.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result
				+ ((username == null) ? 0 : username.hashCode());
		result = prime * result + ((zipCode == null) ? 0 : zipCode.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (address1 == null) {
			if (other.address1 != null)
				return false;
		} else if (!address1.equals(other.address1))
			return false;
		if (address2 == null) {
			if (other.address2 != null)
				return false;
		} else if (!address2.equals(other.address2))
			return false;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (favorites == null) {
			if (other.favorites != null)
				return false;
		} else if (!favorites.equals(other.favorites))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (middleName == null) {
			if (other.middleName != null)
				return false;
		} else if (!middleName.equals(other.middleName))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (reviews == null) {
			if (other.reviews != null)
				return false;
		} else if (!reviews.equals(other.reviews))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		if (zipCode == null) {
			if (other.zipCode != null)
				return false;
		} else if (!zipCode.equals(other.zipCode))
			return false;
		return true;
	}
}
