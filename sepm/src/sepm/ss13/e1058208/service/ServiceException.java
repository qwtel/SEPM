package sepm.ss13.e1058208.service;

/**
 * Ein Fehler auf der Serviceschicht.
 * 
 * @author Florian Klampfer
 */
public class ServiceException extends Exception {
	private static final long serialVersionUID = 1L;

	public ServiceException() {
		super();
	}
	
	public ServiceException(String s) {
		super(s);
	}
}
