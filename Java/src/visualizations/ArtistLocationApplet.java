package visualizations;

import processing.core.PApplet;
import backend.SongList;
import codeanticode.glgraphics.GLConstants;
import database.DatabaseConnectionTotal;

public class ArtistLocationApplet extends PApplet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 464355466041128604L;
	private ArtistLocationMap alm;
	private String artistText = "";

	public void setup() {
		size(768, 768, GLConstants.GLGRAPHICS);
		
		DatabaseConnectionTotal dct = new DatabaseConnectionTotal();
		SongList sl = dct.getArtistLocation();
		alm = new ArtistLocationMap(this, sl);
		
	}
	
	public void mouseMoved() {
		alm.mouseMoved(mouseX, mouseY);
	}
	
	public void draw() {
		fill(255);
		alm.draw();
		
		fill(50);
		text(artistText, 50, 50);
	}
	
	public void mouseText(String t) {
		artistText = t;
		
	}
}
