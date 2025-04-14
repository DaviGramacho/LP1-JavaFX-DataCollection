package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class LampadaController {

    @FXML
    private Button btn_enviar_info_lampada;

    @FXML
    private ComboBox<String> lampadaStatusComboBox;

    @FXML
    public void initialize() {
        // Adiciona as opções ao ComboBox
        lampadaStatusComboBox.getItems().addAll("Ligada", "Desligada");

        // Define um valor padrão (opcional)
        lampadaStatusComboBox.setValue("Ligada");
    }

    @FXML
    private TextField potenciaLampadaTextField;

    @FXML
    private TextField tipoLampadaTextField;

}
