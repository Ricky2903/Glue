CREATE TABLE Event_Occurrence (EVENT_ID VARCHAR(255), OCCURRENCES_ID BIGINT) ENGINE = innodb;
CREATE TABLE Occurrence (id BIGINT NOT NULL, startTime DATETIME NOT NULL, stopTime DATETIME, VENUE_ID VARCHAR(255), PRIMARY KEY (id)) ENGINE = innodb;
ALTER TABLE Event_Occurrence ADD FOREIGN KEY (EVENT_ID) REFERENCES Event (id);
ALTER TABLE Event_Occurrence ADD FOREIGN KEY (OCCURRENCES_ID) REFERENCES Occurrence (id);
ALTER TABLE Occurrence ADD FOREIGN KEY (VENUE_ID) REFERENCES Venue (id);