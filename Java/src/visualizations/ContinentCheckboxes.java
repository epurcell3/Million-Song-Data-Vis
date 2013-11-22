package visualizations;

import map_works.ContinentData;
import processing.core.PApplet;
import controlP5.CheckBox;
import controlP5.ControlEvent;
import controlP5.ControlP5;

public class ContinentCheckboxes {
	private PApplet parent;
	private ControlP5 cp5;
	private CheckBox checkbox;

	public ContinentCheckboxes(PApplet p) {
		this.parent = p;
		String[] continents = ContinentData.getContinents();

		cp5 = new ControlP5(parent);
		checkbox = cp5.addCheckBox("checkBox")
				.setPosition(700, 50)
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
	} // close constructor

	public void keyPressed() {
		if (parent.key==' ') {
			checkbox.deactivateAll();
		} 
		else {
			for (int i=0;i<6;i++) {
				// check if key 0-5 have been pressed and toggle
				// the checkbox item accordingly.
				if (parent.keyCode==(48 + i)) { 
					// the index of checkbox items start at 0
					checkbox.toggle(i);
					System.out.println("toggle "+checkbox.getItem(i).getLabel());
					// also see 
					// checkbox.activate(index);
					// checkbox.deactivate(index);
				}
			}
		}
	}

	public void draw() {
		parent.pushMatrix();
		parent.translate(parent.width/2 + 200, parent.height/2);
		parent.stroke(255);
		parent.strokeWeight(2);
		parent.popMatrix();
	}

	void controlEvent(ControlEvent theEvent) {
		if (theEvent.isFrom(checkbox)) {
			System.out.print("got an event from "+checkbox.getName()+"\t\n");
			// checkbox uses arrayValue to store the state of 
			// individual checkbox-items. usage:
			System.out.println(checkbox.getArrayValue());
			for (int i=0;i<checkbox.getArrayValue().length;i++) {
				int n = (int)checkbox.getArrayValue()[i];
				System.out.print(n);
				if(n==1) {
//					myColorBackground += checkbox.getItem(i).internalValue();
				}
			}
			System.out.println();    
		}
	}

	void checkBox(float[] a) {
		System.out.println(a);
	}
}
