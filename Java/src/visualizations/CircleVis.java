package visualizations;

import processing.core.*;
import backend.GenreBase;
import backend.Genre;
import backend.GenreNode;
import backend.SongList;
import database.DatabaseConnection;

import java.util.List;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import ch.randelshofer.tree.circlemap.*;
import ch.randelshofer.tree.TreeView;

public class CircleVis implements TreeView
{
//    static int SONG_REQ = 50;
    static int WIDTH = 750;
    static int HEIGHT = 750;
    int x;
    int y;
    PApplet p;
    
    CirclemapDraw draw;
    boolean isInvalid;
    boolean drawHandles;
    boolean isAdjusting;
    boolean needsSimplify;
    boolean needsProgressive = true;
    CirclemapNode hoverNode;
    boolean isToolTipVisible = false;
    CirclemapTree model;
    
    GenreNode rootNode;

    public CircleVis(int x, int y, PApplet p, CirclemapTree model)
    {
    	this.x = x;
    	this.y = y;
    	this.p = p;
    	
    	this.model = model;
    	this.draw = new CirclemapDraw(model.getRoot(), model.getInfo());
    }
    
    
    //Totally unrelated now...
	public void setup()
	{
        DatabaseConnection dc = new DatabaseConnection();
        SongList sl = dc.getArtistTerms();
        GenreBase gb = new GenreBase(sl);
        List<Genre> tree = gb.getZeroRank();
//        Genre[] topGenres = new Genre[points];
//        for (int i = 0; i < points; i++)
//        {
//            topGenres[i] = tree.get(i);
//        }
//        for (int i = points; i < tree.size(); i++)
//        {
//            int ind = -1;
//            int minval = Integer.MAX_VALUE;
//            Genre genre = tree.get(i);
//            for (int j = 0; j < points; j++)
//            {
//                if (genre.getSongCount() > topGenres[j].getSongCount() && topGenres[j].getSongCount() < minval)
//                {
//                    ind = j;
//                    minval = topGenres[j].getSongCount();
//                }
//            }
//            if (ind != -1)
//                topGenres[ind] = genre;
//        }
        
        
	}

	public void draw()
	{
		//fill in it's background
		p.fill(255, 255, 255);
		p.noStroke();
		p.rectMode(PConstants.CORNER);
		p.rect(x, y, HEIGHT, WIDTH);
		
		//draw vis
		CirclemapNode selectedNode = draw.getDrawRoot();
		if (selectedNode != null)
		{
			if (selectedNode.children().isEmpty())
			{
				draw.drawSubtreeBounds(null, selectedNode, Color.blue);
			}
			else
			{
				draw.drawDescendantSubtreeBounds(null, selectedNode, Color.blue);
			}
		}
		if (hoverNode != null)
		{
			draw.drawNodeBounds(null, hoverNode, Color.red);
		}
		if (drawHandles)
		{
//			double cx = draw.getCX();
//			double cy = draw.getCY();
//			p.fill(0,0,0);
//			AffineTransform t = new AffineTransform();
//			t.transform(cx, cy);
//			
		}
	}

	@Override
	public void setMaxDepth(int newValue) {
		if (newValue != draw.getMaxDepth()) {
            draw.setMaxDepth(newValue);
            isInvalid = true;
            if (newValue == Integer.MAX_VALUE) {
                needsProgressive = true;
            }
            draw();
        }
	}

	@Override
	public int getMaxDepth() {
        return draw.getMaxDepth();
	}

	@Override
	public void setToolTipEnabled(boolean newValue) {
		this.isToolTipVisible = newValue;
	}

	@Override
	public boolean isToolTipEnabled() {
		return this.isToolTipVisible;
	}
	
	public String getInfoText(int x, int y) {
        CirclemapNode node = draw.getNodeAt(x, y);
        return (node == null) ? null : draw.getInfo().getTooltip(node.getDataNodePath());
	}

	@Override
	public String getInfoText(MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();
        CirclemapNode node = draw.getNodeAt(x, y);
        return (node == null) ? null : draw.getInfo().getTooltip(node.getDataNodePath());
	}

	@Override
	public void repaintView() {
		isInvalid = true;
		draw();
	}
	
	private void setCenter(double cx, double cy) {
        draw.setCX(cx);
        draw.setCY(cy);
    }
	
    private Point2D.Double getCenter() {
        return new Point2D.Double(draw.getCX(),draw.getCY());
    }
    
    private void setOuterRadius(double r) {
        draw.setRadius(r);
    }

}