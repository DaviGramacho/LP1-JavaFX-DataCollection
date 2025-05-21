package org.example.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import org.example.App;
import org.example.model.Celular;

import java.net.URL;
import java.util.ResourceBundle;

public class CelularController implements Initializable {

    @FXML
    private TextField bateriaCelularTextField;

    @FXML
    private Button btn_voltar_celular;

    @FXML
    private TextField marcaCelularTextField;

    @FXML
    private TextField modeloCelularTextField;

    @FXML
    private Button btn_enviar_info_celular;

    @FXML
    private TableView<Celular> tblCelular;

    @FXML
    private TableColumn<Celular, String> tblMarcaCelular;

    @FXML
    private TableColumn<Celular, String> tblModeloCelular;

    @FXML
    private TableColumn<Celular, Integer> tblnivelCelular;

    @FXML
    private ProgressBar bateriaProgressBar;

    @FXML
    private Button btnUsar;

    @FXML
    private Button btnCarregar;

    @FXML
    private Label lblStatus;

    private double nivelBateria = 100;

    private final ObservableList<Celular> celulares = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblMarcaCelular.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tblModeloCelular.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tblnivelCelular.setCellValueFactory(new PropertyValueFactory<>("bateria"));
        tblCelular.setItems(celulares);

        // Inicializando o campo de bateria e barra de progresso
        bateriaCelularTextField.setText(String.valueOf((int) nivelBateria));
        atualizarBarra((int) nivelBateria);
        atualizarStatus((int) nivelBateria);  // Certifica que a label já inicia com o status correto

        // Listener para o campo de texto de bateria
        bateriaCelularTextField.textProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal.matches("\\d{0,3}")) {
                bateriaCelularTextField.setText(oldVal);
                return;
            }

            if (!newVal.isEmpty()) {
                try {
                    int valor = Integer.parseInt(newVal);
                    if (valor < 0 || valor > 100) {
                        bateriaCelularTextField.setStyle("-fx-prompt-text-fill: red;");
                        lblStatus.setText("Digite um valor entre 0 e 100");
                        lblStatus.setTextFill(Color.RED);
                    } else {
                        atualizarBarra(valor);
                        atualizarEstiloPlaceholder(valor);
                        atualizarStatus(valor);  // Atualiza o status da bateria
                    }
                } catch (NumberFormatException ex) {
                    bateriaCelularTextField.setText(oldVal);  // Se a conversão falhar, mantém o valor antigo
                }
            }
        });

        // Ações para os botões
        btnUsar.setOnAction(e -> usarBateria());
        btnCarregar.setOnAction(e -> carregarBateria());
    }

    private void atualizarBarra(int valor) {
        bateriaProgressBar.setProgress(valor / 100.0);
    }

    private void atualizarEstiloPlaceholder(int valor) {
        if (valor >= 70) {
            bateriaCelularTextField.setStyle("-fx-text-inner-color: green;");
        } else if (valor >= 30) {
            bateriaCelularTextField.setStyle("-fx-text-inner-color: orange;");
        } else {
            bateriaCelularTextField.setStyle("-fx-text-inner-color: red;");
        }
    }

    private void atualizarStatus(int valor) {
        if (valor >= 70) {
            lblStatus.setText("Bateria OK");
            lblStatus.setTextFill(Color.GREEN);
        } else if (valor >= 30) {
            lblStatus.setText("Bateria média");
            lblStatus.setTextFill(Color.ORANGE);
        } else {
            lblStatus.setText("Bateria baixa");
            lblStatus.setTextFill(Color.RED);
        }
    }

    private void usarBateria() {
        int valor = Integer.parseInt(bateriaCelularTextField.getText());
        if (valor >= 5) {
            valor -= 5;
            bateriaCelularTextField.setText(String.valueOf(valor));
            atualizarBarra(valor);
            atualizarStatus(valor);  // Atualiza o status após o uso
        }
    }

    private void carregarBateria() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(100), event -> {
                    int atual = Integer.parseInt(bateriaCelularTextField.getText());
                    if (atual < 100) {
                        atual += 1;
                        bateriaCelularTextField.setText(String.valueOf(atual));
                        atualizarBarra(atual);
                        atualizarStatus(atual);  // Atualiza o status durante o carregamento
                    }
                })
        );
        timeline.setCycleCount(100 - Integer.parseInt(bateriaCelularTextField.getText()));
        timeline.play();
    }

    @FXML
    void enviarInfoCelular(ActionEvent event) {
        String marca = marcaCelularTextField.getText().trim();
        String modelo = modeloCelularTextField.getText().trim();
        String bateria = bateriaCelularTextField.getText().trim();

        if (marca.isEmpty() || modelo.isEmpty() || bateria.isEmpty()) {
            showAlert("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }

        Celular celular = new Celular(marca, modelo, Integer.parseInt(bateria));
        celulares.add(celular); // Adiciona o celular à lista para exibição na tabela
    }

    @FXML
    void voltarPrincipalCelular(ActionEvent event) {
        try {
            App.setRoot("TelaPrincipal");  // Navega de volta para a tela principal
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erro", "Não foi possível voltar para a tela principal.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
