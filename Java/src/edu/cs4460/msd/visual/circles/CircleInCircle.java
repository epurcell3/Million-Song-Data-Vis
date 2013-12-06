package edu.cs4460.msd.visual.circles;

import java.util.ArrayList;
//import java.util.Random;

import java.lang.Math;

import edu.cs4460.msd.backend.genre.Genre;
import edu.cs4460.msd.backend.visual_abstract.Drawable;
import edu.cs4460.msd.visual.controls.ToolTip;
import processing.core.PApplet;
import processing.core.PConstants;

public class CircleInCircle implements Drawable
{
	private PApplet parent;
	public boolean highlighted;
	public int x, y, r;
	public double scale;
	public ArrayList<CircleInCircle> innerCircles;

	public int red, grn, blu;
	
	private double angle = Math.PI/2;
	private double d;
	private double BUFFER = 5 * 2*Math.PI/180;
	
	public int redNormal = 255, greenNormal = 0, blueNormal = 0;
	public int redHighlight = 255;
	public int greenHighlight = 255;
	public int blueHighlight = 0;
	public int weightHighlight = 5;
	
	private Genre genre;
	private ToolTip tooltip;

	public CircleInCircle(PApplet parent, int x, int y, int r, int red, int grn, int blu, double scale, Genre genre)
	{
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.r = r;
		this.innerCircles = new ArrayList<CircleInCircle>();
		this.red = red;
		this.grn = grn;
		this.blu = blu;
		
		//Make the highlight a little less of an eyesore and dynamic
		redHighlight = this.red >= 155 ? 255 : this.red + 100;
		greenHighlight = this.grn >= 155 ? 255 : this.grn + 100;
		blueHighlight = this.blu >= 155 ? 255 : this.blu + 100;
		
		redNormal = this.red;
		greenNormal = this.grn;
		blueNormal = this.blu;
		
        highlighted = false;
		this.scale = scale;
		this.d = this.r * 2 / 3 * this.scale;
		
		this.genre = genre;
	}
	
	public CircleInCircle(CircleVis parent, int x, int y, int r, Genre genre)
	{
		//this(parent, x, y, r, 255, 0, 0, 0.01, genre);
	}
	
	public void addCircle(Genre g) {
		//this.setGenre(g);
		this.addCircle(g.getSongCount(), g);
	}

	public void addCircle(int r, Genre genre)
	{
		double bc = 2*Math.pow(this.d, 2);
		double a = 2*Math.pow(r*0.05, 2);
		double bca = (bc-a);
		double bcabc = bca/bc;
		this.angle += Math.acos(bcabc);
		if (this.angle > 5 * Math.PI / 2)
		{
			this.angle = Math.PI/2;
			this.d = this.r / 2;
		}
		
		int x = (int)(Math.cos(this.angle) * this.d * this.scale + this.x);
		int y = (int)(Math.sin(this.angle) * this.d * this.scale + this.y);
		this.innerCircles.add(new CircleInCircle(parent, x, y, r, 0, 255, 0, 0.05, genre));
		
		this.angle += Math.acos(bcabc) + BUFFER;
		//pack();
	}
	
	public void draw()
	{
		parent.ellipseMode(PConstants.RADIUS);
		parent.fill(this.red,this.grn, this.blu);
		if (highlighted)
			parent.strokeWeight(weightHighlight);
		else
			parent.strokeWeight(1);
        parent.ellipse(this.x, this.y, (int)(this.r * scale), (int)(this.r * scale));
        for (CircleInCircle c: this.innerCircles)
        {
        	c.draw();
        }
        parent.strokeWeight(1);
        if (tooltip != null)
        	tooltip.draw();
	}
    
	private void setHighlighted() {
		if(highlighted) {
			this.red = redHighlight;
			this.grn = greenHighlight;
			this.blu = blueHighlight;
			tooltip = new ToolTip(this.parent, this.x, this.y + 10);
			tooltip.setGenreText(genre.getKeyword(), genre.getSongCount());
		} else {
			this.red = redNormal;
			this.grn = greenNormal;
			this.blu = blueNormal;
			tooltip = null;
		}
	}
	
	/**
     * Tests if a given point is contained in the circle
     * @param mx
     * @param my
     * @return
     */
    public boolean contains(int mx, int my) {
    	boolean out = false;
    	double xs = (x-mx) * (x-mx);
        double ys = (y - my) *(y-my);
        double dist = Math.sqrt(xs + ys);
        
        if( dist < r * scale){
            out = true;
        }
    	return out;
    }
    
    public int getChildrenCount() {
    	int count = 0;
    	for(CircleInCircle c: innerCircles) {
    		count += c.getChildrenCount();
    	}
    	
    	return count + 1;
    }
    
    
    public boolean highlight(int mx, int my){
        boolean isHighlighted = false;
        if(contains(mx, my)){
            isHighlighted = true;
            
            // Check if inner circles should be highlighted instead
            if (!(highlightInners(mx,my))){
                highlighted = true;
            }
            else {
                highlighted = false;
            }
        }
        else {
            highlighted = false;
        }
        
        setHighlighted();
        return isHighlighted;
    }
    
    private boolean highlightInners(int mx, int my){
        boolean current = false;
        for(int i = 0; i < innerCircles.size(); i++){
            current = innerCircles.get(i).highlight(mx,my);
            if(current){
                return true;
            }
        }
        return  current;
    }
	
	/**
	 * @return The keyword for the genre object
	 */
	public String getGenreKeyword() {
		return genre.getKeyword();
	}
	
	/**
	 * @param keyword The keyword to set for the genre object
	 */
	public void setGenreKeyword(String keyword) {
		genre.setKeyword(keyword);
	}
	
	
	/**
	 * @return the genre
	 */
	public Genre getGenre() {
		return genre;
	}

	/**
	 * @param genre the genre to set
	 */
	public void setGenre(Genre genre) {
		this.genre = genre;
	}
}
