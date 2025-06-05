package org.example.dao;

import org.example.database.Conexao;
import org.example.model.Relogio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RelogioDAO implements AutoCloseable {

    private Connection connection;

    public RelogioDAO() throws SQLException {
        this.connection = Conexao.conectar();
        if (this.connection == null) {
            throw new SQLException("Falha ao conectar ao banco de dados.");
        }
    }

    public void inserir(Relogio relogio) throws SQLException {
        String sql = "INSERT INTO relogio (marca, hora, minuto) VALUES (?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, relogio.getMarca());
            ps.setString(2, relogio.getHora());
            ps.setString(3, relogio.getMinuto());
            ps.executeUpdate();
        }
    }

    public List<Relogio> listarTodos() throws SQLException {
        List<Relogio> lista = new ArrayList<>();
        String sql = "SELECT marca, hora, minuto FROM relogio";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String marca = rs.getString("marca");
                String hora = rs.getString("hora");
                String minuto = rs.getString("minuto");
                Relogio r = new Relogio(marca, hora, minuto);
                lista.add(r);
            }
        }
        return lista;
    }

    public void atualizar(Relogio relogio, String marcaOriginal) throws SQLException {
        String sql = "UPDATE relogio SET marca = ?, hora = ?, minuto = ? WHERE marca = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, relogio.getMarca());
            ps.setString(2, relogio.getHora());
            ps.setString(3, relogio.getMinuto());
            ps.setString(4, marcaOriginal);
            ps.executeUpdate();
        }
    }

    public void deletar(String marca) throws SQLException {
        String sql = "DELETE FROM relogio WHERE marca = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, marca);
            ps.executeUpdate();
        }
    }

    @Override
    public void close() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}
