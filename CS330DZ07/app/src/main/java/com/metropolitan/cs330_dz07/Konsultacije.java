package com.metropolitan.cs330_dz07;

import java.io.Serializable;

public class Konsultacije implements Serializable{

    private int sifra_predmeta;
    private String naziv_predmeta;
    private String rad_sa_studentima;

    public Konsultacije() {
    }

    public Konsultacije(int sifra_predmeta, String naziv_predmeta, String rad_sa_studentima) {
        this.sifra_predmeta = sifra_predmeta;
        this.naziv_predmeta = naziv_predmeta;
        this.rad_sa_studentima = rad_sa_studentima;
    }

    public int getSifra_predmeta() {
        return sifra_predmeta;
    }

    public void setSifra_predmeta(int sifra_predmeta) {

        this.sifra_predmeta = sifra_predmeta;
    }

    public String getNaziv_predmeta() {

        return naziv_predmeta;
    }

    public void setNaziv_predmeta(String naziv_predmeta) {

        this.naziv_predmeta = naziv_predmeta;
    }

    public String getRad_sa_studentima() {

        return rad_sa_studentima;
    }

    public void setRad_sa_studentima(String rad_sa_studentima) {
        this.rad_sa_studentima = rad_sa_studentima;
    }
}
