package visualizations;

import controlP5.ControlEvent;
import processing.core.PApplet;

public class YearFilterTestBase extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = -848583153743628574L;
	private YearFilter yr;
	
	public void setup() {
		size(400,75);
		yr = new YearFilter(this, 1950, 2011);
		
	}
	
	public void draw() {
		yr.draw();
	}

	public void controlEvent(ControlEvent theControlEvent) {
		yr.controlEvent(theControlEvent);
	}
}
