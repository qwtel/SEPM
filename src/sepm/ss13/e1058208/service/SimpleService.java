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
 * Stellt Methoden für die primären Anwendungsfälle zur Verfügung.
 * 
 * @author Florian Klampfer
 */
public class SimpleService implements Service {
	
	private Connection c;
	private PferdDAO pdao;
	private RechnungDAO rdao;
	
	/**
	 * Erzeugt einen Service welcher die Methoden für die Anwendungsfälle zur Verfügung stellt.
	 * @throws ServiceException wenn ein Problem auftritt.
	 */
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
	 * Erstellt ein neues Pferd
	 * 
	 * @param p Das zu erstellende Pferd
	 * @throws IllegalArgumentException wenn die Daten ungültig sind.
	 * @throws ServiceException 
	 */
	@Override
	public void createPferd(Pferd p) throws IllegalArgumentException, ServiceException {
		validatePferd(p);
		try {
			pdao.create(p);
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}

	/**
	 * Bearbeitet ein vorhandenes Pferd.
	 * 
	 * @param p Die neuen Daten des Pferds.
	 * @throws IllegalArgumentException wenn die Daten ungültig sind, oder das Pferd nicht vorhandne ist.
	 * @throws ServiceException 
	 */
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

	/**
	 * Markiert ein Pferd als gelöscht.
	 * 
	 * @param p Das zu löschende Pferd
	 * @throws IllegalArgumentException wenn das Pferd nicht vorhandne ist.
	 * @throws ServiceException 
	 */
	@Override
	public void deletePferd(Pferd p) throws IllegalArgumentException, ServiceException {
		if(p.getId() == -1) throw new IllegalArgumentException("Horse id must be specified");
		try {
			pdao.delete(p);
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}

	/**
	 * Auflistung aller Pferde.
	 * 
	 * @return collection aller Pferde welche nicht gelöscht wurden.
	 * @throws ServiceException 
	 */
	@Override
	public Collection<Pferd> listPferds() throws ServiceException {
		try {
			return pdao.readAll();
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}

	/**
	 * Listet alle Pferde welche Bedingungen erfüllen.
	 * searchPferds mit default-Werten (null, 0.0f, null) ist identisch mit listPfers (wenn auch nicht performanter).
	 * 
	 * @param nameQuery Name oder teilweiser Name des gesuchten Pferds oder null wenn alle Namen gesucht sind.
	 * @param maxPreis Maximaler Preis pro Therapieeinheit oder 0.0f wenn alle Preise gesucht sind.
	 * @param typQuery Gewünschte Therapieart oder null wenn alle Therapiearten gesucht sind.
	 * @return collection aller Pferde welche die Query matchen.
	 * @throws ServiceException 
	 */
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
	 * Erstellt eine Rechnung.
	 * 
	 * @param r Eine neue Rechnung welche in die Datenbank eingefügt werden soll.
	 * @throws IllegalArgumentException wenn die Daten ungültig sind.
	 * @throws ServiceException 
	 */
	@Override
	public void createRechnung(Rechnung r) throws IllegalArgumentException, ServiceException {
		validateRechnung(r);
		try {
			rdao.create(r);
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}

	/**
	 * Auflistung aller Rechnungen.
	 * 
	 * @return collection aller Rechnungen.
	 * @throws ServiceException 
	 */
	@Override
	public Collection<Rechnung> listRechnungs() throws ServiceException {
		try {
			return rdao.readAll();
		} catch (DAOException e) {
			throw new ServiceException();
		}
	}

	/**
	 * Erhöht den Preis der drei beliebtesten Pferde um fünf Prozent.
	 * @throws ServiceException 
	 */
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
	
	/**
	 * Validiert ein Pferd.
	 * 
	 * @param p Das zu validierende Pferd
	 * @throws IllegalArgumentException wenn das Pferd ungültig ist.
	 */
	private static void validatePferd(Pferd p) throws IllegalArgumentException {
		if(p == null) throw new IllegalArgumentException("Horse must not be null");
		if(p.getName() == null) throw new IllegalArgumentException("Name must not be null");
		if(p.getDat() == null) throw new IllegalArgumentException("Date must not be null");
		if(p.getTyp() == null) throw new IllegalArgumentException("Type must not be null");
		if(p.getPreis() < 0.0f) throw new IllegalArgumentException("Preis darf nicht negativ sein");
		if(p.getName().length() > 30) throw new IllegalArgumentException("Name is too long");
		if(p.getDat().getTime() > System.currentTimeMillis()) throw new IllegalArgumentException("No horses from the future allowed");
	}

	
	/**
	 * Validiert eine Rechnung.
	 * 
	 * @param r Zu validierende Rechnung.
	 * @throws IllegalArgumentException Wenn die Rechnung ungültig ist.
	 */
	private static void validateRechnung(Rechnung r) throws IllegalArgumentException {
		if(r == null) throw new IllegalArgumentException("Rechnung must not be null");
		if(r.getDat().getTime() > System.currentTimeMillis()) throw new IllegalArgumentException("No invoices from the future allowed");
		
		for(Pferd p : r.getEinheiten().keySet()) {
			Therapieeinheit t = r.getEinheiten().get(p);
			if(t == null) throw new IllegalArgumentException("Invoice must not be null");
		}
	}
}
