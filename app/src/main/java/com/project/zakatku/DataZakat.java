package com.project.zakatku;

public class DataZakat {

    String kodePembayaran, nama, noKK, metodePembayaran, jumlahJiwa, nomBayar,statusPembayaran;

    public String getStatusPembayaran() {
        return statusPembayaran;
    }

    public void setStatusPembayaran(String statusPembayaran) {
        this.statusPembayaran = statusPembayaran;
    }
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getNoKK() {
        return noKK;
    }

    public void setNoKK(String noKK) {
        this.noKK = noKK;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
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

    public boolean isStatusBapak() {
        return statusBapak;
    }

    public void setStatusBapak(boolean statusBapak) {
        this.statusBapak = statusBapak;
    }

    public boolean isStatusIbu() {
        return statusIbu;
    }

    public void setStatusIbu(boolean statusIbu) {
        this.statusIbu = statusIbu;
    }

    boolean statusBapak, statusIbu;

    public DataZakat() {
    }

    public DataZakat( String nama, String noKK, String metodePembayaran, String jumlahJiwa, String nomBayar, boolean statusBapak, boolean statusIbu, String statusPembayaran) {
        this.nama = nama;
        this.noKK = noKK;
        this.metodePembayaran = metodePembayaran;
        this.jumlahJiwa = jumlahJiwa;
        this.nomBayar = nomBayar;
        this.statusBapak = statusBapak;
        this.statusIbu = statusIbu;
        this.statusPembayaran = statusPembayaran;
    }
}
