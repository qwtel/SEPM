package sepm.ss13.e1058208.entities;

import java.util.Date;

public class Pferd {
	private int id;
	private String name;
	private int preis;
	private Therapieart type;
	private Date dat;
	
	public Pferd(int id, String name, int preis, Therapieart type, Date dat) {
		this.id = id;
		this.name = name;
		this.preis = preis;
		this.type = type;
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
	public int getPreis() {
		return preis;
	}
	public void setPreis(int preis) {
		this.preis = preis;
	}
	public Therapieart getType() {
		return type;
	}
	public void setType(Therapieart type) {
		this.type = type;
	}
	public Date getDat() {
		return dat;
	}
	public void setDat(Date dat) {
		this.dat = dat;
	}
}
