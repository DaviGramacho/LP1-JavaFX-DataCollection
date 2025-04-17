package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class TelevisaoController {

    @FXML
    private Button btn_enviar_info_televisao;

    @FXML
    private TextField marcaTelevisaoTextField;

    @FXML
    private TextField tamanhoTelevisaoTextField;

    @FXML
    private TableColumn<?, ?> tblMarca;

    @FXML
    private TableColumn<?, ?> tblStatus;

    @FXML
    private TableColumn<?, ?> tblTamanho;

    @FXML
    private TableView<?> tblView;

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