package com.aek.yagoubi.sac;

import java.io.Serializable;

public class Client implements Serializable {

    private int id ;
    private String nom;
    private String numeroTele;

    public Client(String nom, String numeroTele) {
        this.nom = nom;
        this.numeroTele = numeroTele;
    }

    public Client(int id,String nom, String numeroTele) {
        this.nom = nom;
        this.numeroTele = numeroTele;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public String getNumeroTele() {
        return numeroTele;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setNumeroTele(String numeroTele) {
        this.numeroTele = numeroTele;
    }
}
