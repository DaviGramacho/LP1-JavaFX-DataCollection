package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TelaPrincipalController {

    @FXML
    private Button btn_bicicleta;

    @FXML
    private Button btn_carro;

    @FXML
    private Button btn_cartao_credito;

    @FXML
    private Button btn_celular;

    @FXML
    private Button btn_geladeira;

    @FXML
    private Button btn_lampada;

    @FXML
    private Button btn_livro;

    @FXML
    private Button btn_pessoa;

    @FXML
    private Button btn_relogio;

    @FXML
    private Button btn_televisao;

    @FXML
    void abrirTelaCarro(ActionEvent event) {
        try {
            // Altera a cena para a tela do carro
            App.setRoot("carro");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirTelaCartaoCredito(ActionEvent event) {
        try {
            // Altera a cena para a tela do cartao credito
            App.setRoot("cartaoCredito");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}