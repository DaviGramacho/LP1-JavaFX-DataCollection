package org.example.Exercicios_Linguagem_Programacao.Segunda_Aula_11_03_2025.classes;

public class Livro {
    private String titulo;
    private String autor;
    private int pagina;

    public Livro(String titulo, String autor, int pagina){
        this.titulo = titulo;
        this.autor = autor;
        this.pagina = pagina;
    }

    public String getTitulo(){
        return titulo;
    }

    public String getAutor(){
        return autor;
    }

    public int getPagina(){
        return pagina;
    }

    public void mostrarLivro(){
        System.out.println("Titulo: " + titulo);
        System.out.println("Autor: " + autor);
        System.out.println("Pagina" + pagina);
    }

    public static void main(String[]args){
        Livro livro = new Livro("o pequeno principe", "desconhecido", 77);
        livro.mostrarLivro();
    }
}
