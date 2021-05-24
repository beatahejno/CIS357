package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Book;
import model.Library;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddBookPage implements Initializable {
    Library library;
    @FXML private TextField txtName;
    @FXML private ListView<String> lstPublisher;
    @FXML private TextField txtAuthor;
    @FXML private TextField txtISBN;
    @FXML private ComboBox<String> cbxGenre;
    @FXML private TextField txtYear;
    @FXML private Button btnRegister;


    public void registerBook(ActionEvent e) throws IOException {
        if(checkValidity()){
            //add book to the library object
            Book newBook = new Book(txtName.getText().trim(), txtAuthor.getText().trim(),
                    lstPublisher.getSelectionModel().getSelectedItem(), cbxGenre.getValue(),
                    txtISBN.getText().trim(), Long.parseLong(txtYear.getText().trim()));
            library.addBook(newBook);

            //pass the library object back to main menu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuPage.fxml"));
            loader.load();
            MainMenuPage controller = loader.getController();
            controller.library = library;

            //hide this screen
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.hide();

            //display information box ("registered Data Structures")
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Registration Successful");
            a.setHeaderText(null);
            a.setContentText(library.printMsgLog());
            a.initOwner(btnRegister.getScene().getWindow());
            a.showAndWait();
        } else{
            //display error box
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Registration Unsuccessful");
            a.setHeaderText(null);
            a.setContentText(library.printMsgLog());
            a.initOwner(btnRegister.getScene().getWindow());
            a.showAndWait();
        }

    }

    private boolean checkValidity(){
        String name = txtName.getText().trim();
        String publisher = lstPublisher.getSelectionModel().getSelectedItem();
        String author = txtAuthor.getText().trim();
        String genre = cbxGenre.getValue();
        String isbn = txtISBN.getText().trim();
        String year = txtYear.getText().trim();

        //do some checks on the data
        if( name.isEmpty() || author.isEmpty() || isbn.isEmpty() || year.isEmpty()
                || publisher == null || genre == null ){
            //sth empty
            library.msgLog.add("All of the fields must be filled out!");
            return false;
        }

        try { //checking if the year field can be parsed to long
            Long yearLong = Long.parseLong(txtYear.getText());
            if(yearLong > Long.valueOf(LocalDate.now().getYear())){ //year given is in the future
                library.msgLog.add("Book's publication date can't be in the future!");
                return false;
            }
        }catch(Exception e){ //year field is not only digits
            library.msgLog.add("The year field should only contain digits");
            return false;
        }

        for(int i=0; i<author.length(); i++){
            //author name contains illegal characters
            if(!Character.isAlphabetic(author.charAt(i)) && author.charAt(i) != ' '){
                library.msgLog.add("Author's name contains illegal characters!");
                return false;
            }
        }

        for(int i=0; i<isbn.length(); i++){
            if(!Character.isDigit(isbn.charAt(i))){ //ISBN contains sth other than digits
                library.msgLog.add("ISBN can only contain digits!");
                return false;
            }
        }

        //there are no error messages, all's good
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //add items to list view
        lstPublisher.getItems().addAll( "Elsevier", "HarperCollins", "Oxford University Press", "Pearson",
                "Penguin", "Simon & Schuster");

        //add items to genre combo box
        cbxGenre.setItems(FXCollections.observableArrayList("Education",
                "Fantasy", "Science Fiction", "Thriller"));
    }
}
