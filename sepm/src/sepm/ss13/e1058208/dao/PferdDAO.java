package sepm.ss13.e1058208.dao;

import sepm.ss13.e1058208.entities.Pferd;

public interface PferdDAO {
    public void create(Pferd p);
    public Pferd read(int id);
    public void update(Pferd p);
    public void delete(Pferd p);
}
