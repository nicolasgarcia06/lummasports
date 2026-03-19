CREATE TABLE equipos (
                         id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         juego VARCHAR(100) NOT NULL,
                         pais VARCHAR(100) NOT NULL
);

CREATE TABLE torneos (
                         id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
                         nombre VARCHAR(100) NOT NULL,
                         ciudad VARCHAR(100) NOT NULL,
                         premio DECIMAL(10,2) NOT NULL,
                         equipo_campeon_id BIGINT NOT NULL,
                         CONSTRAINT fk_torneo_equipo
                             FOREIGN KEY (equipo_campeon_id) REFERENCES equipos(id)
);