package backend;

import processing.core.PApplet;
import controlP5.ControlEvent;
import controlP5.ControlP5;

/**
 * Abstract object for a Filter
 * @author tbowling3
 *
 */
public abstract class AbstractFilter {
	protected PApplet parent;
	protected ControlP5 cp5;
	
	
	public abstract void draw();
	
	public abstract void controlEvent(ControlEvent theControlEvent);
	

}
