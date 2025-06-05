package org.example.model;

public class Carro {
    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private String marcha;

    public Carro(int id, String marca, String modelo, int ano, String marcha) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.marcha = marcha;
    }

    public Carro(String marca, String modelo, int ano, String marcha) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.marcha = marcha;
    }

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

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getMarcha() {
        return marcha;
    }

    public void setMarcha(String marcha) {
        this.marcha = marcha;
    }
}
