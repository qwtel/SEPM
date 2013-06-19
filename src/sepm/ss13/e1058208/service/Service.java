package sepm.ss13.e1058208.service;

import java.util.Collection;

import sepm.ss13.e1058208.entities.Pferd;
import sepm.ss13.e1058208.entities.Rechnung;
import sepm.ss13.e1058208.entities.Therapieart;

/**
 * Stellt Methoden für die primären Anwendungsfälle zur Verfügung.
 * 
 * @author Florian Klampfer
 */
public interface Service {
	
	/**
	 * Erstellt ein neues Pferd
	 * 
	 * @param p Das zu erstellende Pferd
	 * @throws IllegalArgumentException wenn die Daten ungültig sind.
	 * @throws ServiceException 
	 */
	public void createPferd(Pferd p) throws IllegalArgumentException, ServiceException;

	/**
	 * Bearbeitet ein vorhandenes Pferd.
	 * 
	 * @param p Die neuen Daten des Pferds.
	 * @throws IllegalArgumentException wenn die Daten ungültig sind, oder das Pferd nicht vorhandne ist.
	 * @throws ServiceException 
	 */
	public void editPferd(Pferd p) throws IllegalArgumentException, ServiceException;
	
	/**
	 * Markiert ein Pferd als gelöscht.
	 * 
	 * @param p Das zu löschende Pferd
	 * @throws IllegalArgumentException wenn das Pferd nicht vorhandne ist.
	 * @throws ServiceException 
	 */
	public void deletePferd(Pferd p) throws IllegalArgumentException, ServiceException;
	
	/**
	 * Auflistung aller Pferde.
	 * 
	 * @return collection aller Pferde welche nicht gelöscht wurden.
	 * @throws ServiceException 
	 */
	public Collection<Pferd> listPferds() throws ServiceException;
	
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
	public Collection<Pferd> searchPferds(String nameQuery, float maxPreis, Therapieart typQuery) throws ServiceException;
	
	/**
	 * Erstellt eine Rechnung.
	 * 
	 * @param r Eine neue Rechnung welche in die Datenbank eingefügt werden soll.
	 * @throws IllegalArgumentException wenn die Daten ungültig sind.
	 * @throws ServiceException 
	 */
	public void createRechnung(Rechnung r) throws IllegalArgumentException, ServiceException;
	
	/**
	 * Auflistung aller Rechnungen.
	 * 
	 * @return collection aller Rechnungen.
	 * @throws ServiceException 
	 */
	public Collection<Rechnung> listRechnungs() throws ServiceException;
	
	/**
	 * Erhöht den Preis der drei beliebtesten Pferde um fünf Prozent.
	 * @throws ServiceException 
	 */
	public void increaseTop3PferdsBy5Percent() throws ServiceException;
}
