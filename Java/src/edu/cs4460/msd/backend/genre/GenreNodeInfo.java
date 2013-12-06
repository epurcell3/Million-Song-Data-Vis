package edu.cs4460.msd.backend.genre;

import java.awt.Color;

import ch.randelshofer.tree.Colorizer;
import ch.randelshofer.tree.DefaultNodeInfo;
import ch.randelshofer.tree.TreeNode;
import ch.randelshofer.tree.TreePath2;
import ch.randelshofer.tree.Weighter;
import ch.randelshofer.tree.demo.RGBColorizer;
import edu.cs4460.msd.backend.utilities.DepthWeighter;
import edu.cs4460.msd.backend.utilities.SongCountWeighter;

public class GenreNodeInfo extends DefaultNodeInfo {
	
	private Colorizer colorizer;
	private Weighter weighter;
	private Weighter colorWeighter;
	private int colorWeighterToggle;
	
	protected GenreFilter filter;
	
	private TreeNode root;
	
	private Color[] colors = {
			new Color(27, 158, 119),
			new Color(217, 95, 2),
			new Color(117, 112, 179),
			new Color(231, 41, 138),
			new Color(102, 166, 30),
			new Color(230, 171, 2)
		};
	
	public GenreNodeInfo() {
		colorizer = new RGBColorizer(colors);
		weighter = new SongCountWeighter();
		colorWeighter = new DepthWeighter();
		colorWeighterToggle = 0;
		filter = new GenreFilter();
	}
	
	public void setFilter(GenreFilter filter) {
		this.filter = filter;
	}

	@Override
	public void init(TreeNode node) {
		this.root = node;
		weighter.init(node);
		colorWeighter.init(node);
	}
	
	@Override
    public Weighter getWeighter() {
        return weighter;
    }

    @Override
    public Colorizer getColorizer() {
        return colorizer;
    }
    
    @Override
    public long getWeight(TreePath2<TreeNode> path) {
        GenreNode gn = (GenreNode) path.getLastPathComponent();
        return gn.getFilteredCount(filter);
    }

    @Override
    public Color getColor(TreePath2<TreeNode> path) {
        //GenreNode gn = (GenreNode) path.getLastPathComponent();
        return colorizer.get(colorWeighter.getWeight(path));
    }

    @Override
    public String getName(TreePath2<TreeNode> path) {
        GenreNode gn = (GenreNode) path.getLastPathComponent();
        return gn.getKeyword();
    }
    
    @Override
    public String getTooltip(TreePath2<TreeNode> path) {
        StringBuilder buf = new StringBuilder();

        buf.insert(0, getName(path));
        buf.append("\n");

        GenreNode node = (GenreNode) path.getLastPathComponent();
        if (node.getAllowsChildren()) {
            buf.append("Children: ");
            buf.append(/*intFormat.format*/(node.children().size()));
//            buf.append("<br>descendants: ");
//            buf.append(/*intFormat.format*/(node.getDescendantCount()));
        }

        buf.append("\nSong Count: ");
        buf.append(getWeight(path));

        return buf.toString();
    }
}
