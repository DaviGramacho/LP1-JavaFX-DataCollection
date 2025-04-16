package org.example.Exercicios_Linguagem_Programacao.Segunda_Aula_11_03_2025.classes;

public class Relogio {
    private String marca;
    private int horas;
    private int minutos;

    public Relogio(String marca, int horas, int minutos){
        this.marca = marca;
        this.horas = horas;
        this.minutos = minutos;
    }

    public String getMarca(){
        return marca;
    }

    public int getHoras(){
        return horas;
    }

    public int getMinutos(){
        return minutos;
    }

    public void mostrarRelogio(){
        System.out.println("Marca: " + marca);
        System.out.println("Horas: " + horas);
        System.out.println("Minutos: " + minutos);
    }

    public static void main(String[]args){
        Relogio relogio = new Relogio("Rolex", 13, 45);
        relogio.mostrarRelogio();
    }
}
