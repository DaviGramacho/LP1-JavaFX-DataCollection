package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.model.Geladeira;

public class GeladeiraController {

    @FXML
    private Button btn_enviar_info_geladeira;

    @FXML
    private Button btnAumentarTemp;

    @FXML
    private Button btnDiminuirTemp;

    @FXML
    private ComboBox<String> geladeiraStatusComboBox;

    @FXML
    private TextField marcaGeladeiraTextField;

    @FXML
    private TextField temperaturaGeladeiraTextField;

    @FXML
    private Label labelTemperatura;

    @FXML
    private TableView<Geladeira> tblView;

    @FXML
    private TableColumn<Geladeira, String> tblMarca;

    @FXML
    private TableColumn<Geladeira, Integer> tblTemp;

    @FXML
    private TableColumn<Geladeira, String> tblStatus;

    private final ObservableList<Geladeira> geladeiras = FXCollections.observableArrayList();
    private Geladeira ultimaGeladeira;

    @FXML
    public void initialize() {
        // Configura ComboBox de status
        geladeiraStatusComboBox.getItems().addAll("Ligada", "Desligada");
        geladeiraStatusComboBox.setValue("Ligada");

        // Configura colunas da tabela
        tblMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tblTemp.setCellValueFactory(new PropertyValueFactory<>("temperatura"));
        tblStatus.setCellValueFactory(new PropertyValueFactory<>("status"));

        tblView.setItems(geladeiras);

        // Atualiza a label inicial
        atualizarTemperaturaLabel();
    }

    @FXML
    void enviarInfoGeladeira(ActionEvent event) {
        String marca = marcaGeladeiraTextField.getText().trim();
        String status = geladeiraStatusComboBox.getValue();
        String tempStr = temperaturaGeladeiraTextField.getText().trim();

        if (marca.isEmpty() || tempStr.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        int temperatura;
        try {
            temperatura = Integer.parseInt(tempStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Temperatura inválida! Use um número inteiro.");
            return;
        }

        Geladeira geladeira = new Geladeira(marca, status, temperatura);
        geladeiras.add(geladeira);
        ultimaGeladeira = geladeira;

        marcaGeladeiraTextField.clear();
        temperaturaGeladeiraTextField.clear();
        geladeiraStatusComboBox.setValue("Ligada");

        atualizarTemperaturaLabel();
    }

    @FXML
    void aumentarTemperatura(ActionEvent event) {
        if (ultimaGeladeira != null) {
            ultimaGeladeira.setTemperatura(ultimaGeladeira.getTemperatura() + 1);
            tblView.refresh();
            atualizarTemperaturaLabel();
        } else {
            mostrarAlerta("Erro", "Cadastre uma geladeira primeiro.");
        }
    }

    @FXML
    void diminuirTemperatura(ActionEvent event) {
        if (ultimaGeladeira != null) {
            ultimaGeladeira.setTemperatura(ultimaGeladeira.getTemperatura() - 1);
            tblView.refresh();
            atualizarTemperaturaLabel();
        } else {
            mostrarAlerta("Erro", "Cadastre uma geladeira primeiro.");
        }
    }

    @FXML
    void voltarPrincipalGeladeira(ActionEvent event) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Erro ao voltar à tela principal.");
        }
    }

    private void atualizarTemperaturaLabel() {
        if (ultimaGeladeira != null) {
            labelTemperatura.setText("Temperatura Atual: " + ultimaGeladeira.getTemperatura() + "°C");
        } else {
            labelTemperatura.setText("Temperatura Atual: Indefinida");
        }
    }

    @FXML
    void atualizarGeladeira(ActionEvent event) {
        Geladeira selecionada = tblView.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            mostrarAlerta("Erro", "Selecione uma geladeira na tabela para atualizar.");
            return;
        }

        String novaMarca = marcaGeladeiraTextField.getText().trim();
        String novoStatus = geladeiraStatusComboBox.getValue();
        String novaTemperaturaStr = temperaturaGeladeiraTextField.getText().trim();

        if (novaMarca.isEmpty() || novaTemperaturaStr.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos para atualizar.");
            return;
        }

        int novaTemperatura;
        try {
            novaTemperatura = Integer.parseInt(novaTemperaturaStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Temperatura inválida. Use um número inteiro.");
            return;
        }

        selecionada.setMarca(novaMarca);
        selecionada.setStatus(novoStatus);
        selecionada.setTemperatura(novaTemperatura);

        tblView.refresh(); // Atualiza a tabela visualmente
        ultimaGeladeira = selecionada;
        atualizarTemperaturaLabel();

        marcaGeladeiraTextField.clear();
        temperaturaGeladeiraTextField.clear();
        geladeiraStatusComboBox.setValue("Ligada");
    }

    @FXML
    void deletarGeladeira(ActionEvent event) {
        Geladeira selecionada = tblView.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            mostrarAlerta("Erro", "Selecione uma geladeira para deletar.");
            return;
        }

        geladeiras.remove(selecionada);
        tblView.refresh();
        ultimaGeladeira = geladeiras.isEmpty() ? null : geladeiras.get(geladeiras.size() - 1);
        atualizarTemperaturaLabel();
    }


    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
