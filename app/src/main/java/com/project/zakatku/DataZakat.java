package com.project.zakatku;

public class DataZakat {

    String username, jumlahJiwa, nomBayar;

    public DataZakat(String username, String jumlahJiwa, String nomBayar) {
        this.username = username;
        this.jumlahJiwa = jumlahJiwa;
        this.nomBayar = nomBayar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getJumlahJiwa() {
        return jumlahJiwa;
    }

    public void setJumlahJiwa(String jumlahJiwa) {
        this.jumlahJiwa = jumlahJiwa;
    }

    public String getNomBayar() {
        return nomBayar;
    }

    public void setNomBayar(String nomBayar) {
        this.nomBayar = nomBayar;
    }

    public DataZakat(){

    }

}
