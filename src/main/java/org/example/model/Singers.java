package org.example.model;


public class Singers {

    private int idSinger;
    private String nameSinger;

    public Singers(int idSinger, String nameSinger) {
        this.idSinger = idSinger;
        this.nameSinger = nameSinger;
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

    @Override
    public String toString() {
        return "Singer: {\n" +
                " id: " + idSinger + ";\n" +
                " name: " + nameSinger + ";\n}";
    }

}
