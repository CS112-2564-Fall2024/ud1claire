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
    private boolean encryptUsed = false;

    @FXML
    public void initialize() throws NonASCIIException {
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
            String result = Cipher.Util.bToS(rc4.encrypt());

            String text = rc4selected ? result : rc5.encryptS();

            input3.setText(text);
        }
    }

    private void handleDecrypt() throws NonASCIIException {
        if(isValid()) {
            byte[][] inputs = getInputs();
            String text;
            text = new RC4(Cipher.Util.hToS(input1.getText()),inputs[1]).decryptS();
            System.out.println(text);

//            if(rc4 == null) {
//                System.out.println("Test 1");
//                rc4 = new RC4(inputs[0], inputs[1]);
//                text = Cipher.Util.bToS(rc4.encrypt());
//                byte[] c = new RC4(rc4.encrypt(), inputs[1]).decrypt();
//                System.out.println(Cipher.Util.bToS(c));
////                45a01f645fc35b383552544b9bf5
//            } else {
//                System.out.println("Test 2");
//                byte[] r = new RC4(rc4.getPlaintext(), inputs[1]).decrypt();
//                byte[] ciphertext = new RC4(r, inputs[1]).decrypt();
//                text = rc4selected ? Cipher.Util.bToS(ciphertext) + "\nPlaintext: " + new String(ciphertext): rc5.decryptS();
//                System.out.println(text);
//            }
            RC4 r = new RC4(inputs[0], inputs[1]);
            System.out.println(Arrays.toString(r.encrypt()));

            text = new RC4(inputs[0], inputs[1]).encryptS();
            System.out.println(text);

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