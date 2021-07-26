package sample.home;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.database.DatabaseHandler;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    DatabaseHandler databaseHandler;

    @FXML
    private TextField bookISBNInput;

    @FXML
    private TextField bookISBN;

    @FXML
    private TextField memberIDInput;

    @FXML
    private TextField bookName;

    @FXML
    private TextField bookAuthor;

    @FXML
    private TextField bookStatus;

    @FXML
    private TextField memberName;

    @FXML
    private TextField memberContact;

    @FXML
    private ListView<String> issueDateList;

    Boolean isReadyForSubmission = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseHandler = DatabaseHandler.getInstance();
    }

    public void loadBookInfo(ActionEvent event) throws SQLException {
        String isbn = bookISBNInput.getText();
        String query = "SELECT * FROM BOOK WHERE ISBN = '" + isbn + "'";
        ResultSet resultSet = databaseHandler.execQuery(query);
        Boolean flag = false;

        while(resultSet.next()) {
            String bName = resultSet.getString("title");
            String bAuthor = resultSet.getString("author");
            Boolean bStatus = resultSet.getBoolean("isAvail");

            bookName.setText(bName);
            bookAuthor.setText(bAuthor);

            String status = (bStatus)?"Available" : "Not Available";
            bookStatus.setText(status);
            flag = true;
        }
        if (!flag) {
            bookName.setText("Cannot find book");
            bookAuthor.setText("");
            bookStatus.setText("");
        }
    }

    public void loadMemberInfo(ActionEvent event) throws SQLException {
        String id = memberIDInput.getText();
        String query = "SELECT * FROM MEMBER WHERE ID = '" + id + "'";
        ResultSet resultSet = databaseHandler.execQuery(query);
        Boolean flag = false;

        while(resultSet.next()) {
            String mName = resultSet.getString("name");
            String mContact = resultSet.getString("mobile");

            memberName.setText(mName);
            memberContact.setText(mContact);

            flag = true;
        }
        if (!flag) {
            memberName.setText("Cannot find member");
            memberContact.setText("");
        }
    }

    @FXML
    private void loadIssueOperation(ActionEvent event) {
        String bookISBN = bookISBNInput.getText();
        String memberID = memberIDInput.getText();

        String query = "INSERT INTO ISSUE(ISBN, ID) VALUES ("
               + "'" + bookISBN + "',"
               + "'" + memberID  + "')";
        String query2 = "UPDATE BOOK SET isAvail = false WHERE ISBN = '" + bookISBN + "'";

        if (databaseHandler.execAction(query) && databaseHandler.execAction(query2)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Transaction successful");
            alert.showAndWait();
        }
        else {
            Alert alert1 = new Alert(Alert.AlertType.ERROR);
            alert1.setTitle("Failed");
            alert1.setContentText("Transaction failed");
            alert1.showAndWait();
        }
    }

    @FXML
    private void loadBookInfo2(ActionEvent event) throws SQLException {
        ObservableList<String> issueDate = FXCollections.observableArrayList();
        isReadyForSubmission = true;

        String isbn = bookISBN.getText();
        String query = "SELECT * FROM ISSUE WHERE ISBN = '" + isbn +"'";
        ResultSet resultSet = databaseHandler.execQuery(query);

        while (resultSet.next()) {
            String mBookISBN = isbn;
            String mMemberID = resultSet.getString("ID");
            Timestamp mIssueTime = resultSet.getTimestamp("issueTime");
            int mRenewCount = resultSet.getInt("renew_Count");

            issueDate.add("Issue Date and Time: " + mIssueTime.toGMTString());
            issueDate.add("Renew Count: " + mRenewCount + "'");

            issueDate.add("Book information: ");
            query = "SELECT * FROM BOOK WHERE ISBN = '" + mBookISBN + "'";
            ResultSet resultSet1 = databaseHandler.execQuery(query);
            while (resultSet1.next()) {
                issueDate.add("Book ISBN: " + resultSet1.getString("isbn"));
                issueDate.add("Book Title: " + resultSet1.getString("title"));
                issueDate.add("Book Author: " + resultSet1.getString("author"));
                issueDate.add("Book Publisher: " + resultSet1.getString("publisher"));
            }
            query = "SELECT * FROM MEMBER WHERE ID = '" + mMemberID + "'";
            resultSet1 = databaseHandler.execQuery(query);
            issueDate.add("Member information: ");
            while (resultSet1.next()) {
                issueDate.add("Member ID: " + resultSet1.getString("id"));
                issueDate.add("Member Name: " + resultSet1.getString("name"));
                issueDate.add("Member Mobile: " + resultSet1.getString("mobile"));
            }
            isReadyForSubmission = true;
            issueDateList.getItems().setAll(issueDate);
        }
    }
    @FXML
    private void loadSubmissionOP(ActionEvent event) {
        if (!isReadyForSubmission) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setContentText("Please select a book to submit");
            alert.showAndWait();
            return;
        }
        String isbn = bookISBN.getText();
        String ac1 = "DELETE FROM ISSUE WHERE ISBN = '" + isbn + "'";
        String ac2 = "UPDATE BOOK SET ISAVAIL = TRUE WHERE ISBN = '" + isbn + "'";

        if (databaseHandler.execAction(ac1) && databaseHandler.execAction(ac2)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("SUCCESS");
            alert.showAndWait();
            bookISBN.setText("");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setContentText("FAILED");
            alert.showAndWait();
        }
    }

    @FXML
    private void loadRenewOP(ActionEvent event) {
        String ac = "UPDATE ISSUE SET issueTime = CURRENT_TIMESTAMP, renew_Count = renew_Count + 1 WHERE ISBN = '" + bookISBN.getText() + "'";

        if (databaseHandler.execAction(ac)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("SUCCESS");
            alert.showAndWait();
            bookISBN.setText("");
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Failed");
            alert.setContentText("FAILED");
            alert.showAndWait();
        }
    }

    public void switchToBooks(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/book/books.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToMembers(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/member/members.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void close(ActionEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
        System.out.println("Application closed...");
    }
}
