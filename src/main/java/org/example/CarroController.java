package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.Carro;
import javax.swing.text.html.ImageView;


public class CarroController {

    @FXML
    private TextField anoCarroTextField;

    @FXML
    private ImageView imgCarro;

    @FXML
    private Button btn_enviar_info_carro;

    @FXML
    private TextField marcaCarroTextField;

    @FXML
    private TextField modeloCarroTextField;

    @FXML
    private TableView<Carro> tblViewCarro;

    @FXML
    private TableColumn<Carro, Integer> tblAnoCarro;

    @FXML
    private TableColumn<Carro, String>  tblMarcaCarro;

    @FXML
    private TableColumn<Carro, String> tblModeloCarro;

    //cria uma lista observavel, destinada aos dados da classe carro
    ObservableList<Carro> carros = FXCollections.observableArrayList();

    @FXML
    void enviarInfoCarro(ActionEvent event) {

        String marca = marcaCarroTextField.getText();
        String modelo = modeloCarroTextField.getText();
        Integer ano = Integer.parseInt(anoCarroTextField.getText());


        Carro carro = new Carro (marca, modelo, ano);

        //mostrar no console para teste
        carro.mostrarCarro();

        carros.add(carro);

    }
    @FXML
    void initialize() {
        tblModeloCarro.setCellValueFactory(new PropertyValueFactory<>("modelo"));
        tblMarcaCarro.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tblAnoCarro.setCellValueFactory(new PropertyValueFactory<>("ano"));

        tblViewCarro.setItems(carros);
    }
}