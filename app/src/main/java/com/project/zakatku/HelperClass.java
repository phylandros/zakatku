package com.project.zakatku;

public class HelperClass {

    String nama, email, notel, username, password, conpassword, setrole;

    public HelperClass(String nama, String email, String notel, String username, String password, String setrole) {
        this.nama = nama;
        this.email = email;
        this.notel = notel;
        this.username = username;
        this.password = password;
        this.setrole = setrole;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getSetrole() {
        return setrole;
    }

    public void setSetrole(String setrole) {
        this.setrole = setrole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNotel() {
        return notel;
    }

    public void setNotel(String notel) {
        this.notel = notel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConpassword() {
        return conpassword;
    }

    public void setConpassword(String conpassword) {
        this.conpassword = conpassword;
    }

}
