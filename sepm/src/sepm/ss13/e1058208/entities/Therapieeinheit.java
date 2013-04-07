package sepm.ss13.e1058208.entities;

//import org.apache.log4j.Logger;

/**
 * Kapselt die Werte einer Therapieeinheit (Bestandteil einer Rechnung).
 * 
 * @author Florian Klampfer
 */
public class Therapieeinheit {
	
	//private static final Logger log = Logger.getLogger(Therapieeinheit.class);
	
	/**
	 * Erstellt eine leere Therapieeinheit. Alle Werte (außer id) müssen über die Setter-Methoden gesetzten werden.
	 */
	public Therapieeinheit() { }
	
	/**
	 * Erstellt eine Therapieeinheit mit den gegebenen Werten.
	 * @param array Ein Array von Werten so wie sie von toArray() erstellt wurden.
	 */
	public Therapieeinheit(Object[] array) {
		this.id = (Integer)array[0];
		this.stunden = (Integer)array[1];
		this.preis = (Float)array[2];
	}
	
	/**
	 * Eindeutige id der Therapieeinheit (von Datenbank generiert).
	 * -1 bedeutet dass noch keine id generiert wurde.
	 */
	private int id = -1;
	
	/**
	 * Anzahl der verbuchten Stunden.
	 */
	private int stunden = 0;
	
	/**
	 * Verbuchter Preis pro Stunde.
	 */
	private float preis = 0.0f;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStunden() {
		return stunden;
	}

	public void setStunden(int stunden) {
		this.stunden = stunden;
	}

	public float getPreis() {
		return preis;
	}

	public void setPreis(float preis) {
		this.preis = preis;
	}
	
	@Override
	public String toString() {
		return "" + id + " " + stunden + " " + preis;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other == null) return false;
		if (other == this) return true;
		if (other.getClass() != this.getClass()) return false;
		Therapieeinheit o = (Therapieeinheit)other;
		return o.getId() == this.getId();
	}
	
	public Object[] toArray() {
		return new Object[] {id, stunden, preis };
	}


}
