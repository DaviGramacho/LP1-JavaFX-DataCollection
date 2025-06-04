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
import org.example.dao.CelularDAO;
import org.example.model.Celular;

import java.net.URL;
import java.util.ResourceBundle;

public class CelularController implements Initializable {

    @FXML private TextField bateriaCelularTextField;
    @FXML private TextField marcaCelularTextField;
    @FXML private TextField modeloCelularTextField;
    @FXML private Button btn_enviar_info_celular;
    @FXML private Button btn_voltar_celular;
    @FXML private TableView<Celular> tblCelular;
    @FXML private TableColumn<Celular, String> tblMarcaCelular;
    @FXML private TableColumn<Celular, String> tblModeloCelular;
    @FXML private TableColumn<Celular, Integer> tblnivelCelular;
    @FXML private ProgressBar bateriaProgressBar;
    @FXML private Button btnUsar;
    @FXML private Button btnCarregar;
    @FXML private Button btnAtualizar;
    @FXML private Button btnDeletar;
    @FXML private Label lblStatus;

    private final ObservableList<Celular> celulares = FXCollections.observableArrayList();
    private final CelularDAO dao = new CelularDAO();
    private Celular celularAtual;
    private double nivelBateria = 100;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configurar colunas da tabela
        tblMarcaCelular.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tblModeloCelular.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tblnivelCelular.setCellValueFactory(new PropertyValueFactory<>("bateria"));
        tblCelular.setItems(celulares);

        // Carregar todos os registros do banco
        celulares.addAll(dao.listarCelulares());

        // Inicializar campo de bateria / ProgressBar / Status
        bateriaCelularTextField.setText(String.valueOf((int) nivelBateria));
        atualizarBarra((int) nivelBateria);
        atualizarStatus((int) nivelBateria);

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
                        bateriaCelularTextField.setStyle("-fx-text-inner-color: red;");
                        lblStatus.setText("Digite um valor entre 0 e 100");
                        lblStatus.setTextFill(Color.RED);
                    } else {
                        atualizarBarra(valor);
                        atualizarEstiloPlaceholder(valor);
                        atualizarStatus(valor);
                    }
                } catch (NumberFormatException ex) {
                    bateriaCelularTextField.setText(oldVal);
                }
            }
        });

        // Quando selecionar uma linha na tabela, preenche os campos para edição
        tblCelular.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                celularAtual = newSel;
                marcaCelularTextField.setText(newSel.getMarca());
                modeloCelularTextField.setText(newSel.getModelo());
                bateriaCelularTextField.setText(String.valueOf(newSel.getBateria()));
                atualizarBarra(newSel.getBateria());
                atualizarStatus(newSel.getBateria());
            }
        });

        // Ações para os botões Usar e Carregar bateria
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
        if (celularAtual == null) {
            showAlert("Aviso", "Selecione um celular da tabela antes de usar.");
            return;
        }

        int valor = Integer.parseInt(bateriaCelularTextField.getText());
        if (valor >= 5) {
            valor -= 5;
            bateriaCelularTextField.setText(String.valueOf(valor));
            celularAtual.setBateria(valor);
            dao.atualizarCelular(celularAtual);
            tblCelular.refresh();
            atualizarBarra(valor);
            atualizarStatus(valor);
        }
    }

    private void carregarBateria() {
        if (celularAtual == null) {
            showAlert("Aviso", "Selecione um celular da tabela antes de carregar.");
            return;
        }

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.millis(50), event -> {
                    int atual = Integer.parseInt(bateriaCelularTextField.getText());
                    if (atual < 100) {
                        atual++;
                        bateriaCelularTextField.setText(String.valueOf(atual));
                        celularAtual.setBateria(atual);
                        dao.atualizarCelular(celularAtual);
                        tblCelular.refresh();
                        atualizarBarra(atual);
                        atualizarStatus(atual);
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
        String bateriaStr = bateriaCelularTextField.getText().trim();

        if (marca.isEmpty() || modelo.isEmpty() || bateriaStr.isEmpty()) {
            showAlert("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }

        int bateria;
        try {
            bateria = Integer.parseInt(bateriaStr);
        } catch (NumberFormatException ex) {
            showAlert("Erro", "Nível de bateria deve ser um número inteiro.");
            return;
        }
        if (bateria < 0 || bateria > 100) {
            showAlert("Erro", "Valor de bateria deve estar entre 0 e 100.");
            return;
        }

        // Cria novo Celular e salva no banco
        Celular novo = new Celular(marca, modelo, bateria);
        dao.adicionarCelular(novo);
        celulares.add(novo);

        marcaCelularTextField.clear();
        modeloCelularTextField.clear();
        bateriaCelularTextField.setText(String.valueOf(novo.getBateria()));
        atualizarBarra(novo.getBateria());
        atualizarStatus(novo.getBateria());
    }

    @FXML
    void atualizarCelular(ActionEvent event) {
        if (celularAtual == null) {
            showAlert("Erro", "Selecione um celular na tabela para atualizar.");
            return;
        }

        String marca = marcaCelularTextField.getText().trim();
        String modelo = modeloCelularTextField.getText().trim();
        String bateriaStr = bateriaCelularTextField.getText().trim();

        if (marca.isEmpty() || modelo.isEmpty() || bateriaStr.isEmpty()) {
            showAlert("Erro", "Todos os campos devem ser preenchidos para atualizar.");
            return;
        }

        int bateria;
        try {
            bateria = Integer.parseInt(bateriaStr);
        } catch (NumberFormatException ex) {
            showAlert("Erro", "Nível de bateria deve ser um número inteiro.");
            return;
        }
        if (bateria < 0 || bateria > 100) {
            showAlert("Erro", "Valor de bateria deve estar entre 0 e 100.");
            return;
        }

        // Atualiza dados no objeto e no banco
        celularAtual.setMarca(marca);
        celularAtual.setModelo(modelo);
        celularAtual.setBateria(bateria);
        dao.atualizarCelular(celularAtual);
        tblCelular.refresh();

        showAlert("Sucesso", "Celular atualizado com sucesso!");
        limparCampos();
    }

    @FXML
    void deletarCelular(ActionEvent event) {
        if (celularAtual == null) {
            showAlert("Erro", "Selecione um celular para deletar.");
            return;
        }

        dao.deletarCelular(celularAtual);
        celulares.remove(celularAtual);
        tblCelular.refresh();

        showAlert("Sucesso", "Celular removido com sucesso!");
        limparCampos();
    }

    @FXML
    void voltarPrincipalCelular(ActionEvent event) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erro", "Não foi possível voltar para a tela principal.");
        }
    }

    private void limparCampos() {
        marcaCelularTextField.clear();
        modeloCelularTextField.clear();
        bateriaCelularTextField.setText("0");
        atualizarBarra(0);
        atualizarStatus(0);
        tblCelular.getSelectionModel().clearSelection();
        celularAtual = null;
        nivelBateria = 0;
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
