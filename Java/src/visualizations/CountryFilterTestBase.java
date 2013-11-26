package visualizations;

import controlP5.ControlEvent;
import processing.core.PApplet;

public class CountryFilterTestBase extends PApplet {
	private CountryFilter cf;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5966319136308332604L;

	public void setup() {
		size(315, 725);
		cf = new CountryFilter(this, 10, 10);
	}
	
	public void draw() {
		cf.draw();
	}
	
	public void controlEvent(ControlEvent theEvent) {
		cf.controlEvent(theEvent);
	}

}
