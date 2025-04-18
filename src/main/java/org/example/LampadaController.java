package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class LampadaController {

    @FXML
    private Button btn_enviar_info_lampada;

    @FXML
    private ImageView imgLampada;

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
    private TableColumn<?, ?> tblPotencia;

    @FXML
    private TableColumn<?, ?> tblStatus;

    @FXML
    private TableColumn<?, ?> tblTipo;

    @FXML
    private TableView<?> tblView;

    @FXML
    private TextField tipoLampadaTextField;

    @FXML
    void voltarPrincipalLampada(ActionEvent event) {
        try {
            // Altera a cena para a tela do livro
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
