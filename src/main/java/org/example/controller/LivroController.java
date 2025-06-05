package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.model.Livro;

import java.net.URL;
import java.util.ResourceBundle;

public class LivroController implements Initializable {

    @FXML private TextField tituloLivroTextField;
    @FXML private TextField autorLivroTextField;
    @FXML private TextField paginaLivroTextField;
    @FXML private Button btn_enviar_info_livro;
    @FXML private Button btnPaginaAnterior;
    @FXML private Button btnProximaPagina;
    @FXML private Button btnDeletarLivro;
    @FXML private Button btnAtualizarLivro;
    @FXML private Label labelPagina;           // Exibe "Página X de Y"
    @FXML private TableView<Livro> tblView;
    @FXML private TableColumn<Livro, String> tblTitulo;
    @FXML private TableColumn<Livro, String> tblAutor;
    @FXML private TableColumn<Livro, Integer> tblTotal;
    @FXML private TableColumn<Livro, Integer> tblAtual;

    private final ObservableList<Livro> livros = FXCollections.observableArrayList();
    private Livro ultimoLivro;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // configura colunas
        tblTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));
        tblAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tblTotal.setCellValueFactory(new PropertyValueFactory<>("totalPaginas"));
        tblAtual.setCellValueFactory(new PropertyValueFactory<>("paginaAtual"));
        tblView.setItems(livros);

        labelPagina.setText("Página Atual: —");

        // Preencher campos ao selecionar um livro na tabela (opcional, mas ajuda na atualização)
        tblView.getSelectionModel().selectedItemProperty().addListener((obs, antigo, novo) -> {
            if (novo != null) {
                ultimoLivro = novo;
                preencherCampos(novo);
                atualizarLabelPagina();
            }
        });
    }

    @FXML
    void enviarInfoLivro(ActionEvent event) {
        String titulo = tituloLivroTextField.getText().trim();
        String autor   = autorLivroTextField.getText().trim();
        String totalS  = paginaLivroTextField.getText().trim();

        if (titulo.isEmpty() || autor.isEmpty() || totalS.isEmpty()) {
            showAlert("Erro", "Preencha todos os campos.");
            return;
        }

        int total;
        try {
            total = Integer.parseInt(totalS);
        } catch (NumberFormatException e) {
            showAlert("Erro", "Total de páginas deve ser um número.");
            return;
        }

        Livro livro = new Livro(titulo, autor, total);
        livros.add(livro);
        ultimoLivro = livro;

        // limpa campos
        tituloLivroTextField.clear();
        autorLivroTextField.clear();
        paginaLivroTextField.clear();

        atualizarLabelPagina();
    }

    @FXML
    void paginaAnterior(ActionEvent event) {
        if (ultimoLivro == null) {
            showAlert("Erro", "Nenhum livro selecionado.");
            return;
        }
        int atual = ultimoLivro.getPaginaAtual();
        if (atual > 0) {
            ultimoLivro.setPaginaAtual(atual - 1);
            tblView.refresh();
            atualizarLabelPagina();
        }
    }

    @FXML
    void proximaPagina(ActionEvent event) {
        if (ultimoLivro == null) {
            showAlert("Erro", "Nenhum livro selecionado.");
            return;
        }
        int atual = ultimoLivro.getPaginaAtual();
        if (atual < ultimoLivro.getTotalPaginas()) {
            ultimoLivro.setPaginaAtual(atual + 1);
            tblView.refresh();
            atualizarLabelPagina();
        }
    }

    @FXML
    void deletarLivro(ActionEvent event) {
        Livro selecionado = tblView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            showAlert("Erro", "Selecione um livro para deletar.");
            return;
        }
        livros.remove(selecionado);

        if (selecionado.equals(ultimoLivro)) {
            ultimoLivro = null;
            labelPagina.setText("Página Atual: —");
        }
    }

    @FXML
    void atualizarLivro(ActionEvent event) {
        Livro selecionado = tblView.getSelectionModel().getSelectedItem();
        if (selecionado == null) {
            showAlert("Erro", "Selecione um livro para atualizar.");
            return;
        }

        String novoTitulo = tituloLivroTextField.getText().trim();
        String novoAutor = autorLivroTextField.getText().trim();
        String totalS = paginaLivroTextField.getText().trim();

        if (novoTitulo.isEmpty() || novoAutor.isEmpty() || totalS.isEmpty()) {
            showAlert("Erro", "Preencha todos os campos para atualizar.");
            return;
        }

        int novoTotal;
        try {
            novoTotal = Integer.parseInt(totalS);
        } catch (NumberFormatException e) {
            showAlert("Erro", "Total de páginas deve ser um número.");
            return;
        }

        // Atualiza os campos do livro selecionado
        selecionado.setTitulo(novoTitulo);
        selecionado.setAutor(novoAutor);
        selecionado.setTotalPaginas(novoTotal);

        if (selecionado.getPaginaAtual() > novoTotal) {
            selecionado.setPaginaAtual(novoTotal);
        }

        tblView.refresh();

        if (selecionado.equals(ultimoLivro)) {
            atualizarLabelPagina();
        }

        // Limpa campos
        tituloLivroTextField.clear();
        autorLivroTextField.clear();
        paginaLivroTextField.clear();
    }

    @FXML
    void voltarPrincipalLivro(ActionEvent event) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void atualizarLabelPagina() {
        if (ultimoLivro != null) {
            labelPagina.setText(
                    "Página Atual: " +
                            ultimoLivro.getPaginaAtual() +
                            " de " + ultimoLivro.getTotalPaginas()
            );
        } else {
            labelPagina.setText("Página Atual: —");
        }
    }

    private void showAlert(String title, String message) {
        Alert a = new Alert(Alert.AlertType.ERROR);
        a.setTitle(title);
        a.setHeaderText(null);
        a.setContentText(message);
        a.showAndWait();
    }

    private void preencherCampos(Livro livro) {
        tituloLivroTextField.setText(livro.getTitulo());
        autorLivroTextField.setText(livro.getAutor());
        paginaLivroTextField.setText(String.valueOf(livro.getTotalPaginas()));
    }
}
