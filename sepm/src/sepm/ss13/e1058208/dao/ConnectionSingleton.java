package sepm.ss13.e1058208.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {
	
	private Connection connection;
	
   // Private constructor suppresses generation of a (public) default constructor
   private ConnectionSingleton() {
	   try {
		   Class.forName("org.hsqldb.jdbc.JDBCDriver");
	   } catch(Exception e) {
		   return;
	   }
	   
	   try {
		   connection = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/xdb", "SA", "");
	   } catch(SQLException e) {
		   return;
	   }
   }
 
   private static class ConnectionSingletonHolder {
	   private static ConnectionSingleton instance = new ConnectionSingleton();
   } 
 
   public static Connection getInstance() {
	   return ConnectionSingletonHolder.instance.connection;
   }
}
