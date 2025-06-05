package org.example.model;

public class Lampada {
    private int id;
    private String tipo;
    private int potencia;
    private String status;
    private int brilho;

    // Construtor vazio
    public Lampada() {}

    // Construtor para objetos recuperados do banco (com id)
    public Lampada(int id, String tipo, int potencia, String status, int brilho) {
        this.id = id;
        this.tipo = tipo;
        this.potencia = potencia;
        this.status = status;
        this.brilho = brilho;
    }

    // Construtor para criar novo antes de persistir (id será gerado pelo banco)
    public Lampada(String tipo, int potencia, String status, int brilho) {
        this.tipo = tipo;
        this.potencia = potencia;
        this.status = status;
        this.brilho = brilho;
    }

    // Getters e Setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPotencia() {
        return potencia;
    }
    public void setPotencia(int potencia) {
        this.potencia = potencia;
    }

    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public int getBrilho() {
        return brilho;
    }
    public void setBrilho(int brilho) {
        this.brilho = brilho;
    }
}
