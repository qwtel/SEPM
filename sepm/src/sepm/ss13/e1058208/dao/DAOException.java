package sepm.ss13.e1058208.dao;

/**
 * Weist auf Fehler beim Abfragen von Daten in Data Access Objekten hin.
 * 
 * @author Florian Klampfer
 */
public class DAOException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public DAOException() {
		super();
	}
	
	public DAOException(String s) {
		super(s);
	}
}
