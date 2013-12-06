package edu.cs4460.msd.backend.genre;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import edu.cs4460.msd.backend.database.SongList;
import edu.cs4460.msd.backend.music.Artist;
import edu.cs4460.msd.backend.music.Song;


/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 10/29/13
 * Time: 7:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenreBase {
    HashMap<String,Genre> fullGenreList;
 //   List<Genre> fullList;
    List<Genre> zeroRank;
    Genre rootGenre;
    GenreNode nodeTree;
    
    public GenreBase(SongList sl, int maxChildren){
        rootGenre = new Genre("RootNode",null,null, maxChildren);
      //  fullList = new ArrayList<Genre>();
        fullGenreList = new HashMap<String,Genre>();

        boolean inlist = false; // will just be the dictionary check in python
        //Set<String> songIds = sl.getSongIds();
        Collection<Song> songs = sl.getSongs();
        List<Song> sa = new ArrayList<Song>(songs);
        
        for(int j = 0; j < sa.size(); j++){
            rootGenre.songCount++;
        	Song song = sa.get(j);
        	Artist a = sl.getArtist(song.getArtist_id());
        	List<String> terms = a.getTerms();
        	
            for(int i = 0; i < terms.size(); i++){
                //will use dictionary lookup to determine if it is already in the list first
            	String keyword = terms.get(i);
                if(fullGenreList.get(keyword) == null){
                    fullGenreList.put(keyword, new Genre(keyword, song, a, maxChildren));
                    //fullList.add(new Genre(keyword, song.getMyArtist().getArtist_terms()));
                }
                else{
                    //will get the genre from list by keyword
                    fullGenreList.get(keyword).addSong(song, a);
                   // fullList.get(1).addSong(song);
                }

            }

        }
        zeroRank = new ArrayList<Genre>();
        Genre current = null;
        for(String key : fullGenreList.keySet()){
            fullGenreList.get(key).purgeSelfFromminList();
            generateParentsTree(key);
        }
        for(String key : fullGenreList.keySet()){
            if(fullGenreList.get(key).getRank() ==0){
                zeroRank.add(fullGenreList.get(key));
            }
        }
        for(Genre genre : zeroRank){
            rootGenre.addChild(genre);
        }
        //rootGenre.setChildren(zeroRank);
        nodeTree = new GenreNode(rootGenre, 0);
    }
    public void generateParentsTree(String current){
        int rank = fullGenreList.get(current).getRank();

        if(rank ==0)
        {
            return;
        }
        else {

                int max = 0;
                List<String> parents = new ArrayList<String>();
                for (String keys : fullGenreList.get(current).getMinimalList())
                {
                	
                    if (fullGenreList.get(keys)!= null && fullGenreList.get(keys).getRank() == max){
                        parents.add(keys);
                    }
                    else if(fullGenreList.get(keys)!= null && fullGenreList.get(keys).getRank() > max){
                        if(fullGenreList.get(keys).getRank() < fullGenreList.get(current).getRank()){
                            max = fullGenreList.get(keys).getRank();
                            parents = new ArrayList<String>();
                            parents.add(keys);
                        }
                    }
                }
            for (String key : parents){
                fullGenreList.get(key).addChild(fullGenreList.get(current));
            }
                //generate loop through current's minimal list and compare ranks of the genre's add current as the child of the highest rank genre
        }



    }

    public HashMap<String, Genre> getFullGenreList() {
        return fullGenreList;
    }

    public void setFullGenreList(HashMap<String, Genre> fullGenreList) {
        this.fullGenreList = fullGenreList;
    }

//    public List<Genre> getFullList() {
//        return fullList;
//    }
//
//    public void setFullList(List<Genre> fullList) {
//        this.fullList = fullList;
//    }

    public List<Genre> getZeroRank() {
        return zeroRank;
    }

    public void setZeroRank(List<Genre> zeroRank) {
        this.zeroRank = zeroRank;
    }

    public Genre getRootGenre() {
        return rootGenre;
    }

    public void setRootGenre(Genre rootGenre) {
        this.rootGenre = rootGenre;
    }
    
    public GenreNode getNodeTree() {
    	return nodeTree;
    }
    
    public void setNodeTree(GenreNode nodeTree) {
    	this.nodeTree = nodeTree;
    }
}
