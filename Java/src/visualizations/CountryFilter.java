package visualizations;

import java.util.Arrays;

import map_works.ContinentData;
import processing.core.PApplet;
import controlP5.CheckBox;
import controlP5.ControlEvent;
import controlP5.ControlP5;

public class CountryFilter {
	private PApplet parent;
	private String[] countriesInDB;
	private String[] countriesInCheckboxList;
	private static final String CHECKBOX_NAME = "checkBox";
	private ControlP5 cp5;
	private CheckBox checkbox;
	private ContinentData cd;
	public static int DEFAULT_WIDTH = 100, DEFAULT_HEIGHT = 300;
	private int x, y;
	
	public CountryFilter(PApplet p, int x, int y) {
		this.parent = p;
		this.x = x;
		this.y = y;
		
		cd = new ContinentData();
		
		cp5 = new ControlP5(parent);
		//PFont pFont = parent.createFont("Arial", 10);
//		cp5.setFont(pFont);		
		
		countriesInDB = cd.findCountriesNameInDatabase();
		setCheckboxList(countriesInDB);
	}
	
	private void createCheckbox() {
		if(checkbox != null) {
			//checkbox.deactivateAll();
			System.out.println("checkbox.getName(): " + checkbox.getName());
			cp5.remove(CHECKBOX_NAME);
			cp5.update();
		}
		checkbox = cp5.addCheckBox(CHECKBOX_NAME)
				.setPosition(x, y)
				.setColorForeground(parent.color(120))
				.setColorActive(parent.color(0))
				.setColorLabel(parent.color(0))
				.setSize(20, 20)
				.setItemsPerRow(3)
				.setSpacingColumn(85)
				.setSpacingRow(7)
				;
		
	}
	
	public void setCheckboxList(String[] cs) {
		this.countriesInCheckboxList = cs;
		createCheckbox();
		Arrays.sort(cs);
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
	
	private void printCountriesChecked(boolean[] checks) {
		if(checks.length == countriesInCheckboxList.length) {
			String print = "";
			for(int i = 0; i < checks.length; i++) {
				if(checks[i]) {
					print += countriesInCheckboxList[i] + "\t";
					
//					if(countriesInCheckboxList[i].equals("GERMANY")) {
//						setCheckboxList(countriesFull);
//					}
				}
			}
			System.out.println(print);
		} else {
			System.err.println("Array dimensions don't match!");
		}
		
	}
	
	public void controlEvent(ControlEvent theEvent) {
		if (theEvent.isFrom(checkbox)) {
			boolean[] checked = new boolean[countriesInCheckboxList.length];
			for (int i=0;i<checkbox.getArrayValue().length;i++) {
				int n = (int)checkbox.getArrayValue()[i];
				checked[i] = (n==1);
			}
			printCountriesChecked(checked);
			//System.out.println(Arrays.toString(checked));    
		}
	}

}
