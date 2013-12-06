package edu.cs4460.msd.backend.genre;

import java.util.ArrayList;
import java.util.List;

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
}
