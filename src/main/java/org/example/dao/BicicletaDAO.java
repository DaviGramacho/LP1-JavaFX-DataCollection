package org.example.dao;

import org.example.database.Conexao;
import org.example.model.Bicicleta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BicicletaDAO {

    public List<Bicicleta> listarBicicleta() {
        List<Bicicleta> lista = new ArrayList<>();
        String sql = "SELECT id, marca, velocidadeAtual, marchaAtual FROM bicicleta";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bicicleta b = new Bicicleta(
                        rs.getInt("id"),
                        rs.getString("marca"),
                        rs.getInt("velocidadeAtual"),
                        rs.getInt("marchaAtual")
                );
                lista.add(b);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }

    public void adicionarInformacaoBicicleta(Bicicleta bicicleta) {
        String sql = "INSERT INTO bicicleta (marca, velocidadeAtual, marchaAtual) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, bicicleta.getMarca());
            stmt.setInt(2, bicicleta.getVelocidadeAtual());
            stmt.setInt(3, bicicleta.getMarchaAtual());

            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                bicicleta.setId(rs.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarBicicleta(Bicicleta bicicleta) {
        String sql = "UPDATE bicicleta SET marca = ?, velocidadeAtual = ?, marchaAtual = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, bicicleta.getMarca());
            stmt.setInt(2, bicicleta.getVelocidadeAtual());
            stmt.setInt(3, bicicleta.getMarchaAtual());
            stmt.setInt(4, bicicleta.getId());

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarBicicleta(Bicicleta bicicleta) {
        String sql = "DELETE FROM bicicleta WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, bicicleta.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
