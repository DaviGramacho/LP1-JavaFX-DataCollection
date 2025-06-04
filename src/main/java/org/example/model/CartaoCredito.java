package org.example.model;

public class CartaoCredito {
    private String numero;
    private double limite;
    private double fatura;

    // Construtor vazio (para JavaFX ou frameworks)
    public CartaoCredito() {}

    // Construtor completo, sem id
    public CartaoCredito(String numero, double limite, double fatura) {
        this.numero = numero;
        this.limite = limite;
        this.fatura = fatura;
    }

    // Getters e Setters
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getLimite() {
        return limite;
    }
    public void setLimite(double limite) {
        this.limite = limite;
    }

    public double getFatura() {
        return fatura;
    }
    public void setFatura(double fatura) {
        this.fatura = fatura;
    }
}
