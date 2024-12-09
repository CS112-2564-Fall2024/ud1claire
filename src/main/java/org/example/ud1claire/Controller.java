package org.example.ud1claire;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.example.ud1claire.NonASCIIException;

import java.security.Key;
import java.util.Arrays;
import java.util.EventListener;

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

    private RC4 rc4;
    private RC5 rc5;

    private boolean rc4selected = true;

    @FXML
    public void initialize() throws NonASCIIException {

//        System.out.println(Cipher.Util.bToS("Attack at dawn".getBytes()));

        toggleGroup.selectToggle(rc4radio);
        input3.editableProperty().set(false);
        input3.setFocusTraversable(false);

        rc5radio.setOnAction(actionEvent -> rc4selected = false);

        encrypt.setOnAction(event -> {
            try {
                handleEncrypt();
            } catch (NonASCIIException e) {
            }
        });

        decrypt.setOnAction(event -> {
            try {
                handleDecrypt();
            } catch (NonASCIIException e) {
            }
        });
    }

    private boolean isValid() throws NonASCIIException {
        if(!input1.getText().isEmpty() && !input2.getText().isEmpty()) {
//            The ciphertext may not be ASCII but the key must be.
            if (!Cipher.Util.isStringAscii(input2.getText())) {
                throw new NonASCIIException("Invalid text entered");
            }

            return true;
        } return false;
    }

    private byte[][] getInputs() {
        return new byte[][]{input1.getText().getBytes(), input2.getText().getBytes()};
    }

    private void handleEncrypt() throws NonASCIIException {
        if(isValid()) {
            byte[][] inputs = getInputs();
            rc4 = new RC4(inputs[0],inputs[1]);
            System.out.println(new String(rc4.encrypt()));
            String text = rc4selected ? new String(rc4.encrypt()) : rc5.encryptS();

            input3.setText(text);
        }
    }

    private void handleDecrypt() throws NonASCIIException {
        if(isValid()) {
            byte[][] inputs = getInputs();

//            if(rc4 == null) {
                rc4 = new RC4(inputs[0], inputs[1]);
//            }

            System.out.println(new String(new RC4(rc4.encrypt(), inputs[1]).encrypt()));
            System.out.println(new String(rc4.encrypt()));
            String text = rc4selected ? new String(new RC4(rc4.encrypt(), inputs[1]).encrypt()): rc5.decryptS();
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