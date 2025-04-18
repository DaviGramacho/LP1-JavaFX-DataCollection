package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class PessoaController {

    @FXML
    private TextField alturaPessoaTextField;

    @FXML
    private Button btn_enviar_info_pessoa;

    @FXML
    private TextField idadePessoaTextField;

    @FXML
    private TextField nomePessoaTextField;

    @FXML
    private TableColumn<?, ?> tblAltura;

    @FXML
    private TableColumn<?, ?> tblIdade;

    @FXML
    private TableColumn<?, ?> tblNome;

    @FXML
    private TableView<?> tblView;

    @FXML
    void voltarPrincipalPessoa(ActionEvent event) {
        try {
            // Altera a cena para a tela do livro
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}