/**
 * @(#)CirclemapDraw.java  1.2  2009-02-04
 *
 * Copyright (c) 2008-2009 Werner Randelshofer, Goldau, Switzerland.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the
 * license agreement you entered into with Werner Randelshofer.
 * For details see accompanying license terms.
 */
package ch.randelshofer.tree.circlemap;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import processing.core.PApplet;
import visualizations.VisBase;
import ch.randelshofer.gui.ProgressObserver;
import ch.randelshofer.tree.NodeInfo;

/**
 * CirclemapDraw draws a {@link CirclemapTree}.
 * <p>
 * Can draw a subtree from any node within the tree.
 *
 * @author Werner Randelshofer
 *Â @version 1.2 2009-02-04 If we prune on the depth of the tree, fill the
 * composite node only by an amount which visualizes its weight.
 * <br>1.1 2009-01-30 Added maxDepth property.
 * <br>1.0 Jan 16, 2008 Created.
 */
public class CirclemapDraw {

    private CirclemapNode root;
    private CirclemapNode drawRoot;
    private NodeInfo info;
    /**
     * Center of the tree.
     */
    private double cx = VisBase.DEFAULT_X + VisBase.DEFAULT_WIDTH / 2,  cy = VisBase.DEFAULT_Y  + VisBase.DEFAULT_HEIGHT / 2;
    /**
     * Radius of the phyllotactic tree.
     */
    private double radius = 96;
    private double scaleFactor = 1;
    private int maxDepth = Integer.MAX_VALUE;

    public CirclemapDraw(CirclemapTree model) {
        this(model.getRoot(), model.getInfo());
    }

    public CirclemapDraw(CirclemapNode root, NodeInfo info) {
        this.root = root;
        this.drawRoot = this.root;
        this.info = info;
    }

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

    /**
     * Draws the tree onto
     * the supplied graphics object.
     */
    public void drawTree(PApplet par, ProgressObserver p) {
        scaleFactor = radius / drawRoot.getRadius();
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

        
        Rectangle clipBounds = new Rectangle(VisBase.DEFAULT_X, VisBase.DEFAULT_Y, VisBase.DEFAULT_WIDTH, VisBase.DEFAULT_HEIGHT);
        par.color(30, 40, 255);
        par.fill(235, 107, 43);
        drawTree0(par, root, depth, sx, sy, scaleFactor, clipBounds, p);
    }

