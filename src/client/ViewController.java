package client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ViewController {

    @FXML
    private TextField messageText;

    @FXML
    private TextArea messagesArea;

    @FXML
    private Button buttonEnter;

    private Network network;

    @FXML
    public void buttonEnterClick(ActionEvent actionEvent) {
        String enteredText = messageText.getText();
        if (enteredText.equals("")) {
            return;
        }
        appendMessage("Я: " + enteredText);
        messageText.clear();

        try {
            network.sendMessage(enteredText);
        } catch (IOException e) {
            e.printStackTrace();
            String errorMessage = "Failed to send message";
            Client.showNetworkError(e.getMessage(), errorMessage);
        }
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void appendMessage(String message) {
        messagesArea.appendText(message);
        messagesArea.appendText(System.lineSeparator());
    }

}
