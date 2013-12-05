package edu.cs4460.msd.visual.controls;

import visualizations.VisBase;
import controlP5.ControlEvent;
import controlP5.ControlP5;

public class ControlVisBase {
	private VisBase vb;
	private int x, y, width, height;
	private ControlP5 cp5;
	
	public ControlVisBase(VisBase vb, int x, int y, int width, int height) {
		this.vb = vb;
		this.x = x; 
		this.y = y;
		this.width = width;
		this.height = height;
		
		cp5 = new ControlP5(vb);
	}
	
	public void controlEvent(ControlEvent theEvent) {
		
	}
	
	public void draw() {
		
	}

}
