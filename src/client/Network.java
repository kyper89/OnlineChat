package client;

import client.controllers.ViewController;
import javafx.application.Platform;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Network {

    private static final String SERVER_ADDRESS = "localhost";
    private static final int SERVER_PORT = 8189;

    private static final String AUTH_OK_CMD = "/authok";

    private final String host;
    private final int port;
    private DataInputStream inputStream;
    private DataOutputStream outputStream;
    private Socket socket;
    private Client clientChat;
    private String nickname;

    public Network() {
        this(SERVER_ADDRESS, SERVER_PORT);
    }

    public Network(Client clientChat) {
        this();
        this.clientChat = clientChat;
    }

    public Network(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public boolean connect() {
        try {
            socket = new Socket(host, port);
            inputStream = new DataInputStream(socket.getInputStream());
            outputStream = new DataOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            System.err.println("Соединение не было установлено!");
            e.printStackTrace();
            return false;
        }
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

    public void sendMessage(String message) throws IOException {
        getOutputStream().writeUTF(message);
    }

    public void waitMessages(ViewController viewController) {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    String message = inputStream.readUTF();
                    if (clientChat.getState() == ClientChatState.AUTHENTICATION) {
                        if (message.startsWith(AUTH_OK_CMD)) {
                            String[] parts = message.split(" ", 2);
                            nickname = parts[1];
                            Platform.runLater(() -> clientChat.activeChatDialog(nickname));
                        }
                        else {
                            Platform.runLater(() -> Client.showNetworkError(message, "Auth error"));
                        }
                    }
                    else {
                        Platform.runLater(() -> viewController.appendMessage(message));
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Соединение было потеряно!");
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void close() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}