package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import backend.Artist;
import backend.Song;
import backend.SongList;

/**
 * Makes a connection to the database
 * 
 * @author tbowling3
 * 
 */
public class DatabaseConnection extends DatabaseConnectionInterface{

	public DatabaseConnection() {
		dbOp = new DatabaseOperator();
		
	} // close constructor
	
	public SongList getArtistTerms() {
		ResultSet artistTerms = dbOp.executeSQLQuery(createArtistTermsSQLQuery());
		//ResultSet artistMBTags = dbOp.executeSQLQuery(createArtistMbtagsSQLQuery());
		
		SongList sl = new SongList();
		ResultSet rs = artistTerms;  // Set to whichever is of interest
		try {
			int counter = 0;
			while(rs.next()) {
				// Get data from each row
				String song_id = rs.getString("song_id");
				String title = rs.getString("song_title");
				String artist_id = rs.getString("artist_id");
				String artist_mbid = "";
				String artist_name = rs.getString("artist_name");
				String term = rs.getString("artist_term");
				double artist_fam = Double.NaN;
				double artist_hott = Double.NaN;
				double lat = rs.getDouble("artist_latitude");
				double lon = rs.getDouble("artist_longitude");
				String country = rs.getString("artist_country");
				String continent = rs.getString("artist_continent");
				
				
				Song s = new Song(song_id, title, artist_id);
				Artist a = new Artist(artist_id, artist_mbid, artist_name, artist_fam, artist_hott, lat, lon, country, continent);
				a.addTerm(term);
				
				// Add data to SongList
				sl.addSong(s);
				sl.addArtist(a);
				
				counter++;
			} // close while
			System.out.println("Counter: " + counter);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // close catch
		
		return sl;
	}

	private String createArtistTermsSQLQuery() {
		String q = "";
		
		q = q + "SELECT song_id, song_title, 'songs_h5'.artist_id, year, artist_name, artist_term, artist_country, artist_continent ";
		q = q + "FROM 'songs_h5' JOIN 'artists_h5' ON 'songs_h5'.artist_id = 'artists_h5'.artist_id ";
		q = q + "JOIN 'artist_terms_h5' ON 'songs_h5'.artist_id = 'artist_terms_h5'.artist_id ";
		q = q + "WHERE year <> 0 ";
		q = q + QueryLimit + ";";
		
		return q;
	}

	public static void main(String[] args) {
		DatabaseConnection dc = new DatabaseConnection();
		SongList sl = dc.getArtistTerms();
		System.out.println("Song count: " + sl.getSongsCount());
		System.out.println("Artist count: " + sl.getArtistCount());
		dc.closeConnection();
	}
}
