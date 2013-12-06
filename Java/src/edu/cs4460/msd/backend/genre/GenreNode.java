package edu.cs4460.msd.backend.genre;

import ch.randelshofer.tree.TreeNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class GenreNode implements TreeNode {
    String keyword;
    //List<String> minimalList; //
    List<TreeNode> children;
    int songCount;
    double averageDuration;
    HashMap<Integer, Integer> yearsMap;
    HashMap<String, Integer> continentMap;
    HashMap<String, Integer> countryMap;
    HashMap<String, Integer> masterMap;
    
    private int depth;

    public GenreNode(Genre genre, int depth){
        this.keyword = genre.getKeyword();
        this.songCount = genre.getSongCount();
        this.averageDuration = genre.getAverageDuration();
        this.yearsMap = genre.getYearsMap();
        this.continentMap = genre.getContinentMap();
        this.countryMap = genre.getCountryMap();
        this.masterMap = genre.getMasterMap();
        this.depth = depth;
        children = new ArrayList<TreeNode>();
        for(Genre cGenre: genre.getChildren()){
            if(cGenre.getSongCount() >= 20)
                children.add(new GenreNode(cGenre, depth + 1));
        }
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
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
    
    public int getDepth()
    {
    	return depth;
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

    @Override
    public List<TreeNode> children() {
        return children;
    }

    @Override
    public boolean getAllowsChildren() {
        if (children.size() > 0)
        	return true;
        return false;
    }
}
