package backend;

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

    public GenreNode(Genre genre){
        this.keyword = genre.getKeyword();
        this.songCount = genre.getSongCount();
        this.averageDuration = genre.getAverageDuration();
        this.yearsMap = genre.getYearsMap();
        this.continentMap = genre.getContinentMap();
        this.countryMap = genre.getCountryMap();
        children = new ArrayList<TreeNode>();
        for(Genre cGenre: genre.getChildren()){
            if(cGenre.getSongCount() >= 20)
                children.add(new GenreNode(cGenre));
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

    @Override
    public List<TreeNode> children() {
        return children;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean getAllowsChildren() {
        return true;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
