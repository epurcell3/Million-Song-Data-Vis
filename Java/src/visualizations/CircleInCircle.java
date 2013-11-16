package visualizations;

import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PConstants;

public class CircleInCircle implements Drawable
{
	private PApplet parent;
	
	public int x, y, r;
	public double scale = 0.01;
	public ArrayList<CircleInCircle> innerCircles;
	public int red, grn, blu;

	public CircleInCircle(PApplet parent, int x, int y, int r, int red, int grn, int blu)
	{
		this.parent = parent;
		this.x = x;
		this.y = y;
		this.r = r;
		this.innerCircles = new ArrayList<CircleInCircle>();
		this.red = red;
		this.grn = grn;
		this.blu = blu;
	}
	
	public CircleInCircle(CircleVis parent, int x, int y, int r)
	{
		this(parent, x, y, r, 255, 0, 0);
	}

	public void addCircle(int r)
	{
		this.innerCircles.add(new CircleInCircle(parent, this.x, this.y, r, 0, 255, 0));
		pack();
	}
	
	public void draw()
	{
		parent.ellipseMode(PConstants.RADIUS);
		parent.fill(255,0, 0);
        parent.ellipse(this.x, this.y, (int)(this.r * scale), (int)(this.r * scale));
        for (CircleInCircle c: this.innerCircles)
        {
        	c.draw();
        }
	}

	private void pack()
	{
		int offset = 0;
		for(CircleInCircle c: this.innerCircles)
		{
			c.x = (int)((this.x - this.r + offset + c.r) * scale);
			offset += 2*c.r;
		}
	}
}
