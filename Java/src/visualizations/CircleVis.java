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
	/**
	 * 
	 */
	private static final long serialVersionUID = -3098928797994474272L;
	//CircleInCircle cic;
    CircleInCircle[] circles;
    double scale = 0.01;
    int points = 5;
    static int WIDTH = 500;
    static int HEIGHT = 500;

	public static void main(String args[])
	{
		PApplet.main(new String[] {"--present", "CircleVis"});
	}

	public void setup()
	{
        size(WIDTH, HEIGHT);
        circles = new CircleInCircle[points];
        DatabaseConnection dc = new DatabaseConnection();
        SongList sl = dc.getArtistTerms();

        GenreBase gb = new GenreBase(sl);
        List<Genre> tree = gb.getZeroRank();
        Genre[] topGenres = new Genre[points];
        for (int i = 0; i < points; i++)
        {
            topGenres[i] = tree.get(i);
        }
        for (int i = points; i < tree.size(); i++)
        {
            int ind = -1;
            int minval = Integer.MAX_VALUE;
            Genre genre = tree.get(i);
            for (int j = 0; j < points; j++)
            {
                if (genre.getSongCount() > topGenres[j].getSongCount() && topGenres[j].getSongCount() < minval)
                {
                    ind = j;
                    minval = topGenres[j].getSongCount();
                }
            }
            if (ind != -1)
                topGenres[ind] = genre;
        }
        double angle = Math.PI / 2;
        double increment = 2 * Math.PI / points;
        int d = 150;
        for (int i = 0; i < points; i++)
        {
            int x = (int)(Math.cos(angle) * d) + WIDTH/2;
            int y = (int)(Math.sin(angle) * d) + HEIGHT/2;
            int r = topGenres[i].getSongCount();
            CircleInCircle c = new CircleInCircle(x, y, r);
            for(Genre g: topGenres[i].getChildren())
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
		fill(255, 255, 255);
		rect(0, 0, WIDTH, HEIGHT);
        ellipseMode(RADIUS);
        for (int i = 0; i < 5; i++)
        {
            fill(255,0, 0);
            ellipse(circles[i].x, circles[i].y, (int)(circles[i].r * scale), (int)(circles[i].r * scale));
            fill(0, 255, 0);
            double scale = 0.1;
            for(CircleInCircle c: circles[i].innerCircles)
            {
                ellipse(c.x, c.y, (int)(c.r * scale), (int)(c.r * scale));
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