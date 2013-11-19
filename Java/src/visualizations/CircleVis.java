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
	private static final long serialVersionUID = -3098928797994474272L;
    CircleInCircle[] circles;
    double scale = 0.01;
    int points = 5;
    static int WIDTH = 500;
    static int HEIGHT = 500;
    private ToolTip toolTip;

	public static void main(String args[])
	{
		PApplet.main(new String[] {"--present", "CircleVis"});
	}

	public void setup()
	{
        size(WIDTH, HEIGHT);
        circles = new CircleInCircle[points];
        toolTip = new ToolTip(CircleVis.this, 0, 0);
        
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
            CircleInCircle c = new CircleInCircle(this, x, y, r);
            for(Genre g: topGenres[i].getChildren())
            {
            	if(g.getSongCount() > 50)
            		c.addCircle(g);
            }
            circles[i] = c;
            angle += increment;
        }

	}
	
	
	@Override
	public void mouseMoved() {
		super.mouseMoved();
		
		for(CircleInCircle cic: circles) {
			cic.highlight(mouseX, mouseY);
		}
		toolTip.setPosition(mouseX, mouseY);
	}

	public void draw()
	{
		fill(255,255,255);
		rect(0, 0, WIDTH, HEIGHT);
        for (int i = 0; i < points; i++)
        {
            circles[i].draw();
        }
        // TODO If mouse is over a circle
        toolTip.draw();
	}
}