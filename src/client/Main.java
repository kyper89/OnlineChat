package client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Alert;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void stop() throws Exception {
        System.out.println("Stop");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));

        Parent root = loader.load();

        primaryStage.setTitle("Online chat");
        primaryStage.setScene(new Scene(root, 800, 600));

        primaryStage.show();

        Network network = new Network();
        if (!network.connect()) {
            showNetworkError("", "Failed to connect to server");
        }

        ViewController viewController = loader.getController();
        viewController.setNetwork(network);

        network.waitMessages(viewController);

        primaryStage.setOnCloseRequest(event -> {
//            network.sendMessage("/end");
//            network.close();
        });
    }

    public static void showNetworkError(String errorDetails, String errorTitle) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Network Error");
        alert.setHeaderText(errorTitle);
        alert.setContentText(errorDetails);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
