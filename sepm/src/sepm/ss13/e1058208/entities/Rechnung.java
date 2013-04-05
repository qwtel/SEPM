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
	 * -1 bedeutet dass noch keine id generiert wurde.
	 */
	private int id = -1;
	
	/**
	 * Erstellungszeitpunkt der Rechnung
	 */
	private Date dat = null;
	
	/**
	 * Verknüpfung von Pferden zu Therapieeinheiten
	 */
	private HashMap<Pferd, Therapieeinheit> einheiten = null;

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
	
	@Override
	public String toString() {
		String s = "Rechnung-" + id;
		return s;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (other == this) return true;
		if (other.getClass() != this.getClass()) return false;
		Rechnung o = (Rechnung)other;
		return o.getId() == this.getId();
	}
}
