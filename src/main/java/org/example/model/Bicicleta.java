package org.example.model;

public class Bicicleta {
    private String marca;
    private int velocidadeAtual;
    private int marchaAtual;

    public Bicicleta(String marca, int velocidadeAtual, int marchaAtual) {
        this.marca = marca;
        this.velocidadeAtual = velocidadeAtual;
        this.marchaAtual = marchaAtual;
    }


    public String getMarca() {
        return marca;
    }

    public int getVelocidadeAtual() {
        return velocidadeAtual;
    }

    public int getMarchaAtual() {
        return marchaAtual;
    }

    public void mostarInformacoes() {
        System.out.println("Marca: " + marca);
        System.out.println("Velocidade: " + velocidadeAtual);
        System.out.println("Marcha: " + marchaAtual);
    }

    public static void main(String[] args) {
        Bicicleta bike = new Bicicleta("nike", 10, 4);
        bike.mostarInformacoes();
    }
}

