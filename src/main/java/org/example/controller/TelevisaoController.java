package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.App;
import org.example.dao.TelevisaoDAO;
import org.example.model.Televisao;

import java.util.List;

public class TelevisaoController {

    @FXML
    private Button btn_enviar_info_televisao;

    @FXML
    private Button btnAtualizarTelevisao;


    @FXML
    private TextField canalTelevisaoTextField;

    @FXML
    private TextField marcaTelevisaoTextField;

    @FXML
    private TextField tamanhoTelevisaoTextField;

    @FXML
    private ComboBox<String> televisaoStatusComboBox;

    @FXML
    private TableColumn<Televisao, String> tblMarca;

    @FXML
    private TableColumn<Televisao, String> tblTamanho;

    @FXML
    private TableColumn<Televisao, String> tblStatus;

    @FXML
    private TableView<Televisao> tblView;

    private ObservableList<Televisao> listaTelevisao = FXCollections.observableArrayList();

    private TelevisaoDAO televisaoDAO = new TelevisaoDAO();

    @FXML
    public void initialize() {
        televisaoStatusComboBox.getItems().addAll("Ligada", "Desligada");
        televisaoStatusComboBox.setValue("Ligada");

        tblMarca.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(cellData.getValue().getMarca())
        );

        tblTamanho.setCellValueFactory(cellData ->
                new javafx.beans.property.SimpleStringProperty(String.valueOf(cellData.getValue().getTamanhoTela()))
        );

        tblStatus.setCellValueFactory(cellData -> {
            String statusStr = cellData.getValue().isLigado() ? "Ligada" : "Desligada";
            return new javafx.beans.property.SimpleStringProperty(statusStr);
        });

        carregarTelevisoesDoBanco();
        tblView.setItems(listaTelevisao);
    }

    private void carregarTelevisoesDoBanco() {
        listaTelevisao.clear();
        List<Televisao> televisoes = televisaoDAO.listarTodos();
        listaTelevisao.addAll(televisoes);
    }

    @FXML
    void voltarPrincipalTelevisao(ActionEvent event) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void enviarInfoTelevisao(ActionEvent event) {
        String marca = marcaTelevisaoTextField.getText();
        String tamanhoStr = tamanhoTelevisaoTextField.getText();
        String status = televisaoStatusComboBox.getValue();

        if (marca.isEmpty() || tamanhoStr.isEmpty() || status == null) {
            mostrarAlerta("Preencha todos os campos corretamente.");
            return;
        }

        try {
            double tamanho = Double.parseDouble(tamanhoStr);
            boolean ligado = status.equalsIgnoreCase("Ligada");

            Televisao novaTV = new Televisao();
            novaTV.setMarca(marca);
            novaTV.setTamanhoTela(tamanho);
            novaTV.setLigado(ligado);

            boolean inseriu = televisaoDAO.inserir(novaTV);
            if (inseriu) {
                mostrarAlerta("Televisão cadastrada com sucesso!");
                limparCampos();
                carregarTelevisoesDoBanco();
            } else {
                mostrarAlerta("Erro ao cadastrar a televisão.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Tamanho da tela deve ser um número válido.");
        }
    }

    @FXML
    void trocarCanal(ActionEvent event) {
        Televisao selecionada = tblView.getSelectionModel().getSelectedItem();
        String canalStr = canalTelevisaoTextField.getText();

        if (selecionada == null) {
            mostrarAlerta("Selecione uma televisão na tabela.");
            return;
        }

        if (canalStr.isEmpty()) {
            mostrarAlerta("Digite o número do canal.");
            return;
        }

        try {
            int novoCanal = Integer.parseInt(canalStr);
            selecionada.trocarCanal(novoCanal);

            // Atualizar no banco
            boolean atualizou = televisaoDAO.atualizar(selecionada);
            if (atualizou) {
                mostrarAlerta("Canal alterado para " + novoCanal + " com sucesso!");
                carregarTelevisoesDoBanco();
                canalTelevisaoTextField.clear();
            } else {
                mostrarAlerta("Erro ao atualizar o canal no banco.");
            }

        } catch (NumberFormatException e) {
            mostrarAlerta("Digite um número válido para o canal.");
        }
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    private void limparCampos() {
        marcaTelevisaoTextField.clear();
        tamanhoTelevisaoTextField.clear();
        televisaoStatusComboBox.setValue("Ligada");
        canalTelevisaoTextField.clear();
    }

    public void atualizarTelevisao(ActionEvent event) {
        Televisao selecionada = tblView.getSelectionModel().getSelectedItem();
        if (selecionada == null) {
            mostrarAlerta("Selecione uma televisão na tabela para atualizar.");
            return;
        }

        String marca = marcaTelevisaoTextField.getText();
        String tamanhoStr = tamanhoTelevisaoTextField.getText();
        String status = televisaoStatusComboBox.getValue();

        if (marca.isEmpty() || tamanhoStr.isEmpty() || status == null) {
            mostrarAlerta("Preencha todos os campos corretamente.");
            return;
        }

        try {
            int tamanho = Integer.parseInt(tamanhoStr);
            boolean ligado = status.equalsIgnoreCase("Ligada");

            selecionada.setMarca(marca);
            selecionada.setTamanhoTela(tamanho);
            selecionada.setLigado(ligado);

            boolean atualizado = televisaoDAO.atualizar(selecionada);
            if (atualizado) {
                mostrarAlerta("Televisão atualizada com sucesso!");
                carregarTelevisoesDoBanco();
                limparCampos();
            } else {
                mostrarAlerta("Erro ao atualizar a televisão.");
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Tamanho da tela deve ser um número válido.");
        }
    }

    @FXML
    void deletarTelevisao(ActionEvent event) {
        Televisao selecionada = tblView.getSelectionModel().getSelectedItem();

        if (selecionada == null) {
            mostrarAlerta("Selecione uma televisão na tabela para deletar.");
            return;
        }

        boolean deletou = televisaoDAO.deletar(selecionada.getId());  // <-- aqui, pegar o ID

        if (deletou) {
            listaTelevisao.remove(selecionada);
            mostrarAlerta("Televisão deletada com sucesso!");
        } else {
            mostrarAlerta("Erro ao deletar a televisão.");
        }
    }


}
