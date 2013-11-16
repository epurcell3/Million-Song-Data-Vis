package visualizations;

import java.util.ArrayList;

public class CircleInCircle
{
	public int x, y, r;
	public double scale = 0.01;
	public ArrayList<CircleInCircle> innerCircles;

	public CircleInCircle(int x, int y, int r)
	{
		this.x = x;
		this.y = y;
		this.r = r;
		innerCircles = new ArrayList<CircleInCircle>();
	}

	public void addCircle(int r)
	{
		this.innerCircles.add(new CircleInCircle(this.x, this.y, r));
		pack();
	}

	private void pack()
	{
		int offset = 0;
		for(CircleInCircle c: this.innerCircles)
		{
			c.x = this.x - (int)(this.r * scale) + offset + c.r;
			offset += 2*c.r;
		}
	}
}
