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
import org.example.dao.LampadaDAO;
import org.example.model.Lampada;

import java.net.URL;
import java.util.ResourceBundle;

public class LampadaController implements Initializable {

    @FXML private Button btn_enviar_info_lampada;
    @FXML private ComboBox<String> lampadaStatusComboBox;
    @FXML private TextField potenciaLampadaTextField;
    @FXML private TextField tipoLampadaTextField;
    @FXML private Slider brilhoLampadaSlider;

    @FXML private TableView<Lampada> tblView;
    @FXML private TableColumn<Lampada, Integer> tblId;       // Coluna ID adicionada
    @FXML private TableColumn<Lampada, Integer> tblPotencia;
    @FXML private TableColumn<Lampada, String> tblStatus;
    @FXML private TableColumn<Lampada, String> tblTipo;
    @FXML private TableColumn<Lampada, Integer> tblBrilho;

    @FXML private Button btn_voltar_lampada;
    @FXML private Button btnAtualizarLampada;
    @FXML private Button btnDeletarLampada;

    private final ObservableList<Lampada> lampadas = FXCollections.observableArrayList();
    private final LampadaDAO dao = new LampadaDAO();
    private Lampada selecionada;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Popula ComboBox de status
        lampadaStatusComboBox.getItems().addAll("Ligada", "Desligada");
        lampadaStatusComboBox.setValue("Ligada");

        // Configura colunas da table, incluindo ID (oculta visualmente)
        tblId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblId.setVisible(false);  // Oculta coluna ID para o usuário

        tblPotencia.setCellValueFactory(new PropertyValueFactory<>("potencia"));
        tblStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tblTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        tblBrilho.setCellValueFactory(new PropertyValueFactory<>("brilho"));

        // Liga o ObservableList à TableView
        tblView.setItems(lampadas);

        // Carrega dados do banco
        lampadas.addAll(dao.listarLampadas());

        // Configuração do Slider (de 0 a 100)
        brilhoLampadaSlider.setMin(0);
        brilhoLampadaSlider.setMax(100);
        brilhoLampadaSlider.setValue(100);
        brilhoLampadaSlider.setBlockIncrement(1);

        // Quando selecionar uma lâmpada, preenche os campos
        tblView.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                selecionada = newSel;
                potenciaLampadaTextField.setText(String.valueOf(newSel.getPotencia()));
                tipoLampadaTextField.setText(newSel.getTipo());
                lampadaStatusComboBox.setValue(newSel.getStatus());
                brilhoLampadaSlider.setValue(newSel.getBrilho());
            }
        });
    }

    @FXML
    void enviarInfoLampada(ActionEvent event) {
        String potStr = potenciaLampadaTextField.getText().trim();
        String tipo = tipoLampadaTextField.getText().trim();
        String status = lampadaStatusComboBox.getValue();
        int brilho = (int) brilhoLampadaSlider.getValue();

        if (potStr.isEmpty() || tipo.isEmpty()) {
            showAlert("Erro", "Preencha todos os campos.");
            return;
        }

        int potencia;
        try {
            potencia = Integer.parseInt(potStr);
        } catch (NumberFormatException e) {
            showAlert("Erro", "Potência deve ser um número inteiro.");
            return;
        }

        Lampada nova = new Lampada(tipo, potencia, status, brilho);
        dao.adicionarLampada(nova);        // Persiste no BD e popula o ID no objeto
        lampadas.add(nova);                // Adiciona à lista em memória

        limparCampos();
        selecionada = null;
    }

    @FXML
    void atualizarBrilho(MouseEvent event) {
        if (selecionada != null) {
            int novoBrilho = (int) brilhoLampadaSlider.getValue();
            selecionada.setBrilho(novoBrilho);
            dao.atualizarLampada(selecionada);
            tblView.refresh();
        }
    }

    @FXML
    void atualizarLampada(ActionEvent event) {
        if (selecionada == null) {
            showAlert("Erro", "Selecione uma lâmpada para atualizar.");
            return;
        }

        String potStr = potenciaLampadaTextField.getText().trim();
        String tipo = tipoLampadaTextField.getText().trim();
        String status = lampadaStatusComboBox.getValue();
        int brilho = (int) brilhoLampadaSlider.getValue();

        if (potStr.isEmpty() || tipo.isEmpty()) {
            showAlert("Erro", "Preencha todos os campos para atualizar.");
            return;
        }

        int potencia;
        try {
            potencia = Integer.parseInt(potStr);
        } catch (NumberFormatException e) {
            showAlert("Erro", "Potência inválida. Use um número inteiro.");
            return;
        }

        selecionada.setPotencia(potencia);
        selecionada.setTipo(tipo);
        selecionada.setStatus(status);
        selecionada.setBrilho(brilho);

        dao.atualizarLampada(selecionada);
        tblView.refresh();

        limparCampos();
        selecionada = null;
    }

    @FXML
    void deletarLampada(ActionEvent event) {
        if (selecionada == null) {
            showAlert("Erro", "Selecione uma lâmpada para deletar.");
            return;
        }

        System.out.println("Deletando lâmpada com ID: " + selecionada.getId()); // DEBUG

        dao.deletarLampada(selecionada.getId());
        lampadas.remove(selecionada);
        tblView.refresh();

        limparCampos();
        selecionada = null;
    }

    @FXML
    void voltarPrincipalLampada(ActionEvent event) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void limparCampos() {
        potenciaLampadaTextField.clear();
        tipoLampadaTextField.clear();
        lampadaStatusComboBox.setValue("Ligada");
        brilhoLampadaSlider.setValue(100);
    }

    private void showAlert(String title, String msg) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(msg);
        a.showAndWait();
    }
}
