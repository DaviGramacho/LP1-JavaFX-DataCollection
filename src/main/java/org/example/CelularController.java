package org.example;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;

public class CelularController {

    @FXML
    private ImageView imgCelular;

    @FXML
    private TextField bateriaCelularTextField;

    @FXML
    private Button btn_voltar_celular;

    @FXML
    private TextField marcaCelularTextField;

    @FXML
    private TextField modeloCelularTextField;

    @FXML
    private Button btn_enviar_info_celular;

    @FXML
    private TableView<?> tblCelular;

    public void voltarPrincipalCelular(javafx.event.ActionEvent actionEvent) {
        try {
            // Altera a cena para a tela do livro
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
