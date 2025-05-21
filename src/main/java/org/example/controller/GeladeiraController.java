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
    private TableColumn<Geladeira, String> tblStatus;

    @FXML
    private TableColumn<Geladeira, Integer> tblTemp;

    private final ObservableList<Geladeira> geladeiras = FXCollections.observableArrayList();
    private Geladeira ultimaGeladeira; // Variável para controlar a última geladeira adicionada

    @FXML
    public void initialize() {
        // Popula ComboBox
        geladeiraStatusComboBox.getItems().addAll("Ligada", "Desligada");
        geladeiraStatusComboBox.setValue("Ligada");

        // Configura colunas da tabela
        tblMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tblStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblTemp.setCellValueFactory(new PropertyValueFactory<>("temperatura"));

        tblView.setItems(geladeiras);

        // Inicializa a temperatura na label
        atualizarTemperaturaLabel();
    }

    @FXML
    void enviarInfoGeladeira(ActionEvent event) {
        String marca  = marcaGeladeiraTextField.getText().trim();
        String status = geladeiraStatusComboBox.getValue();
        String tempStr= temperaturaGeladeiraTextField.getText().trim();

        if (marca.isEmpty() || tempStr.isEmpty()) {
            mostrarAlerta("Erro", "Preencha todos os campos.");
            return;
        }

        int temperatura;
        try {
            temperatura = Integer.parseInt(tempStr);
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Temperatura inválida! Digite um número inteiro.");
            return;
        }

        Geladeira g = new Geladeira(marca, status, temperatura);
        geladeiras.add(g);

        ultimaGeladeira = g;  // Atualiza a última geladeira enviada

        // Limpa campos
        marcaGeladeiraTextField.clear();
        temperaturaGeladeiraTextField.clear();
        geladeiraStatusComboBox.setValue("Ligada");

        // Atualiza a temperatura na label
        atualizarTemperaturaLabel();
    }

    @FXML
    void aumentarTemperatura(ActionEvent event) {
        if (ultimaGeladeira != null) {
            int novaTemperatura = ultimaGeladeira.getTemperatura() + 1;
            ultimaGeladeira.setTemperatura(novaTemperatura);  // Atualiza a temperatura da última geladeira

            // Atualiza a tabela
            tblView.refresh();

            // Atualiza a temperatura na label
            atualizarTemperaturaLabel();
        } else {
            mostrarAlerta("Erro", "Não há geladeira para alterar a temperatura.");
        }
    }

    @FXML
    void diminuirTemperatura(ActionEvent event) {
        if (ultimaGeladeira != null) {
            int novaTemperatura = ultimaGeladeira.getTemperatura() - 1;
            ultimaGeladeira.setTemperatura(novaTemperatura);  // Atualiza a temperatura da última geladeira

            // Atualiza a tabela
            tblView.refresh();

            // Atualiza a temperatura na label
            atualizarTemperaturaLabel();
        } else {
            mostrarAlerta("Erro", "Não há geladeira para alterar a temperatura.");
        }
    }

    @FXML
    void voltarPrincipalGeladeira(ActionEvent event) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro", "Não foi possível voltar para a tela principal.");
        }
    }

    // Função para atualizar a label da temperatura
    private void atualizarTemperaturaLabel() {
        if (ultimaGeladeira != null) {
            labelTemperatura.setText("Temperatura Atual: " + ultimaGeladeira.getTemperatura() + "°C");
        } else {
            labelTemperatura.setText("Temperatura Atual: Indefinida");
        }
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
