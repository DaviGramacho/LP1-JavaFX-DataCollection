package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.model.Pessoa;

import java.net.URL;
import java.util.ResourceBundle;

public class PessoaController implements Initializable {

    @FXML private TextField nomePessoaTextField;
    @FXML private TextField idadePessoaTextField;
    @FXML private TextField alturaPessoaTextField;
    @FXML private Button btn_enviar_info_pessoa;
    @FXML private Button btn_atualizar_idade;
    @FXML private TableView<Pessoa> tblView;
    @FXML private TableColumn<Pessoa, String> tblNome;
    @FXML private TableColumn<Pessoa, Integer> tblIdade;
    @FXML private TableColumn<Pessoa, Double> tblAltura;

    private final ObservableList<Pessoa> pessoas = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Configura as colunas da tabela
        tblNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        tblAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));
        tblView.setItems(pessoas);
    }

    @FXML
    void enviarInfoPessoa(ActionEvent event) {
        String nome = nomePessoaTextField.getText().trim();
        String idadeS = idadePessoaTextField.getText().trim();
        String alturaS = alturaPessoaTextField.getText().trim();

        if (nome.isEmpty() || idadeS.isEmpty() || alturaS.isEmpty()) {
            showAlert("Erro", "Preencha todos os campos.");
            return;
        }

        int idade;
        double altura;
        try {
            idade = Integer.parseInt(idadeS);
            altura = Double.parseDouble(alturaS);
        } catch (NumberFormatException e) {
            showAlert("Erro", "Idade e Altura devem ser números válidos.");
            return;
        }

        Pessoa pessoa = new Pessoa(nome, idade, altura);
        pessoas.add(pessoa);

        // Limpa os campos após o envio
        nomePessoaTextField.clear();
        idadePessoaTextField.clear();
        alturaPessoaTextField.clear();
    }

    @FXML
    void atualizarIdadePessoa(ActionEvent event) {
        String nome = nomePessoaTextField.getText().trim();
        String novaIdadeS = idadePessoaTextField.getText().trim();

        if (nome.isEmpty() || novaIdadeS.isEmpty()) {
            showAlert("Erro", "Preencha o nome da pessoa e a nova idade.");
            return;
        }

        int novaIdade;
        try {
            novaIdade = Integer.parseInt(novaIdadeS);
        } catch (NumberFormatException e) {
            showAlert("Erro", "A idade deve ser um número válido.");
            return;
        }

        // Procura pela pessoa na lista
        Pessoa pessoaParaAtualizar = null;
        for (Pessoa pessoa : pessoas) {
            if (pessoa.getNome().equals(nome)) {
                pessoaParaAtualizar = pessoa;
                break;
            }
        }

        if (pessoaParaAtualizar != null) {
            // Atualiza a idade da pessoa encontrada
            pessoaParaAtualizar.setIdade(novaIdade);
            tblView.refresh(); // Atualiza a tabela para refletir a mudança
            showAlert("Sucesso", "Idade da pessoa atualizada!");
        } else {
            showAlert("Erro", "Pessoa não encontrada.");
        }
    }

    @FXML
    void voltarPrincipalPessoa(ActionEvent event) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }
}
