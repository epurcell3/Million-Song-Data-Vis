package backend;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import edu.cs4460.msd.backend.database.DatabaseOperator;
import edu.cs4460.msd.backend.maps_works.ContinentData;

public class SongList {
	/**
	 * Map for songs.  A song's track_id is used as the key and the value is a Song object
	 */
	HashMap<String, Song> songs;

	/**
	 * 
	 */
	HashMap<String, Artist> artists;

	public SongList() {
		songs = new HashMap<String, Song>();
		artists = new HashMap<String, Artist>();
	}

	/**
	 * Adds the given song to the song list structure
	 * @param s
	 */
	public void addSong(Song s) {
		songs.put(s.getSong_id(), s);
	}

	public Song getSong(String song_id) {
		Song s = songs.get(song_id);
		return s;
	}

	public void addArtist(Artist a) {
		if(artists.containsKey(a.getArtist_id())) {
			Artist a_old = artists.get(a.getArtist_id());
			a_old.addTerms(a.getTerms());
			artists.put(a.getArtist_id(), a_old);
		} else {
			artists.put(a.getArtist_id(), a);
		}
	}

	public int getArtistCount() {
		return artists.size();
	}

	public int getSongsCount() {
		return songs.size();
	}

	public List<String>getTermsForSongId(String song_id) {
		Song s = songs.get(song_id);
		Artist a = artists.get(s.getArtist_id());
		return a.getTerms();
	}

	public Artist getArtist(String a_id) {
		return artists.get(a_id);
	}

	public Set<String> getSongIds() {
		return songs.keySet();	
	}

	public Collection<Song> getSongs() {
		return songs.values();
	}

	public Collection<Artist> getArtists() {
		return artists.values();
	}

	public void printArtistInformation() {
		Set<String> artistIDs = artists.keySet();
		for(String a_id: artistIDs) {
			Artist a = artists.get(a_id);
			System.out.println(a.toString());
		}
	}

	public Set<String> getArtistIds() {
		return artists.keySet();
	}

	public List<Artist> getArtistsForContinent(String continent) {
		ArrayList<Artist> out = new ArrayList<Artist>();
		if(ContinentData.checkIfContinent(continent)) {

			DatabaseOperator dbOp = new DatabaseOperator();
			String sqlQuery = "SELECT artist_id FROM artists_h5 WHERE artist_continent = '" + continent + "';";
			ResultSet rs = dbOp.executeSQLQuery(sqlQuery);

			try {
				while(rs.next()) {
					String a_id = rs.getString("artist_id");
					out.add(artists.get(a_id));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return out;
	}
	
	/**
	 * Returns a list of songs whose artist is associated with the given continent
	 * @param continent
	 * @return
	 */
	public List<Song> getSongsForContinent(String continent) {
		ArrayList<Song> out = new ArrayList<Song>();
		if(ContinentData.checkIfContinent(continent)) {
			DatabaseOperator dbOp = new DatabaseOperator();
			String sqlQuery = "SELECT song_id, artist_id FROM songs_h5, artists_h5 WHERE artist_continent = '" + continent + "' AND songs_h5.artist_id = artists_h5.artist_id;";
			ResultSet rs = dbOp.executeSQLQuery(sqlQuery);

			try {
				while(rs.next()) {
					String s_id = rs.getString("song_id");
					out.add(songs.get(s_id));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return out;
	}
}
