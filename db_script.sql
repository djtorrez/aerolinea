/*  creacion de la tabla cliente  */
CREATE TABLE public.cliente
(
    id bigserial NOT NULL,
    nombre character varying(100) NOT NULL,
    ci character varying(10) NOT NULL,
    telefono integer NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE public.cliente
    OWNER to postgres;

/*  creacion de la tabla vacuna  */
CREATE TABLE public.vacuna
(
    id bigserial NOT NULL,
    nombre smallint NOT NULL,
    numero_dosis smallint NOT NULL,
    fecha date NOT NULL,
    cliente_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_cliente FOREIGN KEY (cliente_id)
        REFERENCES public.cliente (id) MATCH FULL
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT rango_nombre CHECK (nombre BETWEEN 1 and 4)
);

ALTER TABLE public.vacuna
    OWNER to postgres;

/*  creacion de la tabla prueba_covid  */
CREATE TABLE public.prueba_covid
(
    id bigserial NOT NULL,
    tipo smallint NOT NULL,
    resultado boolean NOT NULL,
    fecha date NOT NULL,
    cliente_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT cliente_fk FOREIGN KEY (cliente_id)
        REFERENCES public.cliente (id) MATCH FULL
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT tipo_check CHECK (tipo BETWEEN 1 AND 4)
);

ALTER TABLE public.prueba_covid
    OWNER to postgres;

/*  creacion de la tabla horario_vuelo  */
CREATE TABLE public.horario_vuelo
(
    id bigserial NOT NULL,
    fecha date NOT NULL,
    hora time without time zone NOT NULL,
    estado boolean NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE public.horario_vuelo
    OWNER to postgres;

/*  creacion de la tabla destino  */
CREATE TABLE public.destino
(
    id bigserial NOT NULL,
    nombre character varying(100) NOT NULL,
    estado boolean NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE public.destino
    OWNER to postgres;

/*  creacion de la tabla avion  */
CREATE TABLE public.avion
(
    id bigserial NOT NULL,
    modelo character varying(20) NOT NULL,
    fabricante character varying(50) NOT NULL,
    matricula character varying(10) NOT NULL,
    estado boolean NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE public.avion
    OWNER to postgres;

/*  creacion de la tabla asiento  */
CREATE TABLE public.asiento
(
    avion_id bigint NOT NULL,
    id bigserial NOT NULL,
    tipo character(1) NOT NULL,
    estado character(1) NOT NULL,
    PRIMARY KEY (id, avion_id),
    CONSTRAINT unique_key UNIQUE (id),
    CONSTRAINT avion_fk FOREIGN KEY (avion_id)
        REFERENCES public.avion (id) MATCH FULL
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT tipo_check CHECK (tipo IN ('P','E','C')),
    CONSTRAINT estado_check CHECK (estado IN ('R','D','B'))
);

ALTER TABLE public.asiento
    OWNER to postgres;

/*  creacion de la tabla reserva  */
CREATE TABLE public.reserva
(
    id bigserial NOT NULL,
    destino_id bigint NOT NULL,
    horario_vuelo_id bigint NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT destino_fk FOREIGN KEY (destino_id)
        REFERENCES public.destino (id) MATCH FULL
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT horario_vuelo_fk FOREIGN KEY (horario_vuelo_id)
        REFERENCES public.horario_vuelo (id) MATCH FULL
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

ALTER TABLE public.reserva
    OWNER to postgres;

/*  creacion de la tabla reserva_asiento  */
CREATE TABLE public.reserva_asiento
(
    asiento_id bigint NOT NULL,
    reserva_id bigint NOT NULL,
    PRIMARY KEY (asiento_id, reserva_id),
    CONSTRAINT asiento_fk FOREIGN KEY (asiento_id)
        REFERENCES public.asiento (id) MATCH FULL
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT reserva_fk FOREIGN KEY (reserva_id)
        REFERENCES public.reserva (id) MATCH FULL
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

ALTER TABLE public.reserva_asiento
    OWNER to postgres;

/*  creacion de la tabla cliente_reserva  */
CREATE TABLE public.cliente_reserva
(
    cliente_id bigint NOT NULL,
    reserva_id bigint NOT NULL,
    PRIMARY KEY (reserva_id, cliente_id),
    CONSTRAINT cliente_fkey FOREIGN KEY (cliente_id)
        REFERENCES public.cliente (id) MATCH FULL
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT reserva_fkey FOREIGN KEY (reserva_id)
        REFERENCES public.reserva (id) MATCH FULL
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

ALTER TABLE public.cliente_reserva
    OWNER to postgres;

/* insertando datos en la tabla cliente */

INSERT INTO public.cliente (nombre, ci, telefono) VALUES
('Alejandro Rodriguez Salazar', '9794155', 63496394);

/* insertando datos en la tabla vacuna */

INSERT INTO public.vacuna (id, nombre, numero_dosis, fecha, cliente_id) VALUES
(1, 1, 1, '2023-08-29', 1),
(2, 1, 2, '2023-09-10', 1),
(3, 2, 3, '2023-10-01', 1);

/* insertando datos en la tabla prueba_covid */

INSERT INTO public.prueba_covid (id, tipo, resultado, fecha, cliente_id) VALUES
(1, 1, 'f', '2023-08-29', 1),
(2, 2, 'f', '2023-08-01', 1),
(3, 3, 'f', '2023-07-01', 1);

/* insertando datos en la tabla horario_vuelo */

INSERT INTO public.horario_vuelo (id, fecha, hora, estado) VALUES
(1, '2023-08-30', '12:00:00', 't'),
(2, '2023-08-31', '14:00:00', 't'),
(3, '2023-09-01', '15:30:00', 't');


/* insertando datos en la tabla destino */

INSERT INTO public.destino (id, nombre, estado) VALUES
(1, 'Santa Cruz', 't'),
(2, 'Beni', 't'),
(3, 'Chuquisaca', 't'),
(4, 'Cochabamba', 't'),
(5, 'La Paz', 't'),
(6, 'Oruro', 't'),
(7, 'Pando', 't'),
(8, 'Potosi', 't'),
(9, 'Tarija', 't');

/* insertando datos en la tabla avion */

INSERT INTO public.avion (modelo, fabricante, matricula, estado) VALUES
('Boeing 747', 'Boeing', 'ABC123', true),
('Airbus A320', 'Airbus', 'XYZ789', true),
('Boeing 787', 'Boeing', 'DEF456', false);

/* insertando datos en la tabla asiento */

INSERT INTO public.asiento (avion_id, id, tipo, estado) VALUES
(1, 1, 'P', 'D'),
(1, 2, 'P', 'D'),
(1, 3, 'P', 'D'),
(1, 4, 'E', 'D'),
(1, 5, 'C', 'D');

/* insertando datos en la tabla reserva */

INSERT INTO public.reserva (id, destino_id, horario_vuelo_id) VALUES
(1, 1, 1),
(2, 1, 1);

/* insertando datos en la tabla reserva_asiento */

INSERT INTO public.reserva_asiento (asiento_id, reserva_id) VALUES
(1, 1),
(2, 2);

/* insertando datos en la tabla cliente_reserva */

INSERT INTO public.cliente_reserva (cliente_id, reserva_id) VALUES
(1, 1),
(1, 2);