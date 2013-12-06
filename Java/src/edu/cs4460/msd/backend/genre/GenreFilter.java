package edu.cs4460.msd.backend.genre;

import java.util.ArrayList;
import java.util.List;

import edu.cs4460.msd.backend.music.Artist;
import edu.cs4460.msd.backend.music.Song;

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 12/5/13
 * Time: 11:03 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenreFilter{
    private boolean yearsFiltered;
    private boolean countriesFiltered;
    private boolean continentsFiltered;
    private int maxYear;
    private int minYear;
    private List<String> countries;
    private List<String> continents;

    public GenreFilter() {
        yearsFiltered = false;
        countriesFiltered = false;
        continentsFiltered = false;
        continents = new ArrayList<String>();
        countries = new ArrayList<String>();
    }

    public boolean isYearsFiltered() {
        return yearsFiltered;
    }

    public void setYearsFiltered(boolean yearsFiltered) {
        this.yearsFiltered = yearsFiltered;
    }

    public boolean isCountriesFiltered() {
        return countriesFiltered;
    }

    public void setCountriesFiltered(boolean countriesFiltered) {
        this.countriesFiltered = countriesFiltered;
    }

    public boolean isContinentsFiltered() {
        return continentsFiltered;
    }

    public void setContinentsFiltered(boolean continentsFiltered) {
        this.continentsFiltered = continentsFiltered;
    }

    public int getMaxYear() {
        return maxYear;
    }

    public void setMaxYear(int maxYear) {
        this.maxYear = maxYear;
    }

    public int getMinYear() {
        return minYear;
    }

    public void setMinYear(int minYear) {
        this.minYear = minYear;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getContinents() {
        return continents;
    }

    public void setContinents(List<String> continents) {
        this.continents = continents;
    }
    
    public boolean artistConforms(Artist a) {
    	boolean out = false;
    	if(continentsFiltered) {
    		for(String c: continents) {
    			if(c.equals(a.getArtist_continent())) {
    				return true;
    			}
    		}
    	}
    	if(countriesFiltered) {
    		for(String c: countries) {
    			if(c.equals(a.getArtist_country())) {
    				return true;
    			}
    		}
    	}
    	
    	return out;
    }
    
    public boolean songConforms(Song s) {
    	boolean out = true;
    	
    	if(yearsFiltered) {
    		if(s.getYear() < minYear || maxYear < s.getYear()) {
    			out = false;
    		}
    	}
    	return out;
    }
}
