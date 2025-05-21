package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.App;
import org.example.model.Televisao;

public class TelevisaoController {

    @FXML
    private Button btn_enviar_info_televisao;

    @FXML
    private TextField canalTelevisaoTextField;

    @FXML
    private TextField marcaTelevisaoTextField;

    @FXML
    private TextField tamanhoTelevisaoTextField;

    @FXML
    private ComboBox<String> televisaoStatusComboBox;

    @FXML
    private TableColumn<Televisao, String> tblMarca;

    @FXML
    private TableColumn<Televisao, String> tblTamanho;

    @FXML
    private TableColumn<Televisao, String> tblStatus;

    @FXML
    private TableView<Televisao> tblView;

    private ObservableList<Televisao> listaTelevisao = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        televisaoStatusComboBox.getItems().addAll("Ligada", "Desligada");
        televisaoStatusComboBox.setValue("Ligada");

        // Mapeia as colunas com os valores do modelo
        tblMarca.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMarca()));
        tblTamanho.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getTamanhoTela())));
        tblStatus.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getLigado() ? "Ligada" : "Desligada"));

        tblView.setItems(listaTelevisao);
    }

    @FXML
    void voltarPrincipalTelevisao(ActionEvent event) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void enviarInfoTelevisao(ActionEvent event) {
        String marca = marcaTelevisaoTextField.getText();
        String tamanhoStr = tamanhoTelevisaoTextField.getText();
        String status = televisaoStatusComboBox.getValue();

        if (marca.isEmpty() || tamanhoStr.isEmpty() || status == null) {
            mostrarAlerta("Preencha todos os campos corretamente.");
            return;
        }

        try {
            double tamanho = Double.parseDouble(tamanhoStr);
            boolean ligada = status.equals("Ligada");

            Televisao novaTV = new Televisao(marca, tamanho, ligada);
            listaTelevisao.add(novaTV);

            marcaTelevisaoTextField.clear();
            tamanhoTelevisaoTextField.clear();
            televisaoStatusComboBox.setValue("Ligada");

        } catch (NumberFormatException e) {
            mostrarAlerta("Tamanho da tela deve ser um número válido.");
        }
    }

    @FXML
    void trocarCanal(ActionEvent event) {
        Televisao selecionada = tblView.getSelectionModel().getSelectedItem();
        String canalStr = canalTelevisaoTextField.getText();

        if (selecionada == null) {
            mostrarAlerta("Selecione uma televisão na tabela.");
            return;
        }

        if (canalStr.isEmpty()) {
            mostrarAlerta("Digite o número do canal.");
            return;
        }

        try {
            int novoCanal = Integer.parseInt(canalStr);
            selecionada.trocarCanal(novoCanal);
            mostrarAlerta("Canal alterado para " + novoCanal + " com sucesso!");
            canalTelevisaoTextField.clear();
        } catch (NumberFormatException e) {
            mostrarAlerta("Digite um número válido para o canal.");
        }
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
