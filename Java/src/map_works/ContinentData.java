package map_works;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.geonames.WebService;

import backend.PathHandler;

public class ContinentData {
	private HashMap<String, CountryDataEntry> continentData;

	public ContinentData() {

		continentData = loadContinentDataFromCSV();
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

	public HashMap<String, CountryDataEntry> loadContinentDataFromCSV() {
		return this.loadContinentDataFromCSV("Country_Continent.csv");
	}
	
	public static void main(String[] args) {
		ContinentData cd = new ContinentData();
		System.out.println(cd.getContinentForCountry("AE"));
	}

}
