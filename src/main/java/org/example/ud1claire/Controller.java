package org.example.ud1claire;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.awt.Desktop;
import java.net.URI;
import java.util.Base64;

public class Controller {
    @FXML
    private RadioButton rc4Radio, aesRadio;
    @FXML
    private TextField input1, input2;
    @FXML
    private TextArea input3;
    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private Button encrypt, decrypt, clear;

    @FXML
    private MenuItem githubMenu, rc4Menu, aesMenu, importFile, exportFile;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private byte[] aesKey;

    @FXML
    public void initialize() {

        toggleGroup.selectToggle(rc4Radio);
        input3.editableProperty().set(false);
        input3.setFocusTraversable(false);

        input1.setOnKeyPressed(this::handleShortcut);
        input2.setOnKeyPressed(this::handleShortcut);

//        aesRadio.setOnAction(actionEvent -> {
////                We generate a key for AES.
//            input2.setEditable(false);
//        });
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
                if (rc4Radio.isSelected()) {
                    input3.setText("Key length must be between 1 and 256 bytes long.");
                } else {
                    e.printStackTrace();
                }
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


        aesMenu.setOnAction(event -> {
            try {
                openUrl("https://docs.oracle.com/javase/8/docs/api/javax/crypto/Cipher.html");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });


    }

    public void setAesKey(byte[] aesKey) {
        this.aesKey = aesKey;
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
    }

    private void openUrl(String url) throws URISyntaxException, IOException {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI(url));
        }
    }

    private boolean isValid() {
        if (!input1.getText().isEmpty()) {
            if (rc4Radio.isSelected()) {
                return !input2.getText().isEmpty();
            } else {
                return true;
            }
        }

        return false;
    }

    private byte[][] getInputs() {
        return new byte[][]{input2.getText().getBytes(StandardCharsets.US_ASCII), input1.getText().getBytes(StandardCharsets.US_ASCII)};
    }

    private void handleEncrypt() throws Exception {
        System.out.println("test1");

        if (!input1.getText().isEmpty()) {
            byte[][] inputs = getInputs();
            String text = null;
            System.out.println("test2");

            if (rc4Radio.isSelected()) {
                if (!input2.getText().isEmpty()) {
                    RC4 rc4 = new RC4(inputs[0]);
                    text = Cipher.Util.bToS(rc4.encrypt(inputs[1]));
                } else {
                    input3.setText("Key can not be empty.");
                }

            } else {
                AES aes = new AES(inputs[0]);
                text = "Ciphertext: " + Base64.getEncoder().encodeToString(aes.encrypt(inputs[1]));
                text += "\nKey: " + Base64.getEncoder().encodeToString(aes.getKey());
                System.out.println(aes.getKey().length);
                setAesKey(Base64.getEncoder().encode(aes.getKey()));
            }

            input3.setText(text);
        }
    }

    private void handleDecrypt() throws Exception {
        if (isValid()) {
            byte[][] inputs = getInputs();
            String text = null;

            if (aesRadio.isSelected()) {
                AES aes = new AES(Base64.getDecoder().decode(inputs[0]));

                if (aesKey != null) {
                    System.out.println("test 1");
                    System.out.println(Arrays.toString(aesKey));
                    text = new String(aes.decrypt(Base64.getDecoder().decode(aesKey)));
                } else {
                    try {
                        System.out.println("test 2");

                        text = new String(aes.decrypt(inputs[1]));
                    } catch (Exception e) {
                        input3.setText("Invalid AES key.");
                    }
                }


            } else {
                RC4 rc4 = new RC4(inputs[0]);
                text = new String(rc4.decrypt(Cipher.Util.hToB(input1.getText())));
            }

            input3.setText(text);
        }
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
}