package sepm.ss13.e1058208.dao;

import java.util.Collection;

import sepm.ss13.e1058208.entities.Pferd;

/**
 * 
 * @author Florian Klampfer
 */
public interface PferdDAO {
	
	/**
	 * 
	 * @param p
	 */
    public void create(Pferd p) throws DAOException;
    
    /**
     * 
     * @param id
     * @return
     */
    public Pferd read(int id) throws DAOException;
    
    /**
     * 
     * @param p
     */
    public void update(Pferd p) throws DAOException;
    
    /**
     * 
     * @param p
     */
    public void delete(Pferd p) throws DAOException;
    
    /**
     * 
     * @return
     */
	public Collection<Pferd> readAll() throws DAOException;
}
