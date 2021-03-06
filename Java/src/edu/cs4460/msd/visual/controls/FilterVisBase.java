package edu.cs4460.msd.visual.controls;

import java.util.Arrays;

import controlP5.Accordion;
import controlP5.Bang;
import controlP5.CheckBox;
import controlP5.ControlEvent;
import controlP5.ControlP5;
import controlP5.Group;
import controlP5.Range;
import controlP5.Slider;
import edu.cs4460.msd.backend.database.DatabaseConnectionInterface;
import edu.cs4460.msd.backend.maps_works.ContinentData;
import edu.cs4460.msd.backend.utilities.FontHelper;
import edu.cs4460.msd.visual.main.VisBase;

public class FilterVisBase {
	// ControlP5 elements
	private VisBase vb;
	private ControlP5 cp5;
	private FontHelper fh;
	private Accordion acc;
	private Group gYear, gContinent, gCountry, gSongs;
	private Range yearRange;
	private Slider songsSlider;
	private Bang songBang;
	private CheckBox continentCheckBox, countryCheckBox;
	private static final String YEAR_NAME = "Year Range";
	private static final String CONTINENT_NAME = "Continent Checkbox";
	private static final String COUNTRY_NAME = "Country Checkbox";
	private static final String SONG_NAME = "Song Range";
	private static final String SONG_BANG = "Song Bang";
	
	// Graphics Properties - Colors
	private int colorCheckBoxActive;
	private int colorCheckBoxForeground;
	private int colorCheckBoxLabel;
	
	// Graphics Properties - Sizes
	private int accordionBackgroundColor;
	private int yearBackgroundHeight = 50;
	private int continentBackgroundHeight = 225;
	private int countryBackgroundHeight = 575;
	private int songBackgroundHeight = 50;
	private int defaultX = 10, defaultY = 10, defaultWidth = 250;
	
	// Other
	private String[] countriesInCheckboxList, countriesInDB, continents;
	private int songsDefault = DatabaseConnectionInterface.START_QUERY_LIMIT;
	
	public FilterVisBase(VisBase vb, int x, int y, int width, int height) {
		this.vb = vb;
		cp5 = new ControlP5(vb);
		fh = new FontHelper(vb);
		cp5.setFont(fh.accordionHeadFont());
		continents = ContinentData.getContinents();
		
		// Color Setups
		accordionBackgroundColor = vb.color(0,64);
		colorCheckBoxActive = vb.color(0, 100, 0);
		colorCheckBoxForeground = vb.color(0,50, 0);
		colorCheckBoxLabel = vb.color(80, 80, 80);
		
		
		gYear = cp5.addGroup("Year", defaultX, defaultY, defaultWidth)
				.setBackgroundColor(accordionBackgroundColor)
				.setBackgroundHeight(yearBackgroundHeight)
				.setBarHeight(20)
				;
		createYearFilter();	
		
		gContinent = cp5.addGroup("Continent")
				.setBackgroundColor(accordionBackgroundColor)
				.setBackgroundHeight(continentBackgroundHeight)
				.setBarHeight(20)
				;
				
		createContinentFilter();
				
		gCountry = cp5.addGroup("Country")
				.setBackgroundColor(accordionBackgroundColor)
				.setBackgroundHeight(countryBackgroundHeight)
				.setBarHeight(20)
				;
		createCountryFilter();
		
		gSongs = cp5.addGroup("Songs")
				.setBackgroundColor(accordionBackgroundColor)
				.setBackgroundHeight(songBackgroundHeight)
				.setBarHeight(20)
				;
		createSongsFilter();
		
		songBang = cp5.addBang(SONG_BANG, defaultX + 300, defaultY)
				.setSize(40,40)
				.moveTo(gSongs)
				;
		songBang.setBroadcast(true)
				.getCaptionLabel().set("Update")
				;
		
		
		acc = cp5.addAccordion("acc")
                .setPosition(x,y)
                .setWidth(width)
                .addItem(gYear)
                .addItem(gContinent)
                .addItem(gCountry)
                .addItem(gSongs)
                ;
		acc.setCollapseMode(Accordion.MULTI);
	}
	
	public void draw() {
		
	}
	
	public void checkAllContinents()
	{
		continentCheckBox.activateAll();
	}
	
	public void controlEvent(ControlEvent theEvent) {
		if(theEvent.isFrom(yearRange)) {
			int rangeMin = (int) (theEvent.getController().getArrayValue(0));
			int rangeMax = (int) (theEvent.getController().getArrayValue(1));
			vb.filterYears(rangeMin, rangeMax);
		} else if(theEvent.isFrom(continentCheckBox)) {
			boolean[] checked = new boolean[continents.length];
			for(int i = 0; i < continentCheckBox.getArrayValue().length; i++) {
				int n = (int) continentCheckBox.getArrayValue()[i];
				checked[i] = (n==1);
			}
			vb.filterContinents(checked);
		} else if(theEvent.isFrom(countryCheckBox)) {
			boolean[] checked = new boolean[countriesInCheckboxList.length];
			for (int i=0;i<countryCheckBox.getArrayValue().length;i++) {
				int n = (int)countryCheckBox.getArrayValue()[i];
				checked[i] = (n==1);
			}
			vb.filterCountries(checked);
		} else if(theEvent.isFrom(songBang)) {
			int count = (int) songsSlider.getValue();
			vb.filterSongs(count);
		}
	}
	
