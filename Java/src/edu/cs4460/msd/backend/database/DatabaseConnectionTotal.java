package edu.cs4460.msd.backend.database;

import java.sql.ResultSet;
import java.sql.SQLException;

import edu.cs4460.msd.backend.music.Artist;

public class DatabaseConnectionTotal extends DatabaseConnectionInterface {
	
	public DatabaseConnectionTotal() {
		dbOp = new DatabaseOperator();
		
		
	}
	
	public SongList getArtistLocation() {
		ResultSet rs = dbOp.executeSQLQuery("SELECT artist_id, artist_name, artist_latitude, artist_longitude, artist_country, artist_continent FROM artists_h5 WHERE artist_latitude NOT NULL;");
		SongList sl = new SongList();
		
		try {
			int counter = 0;
			while(rs.next()) {
				// Get data from each row
				String artist_id = rs.getString("artist_id");
				String artist_name = rs.getString("artist_name");
				double artist_lat = rs.getDouble("artist_latitude");
				double artist_lon = rs.getDouble("artist_longitude");
				String a_country = rs.getString("artist_country");
				String a_continent = rs.getString("artist_continent");
				
				Artist a = new Artist(artist_id, null, artist_name, Double.NaN, Double.NaN, artist_lat, artist_lon, a_country, a_continent);
				
				// Add data to SongList
				sl.addArtist(a);
				counter++;
			} // close while
			System.out.println("Counter: " + counter);
		} catch (SQLException e) {
			System.out.println("Error with SQL Query");
			e.printStackTrace();
		} // close catch
		return sl;
	}
	
	

	public static void main(String[] args) {
		long startT = System.currentTimeMillis();
		DatabaseConnectionTotal dct = new DatabaseConnectionTotal();
		SongList sl = dct.getArtistLocation();
		long stopT = System.currentTimeMillis();
		dct.closeConnection();
		System.out.println("Artists: " + sl.getArtistCount());
		System.out.println("Milliseconds: " + (stopT - startT));

	}

}
