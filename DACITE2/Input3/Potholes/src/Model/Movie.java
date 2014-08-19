package Model;

import java.util.*;

import DAL.*;

/**
 * The Class Movie.
 */
public class Movie {
	
	/** The id. */
	protected Integer id;
	
	/** The title. */
	protected String title;
	
	/** The production year. */
	protected Integer productionYear;
	
	/** The episode of id. */
	protected Integer episodeOfId;
	
	/** The season number. */
	protected Integer seasonNumber;
	
	/** The episode number. */
	protected Integer episodeNumber;
	
	/** The series years. */
	protected String seriesYears;
	
	/** The description. */
	protected String description;
	
	/** The writers. */
	protected ArrayList<Person> writers;
	
	/** The producers. */
	protected ArrayList<Person> producers;
	
	/** The directors. */
	protected ArrayList<Person> directors;
	
	/** The cast. */
	protected ArrayList<Person> cast;
	
	/** The movie data. */
	protected MovieData movieData;
	
	/** The reviews. */
	protected ArrayList<Review> reviews;

	/**
	 * Instantiates a new movie.
	 *
	 * @param id the id
	 * @param title the title
	 * @param productionYear the production year
	 * @param episodeOfId the episode of id
	 * @param seasonNumber the season number
	 * @param episodeNumber the episode number
	 * @param seriesYears the series years
	 * @param description the description
	 */
	public Movie(Integer id, String title, Integer productionYear,
			Integer episodeOfId, Integer seasonNumber, Integer episodeNumber,
			String seriesYears, String description) {
		super();
		this.id = id;
		this.title = title;
		this.productionYear = productionYear;
		this.episodeOfId = episodeOfId;
		this.seasonNumber = seasonNumber;
		this.episodeNumber = episodeNumber;
		this.seriesYears = seriesYears;
		this.description = description;
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
	 * Gets the title.
	 *
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Sets the title.
	 *
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Gets the production year.
	 *
	 * @return the productionYear
	 */
	public Integer getProductionYear() {
		return productionYear;
	}

	/**
	 * Sets the production year.
	 *
	 * @param productionYear the productionYear to set
	 */
	public void setProductionYear(Integer productionYear) {
		this.productionYear = productionYear;
	}

	/**
	 * Gets the episode of id.
	 *
	 * @return the episodeOfId
	 */
	public Integer getEpisodeOfId() {
		return episodeOfId;
	}

	/**
	 * Sets the episode of id.
	 *
	 * @param episodeOfId the episodeOfId to set
	 */
	public void setEpisodeOfId(Integer episodeOfId) {
		this.episodeOfId = episodeOfId;
	}

	/**
	 * Gets the season number.
	 *
	 * @return the seasonNumber
	 */
	public Integer getSeasonNumber() {
		return seasonNumber;
	}

	/**
	 * Sets the season number.
	 *
	 * @param seasonNumber the seasonNumber to set
	 */
	public void setSeasonNumber(Integer seasonNumber) {
		this.seasonNumber = seasonNumber;
	}

	/**
	 * Gets the episode number.
	 *
	 * @return the episodeNumber
	 */
	public Integer getEpisodeNumber() {
		return episodeNumber;
	}

	/**
	 * Sets the episode number.
	 *
	 * @param episodeNumber the episodeNumber to set
	 */
	public void setEpisodeNumber(Integer episodeNumber) {
		this.episodeNumber = episodeNumber;
	}

	/**
	 * Gets the series years.
	 *
	 * @return the seriesYears
	 */
	public String getSeriesYears() {
		return seriesYears;
	}

	/**
	 * Sets the series years.
	 *
	 * @param seriesYears the seriesYears to set
	 */
	public void setSeriesYears(String seriesYears) {
		this.seriesYears = seriesYears;
	}

	/**
	 * Gets the description.
	 *
	 * @return the description
	 */
	public String getDescription() {
		if (description == null)
			description = DAL.get().getMovieDescription(this.id);
		return description;
	}

	/**
	 * Sets the description.
	 *
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Gets the writers.
	 *
	 * @return the writers
	 */
	public ArrayList<Person> getWriters() {
		// get from DAL...
		if (writers == null)
			writers = DAL.get().getMovieWriters(this.id);
		
		return writers;
	}
	
	/**
	 * Sets the writers.
	 *
	 * @param writers the new writers
	 */
	public void setWriters(ArrayList<Person> writers) {
		this.writers = writers;
	}

	/**
	 * Gets the producers.
	 *
	 * @return the producers
	 */
	public ArrayList<Person> getProducers() {
		// get from DAL...
		if (producers == null)
			producers = DAL.get().getMovieProducers(this.id);
		
		return producers;
	}

	/**
	 * Sets the producers.
	 *
	 * @param producers the new producers
	 */
	public void setProducers(ArrayList<Person> producers) {
		this.producers = producers;
	}
	/** 
	 * @return the directors
	 */
	public ArrayList<Person> getDirectors() {
		// get from DAL...
		if (directors == null)
			directors = DAL.get().getMovieDirectors(this.id);
		
		return directors;
	}

	/**
	 * Sets the directors.
	 *
	 * @param directors the new directors
	 */
	public void setDirectors(ArrayList<Person> directors) {
		this.directors = directors;
	}
	
	/**
	 * Gets the cast.
	 *
	 * @return the cast
	 */
	public ArrayList<Person> getCast() {
		// get from DAL...
		if (cast == null)
			cast = DAL.get().getMovieCast(this.id);
		
		return cast;
	}
	
	/**
	 * Sets the cast.
	 *
	 * @param cast the new cast
	 */
	public void setCast(ArrayList<Person> cast) {
		this.cast = cast;
	}
	
	/**
	 * Gets the movie data.
	 *
	 * @return the movieData
	 */
	public MovieData getMovieData() {
		if (movieData == null) 
			movieData = DAL.get().getMovieData(this.id);
		
		return movieData;
	}
	
	/**
	 * Gets the reviews.
	 *
	 * @return the reviews
	 */
	public ArrayList<Review> getReviews() {
		// get from DAL...
		if (reviews == null)
			reviews = DAL.get().getMovieReviews(this.id);
		
		return reviews;
	}

	/**
	 * Sets the reviews.
	 *
	 * @param reviews the new reviews
	 */
	public void setReviews(ArrayList<Review> reviews) {
		this.reviews = reviews;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((episodeNumber == null) ? 0 : episodeNumber.hashCode());
		result = prime * result
				+ ((episodeOfId == null) ? 0 : episodeOfId.hashCode());
		result = prime * result
				+ ((productionYear == null) ? 0 : productionYear.hashCode());
		result = prime * result
				+ ((seasonNumber == null) ? 0 : seasonNumber.hashCode());
		result = prime * result
				+ ((seriesYears == null) ? 0 : seriesYears.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		Movie other = (Movie) obj;
		if (episodeNumber == null) {
			if (other.episodeNumber != null)
				return false;
		} else if (!episodeNumber.equals(other.episodeNumber))
			return false;
		if (episodeOfId == null) {
			if (other.episodeOfId != null)
				return false;
		} else if (!episodeOfId.equals(other.episodeOfId))
			return false;
		if (productionYear == null) {
			if (other.productionYear != null)
				return false;
		} else if (!productionYear.equals(other.productionYear))
			return false;
		if (seasonNumber == null) {
			if (other.seasonNumber != null)
				return false;
		} else if (!seasonNumber.equals(other.seasonNumber))
			return false;
		if (seriesYears == null) {
			if (other.seriesYears != null)
				return false;
		} else if (!seriesYears.equals(other.seriesYears))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		final int maxLen = 10;
		return "Movie \n{\nid="
				+ id
				+ ", \ntitle="
				+ title
				+ ", \nproductionYear="
				+ productionYear
				+ ", \nepisodeOfId="
				+ episodeOfId
				+ ", \nseasonNumber="
				+ seasonNumber
				+ ", \nepisodeNumber="
				+ episodeNumber
				+ ", \nseriesYears="
				+ seriesYears
				+ ", \ndescription="
				+ description
				+ ", \nwriters="
				+ (writers != null ? writers.subList(0,
						Math.min(writers.size(), maxLen)) : null)
				+ ", \nproducers="
				+ (producers != null ? producers.subList(0,
						Math.min(producers.size(), maxLen)) : null)
				+ ", \ndirectors="
				+ (directors != null ? directors.subList(0,
						Math.min(directors.size(), maxLen)) : null)
				+ ", \ncast="
				+ (cast != null ? cast
						.subList(0, Math.min(cast.size(), maxLen)) : null)
				+ "\n}";
	}
	
	
}
