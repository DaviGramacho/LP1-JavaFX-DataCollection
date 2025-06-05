package org.example.dao;

import org.example.database.Conexao;
import org.example.model.Carro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CarroDAO {

    public List<Carro> listarCarros() {
        List<Carro> lista = new ArrayList<>();
        String sql = "SELECT id, marca_carro, modelo_carro, ano_carro, marcha_carro FROM carro";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Carro c = new Carro(
                        rs.getInt("id"),
                        rs.getString("marca_carro"),
                        rs.getString("modelo_carro"),
                        Integer.parseInt(rs.getString("ano_carro")),
                        rs.getString("marcha_carro")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void adicionarCarro(Carro carro) {
        String sql = "INSERT INTO carro (marca_carro, modelo_carro, ano_carro, marcha_carro) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, carro.getMarca());
            stmt.setString(2, carro.getModelo());
            stmt.setInt(3, carro.getAno());
            stmt.setString(4, carro.getMarcha());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                carro.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarCarro(Carro carro) {
        String sql = "UPDATE carro SET marca_carro = ?, modelo_carro = ?, ano_carro = ?, marcha_carro = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, carro.getMarca());
            stmt.setString(2, carro.getModelo());
            stmt.setInt(3, carro.getAno());
            stmt.setString(4, carro.getMarcha());
            stmt.setInt(5, carro.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarCarro(Carro carro) {
        String sql = "DELETE FROM carro WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, carro.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
