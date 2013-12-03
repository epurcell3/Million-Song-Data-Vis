package visualizations;

import java.util.Collection;

import backend.AbstractMap;
import backend.Artist;
import backend.SongList;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.utils.ScreenPosition;

public class ArtistLocationMap extends AbstractMap {
	
	private ArtistLocationApplet parent;
	private Collection<Artist> artists;
	private static final int EDGE = 10;
	
	public ArtistLocationMap(ArtistLocationApplet p, SongList sl) {
		this.parent = p;
		artists = sl.getArtists();
		
		int parentWidth = parent.width;
		int parentHeight = parent.height;
		map = new UnfoldingMap(parent, "detail", 10, 10, parentWidth - 200, parentHeight - 20);
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
				line += a.getArtist_continent() + "  " + a.getArtist_name() + "\n";
			} // close if
		} // close for
		
		parent.setArtistText(line);
	} // close mouseMoved
	
	
}
