package visualizations;

import java.util.Arrays;

import map_works.ContinentData;
import processing.core.PApplet;
import controlP5.CheckBox;
import controlP5.ControlEvent;
import controlP5.ControlP5;

public class ContinentCheckboxes {
	private PApplet parent;
	private ControlP5 cp5;
	private CheckBox checkbox;
	public static int DEFAULT_WIDTH = 100, DEFAULT_HEIGHT = 300;

	public ContinentCheckboxes(PApplet p, int x, int y) {
		this(p, x, y, DEFAULT_WIDTH, DEFAULT_HEIGHT);
	} // close constructor
	
	public ContinentCheckboxes(PApplet p, int x, int y, int width, int height) {
		this.parent = p;
		String[] continents = ContinentData.getContinents();

		cp5 = new ControlP5(parent);
		//PFont pFont = parent.createFont("Arial", 10);
//		cp5.setFont(pFont);
		
		checkbox = cp5.addCheckBox("checkBox")
				.setPosition(x, y)
				.setColorForeground(parent.color(120))
				.setColorActive(parent.color(0))
				.setColorLabel(parent.color(0))
				.setSize(40, 40)
				.setItemsPerRow(1)
				.setSpacingColumn(30)
				.setSpacingRow(20)
				.addItem(continents[0], 0)
				.addItem(continents[1], 50)
				.addItem(continents[2], 100)
				.addItem(continents[3], 150)
				.addItem(continents[4], 200)
				.addItem(continents[5], 255)
				;
	}

	public void draw() {
		parent.pushMatrix();
		parent.translate(parent.width/2 + 200, parent.height/2);
		parent.stroke(255);
		parent.strokeWeight(2);
		parent.popMatrix();
	}

	public void controlEvent(ControlEvent theEvent) {
		if (theEvent.isFrom(checkbox)) {
			boolean[] checked = new boolean[ContinentData.getContinents().length];
			for (int i=0;i<checkbox.getArrayValue().length;i++) {
				int n = (int)checkbox.getArrayValue()[i];
				checked[i] = (n==1);
			}
			//System.out.println(Arrays.toString(checked));    
		}
	}
}
