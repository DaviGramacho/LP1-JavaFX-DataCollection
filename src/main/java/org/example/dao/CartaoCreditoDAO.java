package org.example.dao;

import org.example.database.Conexao;
import org.example.model.CartaoCredito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CartaoCreditoDAO {

    public List<CartaoCredito> listarCartoes() {
        List<CartaoCredito> lista = new ArrayList<>();
        String sql = "SELECT numero_cartao, limite_cartao, fatura_cartao FROM cartao_credito";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                CartaoCredito c = new CartaoCredito(
                        rs.getString("numero_cartao"),
                        rs.getDouble("limite_cartao"),
                        rs.getDouble("fatura_cartao")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void adicionarCartao(CartaoCredito cartao) {
        String sql = "INSERT INTO cartao_credito (numero_cartao, limite_cartao, fatura_cartao) VALUES (?, ?, ?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cartao.getNumero());
            stmt.setDouble(2, cartao.getLimite());
            stmt.setDouble(3, cartao.getFatura());
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarCartao(CartaoCredito cartao, String numeroAntigo) {
        String sql = "UPDATE cartao_credito "
                + "SET numero_cartao = ?, limite_cartao = ?, fatura_cartao = ? "
                + "WHERE numero_cartao = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cartao.getNumero());
            stmt.setDouble(2, cartao.getLimite());
            stmt.setDouble(3, cartao.getFatura());
            stmt.setString(4, numeroAntigo);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarCartao(String numero) {
        String sql = "DELETE FROM cartao_credito WHERE numero_cartao = ?";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, numero);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
