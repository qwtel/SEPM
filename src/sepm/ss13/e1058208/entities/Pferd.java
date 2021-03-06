package sepm.ss13.e1058208.entities;

import java.sql.Date;

import org.apache.log4j.Logger;

/**
 * Kapselt die Werte eines Pferds.
 * 
 * @author Florian Klampfer
 */
public class Pferd {
	
	private static final Logger log = Logger.getLogger(Pferd.class);
	
	/**
	 * Eindeutige id des Pferds (von Datenbank generiert).
	 * -1 bedeutet dass noch keine id generiert wurde.
	 */
	private int id = -1;
	
	/**
	 * Name des Pferds.
	 */
	private String name = "";
	
	/**
	 * Preis pro Stunde.
	 */
	private float preis = 0.0f;
	
	/**
	 * Therapieart des Pferds als enum Typ.
	 */
	private Therapieart typ = Therapieart.Hippotherapie;
	
	/**
	 * Das Geburtsdatum des Pferds.
	 */
	private Date dat = new Date(System.currentTimeMillis());
	
	/**
	 * Pfad zu einem Bild des Pferds.
	 */
	private String img = null;
	
	/**
	 * Erstellt ein leeres Pferd. Alle Werte (außer id) müssen über die Setter-Methoden gesetzten werden.
	 */
	public Pferd() {}
	
	/**
	 * Erstellt ein Pferd mit den gegebenen Werten.
	 * @param id Eindeutige id des Pferds (von Datenbank generiert).
	 * @param name Name des Pferds.
	 * @param preis Preis pro Stunde.
	 * @param type Therapieart des Pferds als enum Typ.
	 * @param dat Das Geburtsdatum des Pferds.
	 */
	public Pferd(int id, String name, float preis, Therapieart type, Date dat) {
		this.id = id;
		this.name = name;
		this.preis = preis;
		this.typ = type;
		this.dat = dat;
	}
	
	/**
	 * Erstellt ein Pferd mit den gegebenen Werten.
	 * @param id Eindeutige id des Pferds (von Datenbank generiert).
	 * @param name Name des Pferds.
	 * @param preis Preis pro Stunde.
	 * @param type Therapieart des Pferds als enum Typ.
	 * @param dat Das Geburtsdatum des Pferds.
	 * @param img Pfad zu einem Bild des Pferds oder null.
	 */
	public Pferd(int id, String name, float preis, Therapieart type, Date dat, String img) {
		this.id = id;
		this.name = name;
		this.preis = preis;
		this.typ = type;
		this.dat = dat;
		this.setImg(img);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public float getPreis() {
		return preis;
	}
	
	public void setPreis(float preis) {
		this.preis = preis;
	}
	
	public Therapieart getTyp() {
		return typ;
	}
	
	public void setTyp(Therapieart typ) {
		this.typ = typ;
	}
	
	public Date getDat() {
		return dat;
	}
	
	public void setDat(Date dat) {
		this.dat = dat;
	}
	
	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object other) {
		log.debug("equals");
		if (other == null) return false;
		if (other == this) return true;
		if (other.getClass() != this.getClass()) return false;
		Pferd o = (Pferd)other;
		return o.getId() == this.getId();
	}
	
	@Override
	public int hashCode() {
		return id;
	}
}
