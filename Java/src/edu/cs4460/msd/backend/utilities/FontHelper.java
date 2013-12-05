package edu.cs4460.msd.backend.utilities;

import processing.core.PApplet;
import processing.core.PFont;
import controlP5.ControlFont;

public class FontHelper {
	private PApplet parent;
	
	public FontHelper(PApplet p) {
		this.parent = p;
	}
	
	public ControlFont tabFont() {
		PFont pfont = parent.createFont("Arial", 20, true);
		return  new ControlFont(pfont, 20);
	}
	
	public ControlFont accordionHeadFont() {
		PFont pfont = parent.createFont("Arial", 16, true);
		return  new ControlFont(pfont, 16);
	}
	
	public ControlFont accordionSubFont() {
		PFont pfont = parent.createFont("Arial", 12, true);
		return  new ControlFont(pfont, 12);
	}

}
