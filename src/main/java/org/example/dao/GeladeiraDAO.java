package org.example.dao;

import org.example.database.Conexao;
import org.example.model.Geladeira;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GeladeiraDAO {

    public List<Geladeira> listarGeladeiras() {
        List<Geladeira> lista = new ArrayList<>();
        String sql = "SELECT marca_geladeira, status_geladeira, temperatura_geladeira FROM geladeira";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Geladeira g = new Geladeira(
                        rs.getString("marca_geladeira"),
                        rs.getString("status_geladeira"),
                        rs.getInt("temperatura_geladeira")
                );
                lista.add(g);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void adicionarGeladeira(Geladeira geladeira) {
        String sql = "INSERT INTO geladeira (marca_geladeira, status_geladeira, temperatura_geladeira) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, geladeira.getMarca());
            stmt.setString(2, geladeira.getStatus());
            stmt.setInt(3, geladeira.getTemperatura());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarGeladeira(Geladeira geladeira, String marcaAntiga) {
        String sql = "UPDATE geladeira SET marca_geladeira = ?, status_geladeira = ?, temperatura_geladeira = ? WHERE marca_geladeira = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, geladeira.getMarca());
            stmt.setString(2, geladeira.getStatus());
            stmt.setInt(3, geladeira.getTemperatura());
            stmt.setString(4, marcaAntiga);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarGeladeira(String marca) {
        String sql = "DELETE FROM geladeira WHERE marca_geladeira = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, marca);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
