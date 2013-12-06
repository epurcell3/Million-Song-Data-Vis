package edu.cs4460.msd.visual.main;

import java.util.ArrayList;

import processing.core.PConstants;
import ch.randelshofer.gui.ProgressTracker;
import ch.randelshofer.tree.circlemap.CirclemapModel;
import ch.randelshofer.tree.circlemap.CirclemapTree;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import edu.cs4460.msd.backend.database.DatabaseConnection;
import edu.cs4460.msd.backend.database.SongList;
import edu.cs4460.msd.backend.genre.BaseFilteredGenreNodeInfo;
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
	
	private DatabaseConnection dc;
    private SongList sl;
    private GenreBase gb;
    
	private ContinentData cd;
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

	/**
	 * 
	 */
	private static final long serialVersionUID = -4819693994329829461L;

	public void setup() {
		size(WIDTH, HEIGHT);
		cp5 = new ControlP5(this);
		fh = new FontHelper(this);
		dc = new DatabaseConnection();
		cd = new ContinentData();

		boolean connectToDB = true;
		if(connectToDB) {
			buildGenreBase();
			buildCVTree();
			buildGMTree();
		}

		int mapX = DEFAULT_X, mapY = DEFAULT_Y, mapWidth = DEFAULT_HEIGHT, mapHeight = DEFAULT_HEIGHT;
		int filterX = DEFAULT_X + DEFAULT_WIDTH + SPACING, filterY = 10, filterWidth = 400, filterHeight = 500;
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
			glm.draw();
		} else if(activeTabId == circleTabId){
			cv.draw(filter);
			noStroke();
			fill(backgroundColor);
			rectMode(PConstants.CORNER);
			rect(0,0, WIDTH, DEFAULT_Y);
			rect(0, DEFAULT_Y, DEFAULT_X, HEIGHT);
			rect(DEFAULT_X + DEFAULT_WIDTH, DEFAULT_Y, WIDTH, HEIGHT);
			rect(0, DEFAULT_Y + DEFAULT_HEIGHT, WIDTH, HEIGHT);
		} else if(activeTabId == mapArtistsId) {
			alm.draw();
		}
		
	}

	public void mouseMoved() {
		if(activeTabId == circleTabId) {
			cv.mouseMoved(mouseX, mouseY);
		} else if(activeTabId == mapArtistsId) {
			alm.mouseMoved(mouseX, mouseY);
		} else if (activeTabId == mapTabId) {
			glm.mouseMoved(mouseX, mouseY);
		}
	}
	
	public void mouseClicked() {
		if(activeTabId == circleTabId) {
			cv.mouseClicked(mouseX, mouseY);
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
        filterChanged();
	}

	public void filterCountries(boolean[] checked) {
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
        filterChanged();
	}

	public void filterContinents(boolean[] checked) {
		if (activeTabId != mapTabId)
		{
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
	        filterChanged();
		}
		else
		{
			for (int i = 0; i < 6; i++)
			{
				if (checked[i])
					glm.setDrawContinent(i, true);
				else
					glm.setDrawContinent(i, false);
			}
		}
	}
	
	public void filterSongs(int count) {
		buildGenreBase(count);
	}
	
	private void filterChanged() {
		cv.filterChanged(filter);
		alm.updateFilter(filter);
		glm.updateFilter(filter);
	}

	public void controlEvent(ControlEvent theEvent) {
		if(theEvent.isTab()) {
			activeTabId = theEvent.getTab().getId();
			if (activeTabId == mapTabId)
			{
				fvb.checkAllContinents();
			}
		} else {
			fvb.controlEvent(theEvent);
			cvb.controlEvent(theEvent);
		}
	}
	
	private void buildGenreBase() {
		buildGenreBase(-1);
	}
	
	private void buildGenreBase(int limit) {
		if(limit < 1) {
			dc.setQueryLimit("");
		} else {
			dc.setQueryLimit(limit);
		}
		sl = dc.getArtistTerms();
		

		gb = new GenreBase(sl, 10);
	}
	
	private void buildCVTree() {
		GenreNode root = gb.getNodeTree();
		ProgressTracker p = new ProgressTracker("","");
		CirclemapModel model = new CirclemapModel(root, new GenreNodeInfo(), p);
		cv = model.getView();
		cv.setDimensions(DEFAULT_X, DEFAULT_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
		cv.setP(this);
	}
	
	private void buildGMTree() {
		GenreNode root = gb.getNodeTree();
		ProgressTracker p = new ProgressTracker("", "");
		CirclemapModel[] models = new CirclemapModel[6];
		CirclemapTree[] trees = new CirclemapTree[6];
		String[] continents = {"Africa", "Asia", "Europe", "North America", "Oceania", "South America"};
		for (int i = 0; i < 6; i++) 
		{
			GenreFilter baseFilter = new GenreFilter();
			baseFilter.setContinentsFiltered(true);
			ArrayList<String> continent = new ArrayList<String>();
			continent.add(continents[i]);
			baseFilter.setContinents(continent);
			models[i] = new CirclemapModel(root, new BaseFilteredGenreNodeInfo(baseFilter), p);
			trees[i] = models[i].getTree();
		}
		int mapX = DEFAULT_X, mapY = DEFAULT_Y, mapWidth = DEFAULT_HEIGHT, mapHeight = DEFAULT_HEIGHT;
		glm = new GenreLocationMap(this, mapX, mapY, mapWidth, mapHeight, trees);
	}

}
