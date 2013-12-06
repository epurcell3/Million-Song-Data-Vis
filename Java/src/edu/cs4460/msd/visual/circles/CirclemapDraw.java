package edu.cs4460.msd.visual.circles;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;
import processing.core.PConstants;
import ch.randelshofer.tree.NodeInfo;
import ch.randelshofer.tree.circlemap.CirclemapNode;
import ch.randelshofer.tree.circlemap.CirclemapTree;
import edu.cs4460.msd.backend.visual_abstract.ProcessingCirclemapDraw;
import edu.cs4460.msd.visual.main.VisBase;

public class CirclemapDraw implements ProcessingCirclemapDraw {
	
	private CirclemapNode root;
	private CirclemapNode drawRoot;
	private NodeInfo info;
	
	private double cx = VisBase.DEFAULT_X + VisBase.DEFAULT_WIDTH / 2,  cy = VisBase.DEFAULT_Y  + VisBase.DEFAULT_HEIGHT / 2;
	
	private double radius = 96;
    private double scaleFactor = 1;
    private int maxDepth = Integer.MAX_VALUE;
	
	
	public CirclemapDraw(CirclemapTree model)
	{
		this(model.getRoot(), model.getInfo());
	}
	
	public CirclemapDraw(CirclemapNode root, NodeInfo info)
	{
		this.root = this.drawRoot = root;
		this.info = info;
	}
	
	/*
	 * Getters and Setters
	 */
	
    public double getCX() {
        return cx;
    }

    public void setCX(double newValue) {
        cx = newValue;
    }

    public double getCY() {
        return cy;
    }

    public void setCY(double newValue) {
        cy = newValue;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double newValue) {
        radius = newValue;
    }
    
    public NodeInfo getInfo() {
        return info;
    }

    public CirclemapNode getRoot() {
        return root;
    }

    public CirclemapNode getDrawRoot() {
        return drawRoot;
    }

    public void setDrawRoot(CirclemapNode newValue) {
        this.drawRoot = newValue;
    }

    public void setMaxDepth(int newValue) {
        maxDepth = newValue;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    /*
     * Draw functions
     */
    
	@Override
	public void drawTree(PApplet par) {
		scaleFactor = radius/drawRoot.radius;
		double sx = 0;
        double sy = 0;
        CirclemapNode node = drawRoot;
        int depth = 1;
        while (node != null) {
            sx -= node.getCX();
            sy -= node.getCY();
            node = node.getParent();
            depth--;
        }

        //TODO clipBounds should be the CircleVis bounds
        Rectangle clipBounds = par.getBounds();
        if (clipBounds == null) {
            clipBounds = new Rectangle(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
        }
        
        drawTree0(par, root, depth, sx, sy, scaleFactor, clipBounds);
	}

	@Override
	public void drawTree0(PApplet par, CirclemapNode node, int depth,
			double px, double py, double sf, Rectangle clipBounds) {
		drawNode(par, node, depth, px, py, sf);
		
		if (node.radius * sf > 1 && node.children().size() > 0)
		{
			double r = node.getRadius() * sf;
			Rectangle2D.Double bounds = new Rectangle2D.Double(
					cx + sf * px - r,
					cy + sf*py - r,
					r*2, r*2);
			if (depth < maxDepth && clipBounds.intersects(bounds))
			{
				for (CirclemapNode child: node.children())
				{
					drawTree0(par, child, depth + 1, 
							px + child.getCX(),
							py + child.getCY(),
							sf, clipBounds);
				}
			}
		}
	}

	@Override
	public void drawNode(PApplet par, CirclemapNode node, int depth, double px,
			double py, double sf) {
		double r = node.getRadius() * sf;
		double x = cx + px * sf;
		double y = cy + py * sf;
		
		Color c = info.getColor(node.getDataNodePath());
		Color cd = c.darker();
		
		if (node.isLeaf())
		{
			par.noFill();
			par.stroke(cd.getRed(), cd.getGreen(), cd.getBlue(), cd.getAlpha());
			par.smooth();
			par.ellipseMode(PConstants.RADIUS);
			par.ellipse((float)x, (float)y, (float)r, (float)r);
		}
		else if (depth == maxDepth)
		{
			double r2 = node.getWeightRadius(info);
			par.fill(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
			par.stroke(cd.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
			par.smooth();
			par.ellipseMode(PConstants.RADIUS);
			par.ellipse((float)x, (float)y, (float)r2, (float)r2);
		}
		else
		{
			par.fill(255, 255, 255);
			par.noStroke();
			par.ellipseMode(PConstants.RADIUS);
			par.ellipse((float)x, (float)y, (float)r, (float)r);
			par.fill(c.getRed(), c.getGreen(), c.getBlue(), 50);
			par.stroke(cd.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
			par.smooth();
			par.ellipseMode(PConstants.RADIUS);
			par.ellipse((float)x, (float)y, (float)r, (float)r);
		}
	}

	@Override
	public CirclemapNode getNodeAt(int px, int py) {
		return getNodeAt((px - cx) / scaleFactor, (py - cy) / scaleFactor);
	}

	@Override
	public CirclemapNode getNodeAt(double px, double py) {
		CirclemapNode parent = drawRoot;
        int depth = 1;
        while (parent != null) {
            px += parent.getCX();
            py += parent.getCY();
            parent = parent.getParent();
            depth--;
        }

        if (root.contains(px, py)) {
            CirclemapNode found = root;
            parent = found;
            do {
                parent = found;
                px -= parent.cx;
                py -= parent.cy;
                for (CirclemapNode node : parent.children()) {
                    if (node.contains(px, py)) {
                        found = node;
                        depth++;
                        break;
                    }
                }
            } while (found != parent && depth < maxDepth);
            return found;
        } else {
            return null;
        }
	}

	@Override
	public void drawLabel(PApplet par, CirclemapNode node, int depth,
			double px, double py, double sf) {
		
		
	}

}
