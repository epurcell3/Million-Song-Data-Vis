package visualizations;

import backend.AbstractMap;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import processing.core.PApplet;

public class GenreLocationMap extends AbstractMap {
	private PApplet parent;
	private int x, y, width, height;
	
	public GenreLocationMap(PApplet p, int x, int y, int width, int height) {
		this.parent = p;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		map = new UnfoldingMap(parent, "detail", x, y, width, height);
	}
	
	public void draw() {
		map.draw();
		
		
	}
	
	public Location getMapLocation(int mouseX, int mouseY) {
		return map.getLocation(mouseX, mouseY);
	}
	
	

}
