package visualizations;

public class ToolTipResult {
	private String genreName;
	private int childrenCount;
	
	public ToolTipResult(String genreName, int cc) {
		this.genreName = genreName;
		this.childrenCount = cc;
	}

	/**
	 * @return the genreName
	 */
	public String getGenreName() {
		return genreName;
	}

	/**
	 * @param genreName the genreName to set
	 */
	public void setGenreName(String genreName) {
		this.genreName = genreName;
	}

	/**
	 * @return the childrenCount
	 */
	public int getChildrenCount() {
		return childrenCount;
	}

	/**
	 * @param childrenCount the childrenCount to set
	 */
	public void setChildrenCount(int childrenCount) {
		this.childrenCount = childrenCount;
	}

}
