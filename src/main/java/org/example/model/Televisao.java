package org.example.model;

public class Televisao {
    private String marca;
    private double tamanhoTela;
    private boolean ligado;
    private int canalAtual;

    public Televisao(String marca, double tamanhoTela, boolean ligado) {
        this.marca = marca;
        this.tamanhoTela = tamanhoTela;
        this.ligado = ligado;
        this.canalAtual = 1; // Canal padrão
    }

    public String getMarca() {
        return marca;
    }

    public double getTamanhoTela() {
        return tamanhoTela;
    }

    public boolean getLigado() {
        return ligado;
    }

    public int getCanalAtual() {
        return canalAtual;
    }

    public void trocarCanal(int novoCanal) {
        if (ligado && novoCanal > 0) {
            this.canalAtual = novoCanal;
        }
    }

    public void mostrarTelevisao() {
        System.out.println("Marca: " + marca);
        System.out.println("Tamanho tela: " + tamanhoTela);
        System.out.println("Ligado: " + ligado);
        System.out.println("Canal Atual: " + canalAtual);
    }
}
