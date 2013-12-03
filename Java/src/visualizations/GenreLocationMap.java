package visualizations;

import processing.core.PApplet;
import backend.AbstractMap;
import backend.PathHandler;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;

public class GenreLocationMap extends AbstractMap {
	private PApplet parent;
	private int x, y, width, height;
	
	public GenreLocationMap(PApplet p, int x, int y, int width, int height) {
		this.parent = p;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		

		PathHandler ph = new PathHandler();
		String mbTilesConnectionString = "jdbc:sqlite:" + ph.getPathToResource("blankLight-1-3.mbtiles");
		map = new UnfoldingMap(parent, "detail", x, y, width, height, true, false, new MBTilesMapProvider(mbTilesConnectionString));
		MapUtils.createDefaultEventDispatcher(parent, map);
	}
	
	public void draw() {
		map.draw();
		
		
	}
	
	public Location getMapLocation(int mouseX, int mouseY) {
		return map.getLocation(mouseX, mouseY);
	}
	
	

}
