package org.example.model;

import java.util.ArrayList;
import java.util.List;

public class Singers {

    private int idSinger;
    private String nameSinger;
    private List<Songs> songs;

    public Singers(int idSinger, String nameSinger) {
        this.idSinger = idSinger;
        this.nameSinger = nameSinger;
        this.songs = new ArrayList<Songs>();
    }

    public int getIdSinger() {
        return idSinger;
    }

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }

    public List<Songs> getSongs() {
        return songs;
    }

    public void setSongs(List<Songs> songs) {
        this.songs = songs;
    }

    @Override
    public String toString() {
        return "Singer: {\n" +
                " id: " + idSinger + ";\n" +
                " name: " + nameSinger + ";\n}";
    }

}
