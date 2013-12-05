package backend;

import java.util.List;

import edu.cs4460.msd.backend.database.DatabaseConnection;

public class Test {
	
	public static void main(String[] args) {
		DatabaseConnection dc = new DatabaseConnection();
		SongList sl = dc.getArtistTerms();
		
		GenreBase gb = new GenreBase(sl);
		List<Genre> tree = gb.getZeroRank();
		System.out.println("List length: " + tree.size());
		for(Genre g : tree){
			System.out.println(g.keyword + ": " + g.getChildren().size());
		}
        Genre jazz = gb.fullGenreList.get("jazz");
        System.out.println("\n JAZZ TEST ");
        System.out.println("Jazz has " + jazz.getChildren().size() + " Children\n");
        for(Genre g : jazz.getChildren()){
            System.out.println(g.keyword + ": " + g.getChildren().size());
        }
	}

}
