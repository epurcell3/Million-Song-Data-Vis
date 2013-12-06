package edu.cs4460.msd.backend.music;

/**
 * Object representing a song in the database
 * @author tbowling3
 *
 */
public class Song {
	private String track_id;
	private String track_title;
	private String song_id;
	private String artist_id;
	private String release;
	private int year;
	private double duration;
	public static int COUNT = 0;
	private int songNumber;
	
	public Song(String t_id, String title, String s_id, String rel, String a_id, int yr, double dur) {
		this.track_id = t_id;
		this.track_title = title;
		this.song_id = s_id;
		this.release = rel;
		this.artist_id = a_id;
		this.year = yr;
		this.duration = dur;
		
		this.songNumber = (COUNT++);
	}
	
	public Song(String title, String s_id, String rel, String a_id, int yr, double dur) {
		this("", title, s_id, rel, a_id, yr, dur);
	}
	
	public Song(String title, String s_id, String rel, String a_id, int yr) {
		this("", title, s_id, rel, a_id, yr, 0);
	}

	public Song(String s_id, String title, String a_id) {
		this(null, title, s_id, null, a_id, Integer.MIN_VALUE, Double.NaN);
	}

	/**
	 * @return the track_id
	 */
	public String getTrack_id() {
		return track_id;
	}

	/**
	 * @param track_id the track_id to set
	 */
	public void setTrack_id(String track_id) {
		this.track_id = track_id;
	}

	/**
	 * @return the track_title
	 */
	public String getTrack_title() {
		return track_title;
	}

	/**
	 * @param track_title the track_title to set
	 */
	public void setTrack_title(String track_title) {
		this.track_title = track_title;
	}

	/**
	 * @return the song_id
	 */
	public String getSong_id() {
		return song_id;
	}

	/**
	 * @param song_id the song_id to set
	 */
	public void setSong_id(String song_id) {
		this.song_id = song_id;
	}

	/**
	 * @return the release
	 */
	public String getRelease() {
		return release;
	}

	/**
	 * @param release the release to set
	 */
	public void setRelease(String release) {
		this.release = release;
	}

	/**
	 * @return the artist_id
	 */
	public String getArtist_id() {
		return artist_id;
	}

	/**
	 * @param artist_id the artist_id to set
	 */
	public void setArtist_id(String artist_id) {
		this.artist_id = artist_id;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the duration
	 */
	public double getDuration() {
		return duration;
	}

	/**
	 * @param duration the duration to set
	 */
	public void setDuration(double duration) {
		this.duration = duration;
	}
	
	public String toString() {
		return track_title + "\t" + song_id;
	}
	
	/**
	 * @return the songNumber
	 */
	public int getSongNumber() {
		return songNumber;
	}

	/**
	 * @param songNumber the songNumber to set
	 */
	public void setSongNumber(int songNumber) {
		this.songNumber = songNumber;
	}

}
