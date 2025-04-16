package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class GeladeiraController {

    @FXML
    private Button btn_enviar_info_geladeira;

    @FXML
    private ComboBox<String> geladeiraStatusComboBox;

    @FXML
    private TextField marcaGeladeiraTextField;

    @FXML
    private TextField temperaturaGeladeiraTextField;

    @FXML
    public void initialize() {
        // Adiciona as opções ao ComboBox
        geladeiraStatusComboBox.getItems().addAll("Ligada", "Desligada");

        // Define um valor padrão (opcional)
        geladeiraStatusComboBox.setValue("Ligada");
    }
}
