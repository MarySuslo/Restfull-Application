
CREATE TABLE IF NOT EXISTS public.singers
(
    id_singer integer NOT NULL ,
    name_singer character varying(30)  NOT NULL,
    PRIMARY KEY (id_singer)
    );

CREATE TABLE IF NOT EXISTS songs
(
    id_song integer NOT NULL,
    name_song character varying(30) COLLATE pg_catalog."default" NOT NULL,
    singer integer NOT NULL,
    CONSTRAINT songs_pkey PRIMARY KEY (id_song),
    CONSTRAINT singer FOREIGN KEY (singer)
    REFERENCES public.singers (id_singer) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );
