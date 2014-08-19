
package Model;

/**
 * The Class CreditCardType.
 */
public class CreditCardType {
	
	/** The id. */
	protected Integer id;
	
	/** The name. */
	protected String name;
	
	/**
	 * The Enum Type.
	 */
	public static enum Type {
		// 1, 2, 3
	    /** The visa. */
		VISA, 
 /** The mastercard. */
 MASTERCARD, 
 /** The american express. */
 AMERICAN_EXPRESS
	}
	
	/**
	 * Gets the type from number.
	 *
	 * @param number the number
	 * @return the type from number
	 */
	public static Type getTypeFromNumber(Integer number) {
		if (number == 1)
			return Type.VISA;
		else if (number == 2)
			return Type.MASTERCARD;
		else
			return Type.AMERICAN_EXPRESS;
	}
	
	/**
	 * Gets the number from type.
	 *
	 * @param type the type
	 * @return the number from type
	 */
	public static Integer getNumberFromType(Type type) {
		if (type == Type.VISA)
			return 1;
		else if (type == Type.MASTERCARD)
			return 2;
		else
			return 3;
	}
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public Type getType() {
		return getTypeFromNumber(this.id);
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(Type type) {
		if (type == Type.VISA) {
			this.id = 1;
		}
		else if (type == Type.MASTERCARD) {
			this.id = 2;
		}
		else {
			this.id = 3;
		}
		
		this.name = getNameFromType(type);
	}
	
	/**
	 * Gets the name from type.
	 *
	 * @param type the type
	 * @return the name from type
	 */
	public String getNameFromType(Type type) {
		if (type == Type.VISA)
			return "VISA";
		if (type == Type.MASTERCARD)
			return "MASTERCARD";
		else
			return "AMERICAN EXPRESS";
	}
	
	/**
	 * Gets the name from number.
	 *
	 * @param number the number
	 * @return the name from number
	 */
	public String getNameFromNumber(Integer number) {
		if (number == 1)
			return "VISA";
		else if (number == 2)
			return "MASTERCARD";
		else
			return "AMERICAN EXPRESS";
	}
	
	/**
	 * Instantiates a new credit card type.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public CreditCardType(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Sets cc type based on enumeration only... Set type and everything is fine...
	 *
	 * @param type the type
	 */
	public CreditCardType(Type type) {
		super();
		this.setType(type);
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
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		CreditCardType other = (CreditCardType) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CreditCardType \n{\nid=" + id + ", \nname=" + name + "\n}";
	}
	
	
	
}
