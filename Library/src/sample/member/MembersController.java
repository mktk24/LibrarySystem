package sample.member;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MembersController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToAddMembers(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/member/AddMember.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToList(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/member/listOfMembers.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToPrev(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/home/HomeScreen.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
