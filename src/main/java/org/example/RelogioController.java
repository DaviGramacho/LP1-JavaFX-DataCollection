package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.Relogio;

public class RelogioController {

    @FXML private Button btnEnviarInfoRelogio;
    @FXML private Button btnEditarInfoRelogio;
    @FXML private TextField horaRelogioTextField;
    @FXML private TextField marcaRelogioTextField;
    @FXML private TextField minutosRelogioTextField;

    @FXML private TableView<Relogio> tblView;
    @FXML private TableColumn<Relogio, String> marcaColuna;
    @FXML private TableColumn<Relogio, String> horaColuna;
    @FXML private TableColumn<Relogio, String> minutosColuna;

    private final ObservableList<Relogio> relogios = FXCollections.observableArrayList();

    // Modo de edição (null = criação)
    private Relogio relogioEmEdicao = null;

    @FXML
    void initialize() {
        marcaColuna.setCellValueFactory(new PropertyValueFactory<>("marca"));
        horaColuna.setCellValueFactory(new PropertyValueFactory<>("hora"));
        minutosColuna.setCellValueFactory(new PropertyValueFactory<>("minutos"));
        tblView.setItems(relogios);
    }

    @FXML
    void voltarPrincipalRelogio(ActionEvent event) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void enviarInfoRelogio(ActionEvent event) {
        String marca = marcaRelogioTextField.getText().trim();
        String hora = horaRelogioTextField.getText().trim();
        String minutos = minutosRelogioTextField.getText().trim();

        if (marca.isEmpty() || hora.isEmpty() || minutos.isEmpty()) {
            showAlert("Erro", "Preencha todos os campos.");
            return;
        }

        if (!isNumeric(hora) || !isNumeric(minutos)) {
            showAlert("Erro", "Hora e minutos devem ser números válidos.");
            return;
        }

        if (relogioEmEdicao == null) {
            // Criação
            Relogio novoRelogio = new Relogio(marca, hora, minutos);
            relogios.add(novoRelogio);
        } else {
            // Edição
            relogioEmEdicao.setMarca(marca);
            relogioEmEdicao.setHora(hora);
            relogioEmEdicao.setMinutos(minutos);
            tblView.refresh();
            relogioEmEdicao = null;
            btnEnviarInfoRelogio.setText("ENVIAR");
        }

        limparCampos();
    }

    @FXML
    void editarInfoRelogio(ActionEvent event) {
        Relogio selecionado = tblView.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            showAlert("Erro", "Selecione um relógio para editar.");
            return;
        }

        marcaRelogioTextField.setText(selecionado.getMarca());
        horaRelogioTextField.setText(selecionado.getHora());
        minutosRelogioTextField.setText(selecionado.getMinutos());

        relogioEmEdicao = selecionado;
        btnEnviarInfoRelogio.setText("ATUALIZAR");
    }

    private void limparCampos() {
        marcaRelogioTextField.clear();
        horaRelogioTextField.clear();
        minutosRelogioTextField.clear();
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    private void showAlert(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
