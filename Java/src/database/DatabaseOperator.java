package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseOperator {
	private static Connection con;
	public static String dbFilepath = System.getProperty("user.dir") + "/db/subset_track_metadata.db";

	public DatabaseOperator() {
		con = getNewConnection();
		
	}

	protected Statement createStatement() {
		Statement out = null;

		try {
			if (con.isClosed()) {
				con = getNewConnection();
			}

			out = con.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return out;
	}

	public void closeConnection() {
		try {
			if (con != null)
				con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	protected int executeSQLUpdate(String command) {
		int out = -1;
		if (command != null && !command.isEmpty()) {
			try {
				out = createStatement().executeUpdate(command);
				//closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return out;
	}

	protected ResultSet executeSQLQuery(String command) {
		ResultSet out = null;

		if (command != null && !command.isEmpty()) {
			try {
				out = createStatement().executeQuery(command);
				// closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			} // close catch
		} // close null if
		return out;
	} // close executeSQLQuery method

	protected boolean executeSQLCreate(String command) {
		boolean out = false;
		if (command != null && !command.isEmpty()) {
			try {
				out = createStatement().execute(command);
				//closeConnection();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
		return out;
	}
	
	/**
	 * Creates and returns a new connection to the database
	 * @return A new connection to the database
	 */
	public Connection getNewConnection() {
		Connection conOut = null;
		
		try {
			Class.forName("org.sqlite.JDBC").newInstance();
			
			conOut = DriverManager
					.getConnection("jdbc:sqlite:" + dbFilepath);
			
			if (!conOut.isClosed()) {
				//System.out.println("Successfully connected to "
				//		+ "MySQL server using TCP/IP...");
			}
		} catch (Exception e) {
			System.err.println("Exception: " + e.getMessage());
		} finally {
			
		} // close finally
		return conOut;
	}

	protected char convertBool(boolean in) {
		if (in) {
			return 'Y';
		} else {
			return 'N';
		}
	}
	
	/**
	 * Returns true if the string is initialized (not null), contains
	 * characters, and is one word
	 * 
	 * @param in
	 *            String to check
	 * @return true if the string is initialized (not null), contains
	 *         characters, and is one word. returns false otherwise
	 */
	protected boolean checkString(String in) {
		boolean out = false;

		if (in != null && !in.isEmpty()) {
			if (!in.contains(" ")) {
				out = true;
			}
		}
		return out;
	}

	protected boolean checkString2(String in) {
		boolean out = false;

		if (in != null && !in.isEmpty()) {
			out = true;
		}
		return out;
	}
} // close IPresenter