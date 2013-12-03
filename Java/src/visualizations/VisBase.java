package visualizations;

import java.util.List;

import backend.AbstractVizBase;
import backend.Genre;
import backend.GenreBase;
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
	private GenreLocationMap glm;
	private YearFilter yf;
	private CountryFilter cf;
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4819693994329829461L;

	public void setup() {
		size(WIDTH, HEIGHT);
		
		//DatabaseConnection dc = new DatabaseConnection();
		//SongList sl = dc.getArtistTerms();
		
		// TODO Use sl
		//GenreBase gb = new GenreBase(sl);
        //List<Genre> tree = gb.getZeroRank();
        
		
		int mapX = 10, mapY = 10, mapWidth = 500, mapHeight = 400;
		int yearX = 550, yearY = 10, yearWidth = 300, yearHeight = 25;
		int lowerYear = 1900, upperYear = 2011;
		int countryX = 550, countryY = 75;
		
		glm = new GenreLocationMap(this, mapX, mapY, mapWidth, mapHeight);
		yf = new YearFilter(this, lowerYear, upperYear, yearX, yearY, yearWidth, yearHeight);
		cf = new CountryFilter(this, countryX, countryY);
	}
	
	public void draw() {
		
		yf.draw();
		cf.draw();
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
	
	public void controlEvent(ControlEvent theEvent) {
		if(theEvent.isFrom(cf.getName())) {
			cf.controlEvent(theEvent);
		} else if(theEvent.isFrom(yf.getName())) {
			yf.controlEvent(theEvent);
		}
	}

}
