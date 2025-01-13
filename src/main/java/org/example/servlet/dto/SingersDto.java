package org.example.servlet.dto;

import org.example.model.Songs;

import java.util.ArrayList;
import java.util.List;

public class SingersDto {
    private int idSinger;
    private String nameSinger;
    private List<Songs> songs;

    public SingersDto(int idSinger, String nameSinger) {
        this.idSinger = idSinger;
        this.nameSinger = nameSinger;

        this.songs= new ArrayList<>();
    }

    public int getIdSinger() {
        return idSinger;
    }

    public void setIdSinger(int idSinger) {
        this.idSinger = idSinger;
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
        return "Singer: {\n" + " id: " + idSinger + ";\n" + " name: " + nameSinger + ";\n}";
    }
}
