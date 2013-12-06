package edu.cs4460.msd.visual.main;

import processing.core.PApplet;
import controlP5.ControlEvent;
import edu.cs4460.msd.visual.controls.ContinentCheckboxes;

public class CheckBoxTestBase extends PApplet {
	private ContinentCheckboxes ccb;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6298027748440120693L;

	public void setup() {
		size(150, 400);
		ccb = new ContinentCheckboxes(this, 10, 10);
	}
	
	public void draw() {
		ccb.draw();
	}
	
	public void controlEvent(ControlEvent theEvent) {
		ccb.controlEvent(theEvent);
	}

}
