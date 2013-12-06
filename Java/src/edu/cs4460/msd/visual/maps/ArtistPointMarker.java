package edu.cs4460.msd.visual.maps;

import java.awt.Color;
import java.awt.Rectangle;

import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PGraphics;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;

public class ArtistPointMarker extends SimplePointMarker {
	private String artist_id;
	private String artist_name;
	protected int space = 6;
	protected float size = 15;
	private float fontSize = 12;
	private PFont font;
	private Rectangle bounds;

	public ArtistPointMarker(Location location, float radius, String a_id, String artist_name, Rectangle bounds) {
		super(location);
		this.setRadius(radius);
		this.artist_id = a_id;
		this.artist_name = artist_name;
		this.bounds = bounds;
	}

	/* (non-Javadoc)
	 * @see de.fhpotsdam.unfolding.marker.SimplePointMarker#draw(processing.core.PGraphics, float, float)
	 */
	@Override
	public void draw(PGraphics pg, float x, float y) {
		if(bounds.contains(x, y)) {
			super.draw(pg, x, y);
			pg.pushStyle();
			pg.pushMatrix();
			// label
			if (selected && artist_name != null) {
				if (font != null) {
					pg.textFont(font);
				}
				pg.fill(highlightColor);
				pg.stroke(highlightStrokeColor);
				pg.rect(x + strokeWeight / 2, y - fontSize + strokeWeight / 2 - space, pg.textWidth(artist_name) + space * 1.5f,
						fontSize + space);
				pg.fill(255, 255, 255);
				pg.text(artist_name, Math.round(x + space * 0.75f + strokeWeight / 2),
						Math.round(y + strokeWeight / 2 - space * 0.75f));
			}
			pg.popMatrix();
			pg.popStyle();
		}
	}

	public void setColor(PApplet p, int maxArtistRadius) {
		Color cP = getColorForRadius(radius, maxArtistRadius);
		int c = p.color(cP.getRed(), cP.getGreen(), cP.getBlue(), cP.getAlpha() / 2);
		this.setColor(c);
	}

	private Color getColorForRadius(double radius, int maxArtistRadius) {
		Color out = null;
		double factor = maxArtistRadius / radius;
		if(factor > maxArtistRadius * 4/5) {
			out = new Color(237,248,251);
		} else if(factor > maxArtistRadius * 3/5) {
			out = new Color(178, 226, 226);
		} else if(factor > maxArtistRadius * 2/5) {
			out = new Color(102, 194, 164);
		} else if(factor > maxArtistRadius * 1/5) {
			out = new Color(44, 162, 95);
		} else if(factor >= 0) {
			out = new Color(0, 109, 44);
		}

		return out;
	}

	/**
	 * @return the artist_id
	 */
	public String getArtist_id() {
		return artist_id;
	}

	/**
	 * @param artist_id the artist_id to set
	 */
	public void setArtist_id(String artist_id) {
		this.artist_id = artist_id;
	}

}
