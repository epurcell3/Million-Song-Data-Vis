package backend;

import processing.core.PApplet;

public abstract class AbstractVizBase extends PApplet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2243918292012264746L;

	public abstract void setup();
	
	public abstract void draw();
	
	public abstract void filterYears(int lower, int upper);
	
	public abstract void filterCountries(boolean[] checked);

}
