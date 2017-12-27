package database;

public class DatabaseConnection {
	private static DatabaseConnection connection=null;
	
	public DatabaseConnection() {}
	
	public static DatabaseConnection getDatabaseInstance() {
		if (connection==null) {
			connection=new DatabaseConnection();
		}
		return connection;
	}
	
}
