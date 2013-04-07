package sepm.ss13.e1058208.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;

import sepm.ss13.e1058208.dao.ConnectionSingleton;
import sepm.ss13.e1058208.dao.DAOException;
import sepm.ss13.e1058208.dao.DBPferdDAO;
import sepm.ss13.e1058208.dao.DBRechnungDAO;
import sepm.ss13.e1058208.dao.PferdDAO;
import sepm.ss13.e1058208.dao.RechnungDAO;
import sepm.ss13.e1058208.entities.Pferd;
import sepm.ss13.e1058208.entities.Rechnung;
import sepm.ss13.e1058208.entities.Therapieart;
import sepm.ss13.e1058208.entities.Therapieeinheit;

/**
 * 
 * @author Florian Klampfer
 */
public class SimpleService implements Service {
	
	private Connection c;
	private PferdDAO pdao;
	private RechnungDAO rdao;
	
	public SimpleService() throws ServiceException {
		c = ConnectionSingleton.getInstance();
		try {
			pdao = new DBPferdDAO(c);
			rdao = new DBRechnungDAO(c);
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}
	
	/**
	 * Validiert ein Pferd.
	 * 
	 * @param p Das zu validierende Pferd
	 * @throws IllegalArgumentException wenn das Pferd ungültig ist.
	 */
	public static void validatePferd(Pferd p) throws IllegalArgumentException {
		if(p == null) throw new IllegalArgumentException("Horse must not be null");
		if(p.getName() == null) throw new IllegalArgumentException("Name must not be null");
		if(p.getDat() == null) throw new IllegalArgumentException("Date must not be null");
		if(p.getTyp() == null) throw new IllegalArgumentException("Type must not be null");
		if(p.getPreis() < 0.0f) throw new IllegalArgumentException("Preis darf nicht negativ sein");
		if(p.getName().length() > 30) throw new IllegalArgumentException("Name is too long");
		if(p.getDat().getTime() > System.currentTimeMillis()) throw new IllegalArgumentException("No horses from the future allowed");
	}

	@Override
	public void createPferd(Pferd p) throws IllegalArgumentException, ServiceException {
		validatePferd(p);
		try {
			pdao.create(p);
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}

	@Override
	public void editPferd(Pferd p) throws IllegalArgumentException, ServiceException {
		validatePferd(p);
		if(p.getId() == -1) throw new IllegalArgumentException("Horse id must be specified");
		try {
			pdao.update(p);
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}

	@Override
	public void deletePferd(Pferd p) throws IllegalArgumentException, ServiceException {
		if(p.getId() == -1) throw new IllegalArgumentException("Horse id must be specified");
		try {
			pdao.delete(p);
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}

	@Override
	public Collection<Pferd> listPferds() throws ServiceException {
		try {
			return pdao.readAll();
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}

	@Override
	public Collection<Pferd> searchPferds(String nameQuery, float maxPreis, Therapieart typQuery) throws ServiceException {
		Collection<Pferd> pferds = listPferds();
		Collection<Pferd> res = new ArrayList<Pferd>(pferds);
		
		for(Pferd p : pferds) {
			if(nameQuery != null) {
				if(!p.getName().contains(nameQuery)) {
					res.remove(p);
				}
			}
			if(maxPreis != .0f) {
				if(p.getPreis() > maxPreis) {
					res.remove(p);
				}
			}
			if(typQuery != null) {
				if(p.getTyp() != typQuery) {
					res.remove(p);
				}
			}
		}
		
		return res;
	}
	
	/**
	 * Validiert eine Rechnung.
	 * 
	 * @param r Zu validierende Rechnung.
	 * @throws IllegalArgumentException Wenn die Rechnung ungültig ist.
	 */
	public static void validateRechnung(Rechnung r) throws IllegalArgumentException {
		if(r == null) throw new IllegalArgumentException("Rechnung must not be null");
		if(r.getDat().getTime() > System.currentTimeMillis()) throw new IllegalArgumentException("No invoices from the future allowed");
		
		for(Pferd p : r.getEinheiten().keySet()) {
			Therapieeinheit t = r.getEinheiten().get(p);
			if(t == null) throw new IllegalArgumentException("Invoice must not be null");
		}
	}
	
	@Override
	public void createRechnung(Rechnung r) throws IllegalArgumentException, ServiceException {
		validateRechnung(r);
		try {
			rdao.create(r);
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}

	@Override
	public Collection<Rechnung> listRechnungs() throws ServiceException {
		try {
			return rdao.readAll();
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}

	@Override
	public void increaseTop3PferdsBy5Percent() throws ServiceException {
		try {
			ArrayList<Pferd> top = new ArrayList<Pferd>(pdao.readTop3());
			for(Pferd p : top) {
				p.setPreis(p.getPreis()*1.05f);
				pdao.update(p);
			}
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}

}
