package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class RelogioController {

    @FXML
    private Button btn_enviar_info_relogio;

    @FXML
    private TextField horaRelogioTextField;

    @FXML
    private TextField marcaRelogioTextField;

    @FXML
    private TextField minutosRelogioTextField;

    @FXML
    void voltarPrincipalRelogio(ActionEvent event) {
        try {
            // Altera a cena para a tela do livro
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}