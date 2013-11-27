package visualizations;

import processing.core.PApplet;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Range;

public class YearFilter {
	private PApplet parent;
	public ControlP5 cp5;

	public int myColorBackground;
	private int colorRangeForeground;
	private int colorRangeBackground;
	public int rangeMin = 100;
	public int rangeMax = 100;
	public static final int DEFAULT_WIDTH = 300, DEFAULT_HEIGHT = 40;
	public Range range;
	
	public YearFilter(PApplet p, int lowLimit, int highLimit, int rangeWidth, int rangeHeight) {
		this.parent = p;
		setColors();
		int quarter = Math.round((highLimit - lowLimit) / 4);
		System.out.println("Quarter: " + quarter);

		cp5 = new ControlP5(parent);
		range = cp5.addRange("Year")
				// disable broadcasting since setRange and setRangeValues will trigger an event
				.setBroadcast(false) 
				.setPosition(10,10)
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

	public YearFilter(PApplet p, int lowLimit, int highLimit) {
		this(p, lowLimit, highLimit, DEFAULT_WIDTH, DEFAULT_HEIGHT);
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
		// TODO Result of change to range min and max
	}

	public void controlEvent(ControlEvent theControlEvent) {
		if(theControlEvent.isFrom("Year")) {
			rangeMin = (int) (theControlEvent.getController().getArrayValue(0));
			rangeMax = (int) (theControlEvent.getController().getArrayValue(1));
			updateMethod(rangeMin, rangeMax);
		}

	}

}
