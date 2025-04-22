package org.example.model;

public class Lampada {
    private String tipo;
    private int potencia;
    private String status;
    private int brilho;  // Adicionado o atributo de brilho

    public Lampada(String tipo, int potencia, String status, int brilho) {
        this.tipo = tipo;
        this.potencia = potencia;
        this.status = status;
        this.brilho = brilho;
    }

    public String getTipo() {
        return tipo;
    }

    public int getPotencia() {
        return potencia;
    }

    public String getStatus() {
        return status;
    }

    public int getBrilho() {
        return brilho;
    }

    public void setBrilho(int brilho) {
        this.brilho = brilho;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }
}
