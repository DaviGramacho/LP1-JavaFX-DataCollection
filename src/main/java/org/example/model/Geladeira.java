package org.example.model;

public class Geladeira {
    private String marca;
    private String status;
    private int temperatura;

    public Geladeira(String marca, String status, int temperatura){
        this.marca = marca;
        this.status = status;
        this.temperatura = temperatura;
    }

    public String getMarca(){
        return marca;
    }

    public String getStatus(){
        return status;
    }

    public int getTemperatura(){
        return temperatura;
    }

    public void setTemperatura(int temperatura){
        this.temperatura = temperatura;
    }

    public void setMarca(String marca){
        this.marca = marca;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void mostrarGeladeira(){
        System.out.println("Marca: " + marca);
        System.out.println("Temperatura: " + temperatura);
        System.out.println("Status: " + status);
    }

    public static void main(String[]args){
        Geladeira geladeira = new Geladeira ("LG", "Ligada", -50);
        geladeira.mostrarGeladeira();
    }
}
