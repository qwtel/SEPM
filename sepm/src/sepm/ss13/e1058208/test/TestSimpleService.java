package sepm.ss13.e1058208.test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Collection;

//import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import sepm.ss13.e1058208.dao.ConnectionSingleton;
import sepm.ss13.e1058208.entities.Pferd;
import sepm.ss13.e1058208.entities.Therapieart;
import sepm.ss13.e1058208.service.Service;
import sepm.ss13.e1058208.service.ServiceException;
import sepm.ss13.e1058208.service.SimpleService;

public class TestSimpleService {
	//private static final Logger log = Logger.getLogger(TestSimpleService.class);
	
	private Connection c;
	private Service s;
		
	@Before	
	public void setup() throws SQLException, ServiceException {
		c = ConnectionSingleton.getInstance();
		c.setAutoCommit(false);
		s = new SimpleService();
	}
			
	@After
	public void rollback() throws SQLException {
		c.rollback();
	}
	
	@Test
	public void listPferds() throws ServiceException {
		Collection<Pferd> pferds = s.listPferds();
		assertEquals(4, pferds.size());
	}
	
	@Test
	public void defaultSearchPferdsEqualsListPferds() throws ServiceException {
		Collection<Pferd> pferds = s.listPferds();
		Collection<Pferd> search = s.searchPferds(null, 0.0f, null);
		assertEquals(pferds, search);
		Collection<Pferd> search2 = s.searchPferds("Wen", 0.0f, null);
		assertNotEquals(pferds, search2);
	}
	
	@Test
	public void searchForWendy() throws ServiceException {
		Collection<Pferd> pferds = s.listPferds();
		Pferd wendy = (Pferd)pferds.toArray()[0];
		
		Collection<Pferd> search = s.searchPferds("Wen", 0.0f, null);
		assertTrue(search.contains(wendy));
		
		Collection<Pferd> search1 = s.searchPferds("Wend", 0.0f, null);
		assertTrue(search1.contains(wendy));
		
		Collection<Pferd> search2 = s.searchPferds("Wendy", 0.0f, null);
		assertTrue(search2.contains(wendy));
		
		Collection<Pferd> search3 = s.searchPferds(null, 0.0f, Therapieart.HPR);
		assertTrue(search3.contains(wendy));
		
		Collection<Pferd> search4 = s.searchPferds(null, 10.0f, null);
		assertFalse(search4.contains(wendy));
		
		Collection<Pferd> search5 = s.searchPferds("Horst", 0.0f, null);
		assertFalse(search5.contains(wendy));
		
		Collection<Pferd> search6 = s.searchPferds("y", 50.0f, Therapieart.HPR);
		assertTrue(search6.contains(wendy));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createNullPferd() throws IllegalArgumentException, ServiceException {
		Pferd p = null;
		s.createPferd(p);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createPferdWithTooLongName() throws IllegalArgumentException, ServiceException {
		Pferd p = new Pferd();
		p.setName("123456789012345678901234567890toolong");
		s.createPferd(p);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void createPferdFromTheFuture() throws IllegalArgumentException, ServiceException {
		Pferd p = new Pferd();
		p.setName("Test");
		p.setDat(new Date(System.currentTimeMillis() + 1000));
		s.createPferd(p);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void changeNonExistantPferds() throws IllegalArgumentException, ServiceException {
		Pferd wendy = new Pferd();
		s.editPferd(wendy);
	}
	
	@Test
	public void inflation() throws ServiceException {
		s.increaseTop3PferdsBy5Percent();
	}
}
