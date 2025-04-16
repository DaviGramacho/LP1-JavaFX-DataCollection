package org.example.Exercicios_Linguagem_Programacao.Segunda_Aula_11_03_2025.classes;

public class Geladeira {
    private String marca;
    private int temperatura;
    private boolean ligado;

    public Geladeira(String marca, int temperatura, boolean ligado){
        this.marca = marca;
        this.temperatura = temperatura;
        this.ligado = ligado;
    }

    public String getMarca(){
        return marca;
    }

    public int getTemperatura(){
        return temperatura;
    }

    public boolean getLigado(){
        return ligado;
    }

    public void mostrarGeladeira(){
        System.out.println("Marca: " + marca);
        System.out.println("Temperatura: " + temperatura);
        System.out.println("Ligado: " + ligado);
    }

    public static void main(String[]args){
        Geladeira geladeira = new Geladeira ("LG", -50, true);
        geladeira.mostrarGeladeira();
    }

}
