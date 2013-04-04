package sepm.ss13.e1058208.dao;

import java.util.Collection;

import sepm.ss13.e1058208.entities.Rechnung;

public interface RechnungDAO {
    public void create(Rechnung p);
    public Rechnung read(int id);
	public Collection<Rechnung> readAll();
}
