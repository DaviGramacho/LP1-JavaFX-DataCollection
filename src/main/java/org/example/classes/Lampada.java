package org.example.Exercicios_Linguagem_Programacao.Segunda_Aula_11_03_2025.classes;

public class Lampada {
    private int potencia;
    private boolean ligado;
    private String tipo;

    public Lampada(int potencia, boolean ligado, String tipo){
        this.potencia = potencia;
        this.ligado = ligado;
        this.tipo = tipo;
    }

    public int getPotencia(){
        return potencia;
    }

    public boolean getLigado(){
        return ligado;
    }

    public String getTipo(){
        return tipo;
    }

    public void mostrarLampada(){
        System.out.println("Lampada: " + potencia);
        System.out.println("Ligado: " + ligado);
        System.out.println("Tipo: " + tipo);
    }

    public static void main(String[]args){
        Lampada lampada = new Lampada(0, true, "LED");
        lampada.mostrarLampada();
    }
}
