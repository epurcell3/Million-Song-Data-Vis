package edu.cs4460.msd.visual.maps;

import java.awt.Rectangle;
import java.util.Collection;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.utils.MapUtils;
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
	private Rectangle bounds;
	private int maxArtistRadius = 30;
	private double scaleFactor;

	public ArtistLocationMap(VisBase p, SongList sl, int x, int y, int width, int height) {
		this.parent = p;
		bounds = new Rectangle(x,y,width,height);
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
			map.getDefaultMarkerManager().addMarker(makeArtistPointMarker(a));
		}
	}

	public void updateFilter(GenreFilter filter) {
		map.getDefaultMarkerManager().clearMarkers();
		for(Artist a: artists) {
			if(filter.artistConforms(a)) {
				map.getDefaultMarkerManager().addMarker(makeArtistPointMarker(a));
			}
		}
	}
	
	private ArtistPointMarker makeArtistPointMarker(Artist a) {
		Location loc = new Location(a.getArtist_latitude(), a.getArtist_longitude());
		float radius = (float) (scaleFactor * slh.getSongsCountForArtist(a.getArtist_id()));
		ArtistPointMarker apm = new ArtistPointMarker(loc, radius, a.getArtist_id(), a.getArtist_name(), bounds);
		apm.setColor(parent, maxArtistRadius); // sets the marker color
		return apm;
	}

	public void draw() {
		map.draw();
	}

	public void mouseMoved(float mouseX, float mouseY) {
		Marker hitMarker = map.getDefaultMarkerManager().getFirstHitMarker(mouseX, mouseY);
		if (hitMarker != null) {
			hitMarker.setSelected(true);
		} else {
			for (Marker marker : map.getDefaultMarkerManager().getMarkers()) {
				marker.setSelected(false);
			}
		}
	} // close mouseMoved
}
