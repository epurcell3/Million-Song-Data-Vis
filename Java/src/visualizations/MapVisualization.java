package visualizations;

import codeanticode.glgraphics.GLConstants;
import processing.core.PApplet;

public class MapVisualization extends PApplet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1352136491469503414L;
	private WorldMap wm;

	public void setup() {
		size(768, 768, GLConstants.GLGRAPHICS);
		
		wm = new WorldMap(this);
	}
	
	public void draw() {
		wm.draw();
	}

}
