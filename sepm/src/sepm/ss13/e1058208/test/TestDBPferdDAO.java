package sepm.ss13.e1058208.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.junit.*;
import sepm.ss13.e1058208.dao.ConnectionSingleton;
import sepm.ss13.e1058208.dao.DAOException;
import sepm.ss13.e1058208.dao.DBPferdDAO;
import sepm.ss13.e1058208.dao.PferdDAO;
import sepm.ss13.e1058208.entities.Pferd;
import sepm.ss13.e1058208.entities.Therapieart;

public class TestDBPferdDAO {
	private static final Logger log = Logger.getLogger(TestDBPferdDAO.class);
	
	private Connection c;
	private PferdDAO dao;
		
	@Before	
	public void setup() throws SQLException, DAOException {
		c = ConnectionSingleton.getInstance();
		c.setAutoCommit(false);
		log.debug("before" + c);
		dao = new DBPferdDAO(c);
		
		Testdata.generate(c);
	}
			
	@After
	public void rollback() throws SQLException {
		log.debug("rollback?" + c);
		c.rollback();
	}
		
	@Test
	public void read() throws DAOException {
		Pferd p = dao.read(0);
		assertNotNull(p);
		assertEquals(0, p.getId());
		assertEquals("Wendy", p.getName());
		assertEquals(25.95f, p.getPreis(), 0.01f);
		assertEquals(Therapieart.HPR, p.getTyp());
		assertEquals("2008-11-11", p.getDat().toString());
	}
	
	@Test(expected = DAOException.class)
	public void readNegative() throws DAOException {
		dao.read(-1);
	}
	
	@Test
	public void readAll10() throws DAOException {
		Collection<Pferd> res = dao.readAll();
		assertEquals(10, res.size());
	}
	
	@Test
	public void readAllContainsWendy() throws DAOException {
		Collection<Pferd> res = dao.readAll();
		Pferd wendy = dao.read(0);
		assertTrue(res.contains(wendy));
	}
	
	@Test
	public void write() throws DAOException {
		Pferd expected = new Pferd(-1, "Bobby", 9.95f, Therapieart.HPV, new Date(System.currentTimeMillis()));
		dao.create(expected);
		Pferd actual = dao.read(expected.getId());
		assertEquals(expected, actual);
	}
	
	@Test
	public void writeIgnoresId() throws DAOException {
		Pferd expected = new Pferd(10, "Bobby", 9.95f, Therapieart.HPV, new Date(System.currentTimeMillis()));
		dao.create(expected);
		Pferd actual = dao.read(expected.getId());
		assertEquals(expected, actual);
	}
	
	@Test
	public void update() throws DAOException {
		Pferd expected = new Pferd(0, "Daddy", 9.95f, Therapieart.HPV, new Date(System.currentTimeMillis()));
		dao.update(expected);
		Pferd actual = dao.read(expected.getId());
		assertEquals(expected, actual);
	}
	
	@Test(expected = DAOException.class)
	public void updateNonExistant() throws DAOException {
		Pferd expected = new Pferd(99, "Bobby", 9.95f, Therapieart.HPV, new Date(System.currentTimeMillis()));
		dao.update(expected);
	}
	
	@Test
	public void delete() throws DAOException {
		Pferd wendy = dao.read(0);
		dao.delete(wendy);
		Collection<Pferd> res = dao.readAll();
		assertFalse(res.contains(wendy));
	}
	
	@Test(expected = DAOException.class)
	public void deleteNonExistant() throws DAOException {
		Pferd wendy = dao.read(-1);
		dao.delete(wendy);
	}
}

