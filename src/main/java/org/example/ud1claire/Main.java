package org.example.ud1claire;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

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
        TODO fix menu bar.
         */
//        launch();
//        45a01f645fc35b383552544b9bf5
        RC4 rc4 = new RC4("45a01f645fc35b383552544b9bf5".getBytes(StandardCharsets.US_ASCII), "Secret".getBytes(StandardCharsets.US_ASCII));
        System.out.println(new String(rc4.decrypt()));
        System.out.println(rc4.decryptS());
        System.out.println(Cipher.Util.bToS(rc4.encrypt()));
        System.out.println(Arrays.toString(rc4.encrypt()));
        System.out.println(Arrays.toString("Attack at dawn".getBytes()));

    }
}
