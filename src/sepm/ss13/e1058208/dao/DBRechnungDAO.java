package sepm.ss13.e1058208.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import org.apache.log4j.Logger;

import sepm.ss13.e1058208.entities.Pferd;
import sepm.ss13.e1058208.entities.Rechnung;
import sepm.ss13.e1058208.entities.Therapieeinheit;

/**
 * DAO zur Abfrage von Rechnungen aus der Datenbank.
 * 
 * @author Florian Klampfer
 */
public class DBRechnungDAO implements RechnungDAO {

	private static final Logger log = Logger.getLogger(DBRechnungDAO.class);
	
	private PreparedStatement saveStmt;
	private PreparedStatement saveTPStmt;
	private PreparedStatement loadStmt;
	private PreparedStatement followUpStmt;
	private PreparedStatement loadAllStmt;
	private PreparedStatement getRidStmt;
	private PreparedStatement getTidStmt;
	
	private PferdDAO pferdDAO;
	
	/**
	 * Erzeugt ein neues DAO zur Abfrage von Rechnungen.
	 * @param con Verbindung zur Datenbank.
	 * @throws DAOException wenn ein Datenbankfehler auftritt.
	 */
	public DBRechnungDAO(Connection con) throws DAOException {
		try {
			pferdDAO = new DBPferdDAO(con);
			
			saveStmt = con.prepareStatement("INSERT INTO Rechnungen VALUES (DEFAULT, ?);");
			saveTPStmt = con.prepareStatement("INSERT INTO Therapieeinheiten VALUES (DEFAULT, ?, ?, ?, ?);");
			loadStmt = con.prepareStatement("SELECT * FROM Rechnungen WHERE id = ?;");
			followUpStmt = con.prepareStatement("SELECT * FROM Therapieeinheiten WHERE rid = ?;");
			loadAllStmt = con.prepareStatement("SELECT * FROM Rechnungen;");
			getRidStmt = con.prepareStatement("SELECT TOP 1 id FROM Rechnungen ORDER BY id DESC;");
			getTidStmt = con.prepareStatement("SELECT TOP 1 id FROM Therapieeinheiten ORDER BY id DESC;");
		} catch(SQLException e) {
			log.error("const " + e);
			throw new DAOException();
		}
	}
	
	/**
	 * Speichert eine Rechnung und setzt das Feld id auf den von der DB generierten Wert.
	 * 
	 * @param p Die Rechnung welche gespeichert werden soll. Überschreibt das id-Feld.
	 * @throws DAOException wenn die Rechnung nicht erstellt werden konnte.
	 */
	@Override
	public void create(Rechnung r) throws DAOException {
		try {
			saveStmt.setDate(1, r.getDat());
			saveStmt.executeUpdate();
			
			ResultSet result = getRidStmt.executeQuery();
			if (!result.next()) throw new DAOException();
			r.setId(result.getInt("id"));
			
			HashMap<Pferd, Therapieeinheit> einheiten = r.getEinheiten();
			for(Pferd p : einheiten.keySet()) {
				Therapieeinheit t = einheiten.get(p);
				saveTPStmt.setInt(1, p.getId());
				saveTPStmt.setInt(2, r.getId());
				saveTPStmt.setInt(3, t.getStunden());
				saveTPStmt.setFloat(4, t.getPreis());
				saveTPStmt.executeUpdate();
				
				ResultSet followUpResult = getTidStmt.executeQuery();
				if (!followUpResult.next()) throw new DAOException();
				t.setId(followUpResult.getInt("id"));
			}
			
		} catch(SQLException e) {
			log.error("create " + e);
			throw new DAOException();
		}
	}

    /**
     * Liest eine Rechnung (samt Therapieeinheiten und dazugehöriger Pferde) aus.
     * 
     * @param id Die id der gewünschten Rechnung.
     * @return Die gewünschte Rechnung sofern vorhanden.
     * @throws DAOException Falls die Rechnung nicht exisitiert.
     */
	@Override
	public Rechnung read(int id) throws DAOException {
		try {	
			loadStmt.setInt(1, id);
			ResultSet result = loadStmt.executeQuery();
			if (!result.next()) throw new DAOException();
			
			Rechnung r = new Rechnung();
			r.setId(result.getInt("id"));
			r.setDat(result.getDate("dat"));
			
			followUpStmt.setInt(1, id);
			ResultSet followUpResult = followUpStmt.executeQuery();
			r.setEinheiten(getEinheiten(followUpResult));
			return r;
			
		} catch(SQLException e) {
			log.error("read " + e);
			throw new DAOException();
		}
	}
	
    /**
     * Eine Auflistung aller Rechnungen.
     * 
     * @return Eine Collection mit Rechnungen.
     * @throws DAOException wenn ein Fehler auftritt.
     */
	@Override
	public Collection<Rechnung> readAll() throws DAOException {
		try {
			ResultSet result = loadAllStmt.executeQuery();
			Collection<Rechnung> col = new ArrayList<Rechnung>();
			Rechnung r;
			
			while (result.next()) {
				r = new Rechnung();
				r.setId(result.getInt("id"));
				r.setDat(result.getDate("dat"));
				
				followUpStmt.setInt(1, r.getId());
				ResultSet followUpResult = followUpStmt.executeQuery();
				r.setEinheiten(getEinheiten(followUpResult));
				col.add(r);
			}
			return col;
			
		} catch(SQLException e) {
			log.error("read " + e);
			throw new DAOException();
		}
	}
	
	private HashMap<Pferd, Therapieeinheit> getEinheiten(ResultSet result) throws SQLException, DAOException {
		HashMap<Pferd, Therapieeinheit> einheiten = new HashMap<Pferd, Therapieeinheit>();
		while (result.next()) {
			Therapieeinheit t = new Therapieeinheit();
			t.setId(result.getInt("id"));
			t.setPreis(result.getFloat("preis"));
			t.setStunden(result.getInt("stunden"));
			
			Pferd p = pferdDAO.read(result.getInt("pid"));
			einheiten.put(p, t);
		}
		return einheiten;
	}
}
