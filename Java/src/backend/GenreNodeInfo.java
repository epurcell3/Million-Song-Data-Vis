package backend;

import java.awt.Color;
import java.awt.Image;

import javax.swing.Action;
import javax.swing.event.ChangeListener;

import ch.randelshofer.tree.DefaultNodeInfo;
import ch.randelshofer.tree.Colorizer;
import ch.randelshofer.tree.NodeInfo;
import ch.randelshofer.tree.TreeNode;
import ch.randelshofer.tree.TreePath2;
import ch.randelshofer.tree.Weighter;

import ch.randelshofer.tree.demo.RGBColorizer;

public class GenreNodeInfo extends DefaultNodeInfo {
	
	private Colorizer colorizer;
	private Weighter weighter;
	private Weighter colorWeighter;
	private int colorWeighterToggle;
	
	private TreeNode root;
	
	public GenreNodeInfo() {
		colorizer = new RGBColorizer();
		weighter = new SongCountWeighter();
		colorWeighter = new SongCountWeighter(); //should change this
		colorWeighterToggle = 0;
	}
	
	@Override
	public void init(TreeNode node) {
		this.root = node;
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
        return gn.getSongCount();
    }

    @Override
    public Color getColor(TreePath2<TreeNode> path) {
        //GenreNode gn = (GenreNode) path.getLastPathComponent();
        return colorizer.get(weighter.getWeight(path));
    }

    @Override
    public String getName(TreePath2<TreeNode> path) {
        GenreNode gn = (GenreNode) path.getLastPathComponent();
        return gn.getKeyword();
    }
    
    @Override
    public String getTooltip(TreePath2<TreeNode> path) {
        StringBuilder buf = new StringBuilder();

        TreePath2<TreeNode> parentPath = path;
        buf.insert(0, getName(path));
        buf.insert(0, "<html>");
        buf.append("<br>");

        GenreNode node = (GenreNode) path.getLastPathComponent();
        if (node.getAllowsChildren()) {
            buf.append("<br>children: ");
            buf.append(/*intFormat.format*/(node.children().size()));
//            buf.append("<br>descendants: ");
//            buf.append(/*intFormat.format*/(node.getDescendantCount()));
        }

        buf.append("<br>Song Count: ");
        buf.append(getWeight(path));

        return buf.toString();
    }
}
