package visualizations;

import java.util.ArrayList;
//import java.util.Random;

import java.lang.Math;

import processing.core.PApplet;
import processing.core.PConstants;
import backend.Genre;

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
	private Genre genre;
	public int weightHighlight = 5;

	public CircleInCircle(PApplet parent, int x, int y, int r, int red, int grn, int blu, double scale)
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
		
        highlighted = false;
		this.scale = scale;
		this.d = 2 / 3 * this.r;
	}
	
	public CircleInCircle(CircleVis parent, int x, int y, int r)
	{
		this(parent, x, y, r, 255, 0, 0, 0.01);
	}
	
	public void addCircle(Genre g) {
		this.setGenre(g);
		this.addCircle(g.getSongCount());
	}

	public void addCircle(int r)
	{
		this.angle += Math.acos((2*Math.pow(this.d, 2)-Math.pow(r*0.05, 2))/2*Math.pow(this.d, 2));
		if (this.angle > 2 * Math.PI)
		{
			this.angle = Math.PI/2;
			this.d = this.r / 2;
		}
		
		int x = (int)(Math.cos(this.angle) * this.d * this.scale + this.x);
		int y = (int)(Math.sin(this.angle) * this.d * this.scale + this.y);
		this.innerCircles.add(new CircleInCircle(parent, x, y, r, 0, 255, 0, 0.05));
		
		this.angle += Math.acos((2*Math.pow(this.d, 2)-Math.pow(r*0.05, 2))/2*Math.pow(this.d, 2)) + BUFFER;
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
	}
    
	private void setHighlighted() {
		if(highlighted) {
			this.red = redHighlight;
			this.grn = greenHighlight;
			this.blu = blueHighlight;
		} else {
			this.red = redNormal;
			this.grn = greenNormal;
			this.blu = blueNormal;
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
    
//private void pack()
//	{
//		Random rand = new Random();
//		double angle = Math.PI/2;
//		double increment = 2*Math.PI/this.innerCircles.size();
//		
//		for (int i = 0; i < this.innerCircles.size(); i++)
//		{
//			boolean placed = true;
//			do {
//				CircleInCircle c = this.innerCircles.get(i);
//				c.x = this.x;
//				c.y = this.y;
//				//double angle = rand.nextDouble() * 2 * Math.PI;
//				double radius = rand.nextInt((int)(this.r*this.scale - c.r*c.scale));
//				
//				int x = (int)(Math.cos(angle) * radius);
//				int y = (int)(Math.sin(angle) * radius);
//				
//				for (int j = 0; j < i; j++)
//				{
//					CircleInCircle c2 = this.innerCircles.get(j);
//					if ((Math.pow(x - c2.x, 2) + Math.pow(y - c2.y, 2)) < Math.pow(c2.r * c2.scale, 2))
//					{
//						placed = false;
//					}
//				}
//				if (placed)
//				{
//					c.x = x;
//					c.y = y;
//				}
//			} while(!placed);
//			//c.x = (int)((this.x - (this.r*scale) + offset + (c.r*c.scale)));
//			//offset += 2*c.r*c.scale;
//		}
//	}
	
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
