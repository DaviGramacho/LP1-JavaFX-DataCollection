package org.example.Exercicios_Linguagem_Programacao.Segunda_Aula_11_03_2025.classes;

public class Celular {
    private String marca;
    private String modelo;
    private int bateria;

    public Celular(String marca, String modelo, int bateria){
        this.marca = marca;
        this.modelo = modelo;
        this.bateria = bateria;
    }

    public String getMarca(){
        return marca;
    }

    public String getModelo(){
        return modelo;
    }

    public int getBateria(){
        return bateria;
    }

    public void mostrarCelular(){
        System.out.println("Celular: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Bateria:" + bateria);
    }

    public static void main(String[]args){
        Celular celular = new Celular("Xiaomi", "redmi", 77);
        celular.mostrarCelular();
    }
}
