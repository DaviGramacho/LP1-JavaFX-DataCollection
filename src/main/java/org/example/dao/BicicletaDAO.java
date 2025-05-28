package org.example.dao;


import org.example.database.Conexao;
import org.example.model.Bicicleta;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BicicletaDAO {
    public void adicionarInformacaoBicicleta (Bicicleta bicicleta){
        String sql = "INSERT INTO bicicleta (marca_bicicleta, velocidade, marcha) VALUES (?, ?, ?)";

        try (Connection con = Conexao.conectar();
             PreparedStatement stmt = con.prepareStatement(sql)) {

            stmt.setString(1, bicicleta.getMarca());
            stmt.setInt(2, bicicleta.getVelocidadeAtual());
            stmt.setInt(3, bicicleta.getMarchaAtual());

            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao gravar curso: " + e.getMessage());
        }
    }

    public List<Bicicleta> listarBicicleta() throws SQLException{
        String sql = "SELECT bicicleta (marca_bicicleta, marcha, velocidade)";
        List<Bicicleta> lista = new ArrayList<>();

        try (Connection con = Conexao.conectar();
            PreparedStatement stmt = con.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Bicicleta b = new Bicicleta(
                        rs.getInt("velocidade"),
                        rs.getInt("marcha"),
                        rs.getString("marca_bicicleta")

                );
                lista.add(b);

            }
        }
        return lista;

    }
}
