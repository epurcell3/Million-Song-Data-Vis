package visualizations;

import processing.core.*;

import backend.GenreBase;
import backend.Genre;
import backend.SongList;
import database.DatabaseConnection;

import java.util.List;
import java.lang.Math;

public class CircleVis extends PApplet
{
	//CircleInCircle cic;
    CircleInCircle[] circles;
    double scale;

	public static void main(String args[])
	{
		PApplet.main(new String[] {"--present", "CircleVis"});
	}

	public void setup()
	{
        size(500, 500);
        circles = new CircleInCircle[5];
        scale = 1.0;
        DatabaseConnection dc = new DatabaseConnection();
        SongList sl = dc.getArtistTerms();

        GenreBase gb = new GenreBase(sl);
        List<Genre> tree = gb.getZeroRank();
        Genre[] top5 = new Genre[5];
        for (int i = 0; i < 5; i++)
        {
            top5[i] = tree.get(i);
        }
        for (int i = 5; i < tree.size(); i++)
        {
            int ind = -1;
            int minval = Integer.MAX_VALUE;
            Genre genre = tree.get(i);
            for (int j = 0; j < 5; j++)
            {
                if (genre.getSongCount() > top5[j].getSongCount() && top5[j].getSongCount() < minval)
                {
                    ind = j;
                    minval = top5[j].getSongCount();
                }
            }
            if (ind != -1)
                top5[ind] = genre;
        }
        int angle = 18;
        int increment = 72;
        int d = 100;
        for (int i = 0; i < 5; i++)
        {
            int x = (int) Math.cos(angle) * d;
            int y = (int) Math.sin(angle) * d;
            int r = top5[i].getSongCount();
            CircleInCircle c = new CircleInCircle(x, y, r);
            for(Genre g: top5[i].getChildren())
            {
                c.addCircle(g.getSongCount());
            }
            circles[i] = c;
            angle += increment;
        }
		/*cic = new CircleInCircle(100, 100, 50);
		cic.addCircle(10);
		cic.addCircle(25);
		cic.addCircle(5);
		cic.addCircle(7);
		cic.addCircle(3);*/

	}

	public void draw()
	{
        ellipseMode(RADIUS);
        for (int i = 0; i < 5; i++)
        {
            fill(255,0, 0);
            ellipse(circles[i].x, circles[i].y, circles[i].r, circles[i].r);
            fill(0, 255, 0);
            for(CircleInCircle c: circles[i].innerCircles)
            {
                ellipse(c.x, c.y, c.r, c.r);
            }
        }
		/*ellipse(cic.x, cic.y, cic.r, cic.r);
		for(CircleInCircle c: cic.innerCircles)
		{
			fill(0, 255, 0);
			ellipse(c.x, c.y, c.r, c.r);
		}*/
	}
}