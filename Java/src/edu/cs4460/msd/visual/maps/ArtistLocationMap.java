package edu.cs4460.msd.visual.maps;

import java.awt.Color;
import java.util.Collection;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import edu.cs4460.msd.backend.database.SongList;
import edu.cs4460.msd.backend.genre.GenreFilter;
import edu.cs4460.msd.backend.music.Artist;
import edu.cs4460.msd.backend.utilities.SongListHelper;
import edu.cs4460.msd.backend.visual_abstract.AbstractMap;
import edu.cs4460.msd.visual.main.VisBase;

public class ArtistLocationMap extends AbstractMap {

	private VisBase parent;
	private Collection<Artist> artists;
	private SongListHelper slh;
	private static final int EDGE = 10;
	private int x, y, width, height;
	private int maxArtistRadius = 30;
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

		map = new UnfoldingMap(parent, "detail", x, y, width, height);
		addArtistsAsMarkers();

		MapUtils.createDefaultEventDispatcher(parent, map);
	}

	private void addArtistsAsMarkers() {
		for(Artist a: artists) {
			Location loc = new Location(a.getArtist_latitude(), a.getArtist_longitude());
			SimplePointMarker spm = new SimplePointMarker(loc);
		}
	}

	public void updateFilter(GenreFilter filter) {

	}

	public void draw() {
		map.draw();
		for(Artist a: artists) {
			if(filter == null || filter.artistConforms(a)) {
				Location loc = new Location(a.getArtist_latitude(), a.getArtist_longitude());
				ScreenPosition pos = map.getScreenPosition(loc);
				double radiusD = (scaleFactor * slh.getSongsCountForArtist(a.getArtist_id()));
				int radius = (int) radiusD;
				Color c = getColorForRadius(radiusD);
				//parent.fill(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
				parent.color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
				Color cLight = c.brighter();
				parent.fill(cLight.getRed(), cLight.getGreen(), cLight.getBlue(), cLight.getAlpha()/2);
				if((x <= pos.x - radius && pos.x + radius <= x + width) && (y <= pos.y - radius && pos.y + radius <= y + height)) {	
					parent.ellipse(pos.x, pos.y, radius, radius);
				}
			}

		}
	}

	private Color getColorForRadius(double radius) {
		Color out = null;
		double factor = maxArtistRadius / radius;
		if(factor > maxArtistRadius * 4/5) {
			out = new Color(237,248,251);
		} else if(factor > maxArtistRadius * 3/5) {
			out = new Color(178, 226, 226);
		} else if(factor > maxArtistRadius * 2/5) {
			out = new Color(102, 194, 164);
		} else if(factor > maxArtistRadius * 1/5) {
			out = new Color(44, 162, 95);
		} else if(factor >= 0) {
			out = new Color(0, 109, 44);
		}

		return out;
	}

	public void mouseMoved(float x, float y) {
		//		Location location = map.getLocation(x, y);
		//		x = location.x;
		//		y = location.y;

		//String line = "";
		for(Artist a: artists) {
			ScreenPosition pos = map.getScreenPosition(new Location(a.getArtist_latitude(), a.getArtist_longitude()));

			if(contains(x,y, pos.x, pos.y, EDGE)) {
				//line += a.getArtist_continent() + "  " + a.getArtist_name() + "\n";
			} // close if
		} // close for


	} // close mouseMoved


}
