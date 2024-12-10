package org.example.ud1claire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/style.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("UD1 - Claire King");
        stage.setScene(scene);
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/appicon.png")));
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) throws KeySizeError {
        /*
        TODO fix menu bar.
         */

        launch();
    }
}
