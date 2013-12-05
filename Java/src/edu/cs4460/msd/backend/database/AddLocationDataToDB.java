package edu.cs4460.msd.backend.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import edu.cs4460.msd.backend.utilities.PathHandler;
import map_works.ContinentData;
import backend.Artist;
import backend.SongList;

public class AddLocationDataToDB {
	public static String dbTotal = "MillionSongSubset.db";
	private String dbPath;

	public AddLocationDataToDB() {
		
	} // close constructor
	
	public void performLocationUpdate() {
		PathHandler ph = new PathHandler();
		dbPath = ph.getPathToResource(dbTotal);

		Connection conOut = null;

		try {
			Class.forName("org.sqlite.JDBC").newInstance();

			conOut = DriverManager
					.getConnection("jdbc:sqlite:" + dbPath);

			if (!conOut.isClosed())  System.out.println("Successfully connected to database");
			
			Statement state = conOut.createStatement();
			//state.execute("ALTER TABLE artists_h5 ADD COLUMN artist_country text"); // ALREADY DONE
			
			DatabaseConnectionTotal dct = new DatabaseConnectionTotal();
			SongList sl = dct.getArtistLocation();
			Collection<Artist> artists = sl.getArtists();
			
			
			System.out.println();
			System.out.println("Update Queries:");
			
			ContinentData cd = new ContinentData();
			int counter = 0;
			
			for(Artist a: artists) {
				String a_id = a.getArtist_id();
				double a_lat = a.getArtist_latitude();
				double a_lon = a.getArtist_longitude();
				String country = cd.getCountryForLocation(a_lat, a_lon);
				String continent = cd.getContinentForCountry(country);
				
				String updateSQL = createUpdateSQL(a_id, continent, country);
				System.out.println(counter + "\t" + updateSQL);
				state.execute(updateSQL);
				counter++;
			}
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {

		} // close finally
	}
	
	public String createUpdateSQL(String a_id, String continent, String country) {
		return "UPDATE artists_h5 SET artist_continent = '" + continent + "', artist_country = '" + country + "' WHERE artist_id = '" + a_id + "';";
	}

	public static void main(String[] args) {
		AddLocationDataToDB ald = new AddLocationDataToDB();
		ald.performLocationUpdate();

	}

}
