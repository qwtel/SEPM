package sepm.ss13.e1058208.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Logger;
import sepm.ss13.e1058208.entities.Pferd;
import sepm.ss13.e1058208.entities.Therapieart;

/**
 * 
 * @author Florian Klampfer
 */
public class DBPferdDAO implements PferdDAO {
	
	private static final Logger log = Logger.getLogger(DBPferdDAO.class);

	private PreparedStatement saveStmt;
	private PreparedStatement loadStmt;
	private PreparedStatement loadAllStmt;
	private PreparedStatement updtStmt;
	private PreparedStatement deltStmt;
	private PreparedStatement getIdStmt;

	public DBPferdDAO(Connection con) {
		try {
			saveStmt = con.prepareStatement("INSERT INTO Pferde VALUES (DEFAULT, ?, ?, ?, ?, FALSE);");
			loadStmt = con.prepareStatement("SELECT * FROM Pferde WHERE id = ?;");
			loadAllStmt = con.prepareStatement("SELECT * FROM Pferde WHERE deleted = FALSE;");
			getIdStmt = con.prepareStatement("SELECT TOP 1 id FROM Pferde ORDER BY id DESC;");
			updtStmt = con.prepareStatement("UPDATE Pferde SET name = ?, preis = ?, typ = ?, dat = ? WHERE id = ?");
			deltStmt = con.prepareStatement("UPDATE Pferde SET deleted = TRUE WHERE id = ?");
		} catch(SQLException e) {
			log.error("const " + e);
			throw new RuntimeException();
		}
	}

	@Override
	public void create(Pferd p) {
		try {
			saveStmt.setString(1, p.getName());
			saveStmt.setFloat(2, p.getPreis());
			saveStmt.setString(3, p.getTyp().toString());
			saveStmt.setDate(4, p.getDat());
			saveStmt.executeUpdate();
			
			ResultSet result = getIdStmt.executeQuery();
			if (!result.next()) throw new RuntimeException();
			p.setId(result.getInt("id"));
			
		} catch(SQLException e) {
			log.error("create " + e);
			throw new RuntimeException();
		}
	}

	@Override
	public Pferd read(int id) {
		try {	
			loadStmt.setInt(1, id);
			ResultSet result = loadStmt.executeQuery();
			if (!result.next()) throw new RuntimeException();
			
			Pferd p = new Pferd();
			p.setId(result.getInt("id"));
			p.setName(result.getString("name"));
			p.setPreis(result.getFloat("preis"));
			p.setTyp(Therapieart.valueOf(result.getString("typ")));
			p.setDat(result.getDate("dat"));
			return p;
		} catch(SQLException e) {
			log.error("read " + e);
			throw new RuntimeException();
		}
	}
	
	@Override
	public Collection<Pferd> readAll() {
		try {	
			ResultSet result = loadAllStmt.executeQuery();
			Collection<Pferd> col = new ArrayList<Pferd>();
			Pferd p;
			
			while (result.next()) {
				p = new Pferd();
				p.setId(result.getInt("id"));
				p.setName(result.getString("name"));
				p.setPreis(result.getFloat("preis"));
				p.setTyp(Therapieart.valueOf(result.getString("typ")));
				p.setDat(result.getDate("dat"));
				col.add(p);
			}
			return col;
			
		} catch(SQLException e) {
			log.error("readAll " + e);
			throw new RuntimeException();
		}
	}

	@Override
	public void update(Pferd p) {
		try {
			updtStmt.setString(1, p.getName());
			updtStmt.setFloat(2, p.getPreis());
			updtStmt.setString(3, p.getTyp().toString());
			updtStmt.setDate(4, p.getDat());
			updtStmt.setInt(5, p.getId());
			updtStmt.executeUpdate();
		} catch(SQLException e) {
			log.error("update " + e);
			throw new RuntimeException();
		}
	}

	@Override
	public void delete(Pferd p) {
		try {
			deltStmt.setInt(1, p.getId());
			deltStmt.executeUpdate();
		} catch(SQLException e) {
			log.error("delete " + e);
			throw new RuntimeException();
		}
	}
}
