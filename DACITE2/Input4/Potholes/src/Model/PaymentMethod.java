package Model;
import DAL.*;

/**
 * The Class PaymentMethod.
 */
public class PaymentMethod {
	
	/** The id. */
	protected Integer id;
	
	/** The account id. */
	protected Integer accountId;
	
	/** The credit card type id. */
	protected Integer creditCardTypeId;
	
	// to be gotten through DAL... or Set when creating na order...
	/** The account. */
	protected Account account;
	
	/** The credit card type. */
	protected CreditCardType creditCardType;

	/** The credit card number. */
	protected String creditCardNumber;
	
	/** The expiration month. */
	protected Integer expirationMonth;
	
	/** The expiration year. */
	protected Integer expirationYear;
	
	/** The cvv. */
	protected Integer cvv;
	
	/** The name on card. */
	protected String nameOnCard;
	
	/** The zip code. */
	protected String zipCode;
	
	/**
	 * Instantiates a new payment method.
	 *
	 * @param id the id
	 * @param accountId the account id
	 * @param creditCardTypeId the credit card type id
	 * @param creditCardNumber the credit card number
	 * @param expirationMonth the expiration month
	 * @param expirationYear the expiration year
	 * @param cvv the cvv
	 * @param nameOnCard the name on card
	 * @param zipCode the zip code
	 */
	public PaymentMethod(Integer id, Integer accountId,
			Integer creditCardTypeId,
			String creditCardNumber, Integer expirationMonth,
			Integer expirationYear, Integer cvv, String nameOnCard,
			String zipCode) {
		super();
		this.id = id;
		this.accountId = accountId;
		this.creditCardTypeId = creditCardTypeId;
		this.creditCardNumber = creditCardNumber;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
		this.cvv = cvv;
		this.nameOnCard = nameOnCard;
		this.zipCode = zipCode;
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
	 * Gets the credit card type id.
	 *
	 * @return the creditCardTypeId
	 */
	public Integer getCreditCardTypeId() {
		return creditCardTypeId;
	}

	/**
	 * Sets the credit card type id.
	 *
	 * @param creditCardTypeId the creditCardTypeId to set
	 */
	public void setCreditCardTypeId(Integer creditCardTypeId) {
		this.creditCardTypeId = creditCardTypeId;
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
	 * Gets the credit card type.
	 *
	 * @return the creditCardType
	 */
	public CreditCardType getCreditCardType() {
		// get from DAL...
		if (creditCardType == null)
			creditCardType = DAL.get().getCreditCardType(this.creditCardTypeId);
		
		return creditCardType;
	}
	
	/**
	 * Sets the account.
	 *
	 * @param account the account to set
	 */
	public void setAccount(Account account) {
		this.account = account;
	}

	/**
	 * Sets the credit card type.
	 *
	 * @param creditCardType the creditCardType to set
	 */
	public void setCreditCardType(CreditCardType creditCardType) {
		this.creditCardType = creditCardType;
	}
	
	/**
	 * Gets the credit card number.
	 *
	 * @return the creditCardNumber
	 */
	public String getCreditCardNumber() {
		return creditCardNumber;
	}

	/**
	 * Sets the credit card number.
	 *
	 * @param creditCardNumber the creditCardNumber to set
	 */
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}

	/**
	 * Gets the expiration month.
	 *
	 * @return the expirationMonth
	 */
	public Integer getExpirationMonth() {
		return expirationMonth;
	}

	/**
	 * Sets the expiration month.
	 *
	 * @param expirationMonth the expirationMonth to set
	 */
	public void setExpirationMonth(Integer expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	/**
	 * Gets the expiration year.
	 *
	 * @return the expirationYear
	 */
	public Integer getExpirationYear() {
		return expirationYear;
	}

	/**
	 * Sets the expiration year.
	 *
	 * @param expirationYear the expirationYear to set
	 */
	public void setExpirationYear(Integer expirationYear) {
		this.expirationYear = expirationYear;
	}

	/**
	 * Gets the cvv.
	 *
	 * @return the cvv
	 */
	public Integer getCvv() {
		return cvv;
	}

	/**
	 * Sets the cvv.
	 *
	 * @param cvv the cvv to set
	 */
	public void setCvv(Integer cvv) {
		this.cvv = cvv;
	}

	/**
	 * Gets the name on card.
	 *
	 * @return the nameOnCard
	 */
	public String getNameOnCard() {
		return nameOnCard;
	}

	/**
	 * Sets the name on card.
	 *
	 * @param nameOnCard the nameOnCard to set
	 */
	public void setNameOnCard(String nameOnCard) {
		this.nameOnCard = nameOnCard;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((accountId == null) ? 0 : accountId.hashCode());
		result = prime
				* result
				+ ((creditCardNumber == null) ? 0 : creditCardNumber.hashCode());
		result = prime
				* result
				+ ((creditCardTypeId == null) ? 0 : creditCardTypeId.hashCode());
		result = prime * result + ((cvv == null) ? 0 : cvv.hashCode());
		result = prime * result
				+ ((expirationMonth == null) ? 0 : expirationMonth.hashCode());
		result = prime * result
				+ ((expirationYear == null) ? 0 : expirationYear.hashCode());
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
		PaymentMethod other = (PaymentMethod) obj;
		if (accountId == null) {
			if (other.accountId != null)
				return false;
		} else if (!accountId.equals(other.accountId))
			return false;
		if (creditCardNumber == null) {
			if (other.creditCardNumber != null)
				return false;
		} else if (!creditCardNumber.equals(other.creditCardNumber))
			return false;
		if (creditCardTypeId == null) {
			if (other.creditCardTypeId != null)
				return false;
		} else if (!creditCardTypeId.equals(other.creditCardTypeId))
			return false;
		if (cvv == null) {
			if (other.cvv != null)
				return false;
		} else if (!cvv.equals(other.cvv))
			return false;
		if (expirationMonth == null) {
			if (other.expirationMonth != null)
				return false;
		} else if (!expirationMonth.equals(other.expirationMonth))
			return false;
		if (expirationYear == null) {
			if (other.expirationYear != null)
				return false;
		} else if (!expirationYear.equals(other.expirationYear))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PaymentMethod \n{\nid=" + id + ", \naccountId=" + accountId
				+ ", \ncreditCardTypeId=" + creditCardTypeId + ", \naccount="
				+ account + ", \ncreditCardType=" + creditCardType
				+ ", \ncreditCardNumber=" + creditCardNumber
				+ ", \nexpirationMonth=" + expirationMonth
				+ ", \nexpirationYear=" + expirationYear + ", \ncvv=" + cvv
				+ ", \nnameOnCard=" + nameOnCard + ", \nzipCode=" + zipCode
				+ "\n}";
	}

}
