package org.example.model;

public class Bicicleta {
    private static String marca;
    private static int velocidadeAtual;
    private static int  marchaAtual;

    public Bicicleta(String marca, int velocidadeAtual, int marchaAtual) {
        this.marca = marca;
        this.velocidadeAtual = velocidadeAtual;
        this.marchaAtual = marchaAtual;
    }

    public Bicicleta(int velocidade, int marcha, String marcaBicicleta) {
    }

    public static String getMarca() {
        return marca;
    }

    public static int getVelocidadeAtual() {
        return velocidadeAtual;
    }

    public void setVelocidadeAtual(int velocidadeAtual) {
        this.velocidadeAtual = velocidadeAtual;
    }

    public static int getMarchaAtual() {
        return marchaAtual;
    }

    public void setMarchaAtual(int marchaAtual) {
        this.marchaAtual = marchaAtual;
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
