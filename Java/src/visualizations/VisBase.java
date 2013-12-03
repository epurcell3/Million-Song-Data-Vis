package visualizations;

import java.util.Arrays;
import java.util.List;

import ch.randelshofer.tree.circlemap.CirclemapModel;
import backend.AbstractVizBase;
import backend.Genre;
import backend.GenreBase;
import backend.GenreNode;
import backend.SongList;
import controlP5.ControlEvent;
import database.DatabaseConnection;

/**
 * Base PApplet and controller for the visualization
 * @author tbowling3
 *
 */
public class VisBase extends AbstractVizBase {
	public static final int WIDTH = 1000, HEIGHT = 700;
	private int backgroundColor;
	private GenreLocationMap glm;
	private FilterVisBase cvb;
	private CircleVis cv;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4819693994329829461L;

	public void setup() {
		size(WIDTH, HEIGHT);
		
		boolean connectToDB = false;
		if(connectToDB) {
			DatabaseConnection dc = new DatabaseConnection();
			SongList sl = dc.getArtistTerms();
			
			// TODO Use sl
			GenreBase gb = new GenreBase(sl);
	        List<Genre> tree = gb.getZeroRank();
	        GenreNode root = gb.getNodeTree();
	        CirclemapModel model = new CirclemapModel(root, null, null);
	        cv = model.getView();
	        cv.setX(100);
	        cv.setY(100);
	        cv.setP(this);
		}
        
		int mapX = 10, mapY = 10, mapWidth = 500, mapHeight = 400;
		int controlX = 550, controlY = 10, controlWidth = 400, controlHeight = 500;
		backgroundColor = color(164);
		
		cvb = new FilterVisBase(this, controlX, controlY, controlWidth, controlHeight);
		glm = new GenreLocationMap(this, mapX, mapY, mapWidth, mapHeight);
	}
	
	public void draw() {
		background(backgroundColor);
		cvb.draw();
		glm.draw();
	}
	
	public void mouseMoved() {
		
	}
	
	public void filterYears(int lower, int upper) {
		// TODO
	}
	
	public void filterCountries(boolean[] checked) {
		// TODO
	}
	
	public void filterContinents(boolean[] checked) {
		// TODO
	}
	
	public void controlEvent(ControlEvent theEvent) {
		cvb.controlEvent(theEvent);
	}

}
