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
	 * 
	 * @param p
	 * @throws DAOException
	 */
    public void create(Rechnung p) throws DAOException;
    
    /**
     * 
     * @param id
     * @return
     * @throws DAOException
     */
    public Rechnung read(int id) throws DAOException;
    
    /**
     * 
     * @return
     * @throws DAOException
     */
	public Collection<Rechnung> readAll() throws DAOException;
}
