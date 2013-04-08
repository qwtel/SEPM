package sepm.ss13.e1058208.test;

import java.sql.Connection;
import java.sql.SQLException;

public class Testdata {
	public static void generate(Connection c) throws SQLException {
		c.prepareStatement("delete from therapieeinheiten").execute();
		c.prepareStatement("delete from rechnungen").execute();
		c.prepareStatement("delete from pferde").execute();
		c.prepareStatement("INSERT INTO Pferde (id, name, preis, typ, dat, deleted, img) OVERRIDING SYSTEM VALUE VALUES (0, 'Wendy', 25.95, 'HPR', '2008-11-11', false, null) ").execute();
		c.prepareStatement("INSERT INTO Pferde (id, name, preis, typ, dat, deleted, img) OVERRIDING SYSTEM VALUE VALUES (1, 'Mandy', 15.95, 'Hippotherapie', '2008-11-11', false, null)").execute();
		c.prepareStatement("INSERT INTO Pferde (id, name, preis, typ, dat, deleted, img) OVERRIDING SYSTEM VALUE VALUES (2, 'Sassy', 10.95, 'HPR', '2008-11-11', false, null)").execute();
		c.prepareStatement("INSERT INTO Pferde (id, name, preis, typ, dat, deleted, img) OVERRIDING SYSTEM VALUE VALUES (3, 'Sissy', 5.95, 'HPV', '2008-11-11', false, null)").execute();
		c.prepareStatement("INSERT INTO Pferde (id, name, preis, typ, dat, deleted, img) OVERRIDING SYSTEM VALUE VALUES (4, 'Bobby', 8.95, 'Hippotherapie', '2008-11-11', false, null)").execute();
		c.prepareStatement("INSERT INTO Pferde (id, name, preis, typ, dat, deleted, img) OVERRIDING SYSTEM VALUE VALUES (5, 'Selly', 3.95, 'HPV', '2008-11-11', false, null)").execute();
		c.prepareStatement("INSERT INTO Pferde (id, name, preis, typ, dat, deleted, img) OVERRIDING SYSTEM VALUE VALUES (6, 'Tommy', 14.95, 'HPR', '2008-11-11', false, null)").execute();
		c.prepareStatement("INSERT INTO Pferde (id, name, preis, typ, dat, deleted, img) OVERRIDING SYSTEM VALUE VALUES (7, 'Timmy', 14.95, 'HPV', '2008-11-11', false, null)").execute();
		c.prepareStatement("INSERT INTO Pferde (id, name, preis, typ, dat, deleted, img) OVERRIDING SYSTEM VALUE VALUES (8, 'Donky', 4.95, 'HPV', '2008-11-11', false, null)").execute();
		c.prepareStatement("INSERT INTO Pferde (id, name, preis, typ, dat, deleted, img) OVERRIDING SYSTEM VALUE VALUES (9, 'Wonky', 21.95, 'HPR', '2008-11-11', false, null)").execute();
		
		c.prepareStatement("INSERT INTO Rechnungen (id, dat) OVERRIDING SYSTEM VALUE VALUES (0, '2008-11-11')").execute();
		c.prepareStatement("INSERT INTO Rechnungen (id, dat) OVERRIDING SYSTEM VALUE VALUES (1, '2008-11-11')").execute();
		c.prepareStatement("INSERT INTO Rechnungen (id, dat) OVERRIDING SYSTEM VALUE VALUES (2, '2008-11-11')").execute();
		c.prepareStatement("INSERT INTO Rechnungen (id, dat) OVERRIDING SYSTEM VALUE VALUES (3, '2008-11-11')").execute();
		c.prepareStatement("INSERT INTO Rechnungen (id, dat) OVERRIDING SYSTEM VALUE VALUES (4, '2008-11-11')").execute();
		c.prepareStatement("INSERT INTO Rechnungen (id, dat) OVERRIDING SYSTEM VALUE VALUES (5, '2008-11-11')").execute();
		c.prepareStatement("INSERT INTO Rechnungen (id, dat) OVERRIDING SYSTEM VALUE VALUES (6, '2008-11-11')").execute();
		c.prepareStatement("INSERT INTO Rechnungen (id, dat) OVERRIDING SYSTEM VALUE VALUES (7, '2008-11-11')").execute();
		c.prepareStatement("INSERT INTO Rechnungen (id, dat) OVERRIDING SYSTEM VALUE VALUES (8, '2008-11-11')").execute();
		c.prepareStatement("INSERT INTO Rechnungen (id, dat) OVERRIDING SYSTEM VALUE VALUES (9, '2008-11-11')").execute();
		
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (0, 0, 0, 16, 25.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (1, 1, 0, 18, 15.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (2, 4, 1, 34, 8.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (3, 2, 2, 10, 10.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (4, 8, 2, 49, 4.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (5, 3, 2, 30, 5.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (6, 7, 3, 32, 14.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (7, 8, 4, 10, 4.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (8, 2, 4, 10, 10.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (9, 0, 4, 10, 25.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (10, 5, 4, 10, 3.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (11, 0, 5, 17, 25.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (12, 2, 6, 8, 10.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (13, 3, 6, 12, 5.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (14, 5, 7, 29, 3.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (15, 2, 7, 10, 10.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (16, 8, 7, 50, 4.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (17, 2, 8, 10, 10.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (18, 0, 8, 12, 25.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (19, 0, 9, 22, 25.95)").execute();
		c.prepareStatement("INSERT INTO Therapieeinheiten (id, pid, rid, stunden, preis) OVERRIDING SYSTEM VALUE VALUES (20, 4, 9, 16, 8.95)").execute();
	}
}
