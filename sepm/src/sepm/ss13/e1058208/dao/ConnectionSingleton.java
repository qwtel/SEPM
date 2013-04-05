package sepm.ss13.e1058208.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Erlaubt den Zugriff auf die Datenbankverbindung.
 * @author Florian Klampfer
 */
public class ConnectionSingleton {
	
	private static final Logger log = Logger.getLogger(ConnectionSingleton.class);
	private Connection connection;
	
	// Private constructor suppresses generation of a (public) default constructor
	private ConnectionSingleton() {
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
		} catch(Exception e) {
			log.error("jbdcdriver " + e);
			throw new RuntimeException();
		}
		
		try {
			connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
		} catch(SQLException e) {
			log.error("connection " + e);
			throw new RuntimeException();
		}
	}
 
	private static class ConnectionSingletonHolder {
		private static ConnectionSingleton instance = new ConnectionSingleton();
	} 
 
	/**
	 * Liefert die Verbindung zur Datenbank.
	 * @return Die einzige aktive Connection der Datenbank.
	 */
	public static Connection getInstance() {
		return ConnectionSingletonHolder.instance.connection;
	}
}
