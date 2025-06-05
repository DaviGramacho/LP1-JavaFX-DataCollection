package org.example.model;

public class Televisao {

    private int id;               // campo id para banco de dados
    private String marca;
    private double tamanhoTela;
    private boolean ligado;
    private int canal;

    // Construtor padrão
    public Televisao() {
        this.id = 0;
        this.marca = "";
        this.tamanhoTela = 0.0;
        this.ligado = false;
        this.canal = 1; // canal inicial padrão
    }

    // Construtor com parâmetros (sem id, usado para inserir novo)
    public Televisao(String marca, double tamanhoTela, boolean ligado) {
        this.id = 0;
        this.marca = marca;
        this.tamanhoTela = tamanhoTela;
        this.ligado = ligado;
        this.canal = 1; // canal inicial padrão
    }

    // Getters e setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public double getTamanhoTela() {
        return tamanhoTela;
    }

    public void setTamanhoTela(double tamanhoTela) {
        this.tamanhoTela = tamanhoTela;
    }

    public boolean isLigado() {
        return ligado;
    }

    public void setLigado(boolean ligado) {
        this.ligado = ligado;
    }

    public int getCanal() {
        return canal;
    }

    public void setCanal(int canal) {
        if (canal > 0) {
            this.canal = canal;
        }
    }

    // Método para trocar canal
    public void trocarCanal(int novoCanal) {
        setCanal(novoCanal);
    }

    @Override
    public String toString() {
        return "Televisao{" +
                "id=" + id +
                ", marca='" + marca + '\'' +
                ", tamanhoTela=" + tamanhoTela +
                ", ligado=" + ligado +
                ", canal=" + canal +
                '}';
    }
}
