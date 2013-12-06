package edu.cs4460.msd.backend.utilities;

import java.util.ArrayList;
import java.util.Collections;

import ch.randelshofer.tree.TreeNode;
import ch.randelshofer.tree.TreePath2;
import ch.randelshofer.tree.Weighter;
import edu.cs4460.msd.backend.genre.GenreNode;

public class DepthWeighter implements Weighter {
	
	protected int min = 0;
	protected int max = 100;
	protected int median = 50;
	protected int[] histogram;
	

	@Override
	public void init(TreeNode root) {
		min = Integer.MAX_VALUE;
		max = Integer.MIN_VALUE;
		ArrayList<Integer> depths = new ArrayList<Integer>();
		collectDepths(root, depths);
		Collections.sort(depths);
		
		if (depths.size() > 0) {
			min = depths.get(0);
			max = depths.get(depths.size() - 1);
			median = depths.get(depths.size()/2);
		}
		
		if (depths.size() > 0) {
			histogram = new int[256];
			for (int depth: depths)
			{
				int index = Math.min(histogram.length - 1, Math.max(0, (int) ((depth - min) * (histogram.length - 1) / (double) (max - min))));
				histogram[index]++;
			}
		}
		else {
            histogram = new int[1];
            histogram[0] = 1;
		}
	}
	
	protected void collectDepths(TreeNode root, ArrayList<Integer> depths)
	{
		GenreNode gn = (GenreNode)root;
		int songCount = gn.getDepth();
		depths.add(songCount);
		for(TreeNode child: gn.children()) {
			collectDepths(child, depths);
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public float getWeight(TreePath2 path) {
		TreeNode node = (TreeNode)path.getLastPathComponent();
		GenreNode gn = (GenreNode)node;
		int depth = gn.getDepth();
		return (float)(depth - min)/(max - min);
	}

	@Override
	public int[] getHistogram() {
		return histogram;
	}

	@Override
	public String getHistogramLabel(int index) {
		return min + " - " + max;
	}

	@Override
	public String getMinimumWeightLabel() {
		return Integer.toString(min);
	}

	@Override
	public String getMaximumWeightLabel() {
		return Integer.toString(max);
	}

	@Override
	public float getMedianWeight() {
		return median;
	}

}
