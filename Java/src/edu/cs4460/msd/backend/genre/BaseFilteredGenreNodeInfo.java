package edu.cs4460.msd.backend.genre;

public class BaseFilteredGenreNodeInfo extends GenreNodeInfo {

	private GenreFilter baseFilter;
	
	public BaseFilteredGenreNodeInfo(GenreFilter baseFilter)
	{
		this.baseFilter = baseFilter;
		this.filter = this.baseFilter;
	}
	
	@Override
	public void setFilter(GenreFilter filter)
	{
		GenreFilter newFilter = new GenreFilter();
		//Set the continent filter to base filter
		newFilter.setContinentsFiltered(true);
		newFilter.setContinents(baseFilter.getContinents());
		//Set the rest of the filters
		newFilter.setCountriesFiltered(filter.isCountriesFiltered());
		newFilter.setCountries(filter.getCountries());
		newFilter.setMaxYear(filter.getMaxYear());
		newFilter.setMinYear(filter.getMinYear());
		newFilter.setYearsFiltered(filter.isYearsFiltered());
		
		this.filter = newFilter;
	}
}
