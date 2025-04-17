package org.example;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
public class CelularController {

    @FXML
    private ImageView imgCelular;

    @FXML
    private TextField bateriaCelularTextField;

    @FXML
    private TextField marcaCelularTextField;

    @FXML
    private TextField modeloCelularTextField;

    @FXML
    private Button btn_enviar_info_celular;

    @FXML
    private TableView<?> tblCelular;

}
