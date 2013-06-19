package sepm.ss13.e1058208.dao;

import java.util.Collection;

import sepm.ss13.e1058208.entities.Pferd;

/**
 * Interface zum Abfragen von Pferden.
 * 
 * @author Florian Klampfer
 */
public interface PferdDAO {
	
	/**
	 * Speichert ein Pferd.
	 * 
	 * @param p Das Pferd das gespeichert werden soll.
	 * @throws DAOException wenn das Pferd nicht erstellt werden konnte.
	 */
    public void create(Pferd p) throws DAOException;
    
    /**
     * Liest ein Pferd (auch wenn es als gelöscht markiert wurde).
     * 
     * @param id Id des Pferds das gelesen werden soll.
     * @return Das gewünschte Pferd.
     * @throws DAOException wenn das Pferd nicht gelesen werdne kann.
     */
    public Pferd read(int id) throws DAOException;
    
    /**
     * Überschreibt ein vorhandes Pferd mit neuen Daten.
     * 
     * @param p Ein Pferd Objekt mit gesetzter id und den vollständigen neuen Daten.
     * @throws DAOException wenn das Pferd nicht geschrieben werden konnte.
     */
    public void update(Pferd p) throws DAOException;
    
    /**
     * Markiert ein Pferd als gelöscht.
     * 
     * @param p Ein Pferd mit gesetzter id.
     * @throws DAOException wenn das Pferd nicht gelöscht werden konnte.
     */
    public void delete(Pferd p) throws DAOException;
    
    /**
     * Liefert alle Pferde, welche nicht gelöscht wurden.
     * 
     * @return Alle Pferde, welche nicht gelöscht wurden.
	 * @throws DAOExceoption wenn die Pferde nicht gelesen werden konnten.
     */
	public Collection<Pferd> readAll() throws DAOException;
	
	/**
	 * Liefert die drei meistgebuchten Pferde, welche nicht gelöscht wurden.
	 * 
	 * @return Die drei meistgebucthen Pferde.
	 * @throws DAOExceoption wenn die Pferde nicht gelesen werden konnten.
	 */
	public Collection<Pferd> readTop3() throws DAOException;
}
