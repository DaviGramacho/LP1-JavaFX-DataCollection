package org.example.dao;

import org.example.database.Conexao;
import org.example.model.Televisao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelevisaoDAO {

    // Inserir nova TV
    public boolean inserir(Televisao tv) {
        String sql = "INSERT INTO televisao (marca_televisao, tamanho_tela, ligada_televisao, canal_atual) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tv.getMarca());
            stmt.setDouble(2, tv.getTamanhoTela());
            stmt.setBoolean(3, tv.isLigado());
            stmt.setInt(4, tv.getCanal());

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Atualizar TV existente
    public boolean atualizar(Televisao tv) {
        String sql = "UPDATE televisao SET marca_televisao = ?, tamanho_tela = ?, ligada_televisao = ?, canal_atual = ? WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, tv.getMarca());
            stmt.setDouble(2, tv.getTamanhoTela());
            stmt.setBoolean(3, tv.isLigado());
            stmt.setInt(4, tv.getCanal());
            stmt.setInt(5, tv.getId());

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Deletar TV pelo id
    public boolean deletar(int id) {
        String sql = "DELETE FROM televisao WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            int rowsDeleted = stmt.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Buscar TV pelo id
    public Televisao buscarPorId(int id) {
        String sql = "SELECT * FROM televisao WHERE id = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Televisao tv = new Televisao();
                tv.setId(rs.getInt("id"));
                tv.setMarca(rs.getString("marca_televisao"));
                tv.setTamanhoTela(rs.getDouble("tamanho_tela"));
                tv.setLigado(rs.getBoolean("ligada_televisao"));
                tv.setCanal(rs.getInt("canal_atual"));
                return tv;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Listar todas as TVs
    public List<Televisao> listarTodos() {
        List<Televisao> lista = new ArrayList<>();
        String sql = "SELECT * FROM televisao";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Televisao tv = new Televisao();
                tv.setId(rs.getInt("id"));
                tv.setMarca(rs.getString("marca_televisao"));
                tv.setTamanhoTela(rs.getDouble("tamanho_tela"));
                tv.setLigado(rs.getBoolean("ligada_televisao"));
                tv.setCanal(rs.getInt("canal_atual"));
                lista.add(tv);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
