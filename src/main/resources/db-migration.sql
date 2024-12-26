

ALTER TABLE IF EXISTS singers
    (
    id_song integer NOT NULL,
    name_song character varying(30) COLLATE pg_catalog."default" NOT NULL,
    singer integer NOT NULL,
    CONSTRAINT songs_pkey PRIMARY KEY (id_song),
    CONSTRAINT singer FOREIGN KEY (singer)
    REFERENCES public.singers (id_singer) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    NOT VALID
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
    NOT VALID
    );



insert into singers values (1,'KingAndJocker');
insert into singers values (2,'Windmill');
insert into singers values (3,'Movie');

insert into songs values (1,'Confession of a Vampire',1);
insert into songs values (2,'Withards doll',1);
insert into songs values (3,'Star of name Sun',3);

