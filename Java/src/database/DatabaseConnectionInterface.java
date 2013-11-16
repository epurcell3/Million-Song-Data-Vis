package database;

public abstract class DatabaseConnectionInterface {
	protected String dbFilepath;
	public static String QUERY_LIMIT = "";
	protected DatabaseOperator dbOp;
	
	
	public void closeConnection() {
		dbOp.closeConnection();
	}

}
