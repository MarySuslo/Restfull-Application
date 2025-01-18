
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

truncate table public.songs;
truncate table public.singers cascade;

INSERT INTO public.singers (id_singer, name_singer) VALUES (1, 'KingAndJocker');
INSERT INTO public.singers (id_singer, name_singer) VALUES (2, 'Windmill');
INSERT INTO public.singers (id_singer, name_singer) VALUES (3, 'Movie');
INSERT INTO public.singers (id_singer, name_singer) VALUES (4, 'Stray Kids');
INSERT INTO public.singers (id_singer, name_singer) VALUES (5, 'Skilet');
INSERT INTO public.singers (id_singer, name_singer) VALUES (6, 'Three Days Grace');
INSERT INTO public.singers (id_singer, name_singer) VALUES (7, 'Imagine Dragons');
INSERT INTO public.singers (id_singer, name_singer) VALUES (10, 'Green Day');
INSERT INTO public.singers (id_singer, name_singer) VALUES (11, 'Lindsy Stirlink');

INSERT INTO public.songs (id_song, name_song, singer) VALUES (1, 'Confession of a Vampire', 1);
INSERT INTO public.songs (id_song, name_song, singer) VALUES (2, 'Way of dream', 2);
INSERT INTO public.songs (id_song, name_song, singer) VALUES (3, 'Star of name Sun', 3);
INSERT INTO public.songs (id_song, name_song, singer) VALUES (4, 'Rom', 1);
INSERT INTO public.songs (id_song, name_song, singer) VALUES (5, 'Wild herbs', 2);
INSERT INTO public.songs (id_song, name_song, singer) VALUES (6, 'Miroh', 4);
INSERT INTO public.songs (id_song, name_song, singer) VALUES (7, 'Unpopular', 5);
INSERT INTO public.songs (id_song, name_song, singer) VALUES (8, 'Home', 6);
INSERT INTO public.songs (id_song, name_song, singer) VALUES (9, 'Blood group', 3);
INSERT INTO public.songs (id_song, name_song, singer) VALUES (10, 'Believer', 7);
INSERT INTO public.songs (id_song, name_song, singer) VALUES (11, 'Thunderous', 4);
INSERT INTO public.songs (id_song, name_song, singer) VALUES (13, 'American Idiot', 10);
INSERT INTO public.songs (id_song, name_song, singer) VALUES (16, 'Inner gold', 11);