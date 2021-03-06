package com.aek.yagoubi.sac;

import java.io.Serializable;

public class Sac  implements Serializable {
  private  int id;
   private int id_client;
    private String nom;
    private String code_bare;
    private int payee;

    private Float prix;
    private int qte;
    private String codeBareFormat;



    public Sac(int id, int id_client, String nom, String code_bare, String codeBareFormat, int payee, Float prix,int qte) {
        this.id = id;
        this.id_client = id_client;
        this.nom = nom;
        this.code_bare = code_bare;
        this.payee = payee;
      this.qte = qte;
        this.codeBareFormat = codeBareFormat;
        this.prix = prix;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }

    public int getQte() {
        return qte;
    }

    public String getCodeBareFormat() {
        return codeBareFormat;
    }

    public void setCodeBareFormat(String codeBareFormat) {
        this.codeBareFormat = codeBareFormat;
    }

    public String getNom() {
        return nom;
    }

    public String getCode_bare() {
        return code_bare;
    }

    public boolean isPayee() {
        if(payee == 1){
            return true;
        }else
        return false;
    }

    public Float getPrix() {
        return prix;
    }

    public int getId_client() {
        return id_client;
    }

    public int getId() {
        return id;
    }




    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCode_bare(String code_bare) {
        this.code_bare = code_bare;
    }

    public void setPayee(int payee) {
        this.payee = payee;
    }

    public void setPrix(Float prix) {
        this.prix = prix;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean CodeBareExist(){
        return !this.code_bare.equals("");
    }
}
