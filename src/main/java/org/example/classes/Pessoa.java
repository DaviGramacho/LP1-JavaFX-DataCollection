package org.example.Exercicios_Linguagem_Programacao.Segunda_Aula_11_03_2025.classes;

public class Pessoa {
    private String nome;
    private int idade;
    private double altura;

    public Pessoa(String nome, int idade, double altura){
        this.nome = nome;
        this.idade = idade;
        this.altura = altura;
    }

    public String getNome(){
        return nome;
    }

    public int getIdade(){
        return idade;
    }

    public double getAltura(){
        return altura;
    }

    public void mostarPessoa(){
        System.out.println("Nome: " + nome);
        System.out.println("Idade: " + idade);
        System.out.println("Altura: " + altura);
    }

    public static void main(String[]args){
        Pessoa pessoa = new Pessoa("Davi", 18, 1.75);
        pessoa.mostarPessoa();
    }
}


