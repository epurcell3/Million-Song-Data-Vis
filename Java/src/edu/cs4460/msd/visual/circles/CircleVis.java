package edu.cs4460.msd.visual.circles;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import processing.core.PApplet;
import processing.core.PConstants;
import ch.randelshofer.gui.ProgressTracker;
import ch.randelshofer.tree.TreeView;
import ch.randelshofer.tree.circlemap.CirclemapDraw;
import ch.randelshofer.tree.circlemap.CirclemapNode;
import ch.randelshofer.tree.circlemap.CirclemapTree;
import edu.cs4460.msd.backend.genre.GenreNode;

public class CircleVis implements TreeView
{
//    static int SONG_REQ = 50;
    private int width = 500;
    private int height = 400;
    private int x;
    private int y;
    private PApplet p;
    
    private CirclemapDraw cmDraw;
    private boolean isInvalid;
    private boolean drawHandles;
    private boolean isAdjusting;
    private boolean needsSimplify;
    private boolean needsProgressive = true;
    private CirclemapNode hoverNode;
    private boolean isToolTipVisible = false;
    private CirclemapTree model;
    
    private GenreNode rootNode;

    public CircleVis(CirclemapTree model)
    {
    	//this.x = x;
    	//this.y = y;
    	//this.p = p;
    	
    	this.model = model;
    	this.cmDraw = new CirclemapDraw(model.getRoot(), model.getInfo());
    }
    
    
    public void setP(PApplet p)
    {
    	this.p = p;
    }

	public void draw()
	{
		//fill in it's background
		p.fill(255, 255, 255);
		p.noStroke();
		p.rectMode(PConstants.CORNER);
		p.rect(x, y, width, height);
		
		//draw vis
		CirclemapNode selectedNode = cmDraw.getDrawRoot();
		if (selectedNode != null)
		{
			if (selectedNode.children().isEmpty())
			{
				cmDraw.drawSubtreeBounds(p, selectedNode, Color.blue);
			}
			else
			{
//				draw.drawDescendantSubtreeBounds(p, selectedNode, Color.blue);
				ProgressTracker pt = new ProgressTracker("","");
				cmDraw.drawTree(p, pt);
			}
		}
		if (hoverNode != null)
		{
			cmDraw.drawNodeBounds(null, hoverNode, Color.red);
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
		if (newValue != cmDraw.getMaxDepth()) {
            cmDraw.setMaxDepth(newValue);
            isInvalid = true;
            if (newValue == Integer.MAX_VALUE) {
                needsProgressive = true;
            }
            draw();
        }
	}

	@Override
	public int getMaxDepth() {
        return cmDraw.getMaxDepth();
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
        CirclemapNode node = cmDraw.getNodeAt(x, y);
        return (node == null) ? null : cmDraw.getInfo().getTooltip(node.getDataNodePath());
	}

	@Override
	public String getInfoText(MouseEvent evt) {
        int x = evt.getX();
        int y = evt.getY();
        CirclemapNode node = cmDraw.getNodeAt(x, y);
        return (node == null) ? null : cmDraw.getInfo().getTooltip(node.getDataNodePath());
	}

	@Override
	public void repaintView() {
		isInvalid = true;
		draw();
	}
	
	private void setCenter(double cx, double cy) {
        cmDraw.setCX(cx);
        cmDraw.setCY(cy);
    }
	
    private Point2D.Double getCenter() {
        return new Point2D.Double(cmDraw.getCX(),cmDraw.getCY());
    }
    
    private void setOuterRadius(double r) {
        cmDraw.setRadius(r);
    }

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setX(int x)
    {
    	this.x = x;
    }
    
    public void setY(int y)
    {
    	this.y = y;
    }
    
    public void setDimensions(int x, int y, int width, int height) {
    	this.setXY(x, y);
    	this.setWidth(width);
    	this.setHeight(height);
    }
    
    public void setXY(int x, int y) {
    	this.setX(x);
    	this.setY(y);
    }
}