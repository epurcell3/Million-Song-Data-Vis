package edu.cs4460.msd.backend.maps_works;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.geonames.WebService;

import de.fhpotsdam.unfolding.geo.Location;
import edu.cs4460.msd.backend.database.DatabaseOperator;
import edu.cs4460.msd.backend.utilities.PathHandler;

public class ContinentData {
	private HashMap<String, CountryDataEntry> continentData;
	private static final String[] CONTINENTS = {"Africa",  "Asia", "Europe", "North America", "Oceania", "South America"};
	private HashMap<String, Location> continentCenters;

	public ContinentData() {

		continentData = loadContinentDataFromCSV();
		continentCenters = createContinentCenters();
	}

	/**
	 * Gets the name of the continent given a latitude-longitude
	 * @param lat
	 * @param lon
	 * @return
	 */
	public String getContinentForLocation(double lat, double lon) {
		String out = "";
		String countryIso = getCountryForLocation(lat, lon);
		out = getContinentForCountry(countryIso);

		return out;
	}
	
	/**
	 * Gets the geographical center of a continent given the continent name.  String
	 * input must match one of the Strings in CONTINENTS
	 * @param continent Name of a continent
	 * @return Location of center of continent or null otherwise
	 */
	public Location getContinentCenter(String continent) {
		Location out = null;
		if(continentCenters.containsKey(continent)) {
			out = continentCenters.get(continent);
		}
		return out;
	}
	
	private HashMap<String, Location> createContinentCenters() {
		HashMap<String, Location> out = new HashMap<String, Location>();
		
		Location africa = new Location(2.378, 16.063);
		Location asia = new Location(43.67694, 87.331);
		Location europe = new Location(48.236210, 21.22574);
		Location nAmerica = new Location(48.1667, -100.1667);
		Location oceania = new Location(-7.2807, 130.2539);
		Location sAmerica = new Location(-15.595833, -56.096944);
		
		out.put(CONTINENTS[0], africa);
		out.put(CONTINENTS[1], asia);
		out.put(CONTINENTS[2], europe);
		out.put(CONTINENTS[3], nAmerica);
		out.put(CONTINENTS[4], oceania);
		out.put(CONTINENTS[5], sAmerica);
		return out;
	}
	
	public static boolean checkIfContinent(String cMaybe) {
		boolean match = false;
		
		for(int i = 0; i < CONTINENTS.length; i++) {
			if(CONTINENTS[i].equals(cMaybe)) {
				match = true;
				break;
			}
		}
		return match;
	}

	/**
	 * Gets the name of the continent given a country ISO code
	 * @param isoCountry
	 * @return
	 */
	public String getContinentForCountry(String isoCountry) {
		String out = "";
		if(continentData.containsKey(isoCountry)) {
			out = continentData.get(isoCountry).getContinentString();
		}
		return out;
	}

	/**
	 * Gets the ISO code for the country of the latitude-longitude position
	 * @param lat
	 * @param lon
	 * @return
	 */
	public String getCountryForLocation(double lat, double lon) {
		String country = "";
		try {
			WebService.setUserName("mcdc09");
			country = WebService.countryCode(lat, lon);
		} catch (IOException e) {
			System.out.println("WebService could not Find country");
			e.printStackTrace();
		}
		return country;
	}

	/**
	 * Reads in the data from the Country_Continent file and returns results as HashMap
	 * @param filename Path to Country_Continent.csv
	 * @return
	 */
	private HashMap<String, CountryDataEntry> loadContinentDataFromCSV(String filename) {
		HashMap<String, CountryDataEntry> out = new HashMap<String, CountryDataEntry>();

		PathHandler ph = new PathHandler();
		String fullPath = ph.getPathToResource(filename);

		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(fullPath));
			while((line = br.readLine()) != null) {
				String[] lineBroken = line.split(cvsSplitBy);
				String id = lineBroken[0];
				String countryName = lineBroken[1]; // country name
				String continent = lineBroken[2];
				CountryDataEntry dataEntry = new CountryDataEntry(countryName, id, continent);
				out.put(id, dataEntry);
			}
			br.close();
		} catch(FileNotFoundException e) {
			e.printStackTrace();

		} catch (IOException e) {
			e.printStackTrace();
		}

		return out;
	}

	private HashMap<String, CountryDataEntry> loadContinentDataFromCSV() {
		return this.loadContinentDataFromCSV("Country_Continent.csv");
	}
	
	public String[] getFullCountriesArray() {
		String[] out = new String[continentData.size()];
		ArrayList<String> keys = new ArrayList<String>(continentData.keySet());
		
		for(int i = 0; i < out.length; i++) {
			out[i] = continentData.get(keys.get(i)).getCountryName();
		}
		
		return out;
	}
	
	/**
	 * Creates an array of the ISO-codes for all the countries that appear in the MSD database
	 * @return
	 */
	public String[] findCountriesISOInDatabase() {
		DatabaseOperator dbOp = new DatabaseOperator();
		ResultSet rs = dbOp.executeSQLQuery("SELECT DISTINCT artist_country FROM artists_h5;");
		
		ArrayList<String> countries = new ArrayList<String>();
		try {
			while(rs.next()) {
				String country = rs.getString("artist_country");
				if(country != null && !"null".equals(country)) {
					countries.add(country);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		String[] out = new String[countries.size()];
		
		for(int i = 0; i < out.length; i++) {
			out[i] = countries.get(i);
		}
		
		return out;
	}
	
	public String[] findCountriesNameInDatabase() {
		String[] countryIso = findCountriesISOInDatabase();
		String[] out = new String[countryIso.length];
		
		for(int i = 0; i < out.length; i++) {
			String iso = countryIso[i];
			String upperCase = continentData.get(iso).getCountryName();
			out[i] = convertCountryToLowerCase(upperCase);
		}
		return out;
	}
	
	private String convertCountryToLowerCase(String text) {
		text = text.toLowerCase();
		int pos = 0;
		boolean capitalize = true;
		StringBuilder sb = new StringBuilder(text);
		while (pos < sb.length()) {
		    if (sb.charAt(pos) == '.' || sb.charAt(pos) == ' ') {
		        capitalize = true;
		    } else if (capitalize && !Character.isWhitespace(sb.charAt(pos))) {
		        sb.setCharAt(pos, Character.toUpperCase(sb.charAt(pos)));
		        capitalize = false;
		    }
		    pos++;
		}
		return sb.toString();
		
	}
	
	public static void main(String[] args) {
		ContinentData cd = new ContinentData();
		System.out.println(cd.getContinentForCountry("AE"));
		String e = cd.convertCountryToLowerCase("good morning, dave");
		System.out.println(e);
	}
	
	public static String[] getContinents() {
		return CONTINENTS;
	}

}
