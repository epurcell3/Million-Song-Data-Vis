import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 10/29/13
 * Time: 7:35 PM
 * To change this template use File | Settings | File Templates.
 */
public class GenreBase {
    HashMap<String,Genre> fullGenreList;
    List<Genre> fullList;
    List<Genre> zeroRank;
    public GenreBase(List<Song> songs){
        fullList = new ArrayList<Genre>();
        fullGenreList = new HashMap<String,Genre>();

        boolean inlist = false; // will just be the dictionary check in python
        for(Song song : songs){
            for(String keyword: song.getMyArtist().getArtist_terms()){
                //will use dictionary lookup to determine if it is already in the list first
                if(fullGenreList.get(keyword) == null){
                    fullGenreList.put(keyword, new Genre(keyword, song);
                    //fullList.add(new Genre(keyword, song.getMyArtist().getArtist_terms()));
                }
                else{
                    //will get the genre from list by keyword
                    fullGenreList.get(keyword).addSong(song);
                   // fullList.get(1).addSong(song);
                }

            }

        }
        zeroRank = new ArrayList<Genre>();
        Genre current = null;
        for(String key : fullGenreList.keySet()){

            generateParentsTree(key);
        }
        for(String key : fullGenreList){
            if(fullGenreList.get(key).getRank() ==0){
                zeroRank.add(fullGenreList.get(key));
            }
        }
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
                for (String keys : current.getMinimalList())
                {
                    if (fullGenreList.get(keys).getRank() == max){
                        parents.add(keys);
                    }
                    else if(fullGenreList.get(keys).getRank() > max){
                        max = fullGenreList.get(keys).getRank();
                        parents = new ArrayList<String>();
                        parents.add(keys)
                    }
                }
            for (String key : parents){
                fullGenreList.get(key).addChild(fullGenreList.get(current));
            }
                //generate loop through current's minimal list and compare ranks of the genre's add current as the child of the highest rank genre
        }



    }
}
