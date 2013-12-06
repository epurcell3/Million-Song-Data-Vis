package edu.cs4460.msd.visual.maps;

import processing.core.PApplet;
import ch.randelshofer.tree.NodeInfo;
import ch.randelshofer.tree.circlemap.CirclemapNode;
import ch.randelshofer.tree.circlemap.CirclemapTree;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import edu.cs4460.msd.backend.genre.GenreFilter;
import edu.cs4460.msd.backend.utilities.PathHandler;
import edu.cs4460.msd.backend.visual_abstract.AbstractMap;
import edu.cs4460.msd.visual.circles.CirclemapDraw;

public class GenreLocationMap extends AbstractMap {
	private PApplet parent;
	private int x, y, width, height;
	
	private CirclemapTree[] models;
	private CirclemapDraw[] cmDraws;
	private NodeInfo[] infos;
	
	private CirclemapNode hoverNode;
	
	public GenreLocationMap(PApplet p, int x, int y, int width, int height, CirclemapTree[] models) {
		this.parent = p;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		
		this.models = models;
		this.cmDraws = new CirclemapDraw[6];
		this.infos = new NodeInfo[6];
		for (int i = 0; i < 6; i++)
		{
			this.cmDraws[i] = new CirclemapDraw(this.models[i]);
			this.infos[i] = this.models[i].getInfo();
		}

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
