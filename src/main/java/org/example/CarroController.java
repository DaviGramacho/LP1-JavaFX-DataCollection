package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.Carro;

public class CarroController {

    // Componentes da interface gráfica
    @FXML
    private TextField anoCarroTextField;

    @FXML
    private TextField marcaCarroTextField;

    @FXML
    private TextField modeloCarroTextField;

    @FXML
    private TableView<Carro> tblViewCarro;

    @FXML
    private TableColumn<Carro, Integer> tblAnoCarro;

    @FXML
    private TableColumn<Carro, String> tblMarcaCarro;

    @FXML
    private TableColumn<Carro, String> tblModeloCarro;

    @FXML
    private Label marchaLabel;

    // Lista observável para armazenar os carros
    private final ObservableList<Carro> carros = FXCollections.observableArrayList();

    // Variável para controlar o estado atual da marcha
    private String marchaAtual = "1"; // Inicia na primeira marcha

    /**
     * Método para aumentar a marcha.
     */
    @FXML
    public void aumentarMarcha() {
        switch (marchaAtual) {
            case "1":
                marchaAtual = "2";
                break;
            case "2":
                marchaAtual = "3";
                break;
            case "3":
                marchaAtual = "4";
                break;
            case "4":
                marchaAtual = "5";
                break;
            case "5":
                showAlert("Você já está na quinta marcha.");
                return;
            case "R":
                marchaAtual = "1";
                break;
        }
        atualizarLabel();
    }

    /**
     * Método para diminuir a marcha.
     */
    @FXML
    public void diminuirMarcha() {
        switch (marchaAtual) {
            case "5":
                marchaAtual = "4";
                break;
            case "4":
                marchaAtual = "3";
                break;
            case "3":
                marchaAtual = "2";
                break;
            case "2":
                marchaAtual = "1";
                break;
            case "1":
                marchaAtual = "R";
                break;
            case "R":
                showAlert("Você já está na ré.");
                return;
        }
        atualizarLabel();
    }

    /**
     * Atualiza o texto do Label com a marcha atual.
     */
    private void atualizarLabel() {
        marchaLabel.setText("Marcha: " + marchaAtual);
    }

    /**
     * Exibe um alerta personalizado.
     *
     * @param mensagem A mensagem a ser exibida no alerta.
     */
    private void showAlert(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Aviso");
        alert.setHeaderText(null); // Remove o cabeçalho
        alert.setContentText(mensagem);

        // Remove os botões padrão e adiciona apenas o botão "Fechar"
        alert.getButtonTypes().clear();
        alert.getButtonTypes().add(ButtonType.CLOSE);

        // Mostra o alerta e aguarda até que o usuário o feche
        alert.showAndWait();
    }

    /**
     * Envia as informações do carro para a tabela.
     */
    @FXML
    void enviarInfoCarro(ActionEvent event) {
        try {
            // Obtém os valores dos campos de texto
            String marca = marcaCarroTextField.getText();
            String modelo = modeloCarroTextField.getText();

            // Verifica se o ano do carro contém apenas números
            String anoText = anoCarroTextField.getText();
            if (!anoText.matches("\\d+")) {
                showAlert("Erro: O campo 'Ano' deve conter apenas números.");
                return;
            }

            int ano = Integer.parseInt(anoText);

            // Cria um novo carro e adiciona à lista
            Carro carro = new Carro(marca, modelo, ano);
            carros.add(carro);

            // Limpa os campos de texto após adicionar o carro
            marcaCarroTextField.clear();
            modeloCarroTextField.clear();
            anoCarroTextField.clear();

            // Exibe o carro no console para teste
            carro.mostrarCarro();
        } catch (Exception e) {
            showAlert("Erro ao adicionar o carro: " + e.getMessage());
        }
    }

    /**
     * Inicializa a tabela e configura as colunas.
     */
    @FXML
    void initialize() {
        // Configura as colunas da tabela
        tblMarcaCarro.setCellValueFactory(new PropertyValueFactory<>("marca"));
        tblAnoCarro.setCellValueFactory(new PropertyValueFactory<>("ano"));
        tblModeloCarro.setCellValueFactory(new PropertyValueFactory<>("modelo"));

        // Define os itens da tabela como a lista de carros
        tblViewCarro.setItems(carros);
    }

    @FXML
    void voltarPrincipalCarro(ActionEvent event) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            showAlert("Erro ao voltar para a tela principal: " + e.getMessage());
        }
    }
}
