package edu.cs4460.msd.backend.circles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ch.randelshofer.tree.TreeNode;
import ch.randelshofer.tree.TreePath2;
import ch.randelshofer.tree.Weighter;
import edu.cs4460.msd.backend.genre.GenreNode;

public class SongCountWeighter implements Weighter {

	protected int min = 1;
	protected int max = 1820;
	protected int median = 3001/2;
	protected int[] histogram;
	
	@Override
	public void init(TreeNode root) {
		min = Integer.MAX_VALUE;
		max = Integer.MIN_VALUE;
		ArrayList<Integer> counts = new ArrayList<Integer>();
		collectSongCounts(root, counts);
		Collections.sort(counts);
		if (counts.size() > 0) {
			min = counts.get(0);
			max = counts.get(counts.size() - 1);
			median = counts.get(counts.size()/2);
		}
		
		if (counts.size() > 0) {
			histogram = new int[256];
			for (int count: counts)
			{
				int index = Math.min(histogram.length - 1, Math.max(0, (int) ((count - min) * (histogram.length - 1) / (double) (max - min))));
				histogram[index]++;
			}
		}
		else {
            histogram = new int[1];
            histogram[0] = 1;
		}
	}
	
	protected void collectSongCounts(TreeNode root, List<Integer> counts)
	{
		GenreNode gn = (GenreNode)root;
		int songCount = gn.getSongCount();
		counts.add(songCount);
		for(TreeNode child: gn.children()) {
			collectSongCounts(child, counts);
		}
	}

	@Override
	public float getWeight(TreePath2 path) {
		TreeNode node = (TreeNode) path.getLastPathComponent();
        GenreNode gn = (GenreNode) node;
        long songCount = gn.getSongCount();

        return (float) ((songCount - min) /
                (float) (max - min));
	}

	@Override
	public int[] getHistogram() {
		return histogram;
	}

	@Override
	public String getHistogramLabel(int index) {
		return "1 - 3000";
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
		return (float)(median - min)/(max - min);
	}

}
