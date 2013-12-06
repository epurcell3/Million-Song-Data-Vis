package edu.cs4460.msd.visual.maps;

import java.util.Collection;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import edu.cs4460.msd.backend.database.SongList;
import edu.cs4460.msd.backend.genre.GenreFilter;
import edu.cs4460.msd.backend.music.Artist;
import edu.cs4460.msd.backend.utilities.PathHandler;
import edu.cs4460.msd.backend.utilities.SongListHelper;
import edu.cs4460.msd.backend.visual_abstract.AbstractMap;
import edu.cs4460.msd.visual.main.VisBase;

public class ArtistLocationMap extends AbstractMap {
	
	private VisBase parent;
	private Collection<Artist> artists;
	private SongListHelper slh;
	private static final int EDGE = 10;
	private int x, y, width, height;
	private int maxArtistRadius = 40;
	private double scaleFactor;
	
	public ArtistLocationMap(VisBase p, SongList sl, int x, int y, int width, int height) {
		this.parent = p;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		artists = sl.getArtists();
		
		slh = new SongListHelper(sl);
		int artistMax = slh.getMostSongsForArtist();
		scaleFactor = maxArtistRadius / artistMax;
		
		PathHandler ph = new PathHandler();
		String mbTilesConnectionString = "jdbc:sqlite:" + ph.getPathToResource("blankLight-1-3.mbtiles");
		map = new UnfoldingMap(parent, "detail", x, y, width, height, true, false, new MBTilesMapProvider(mbTilesConnectionString));
		//MapUtils.createDefaultEventDispatcher(parent, map);
		
		MapUtils.createDefaultEventDispatcher(parent, map);
	}
	
	public void draw(GenreFilter filter) {
		map.draw();
		for(Artist a: artists) {
			if(filter.artistConforms(a)) {
				Location loc = new Location(a.getArtist_latitude(), a.getArtist_longitude());
				ScreenPosition pos = map.getScreenPosition(loc);
				int radius = (int)(scaleFactor * slh.getSongsCountForArtist(a.getArtist_id()));
				parent.ellipse(pos.x, pos.y, radius, radius);
			}
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
		
		
	} // close mouseMoved
	
	
}
