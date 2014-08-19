package Model;

/**
 * The Class CurrencyType.
 */
public class CurrencyType {
	
	/** The id. */
	protected Integer id;
	
	/** The name. */
	protected String name;
	
	/**
	 * Instantiates a new currency type.
	 *
	 * @param id the id
	 * @param name the name
	 */
	public CurrencyType(Integer id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Instantiates a new currency type.
	 *
	 * @param name the name
	 */
	public CurrencyType(String name) {
		super();
		this.name = name;
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
		CurrencyType other = (CurrencyType) obj;
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
		return "CurrencyType \n{\nid=" + id + ", \nname=" + name + "\n}";
	}
	
	
	
}
