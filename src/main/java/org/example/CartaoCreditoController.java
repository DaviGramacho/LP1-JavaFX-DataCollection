package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.CartaoCredito;

public class CartaoCreditoController {

    @FXML
    private Button btn_enviar_info_cartao_credito;

    @FXML
    private TextField faturaCartaoTextField;

    @FXML
    private TextField limiteCartaoTextField;

    @FXML
    private TextField numeroCartaoTextField;

    @FXML
    private ImageView imagemCartao;

    @FXML
    private TableColumn<CartaoCredito, String> tblNumeroCartao;
    @FXML
    private TableColumn<CartaoCredito, Double> tblFaturaCartao;
    @FXML
    private TableColumn<CartaoCredito, Double> tblLimeteCartao;

    @FXML
    private TableView<CartaoCredito> tblViewCartaoCredito;

    ObservableList<CartaoCredito> cartoes = FXCollections.observableArrayList();

    @FXML
    void enviarInfoCartaoCredito(ActionEvent event) {
        double fatura = Double.parseDouble(faturaCartaoTextField.getText());
        double limite = Double.parseDouble(limiteCartaoTextField.getText());
        String numero = numeroCartaoTextField.getText();

        // Cria o objeto CartaoCredito
        CartaoCredito cartaoCredito = new CartaoCredito(numero, fatura, limite);

        // Mostrar no console para teste
        cartaoCredito.mostrarCartao();

        // Adiciona o cartão à lista
        cartoes.add(cartaoCredito);
    }
    @FXML
    public void initialize() {
        tblNumeroCartao.setCellValueFactory(new PropertyValueFactory<>("numeroCartao"));
        tblFaturaCartao.setCellValueFactory(new PropertyValueFactory<>("faturaAtual"));
        tblLimeteCartao.setCellValueFactory(new PropertyValueFactory<>("limite"));

        tblViewCartaoCredito.setItems(cartoes);
    }


}