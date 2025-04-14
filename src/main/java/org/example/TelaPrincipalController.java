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
    void abrirTelaBicicleta(ActionEvent event) {
        try {
            // Altera a cena para a tela da bicicleta
            App.setRoot("bicicleta");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            // Altera a cena para a tela do cartao de credito
            App.setRoot("cartaoCredito");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirTelaCelular(ActionEvent event) {
        try {
            // Altera a cena para a tela do celular
            App.setRoot("celular");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirTelaGeladeira(ActionEvent event) {
        try {
            // Altera a cena para a tela da geladeira
            App.setRoot("geladeira");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirTelaLampada(ActionEvent event) {
        try {
            // Altera a cena para a tela da lampada
            App.setRoot("lampada");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirTelaLivro(ActionEvent event) {
        try {
            // Altera a cena para a tela do livro
            App.setRoot("livro");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirTelaPessoa(ActionEvent event) {
        try {
            // Altera a cena para a tela da pessoa
            App.setRoot("pessoa");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirTelaRelogio(ActionEvent event) {
        try {
            // Altera a cena para a tela do relogio
            App.setRoot("relogio");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void abrirTelaTelevisao(ActionEvent event) {
        try {
            // Altera a cena para a tela da televisao
            App.setRoot("televisao");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
