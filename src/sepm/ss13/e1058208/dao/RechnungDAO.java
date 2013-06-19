package sepm.ss13.e1058208.dao;

import java.util.Collection;

import sepm.ss13.e1058208.entities.Rechnung;

/**
 * DAO zur Abfrage von Rechnungen.
 * 
 * @author Florian Klampfer
 */
public interface RechnungDAO {
	
	/**
	 * Speichert eine Rechnung. 
	 * 
	 * @param p Die Rechnung welche gespeichert werden soll. 
	 * @throws DAOException wenn die Rechnung nicht erstellt werden konnte.
	 */
    public void create(Rechnung p) throws DAOException;
    
    /**
     * Liest eine Rechnung (samt Therapieeinheiten und dazugehöriger Pferde) aus.
     * 
     * @param id Die id der gewünschten Rechnung.
     * @return Die gewünschte Rechnung sofern vorhanden.
     * @throws DAOException Falls die Rechnung nicht exisitiert.
     */
    public Rechnung read(int id) throws DAOException;
    
    /**
     * Eine Auflistung aller Rechnungen.
     * 
     * @return Eine Collection mit Rechnungen.
     * @throws DAOException wenn ein Fehler auftritt.
     */
	public Collection<Rechnung> readAll() throws DAOException;
}
