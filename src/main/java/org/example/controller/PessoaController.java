package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.dao.PessoaDAO;
import org.example.model.Pessoa;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PessoaController implements Initializable {

    @FXML private TextField nomePessoaTextField;
    @FXML private TextField idadePessoaTextField;
    @FXML private TextField alturaPessoaTextField;
    @FXML private Button btn_enviar_info_pessoa;
    @FXML private Button btnAtualizarPessoa;
    @FXML private Button btnDeletarPessoa;
    @FXML private TableView<Pessoa> tblView;
    @FXML private TableColumn<Pessoa, String> tblNome;
    @FXML private TableColumn<Pessoa, Integer> tblIdade;
    @FXML private TableColumn<Pessoa, Double> tblAltura;

    private final ObservableList<Pessoa> pessoasObservable = FXCollections.observableArrayList();
    private final PessoaDAO pessoaDAO = new PessoaDAO();

    // Variável para armazenar o nome antigo (antes da edição)
    private String nomeAntigo = null;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tblNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tblIdade.setCellValueFactory(new PropertyValueFactory<>("idade"));
        tblAltura.setCellValueFactory(new PropertyValueFactory<>("altura"));

        carregarPessoas();
        tblView.setItems(pessoasObservable);

        // Atualiza os campos ao selecionar uma linha da tabela e guarda o nome antigo
        tblView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                nomePessoaTextField.setText(newSelection.getNome());
                idadePessoaTextField.setText(String.valueOf(newSelection.getIdade()));
                alturaPessoaTextField.setText(String.valueOf(newSelection.getAltura()));
                nomeAntigo = newSelection.getNome();
            }
        });
    }

    private void carregarPessoas() {
        List<Pessoa> lista = pessoaDAO.listar();
        pessoasObservable.setAll(lista);
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
        boolean inserido = pessoaDAO.adicionar(pessoa);
        if (inserido) {
            carregarPessoas();
            limparCampos();
            showAlertInfo("Sucesso", "Pessoa adicionada com sucesso!");
        } else {
            showAlert("Erro", "Erro ao adicionar pessoa.");
        }
    }

    @FXML
    void atualizarPessoa(ActionEvent event) {
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

        if (nomeAntigo == null) {
            showAlert("Erro", "Selecione uma pessoa para atualizar.");
            return;
        }

        Pessoa pessoaAtualizada = new Pessoa(nome, idade, altura);
        boolean atualizado = pessoaDAO.atualizarPessoa(pessoaAtualizada, nomeAntigo);
        if (atualizado) {
            carregarPessoas();
            limparCampos();
            showAlertInfo("Sucesso", "Pessoa atualizada com sucesso!");
            nomeAntigo = null;
        } else {
            showAlert("Erro", "Erro ao atualizar pessoa.");
        }
    }

    @FXML
    void deletarPessoa(ActionEvent event) {
        Pessoa pessoaSelecionada = tblView.getSelectionModel().getSelectedItem();
        if (pessoaSelecionada == null) {
            showAlert("Erro", "Selecione uma pessoa para deletar.");
            return;
        }

        boolean deletado = pessoaDAO.deletarPorNome(pessoaSelecionada.getNome());
        if (deletado) {
            carregarPessoas();
            limparCampos();
            showAlertInfo("Sucesso", "Pessoa deletada com sucesso!");
        } else {
            showAlert("Erro", "Erro ao deletar pessoa.");
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

    private void limparCampos() {
        nomePessoaTextField.clear();
        idadePessoaTextField.clear();
        alturaPessoaTextField.clear();
    }

    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }

    private void showAlertInfo(String title, String message) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }
}