	private void createYearFilter() {
		int yearX = defaultX, yearY = defaultX, yearWidth = 300, yearHeight = 25;
		int lowerYear = 1926, upperYear = 2010;
		int quarterYear = Math.round((upperYear - lowerYear) / 4);
		
		int yearRangeForeground = vb.color(0, 0, 255);
		int yearBackgroundColor = accordionBackgroundColor;
		
		yearRange = cp5.addRange(YEAR_NAME, lowerYear, upperYear, lowerYear + quarterYear,upperYear - quarterYear, yearX, yearY, yearWidth, yearHeight)
				.setHandleSize(10)
				// after the initialization we turn broadcast back on again
				.setBroadcast(true)
				.setDecimalPrecision(0)
				.setColorForeground(yearRangeForeground)
				.setColorBackground(yearBackgroundColor)
				.moveTo(gYear)
				;
		yearRange.getCaptionLabel().setFont(fh.accordionSubFont());
	}
	
	private void createContinentFilter() {
		int continentX = defaultX, continentY = defaultY;
		continentCheckBox = cp5.addCheckBox(CONTINENT_NAME, continentX, continentY)
				.setColorForeground(colorCheckBoxForeground)
				.setColorActive(colorCheckBoxActive)
				.setColorLabel(colorCheckBoxLabel)
				.setSize(20, 20)
				.setItemsPerRow(1)
				.setSpacingRow(7)
				.toUpperCase(false)
				.moveTo(gContinent)
				;
		continentCheckBox.getCaptionLabel().setFont(fh.accordionSubFont());
		
		Arrays.sort(continents);
		for(int i = 0; i<continents.length; i++) {
			continentCheckBox.addItem(continents[i], i);
			continentCheckBox.toUpperCase(false);
		}
	}
	
	private void createCountryFilter() {
		int countryX = defaultX, countryY = defaultY;
		
		countryCheckBox = cp5.addCheckBox(COUNTRY_NAME, countryX, countryY)
				.setColorForeground(colorCheckBoxForeground)
				.setColorActive(colorCheckBoxActive)
				.setColorLabel(colorCheckBoxLabel)
				.setSize(20, 20)
				.setItemsPerRow(3)
				.setSpacingColumn(95)
				.setSpacingRow(7)
				.toUpperCase(false)
				.moveTo(gCountry)
				;
		countryCheckBox.getCaptionLabel().setFont(fh.accordionSubFont());
			
		ContinentData cd = new ContinentData();
		countriesInDB = cd.findCountriesNameInDatabase();
		setCheckboxList(countriesInDB);
	}
	
	private void createSongsFilter() {
		int songsX = defaultX, songsY = defaultY, songsHeight = 25;
		int upperBound = 137705;
		
		songsSlider = cp5.addSlider(SONG_NAME, 0, upperBound, songsDefault, songsX, songsY, defaultWidth - 75, songsHeight)
				.setDecimalPrecision(0)
				.moveTo(gSongs)
				;
		songsSlider.getCaptionLabel().setFont(fh.accordionSubFont());
	}
	
	private void setCheckboxList(String[] cs) {
		this.countriesInCheckboxList = cs;
		//Arrays.sort(cs);
		//System.out.println(Arrays.toString(cs));
		for(int i = 0; i < cs.length; i++) {
			countryCheckBox.addItem(cs[i], i);
			countryCheckBox.toUpperCase(false);
		}
	}

	/**
	 * @return the countriesInCheckboxList
	 */
	public String[] getCountriesInCheckboxList() {
		return countriesInCheckboxList;
	}

	/**
	 * @param countriesInCheckboxList the countriesInCheckboxList to set
	 */
	public void setCountriesInCheckboxList(String[] countriesInCheckboxList) {
		this.countriesInCheckboxList = countriesInCheckboxList;
	}

	/**
	 * @return the countriesInDB
	 */
	public String[] getCountriesInDB() {
		return countriesInDB;
	}

	/**
	 * @param countriesInDB the countriesInDB to set
	 */
	public void setCountriesInDB(String[] countriesInDB) {
		this.countriesInDB = countriesInDB;
	}

	/**
	 * @return the continents
	 */
	public String[] getContinents() {
		return continents;
	}

	/**
	 * @param continents the continents to set
	 */
	public void setContinents(String[] continents) {
		this.continents = continents;
	}

}
