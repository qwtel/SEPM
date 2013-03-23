package sepm.ss13.e1058208.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

	public DBPferdDAO(String url, String user, String pw) throws SQLException {
		Connection con = DriverManager.getConnection(url, user, pw);
		saveStmt = con.prepareStatement("INSERT INTO Pferde VALUES (DEFAULT, ?, ?, ?, ?)");
		loadStmt = con.prepareStatement("SELECT * FROM Pferde WHERE id = ?");
	}

	@Override
	public void create(Pferd p) {
		try {
			saveStmt.setString(1, p.getName());
			saveStmt.setFloat(2, p.getPreis());
			saveStmt.setString(3, p.getTyp().toString());
			saveStmt.setDate(4, p.getDat());
			saveStmt.executeUpdate();
		} catch(SQLException e) {}
	}

	@Override
    public Pferd read(int id) {
		try {	
			loadStmt.setInt(1, id);
			ResultSet result = loadStmt.executeQuery();
			if (!result.next()) return null;
			
			Pferd p = new Pferd();
			p.setId(result.getInt("id"));
			p.setName(result.getString("name"));
			p.setPreis(result.getFloat("preis"));
			p.setTyp(Therapieart.valueOf(result.getString("typ")));
			p.setDat(result.getDate("dat"));
			return p;
		} catch(SQLException e) {}
		return null;
	}

	@Override
	public void update(Pferd p) {

	}

	@Override
	public void delete(Pferd p) {

	}
}
