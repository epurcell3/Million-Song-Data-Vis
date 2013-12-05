package edu.cs4460.msd.backend.visual_abstract;

import controlP5.ControlEvent;
import controlP5.ControlP5;

/**
 * Abstract object for a Filter
 * @author tbowling3
 *
 */
public abstract class AbstractFilter {
	protected AbstractVizBase parent;
	protected ControlP5 cp5;
	protected String name;
	
	
	public abstract void draw();
	
	public abstract void controlEvent(ControlEvent theControlEvent);

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	

}
