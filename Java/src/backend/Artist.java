package backend;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Artist {
	private String artist_id;
	private String artist_mbid;
	private String artist_name;
	private double artist_familiarity;
	private double artist_hottness;
	private List<String> artist_terms;
	private List<String> artist_mbtags;
	
	public Artist(String a_id, String mbid, String name, double fam, double hott) {
		this.artist_id = a_id;
		this.artist_mbid = mbid;
		this.artist_name = name;
		this.artist_familiarity = fam;
		this.artist_hottness = hott;
		
		// Actual data structure used is up for discussion
		artist_terms = new ArrayList<String>();
		artist_mbtags = new ArrayList<String>();
	}
	
	public Artist(String a_id, String name) {
		this(a_id, null, name, Double.NaN, Double.NaN);
	}
	
	public void addTerm(String term) {
		if(term != null && !term.isEmpty()) {
			artist_terms.add(term);
		}
	}
	
	public void addTerms(Collection<String> terms) {
		if(terms != null && !terms.isEmpty()) {
			artist_terms.addAll(terms);
		}
	}
	
	public List<String> getTerms() {
		return artist_terms;
	}
	
	public void addMbtag(String tag) {
		if(tag != null && !tag.isEmpty()) {
			artist_mbtags.add(tag);
		}
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
	 * @return the artist_mbid
	 */
	public String getArtist_mbid() {
		return artist_mbid;
	}

	/**
	 * @param artist_mbid the artist_mbid to set
	 */
	public void setArtist_mbid(String artist_mbid) {
		this.artist_mbid = artist_mbid;
	}

	/**
	 * @return the artist_name
	 */
	public String getArtist_name() {
		return artist_name;
	}

	/**
	 * @param artist_name the artist_name to set
	 */
	public void setArtist_name(String artist_name) {
		this.artist_name = artist_name;
	}

	/**
	 * @return the artist_familiarity
	 */
	public double getArtist_familiarity() {
		return artist_familiarity;
	}

	/**
	 * @param artist_familiarity the artist_familiarity to set
	 */
	public void setArtist_familiarity(double artist_familiarity) {
		this.artist_familiarity = artist_familiarity;
	}

	/**
	 * @return the artist_hottness
	 */
	public double getArtist_hottness() {
		return artist_hottness;
	}

	/**
	 * @param artist_hottness the artist_hottness to set
	 */
	public void setArtist_hottness(double artist_hottness) {
		this.artist_hottness = artist_hottness;
	}

    public List<String> getArtist_terms() {
        return artist_terms;
    }

    public List<String> getArtist_mbtags() {
        return artist_mbtags;
    }
}
