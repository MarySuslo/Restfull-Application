package org.example.model;

public class Songs {

    private int idSong;
    private String nameSong;
    private Singers singer;

    public Songs(int idSong, String nameSong, Singers singer) {
        this.idSong = idSong;
        this.nameSong = nameSong;
        this.singer = singer;
    }

    public int getIdSong() {
        return idSong;
    }


    public String getNameSong() {
        return nameSong;
    }

    public void setNameSong(String nameSong) {
        this.nameSong = nameSong;
    }

    public Singers getSinger() {
        return singer;
    }

    public void setSinger(Singers singer) {
        this.singer = singer;
    }

    @Override
    public String toString() {
        return "Song: {\n" +
                " id: " + idSong + ";\n" +
                " name: " + nameSong + ";\n" +
                " singer: " + singer.getNameSinger() + ";\n}";
    }

}
