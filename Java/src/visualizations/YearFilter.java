package visualizations;

import backend.AbstractFilter;
import backend.AbstractVizBase;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Range;

public class YearFilter extends AbstractFilter {

	public int myColorBackground;
	private int colorRangeForeground;
	private int colorRangeBackground;
	public int rangeMin = 100;
	public int rangeMax = 100;
	public static final int DEFAULT_WIDTH = 300, DEFAULT_HEIGHT = 40;
	public Range range;
	
	public YearFilter(AbstractVizBase p, int lowLimit, int highLimit, int x, int y, int rangeWidth, int rangeHeight) {
		this.parent = p;
		this.name = "Year";
		setColors();
		int quarter = Math.round((highLimit - lowLimit) / 4);

		cp5 = new ControlP5(parent);
		range = cp5.addRange(name)
				// disable broadcasting since setRange and setRangeValues will trigger an event
				.setBroadcast(false) 
				.setPosition(x,y)
				.setSize(rangeWidth, rangeHeight)
				.setHandleSize(10)
				.setRange(lowLimit,highLimit)
				.setRangeValues(lowLimit + quarter,highLimit - quarter)
				// after the initialization we turn broadcast back on again
				.setBroadcast(true)
				.setDecimalPrecision(0)
				.setColorForeground(colorRangeForeground)
				.setColorBackground(colorRangeBackground)  
				;

		parent.noStroke();
		
	}
	
	private void setColors() {
		myColorBackground = parent.color(0,0,0);
		colorRangeForeground = parent.color(255,40);
		colorRangeBackground = parent.color(255,40);
	}

	public YearFilter(AbstractVizBase p, int lowLimit, int highLimit) {
		this(p, lowLimit, highLimit, 0, 0, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	}

	public void draw() {
		parent.background(100);
//		parent.fill(50);
//		parent.rect(0,0,parent.width,parent.height/2);
	}
	
	/**
	 * Method called when ever the Range slider has been changed.  Updates UI, etc...
	 * @param min
	 * @param max
	 */
	private void updateMethod(int min, int max) {
		parent.filterYears(min, max);
	}

	public void controlEvent(ControlEvent theControlEvent) {
		if(theControlEvent.isFrom(name)) {
			rangeMin = (int) (theControlEvent.getController().getArrayValue(0));
			rangeMax = (int) (theControlEvent.getController().getArrayValue(1));
			updateMethod(rangeMin, rangeMax);
		}

	}

}
