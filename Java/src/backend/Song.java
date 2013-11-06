package backend;

/**
 * Object representing a song in the database
 * @author tbowling3
 *
 */
public class Song {
	private String track_id;
	private String track_title;
	private String song_id;
	private String release;
	private Artist myArtist;
	private int year;
	private double duration;
	
	private Song(String t_id, String title, String s_id, String rel, Artist artist, int yr, double dur) {
		this.track_id = t_id;
		this.track_title = title;
		this.song_id = s_id;
		this.release = rel;
		this.myArtist = artist;
		this.year = yr;
		this.duration = dur;
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
	 * @return the myArtist
	 */
	public Artist getMyArtist() {
		return myArtist;
	}

	/**
	 * @param myArtist the myArtist to set
	 */
	public void setMyArtist(Artist myArtist) {
		this.myArtist = myArtist;
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

}
