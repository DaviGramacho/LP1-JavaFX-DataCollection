package org.example.model;

public class Bicicleta {

    private int id;
    private String marca;
    private int velocidadeAtual;
    private int marchaAtual;

    public Bicicleta() {}

    public Bicicleta(int id, String marca, int velocidadeAtual, int marchaAtual) {
        this.id = id;
        this.marca = marca;
        this.velocidadeAtual = velocidadeAtual;
        this.marchaAtual = marchaAtual;
    }

    public Bicicleta(String marca, int velocidadeAtual, int marchaAtual) {
        this.marca = marca;
        this.velocidadeAtual = velocidadeAtual;
        this.marchaAtual = marchaAtual;
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

    public int getVelocidadeAtual() {
        return velocidadeAtual;
    }
    public void setVelocidadeAtual(int velocidadeAtual) {
        this.velocidadeAtual = velocidadeAtual;
    }

    public int getMarchaAtual() {
        return marchaAtual;
    }
    public void setMarchaAtual(int marchaAtual) {
        this.marchaAtual = marchaAtual;
    }

    public void mostrarInformacoes() {
        System.out.println("Bicicleta: " + marca + ", Velocidade: " + velocidadeAtual + ", Marcha: " + marchaAtual);
    }
}
