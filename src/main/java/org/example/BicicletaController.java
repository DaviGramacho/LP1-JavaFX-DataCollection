package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.example.model.Bicicleta;

public class BicicletaController {

    @FXML private Button btn_enviar_info_bicicleta;
    @FXML private Button btn_voltar_bicicleta;
    @FXML private Button btn_pedalar;
    @FXML private ImageView imagemBicicleta;
    @FXML private TextField marcaBicicletaTextField;
    @FXML private TextField marchaAtualBicicletaTextField;
    @FXML private TextField velocidadeAtualBicicletaTextField;
    @FXML private TableView<Bicicleta> tblView;
    @FXML private TableColumn<Bicicleta, String> tblMarca;
    @FXML private TableColumn<Bicicleta, Integer> tblMarcha;
    @FXML private TableColumn<Bicicleta, Integer> tblVel;
    @FXML private Label lblVelocidadeAtual;

    private final ObservableList<Bicicleta> bicicletas = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        tblMarca.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tblMarcha.setCellValueFactory(new PropertyValueFactory<>("marchaAtual"));
        tblVel.setCellValueFactory(new PropertyValueFactory<>("velocidadeAtual"));
        tblView.setItems(bicicletas);

        // Inicializa a label com valor padrão
        lblVelocidadeAtual.setText("Velocidade Atual: 0 km/h");
    }

    @FXML
    void enviarInfoBicicleta(ActionEvent event) {
        String marca = marcaBicicletaTextField.getText().trim();
        String velocidadeStr = velocidadeAtualBicicletaTextField.getText().trim();
        String marchaStr = marchaAtualBicicletaTextField.getText().trim();

        if (marca.isEmpty() || velocidadeStr.isEmpty() || marchaStr.isEmpty()) {
            showAlert("Erro", "Todos os campos devem ser preenchidos.");
            return;
        }

        int velocidade, marcha;
        try {
            velocidade = Integer.parseInt(velocidadeStr);
            marcha = Integer.parseInt(marchaStr);
            if (velocidade < 0 || marcha < 0) {
                showAlert("Erro", "Velocidade e marcha não podem ser números negativos.");
                return;
            }
        } catch (NumberFormatException e) {
            showAlert("Erro", "Velocidade e marcha devem ser números inteiros válidos.");
            return;
        }

        Bicicleta bicicleta = new Bicicleta(marca, velocidade, marcha);
        bicicleta.mostarInformacoes();
        bicicletas.add(bicicleta);

        // Atualiza a label para refletir a velocidade da nova bicicleta
        lblVelocidadeAtual.setText("Velocidade Atual: " + velocidade + " km/h");

        marcaBicicletaTextField.clear();
        velocidadeAtualBicicletaTextField.clear();
        marchaAtualBicicletaTextField.clear();
    }

    @FXML
    void pedalarBicicleta(ActionEvent event) {
        if (!bicicletas.isEmpty()) {
            Bicicleta ultima = bicicletas.get(bicicletas.size() - 1);
            int novaVelocidade = ultima.getVelocidadeAtual() + 1;
            ultima.setVelocidadeAtual(novaVelocidade);
            tblView.refresh();

            // Atualiza a label em tempo real
            lblVelocidadeAtual.setText("Velocidade Atual: " + novaVelocidade + " km/h");
            System.out.println("🚴 Pedalou! Nova velocidade: " + novaVelocidade);
        } else {
            showAlert("Aviso", "Nenhuma bicicleta cadastrada para pedalar.");
        }
    }

    @FXML
    void voltarPrincipalBicicleta(ActionEvent event) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String titulo, String mensagem) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
