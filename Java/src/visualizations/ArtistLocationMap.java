package visualizations;

import java.util.Collection;

import backend.Artist;
import backend.SongList;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.utils.ScreenPosition;

public class ArtistLocationMap {
	
	private ArtistLocationApplet parent;
	private UnfoldingMap map;
	private Collection<Artist> artists;
	private static final int EDGE = 10;
	
	public ArtistLocationMap(ArtistLocationApplet p, SongList sl) {
		this.parent = p;
		artists = sl.getArtists();
		
		
		map = new UnfoldingMap(parent);
		//MapUtils.createDefaultEventDispatcher(parent, map);
		
		MapUtils.createDefaultEventDispatcher(parent, map);
	}
	
	public void draw() {
		map.draw();
		for(Artist a: artists) {
			Location loc = new Location(a.getArtist_latitude(), a.getArtist_longitude());
			ScreenPosition pos = map.getScreenPosition(loc);
			parent.ellipse(pos.x, pos.y, EDGE, EDGE);
		}
	}
	
	public void mouseMoved(float x, float y) {
//		Location location = map.getLocation(x, y);
//		x = location.x;
//		y = location.y;
		
		String line = "";
		for(Artist a: artists) {
			ScreenPosition pos = map.getScreenPosition(new Location(a.getArtist_latitude(), a.getArtist_longitude()));
			
			if(contains(x,y, pos.x, pos.y, EDGE)) {
				line += a.getArtist_name() + "\n";
			} // close if
		} // close for
		
		parent.setArtistText(line);
	} // close mouseMoved
	
	private boolean contains(float x1, float y1, double cx, double cy, double radius) {
		boolean out = false;
		if(distance(x1, y1, cx, cy) <= radius) {
			out = true;
		}
		return out;
	}
	
	private double distance(double x1, double y1, double x2, double y2) {
		return Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}
}
