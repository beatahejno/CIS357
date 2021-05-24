package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddUserPage implements Initializable {
    Library library;
    @FXML private TextField txtName;
    @FXML private TextField txtEmail;
    @FXML private TextArea txtAddress;
    @FXML private Button btnRegister;
    @FXML private RadioButton radioStudent;
    @FXML private RadioButton radioFaculty;
    @FXML private DatePicker dateDateOfBirth;

    public void registerUser(ActionEvent e) throws IOException {
        if(checkValidity()){
            //add user to the library object
            boolean isStudent = false;
            if(radioStudent.isSelected())
                isStudent = true;
            User newUser = new User(txtName.getText().trim(), txtEmail.getText(), txtAddress.getText(),
                    dateDateOfBirth.getValue(), isStudent);
            library.addUser(newUser);

            //pass the library object back to main menu
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenuPage.fxml"));
            loader.load();
            MainMenuPage controller = loader.getController();
            controller.library = library;

            //hide this screen
            Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();
            stage.hide();


            //display information box ("registered Johnny Lewis")
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
        String name = txtName.getText();
        String email = txtEmail.getText();
        String address = txtAddress.getText();
        LocalDate dateOfBirth = dateDateOfBirth.getValue();

        //do some checks on the data
        if( name.trim().isEmpty() || email.trim().isEmpty() || address.trim().isEmpty()
                || dateOfBirth == null ){
            //sth empty
            library.msgLog.add("All of the fields must be filled out!");
            return false;
        }
        for(int i=0; i<name.length(); i++){
            //name contains illegal characters
            if(!Character.isAlphabetic(name.charAt(i)) && name.charAt(i) != ' '){
                library.msgLog.add("Name contains illegal characters!");
                return false;
            }
        }
        if(!email.contains("@") || email.lastIndexOf(".")==-1
                || (email.indexOf("@") > email.lastIndexOf("."))){
            //email in incorrect format
            library.msgLog.add("Email is in incorrect format!");
            return false;
        }

        //there are no error messages, all's good
        return true;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //add radio buttons to toggle group so that only one can be selected at a time
        ToggleGroup radioGroup = new ToggleGroup();
        radioFaculty.setToggleGroup(radioGroup);
        radioStudent.setToggleGroup(radioGroup);

        //default to student
        radioStudent.setSelected(true);
    }
}