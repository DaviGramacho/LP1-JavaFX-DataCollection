package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import org.example.App;
import org.example.model.Lampada;

import java.net.URL;
import java.util.ResourceBundle;

public class LampadaController implements Initializable {

    @FXML private Button btn_enviar_info_lampada;
    @FXML private ComboBox<String> lampadaStatusComboBox;
    @FXML private TextField potenciaLampadaTextField;
    @FXML private TextField tipoLampadaTextField;
    @FXML private Slider brilhoLampadaSlider;  // Slider para controlar o brilho

    @FXML private TableView<Lampada> tblView;
    @FXML private TableColumn<Lampada, String> tblTipo;
    @FXML private TableColumn<Lampada, Integer> tblPotencia;
    @FXML private TableColumn<Lampada, String> tblStatus;
    @FXML private TableColumn<Lampada, Integer> tblBrilho;  // Coluna para exibir o brilho

    private final ObservableList<Lampada> lampadas = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Popula ComboBox
        lampadaStatusComboBox.getItems().addAll("Ligada", "Desligada");
        lampadaStatusComboBox.setValue("Ligada");

        // Configura colunas da table
        tblTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tblPotencia.setCellValueFactory(new PropertyValueFactory<>("potencia"));
        tblStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblBrilho.setCellValueFactory(new PropertyValueFactory<>("brilho"));

        // Liga o ObservableList à TableView
        tblView.setItems(lampadas);

        // Configuração do Slider (de 0 a 100)
        brilhoLampadaSlider.setMin(0);
        brilhoLampadaSlider.setMax(100);
        brilhoLampadaSlider.setValue(100);  // valor inicial
        brilhoLampadaSlider.setBlockIncrement(1);
    }

    @FXML
    void enviarInfoLampada(ActionEvent event) {
        String tipo = tipoLampadaTextField.getText().trim();
        String pot = potenciaLampadaTextField.getText().trim();
        String status = lampadaStatusComboBox.getValue();
        int brilho = (int) brilhoLampadaSlider.getValue();  // Pega o valor do brilho

        if (tipo.isEmpty() || pot.isEmpty()) {
            showAlert("Erro", "Preencha todos os campos.");
            return;
        }

        int potencia;
        try {
            potencia = Integer.parseInt(pot);
        } catch (NumberFormatException e) {
            showAlert("Erro", "Potência deve ser um número.");
            return;
        }

        Lampada l = new Lampada(tipo, potencia, status, brilho);
        lampadas.add(l);  // Adiciona a lâmpada à lista, atualizando a tabela

        // limpa campos
        tipoLampadaTextField.clear();
        potenciaLampadaTextField.clear();
        lampadaStatusComboBox.setValue("Ligada");
        brilhoLampadaSlider.setValue(100);  // Reseta o brilho para 100
    }

    @FXML
    void atualizarBrilho(MouseEvent event) {
        // Atualiza o brilho da lâmpada selecionada
        Lampada selecionada = tblView.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            int novoBrilho = (int) brilhoLampadaSlider.getValue();
            selecionada.setBrilho(novoBrilho);
            tblView.refresh();  // Atualiza a tabela para refletir a mudança
        }
    }

    @FXML
    void voltarPrincipalLampada(ActionEvent event) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
