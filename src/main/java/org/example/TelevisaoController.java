package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class TelevisaoController {

    @FXML
    private Button btn_enviar_info_geladeira;

    @FXML
    private TextField marcaTelevisaoTextField;

    @FXML
    private TextField tamanhoTelevisaoTextField;

    @FXML
    private ComboBox<String> televisaoStatusComboBox;

    @FXML
    public void initialize() {
        // Adiciona as opções ao ComboBox
        televisaoStatusComboBox.getItems().addAll("Ligada", "Desligada");

        // Define um valor padrão (opcional)
        televisaoStatusComboBox.setValue("Ligada");
    }
}
