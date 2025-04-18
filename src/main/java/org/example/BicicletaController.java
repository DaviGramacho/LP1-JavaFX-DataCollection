package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.example.model.Bicicleta;


public class BicicletaController {

    @FXML
    private Button btn_enviar_info_bicicleta;

    @FXML
    private Button btn_voltar_bicicleta;

    @FXML
    private ImageView imagemBicicleta;

    @FXML
    private TextField marcaBicicletaTextField;

    @FXML
    private TextField marchaAtualBicicletaTextField;

    @FXML
    private TextField velocidadeAtualBicicletaTextField;

    @FXML
    private TableView<Bicicleta> tblView;

    @FXML
    private TableColumn<Bicicleta, String> tblMarca;

    @FXML
    private TableColumn<Bicicleta, Integer> tblMarcha;

    @FXML
    private TableColumn<Bicicleta, Integer> tblVel;

    //cria uma lista observavel, destinada aos dados da classe bicicleta
    ObservableList<Bicicleta> bicicletas = FXCollections.observableArrayList();

    @FXML
    void enviarInfoBicicleta(ActionEvent event) {

        String marca = marcaBicicletaTextField.getText();
        Integer velocidade = Integer.valueOf(velocidadeAtualBicicletaTextField.getText());
        Integer marcha = Integer.parseInt(marchaAtualBicicletaTextField.getText());


        Bicicleta bicicleta = new Bicicleta (marca, velocidade, marcha );

        //mostrar no console para teste
        bicicleta.mostarInformacoes();

        bicicletas.add(bicicleta);

    }
    @FXML
    void initialize() {
        tblMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tblMarcha.setCellValueFactory(new PropertyValueFactory<>("marchaAtual"));
        tblVel.setCellValueFactory(new PropertyValueFactory<>("velocidadeAtual"));

        tblView.setItems(bicicletas);
    }

    @FXML
    void voltarPrincipalBicicleta(ActionEvent event){
        try {
            // Altera a cena para a tela do livro
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
