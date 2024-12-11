package org.example.ud1claire;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.awt.Desktop;
import java.net.URI;
import java.security.Key;

public class Controller {
    @FXML
    private TextField input1, input2;
    @FXML
    private TextArea input3;
    @FXML
    private Button encrypt, decrypt;
    @FXML
    private MenuItem githubMenu, rc4Menu, importFile, exportFile;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        input3.editableProperty().set(false);
        input3.setFocusTraversable(false);

        input1.setOnKeyPressed(this::handleShortcut);
        input2.setOnKeyPressed(this::handleShortcut);

        importFile.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            File file = fileChooser.showOpenDialog(stage);

            if (file != null) {
                Path path = Paths.get(file.getAbsolutePath());
                try {
                    input1.setText(Files.readAllLines(path).getFirst());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        exportFile.setOnAction(actionEvent -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setInitialFileName("output.txt");
            fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
            File file = fileChooser.showSaveDialog(stage);

            if (file != null) {
                try {
                    Files.writeString(Paths.get(new File(file.getAbsolutePath()).toString()), input3.getText());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        encrypt.setOnAction(event -> {
            try {
                handleEncrypt();
            } catch (KeySizeError e) {
                input3.setText("Key length must be between 1 and 256 bytes long.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        decrypt.setOnAction(event -> {
            try {
                handleDecrypt();
            } catch (KeySizeError e) {
                    input3.setText("Key length must be between 1 and 256 bytes long.");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        githubMenu.setOnAction(event -> {
            try {
                openUrl("https://github.com/CS112-2564-Fall2024/ud1claire");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        rc4Menu.setOnAction(event -> {
            try {
                openUrl("https://en.wikipedia.org/wiki/RC4#Key-scheduling_algorithm_(KSA)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void handleCopy() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(input3.getText());
        clipboard.setContent(content);
    }

    @FXML
    public void handleClear() {
        input1.setText("");
        input2.setText("");
        input3.setText("");
    }

    private void handleEncrypt() throws Exception {
        if (!input1.getText().isEmpty()) {
            byte[][] inputs = getInputs();
            RC4 rc4 = new RC4(inputs[0]);
            String text = Cipher.Util.bToS(rc4.encrypt(inputs[1]));


            input3.setText(text);
        }
    }

    private void handleDecrypt() throws Exception {
        if (isValid()) {
            byte[][] inputs = getInputs();

            RC4 rc4 = new RC4(inputs[0]);
            String text = new String(rc4.decrypt(Cipher.Util.hToB(input1.getText())));

            input3.setText(text);
        }
    }

    private byte[][] getInputs() {
        return new byte[][]{input2.getText().getBytes(StandardCharsets.US_ASCII), input1.getText().getBytes(StandardCharsets.US_ASCII)};
    }

    private void handleShortcut(KeyEvent event) {
        KeyCode code = event.getCode();
        if (isValid()) {
            if (event.isControlDown() && code == KeyCode.E) {
                try {
                    handleEncrypt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (event.isControlDown() && code == KeyCode.D) {
                try {
                    handleDecrypt();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if(event.isControlDown() && code == KeyCode.C) {
            handleClear();
        }
        if(event.isControlDown() && code == KeyCode.Q) {
            Platform.exit();
            System.exit(0);
        }
    }

    private void openUrl(String url) throws URISyntaxException, IOException {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI(url));
        }
    }

    private boolean isValid() {
        return !input1.getText().isEmpty() && !input2.getText().isEmpty();
    }
}