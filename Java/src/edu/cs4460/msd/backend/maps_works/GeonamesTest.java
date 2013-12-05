package edu.cs4460.msd.backend.maps_works;

import java.io.IOException;

import org.geonames.WebService;

/**
 * Simple test for using Geonames lookups with our database
 * @author tbowling3
 *
 */
public class GeonamesTest {
	
	public static void main(String[] args) {
		WebService.setUserName("mcdc09");
		
		//String searchName = "http://api.geonames.org/countryCode?lat=47.03&lng=10.2&username=mcdc09"; // the string we want to lookup in geonames database
		try {
			String country = WebService.countryCode(35.00, 80.00);
			System.out.println("Country: " + country);
		} catch (IOException e) {
			System.err.println("Error getting Country name");
			e.printStackTrace();
		}
	}
} // close GeonamesTest


