package sample.member;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddMemberController implements Initializable {
    @FXML
    private AnchorPane scenePane;

    @FXML
    private TextField id;

    @FXML
    private TextField name;

    @FXML
    private TextField mobile;

    @FXML
    private TextField mail;

    private Stage stage;
    private Scene scene;
    private Parent root;

    DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseHandler = new DatabaseHandler();
    }

    public void save(ActionEvent event) {
        String memberId = id.getText();
        String memberName = name.getText();
        String memberMobile = mobile.getText();
        String memberMail = mail.getText();

        if (memberId.isEmpty() || memberName.isEmpty() || memberMobile.isEmpty() || memberMail.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill in all fields");
            alert.showAndWait();
            return;
        }

        String query = "INSERT INTO MEMBER VALUES ("+
                "'" + memberId + "'," +
                "'" + memberName + "'," +
                "'" + memberMobile + "'," +
                "'" + memberMail + "'" +
                ")";

        System.out.println("ID: " + memberId + ", " + "Name: " + memberName + ", "
                + "Mobile: " + memberMobile  + ", " + "Mail: " + memberMail);
        databaseHandler.execAction(query);

        System.out.println("You saved member");
    }

    public void close(ActionEvent event) {
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
        System.out.println("Application closed...");
    }

    public void switchToPrev(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/member/members.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
