package visualizations;

import processing.core.PApplet;
import backend.SongList;
import codeanticode.glgraphics.GLConstants;
import controlP5.ControlEvent;
import database.DatabaseConnectionTotal;

public class ArtistLocationApplet extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 464355466041128604L;
	private ArtistLocationMap alm;
	private static boolean USE_CHECKBOX = true;
	private ContinentCheckboxes ccbx;
	private String artistText = "";

	public void setup() {
		size(868, 768, GLConstants.GLGRAPHICS);
		
		DatabaseConnectionTotal dct = new DatabaseConnectionTotal();
		SongList sl = dct.getArtistLocation();
		alm = new ArtistLocationMap(this, sl);
		
		if (USE_CHECKBOX) ccbx = new ContinentCheckboxes(this, 700, 59);
		
	}
	
	public void mouseMoved() {
		alm.mouseMoved(mouseX, mouseY);
	}
	
	public void keyPressed() {
		if (USE_CHECKBOX) ccbx.keyPressed();
	}
	
	public void controlEvent(ControlEvent theEvent) {
		if (USE_CHECKBOX) ccbx.controlEvent(theEvent);
	}
	
	public void draw() {
		fill(255,255,0);
		alm.draw();
		if (USE_CHECKBOX) ccbx.draw();
		
		fill(50);
		text(artistText, 50, 50);
	}
	
	/**
	 * @return the artistText
	 */
	public String getArtistText() {
		return artistText;
	}

	/**
	 * @param artistText the artistText to set
	 */
	public void setArtistText(String artistText) {
		this.artistText = artistText;
	}
}
