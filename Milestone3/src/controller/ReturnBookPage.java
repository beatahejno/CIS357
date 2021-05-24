package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import model.*;

import java.net.URL;
import java.util.ResourceBundle;

public class ReturnBookPage implements Initializable {
    Library library;

    @FXML
    private Label lblMessage;
    @FXML
    private Button btnReturnBook;
    @FXML
    private TextField txtBookID;
    @FXML
    private ListView<String> lstBooks;
    @FXML
    private TextField txtBookSearch;

    public void bookSearch() {
        String phrase = txtBookSearch.getText();
        library.printResults(library.searchBook(phrase));
        lstBooks.setItems(FXCollections.observableArrayList(library.msgLog)); //put this in list view
        library.msgLog.clear();
    }

    public void populateBookIDTextField() {
        //extract the selected line from list view
        String line = lstBooks.getSelectionModel().getSelectedItem();
        //populate textfield with first id
        txtBookID.setText(getFirstNumbersInString(line));
    }

    public void returnBook() {
        String bookId = txtBookID.getText();

        if (bookId.isEmpty()) {
            library.msgLog.add("Book ID is not filled out!");
            printMessage(true);
            return;
        }

        //true return value means a positive message and book successfully returned
        //negative return value means some issues along the way
        try {
            if (library.returnBook(Integer.valueOf(bookId))) {
                printMessage(false);
            } else {
                printMessage(true);
            }
        } catch (NumberFormatException e) {
            library.msgLog.add("ID has to be an integer!");
            printMessage(true);

        }

    }

    public void printMessage(boolean isError) {
        if (isError) {
            lblMessage.setTextFill(Color.web("ff0000"));
            lblMessage.setText(library.printMsgLog());
        } else {
            lblMessage.setTextFill(Color.web("008000"));
            lblMessage.setText(library.printMsgLog());
        }
    }

    public String getFirstNumbersInString(String line) {
        if (line == null) line = "";
        boolean numberFound = false;
        String result = "";
        for (int i = 0; i < line.length(); i++) {
            Character c = line.charAt(i);
            if (!Character.isDigit(c) && !numberFound) { //no numbers were found yet
                //do nothing
            } else if (Character.isDigit(c)) { //it's a digit
                result += c;
                numberFound = true;
            } else { //digits were found before and this is a second occurrence of a non-digit substring
                return result;
            }
        }
        return result;
    }

    public void loadData() {
        //prepopulate listview with data
        library.printResults(library.searchBook(""));
        lstBooks.setItems(FXCollections.observableArrayList(library.msgLog));
        library.msgLog.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblMessage.setText("");
    }
}
