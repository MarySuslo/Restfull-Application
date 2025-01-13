
CREATE TABLE IF NOT EXISTS public.singers
(
    id_singer integer NOT NULL,
    name_singer character varying(30)  NOT NULL,
    CONSTRAINT singers_pkey PRIMARY KEY (id_singer)
    );

CREATE TABLE IF NOT EXISTS public.songs
(
    id_song integer NOT NULL,
    name_song character varying(30) NOT NULL,
    singer integer NOT NULL,
    CONSTRAINT songs_pkey PRIMARY KEY (id_song),
    CONSTRAINT singer FOREIGN KEY (singer)
    REFERENCES public.singers (id_singer) MATCH SIMPLE
    ON UPDATE NO ACTION
    ON DELETE NO ACTION
    );



-- Вставка данных в таблицу singers
INSERT INTO singers (id_singer, name_singer) VALUES (1, 'KingAndJocker');
INSERT INTO singers (id_singer, name_singer) VALUES (2, 'Windmill');
INSERT INTO singers (id_singer, name_singer) VALUES (3, 'Movie');

-- Вставка данных в таблицу songs
INSERT INTO songs (id_song, name_song, singer) VALUES (1, 'Confession of a Vampire', 1);
INSERT INTO songs (id_song, name_song, singer) VALUES (2, 'Withards doll', 1);
INSERT INTO songs (id_song, name_song, singer) VALUES (3, 'Star of name Sun', 3);