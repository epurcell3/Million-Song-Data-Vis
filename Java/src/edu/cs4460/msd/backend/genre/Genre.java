package edu.cs4460.msd.backend.genre;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.cs4460.msd.backend.music.Artist;
import edu.cs4460.msd.backend.music.Song;

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 10/29/13
 * Time: 7:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class Genre {
    String keyword;
    List<String> minimalList; //
    List<Genre> children;
    int songCount;
    double averageDuration;
    int maxChildren;
    Genre smallestChild;
    /**
     * <Year, Count>
     */
    HashMap<Integer, Integer> yearsMap;
    /**
     * <Continent name, Count>
     */
    HashMap<String, Integer> continentMap;
    /**
     * <Country name, Count>
     */
    HashMap<String, Integer> countryMap;
    /**
     * <Key combination, Count>
     */
    HashMap<String, Integer> masterMap;
    
    public Genre(String keyword, Song initial, Artist a, int maxChildren){
        if(keyword.equals("RootNode")){
            this.children = new ArrayList<Genre>();
            yearsMap = new HashMap<Integer,Integer>();
            continentMap  = new HashMap<String, Integer>();
            countryMap = new HashMap<String, Integer>();
            masterMap = new HashMap<String, Integer>();
            minimalList = new ArrayList<String>();
            this.maxChildren = maxChildren;
            return;
        }
        List<String> terms = a.getTerms();
        yearsMap = new HashMap<Integer,Integer>();
        continentMap  = new HashMap<String, Integer>();
        countryMap = new HashMap<String, Integer>();
        masterMap = new HashMap<String, Integer>();
        this.maxChildren = maxChildren;
        this.keyword = keyword;
        this.minimalList = terms;
        minimalList.remove(keyword);
        removeOriginalDuplicates();
        this.children = new ArrayList<Genre>();
        int tempYear = initial.getYear();
        String yearStr = "";
        if(tempYear > 1000 && tempYear < 2050){
            yearStr = String.valueOf(tempYear);
        }
        else{
            yearStr = "None";
        }
        masterMap.put("AllAllAll", Integer.valueOf(1));
        masterMap.put(yearStr + "AllAll", Integer.valueOf(1));
        yearsMap.put(Integer.valueOf(initial.getYear()), Integer.valueOf(1));
        masterMap.put("All" + a.getArtist_continent() + "All", Integer.valueOf(1));
        continentMap.put(a.getArtist_continent(), Integer.valueOf(1));
        masterMap.put("AllAll" + a.getArtist_country(), Integer.valueOf(1));
        countryMap.put(a.getArtist_country(), Integer.valueOf(1));
        masterMap.put(yearStr + "All" + a.getArtist_country(), Integer.valueOf(1));
        masterMap.put(yearStr + a.getArtist_continent() + "All", Integer.valueOf(1));
        masterMap.put("All" + a.getArtist_continent() + a.getArtist_country(), Integer.valueOf(1));
        masterMap.put(yearStr + a.getArtist_continent() + a.getArtist_country(), Integer.valueOf(1));
        averageDuration= initial.getDuration();
        this.songCount = 1;
    }
    private void calcSmallestChild(){
        int min = 1000000;
        Genre filler = null;
        for(int i = 0; i < children.size(); i++){
            if(children.get(i).songCount < min){
                filler = children.get(i);
                min = filler.songCount;
            }

        }
        smallestChild = filler;
    }
    public void addSong(Song song, Artist a){
        List<String> terms = a.getTerms();
        songCount++;
        int tempYear = song.getYear();
        String yearStr = "";
        if(tempYear > 1000 && tempYear < 2050){
            yearStr = String.valueOf(tempYear);
        }
        else{
            yearStr = "None";
        }
        if(masterMap.get(yearStr + a.getArtist_continent() + a.getArtist_country()) != null){
            masterMap.put(yearStr + a.getArtist_continent() + a.getArtist_country(), Integer.valueOf(masterMap.get(yearStr + a.getArtist_continent() + a.getArtist_country()) + 1));
            masterMap.put("AllAllAll", Integer.valueOf(masterMap.get("AllAllAll") + 1));
//            masterMap.put(yearStr + "AllAll", Integer.valueOf(masterMap.get(yearStr + "AllAll") + 1));
//            masterMap.put("All" + a.getArtist_continent() + "All",  Integer.valueOf(masterMap.get("All" + a.getArtist_continent() + "All") + 1));
//            masterMap.put("AllAll" + a.getArtist_country(), Integer.valueOf(masterMap.get("AllAll" + a.getArtist_country()) + 1));
            masterMap.put(yearStr + "All" + a.getArtist_country(),  Integer.valueOf(masterMap.get(yearStr + "All" + a.getArtist_country()) + 1));
            masterMap.put(yearStr + a.getArtist_continent() + "All",Integer.valueOf(masterMap.get(yearStr + a.getArtist_continent() + "All") + 1));
            masterMap.put("All" + a.getArtist_continent() + a.getArtist_country(), Integer.valueOf(masterMap.get("All" + a.getArtist_continent() + a.getArtist_country()) + 1));
        }
        else{
            masterMap.put(yearStr + a.getArtist_continent() + a.getArtist_country(), Integer.valueOf(1));
            if(masterMap.get(yearStr + "All" + a.getArtist_country()) != null){
                masterMap.put(yearStr + "All" + a.getArtist_country(),  Integer.valueOf(masterMap.get(yearStr + "All" + a.getArtist_country()) + 1));
            }
            else{
                masterMap.put(yearStr + "All" + a.getArtist_country(),  Integer.valueOf(1));
            }
            if(masterMap.get(yearStr + a.getArtist_continent() + "All") != null){
                masterMap.put(yearStr + a.getArtist_continent() + "All",Integer.valueOf(masterMap.get(yearStr + a.getArtist_continent() + "All") + 1));
            }
            else{
                masterMap.put(yearStr + a.getArtist_continent() + "All",Integer.valueOf(1));
            }
            if(masterMap.get("All" + a.getArtist_continent() + a.getArtist_country()) != null){
                masterMap.put("All" + a.getArtist_continent() + a.getArtist_country(), Integer.valueOf(masterMap.get("All" + a.getArtist_continent() + a.getArtist_country()) + 1));
            }
            else{
                masterMap.put("All" + a.getArtist_continent() + a.getArtist_country(), Integer.valueOf(1));
            }

        }
        if(yearsMap.get(Integer.valueOf(song.getYear())) == null){
            yearsMap.put(Integer.valueOf(song.getYear()), Integer.valueOf(1));
            masterMap.put(yearStr + "AllAll", Integer.valueOf(1));
        }
        else {
            Integer newVal = yearsMap.get(Integer.valueOf(song.getYear())) + 1;
            masterMap.put(yearStr + "AllAll", Integer.valueOf(masterMap.get(yearStr + "AllAll") + 1));
            yearsMap.put(Integer.valueOf(song.getYear()), newVal);
        }

        if(continentMap.get(a.getArtist_continent()) == null){
            continentMap.put(a.getArtist_continent(), Integer.valueOf(1));
            masterMap.put("All" + a.getArtist_continent() + "All",  Integer.valueOf(1));
        }
        else {
            Integer newVal = continentMap.get(a.getArtist_continent()) + 1;
            masterMap.put("All" + a.getArtist_continent() + "All",  Integer.valueOf(masterMap.get("All" + a.getArtist_continent() + "All") + 1));
            continentMap.put(a.getArtist_continent(), newVal);
        }
        if(countryMap.get(a.getArtist_country()) == null){
            countryMap.put(a.getArtist_country(), Integer.valueOf(1));
            masterMap.put("AllAll" + a.getArtist_country(), Integer.valueOf(1));
        }
        else {
            Integer newVal = countryMap.get(a.getArtist_country()) + 1;
            masterMap.put("AllAll" + a.getArtist_country(), Integer.valueOf(masterMap.get("AllAll" + a.getArtist_country()) + 1));
            countryMap.put(a.getArtist_country(), newVal);
        }
        minListAdjust(terms);
        double most = (((double)(songCount-1))/((double)(songCount)))*averageDuration;
        averageDuration = most +  ((1.0 /(double) songCount)*song.getDuration());
    }
    private void removeOriginalDuplicates(){
        List<String> newMinimalList = new ArrayList<String>();
        for (String term : minimalList){
            if (!newMinimalList.contains(term)){
                newMinimalList.add(term);
            }
        }
        minimalList = newMinimalList;
    }
    private void minListAdjust(List<String> newSongsList){
    	List<String> toRemove = new ArrayList<String>();
        for(String gen : minimalList){
            if(!(newSongsList.contains(gen))){
                toRemove.add(gen);
            }
        }
        for(String gen : toRemove){
        	minimalList.remove(gen);
        }
    }
    public void purgeSelfFromminList(){
        for(int i = 0; i < minimalList.size(); i++){
            if(keyword.equals(minimalList.get(i))){
                minimalList.remove(i);
                i--;
            }
        }
    }
    public int getRank(){
        return minimalList.size();
    }
    public void addChild(Genre genre){
        if(children.size() < maxChildren){
            children.add(genre);
            if(children.size() == maxChildren){
                calcSmallestChild();
            }
        }
        else {
            if(genre.songCount > smallestChild.songCount){
                children.remove(smallestChild);
                children.add(genre);
                calcSmallestChild();
            }

        }
    }
    
    public String toString() {
    	return "Keyword: " + keyword + "\tChildre: " + children.size();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public List<String> getMinimalList() {
        return minimalList;
    }

    public void setMinimalList(List<String> minimalList) {
        this.minimalList = minimalList;
    }

    public List<Genre> getChildren() {
        return children;
    }

    public void setChildren(List<Genre> children) {
        this.children = children;
    }

    public int getSongCount() {
        return songCount;
    }

    public void setSongCount(int songCount) {
        this.songCount = songCount;
    }

    public double getAverageDuration() {
        return averageDuration;
    }

    public void setAverageDuration(double averageDuration) {
        this.averageDuration = averageDuration;
    }

    public HashMap<Integer, Integer> getYearsMap() {
        return yearsMap;
    }

    public void setYearsMap(HashMap<Integer, Integer> yearsMap) {
        this.yearsMap = yearsMap;
    }

    public HashMap<String, Integer> getContinentMap() {
        return continentMap;
    }

    public void setContinentMap(HashMap<String, Integer> continentMap) {
        this.continentMap = continentMap;
    }

    public HashMap<String, Integer> getCountryMap() {
        return countryMap;
    }

    public void setCountryMap(HashMap<String, Integer> countryMap) {
        this.countryMap = countryMap;
    }
    public String getMaxCountry(){
        String maxCountry= "";
        int max = 0;
        for(String key : countryMap.keySet()){
            if(countryMap.get(key).intValue() > max){
                max = countryMap.get(key).intValue();
                maxCountry = key;
            }
        }
        return maxCountry;

    }
    public String getMaxContinent(){
        String maxContinent= "";
        int max = 0;
        for(String key : continentMap.keySet()){
            if(continentMap.get(key).intValue() > max){
                max = continentMap.get(key).intValue();
                maxContinent = key;
            }
        }
        return maxContinent;

    }
    public int getFilteredCount(GenreFilter filter){
        int total = 0;
        if(!filter.isYearsFiltered() && !filter.isContinentsFiltered() && !filter.isCountriesFiltered()){
            return songCount;

        }
        if(filter.isYearsFiltered() && !filter.isContinentsFiltered() && !filter.isCountriesFiltered()){
            for(int i = filter.getMinYear(); i<= filter.getMaxYear(); i++){
                Integer temp = yearsMap.get(Integer.valueOf(i));
                if (temp != null){
                    total += temp;
                }
            }
        }
        else if(!filter.isYearsFiltered() && filter.isContinentsFiltered() && !filter.isCountriesFiltered()){
            for(String s : filter.getContinents()){
                Integer temp = continentMap.get(s);
                if (temp != null){
                    total += temp;
                }
            }
        }
        else if(!filter.isYearsFiltered() && !filter.isContinentsFiltered() && filter.isCountriesFiltered()){
            for(String s : filter.getCountries()){
                Integer temp = countryMap.get(s);
                if (temp != null){
                    total += temp;
                }
            }
        }
        else if(filter.isYearsFiltered() && filter.isContinentsFiltered() && !filter.isCountriesFiltered()){
            for(int i = filter.getMinYear(); i<= filter.getMaxYear(); i++){
                for(String s : filter.getContinents()){
                    Integer temp = masterMap.get(String.valueOf(i) + s + "All");
                    if (temp != null){
                        total += temp;
                    }
                }
            }
        }
        else if(filter.isYearsFiltered() && !filter.isContinentsFiltered() && filter.isCountriesFiltered()){
            for(int i = filter.getMinYear(); i<= filter.getMaxYear(); i++){
                for(String s : filter.getCountries()){
                    Integer temp = masterMap.get(String.valueOf(i) + "All" + s);
                    if (temp != null){
                        total += temp;
                    }
                }
            }
        }
        else if(!filter.isYearsFiltered() && filter.isContinentsFiltered() && filter.isCountriesFiltered()){
            for(String h : filter.getContinents()){
                for(String s : filter.getCountries()){
                    Integer temp = masterMap.get("All" + h + s);
                    if (temp != null){
                        total += temp;
                    }
                }
            }
        }
        else{
            for(int i = filter.getMinYear(); i<= filter.getMaxYear(); i++){
                for(String h : filter.getContinents()){
                    for(String s : filter.getCountries()){
                        Integer temp = masterMap.get(String.valueOf(i) + h + s);
                        if (temp != null){
                            total += temp;
                        }
                    }
                }
            }
        }
        return total;
    }
    public HashMap<String, Integer> getMasterMap() {
        return masterMap;
    }
}
