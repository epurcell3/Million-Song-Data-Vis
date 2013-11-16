package visualizations;

import java.util.ArrayList;

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
        highlighted = false;
		this.scale = scale;

	}
	
	public CircleInCircle(CircleVis parent, int x, int y, int r)
	{
		this(parent, x, y, r, 255, 0, 0, 0.01);
	}

	public void addCircle(int r)
	{
		this.innerCircles.add(new CircleInCircle(parent, this.x, this.y, r, 0, 255, 0, 0.05));
		pack();
	}
	
	public void draw()
	{
		parent.ellipseMode(PConstants.RADIUS);
		parent.fill(this.red,this.grn, this.blu);
        parent.ellipse(this.x, this.y, (int)(this.r * scale), (int)(this.r * scale));
        for (CircleInCircle c: this.innerCircles)
        {
        	c.draw();
        }
	}
    public boolean highlight(int mx, int my){
        boolean isHighlighted = false;
        double xs = (x-mx) * (x-mx);
        double ys = (y - my) *(y-my);
        double dist = Math.sqrt(xs + ys);
        if( dist < r){
            isHighlighted = true;
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
        return isHighlighted;
    }
    public boolean highlightInners(int mx, int my){
        boolean current = false;
        for(int i = 0; i < innerCircles.size(); i++){
            current = innerCircles.get(i).highlight(mx,my);
            if(current){
                return true;
            }
        }
        return  current;
    }
	private void pack()
	{
		int offset = 0;
		for(CircleInCircle c: this.innerCircles)
		{
			c.x = (int)((this.x - (this.r*scale) + offset + (c.r*c.scale)));
			offset += 2*c.r*c.scale;
		}
	}
}
