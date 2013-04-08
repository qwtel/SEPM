package sepm.ss13.e1058208.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

//import org.apache.log4j.Logger;
import org.junit.*;
import sepm.ss13.e1058208.dao.ConnectionSingleton;
import sepm.ss13.e1058208.dao.DAOException;
import sepm.ss13.e1058208.dao.DBPferdDAO;
import sepm.ss13.e1058208.dao.DBRechnungDAO;
import sepm.ss13.e1058208.dao.PferdDAO;
import sepm.ss13.e1058208.dao.RechnungDAO;
import sepm.ss13.e1058208.entities.Pferd;
import sepm.ss13.e1058208.entities.Rechnung;
import sepm.ss13.e1058208.entities.Therapieeinheit;

public class TestDBRechnungDAO {
	//private static final Logger log = Logger.getLogger(TestDBRechnungDAO.class);
	
	private Connection c;
	private RechnungDAO dao;
	private PferdDAO pdao;
		
	@Before	
	public void setup() throws SQLException, DAOException {
		c = ConnectionSingleton.getInstance();
		c.setAutoCommit(false);
		dao = new DBRechnungDAO(c);
		pdao = new DBPferdDAO(c);
		
		Testdata.generate(c);
	}
			
	@After
	public void rollback() throws SQLException {
		c.rollback();
	}
	
	@Test(expected = DAOException.class)
	public void readEmptyThrowsException() throws DAOException {
		dao.read(10);
	}
	
	@Test
	public void create() throws DAOException {
		Rechnung r = new Rechnung();
		long time = System.currentTimeMillis();
		r.setDat(new Date(time));
		
		Pferd p0 = pdao.read(0);
		Pferd p1 = pdao.read(1);
		
		HashMap<Pferd, Therapieeinheit> einheiten = new HashMap<Pferd, Therapieeinheit>();
		
		Therapieeinheit t = new Therapieeinheit();
		t.setPreis(p0.getPreis());
		t.setStunden(3);
		einheiten.put(p0, t);
		
		t = new Therapieeinheit();
		t.setPreis(p1.getPreis());
		t.setStunden(2);
		einheiten.put(p1, t);
		
		r.setEinheiten(einheiten);
		
		dao.create(r);
		
		Rechnung actual = dao.read(r.getId());
		
		assertNotNull(r);
		assertEquals(r, actual);
	}
		
	@Test
	public void read() throws DAOException {
		Rechnung r = dao.read(0);
		assertNotNull(r);
		assertEquals("2008-11-11", r.getDat().toString());
	}
	
	@Test
	public void readAll() throws DAOException {
		ArrayList<Rechnung> res = new ArrayList<Rechnung>(dao.readAll());
		assertEquals(10, res.size());
		Rechnung r = res.get(0);
		assertEquals("2008-11-11", r.getDat().toString());
		for(Therapieeinheit t : r.getEinheiten().values()) {
			//log.info(t);
			assertNotNull(t.getId());
			assertNotNull(t.getPreis());
			assertNotNull(t.getStunden());
		}
	}
}
