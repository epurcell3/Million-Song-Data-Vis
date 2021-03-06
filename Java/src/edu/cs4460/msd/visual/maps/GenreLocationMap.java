package edu.cs4460.msd.visual.maps;

import java.awt.Color;

import processing.core.PApplet;
import ch.randelshofer.tree.NodeInfo;
import ch.randelshofer.tree.circlemap.CirclemapNode;
import ch.randelshofer.tree.circlemap.CirclemapTree;
import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import de.fhpotsdam.unfolding.utils.ScreenPosition;
import edu.cs4460.msd.backend.genre.GenreFilter;
import edu.cs4460.msd.backend.maps_works.ContinentData;
import edu.cs4460.msd.backend.utilities.PathHandler;
import edu.cs4460.msd.backend.visual_abstract.AbstractMap;
import edu.cs4460.msd.visual.circles.CirclemapDraw;
import edu.cs4460.msd.visual.controls.ToolTip;

public class GenreLocationMap extends AbstractMap {
	private PApplet parent;
	private int x, y, width, height;
	
	private CirclemapTree[] models;
	private CirclemapDraw[] cmDraws;
	private NodeInfo[] infos;
	
	private boolean[] drawContinent = {false, false, false, false, false, false};
	
	private CirclemapNode hoverNode;
	private int hoverIndex;
	
	private boolean isToolTipVisible = true;
	private ToolTip tooltip;
	
	public GenreLocationMap(PApplet p, int x, int y, int width, int height, CirclemapTree[] models) {
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
		
		this.models = models;
		this.cmDraws = new CirclemapDraw[6];
		this.infos = new NodeInfo[6];
		
		for (int i = 0; i < 6; i++)
		{
			this.cmDraws[i] = new CirclemapDraw(this.models[i]);
			this.cmDraws[i].setRadius(40);
			ContinentData cd = new ContinentData();
			Location loc = cd.getContinentCenter(ContinentData.getContinents()[i]);
			ScreenPosition pos = map.getScreenPosition(loc);
			this.cmDraws[i].setCX(pos.x);
			this.cmDraws[i].setCY(pos.y);
			this.infos[i] = this.models[i].getInfo();
		}
		
		this.tooltip = new ToolTip(parent, 0, 0);
	}
	
	public void draw() {
		map.draw();
		
		for(int i = 0; i < 6; i++)
		{
			if (drawContinent[i])
			{
				cmDraws[i].drawTree(parent);
			}
		}
		if (hoverNode != null)
		{
			if(infos[hoverIndex].getWeight(hoverNode.getDataNodePath()) > 0)
			{
				cmDraws[hoverIndex].drawNodeBounds(parent, hoverNode, Color.red);
				if (this.isToolTipVisible && hoverNode != cmDraws[hoverIndex].getRoot())
				{
					tooltip.draw();
				}
			}
		}
	}
	
	public void mouseMoved(int mx, int my)
	{
		for (int i = 0; i < 6; i++)
		{
			if (drawContinent[i])
			{
				hoverNode = cmDraws[i].getNodeAt(mx, my);
				if (hoverNode != null)
				{
					hoverIndex = i;
					tooltip.setText(infos[i].getTooltip(hoverNode.getDataNodePath()));
					tooltip.setXpos(mx - 5);
					tooltip.setYpos(my - 64);
					break;
				}
			}
		}
	}
	
	public void setDrawContinent(int index, boolean bool)
	{
		drawContinent[index] = bool;
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
