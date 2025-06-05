package org.example.dao;

import org.example.model.Livro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/seu_schema";
    private static final String USER = "seu_usuario";
    private static final String PASSWORD = "sua_senha";

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Inserir livro no banco
    public boolean inserir(Livro livro) {
        String sql = "INSERT INTO livro (titulo, autor, total_paginas, pagina_atual) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getTotalPaginas());
            stmt.setInt(4, livro.getPaginaAtual());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Buscar todos livros
    public List<Livro> listarTodos() {
        List<Livro> lista = new ArrayList<>();
        String sql = "SELECT titulo, autor, total_paginas, pagina_atual FROM livro";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Livro livro = new Livro(
                        rs.getString("titulo"),
                        rs.getString("autor"),
                        rs.getInt("total_paginas")
                );
                livro.setPaginaAtual(rs.getInt("pagina_atual"));
                lista.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Atualizar página atual do livro (por título)
    public boolean atualizarPaginaAtual(Livro livro) {
        String sql = "UPDATE livro SET pagina_atual = ? WHERE titulo = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, livro.getPaginaAtual());
            stmt.setString(2, livro.getTitulo());
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Deletar livro pelo título
    public boolean deletar(String titulo) {
        String sql = "DELETE FROM livro WHERE titulo = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, titulo);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
