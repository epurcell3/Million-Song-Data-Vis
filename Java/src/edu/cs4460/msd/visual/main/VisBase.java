package edu.cs4460.msd.visual.main;

import ch.randelshofer.gui.ProgressTracker;
import ch.randelshofer.tree.circlemap.CirclemapModel;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import edu.cs4460.msd.backend.database.DatabaseConnection;
import edu.cs4460.msd.backend.database.SongList;
import edu.cs4460.msd.backend.genre.GenreBase;
import edu.cs4460.msd.backend.genre.GenreFilter;
import edu.cs4460.msd.backend.genre.GenreNode;
import edu.cs4460.msd.backend.genre.GenreNodeInfo;
import edu.cs4460.msd.backend.maps_works.ContinentData;
import edu.cs4460.msd.backend.utilities.FontHelper;
import edu.cs4460.msd.backend.visual_abstract.AbstractVizBase;
import edu.cs4460.msd.visual.circles.CircleVis;
import edu.cs4460.msd.visual.controls.ControlVisBase;
import edu.cs4460.msd.visual.controls.FilterVisBase;
import edu.cs4460.msd.visual.maps.ArtistLocationMap;
import edu.cs4460.msd.visual.maps.GenreLocationMap;


/**
 * Base PApplet and controller for the visualization
 * @author tbowling3
 *
 */
public class VisBase extends AbstractVizBase {
	public static final int WIDTH = 1200, HEIGHT = 800;
	public static final int DEFAULT_X = 25, DEFAULT_Y = 35, DEFAULT_WIDTH = 700, DEFAULT_HEIGHT = 500;
	public static final int SPACING = 25;
	private int backgroundColor;
	private ControlP5 cp5;
	private FontHelper fh;
	private String mapTabName = "Map";
	private String circleTabName = "Circles Only";
	private String mapArtistsName = "Artist Map";
	private int activeTabId;
	private int mapTabId = 1245, circleTabId = 32155, mapArtistsId = 43254;
	private GenreLocationMap glm;
	private ArtistLocationMap alm;
	private FilterVisBase fvb;
	private ControlVisBase cvb;
	private CircleVis cv;
    private GenreFilter filter;
    private SongList sl;
//    private boolean yearsFiltered;
//    private boolean countriesFiltered;
//    private boolean continentsFiltered;
//    private int maxYear;
//    private int minYear;
//    private List<String> countries;
//    private List<String> continents;

	/**
	 * 
	 */
	private static final long serialVersionUID = -4819693994329829461L;

	public void setup() {
		size(WIDTH, HEIGHT);
		cp5 = new ControlP5(this);
		fh = new FontHelper(this);

		boolean connectToDB = true;
		if(connectToDB) {
			updateCircleVis();
			
		}

		int filterX = DEFAULT_X + DEFAULT_WIDTH + SPACING, filterY = 10, filterWidth = 400, filterHeight = 500;
		int mapX = DEFAULT_X, mapY = DEFAULT_Y, mapWidth = DEFAULT_HEIGHT, mapHeight = DEFAULT_HEIGHT;
		int controlX = DEFAULT_X, controlY = DEFAULT_HEIGHT + SPACING, controlWidth = DEFAULT_WIDTH, controlHeight = 100;
		backgroundColor = color(164);

		// Change Font
		cp5.setFont(fh.tabFont());
		
		// Create Tabs
		cp5.getTab("default")
			.activateEvent(true)
			.setLabel(circleTabName)
			.setId(circleTabId)
			.setColorBackground(backgroundColor)
			.setHeight(25)
			;

		cp5.addTab(mapTabName)
			.setColorBackground(backgroundColor)
			.setHeight(25)
			;
		cp5.getTab(mapTabName)
			.activateEvent(true)
			.setId(mapTabId)
			;
		
		cp5.addTab(mapArtistsName)
			.setColorBackground(backgroundColor)
			.setHeight(25)
			;
		cp5.getTab(mapArtistsName)
			.activateEvent(true)
			.setId(mapArtistsId)
			;

		activeTabId = circleTabId;
		fvb = new FilterVisBase(this, filterX, filterY, filterWidth, filterHeight);
		glm = new GenreLocationMap(this, mapX, mapY, mapWidth, mapHeight);
		alm = new ArtistLocationMap(this, sl, mapX, mapY, mapWidth, mapHeight);
		cvb = new ControlVisBase(this, controlX, controlY, controlWidth, controlHeight);
        filter = new GenreFilter();
//        yearsFiltered = false;
//        countriesFiltered = false;
//        continentsFiltered = false;
//        continents = new ArrayList<String>();
//        countries = new ArrayList<String>();
	}

	public void draw() {
		background(backgroundColor);
		fvb.draw();
		cvb.draw();
		
		if(activeTabId == mapTabId) {
			glm.draw(filter);
		} else if(activeTabId == circleTabId){
			cv.draw(filter);
		} else if(activeTabId == mapArtistsId) {
			alm.draw(filter);
		}
		
	}

	public void mouseMoved() {
		if(activeTabId == circleTabId) {
			cv.mouseMoved(mouseX, mouseY);
		} else if(activeTabId == mapArtistsId) {
			alm.mouseMoved(mouseX, mouseY);
		}
	}

	public void filterYears(int lower, int upper) {
        if(lower == 1926 && upper == 2010){
            filter.setYearsFiltered(false);
        }
        else {
            filter.setYearsFiltered(true);
            filter.setMinYear(lower);
            filter.setMaxYear(upper);
        }
		// TODO
	}

	public void filterCountries(boolean[] checked) {
        ContinentData cd = new ContinentData();
        String[] allCountries = cd.findCountriesISOInDatabase();
        filter.getCountries().clear();
        for(int i = 0; i < checked.length; i++){
            if(checked[i]){
                filter.getCountries().add(allCountries[i]);
            }
        }
        if(filter.getCountries().size() > 0){
            filter.setCountriesFiltered(true);
        }
        else {
            filter.setCountriesFiltered(false);
        }

		// TODO
	}

	public void filterContinents(boolean[] checked) {
        String[] allCONTINENTS = ContinentData.getContinents();
        filter.getContinents().clear();
        for(int i = 0; i < checked.length; i++){
            if(checked[i]){
                filter.getContinents().add(allCONTINENTS[i]);
            }
        }
        if (filter.getContinents().size() >0){
            filter.setContinentsFiltered(true);
        }
        else{
            filter.setContinentsFiltered(false);
        }
		// TODO
	}
	
	public void filterSongs(int count) {
		updateCircleVis(count);
	}

	public void controlEvent(ControlEvent theEvent) {
		if(theEvent.isTab()) {
			activeTabId = theEvent.getTab().getId();
		} else {
			fvb.controlEvent(theEvent);
			cvb.controlEvent(theEvent);
		}
	}
	
	private void updateCircleVis() {
		updateCircleVis(-1);
	}
	
	private void updateCircleVis(int limit) {
		DatabaseConnection dc = new DatabaseConnection();
		
		if(limit < 1) {
			dc.setQueryLimit("");
		} else {
			dc.setQueryLimit(limit);
		}
		sl = dc.getArtistTerms();
		

		GenreBase gb = new GenreBase(sl, 6);
		GenreNode root = gb.getNodeTree();
		ProgressTracker p = new ProgressTracker("","");
		CirclemapModel model = new CirclemapModel(root, new GenreNodeInfo(), p);
		cv = model.getView();
		cv.setDimensions(DEFAULT_X, DEFAULT_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		cv.setP(this);
	}

}
