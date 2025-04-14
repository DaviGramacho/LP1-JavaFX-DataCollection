package org.example.Exercicios_Linguagem_Programacao.Segunda_Aula_11_03_2025.classes;

public class Carro {
    private String marca;
    private String modelo;
    private int ano;

    public Carro(String marca, String modelo, int ano) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
    }
    public String getMarca() {
        return marca;
    }
    public String getModelo(){
        return modelo;
    }
    public int getAno(){
        return ano;
    }

    public void mostrarCarro(){
        System.out.println("Marca: " + marca);
        System.out.println("Modelo:" + modelo);
        System.out.println("Ano: " + ano);
    }

    public static void main(String[] args) {
        Carro carro = new Carro("Marca", "Modelo", 180);
        carro.mostrarCarro();
    }
}
