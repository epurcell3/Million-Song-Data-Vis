/*
 * @(#)CirclemapModel.java  1.0  2008-01-16
 *
 * Copyright (c) 2008 Werner Randelshofer, Goldau, Switzerland.
 * All rights reserved.
 *
 * You may not use, copy or modify this file, except in compliance with the
 * license agreement you entered into with Werner Randelshofer.
 * For details see accompanying license terms.
 */
package ch.randelshofer.tree.circlemap;

import ch.randelshofer.gui.ProgressObserver;
import ch.randelshofer.tree.TreeNode;
import ch.randelshofer.tree.NodeInfo;
import edu.cs4460.msd.visual.circles.CircleVis;

/**
 * CirclemapModel manages a CirclemapTree and its CirclemapView.
 * 
 * @author Werner Randelshofer
 * @version 1.0 2008-01-16 Created.
 */
public class CirclemapModel {
    private CirclemapTree tree;
    private NodeInfo info;
    
    /** Creates a new instance. */
    public CirclemapModel(TreeNode root, NodeInfo info, ProgressObserver p) {
        tree = new CirclemapTree(root, info, p);
        this.info = info;
    }
    
    public CircleVis getView() {
        return new CircleVis(tree);
    }
    
    public NodeInfo getInfo() {
        return info;
    }
}
