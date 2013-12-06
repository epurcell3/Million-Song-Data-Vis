package edu.cs4460.msd.backend.utilities;

import java.util.Collection;
import java.util.HashMap;

import edu.cs4460.msd.backend.database.SongList;
import edu.cs4460.msd.backend.music.Song;

public class SongListHelper {
	private SongList sl;
	private HashMap<String, Integer> artistCounts;
	
	public SongListHelper(SongList sl) {
		this.sl = sl;
		
		Collection<Song> songs = sl.getSongs();
		artistCounts = new HashMap<String, Integer>(); 
		
		// Put counts for each artist into HashMap
		for(Song s: songs) {
			if(artistCounts.containsKey(s.getArtist_id())) {
				int currentCount = artistCounts.get(s.getArtist_id());
				artistCounts.put(s.getArtist_id(), currentCount + 1);
			} else {
				artistCounts.put(s.getArtist_id(), 1);
			}
		}
	}
	
	public int getMostSongsForArtist() {
		// Find max count
		int count = -1;
		
		Collection<Integer> v = artistCounts.values();
		for(int i: v) {
			if(i > count) count = i;
		}
		return count;
	}
	
	public int getSongsCountForArtist(String a_id) {
		return artistCounts.get(a_id);
	}

}
