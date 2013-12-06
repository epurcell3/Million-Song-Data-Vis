package edu.cs4460.msd.visual.maps;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import edu.cs4460.msd.backend.genre.GenreFilter;
import edu.cs4460.msd.backend.utilities.PathHandler;
import edu.cs4460.msd.backend.visual_abstract.AbstractMap;

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
		map.setPanningRestriction(getMapCenter(), 0);
		map.setZoomRange(1, 1);
		
		MapUtils.createDefaultEventDispatcher(parent, map);
	}
	
	public void draw() {
		map.draw();
		
		
	}
	
	private Location getMapCenter() {
		return getMapLocation(x + width / 2,y + height / 2);
	}
	
	public Location getMapLocation(int mouseX, int mouseY) {
		return map.getLocation(mouseX, mouseY);
	}

	@Override
	public void updateFilter(GenreFilter filter) {
		// TODO Auto-generated method stub
		
	}
	
	

}
