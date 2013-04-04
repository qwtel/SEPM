package sepm.ss13.e1058208.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.*;
import sepm.ss13.e1058208.dao.ConnectionSingleton;
import sepm.ss13.e1058208.dao.DBPferdDAO;
import sepm.ss13.e1058208.dao.PferdDAO;
import sepm.ss13.e1058208.entities.Pferd;
import sepm.ss13.e1058208.entities.Therapieart;

public class TestDBPferdDAO {
	
	private static final Logger log = Logger.getLogger(TestDBPferdDAO.class);
	
	private Connection c;
	private PferdDAO dao;
		
	@Before	
	public void setup() throws SQLException {
		c = ConnectionSingleton.getInstance();
		c.setAutoCommit(false);
		log.debug("before" + c);
		dao = new DBPferdDAO(c);
	}
			
	@After
	public void rollback() throws SQLException {
		log.debug("rollback?" + c);
		c.rollback();
	}
		
	@Test
	public void read() {
		Pferd p = dao.read(0);
		assertNotNull(p);
		assertEquals("Wendy", p.getName());
	}
	
	@Test
	public void readAll() {
		Collection<Pferd> res = dao.readAll();
		assertEquals(4, res.size());
		Pferd wendy = dao.read(0);
		assertTrue(res.contains(wendy));
	}
	
	@Test
	public void write() {
		Pferd expected = new Pferd(0, "Bobby", 9.95f, Therapieart.HPV, new Date(System.currentTimeMillis()));
		dao.create(expected);
		Pferd actual = dao.read(expected.getId());
		assertEquals(expected.getId(), actual.getId());
		assertNotEquals(0, actual.getId());
	}
}

