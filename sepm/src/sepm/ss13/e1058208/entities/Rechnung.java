package sepm.ss13.e1058208.entities;

import java.sql.Date;
import java.util.HashMap;

/**
 * Kapselt die Werte einer Rechnung.
 * 
 * @author Florian Klampfer
 */
public class Rechnung {
	
	/**
	 * Eindeutige id der Rechnung (von Datenbank generiert).
	 */
	private int id;
	
	/**
	 * Erstellungszeitpunkt der Rechnung
	 */
	private Date dat;
	
	/**
	 * Verknüpfung von Pferden zu Therapieeinheiten
	 */
	private HashMap<Pferd, Therapieeinheit> einheiten;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getDat() {
		return dat;
	}

	public void setDat(Date dat) {
		this.dat = dat;
	}

	public HashMap<Pferd, Therapieeinheit> getEinheiten() {
		return einheiten;
	}

	public void setEinheiten(HashMap<Pferd, Therapieeinheit> einheiten) {
		this.einheiten = einheiten;
	}
}
