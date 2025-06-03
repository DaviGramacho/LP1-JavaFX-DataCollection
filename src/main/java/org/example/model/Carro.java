package org.example.model;

public class Carro {
    private int id;
    private String marca;
    private String modelo;
    private int ano;
    private String marcha;

    // Construtor completo (usado para carregar do banco)
    public Carro(int id, String marca, String modelo, int ano, String marcha) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.marcha = marcha;
    }

    // Construtor sem ID (usado para inserir um novo carro)
    public Carro(String marca, String modelo, int ano, String marcha) {
        this.marca = marca;
        this.modelo = modelo;
        this.ano = ano;
        this.marcha = marcha;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getMarcha() {
        return marcha;
    }

    public void setMarcha(String marcha) {
        this.marcha = marcha;
    }

    // Método para exibir informações
    public void mostrarCarro() {
        System.out.println("ID: " + id);
        System.out.println("Marca: " + marca);
        System.out.println("Modelo: " + modelo);
        System.out.println("Ano: " + ano);
        System.out.println("Marcha: " + marcha);
    }

    // Método main para teste
    public static void main(String[] args) {
        Carro carro = new Carro("Chevrolet", "Onix", 2020, "5");
        carro.setId(1);
        carro.mostrarCarro();
    }
}
