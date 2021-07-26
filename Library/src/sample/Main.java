package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;

import java.sql.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("home/homeScreen.fxml"));
        primaryStage.setTitle("Library");
        primaryStage.setScene(new Scene(root, 600, 400));
        primaryStage.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                DatabaseHandler.getInstance();
            }
        }).start();
    }


    public static void main(String[] args) throws SQLException {
        launch(args);
    }
}
