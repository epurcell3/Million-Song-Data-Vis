package visualizations;

import processing.core.PApplet;


public abstract class Drawable {
    private static PApplet applet;
    public Drawable(PApplet applet){
        this.applet = applet;
    }
}
