package sepm.ss13.e1058208.test;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.*;
import sepm.ss13.e1058208.dao.ConnectionSingleton;

public class TestConnectionSingleton {
	
	//private static final Logger log = Logger.getLogger(TestConnectionSingleton.class);
	private static Connection c;
	
	@BeforeClass
	public static void refConnection() {
		c = ConnectionSingleton.getInstance();
	}
	
	@Test
	public void compareConnections() throws Exception {
		Connection d = ConnectionSingleton.getInstance();
		assertNotNull("Reference connection must not be null", c);
		assertNotNull("Test connection must not be null", d);
		assertSame("Connections must be identical", d, c);
	}
}
