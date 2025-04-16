package org.example.Exercicios_Linguagem_Programacao.Segunda_Aula_11_03_2025.classes;

public class Televisao {
    private String marca;
    private double tamanhoTela;
    private boolean ligado;

    public Televisao(String marca, double tamanhoTela, boolean ligado){
        this.marca = marca;
        this.tamanhoTela = tamanhoTela;
        this.ligado = ligado;
    }

    public String getMarca(){
        return marca;
    }

    public double getTamanhoTela(){
        return tamanhoTela;
    }

    public boolean getLigado(){
        return ligado;
    }

    public void mostrarTelevisao(){
        System.out.println("Marca: " + marca);
        System.out.println("Tamanho tela: " + tamanhoTela);
        System.out.println("Ligado: " + ligado);
    }

    public static void main(String[]args){
        Televisao televisao = new Televisao("LG", 50, true);
        televisao.mostrarTelevisao();
    }

}
