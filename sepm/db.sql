CREATE TABLE Pferde (id INTEGER GENERATED ALWAYS AS IDENTITY, name VARCHAR (30) not null, preis FLOAT not null, typ VARCHAR (30), dat DATE, deleted BOOLEAN, img VARCHAR(999), PRIMARY KEY (id), CHECK (typ IN ('Hippotherapie', 'HPV', 'HPR')));
CREATE TABLE Rechnungen (id INTEGER GENERATED ALWAYS AS IDENTITY, dat DATE, PRIMARY KEY (id));

-- next query
CREATE TABLE Therapieeinheiten (id INTEGER GENERATED ALWAYS AS IDENTITY, pid INTEGER, rid INTEGER, stunden INTEGER, preis FLOAT, PRIMARY KEY (id), FOREIGN KEY (pid) REFERENCES Pferde(id), FOREIGN KEY (rid) REFERENCES Rechnungen(id));

INSERT INTO Pferde VALUES (DEFAULT, 'Wendy', 25.95, 'HPR', '2008-11-11', false, null)
INSERT INTO Pferde VALUES (DEFAULT, 'Mandy', 15.95, 'Hippotherapie', '2008-11-11', false, null)
INSERT INTO Pferde VALUES (DEFAULT, 'Sassy', 10.95, 'HPR', '2008-11-11', false, null)
INSERT INTO Pferde VALUES (DEFAULT, 'Sissy', 5.95, 'HPV', '2008-11-11', false, null)
