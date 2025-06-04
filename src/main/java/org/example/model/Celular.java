package org.example.model;

public class Celular {
    private int id;
    private String marca;
    private String modelo;
    private int bateria;

    public Celular() {}

    public Celular(int id, String marca, String modelo, int bateria) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.bateria = bateria;
    }

    public Celular(String marca, String modelo, int bateria) {
        this(0, marca, modelo, bateria);
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

    public String getModelo() {
        return modelo;
    }
    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getBateria() {
        return bateria;
    }
    public void setBateria(int bateria) {
        this.bateria = bateria;
    }
}
