package controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.util.Callback;
import model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class UserDetailsPage implements Initializable {
    Library library;
    @FXML
    private ComboBox<User> cbxUser;
    @FXML
    private Label lblName;
    @FXML
    private Label lblEmail;
    @FXML
    private Label lblAddress;
    @FXML
    private Label lblBirthday;
    @FXML
    private Label lblUserType;
    @FXML
    private Label lblBalance;
    @FXML
    private ListView<String> lstBooks;
    @FXML
    private Button btnCollectFine;
    @FXML
    private Label lblMessage;

    public void selectUser() {
        User selectedUser = cbxUser.getValue();

        //fill out the labels with data
        lblName.setText(selectedUser.getName());
        lblAddress.setText(selectedUser.getAddress());
        lblBalance.setText(String.valueOf(selectedUser.getBalance()));
        lblBirthday.setText(selectedUser.getDateOfBirth().toString());
        lblEmail.setText(selectedUser.getEmail());
        lblUserType.setText(selectedUser.getStudent() ? "Student" : "Faculty");

        //set text color to red if balance is positive and display button to collect fine
        if (selectedUser.getBalance() > 0.0) {
            lblBalance.setTextFill(Color.web("ff0000"));
            btnCollectFine.setVisible(true);
        } else {
            lblBalance.setTextFill(Color.web("ffffff"));
            btnCollectFine.setVisible(false);
        }

        //populate the list view
        ArrayList<String> bookNames = new ArrayList<String>();
        for (Transaction t : library.transactions) {
            if (t.getUserID() == selectedUser.getID() && t.isStatus()) {
                Book b = library.getBook(t.getBookID());
                bookNames.add(b.getName());
            }
        }
        lstBooks.setItems(FXCollections.observableArrayList(bookNames));
    }

    public void collectFine(){
        User selectedUser = cbxUser.getValue();
        library.collectFine(selectedUser);
        lblBalance.setText(String.valueOf(selectedUser.getBalance()));
        lblBalance.setTextFill(Color.web("ffffff"));
        printMessage(false);
    }

    public void printMessage(boolean isError){
        if(isError){
            lblMessage.setTextFill(Color.web("ff0000"));
            lblMessage.setText(library.printMsgLog());
        }else{
            lblMessage.setTextFill(Color.web("008000"));
            lblMessage.setText(library.printMsgLog());
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblName.setText("");
        lblAddress.setText("");
        lblBalance.setText("");
        lblBirthday.setText("");
        lblEmail.setText("");
        lblUserType.setText("");
        lblMessage.setText("");
        btnCollectFine.setVisible(false);

        //create a custom combobox so that we have access to the User object
        //credit - stackoverflow user fabian
        // https://stackoverflow.com/questions/41201043/javafx-combobox-using-object-property
        Callback<ListView<User>, ListCell<User>> factory = lv -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? "" : item.getName());
            }
        };
        cbxUser.setCellFactory(factory);
        cbxUser.setButtonCell(factory.call(null));
    }

    public void loadData() {
        //add users to the combo box (can't do in initialize, because library gets passed later)
        cbxUser.setItems(FXCollections.observableArrayList(library.users));
    }
}
