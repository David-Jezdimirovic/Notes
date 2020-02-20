package com.metropolitan.beleske;

public class Sifra {

    private int sifra_id;
    private String sifra;

    public Sifra() {
    }

    public Sifra(int sifra_id, String sifra) {
        this.sifra_id = sifra_id;
        this.sifra = sifra;
    }

    public int getSifra_id() {
        return sifra_id;
    }

    public void setSifra_id(int sifra_id) {
        this.sifra_id = sifra_id;
    }

    public String getSifra() {
        return sifra;
    }

    public void setSifra(String sifra) {
        this.sifra = sifra;
    }

    @Override
    public String toString() {
        return "Sifra{" +
                "sifra_id=" + sifra_id +
                ", sifra='" + sifra + '\'' +
                '}';
    }
}
