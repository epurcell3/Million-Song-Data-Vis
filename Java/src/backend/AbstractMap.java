package backend;

import de.fhpotsdam.unfolding.UnfoldingMap;

public abstract class AbstractMap {
	
	protected UnfoldingMap map;
	
	public abstract void draw();
	
	protected boolean contains(float x1, float y1, double cx, double cy, double radius) {
		boolean out = false;
		if(distance(x1, y1, cx, cy) <= radius) {
			out = true;
		}
		return out;
	}
	
	protected double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}

}
