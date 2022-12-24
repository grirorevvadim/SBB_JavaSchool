--http://localhost:8080/h2-console/
CREATE TABLE countries
(
    country_id           int,
    country_name varchar(255),
    PRIMARY KEY (country_id)
);
INSERT INTO countries (country_id, country_name)
VALUES (2, 'Russia');
INSERT INTO countries (country_id, country_name)
VALUES (3, 'Hungary');
INSERT INTO countries (country_id, country_name)
VALUES (4, 'Austria');
INSERT INTO countries (country_id, country_name)
VALUES (5, 'Stockholm');
INSERT INTO countries (country_id, country_name)
VALUES (6, 'Germany');

CREATE TABLE cities(
    city_id int,
    city_name varchar (255),
    country_id int,
    PRIMARY KEY (city_id),
    FOREIGN KEY (country_id) REFERENCES countries(country_id)
);

INSERT INTO cities (city_id, city_name,country_id)
VALUES (2, 'Saint Petersburg',2);
INSERT INTO cities (city_id, city_name,country_id)
VALUES (3, 'Budapest',3);
INSERT INTO cities (city_id, city_name,country_id)
VALUES (4, 'Wien',4);
INSERT INTO cities (city_id, city_name,country_id)
VALUES (5, 'Dusseldorf',5);