    public void drawTree0(PApplet par, CirclemapNode node, int depth, double px, double py, double sf, Rectangle clipBounds, ProgressObserver p) {
        if (!p.isCanceled()) {
            drawNode(par, node, depth, px, py, sf);
            drawLabel(par, node, depth, px, py, sf);

            if (node.radius * sf > 1 && node.children().size() > 0) {
                double r = node.getRadius() * sf;
                Rectangle2D.Double bounds = new Rectangle2D.Double(
                        cx + sf * px - r,
                        cy + sf * py - r, r * 2, r * 2);
                if (depth < maxDepth && clipBounds.intersects(bounds)) {
                    for (CirclemapNode child : node.children()) {
                        drawTree0(par, child, depth + 1,
                                px + child.getCX(),
                                py + child.getCY(),
                                sf, clipBounds, p);
                    }
                }
            }
        }
    }
    public void drawNode(PApplet par, CirclemapNode node, int depth, double px, double py, double sf) {
        double r = node.getRadius() * sf;
        float ex = (float)(cx + sf * px - r);
        float ey = (float)(cy + sf * py - r);
        float ew = (float)(r * 2);
        float eh = (float)(r * 2);

        Color c = info.getColor(node.getDataNodePath());
    	Color cDark = c.darker();
    	
        if (node.isLeaf()) {
        	par.fill(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
            par.color(cDark.getRed(), cDark.getGreen(), cDark.getBlue(), cDark.getAlpha());
            par.ellipse(ex, ey, ew, eh);
        } else if (depth == maxDepth) {
            double r2 = node.getWeightRadius(info) * sf;
            ex = (float)(cx + sf * px - r2);
            ey = (float)(cy + sf * py - r2);
            ew = eh = (float)(r2 * 2);
            par.fill(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());
            par.color(cDark.getRed(), cDark.getGreen(), cDark.getBlue(), cDark.getAlpha());
            par.ellipse(ex, ey, ew, eh);
        } else {
        	par.color(cDark.getRed(), cDark.getGreen(), cDark.getBlue(), cDark.getAlpha());
        	par.ellipse(ex, ey, ew, eh);
        }
    }


    public void drawLabel(PApplet par, CirclemapNode node, int depth, double px, double py, double sf) {
        if (node.children().size() == 0 || depth == maxDepth) {
            double r;
            if (depth == maxDepth) {
                r = node.getWeightRadius(info) * sf;
            } else {
                r = node.getRadius() * sf;
            }
            Ellipse2D.Double ellipse = new Ellipse2D.Double(
                    cx + sf * px - r,
                    cy + sf * py - r,
                    r * 2, r * 2);
            Font f = par.getFont();
            FontMetrics fm = par.getFontMetrics(f);
            int fh = fm.getHeight();
            if (fh < ellipse.height) {
                par.color(0);

                double space = ellipse.width - 6;

                int y = (int) (ellipse.y + (ellipse.height - fh) / 2) + fm.getAscent();

                // Draw the weight only, if it fully fits into the rectangle
                if (fh * 2 < ellipse.height) {
                    String weightStr = info.getWeightFormatted(node.getDataNodePath());
                    int weightWidth = fm.stringWidth(weightStr);
                    if (weightWidth < space) {
                        par.text(weightStr,
                                (int) (ellipse.x + (ellipse.width - weightWidth) / 2),
                                y + fh / 2);
                        y -= fh / 2;
                    }
                }

                String name;
                char[] nameC;
                int nameLength;
                int nameWidth;

                if (node.children().size() == 0) {
                    // Label for node without children ends with a mid-dot, if
                    // the name is too long
                    name = info.getName(node.getDataNodePath());
                    nameC = name.toCharArray();
                    nameLength = nameC.length;
                    nameWidth = fm.charsWidth(nameC, 0, nameLength);


                    while ((nameWidth >= space) && (nameLength > 1)) {
                        nameLength--;
                        nameC[nameLength - 1] = '·';
                        nameWidth = fm.charsWidth(nameC, 0, nameLength);
                    }
                } else {
                    // Label for node with children allways ands with >,
                    // regardless. So that it is clear, that we pruned
                    // the tree here.
                    name = info.getName(node.getDataNodePath()) + "›";
                    nameC = name.toCharArray();
                    nameLength = nameC.length;
                    nameWidth = fm.charsWidth(nameC, 0, nameLength);


                    while ((nameWidth >= space) && (nameLength > 1)) {
                        nameLength--;
                        nameC[nameLength - 1] = '›';
                        nameWidth = fm.charsWidth(nameC, 0, nameLength);
                    }
                }

                if (nameLength > 1 || nameLength == nameC.length) {
                    par.text(new String(nameC, 0, nameLength),
                            (int) (ellipse.x + (ellipse.width - nameWidth) / 2),
                            y);
                }
            }
        }
    }

    public void drawContours(PApplet par, CirclemapNode node, Color color) {
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

    public void drawNodeBounds(PApplet par, CirclemapNode selectedNode, Color color) {
        par.color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
        double r = selectedNode.getRadius() * scaleFactor;
        double scx = 0;
        double scy = 0;

        CirclemapNode node = selectedNode;
        while (node != null) {
            scx += node.getCX();
            scy += node.getCY();
            node = node.getParent();
        }
        node = drawRoot;
        while (node != null) {
            scx -= node.getCX();
            scy -= node.getCY();
            node = node.getParent();
        }

        double px = scx * scaleFactor + cx;
        double py = scy * scaleFactor + cy;

        par.ellipse((float)(px -r), (float)(py-r), (float) r*2, (float) r*2);
    }

    public void drawSubtreeBounds(PApplet par, CirclemapNode selectedNode, Color color) {
    }

    public void drawDescendantSubtreeBounds(PApplet par, CirclemapNode parent, Color color) {
    }

    /**
     * Returns the node at the specified view coordinates.
     */
    public CirclemapNode getNodeAt(int px, int py) {
        return getNodeAt((px - cx) / scaleFactor, (py - cy) / scaleFactor);
    }

    /**
     * Returns the node at the specified draw coordinates.
     */
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

    public void setMaxDepth(int newValue) {
        maxDepth = newValue;
    }

    public int getMaxDepth() {
        return maxDepth;
    }
}
