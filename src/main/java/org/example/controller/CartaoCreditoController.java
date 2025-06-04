package org.example.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.App;
import org.example.dao.CartaoCreditoDAO;
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
    private TableColumn<CartaoCredito, Double> tblLimiteCartao;

    @FXML
    private TableColumn<CartaoCredito, Double> tblFaturaCartao;

    @FXML
    private Label lblHistoricoGastos;

    private final ObservableList<CartaoCredito> cartoes = FXCollections.observableArrayList();
    private final Map<String, Double> historicoGastos = new HashMap<>();

    // Guardar o número antigo ao selecionar para edição
    private String numeroAntigo;

    // Marca se já foi “hackeado” o cartão selecionado
    private CartaoCredito cartaoAtual;
    private boolean hackeado = false;

    private final CartaoCreditoDAO dao = new CartaoCreditoDAO();

    @FXML
    public void initialize() {
        // Configura as colunas da tabela para pegar as propriedades do modelo
        tblNumeroCartao.setCellValueFactory(new PropertyValueFactory<>("numero"));
        tblLimiteCartao.setCellValueFactory(new PropertyValueFactory<>("limite"));
        tblFaturaCartao.setCellValueFactory(new PropertyValueFactory<>("fatura"));
        tblViewCartaoCredito.setItems(cartoes);

        // Carrega da base todos os cartões
        cartoes.addAll(dao.listarCartoes());

        // Ao selecionar um cartão da tabela, preenche os campos para edição
        tblViewCartaoCredito.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                cartaoAtual = newSel;
                numeroAntigo = newSel.getNumero();
                numeroCartaoTextField.setText(newSel.getNumero());
                limiteCartaoTextField.setText(String.valueOf(newSel.getLimite()));
                faturaCartaoTextField.setText(String.valueOf(newSel.getFatura()));
                hackeado = false;
            }
        });
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

        // Verifica duplicado pelo número
        for (CartaoCredito c : cartoes) {
            if (c.getNumero().equals(numero)) {
                showAlert("Já existe um cartão com esse número. Insira um número único.");
                return;
            }
        }

        double limite = Double.parseDouble(limiteStr);
        double fatura = Double.parseDouble(faturaStr);
        if (fatura > limite) {
            showAlert("Erro: A fatura não pode ser maior que o limite.");
            return;
        }

        // Cria e salva no banco
        CartaoCredito novo = new CartaoCredito(numero, limite, fatura);
        dao.adicionarCartao(novo);
        cartoes.add(novo);
        cartaoAtual = novo;
        hackeado = false;

        limparCampos();
        showAlert("Cartão adicionado com sucesso!");
    }

    @FXML
    public void hackearCartao(ActionEvent event) {
        if (cartaoAtual == null) {
            showAlert("Selecione um cartão antes de hackear.");
            return;
        }
        hackeado = true;
        showAlert("Cartão " + cartaoAtual.getNumero() + " hackeado com sucesso!");
    }

    @FXML
    public void gastarValor(ActionEvent event) {
        if (cartaoAtual == null) {
            showAlert("Selecione um cartão antes de gastar.");
            return;
        }
        if (!hackeado) {
            showAlert("Você precisa hackear o cartão antes de gastar.");
            return;
        }

        double limite = cartaoAtual.getLimite();
        double fatura = cartaoAtual.getFatura();
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

        cartaoAtual.setFatura(fatura + gasto);
        dao.atualizarCartao(cartaoAtual, numeroAntigo);
        tblViewCartaoCredito.refresh();

        historicoGastos.put(
                cartaoAtual.getNumero(),
                historicoGastos.getOrDefault(cartaoAtual.getNumero(), 0.0) + gasto
        );
        atualizarLabelHistorico();

        if (cartaoAtual.getFatura() >= limite) {
            showAlert("Todo o limite do cartão foi gasto!");
        } else {
            showAlert("Você gastou R$ " + gasto + ". Fatura atual: R$ " + cartaoAtual.getFatura());
        }
    }

    @FXML
    public void atualizarCartaoCredito(ActionEvent event) {
        if (cartaoAtual == null) {
            showAlert("Selecione um cartão na tabela para atualizar.");
            return;
        }

        String novoNumero = numeroCartaoTextField.getText().trim();
        String limiteStr = limiteCartaoTextField.getText().trim();
        String faturaStr = faturaCartaoTextField.getText().trim();

        if (novoNumero.isEmpty() || limiteStr.isEmpty() || faturaStr.isEmpty()) {
            showAlert("Por favor, preencha todos os campos para atualizar.");
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

        // Verifica duplicado em outro cartão
        for (CartaoCredito c : cartoes) {
            if (c != cartaoAtual && c.getNumero().equals(novoNumero)) {
                showAlert("Já existe um cartão com esse número. Insira um número único.");
                return;
            }
        }

        cartaoAtual.setNumero(novoNumero);
        cartaoAtual.setLimite(limite);
        cartaoAtual.setFatura(fatura);
        dao.atualizarCartao(cartaoAtual, numeroAntigo);
        tblViewCartaoCredito.refresh();
        showAlert("Cartão atualizado com sucesso!");
        limparCampos();
    }

    @FXML
    public void deletarCartaoCredito(ActionEvent event) {
        if (cartaoAtual == null) {
            showAlert("Selecione um cartão para deletar.");
            return;
        }
        dao.deletarCartao(cartaoAtual.getNumero());
        cartoes.remove(cartaoAtual);
        tblViewCartaoCredito.refresh();
        showAlert("Cartão removido com sucesso!");
        limparCampos();
    }

    @FXML
    public void voltarPrincipalCartao(ActionEvent actionEvent) {
        try {
            App.setRoot("TelaPrincipal");
        } catch (Exception e) {
            showAlert("Erro ao voltar para a tela principal: " + e.getMessage());
        }
    }

    private void atualizarLabelHistorico() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Double> entry : historicoGastos.entrySet()) {
            sb.append("Cartão ").append(entry.getKey())
                    .append(" foi gasto R$ ").append(String.format("%.2f", entry.getValue()))
                    .append("\n");
        }
        lblHistoricoGastos.setText(sb.toString());
    }

    private void limparCampos() {
        numeroCartaoTextField.clear();
        limiteCartaoTextField.clear();
        faturaCartaoTextField.clear();
        tblViewCartaoCredito.getSelectionModel().clearSelection();
        cartaoAtual = null;
        hackeado = false;
        numeroAntigo = null;
    }

    private void showAlert(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Aviso");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
