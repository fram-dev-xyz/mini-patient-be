CREATE TABLE state (
	id INT NOT NULL,
	name varchar(50) NOT NULL,
	CONSTRAINT state_pk PRIMARY KEY (id)
);

CREATE TABLE suburb (
	id INT auto_increment NOT NULL,
	state_id INT NOT NULL,
	name varchar(50) NOT NULL,
	CONSTRAINT suburb_pk PRIMARY KEY (id),
	CONSTRAINT suburb_state_FK FOREIGN KEY (state_id) REFERENCES state(id) ON DELETE CASCADE
);