package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void stop() throws Exception {
        System.out.println("Stop");
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        System.out.println("Start");
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Online chat");
        primaryStage.setScene(new Scene(root, 800, 600));

        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
