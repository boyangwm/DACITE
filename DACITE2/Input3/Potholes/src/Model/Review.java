package Model;

import DAL.*;

/**
 *
 * @Table(name = "Reviews")
 */
public class Review {
	
	/**
	 *
	 * @Column(name = "id")
	 */
	protected Integer id;
	
	/** The account id. */
	protected Integer accountId;
	
	/** The movie id. */
	protected Integer movieId;
	
	/** The account. */
	protected Account account;
	
	/** The movie. */
	protected Movie movie;
	
	/** The review. */
	protected String review;
	
	/** The rating. */
	protected Integer rating;
	
	/**
	 * Instantiates a new review.
	 *
	 * @param id the id
	 * @param accountId the account id
	 * @param movieId the movie id
	 * @param review the review
	 * @param rating the rating
	 */
	public Review(Integer id, Integer accountId, Integer movieId,
			String review, Integer rating) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.movieId = movieId;
		this.review = review;
		this.rating = rating;
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
	 * Gets the account id.
	 *
	 * @return the accountId
	 */
	public Integer getAccountId() {
		return accountId;
	}

	/**
	 * Sets the account id.
	 *
	 * @param accountId the accountId to set
	 */
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	/**
	 * Gets the movie id.
	 *
	 * @return the movieId
	 */
	public Integer getMovieId() {
		return movieId;
	}

	/**
	 * Sets the movie id.
	 *
	 * @param movieId the movieId to set
	 */
	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}
	
	/**
	 * Gets the account.
	 *
	 * @return the account
	 */
	public Account getAccount() {
		// get from DAL...
		if (account == null)
			account = DAL.get().getAccount(this.accountId);
		
		return account;
	}
	
	/**
	 * Sets the account.
	 *
	 * @param account the new account
	 */
	public void setAccount(Account account) {
		this.account = account;
		this.accountId = account.getId();
	}
	
	/**
	 * Gets the movie.
	 *
	 * @return the movie
	 */
	public Movie getMovie() {
		// get from DAL...
		if (movie == null)
			movie = DAL.get().getMovie(this.movieId);
		
		return movie;
	}

	/**
	 * Gets the review.
	 *
	 * @return the review
	 */
	public String getReview() {
		return review;
	}

	/**
	 * Sets the review.
	 *
	 * @param review the review to set
	 */
	public void setReview(String review) {
		this.review = review;
	}

	/**
	 * Gets the rating.
	 *
	 * @return the rating
	 */
	public Integer getRating() {
		return rating;
	}

	/**
	 * Sets the rating.
	 *
	 * @param rating the rating to set
	 */
	public void setRating(Integer rating) {
		this.rating = rating;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());
		result = prime * result + ((movieId == null) ? 0 : movieId.hashCode());
		result = prime * result + ((rating == null) ? 0 : rating.hashCode());
		result = prime * result + ((review == null) ? 0 : review.hashCode());
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
		Review other = (Review) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (movieId == null) {
			if (other.movieId != null)
				return false;
		} else if (!movieId.equals(other.movieId))
			return false;
		if (rating == null) {
			if (other.rating != null)
				return false;
		} else if (!rating.equals(other.rating))
			return false;
		if (review == null) {
			if (other.review != null)
				return false;
		} else if (!review.equals(other.review))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Review \n{\nid=" + id + ", \naccountId=" + accountId
				+ ", \nmovieId=" + movieId + ", \naccount=" + account
				+ ", \nmovie=" + movie + ", \nreview=" + review + ", \nrating="
				+ rating + "\n}";
	}
	
}
