package org.example.model;

public class Relogio {
    private String marca;
    private String hora;
    private String minutos;

    public Relogio(String marca, String hora, String minutos) {
        this.marca = marca;
        this.hora = hora;
        this.minutos = minutos;
    }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getMinutos() { return minutos; }
    public void setMinutos(String minutos) { this.minutos = minutos; }
}
