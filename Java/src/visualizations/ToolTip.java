package visualizations;

import backend.Genre;
import processing.core.PApplet;
import processing.core.PConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Ryan
 * Date: 11/16/13
 * Time: 4:50 PM
 * To change this template use File | Settings | File Templates.
 */
public class ToolTip implements Drawable {
    private int xpos;
    private int ypos;
    private int height;
    private int width;
    PApplet parent;
    private List<String> text;
    private int textSize;
    public ToolTip(PApplet parent, int xpos, int ypos, int width, int height){
        this.xpos = xpos;
        this.ypos = ypos;
        this.width = width;
        this.height = height;
        this.parent = parent;
        textSize = 12;
        text = new ArrayList<String>();
    }
    public void addString(String words){
        text.add(words);
    }
    public void draw(){
        if(text.size() == 0)
            return;
        int increment = height/ text.size();
        parent.fill(255,255,255);
        parent.stroke(0,0,0);
        parent.rect(xpos,ypos,width,height);
        parent.textAlign(PConstants.RIGHT);
        parent.textSize(textSize);
        for(int i = 0; i < text.size(); i++){
            parent.text(text.get(i), xpos, ypos + (i * increment));
        }
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }

    public int getXpos() {
        return xpos;
    }

    public void setXpos(int xpos) {
        this.xpos = xpos;
    }

    public int getYpos() {
        return ypos;
    }

    public void setYpos(int ypos) {
        this.ypos = ypos;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getTextSize() {
        return textSize;
    }

    public void setTextSize(int textSize) {
        this.textSize = textSize;
    }
}
