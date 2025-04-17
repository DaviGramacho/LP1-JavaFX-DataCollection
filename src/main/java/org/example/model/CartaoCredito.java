package org.example.model;

public class CartaoCredito {
    private String numeroCartao;
    private double limite;
    private double faturaAtual;

    public CartaoCredito(String numeroCartao, double limite, double fatutaAtual){
        this.numeroCartao = numeroCartao;
        this.limite = limite;
        this.faturaAtual = fatutaAtual;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public double getLimite() {
        return limite;
    }

    public double getFaturalAtual() {
        return faturaAtual;
    }

    public void mostrarCartao(){
        System.out.println("Numero cartao: " + numeroCartao);
        System.out.println("Limite: " + limite);
        System.out.println("Fatura atual: " + faturaAtual);
    }

    public static void main(String[] args) {
        CartaoCredito CartaoCredito = new CartaoCredito("9128 2312 2132 5462", 2500, 180);
        CartaoCredito.mostrarCartao();
    }
}
