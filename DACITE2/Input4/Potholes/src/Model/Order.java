package Model;

import DAL.*;

/**
 * The Class Order.
 */
public class Order {
	// keys...
	/** The id. */
	protected Integer id;
	
	/** The payment method id. */
	protected Integer paymentMethodId;
	
	/** The order type id. */
	protected Integer orderTypeId;
	
	/** The account id. */
	protected Integer accountId;
	
	/** The movie id. */
	protected Integer movieId;
	
	// fields to be gotten like a singleton... Or set through setters when making an order...
	/** The payment method. */
	protected PaymentMethod paymentMethod;
	
	/** The order type. */
	protected OrderType orderType;
	
	/** The account. */
	protected Account account;
	
	/** The movie. */
	protected Movie movie;
	
	// fields...
	/** The transaction second. */
	protected Integer transactionSecond;
	
	/** The transaction minute. */
	protected Integer transactionMinute;
	
	/** The transaction hour. */
	protected Integer transactionHour;
	
	/** The transaction day. */
	protected Integer transactionDay;
	
	/** The transaction month. */
	protected Integer transactionMonth;
	
	/** The transaction year. */
	protected Integer transactionYear;
	
	
	
	/**
	 * Instantiates a new order.
	 *
	 * @param id the id
	 * @param paymentMethodId the payment method id
	 * @param orderTypeId the order type id
	 * @param accountId the account id
	 * @param movieId the movie id
	 * @param transactionSecond the transaction second
	 * @param transactionMinute the transaction minute
	 * @param transactionHour the transaction hour
	 * @param transactionDay the transaction day
	 * @param transactionMonth the transaction month
	 * @param transactionYear the transaction year
	 */
	public Order(Integer id, Integer paymentMethodId, Integer orderTypeId,
			Integer accountId, Integer movieId, Integer transactionSecond,
			Integer transactionMinute, Integer transactionHour,
			Integer transactionDay, Integer transactionMonth,
			Integer transactionYear) {
		super();
		this.id = id;
		this.paymentMethodId = paymentMethodId;
		this.orderTypeId = orderTypeId;
		this.accountId = accountId;
		this.movieId = movieId;
		this.transactionSecond = transactionSecond;
		this.transactionMinute = transactionMinute;
		this.transactionHour = transactionHour;
		this.transactionDay = transactionDay;
		this.transactionMonth = transactionMonth;
		this.transactionYear = transactionYear;
	}

	
	/**
	 * Instantiates a new order.
	 *
	 * @param paymentMethodId the payment method id
	 * @param orderTypeId the order type id
	 * @param accountId the account id
	 * @param movieId the movie id
	 */
	public Order(Integer paymentMethodId, Integer orderTypeId,
			Integer accountId, Integer movieId) {
		super();
		this.paymentMethodId = paymentMethodId;
		this.orderTypeId = orderTypeId;
		this.accountId = accountId;
		this.movieId = movieId;
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
	 * Gets the payment method id.
	 *
	 * @return the paymentMethodId
	 */
	public Integer getPaymentMethodId() {
		return paymentMethodId;
	}



	/**
	 * Sets the payment method id.
	 *
	 * @param paymentMethodId the paymentMethodId to set
	 */
	public void setPaymentMethodId(Integer paymentMethodId) {
		this.paymentMethodId = paymentMethodId;
	}



	/**
	 * Gets the order type id.
	 *
	 * @return the orderTypeId
	 */
	public Integer getOrderTypeId() {
		return orderTypeId;
	}



	/**
	 * Sets the order type id.
	 *
	 * @param orderTypeId the orderTypeId to set
	 */
	public void setOrderTypeId(Integer orderTypeId) {
		this.orderTypeId = orderTypeId;
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
	 * Gets the payment method.
	 *
	 * @return the paymentMethod
	 */
	public PaymentMethod getPaymentMethod() {
		// get from DAL...
		if (paymentMethod == null)
			paymentMethod = DAL.get().getPaymentMethod(this.paymentMethodId);
		
		return paymentMethod;
	}

	/**
	 * Gets the order type.
	 *
	 * @return the orderType
	 */
	public OrderType getOrderType() {
		// get from DAL...
		if (orderType == null)
			orderType = DAL.get().getOrderType(this.orderTypeId);
		return orderType;
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
	 * Sets the payment method.
	 *
	 * @param paymentMethod the paymentMethod to set
	 */
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}



	/**
	 * Sets the order type.
	 *
	 * @param orderType the orderType to set
	 */
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}



	/**
	 * Sets the account.
	 *
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
		this.accountId = account.getId();
	}



	/**
	 * Sets the movie.
	 *
	 * @param movie the movie to set
	 */
	public void setMovie(Movie movie) {
		this.movie = movie;
		this.movieId = movie.getId();
	}
	

	/**
	 * Gets the transaction second.
	 *
	 * @return the transactionSecond
	 */
	public Integer getTransactionSecond() {
		return transactionSecond;
	}



	/**
	 * Sets the transaction second.
	 *
	 * @param transactionSecond the transactionSecond to set
	 */
	public void setTransactionSecond(Integer transactionSecond) {
		this.transactionSecond = transactionSecond;
	}



	/**
	 * Gets the transaction minute.
	 *
	 * @return the transactionMinute
	 */
	public Integer getTransactionMinute() {
		return transactionMinute;
	}



	/**
	 * Sets the transaction minute.
	 *
	 * @param transactionMinute the transactionMinute to set
	 */
	public void setTransactionMinute(Integer transactionMinute) {
		this.transactionMinute = transactionMinute;
	}



	/**
	 * Gets the transaction hour.
	 *
	 * @return the transactionHour
	 */
	public Integer getTransactionHour() {
		return transactionHour;
	}



	/**
	 * Sets the transaction hour.
	 *
	 * @param transactionHour the transactionHour to set
	 */
	public void setTransactionHour(Integer transactionHour) {
		this.transactionHour = transactionHour;
	}



	/**
	 * Gets the transaction day.
	 *
	 * @return the transactionDay
	 */
	public Integer getTransactionDay() {
		return transactionDay;
	}



	/**
	 * Sets the transaction day.
	 *
	 * @param transactionDay the transactionDay to set
	 */
	public void setTransactionDay(Integer transactionDay) {
		this.transactionDay = transactionDay;
	}



	/**
	 * Gets the transaction month.
	 *
	 * @return the transactionMonth
	 */
	public Integer getTransactionMonth() {
		return transactionMonth;
	}



	/**
	 * Sets the transaction month.
	 *
	 * @param transactionMonth the transactionMonth to set
	 */
	public void setTransactionMonth(Integer transactionMonth) {
		this.transactionMonth = transactionMonth;
	}



	/**
	 * Gets the transaction year.
	 *
	 * @return the transactionYear
	 */
	public Integer getTransactionYear() {
		return transactionYear;
	}



	/**
	 * Sets the transaction year.
	 *
	 * @param transactionYear the transactionYear to set
	 */
	public void setTransactionYear(Integer transactionYear) {
		this.transactionYear = transactionYear;
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
		result = prime * result
				+ ((orderTypeId == null) ? 0 : orderTypeId.hashCode());
		result = prime * result
				+ ((paymentMethodId == null) ? 0 : paymentMethodId.hashCode());
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
		Order other = (Order) obj;
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
		if (orderTypeId == null) {
			if (other.orderTypeId != null)
				return false;
		} else if (!orderTypeId.equals(other.orderTypeId))
			return false;
		if (paymentMethodId == null) {
			if (other.paymentMethodId != null)
				return false;
		} else if (!paymentMethodId.equals(other.paymentMethodId))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Order \n{\nid=" + id + ", \npaymentMethodId=" + paymentMethodId
				+ ", \norderTypeId=" + orderTypeId + ", \naccountId="
				+ accountId + ", \nmovieId=" + movieId + ", \npaymentMethod="
				+ paymentMethod + ", \norderType=" + orderType + ", \naccount="
				+ account + ", \nmovie=" + movie + ", \ntransactionSecond="
				+ transactionSecond + ", \ntransactionMinute="
				+ transactionMinute + ", \ntransactionHour=" + transactionHour
				+ ", \ntransactionDay=" + transactionDay
				+ ", \ntransactionMonth=" + transactionMonth
				+ ", \ntransactionYear=" + transactionYear + "\n}";
	}

	

	
}
