package visualizations;

import controlP5.CheckBox;
import controlP5.ControlP5;
import map_works.ContinentData;
import processing.core.PApplet;

public class CountryFilter {
	private PApplet parent;
	private String[] countriesFull;
	private String[] countriesInDB;
	private ControlP5 cp5;
	private CheckBox checkbox;
	private ContinentData cd;
	public static int DEFAULT_WIDTH = 100, DEFAULT_HEIGHT = 300;
	
	public CountryFilter(PApplet p, int x, int y) {
		this.parent = p;
		
		cd = new ContinentData();
		countriesFull = cd.getFullCountriesArray();
		
		cp5 = new ControlP5(parent);
		//PFont pFont = parent.createFont("Arial", 10);
//		cp5.setFont(pFont);
		
		checkbox = cp5.addCheckBox("checkBox")
				.setPosition(x, y)
				.setColorForeground(parent.color(120))
				.setColorActive(parent.color(0))
				.setColorLabel(parent.color(0))
				.setSize(20, 20)
				.setItemsPerRow(3)
				.setSpacingColumn(85)
				.setSpacingRow(15)
				;
		
		
		countriesInDB = cd.findCountriesNameInDatabase();
		setCheckboxList(countriesInDB);
	}
	
	public void setCheckboxList(String[] cs) {
		for(int i = 0; i < cs.length; i++) {
			checkbox.addItem(cs[i], i);
		}
		System.out.println(cs.length + " countries added to checkbox");
	}
	
	public void draw() {
		parent.pushMatrix();
		parent.translate(parent.width/2 + 200, parent.height/2);
		parent.stroke(255);
		parent.strokeWeight(2);
		parent.popMatrix();
	}

}
