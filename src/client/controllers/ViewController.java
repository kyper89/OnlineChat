package client.controllers;

import client.Client;
import client.Network;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class ViewController {

    @FXML
    public ListView<String> usersList;

    @FXML
    public Button buttonEnter;

    @FXML
    private TextField textField;

    @FXML
    private TextArea messagesArea;

    private Network network;

    @FXML
    public void initialize() {
        usersList.setItems(FXCollections.observableArrayList(Client.USERS_TEST_DATA));
        textField.requestFocus();
    }

    @FXML
    public void sendMessage() {
        String enteredText = textField.getText();
        if (enteredText.equals("")) {
            return;
        }
        appendMessage("Я: " + enteredText);
        textField.clear();

        try {
            network.sendMessage(enteredText);
        } catch (IOException e) {
            e.printStackTrace();
            String errorMessage = "Failed to send message";
            Client.showNetworkError(e.getMessage(), errorMessage);
        }
    }

    public void appendMessage(String message) {
        messagesArea.appendText(message);
        messagesArea.appendText(System.lineSeparator());
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public TextField getTextField() {
        return textField;
    }

}
