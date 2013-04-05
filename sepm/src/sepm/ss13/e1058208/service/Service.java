package sepm.ss13.e1058208.service;

import java.util.Collection;

import sepm.ss13.e1058208.entities.Pferd;
import sepm.ss13.e1058208.entities.Rechnung;
import sepm.ss13.e1058208.entities.Therapieart;

/**
 * 
 * @author Florian Klampfer
 */
public interface Service {
	
	/**
	 * Erstellt ein neues Pferd
	 * @param p Das zu erstellende Pferd
	 * @throws IllegalArgumentException wenn die Daten ungültig sind.
	 */
	public void createPferd(Pferd p) throws IllegalArgumentException;

	/**
	 * Bearbeitet ein vorhandenes Pferd.
	 * @param p Die neuen Daten des Pferds.
	 * @throws IllegalArgumentException wenn die Daten ungültig sind, oder das Pferd nicht vorhandne ist.
	 */
	public void editPferd(Pferd p) throws IllegalArgumentException;
	
	/**
	 * Markiert ein Pferd als gelöscht.
	 * @param p Das zu löschende Pferd
	 * @throws IllegalArgumentException wenn das Pferd nicht vorhandne ist.
	 */
	public void deletePferd(Pferd p) throws IllegalArgumentException;
	
	/**
	 * Auflistung aller Pferde.
	 * @return collection aller Pferde welche nicht gelöscht wurden.
	 */
	public Collection<Pferd> listPferds();
	
	/**
	 * Listet alle Pferde welche Bedingungen erfüllen.
	 * searchPferds mit default-Werten (null, 0.0f, null) ist identisch mit listPfers (wenn auch nicht performanter).
	 * 
	 * @param nameQuery Name oder teilweiser Name des gesuchten Pferds oder null wenn alle Namen gesucht sind.
	 * @param maxPreis Maximaler Preis pro Therapieeinheit oder 0.0f wenn alle Preise gesucht sind.
	 * @param typQuery Gewünschte Therapieart oder null wenn alle Therapiearten gesucht sind.
	 * @return collection aller Pferde welche die Query matchen.
	 */
	public Collection<Pferd> searchPferds(String nameQuery, float maxPreis, Therapieart typQuery);
	
	/**
	 * Erstellt eine Rechnung
	 * @param r Eine neue Rechnung welche in die Datenbank eingefügt werden soll.
	 * @throws IllegalArgumentException wenn die Daten ungültig sind.
	 */
	public void createRechnung(Rechnung r) throws IllegalArgumentException;
	
	/**
	 * Auflistung aller Rechnungen.
	 * @return collection aller Rechnungen.
	 */
	public Collection<Rechnung> listRechnungs();
	
	/**
	 * Erhöht den Preis der drei beliebtesten Pferde um fünf Prozent.
	 */
	public void increaseTop3PferdsBy5Percent();
}
