package org.example.model;

public class Relogio {
    private String marca;
    private String hora;
    private String minuto;  // singular, para bater com o banco

    public Relogio(String marca, String hora, String minuto) {
        this.marca = marca;
        this.hora = hora;
        this.minuto = minuto;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getMinuto() {
        return minuto;
    }

    public void setMinuto(String minuto) {
        this.minuto = minuto;
    }
}
