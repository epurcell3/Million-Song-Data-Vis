package edu.cs4460.msd.backend.database;

public abstract class DatabaseConnectionInterface {
	protected String dbFilepath;
	public static final int START_QUERY_LIMIT = 20000;
	protected String QueryLimit = " LIMIT " + START_QUERY_LIMIT;
	protected DatabaseOperator dbOp;
	
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
	
	public void setQueryLimit(int limit) {
		QueryLimit = " LIMIT " + limit;
	}
	
	
	public void closeConnection() {
		dbOp.closeConnection();
	}

}
