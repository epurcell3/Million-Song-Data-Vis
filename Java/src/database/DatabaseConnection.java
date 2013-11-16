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
	public static String QUERY_LIMIT = "";
	private DatabaseOperator dbOp;

	public DatabaseConnection() {
		dbOp = new DatabaseOperator();
		
		dbOp.executeSQLUpdate("ATTACH database 'db/subset_track_metadata.db' AS t_db;");
		dbOp.executeSQLUpdate("ATTACH database 'db/subset_artist_term.db' AS at_db;");
		dbOp.executeSQLUpdate("ATTACH database 'subset_artist_similarity.db' AS as_db;");
		System.out.println("Databases attached");
	} // close constructor
	
	public SongList getArtistTags() {
		ResultSet artistMBTags = dbOp.executeSQLQuery(createArtistMbtagsSQLQuery());
		
		SongList sl = new SongList();
		ResultSet rs = artistMBTags;
		try {
			int counter = 0;
			while(rs.next()) {
				// Get data from each row
				String song_id = rs.getString("song_id");
				String title = rs.getString("title");
				String artist_id = rs.getString("artist_id");
				String artist_name = rs.getString("artist_name");
				String term = rs.getString("term");
				
				
				Song s = new Song(song_id, title, artist_id);
				Artist a = new Artist(artist_id, artist_name);
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
				String title = rs.getString("title");
				String artist_id = rs.getString("artist_id");
				String artist_name = rs.getString("artist_name");
				String term = rs.getString("term");
				
				
				Song s = new Song(song_id, title, artist_id);
				Artist a = new Artist(artist_id, artist_name);
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
		q = q + "SELECT song_id, title, 'songs'.artist_id, artist_name, term, year ";
		q = q + "FROM (t_db.'songs' JOIN at_db.'artist_term') ";
		q = q + "WHERE 'songs'.artist_id = 'artist_term'.artist_id AND year <> 0";
		q = q + QUERY_LIMIT;
		q = q + ";";
		return q;
	}
	
	private String createArtistMbtagsSQLQuery() {
		// Get Artist mbtags results
        String q = "";
        q = q + "SELECT song_id, title, 'songs'.artist_id, artist_name, mbtag ";
        q = q + "FROM (t_db.'songs' JOIN at_db.'artist_mbtag') ";
        q = q + "WHERE 'songs'.artist_id = 'artist_mbtag'.artist_id ";
        q = q + QUERY_LIMIT;
        q = q + ";";
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
