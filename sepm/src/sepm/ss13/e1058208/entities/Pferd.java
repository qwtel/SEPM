package sepm.ss13.e1058208.entities;

import java.sql.Date;

import org.apache.log4j.Logger;

import sepm.ss13.e1058208.test.TestDBPferdDAO;

/**
 * Kapselt die Werte eines Pferds.
 * 
 * @author Florian Klampfer
 */
public class Pferd {
	
	private static final Logger log = Logger.getLogger(Pferd.class);
	
	/**
	 * Eindeutige id des Pferds (von Datenbank generiert).
	 */
	private int id;
	
	/**
	 * Name des Pferds.
	 */
	private String name;
	
	/**
	 * Preis pro Stunde.
	 */
	private float preis;
	
	/**
	 * Therapieart des Pferds als enum Typ.
	 */
	private Therapieart typ;
	
	/**
	 * Das Geburtsdatum des Pferds.
	 */
	private Date dat;
	
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
}
