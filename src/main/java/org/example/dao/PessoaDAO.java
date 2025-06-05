package org.example.dao;

import org.example.database.Conexao;
import org.example.model.Pessoa;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PessoaDAO {

    public List<Pessoa> listar() {
        List<Pessoa> pessoas = new ArrayList<>();
        String sql = "SELECT nome, idade, altura FROM pessoa";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String nome = rs.getString("nome");
                int idade = rs.getInt("idade");
                double altura = rs.getDouble("altura");
                pessoas.add(new Pessoa(nome, idade, altura));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pessoas;
    }

    public boolean adicionar(Pessoa pessoa) {
        String sql = "INSERT INTO pessoa (nome, idade, altura) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setInt(2, pessoa.getIdade());
            stmt.setDouble(3, pessoa.getAltura());

            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean atualizarPessoa(Pessoa pessoa, String nomeAntigo) {
        String sql = "UPDATE pessoa SET nome = ?, idade = ?, altura = ? WHERE nome = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, pessoa.getNome());
            stmt.setInt(2, pessoa.getIdade());
            stmt.setDouble(3, pessoa.getAltura());
            stmt.setString(4, nomeAntigo);

            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deletarPorNome(String nome) {
        String sql = "DELETE FROM pessoa WHERE nome = ?";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            int linhas = stmt.executeUpdate();
            return linhas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
