package database;

public abstract class DatabaseConnectionInterface {
	protected String dbFilepath;
	protected String QueryLimit = "";
	/**
	 * @return the queryLimit
	 */
	public String getQueryLimit() {
		return QueryLimit;
	}


	/**
	 * @param queryLimit the queryLimit to set
	 */
	public void setQueryLimit(String queryLimit) {
		QueryLimit = queryLimit;
	}


	protected DatabaseOperator dbOp;
	
	
	public void closeConnection() {
		dbOp.closeConnection();
	}

}
