package sample.book;

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
import sample.database.DatabaseHandler;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ListOfBooksController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;

    ObservableList<Book> list = FXCollections.observableArrayList();

    @FXML
    private AnchorPane rootPane;

    @FXML
    private TableView<Book> tableView;

    @FXML
    private TableColumn<Book, String> isbnCol;

    @FXML
    private TableColumn<Book, String> titleCol;

    @FXML
    private TableColumn<Book, String> authorCol;

    @FXML
    private TableColumn<Book, String> publisherCol;

    @FXML
    private TableColumn<Book, Boolean> availabilityCol;

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
        isbnCol.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        titleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        authorCol.setCellValueFactory(new PropertyValueFactory<>("Author"));
        publisherCol.setCellValueFactory(new PropertyValueFactory<>("Publisher"));
        availabilityCol.setCellValueFactory(new PropertyValueFactory<>("Availability"));
    }

    private void loadData() throws SQLException {
        String query = "select * from book";
        ResultSet resultSet = databaseHandler.execQuery(query);

        while (resultSet.next()) {
            String bookISBN = resultSet.getString("ISBN");
            String bookTitle = resultSet.getString("Title");
            String bookAuthor = resultSet.getString("Author");
            String bookPublisher = resultSet.getString("Publisher");
            Boolean bookAvailability = resultSet.getBoolean("isAvail");

            list.add(new Book(bookISBN, bookTitle, bookAuthor, bookPublisher, bookAvailability));
        }

        tableView.setItems(list);
    }

    public static class Book {
        private final SimpleStringProperty ISBN;
        private final SimpleStringProperty Title;
        private final SimpleStringProperty Author;
        private final SimpleStringProperty Publisher;
        private final SimpleBooleanProperty Availability;

        public Book(String ISBN, String Title, String Author,
             String Publisher, boolean Availability) {
            this.ISBN = new SimpleStringProperty(ISBN);
            this.Title = new SimpleStringProperty(Title);
            this.Author = new SimpleStringProperty(Author);
            this.Publisher = new SimpleStringProperty(Publisher);
            this.Availability = new SimpleBooleanProperty(Availability);
        }

        public String getISBN() {
            return ISBN.get();
        }

        public String getTitle() {
            return Title.get();
        }

        public String getAuthor() {
            return Author.get();
        }

        public String getPublisher() {
            return Publisher.get();
        }

        public boolean getAvailability() {
            return Availability.get();
        }
    }

    public void switchToPrev(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/sample/book/books.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
