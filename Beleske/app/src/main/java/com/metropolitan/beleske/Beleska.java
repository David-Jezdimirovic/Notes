package com.metropolitan.beleske;

public class Beleska {

    private int beleska_id;
    private String naslov;
    private String tekst;
    private String datum;
    private String datumkreirano;
    private String status;

    public Beleska(){

    }

    public Beleska(int beleska_id, String naslov, String tekst, String datum, String datumkreirano, String status) {
        this.beleska_id = beleska_id;
        this.naslov = naslov;
        this.tekst = tekst;
        this.datum = datum;
        this.datumkreirano = datumkreirano;
        this.status = status;
    }

    public int getBeleska_id() {
        return beleska_id;
    }

    public void setBeleska_id(int beleska_id) {
        this.beleska_id = beleska_id;
    }

    public String getNaslov() {
        return naslov;
    }

    public void setNaslov(String naslov) {
        this.naslov = naslov;
    }

    public String getTekst() {
        return tekst;
    }

    public void setTekst(String tekst) {
        this.tekst = tekst;
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

    public String getDatumkreirano() {
        return datumkreirano;
    }

    public void setDatumkreirano(String datumkreirano) {
        this.datumkreirano = datumkreirano;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return naslov + "  " + datum + "  " + status;

//        return "Beleska{" +
//                "beleska_id=" + beleska_id +
//                ", naslov='" + naslov + '\'' +
//                ", tekst='" + tekst + '\'' +
//                ", datum='" + datum + '\'' +
//                ", status='" + status + '\'' +
//                '}';
    }
}
