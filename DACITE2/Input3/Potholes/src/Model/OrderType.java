package Model;

/**
 * The Class OrderType.
 */
public class OrderType {
	
	/** The id. */
	protected Integer id;
	
	/** The name. */
	protected String name;
	
	/**
	 * The Enum Type.
	 */
	public static enum Type {
	    
    	/** The purchase. */
    	PURCHASE, 
		 /** The rental. */
		 RENTAL
	}
	
	/**
	 * Gets the type from number.
	 *
	 * @param number the number
	 * @return the type from number
	 */
	public static Type getTypeFromNumber(Integer number) {
		if (number == 1)
			return Type.PURCHASE;
		else
			return Type.RENTAL;
	}
	
	/**
	 * Gets the number from type.
	 *
	 * @param type the type
	 * @return the number from type
	 */
	public static Integer getNumberFromType(Type type) {
		if (type == Type.PURCHASE)
			return 1;
		else
			return 2;
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
		if (type == Type.PURCHASE) {
			this.id = 1;
		}
		else {
			this.id = 2;
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
		if (type == Type.PURCHASE)
			return "PURCHASE";
		else
			return "RENTAL";
	}
	
	/**
	 * Gets the name from number.
	 *
	 * @param number the number
	 * @return the name from number
	 */
	public String getNameFromNumber(Integer number) {
		if (number == 1)
			return "PURCHASE";
		else
			return "RENTAL";
	}
	
	/**
	 * Instantiates a new order type.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public OrderType(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Sets order type based on enumeration only... Set type and everything is fine...
	 *
	 * @param type the type
	 */
	public OrderType(Type type) {
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
		final OrderType other = (OrderType) obj;
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
		return "OrderType \n{\nid=" + id + ", \nname=" + name + "\n}";
	}
	
	
}
