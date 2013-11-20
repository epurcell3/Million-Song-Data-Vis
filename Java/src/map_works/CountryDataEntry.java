package map_works;

public class CountryDataEntry {
	private String countryName;
	private String id;
	private int continent;
	private String continentString;
	public static int CONTINENT_EUROPE = 123453;
	public static int CONTINENT_ASIA = 542367;
	public static int CONTINENT_AFRICA = 87091;
	public static int CONTINENT_OCEANIA = 8912546;
	public static int CONTINENT_N_AMERICA = 64573;
	public static int CONTINENT_S_AMERICA = 876465;
	
	
	public CountryDataEntry(String cName, String cId, String cont) {
		this.countryName = cName;
		this.id = cId;
		this.continent = convertContinent(cont);
		this.continentString = cont;
		
	}
	
	private int convertContinent(String cont) {
		int out = 0;
		
		if(cont != null && !cont.isEmpty()) {
			if("Europe".equals(cont)) {
				out = CONTINENT_EUROPE;
			} else if("Asia".equals(cont)) {
				out = CONTINENT_ASIA;
			} else if("Africa".equals(cont)) {
				out = CONTINENT_AFRICA;
			} else if("Oceania".equals(cont)) {
				out = CONTINENT_OCEANIA;
			} else if("North America".equals(cont)) {
				out = CONTINENT_N_AMERICA;
			} else if("South America".equals(cont)) {
				out = CONTINENT_S_AMERICA;
			}
		}
		return out;
	}

	/**
	 * @return the countryName
	 */
	public String getCountryName() {
		return countryName;
	}

	/**
	 * @param countryName the countryName to set
	 */
	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	
	/**
	 * @return the continent
	 */
	public int getCountinent() {
		return continent;
	}

	/**
	 * @param continent the continent to set
	 */
	public void setCountinent(int continent) {
		this.continent = continent;
	}

	/**
	 * @return the continentString
	 */
	public String getContinentString() {
		return continentString;
	}

	/**
	 * @param continentString the continentString to set
	 */
	public void setContinentString(String continentString) {
		this.continentString = continentString;
	}

}
