package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.model.Carro;

public class CarroController {

    // Componentes da interface gráfica
    @FXML
    private TextField anoCarroTextField;

    @FXML
    private TextField marcaCarroTextField;

    @FXML
    private TextField modeloCarroTextField;

    @FXML
    private TableView<Carro> tblViewCarro;

    @FXML
    private TableColumn<Carro, Integer> tblAnoCarro;

    @FXML
    private TableColumn<Carro, String> tblMarcaCarro;

    @FXML
    private TableColumn<Carro, String> tblModeloCarro;

    @FXML
    private Label marchaLabel;

    // Lista observável para armazenar os carros
    ObservableList<Carro> carros = FXCollections.observableArrayList();

    // Variável para controlar o estado atual da marcha
    private String marchaAtual = "1"; // Inicia na primeira marcha

    @FXML
    public void aumentarMarcha() {
        switch (marchaAtual) {
            case "1":
                marchaAtual = "2";
                break;
            case "2":
                marchaAtual = "3";
                break;
            case "3":
                marchaAtual = "4";
                break;
            case "4":
                marchaAtual = "5";
                break;
            case "5":
                showAlert("Você já está na quinta marcha.");
                return;
            case "R":
                marchaAtual = "1";
                break;
        }
        atualizarLabel();
    }

    @FXML
    public void diminuirMarcha() {
        switch (marchaAtual) {
            case "5":
                marchaAtual = "4";
                break;
            case "4":
                marchaAtual = "3";
                break;
            case "3":
                marchaAtual = "2";
                break;
            case "2":
                marchaAtual = "1";
                break;
            case "1":
                marchaAtual = "R";
                break;
            case "R":
                showAlert("Você já está na ré.");
                return;
        }
        atualizarLabel();
    }

    private void atualizarLabel() {
        marchaLabel.setText("Marcha: " + marchaAtual);
    }

    private void showAlert(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ButtonType.CLOSE);
        alert.showAndWait();
    }

    @FXML
    void enviarInfoCarro(ActionEvent event) {
        try {
            String marca = marcaCarroTextField.getText().trim();
            String modelo = modeloCarroTextField.getText().trim();
            String anoText = anoCarroTextField.getText().trim();

            // Verificação de campos vazios
            if (marca.isEmpty() || modelo.isEmpty() || anoText.isEmpty()) {
                showAlert("Por favor, preencha todos os campos.");
                return;
            }

            // Verificação se o campo ano possui apenas números
            if (!anoText.matches("\\d+")) {
                showAlert("Erro: O campo 'Ano' deve conter apenas números.");
                return;
            }

            int ano = Integer.parseInt(anoText);

            Carro carro = new Carro(marca, modelo, ano);
            carros.add(carro);

            marcaCarroTextField.clear();
            modeloCarroTextField.clear();
            anoCarroTextField.clear();

            carro.mostrarCarro();
        } catch (Exception e) {
            showAlert("Erro ao adicionar o carro: " + e.getMessage());
        }
    }

    @FXML
    void initialize() {
        tblMarcaCarro.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tblAnoCarro.setCellValueFactory(new PropertyValueFactory<>("ano"));
        tblModeloCarro.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tblViewCarro.setItems(carros);
    }

    @FXML
    void voltarPrincipalCarro(ActionEvent event) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            showAlert("Erro ao voltar para a tela principal: " + e.getMessage());
        }
    }
}
