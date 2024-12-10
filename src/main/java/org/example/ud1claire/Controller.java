package org.example.ud1claire;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.awt.Desktop;
import java.net.URI;

public class Controller {
    @FXML
    private RadioButton rc4radio, rc5radio;
    @FXML
    private TextField input1, input2;
    @FXML
    private TextArea input3;
    @FXML
    private ToggleGroup toggleGroup;

    @FXML
    private Button copy, encrypt, decrypt;

    @FXML
    private MenuItem githubMenu, rc4Menu, rc5Menu;

    //    private RC4 rc4;
    private RC5 rc5;

    private boolean rc4selected = true;


    @FXML
    public void initialize() throws KeySizeError {
        toggleGroup.selectToggle(rc4radio);
        input3.editableProperty().set(false);
        input3.setFocusTraversable(false);

        rc5radio.setOnAction(actionEvent -> rc4selected = false);

        encrypt.setOnAction(event -> {
            try {
                handleEncrypt();
            } catch (KeySizeError e) {
            }
        });

        decrypt.setOnAction(event -> {
            try {
                handleDecrypt();
            } catch (KeySizeError e) {
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


        rc5Menu.setOnAction(event -> {
            try {
                openUrl("https://github.com/CS112-2564-Fall2024/ud1claire");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });



    }

    private void openUrl(String url) throws URISyntaxException, IOException {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            Desktop.getDesktop().browse(new URI(url));
        }
    }

    private boolean isValid() throws KeySizeError {
        if (!input1.getText().isEmpty() && !input2.getText().isEmpty()) {
//            The ciphertext may not be ASCII but the key must be.
            if (!Cipher.Util.isStringAscii(input2.getText())) {
                throw new KeySizeError("Invalid text entered");
            }

            return true;
        }
        return false;
    }

    private byte[][] getInputs() {
        return new byte[][]{input2.getText().getBytes(StandardCharsets.US_ASCII), input1.getText().getBytes(StandardCharsets.US_ASCII)};
    }

    private void handleEncrypt() throws KeySizeError {
        if (isValid()) {
            byte[][] inputs = getInputs();
            RC4 rc4 = new RC4(inputs[0]);
//            input3.setText();
//            this.data = rc4.process(inputs[1]);

            String text = rc4selected ? Cipher.Util.bToS(rc4.encrypt(inputs[1])) : Cipher.Util.bToS(rc5.encrypt(inputs[1]));

            input3.setText(text);
        }
    }

    private void handleDecrypt() throws KeySizeError {
        if (isValid()) {
            byte[][] inputs = getInputs();
            RC4 rc4 = new RC4(inputs[0]);

            byte[] data = rc4.decrypt(Cipher.Util.hToB(input1.getText()));
            String text = rc4selected ? "Hex: " + Cipher.Util.bToS(data) +
                    "\nPlaintext: " + new String(data) : Arrays.toString(rc5.decrypt(inputs[1]));

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
}