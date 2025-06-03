package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.dao.CarroDAO;
import org.example.model.Carro;

public class CarroController {

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

    private ObservableList<Carro> carros = FXCollections.observableArrayList();
    private final CarroDAO carroDAO = new CarroDAO();

    private String marchaAtual = "1"; // Inicia na primeira marcha

    @FXML
    public void initialize() {
        tblMarcaCarro.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tblAnoCarro.setCellValueFactory(new PropertyValueFactory<>("ano"));
        tblModeloCarro.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tblViewCarro.setItems(carros);

        carros.addAll(carroDAO.listarCarros());

        tblViewCarro.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                marcaCarroTextField.setText(novo.getMarca());
                modeloCarroTextField.setText(novo.getModelo());
                anoCarroTextField.setText(String.valueOf(novo.getAno()));
                marchaAtual = novo.getMarcha();
                atualizarLabel();
            }
        });

        atualizarLabel();
    }

    @FXML
    void enviarInfoCarro(ActionEvent event) {
        if (!validarCampos()) return;

        String marca = marcaCarroTextField.getText().trim();
        String modelo = modeloCarroTextField.getText().trim();
        int ano = Integer.parseInt(anoCarroTextField.getText().trim());

        Carro carro = new Carro(0, marca, modelo, ano, marchaAtual);
        carroDAO.adicionarCarro(carro);
        carros.clear();
        carros.addAll(carroDAO.listarCarros());

        limparCampos();
    }

    @FXML
    void atualizarCarro(ActionEvent event) {
        Carro selecionado = tblViewCarro.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            showAlert("Selecione um carro para atualizar.");
            return;
        }

        if (!validarCampos()) return;

        selecionado.setMarca(marcaCarroTextField.getText().trim());
        selecionado.setModelo(modeloCarroTextField.getText().trim());
        selecionado.setAno(Integer.parseInt(anoCarroTextField.getText().trim()));
        selecionado.setMarcha(marchaAtual);

        carroDAO.atualizarCarro(selecionado);
        carros.clear();
        carros.addAll(carroDAO.listarCarros());

        limparCampos();
    }

    @FXML
    void deletarCarro(ActionEvent event) {
        Carro selecionado = tblViewCarro.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            showAlert("Selecione um carro para deletar.");
            return;
        }

        carroDAO.deletarCarro(selecionado);
        carros.clear();
        carros.addAll(carroDAO.listarCarros());

        limparCampos();
    }

    private boolean validarCampos() {
        String marca = marcaCarroTextField.getText().trim();
        String modelo = modeloCarroTextField.getText().trim();
        String anoText = anoCarroTextField.getText().trim();

        if (marca.isEmpty() || modelo.isEmpty() || anoText.isEmpty()) {
            showAlert("Por favor, preencha todos os campos.");
            return false;
        }

        if (!anoText.matches("\\d+")) {
            showAlert("Erro: O campo 'Ano' deve conter apenas números.");
            return false;
        }
        return true;
    }

    private void limparCampos() {
        marcaCarroTextField.clear();
        modeloCarroTextField.clear();
        anoCarroTextField.clear();
        tblViewCarro.getSelectionModel().clearSelection();
        marchaAtual = "1";
        atualizarLabel();
    }

    private void showAlert(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    // Marcha...

    @FXML
    public void aumentarMarcha() {
        switch (marchaAtual) {
            case "1": marchaAtual = "2"; break;
            case "2": marchaAtual = "3"; break;
            case "3": marchaAtual = "4"; break;
            case "4": marchaAtual = "5"; break;
            case "5": showAlert("Você já está na quinta marcha."); return;
            case "R": marchaAtual = "1"; break;
        }
        atualizarLabel();
    }

    @FXML
    public void diminuirMarcha() {
        switch (marchaAtual) {
            case "5": marchaAtual = "4"; break;
            case "4": marchaAtual = "3"; break;
            case "3": marchaAtual = "2"; break;
            case "2": marchaAtual = "1"; break;
            case "1": marchaAtual = "R"; break;
            case "R": showAlert("Você já está na ré."); return;
        }
        atualizarLabel();
    }

    private void atualizarLabel() {
        marchaLabel.setText("Marcha: " + marchaAtual);
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
