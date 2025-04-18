package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LivroController {

    @FXML
    private TextField autorLivroTextField;

    @FXML
    private Button btn_enviar_info_livro;

    @FXML
    private TextField paginaLivroTextField;

    @FXML
    private TextField tituloLivroTextField;

    @FXML
    void voltarPrincipalLivro(ActionEvent event) {
        try {
            // Altera a cena para a tela do livro
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
