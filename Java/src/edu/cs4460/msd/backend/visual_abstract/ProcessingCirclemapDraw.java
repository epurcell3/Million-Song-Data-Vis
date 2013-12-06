package edu.cs4460.msd.backend.visual_abstract;

import java.awt.Rectangle;

import ch.randelshofer.tree.circlemap.CirclemapNode;
import processing.core.PApplet;

public interface ProcessingCirclemapDraw {
	
	/**
     * Draws the tree onto
     * the supplied PApplet object.
     */
    public abstract void drawTree(PApplet par);
    
    /**
     * Draws the subtree onto the supplied PApplet object
     * @param par Graphics Object
     * @param node Current node
     * @param depth
     * @param px
     * @param py
     * @param sf
     * @param clipBounds
     */
    public abstract void drawTree0(PApplet par, CirclemapNode node, int depth, double px, double py, double sf, Rectangle clipBounds);
    
    /**
     * Draws the node onto the supplied PApplet object
     * @param par
     * @param node
     * @param depth
     * @param px
     * @param py
     * @param sf
     */
    public abstract void drawNode(PApplet par, CirclemapNode node, int depth, double px, double py, double sf);
    
    /**
     * Draws the node's label onto the supplied PApplet object
     * @param par
     * @param node
     * @param depth
     * @param px
     * @param py
     * @param sf
     */
    public void drawLabel(PApplet par, CirclemapNode node, int depth, double px, double py, double sf);
    
    /**
     * Returns the node at the specified view coordinates.
     */
    public abstract CirclemapNode getNodeAt(int px, int py);

    /**
     * Returns the node at the specified draw coordinates.
     */
    public abstract CirclemapNode getNodeAt(double px, double py);
}
