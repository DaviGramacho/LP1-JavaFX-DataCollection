package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class GeladeiraController {

    @FXML
    private Button btn_enviar_info_geladeira;

    @FXML
    private ComboBox<String> geladeiraStatusComboBox;

    @FXML
    private TextField marcaGeladeiraTextField;

    @FXML
    private TableColumn<?, ?> tblMarca;

    @FXML
    private TableColumn<?, ?> tblStatus;

    @FXML
    private TableColumn<?, ?> tblTemp;

    @FXML
    private TableView<?> tblView;

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
