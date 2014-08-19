package Model;

import java.util.ArrayList;

import DAL.*;

/**
 * The Class Person.
 */
public class Person {
	
	/** The id. */
	protected Integer id;
	
	/** The first name. */
	protected String firstName;
	
	/** The last name. */
	protected String lastName;
	
	/** The gender. */
	protected Character gender;
	
	/** The biography. */
	protected String biography;
	
	/** The movies as a castmember. */
	ArrayList<Movie> moviesAsACastmember;
	
	/** The movies as a director. */
	ArrayList<Movie> moviesAsADirector;
	
	/** The movies as a producer. */
	ArrayList<Movie> moviesAsAProducer;
	
	/** The movies as a writer. */
	ArrayList<Movie> moviesAsAWriter;
	
	
	/**
	 * Instantiates a new person.
	 *
	 * @param id the id
	 * @param firstName the first name
	 * @param lastName the last name
	 * @param gender the gender
	 */
	public Person(Integer id, String firstName, String lastName,
			Character gender) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
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
	 * Gets the gender.
	 *
	 * @return the gender
	 */
	public Character getGender() {
		return gender;
	}

	/**
	 * Sets the gender.
	 *
	 * @param gender the gender to set
	 */
	public void setGender(Character gender) {
		this.gender = gender;
	}

	/**
	 * Gets the biography.
	 *
	 * @return the biography
	 */
	public String getBiography() {
		if (biography == null)
			biography = DAL.get().getPersonBiography(this.id);
		
		return biography;
	}
	
	/**
	 * Sets the biography.
	 *
	 * @param biography data
	 */
	public void setBiography(String biography) {
		this.biography = biography; 
	}
	
	/**
	 * Gets the movies as a castmember.
	 *
	 * @return the movies as a castmember
	 */
	public ArrayList<Movie> getMoviesAsACastmember() {
		if ( this.moviesAsACastmember == null)
		{
			ArrayList <Movie> movies = DAL.get().getMoviesFromCastmember(this.id);
			return movies;
		}
		return this.moviesAsACastmember;
	}
	
	/**
	 * Sets the movies as a castmember.
	 *
	 * @param movies the new movies as a castmember
	 */
	public void setMoviesAsACastmember(ArrayList<Movie> movies) {
		this.moviesAsACastmember = movies;
	}
	
	/**
	 * Gets the movies as a director.
	 *
	 * @return the movies as a director
	 */
	public ArrayList<Movie> getMoviesAsADirector() {
		if ( this.moviesAsADirector == null )
		{
			ArrayList <Movie> movies = DAL.get().getMoviesFromDirector(this.id);
			return movies;
		}
		return this.moviesAsADirector;
	}
	
	/**
	 * Sets the movies as a director.
	 *
	 * @param movies the new movies as a director
	 */
	public void setMoviesAsADirector(ArrayList<Movie> movies) {
		this.moviesAsADirector = movies;
	}
	
	/**
	 * Gets the movies as a producer.
	 *
	 * @return the movies as a producer
	 */
	public ArrayList<Movie> getMoviesAsAProducer() {
		if ( this.moviesAsAProducer == null )
		{
			ArrayList <Movie> movies = DAL.get().getMoviesFromProducer(this.id);
			return movies;
		}
		return this.moviesAsAProducer;
	}
	
	/**
	 * Sets the movies as a producer.
	 *
	 * @param movies the new movies as a producer
	 */
	public void setMoviesAsAProducer(ArrayList<Movie> movies) {
		this.moviesAsAProducer = movies;
	}
	
	/**
	 * Gets the movies as a writer.
	 *
	 * @return the movies as a writer
	 */
	public ArrayList<Movie> getMoviesAsAWriter() {
		if ( this.moviesAsAWriter == null )
		{
			ArrayList <Movie> movies = DAL.get().getMoviesFromWriter(this.id);
			return movies;
		}
		return this.moviesAsAWriter;
	}
	
	/**
	 * Sets the movies as a writer.
	 *
	 * @param movies the new movies as a writer
	 */
	public void setMoviesAsAWriter(ArrayList<Movie> movies) {
		this.moviesAsAWriter = movies;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		final Person other = (Person) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Person \n{\nid=" + id + ", \nfirstName=" + firstName
				+ ", \nlastName=" + lastName + ", \ngender=" + gender
				+ ", \nbiography=" + biography + "\n}";
	}
}
