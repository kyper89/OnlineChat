package client.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import client.Client;
import client.Network;

import java.io.IOException;

public class AuthController {

    private static final String AUTH_CMD = "/auth"; // "/auth login password"

    @FXML
    public TextField loginField;

    @FXML
    public PasswordField passwordField;

    private Network network;

    @FXML
    public void executeAuth() {
        String login = loginField.getText();
        String password = passwordField.getText();
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            Client.showNetworkError("Логин и пароль обязательны!", "Валидация");
            return;
        }

        String authCommandMessage = String.format("%s %s %s", AUTH_CMD, login, password);
        try {
            network.sendMessage(authCommandMessage);
        } catch (IOException e) {
            Client.showNetworkError(e.getMessage(), "Auth error!");
            e.printStackTrace();
        }
    }

    public void setNetwork(Network network) {
        this.network = network;
    }
}
