package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuPage implements Initializable {
    Library library;

    @FXML private Button btnAddUser;
    @FXML private Button btnAddBook;
    @FXML private Button btnIssueBook;
    @FXML private Button btnReturnBook;
    @FXML private Button btnViewBooks;
    @FXML private Button btnUserDetails;

    public void launchAddUserPage(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddUserPage.fxml"));
        Parent root = loader.load();

        AddUserPage controller = loader.getController();
        controller.library = library;

        stage.setTitle("Add User");
        stage.initModality(Modality.WINDOW_MODAL);
        Stage parentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.setScene(new Scene(root, 300, 350));
        stage.show();
    }

    public void launchAddBookPage(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddBookPage.fxml"));
        Parent root = loader.load();

        AddBookPage controller = loader.getController();
        controller.library = library;

        stage.setTitle("Add Book");
        stage.initModality(Modality.WINDOW_MODAL);
        Stage parentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.setScene(new Scene(root, 300, 350));
        stage.show();
    }

    public void launchUserDetailsPage(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("UserDetailsPage.fxml"));
        Parent root = loader.load();

        UserDetailsPage controller = loader.getController();
        controller.library = library;
        controller.loadData();

        stage.setTitle("User Details");
        stage.initModality(Modality.WINDOW_MODAL);
        Stage parentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.setScene(new Scene(root, 350, 500));
        stage.show();
    }

    public void launchIssueBookPage(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("IssueBookPage.fxml"));
        Parent root = loader.load();

        IssueBookPage controller = loader.getController();
        controller.library = library;
        controller.loadData();

        stage.setTitle("Issue Book to User");
        stage.initModality(Modality.WINDOW_MODAL);
        Stage parentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.setScene(new Scene(root, 600, 450));
        stage.show();
    }

    public void launchReturnBookPage(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ReturnBookPage.fxml"));
        Parent root = loader.load();

        ReturnBookPage controller = loader.getController();
        controller.library = library;
        controller.loadData();

        stage.setTitle("Return Book");
        stage.initModality(Modality.WINDOW_MODAL);
        Stage parentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.setScene(new Scene(root, 350, 450));
        stage.show();
    }

    public void launchViewBooksPage(ActionEvent e) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewBooksPage.fxml"));
        Parent root = loader.load();

        ViewBooksPage controller = loader.getController();
        controller.library = library;
        controller.loadData();

        stage.setTitle("View Issued Books");
        stage.initModality(Modality.WINDOW_MODAL);
        Stage parentStage = (Stage) ((Node) e.getSource()).getScene().getWindow();
        stage.initOwner(parentStage);
        stage.setScene(new Scene(root, 350, 450));
        stage.show();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
