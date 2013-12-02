package visualizations;

import processing.core.PApplet;
import backend.SongList;
import controlP5.ControlEvent;
import database.DatabaseConnection;

/**
 * Base PApplet and controller for the visualization
 * @author tbowling3
 *
 */
public class VisBase extends PApplet {
	public static final int WIDTH = 800, HEIGHT = 700;
	private GenreLocationMap glm;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4819693994329829461L;

	public void setup() {
		size(WIDTH, HEIGHT);
		
		DatabaseConnection dc = new DatabaseConnection();
		SongList sl = dc.getArtistTerms();
		
		// TODO Use sl
		
		int mapX = 10, mapY = 10, mapWidth = 400, mapHeight = 400;
		glm = new GenreLocationMap(this, mapX, mapY, mapWidth, mapHeight);
	}
	
	public void draw() {
		glm.draw();
	}
	
	public void mouseMoved() {
		
	}
	
	public void controlEvent(ControlEvent theEvent) {
		
	}

}
