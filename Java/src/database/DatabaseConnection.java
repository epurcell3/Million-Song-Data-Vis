package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Makes a connection to the database
 * @author tbowling3
 *
 */
public class DatabaseConnection {
	public DatabaseConnection(String filepath) {
		Connection c = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection(filepath + "db/subset_dummy.db");
	      System.out.println("Opened database successfully");
	      
	      Statement s = c.createStatement();
	      
	      s.execute("ATTACH database 'db/subset_track_metadata.db' AS t_db;");
	      s.execute("ATTACH database 'db/subset_artist_term.db' AS at_db;");
	      s.execute("ATTACH database 'subset_artist_similarity.db' AS as_db;");
	      System.out.println("Databases attached");
	      s.close();
	      c.close();
	      
	      
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	    
	}
	
	public ArrayList<String> executeQuery(String sql) {
		return null;
	}

	public static void main(String[] args) {
		DatabaseConnection dc = new DatabaseConnection("/Users/ducttapeboro/College/CS_4460/MillionSong/Million-Song-Data-Vis/Java/");
	}
}


