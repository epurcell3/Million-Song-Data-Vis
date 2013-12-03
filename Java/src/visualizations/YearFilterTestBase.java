package visualizations;

import backend.AbstractVizBase;
import controlP5.ControlEvent;

public class YearFilterTestBase extends AbstractVizBase {
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

	@Override
	public void filterYears(int lower, int upper) {}

	@Override
	public void filterCountries(boolean[] checked) {}
}
