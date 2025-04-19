package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        try {
            scene = new Scene(loadFXML("TelaPrincipal"), 640, 480);
            stage.setScene(scene);
            stage.setTitle("LP1-JavaFX-DataCollection");
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo FXML.");
            e.printStackTrace();
        }
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        String caminho = "/org/view/" + fxml + ".fxml";
        System.out.println("Carregando: " + caminho); // Apenas para debug
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(caminho));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
