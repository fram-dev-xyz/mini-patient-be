CREATE TABLE patient (
	id BIGINT auto_increment NOT NULL,
	pid varchar(50) NOT NULL,
	first_name varchar(100) NOT NULL,
	last_name varchar(100) NULL,
	dob DATE NOT NULL,
	gender CHAR(1) NOT NULL,
	phone_number VARCHAR(20) NOT NULL,
	address varchar(255) NOT NULL,
    suburb_id INT NULL,
    postcode char(4) NULL,
    created_at TIMESTAMP NULL,
    updated_at TIMESTAMP NULL,
    version INT NULL,
	CONSTRAINT patient_pk PRIMARY KEY (id),
	CONSTRAINT patient_unique UNIQUE (pid),
	CONSTRAINT patient_suburb_FK FOREIGN KEY (suburb_id) REFERENCES suburb(id) ON DELETE SET NULL
);