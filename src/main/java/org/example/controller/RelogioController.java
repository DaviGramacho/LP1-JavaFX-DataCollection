package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.dao.RelogioDAO;
import org.example.model.Relogio;

import java.sql.SQLException;
import java.util.List;

public class RelogioController {

    @FXML private Button btnEnviarInfoRelogio;
    @FXML private Button btnEditarInfoRelogio;
    @FXML private TextField horaRelogioTextField;
    @FXML private TextField marcaRelogioTextField;
    @FXML private TextField minutosRelogioTextField;

    @FXML private TableView<Relogio> tblView;
    @FXML private TableColumn<Relogio, String> marcaColuna;
    @FXML private TableColumn<Relogio, String> horaColuna;
    @FXML private TableColumn<Relogio, String> minutosColuna;

    private final ObservableList<Relogio> relogios = FXCollections.observableArrayList();
    private Relogio relogioEmEdicao = null;

    @FXML
    void initialize() {
        marcaColuna.setCellValueFactory(new PropertyValueFactory<>("marca"));
        horaColuna.setCellValueFactory(new PropertyValueFactory<>("hora"));
        minutosColuna.setCellValueFactory(new PropertyValueFactory<>("minuto"));  // singular

        tblView.setItems(relogios);

        carregarTabela();
    }

    private void carregarTabela() {
        try (RelogioDAO dao = new RelogioDAO()) {
            List<Relogio> lista = dao.listarTodos();
            relogios.setAll(lista);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erro", "Falha ao carregar dados da tabela.");
        }
    }

    @FXML
    void enviarInfoRelogio(ActionEvent event) {
        String marca = marcaRelogioTextField.getText().trim();
        String hora = horaRelogioTextField.getText().trim();
        String minuto = minutosRelogioTextField.getText().trim();  // singular

        if (marca.isEmpty() || hora.isEmpty() || minuto.isEmpty()) {
            showAlert("Erro", "Preencha todos os campos.");
            return;
        }

        if (!isNumeric(hora) || !isNumeric(minuto)) {
            showAlert("Erro", "Hora e minutos devem ser números válidos.");
            return;
        }

        try (RelogioDAO dao = new RelogioDAO()) {
            if (relogioEmEdicao == null) {
                Relogio novoRelogio = new Relogio(marca, hora, minuto);
                dao.inserir(novoRelogio);
                relogios.add(novoRelogio);
            } else {
                String marcaOriginal = relogioEmEdicao.getMarca();
                relogioEmEdicao.setMarca(marca);
                relogioEmEdicao.setHora(hora);
                relogioEmEdicao.setMinuto(minuto);
                dao.atualizar(relogioEmEdicao, marcaOriginal);
                tblView.refresh();
                relogioEmEdicao = null;
                btnEnviarInfoRelogio.setText("ENVIAR");
            }
            limparCampos();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erro", "Erro ao salvar no banco de dados.");
        }
    }

    @FXML
    void atualizarRelogio(ActionEvent event) {
        Relogio selecionado = tblView.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            showAlert("Erro", "Selecione um relógio para editar.");
            return;
        }

        marcaRelogioTextField.setText(selecionado.getMarca());
        horaRelogioTextField.setText(selecionado.getHora());
        minutosRelogioTextField.setText(selecionado.getMinuto());

        relogioEmEdicao = selecionado;
        btnEnviarInfoRelogio.setText("ATUALIZAR");
    }

    @FXML
    void deletarRelogio(ActionEvent event) {
        Relogio selecionado = tblView.getSelectionModel().getSelectedItem();

        if (selecionado == null) {
            showAlert("Erro", "Selecione um relógio para deletar.");
            return;
        }

        try (RelogioDAO dao = new RelogioDAO()) {
            dao.deletar(selecionado.getMarca());
            relogios.remove(selecionado);
            limparCampos();
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erro", "Erro ao deletar do banco de dados.");
        }
    }

    @FXML
    void voltarPrincipalRelogio(ActionEvent event) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
            showAlert("Erro", "Erro ao trocar de tela.");
        }
    }

    private void limparCampos() {
        marcaRelogioTextField.clear();
        horaRelogioTextField.clear();
        minutosRelogioTextField.clear();
        btnEnviarInfoRelogio.setText("ENVIAR");
        relogioEmEdicao = null;
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }

    private void showAlert(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
