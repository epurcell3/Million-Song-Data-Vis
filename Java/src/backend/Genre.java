package backend;

import java.lang.Integer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
    HashMap<Integer, Integer> yearsMap = new HashMap<Integer,Integer>();
    
    public Genre(String keyword, Song initial, List<String> terms){
        this.keyword = keyword;
        this.minimalList = terms;
        minimalList.remove(keyword);
        removeOriginalDuplicates();
        this.children = new ArrayList<Genre>();
        yearsMap.put(Integer.valueOf(initial.getYear()), Integer.valueOf(1));
        averageDuration= initial.getDuration();
        this.songCount = 1;
    }
    public void addSong(Song song, List<String> terms){
        songCount++;
        if(yearsMap.get(Integer.valueOf(song.getYear())) == null){
            yearsMap.put(Integer.valueOf(song.getYear()), Integer.valueOf(1));
        }
        else {
            Integer newVal = yearsMap.get(Integer.valueOf(song.getYear())) + 1;
            yearsMap.put(Integer.valueOf(song.getYear()), newVal);
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
        children.add(genre);
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
}
