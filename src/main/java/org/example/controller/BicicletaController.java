package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.dao.BicicletaDAO;
import org.example.model.Bicicleta;

import java.io.IOException;

public class BicicletaController {

    @FXML private TextField marcaBicicletaTextField;
    @FXML private TextField marchaAtualBicicletaTextField;
    @FXML private TextField velocidadeAtualBicicletaTextField;
    @FXML private TableView<Bicicleta> tblView;
    @FXML private TableColumn<Bicicleta, String> tblMarca;
    @FXML private TableColumn<Bicicleta, Integer> tblMarcha;
    @FXML private TableColumn<Bicicleta, Integer> tblVel;
    @FXML private Label lblVelocidadeAtual;

    private final BicicletaDAO bicicletaDAO = new BicicletaDAO();
    private final ObservableList<Bicicleta> bicicletas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        configurarTabela();
        bicicletas.addAll(bicicletaDAO.listarBicicleta());
        tblView.setItems(bicicletas);

        // Adiciona listener para popular os campos ao selecionar uma bicicleta
        tblView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                marcaBicicletaTextField.setText(newSelection.getMarca());
                velocidadeAtualBicicletaTextField.setText(String.valueOf(newSelection.getVelocidadeAtual()));
                marchaAtualBicicletaTextField.setText(String.valueOf(newSelection.getMarchaAtual()));
                lblVelocidadeAtual.setText("Velocidade Atual: " + newSelection.getVelocidadeAtual() + " km/h");
            }
        });
    }

    private void configurarTabela() {
        tblMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tblMarcha.setCellValueFactory(new PropertyValueFactory<>("marchaAtual"));
        tblVel.setCellValueFactory(new PropertyValueFactory<>("velocidadeAtual"));
    }

    @FXML
    void enviarInfoBicicleta(ActionEvent event) {
        String marca = marcaBicicletaTextField.getText().trim();
        String velocidadeStr = velocidadeAtualBicicletaTextField.getText().trim();
        String marchaStr = marchaAtualBicicletaTextField.getText().trim();

        if (marca.isEmpty() || velocidadeStr.isEmpty() || marchaStr.isEmpty()) {
            showAlert("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }

        try {
            int velocidade = Integer.parseInt(velocidadeStr);
            int marcha = Integer.parseInt(marchaStr);

            if (velocidade < 0 || marcha < 0) {
                showAlert("Erro", "Velocidade e marcha não podem ser negativas.");
                return;
            }

            Bicicleta bicicleta = new Bicicleta(marca, velocidade, marcha);
            bicicletaDAO.adicionarInformacaoBicicleta(bicicleta);
            bicicletas.add(bicicleta);

            lblVelocidadeAtual.setText("Velocidade Atual: " + velocidade + " km/h");

            marcaBicicletaTextField.clear();
            velocidadeAtualBicicletaTextField.clear();
            marchaAtualBicicletaTextField.clear();

        } catch (NumberFormatException e) {
            showAlert("Erro", "Velocidade e marcha devem ser números inteiros válidos.");
        }
    }

    @FXML
    void pedalarBicicleta(ActionEvent event) {
        if (!bicicletas.isEmpty()) {
            Bicicleta ultima = bicicletas.get(bicicletas.size() - 1);
            int novaVelocidade = ultima.getVelocidadeAtual() + 1;
            ultima.setVelocidadeAtual(novaVelocidade);
            tblView.refresh();

            lblVelocidadeAtual.setText("Velocidade Atual: " + novaVelocidade + " km/h");
        } else {
            showAlert("Aviso", "Nenhuma bicicleta cadastrada.");
        }
    }

    @FXML
    void atualizarBicicleta(ActionEvent event) {
        Bicicleta selecionada = tblView.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            showAlert("Aviso", "Selecione uma bicicleta para atualizar.");
            return;
        }

        String novaMarca = marcaBicicletaTextField.getText().trim();
        String novaVelStr = velocidadeAtualBicicletaTextField.getText().trim();
        String novaMarchaStr = marchaAtualBicicletaTextField.getText().trim();

        if (novaMarca.isEmpty() || novaVelStr.isEmpty() || novaMarchaStr.isEmpty()) {
            showAlert("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }

        try {
            int novaVel = Integer.parseInt(novaVelStr);
            int novaMarcha = Integer.parseInt(novaMarchaStr);

            if (novaVel < 0 || novaMarcha < 0) {
                showAlert("Erro", "Velocidade e marcha não podem ser negativas.");
                return;
            }

            selecionada.setMarca(novaMarca);
            selecionada.setVelocidadeAtual(novaVel);
            selecionada.setMarchaAtual(novaMarcha);

            bicicletaDAO.atualizarBicicleta(selecionada);

            tblView.refresh();
            lblVelocidadeAtual.setText("Velocidade Atual: " + novaVel + " km/h");

            marcaBicicletaTextField.clear();
            velocidadeAtualBicicletaTextField.clear();
            marchaAtualBicicletaTextField.clear();

        } catch (NumberFormatException e) {
            showAlert("Erro", "Velocidade e marcha devem ser números inteiros válidos.");
        }
    }

    @FXML
    void deletarBicicleta(ActionEvent event) {
        Bicicleta selecionada = tblView.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            showAlert("Aviso", "Selecione uma bicicleta para deletar.");
            return;
        }

        bicicletaDAO.deletarBicicleta(selecionada);

        bicicletas.remove(selecionada);
        tblView.refresh();

        lblVelocidadeAtual.setText("Velocidade Atual: 0 km/h");

        marcaBicicletaTextField.clear();
        velocidadeAtualBicicletaTextField.clear();
        marchaAtualBicicletaTextField.clear();
    }

    @FXML
    public void voltarPrincipalBicicleta(ActionEvent actionEvent) throws IOException {
        App.setRoot("TelaPrincipal");
    }

    private void showAlert(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
