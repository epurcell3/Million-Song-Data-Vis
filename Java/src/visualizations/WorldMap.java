package visualizations;

import processing.core.PApplet;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.utils.MapUtils;

public class WorldMap {
	private PApplet parent;
	private UnfoldingMap map;
	
	public WorldMap(PApplet p) {
		this.parent = p;
		
		
		map = new UnfoldingMap(parent);
		MapUtils.createDefaultEventDispatcher(parent, map);
	}
	
	public void draw() {
		map.draw();
	}
	

}
