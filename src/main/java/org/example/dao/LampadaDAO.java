package org.example.dao;

import org.example.database.Conexao;
import org.example.model.Lampada;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LampadaDAO {

    // LISTAR TODAS AS LÂMPADAS
    public List<Lampada> listarLampadas() {
        List<Lampada> lampadas = new ArrayList<>();
        String sql = "SELECT id, tipo, potencia, status, brilho FROM lampada";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Lampada l = new Lampada(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getInt("potencia"),
                        rs.getString("status"),
                        rs.getInt("brilho")
                );
                lampadas.add(l);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar lâmpadas: " + e.getMessage());
        }

        return lampadas;
    }

    // INSERIR NOVA LÂMPADA
    public void adicionarLampada(Lampada lampada) {
        String sql = "INSERT INTO lampada (tipo, potencia, status, brilho) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, lampada.getTipo());
            stmt.setInt(2, lampada.getPotencia());
            stmt.setString(3, lampada.getStatus());
            stmt.setInt(4, lampada.getBrilho());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                lampada.setId(generatedKeys.getInt(1));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao adicionar lâmpada: " + e.getMessage());
        }
    }

    // ATUALIZAR LÂMPADA EXISTENTE
    public void atualizarLampada(Lampada lampada) {
        String sql = "UPDATE lampada SET tipo = ?, potencia = ?, status = ?, brilho = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, lampada.getTipo());
            stmt.setInt(2, lampada.getPotencia());
            stmt.setString(3, lampada.getStatus());
            stmt.setInt(4, lampada.getBrilho());
            stmt.setInt(5, lampada.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar lâmpada: " + e.getMessage());
        }
    }

    // EXCLUIR LÂMPADA PELO ID
    public void deletarLampada(int id) {
        String sql = "DELETE FROM lampada WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Erro ao excluir lâmpada: " + e.getMessage());
        }
    }
}
