package backend;

import database.DatabaseConnection;

public class Test {
	
	public static void main(String[] args) {
		DatabaseConnection dc = new DatabaseConnection();
		SongList sl = dc.getArtistTerms();
		
		GenreBase gb = new GenreBase(sl);
	}

}
