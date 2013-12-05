package visualizations;

import backend.GenreBase;
import backend.GenreNode;
import backend.GenreNodeInfo;
import backend.SongList;
import ch.randelshofer.gui.ProgressTracker;
import ch.randelshofer.tree.circlemap.CirclemapModel;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import edu.cs4460.msd.backend.database.DatabaseConnection;
import edu.cs4460.msd.backend.utilities.FontHelper;
import edu.cs4460.msd.backend.visual_abstract.AbstractVizBase;

/**
 * Base PApplet and controller for the visualization
 * @author tbowling3
 *
 */
public class VisBase extends AbstractVizBase {
	public static final int WIDTH = 1000, HEIGHT = 700;
	public static final int DEFAULT_X = 25, DEFAULT_Y = 25, DEFAULT_WIDTH = 500, DEFAULT_HEIGHT = 400;
	private int backgroundColor;
	private ControlP5 cp5;
	private FontHelper fh;
	private String mapTabName = "Map";
	private String circleTabName = "Circles Only";
	private int activeTabId;
	private int mapTabId = 1245, circleTabId = 32155;
	private GenreLocationMap glm;
	private FilterVisBase fvb;
	private ControlVisBase cvb;
	private CircleVis cv;

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
			DatabaseConnection dc = new DatabaseConnection();
			SongList sl = dc.getArtistTerms();

			GenreBase gb = new GenreBase(sl);
			GenreNode root = gb.getNodeTree();
			ProgressTracker p = new ProgressTracker("","");
			CirclemapModel model = new CirclemapModel(root, new GenreNodeInfo(), p);
			cv = model.getView();
			cv.setDimensions(DEFAULT_X, DEFAULT_Y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
			cv.setP(this);
		}

		int filterX = 550, filterY = 10, filterWidth = 400, filterHeight = 500;
		int mapX = DEFAULT_X, mapY = DEFAULT_Y, mapWidth = DEFAULT_WIDTH, mapHeight = DEFAULT_HEIGHT;
		int controlX = DEFAULT_X, controlY = 500, controlWidth = DEFAULT_WIDTH, controlHeight = 100;
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


		fvb = new FilterVisBase(this, filterX, filterY, filterWidth, filterHeight);
		glm = new GenreLocationMap(this, mapX, mapY, mapWidth, mapHeight);
		cvb = new ControlVisBase(this, controlX, controlY, controlWidth, controlHeight);
	}

	public void draw() {
		background(backgroundColor);
		fvb.draw();
		cvb.draw();
		
		if(activeTabId == mapTabId) {
			glm.draw();
		} else if(activeTabId == circleTabId){
			cv.draw();
		}
		
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
		if(theEvent.isTab()) {
			activeTabId = theEvent.getTab().getId();
		} else {
			fvb.controlEvent(theEvent);
			cvb.controlEvent(theEvent);
		}
	}

}
