package org.example.ud1claire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/style.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Final");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws NonASCIIException {
        /*
        TODO Get decryption fully functioning.
        TODO Maybe finish RC5, maybe just do RC4, depends on time.
        TODO fix menu bar.
         */
        launch();
    }
}
