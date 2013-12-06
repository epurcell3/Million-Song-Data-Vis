package edu.cs4460.msd.visual.controls;

import java.util.ArrayList;
import java.util.List;

import edu.cs4460.msd.backend.utilities.ToolTipResult;
import edu.cs4460.msd.backend.visual_abstract.Drawable;
import processing.core.PApplet;

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
    private static int TOOL_TIP_WIDTH = 125;
    private static int TOOL_TIP_HEIGHT = 25;
    
    public ToolTip(PApplet parent, int xpos, int ypos) {
    	this(parent, xpos, ypos, TOOL_TIP_WIDTH, TOOL_TIP_HEIGHT);
    }
    
    public ToolTip(PApplet parent, int xpos, int ypos, int width, int height){
        this.xpos = xpos;
        this.ypos = ypos;
        this.width = width;
        this.height = height;
        this.parent = parent;
        textSize = 12;
        resetText();
    }
    
    public void addStringLine(String words){
        text.add(words);
    }
    
    /**
     * Moves the ToolTip to the given location
     * @param x
     * @param y
     */
    public void setPosition(int x, int y) {
    	this.xpos = x;
    	this.ypos = y;
    }
    
    /**
     * Clears the text of the ToolTip to show no information
     */
    public void resetText() {
    	text = new ArrayList<String>();
    }
    
    /**
     * Sets the text for the ToolTip with the following format
     * Genre: 'Some genre'
     * Child Genres: 'Some number'
     * 
     * @param genreName
     * @param childGenres
     */
    public void setGenreText(String genreName, int songCount) {
    	resetText();
    	addStringLine("Genre: " + genreName);
    	addStringLine("Song Count: " + songCount);
    }
    
    public void setGenreText(ToolTipResult ttr) {
    	this.setGenreText(ttr.getGenreName(), ttr.getChildrenCount());
    }
    
    public void draw(){
        if(text.size() == 0)
            return;
        int increment = this.textSize + 2;
        parent.fill(255,255,255);
        parent.stroke(0,0,0);
        parent.rect(xpos,ypos,width,height);
        //parent.textAlign(PConstants.RIGHT);
        parent.textSize(textSize);
        parent.fill(0, 0, 0);
        for(int i = 0; i < text.size(); i++){
            parent.text(text.get(i), xpos + 5, ypos + 5 + ((i + 1) * increment));
        }
    }

    public List<String> getText() {
        return text;
    }

    public void setText(List<String> text) {
        this.text = text;
    }
    
    public void setText(String text)
    {
    	resetText();
    	this.text.add(text);
    	this.width = (int)parent.textWidth(text) + 10;
    	this.height = this.textSize * 4 + 12;
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
