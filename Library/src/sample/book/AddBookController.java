package sample.book;

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


public class AddBookController implements Initializable {

    @FXML
    private AnchorPane scenePane;

    @FXML
    private TextField isbn;

    @FXML
    private TextField title;

    @FXML
    private TextField author;

    @FXML
    private TextField publisher;

    private Stage stage;
    private Scene scene;
    private Parent root;

    DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseHandler = new DatabaseHandler();
    }

    public void save(ActionEvent event) {
        String bookISBN = isbn.getText();
        String bookTitle = title.getText();
        String bookAuthor = author.getText();
        String bookPublisher = publisher.getText();

        if (bookISBN.isEmpty() || bookTitle.isEmpty() || bookAuthor.isEmpty() || bookPublisher.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Fill in all fields");
            alert.showAndWait();
            return;
        }

        String query = "INSERT INTO BOOK VALUES ("+
                "'" + bookISBN + "'," +
                "'" + bookTitle + "'," +
                "'" + bookAuthor + "'," +
                "'" + bookPublisher + "'," +
                "" + true + "" +
                ")";

        System.out.println("ISBN: " + bookISBN + ", " + "Title: " + bookTitle + ", "
                + "Author: " + bookAuthor  + ", " + "Publisher: " + bookPublisher);
        databaseHandler.execAction(query);

        System.out.println("You saved book");
    }

    public void close(ActionEvent event) {
        stage = (Stage) scenePane.getScene().getWindow();
        stage.close();
        System.out.println("Application closed...");
    }

    public void switchToPrev(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/book/books.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
