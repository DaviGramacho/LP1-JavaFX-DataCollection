package org.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.model.CartaoCredito;

import java.util.HashMap;
import java.util.Map;

public class CartaoCreditoController {

    @FXML
    private TextField numeroCartaoTextField;

    @FXML
    private TextField limiteCartaoTextField;

    @FXML
    private TextField faturaCartaoTextField;

    @FXML
    private TableView<CartaoCredito> tblViewCartaoCredito;

    @FXML
    private TableColumn<CartaoCredito, String> tblNumeroCartao;

    @FXML
    private TableColumn<CartaoCredito, Double> tblLimeteCartao;

    @FXML
    private TableColumn<CartaoCredito, Double> tblFaturaCartao;

    @FXML
    private Button btnHackear;

    @FXML
    private Button btnGastar;

    @FXML
    private Label lblHistoricoGastos;

    private final ObservableList<CartaoCredito> cartoes = FXCollections.observableArrayList();
    private final Map<String, Double> historicoGastos = new HashMap<>();

    private CartaoCredito cartaoAtual;
    private boolean hackeado = false;

    @FXML
    public void initialize() {
        tblNumeroCartao.setCellValueFactory(new PropertyValueFactory<>("numeroCartao"));
        tblLimeteCartao.setCellValueFactory(new PropertyValueFactory<>("limite"));
        tblFaturaCartao.setCellValueFactory(new PropertyValueFactory<>("faturaAtual"));
        tblViewCartaoCredito.setItems(cartoes);
    }

    @FXML
    public void enviarInfoCartaoCredito(ActionEvent actionEvent) {
        String numero = numeroCartaoTextField.getText().trim();
        String limiteStr = limiteCartaoTextField.getText().trim();
        String faturaStr = faturaCartaoTextField.getText().trim();

        if (numero.isEmpty() || limiteStr.isEmpty() || faturaStr.isEmpty()) {
            showAlert("Por favor, preencha todos os campos.");
            return;
        }

        if (!limiteStr.matches("\\d+(\\.\\d+)?") || !faturaStr.matches("\\d+(\\.\\d+)?")) {
            showAlert("Erro: 'Limite' e 'Fatura' devem conter apenas números.");
            return;
        }

        double limite = Double.parseDouble(limiteStr);
        double fatura = Double.parseDouble(faturaStr);

        if (fatura > limite) {
            showAlert("Erro: A fatura não pode ser maior que o limite.");
            return;
        }

        CartaoCredito novoCartao = new CartaoCredito(numero, limite, fatura);
        cartoes.add(novoCartao);

        // Define o novo cartão como o atual
        cartaoAtual = novoCartao;
        hackeado = false;

        // Limpar os campos
        numeroCartaoTextField.clear();
        limiteCartaoTextField.clear();
        faturaCartaoTextField.clear();

        showAlert("Cartão adicionado com sucesso! Agora você pode hackear este cartão.");
    }

    @FXML
    void hackearCartao(ActionEvent event) {
        if (cartaoAtual == null) {
            showAlert("Adicione um cartão antes de hackear.");
            return;
        }

        hackeado = true;
        showAlert("Cartão " + cartaoAtual.getNumeroCartao() + " hackeado com sucesso!");
    }

    @FXML
    void gastarValor(ActionEvent event) {
        if (cartaoAtual == null) {
            showAlert("Nenhum cartão ativo. Adicione e hackeie um cartão antes de gastar.");
            return;
        }

        if (!hackeado) {
            showAlert("Você precisa hackear o cartão antes de gastar.");
            return;
        }

        double limite = cartaoAtual.getLimite();
        double fatura = cartaoAtual.getFaturaAtual();
        double disponivel = limite - fatura;

        if (disponivel <= 0) {
            showAlert("Limite esgotado. Não é possível gastar mais.");
            return;
        }

        double gasto = 10.0;
        if (gasto > disponivel) {
            showAlert("Você só pode gastar até R$ " + String.format("%.2f", disponivel));
            return;
        }

        // Atualiza fatura do cartão atual
        fatura += gasto;
        cartaoAtual.setFaturaAtual(fatura);
        tblViewCartaoCredito.refresh(); // Atualiza a tabela

        historicoGastos.put(cartaoAtual.getNumeroCartao(),
                historicoGastos.getOrDefault(cartaoAtual.getNumeroCartao(), 0.0) + gasto);
        atualizarLabelHistorico();

        if (fatura >= limite) {
            showAlert("Todo o limite do cartão foi gasto!");
        } else {
            showAlert("Você gastou R$ " + gasto + ". Fatura atual: R$ " + fatura);
        }
    }

    private void atualizarLabelHistorico() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Double> entry : historicoGastos.entrySet()) {
            sb.append("Cartão ").append(entry.getKey())
                    .append(" foi gasto R$ ")
                    .append(String.format("%.2f", entry.getValue()))
                    .append("\n");
        }
        lblHistoricoGastos.setText(sb.toString());
    }

    private boolean validarCampos() {
        String limiteStr = limiteCartaoTextField.getText();
        String faturaStr = faturaCartaoTextField.getText();

        if (limiteStr.isEmpty() || faturaStr.isEmpty()) {
            showAlert("Por favor, preencha todos os campos.");
            return false;
        }

        if (!limiteStr.matches("\\d+(\\.\\d+)?") || !faturaStr.matches("\\d+(\\.\\d+)?")) {
            showAlert("Erro: 'Limite' e 'Fatura' devem conter apenas números.");
            return false;
        }

        return true;
    }

    private void showAlert(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    public void voltarPrincipalCartao(ActionEvent actionEvent) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            showAlert("Erro ao voltar para a tela principal: " + e.getMessage());
        }
    }
}
