package org.example.dao;

import org.example.database.Conexao;
import org.example.model.Celular;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CelularDAO {

    public List<Celular> listarCelulares() {
        List<Celular> lista = new ArrayList<>();
        String sql = "SELECT id, marca_celular, modelo_celular, nivel_bateria FROM celular";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Celular c = new Celular(
                        rs.getInt("id"),
                        rs.getString("marca_celular"),
                        rs.getString("modelo_celular"),
                        rs.getInt("nivel_bateria")
                );
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void adicionarCelular(Celular celular) {
        String sql = "INSERT INTO celular (marca_celular, modelo_celular, nivel_bateria) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, celular.getMarca());
            stmt.setString(2, celular.getModelo());
            stmt.setInt(3, celular.getBateria());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                celular.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarCelular(Celular celular) {
        String sql = "UPDATE celular SET marca_celular = ?, modelo_celular = ?, nivel_bateria = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, celular.getMarca());
            stmt.setString(2, celular.getModelo());
            stmt.setInt(3, celular.getBateria());
            stmt.setInt(4, celular.getId());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarCelular(Celular celular) {
        String sql = "DELETE FROM celular WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, celular.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
