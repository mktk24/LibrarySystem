package sample.member;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.book.ListOfBooksController;
import sample.database.DatabaseHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ListOfMembersController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    ObservableList<Member> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<Member> tableView;

    @FXML
    private TableColumn<Member, String> idCol;

    @FXML
    private TableColumn<Member, String> nameCol;

    @FXML
    private TableColumn<Member, String> mobileCol;

    @FXML
    private TableColumn<Member, String> mailCol;

    DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseHandler = new DatabaseHandler();
        initCol();

        try {
            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    private void initCol() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("Name"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
        mailCol.setCellValueFactory(new PropertyValueFactory<>("Mail"));
    }

    private void loadData() throws SQLException {
        String query = "select * from member";
        ResultSet resultSet = databaseHandler.execQuery(query);

        while (resultSet.next()) {
            String memberID = resultSet.getString("ID");
            String memberName = resultSet.getString("Name");
            String memberMobile = resultSet.getString("Mobile");
            String memberMail = resultSet.getString("Mail");

            list.add(new Member(memberID, memberName, memberMobile, memberMail));
        }

        tableView.setItems(list);
    }

    public static class Member {
        private final SimpleStringProperty ID;
        private final SimpleStringProperty Name;
        private final SimpleStringProperty Mobile;
        private final SimpleStringProperty Mail;

        public Member(String ID, String Name, String Mobile,
                    String Mail) {
            this.ID = new SimpleStringProperty(ID);
            this.Name = new SimpleStringProperty(Name);
            this.Mobile = new SimpleStringProperty(Mobile);
            this.Mail = new SimpleStringProperty(Mail);
        }

        public String getID() {
            return ID.get();
        }

        public String getName() {
            return Name.get();
        }

        public String getMobile() {
            return Mobile.get();
        }

        public String getMail() {
            return Mail.get();
        }
    }

    public void switchToPrev(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/member/members.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
