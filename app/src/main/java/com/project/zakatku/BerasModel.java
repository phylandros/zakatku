package com.project.zakatku;

public class BerasModel {
    private String namaBeras;
    private String hargaBeras;
    private int gambarBeras; // Contoh: Gunakan ID resource gambar

    public BerasModel(String namaBeras, String hargaBeras, int gambarBeras) {
        this.namaBeras = namaBeras;
        this.hargaBeras = hargaBeras;
        this.gambarBeras = gambarBeras;
    }

    public String getNamaBeras() {
        return namaBeras;
    }

    public String getHargaBeras() {
        return hargaBeras;
    }

    public int getGambarBeras() {
        return gambarBeras;
    }
}

